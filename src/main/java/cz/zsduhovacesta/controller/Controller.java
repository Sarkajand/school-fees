package cz.zsduhovacesta.controller;

import cz.zsduhovacesta.model.BankStatement;
import cz.zsduhovacesta.model.Student;
import cz.zsduhovacesta.model.Transaction;
import cz.zsduhovacesta.service.csv.CsvReader;
import cz.zsduhovacesta.service.database.DaoManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Controller {
    @FXML
    private BorderPane mainWindow;
    @FXML
    private TableView<Student> studentsTable;
    @FXML
    private ToggleGroup schoolStageToggleGroup;
    @FXML
    private ChoiceBox<String> classesChoiceBoxOnStudentsTab;
    @FXML
    private TableView<BankStatement> bankStatementsTable;
    @FXML
    private TableView<Transaction> transactionsTable;

    private ObservableList<Student> students = FXCollections.observableList(DaoManager.getInstance().listAllStudents());
    private ObservableList<BankStatement> bankStatements = FXCollections.observableList(DaoManager.getInstance().listBankStatements());
    private ObservableList<Transaction> transactions = FXCollections.observableList(DaoManager.getInstance().listTransactions());

    public void listStudents () {
        studentsTable.itemsProperty().set(students);
    }

    public void listBankStatements () {
        bankStatementsTable.itemsProperty().set(bankStatements);
    }

    public void listTransactions () {
        transactionsTable.itemsProperty().set(transactions);
    }


    @FXML
    public void listStudentsBySchoolStage() {
        classesChoiceBoxOnStudentsTab.valueProperty().set(null);
        RadioButton selectedRadioButton = (RadioButton) schoolStageToggleGroup.getSelectedToggle();
        String selectedSchoolStage = selectedRadioButton.getText();
        if (selectedSchoolStage.equals("všichni")) {
            students = FXCollections.observableList(DaoManager.getInstance().listAllStudents());
            listStudents();
        } else {
            students = FXCollections.observableList(DaoManager.getInstance().listStudentsBySchoolStage(selectedSchoolStage));
            listStudents();
        }
    }

    @FXML
    public void setClassesChoiceBoxOnStudentsTab() {
        classesChoiceBoxOnStudentsTab.setItems(FXCollections.observableArrayList(DaoManager.getInstance().listClassesNames()));
    }

    @FXML
    public void listStudentsByClass() {
        String className = classesChoiceBoxOnStudentsTab.getSelectionModel().getSelectedItem();
        students = FXCollections.observableList(DaoManager.getInstance().listStudentsByClass(className));
        listStudents();
    }

    @FXML
    public void newStudent() {
            try {
                StudentController studentController = setStudentDialogAndGetController("Nový žák", null);
                if (studentController.isSaveClicked()) {
                    Student student = studentController.handleSave();
                    insertStudent(student);
                    listStudentsBySchoolStage();
                }
            } catch (Exception e) {
                showAlert("Chyba", "Nepodařilo se vložit žáka");
            }
    }

    private void insertStudent(Student student) throws Exception {
        DaoManager.getInstance().insertStudent(student);
    }

    private StudentController setStudentDialogAndGetController(String title, Student student) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Controller.class.getResource("studentDialog.fxml"));
        BorderPane page = loader.load();

        Stage studentStage = new Stage();
        studentStage.setTitle(title);
        studentStage.initModality(Modality.WINDOW_MODAL);
        studentStage.initOwner(mainWindow.getScene().getWindow());
        Scene scene = new Scene(page);
        studentStage.setScene(scene);

        StudentController controller = loader.getController();
        controller.setStage(studentStage);
        if (student != null) {
            controller.setFields(student);
        }
        studentStage.showAndWait();
        return controller;
    }

    private void showAlert(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void editStudent() {
        final Student student = studentsTable.getSelectionModel().getSelectedItem();
        if (student == null) {
            showAlert("Chyba, není vybraný žák", "Musíte vybrat žáka");
        } else {
            try {
                StudentController studentController = setStudentDialogAndGetController("Upravit žáka", student);
                if (studentController.isSaveClicked()) {
                    DaoManager.getInstance().deleteStudent(student.getVS());
                    Student editedStudent = studentController.handleSave();
                    DaoManager.getInstance().insertStudent(editedStudent);
                    listStudentsBySchoolStage();
                }
            } catch (Exception e) {
                showAlert("Chyba", "Nepodařilo se upravit žáka");
            }
        }
    }

    public void deleteStudent() {
        final Student student = studentsTable.getSelectionModel().getSelectedItem();
        if (student == null) {
            showAlert("Chyba, není vybraný žák", "Musíte vybrat žáka");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Smazat žáka");
            alert.setContentText("Chcete smazat žáka: " + student.getLastName() + " " + student.getFirstName() +
                    "? \n pro potvrzení stiskni ok, pro zrušení stiskni Cancel");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    DaoManager.getInstance().deleteStudent(student.getVS());
                    listStudentsBySchoolStage();
                } catch (Exception e) {
                    showAlert("Chyba", "Nepodařilo se smazat studenta");
                }
            }
        }
    }

    public void showClasses () {
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

    public void importBankStatement () {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Přidej bankovní výpis");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("csv soubory", "*.csv"));
        File file = fileChooser.showOpenDialog(stage);
        String path = "";
        CsvReader csvReader = new CsvReader();
        if (file != null) {
            path = file.getAbsolutePath();
        }
        try{
            BankStatement bankStatement = csvReader.readNewBankStatement(path);
            DaoManager.getInstance().insertBankStatementWithAllTransactions(bankStatement);
        } catch (Exception e) {
            showAlert("Chyba", "nepodařilo se nahrát bankovní účet");
        }
    }
}
