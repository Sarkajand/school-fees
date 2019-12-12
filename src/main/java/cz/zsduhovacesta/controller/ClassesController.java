package cz.zsduhovacesta.controller;

import cz.zsduhovacesta.model.Classes;
import cz.zsduhovacesta.service.database.DaoManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

public class ClassesController {

    @FXML
    private TableView<Classes> classesTableView;

    private Stage classStage;

    public void initialize() {
        listClasses();
    }

    public void setStage(Stage classStage) {
        this.classStage = classStage;
    }

    public void listClasses() {
        ObservableList<Classes> classes = FXCollections.observableList(DaoManager.getInstance().listAllClasses());
        classesTableView.itemsProperty().set(classes);
    }

    public void newClass() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Controller.class.getResource("classDialog.fxml"));
            BorderPane page = loader.load();

            Stage editClassStage = new Stage();
            editClassStage.setTitle("Nová třída");
            editClassStage.initModality(Modality.WINDOW_MODAL);
            editClassStage.initOwner(classStage);
            Scene scene = new Scene(page);
            editClassStage.setScene(scene);

            ClassDialogController controller = loader.getController();
            controller.setStage(editClassStage);

            editClassStage.showAndWait();

            if (controller.isSaveClicked()) {
                Classes newClass = controller.handleSave();
                DaoManager.getInstance().insertClass(newClass);
                listClasses();
            }
        } catch (Exception e ) {
            showAlert("Chyba", "Nepodařilo se vložit třídu");
        }
    }

    private void showAlert(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void editClass() {
        final Classes classes = classesTableView.getSelectionModel().getSelectedItem();

        if (classes == null) {
            showAlert("Chyba, není vybraná třída", "Musíte vybrat třídu");
        } else {
            try {
                int classId = DaoManager.getInstance().queryClassIdByClassName(classes.getClassName());

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Controller.class.getResource("classDialog.fxml"));
                BorderPane page = loader.load();

                Stage editClassStage = new Stage();
                editClassStage.setTitle("Upravit třídu");
                editClassStage.initModality(Modality.WINDOW_MODAL);
                editClassStage.initOwner(classStage);
                Scene scene = new Scene(page);
                editClassStage.setScene(scene);

                ClassDialogController controller = loader.getController();
                controller.setStage(editClassStage);
                controller.setFields(classes);

                editClassStage.showAndWait();

                if (controller.isSaveClicked()) {
                    Classes editedClass = controller.handleSave();
                    editedClass.setClassId(classId);
                    DaoManager.getInstance().editClass(editedClass);
                    listClasses();
                }
            } catch (Exception e) {
                showAlert("Chyba", "Nepodařilo se upravit třídu");
            }
        }
    }

    public void deleteClass() {
        final Classes classToDelete = classesTableView.getSelectionModel().getSelectedItem();

        if (classToDelete == null) {
            showAlert("Chyba, není vybraná třída", "Musíte vybrat třídu");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Smazat třídu");
            alert.setContentText("Chcete smazat třídu: " + classToDelete.getClassName() +
                    "? \nSmaže i všechny žáky třídy \nPřed smazáním je vhodné udělat zálohu \nPro potvrzení stiskni OK, pro zrušení stiskni Cancel");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    DaoManager.getInstance().deleteClassWithAllStudents(classToDelete);
                    listClasses();
                } catch (Exception e) {
                    showAlert("Chyba", "Nepodařilo se smazat třídu");
                }
            }
        }
    }
}

