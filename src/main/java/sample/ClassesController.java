package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.model.Classes;
import sample.model.DataSource;

import java.io.IOException;
import java.util.Optional;

public class ClassesController {

    @FXML
    private TableView classesTableView;

    private Stage classStage;

    public void initialize() {
        listClasses();
    }

    public void setStage(Stage classStage) {
        this.classStage = classStage;
    }

    public void listClasses() {
        Task<ObservableList<Classes>> task = new Task<ObservableList<Classes>>() {
            @Override
            protected ObservableList<Classes> call() throws Exception {
                return FXCollections.observableArrayList(DataSource.getInstance().listClassesWithStage());
            }
        };
        classesTableView.itemsProperty().bind(task.valueProperty());

        new Thread(task).start();
    }

    public void newClass() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Controller.class.getResource("editClasses.fxml"));
            BorderPane page = loader.load();

            Stage editClassStage = new Stage();
            editClassStage.setTitle("Nová třída");
            editClassStage.initModality(Modality.WINDOW_MODAL);
            editClassStage.initOwner(classStage);
            Scene scene = new Scene(page);
            editClassStage.setScene(scene);

            EditClassesController controller = loader.getController();
            controller.setStage(editClassStage);

            editClassStage.showAndWait();

            if (controller.isSaveClicked()) {
                Classes classes = controller.handleSave();

                Task<Boolean> task = new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        return DataSource.getInstance().insertClass(classes);
                    }
                };
                task.setOnSucceeded(e -> {
                    listClasses();
                });

                new Thread(task).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editClass() {
        final Classes classes = (Classes) classesTableView.getSelectionModel().getSelectedItem();

        if (classes == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Chyba, nevybraná třída");
            alert.setContentText("Musíte vybrat třídu");
            alert.showAndWait();
        } else {
            try {
                int classesId = DataSource.getInstance().findClassIdByClassName(classes.getClassName());

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Controller.class.getResource("editClasses.fxml"));
                BorderPane page = loader.load();

                Stage editClassStage = new Stage();
                editClassStage.setTitle("Upravit třídu");
                editClassStage.initModality(Modality.WINDOW_MODAL);
                editClassStage.initOwner(classStage);
                Scene scene = new Scene(page);
                editClassStage.setScene(scene);

                EditClassesController controller = loader.getController();
                controller.setStage(editClassStage);
                controller.setFields(classes);

                editClassStage.showAndWait();

                if (controller.isSaveClicked()) {
                    Classes editedClass = controller.handleSave();

                    Task<Boolean> task = new Task<Boolean>() {
                        @Override
                        protected Boolean call() throws Exception {
                            return DataSource.getInstance().editClass(classesId, editedClass);
                        }
                    };
                    task.setOnSucceeded(e -> {
                        listClasses();
                    });

                    new Thread(task).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteClass() {
        final Classes classes = (Classes) classesTableView.getSelectionModel().getSelectedItem();

        if (classes == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Chyba, nevybraná třída");
            alert.setContentText("Musíte vybrat třídu");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Smazat třídu");
            alert.setContentText("Chcete smazat třídu: " + classes.getClassName() +
                    "? \n smaže i všechny žáky třídy \n pro potvrzení stiskni ok, pro zrušení stiskni Cancel");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                int classId = DataSource.getInstance().findClassIdByClassName(classes.getClassName());
                Task<Boolean> task = new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        return DataSource.getInstance().deleteClass(classId);
                    }
                };
                task.setOnSucceeded(e -> listClasses());

                new Thread(task).start();
            }

        }
    }
}
