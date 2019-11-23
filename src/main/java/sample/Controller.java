package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.model.DataSource;
import sample.model.Student;

import java.io.IOException;
import java.util.Optional;


public class Controller {

    @FXML
    private BorderPane mainWindow;
    @FXML
    private TableView studentsTable;
    @FXML
    private ToggleGroup schoolStageToggleGroup;
    @FXML
    private ChoiceBox classesChoiceBoxOnStudentsTab;


    @FXML
    public void listStudents() {
        classesChoiceBoxOnStudentsTab.valueProperty().set(null);
        RadioButton selectedRadioButton = (RadioButton) schoolStageToggleGroup.getSelectedToggle();
        String schoolStageSelected = selectedRadioButton.getText();
        Task<ObservableList<Student>> task = new Task<ObservableList<Student>>() {
            @Override
            protected ObservableList<Student> call() throws Exception {
                if (schoolStageSelected.equals("všichni")) {
                    return FXCollections.observableArrayList(DataSource.getInstance().queryStudent());
                } else {
                    return FXCollections.observableArrayList(DataSource.getInstance().queryStudentsBySchoolStage(schoolStageSelected));
                }
            }
        };
        studentsTable.itemsProperty().bind(task.valueProperty());

        new Thread(task).start();
    }

    @FXML
    public void setClassesChoiceBoxOnStudentsTab() {
        classesChoiceBoxOnStudentsTab.setItems(FXCollections.observableArrayList(DataSource.getInstance().listClasses()));
    }

    @FXML
    public void listStudentsByClass() {
        String className = (String) classesChoiceBoxOnStudentsTab.getSelectionModel().getSelectedItem();
        Task<ObservableList<Student>> task = new Task<ObservableList<Student>>() {
            @Override
            protected ObservableList<Student> call() throws Exception {
                return FXCollections.observableArrayList(DataSource.getInstance().queryStudentsByClass(className));
            }
        };
        studentsTable.itemsProperty().bind(task.valueProperty());

        new Thread(task).start();
    }

    @FXML
    public void newStudent() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Controller.class.getResource("editStudent.fxml"));
            BorderPane page = loader.load();

            Stage studentStage = new Stage();
            studentStage.setTitle("Nový žák");
            studentStage.initModality(Modality.WINDOW_MODAL);
            studentStage.initOwner(mainWindow.getScene().getWindow());
            Scene scene = new Scene(page);
            studentStage.setScene(scene);

            EditStudentController controller = loader.getController();
            controller.setStage(studentStage);

            studentStage.showAndWait();

            if (controller.isSaveClicked()) {
                Student student = controller.handleSave();
                String className = student.getClassName();
                int classId = DataSource.getInstance().findClassIdByClassName(className);

                Task<Boolean> task = new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        return DataSource.getInstance().insertStudent(student, classId);
                    }
                };
                task.setOnSucceeded(e -> {
                    listStudents();
                });

                new Thread(task).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editStudent() {
        final Student student = (Student) studentsTable.getSelectionModel().getSelectedItem();
        if (student == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Chyba, není vybraný žák");
            alert.setContentText("Musíte vybrat žáka");
            alert.showAndWait();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Controller.class.getResource("editStudent.fxml"));
                BorderPane page = loader.load();

                Stage studentStage = new Stage();
                studentStage.setTitle("Nový žák");
                studentStage.initModality(Modality.WINDOW_MODAL);
                studentStage.initOwner(mainWindow.getScene().getWindow());
                Scene scene = new Scene(page);
                studentStage.setScene(scene);

                EditStudentController controller = loader.getController();
                controller.setStage(studentStage);
                controller.setFields(student);

                studentStage.showAndWait();

                if (controller.isSaveClicked()) {
                    DataSource.getInstance().deleteStudent(student.getVS());
                    Student editedStudent = controller.handleSave();
                    String className = editedStudent.getClassName();
                    int classId = DataSource.getInstance().findClassIdByClassName(className);

                    Task<Boolean> task = new Task<Boolean>() {
                        @Override
                        protected Boolean call() throws Exception {
                            return DataSource.getInstance().insertStudent(editedStudent, classId);
                        }
                    };
                    task.setOnSucceeded(e -> {
                        listStudents();
                    });
                    new Thread(task).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteStudent() {
        final Student student = (Student) studentsTable.getSelectionModel().getSelectedItem();
        if (student == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Chyba, není vybraný žák");
            alert.setContentText("Musíte vybrat žáka");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Smazat žáka");
            alert.setContentText("Chcete smazat žáka: " + student.getLastName() + " " + student.getFirstName() +
                    "? \n pro potvrzení stiskni ok, pro zrušení stiskni Cancel");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Task<Boolean> task = new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        return DataSource.getInstance().deleteStudent(student.getVS());
                    }
                };
                task.setOnSucceeded(e -> listStudents());

                new Thread(task).start();
            }
        }
    }

    public void showClasses() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Controller.class.getResource("classes.fxml"));
            BorderPane page = loader.load();

            Stage classesStage = new Stage();
            classesStage.setTitle("Nový žák");
            classesStage.initModality(Modality.WINDOW_MODAL);
            classesStage.initOwner(mainWindow.getScene().getWindow());
            Scene scene = new Scene(page);
            classesStage.setScene(scene);

            ClassesController controller = loader.getController();
            controller.setStage(classesStage);

            classesStage.showAndWait();

            setClassesChoiceBoxOnStudentsTab();
            listStudents();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
