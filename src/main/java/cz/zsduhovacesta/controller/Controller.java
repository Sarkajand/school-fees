package cz.zsduhovacesta.controller;

import cz.zsduhovacesta.model.BankStatement;
import cz.zsduhovacesta.model.FeesHistory;
import cz.zsduhovacesta.model.Student;
import cz.zsduhovacesta.model.Transaction;
import cz.zsduhovacesta.service.csv.CsvReader;
import cz.zsduhovacesta.service.csv.CsvWriter;
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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
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
    @FXML
    private TextField vsFieldOnTransactionTab;
    @FXML
    private TableView<Student> summaryTable;
    @FXML
    private ChoiceBox<String> classesChoiceBoxOnSummaryTab;
    @FXML
    private ToggleGroup schoolStageToggleGroupOnSummary;

    private DaoManager daoManager = DaoManager.getInstance();
    private ObservableList<Student> students = FXCollections.observableList(DaoManager.getInstance().listAllStudents());
    private final Logger logger = LoggerFactory.getLogger(Controller.class);

    public void listStudents() {
        studentsTable.itemsProperty().set(students);
    }

    public void listBankStatements() {
        ObservableList<BankStatement> bankStatements = FXCollections.observableList(daoManager.listBankStatements());
        bankStatementsTable.itemsProperty().set(bankStatements);
    }

    public void listTransactions() {
        ObservableList<Transaction> transactions = FXCollections.observableList(daoManager.listTransactionsFromExistingStudents());
        transactionsTable.itemsProperty().set(transactions);
    }

    public void listSummary() {
        summaryTable.itemsProperty().set(students);
    }


    @FXML
    public void listStudentsBySchoolStage() {
        classesChoiceBoxOnStudentsTab.valueProperty().set(null);
        RadioButton selectedRadioButton = (RadioButton) schoolStageToggleGroup.getSelectedToggle();
        String selectedSchoolStage = selectedRadioButton.getText();
        if (selectedSchoolStage.equals("všichni")) {
            students = FXCollections.observableList(daoManager.listAllStudents());
        } else {
            students = FXCollections.observableList(daoManager.listStudentsBySchoolStage(selectedSchoolStage));
        }
        listStudents();
    }

    @FXML
    public void listStudentsBySchoolStageOnSummary() {
        classesChoiceBoxOnSummaryTab.valueProperty().set(null);
        RadioButton selectedRadioButton = (RadioButton) schoolStageToggleGroupOnSummary.getSelectedToggle();
        String selectedSchoolStage = selectedRadioButton.getText();
        if (selectedSchoolStage.equals("všichni")) {
            students = FXCollections.observableList(daoManager.listAllStudents());
        } else {
            students = FXCollections.observableList(daoManager.listStudentsBySchoolStage(selectedSchoolStage));
        }
        listSummary();
    }

    @FXML
    public void setClassesChoiceBoxOnStudentsTab() {
        classesChoiceBoxOnStudentsTab.setItems(FXCollections.observableArrayList(daoManager.listClassesNames()));
    }

    @FXML
    public void setClassesChoiceBoxOnSummaryTab() {
        classesChoiceBoxOnSummaryTab.setItems(FXCollections.observableArrayList(daoManager.listClassesNames()));
    }

    @FXML
    public void listStudentsByClass() {
        String className = classesChoiceBoxOnStudentsTab.getSelectionModel().getSelectedItem();
        students = FXCollections.observableList(daoManager.listStudentsByClass(className));
        listStudents();
    }

    @FXML
    public void listStudentsByClassOnSummary() {
        String className = classesChoiceBoxOnSummaryTab.getSelectionModel().getSelectedItem();
        students = FXCollections.observableList(daoManager.listStudentsByClass(className));
        listSummary();
    }

    @FXML
    public void newStudent() {
        try {
            FXMLLoader loader = getLoaderWithSetResource("studentDialog.fxml");
            Stage stage = prepareStage("Nový žák", loader);
            StudentController studentController = loader.getController();
            studentController.setStage(stage);
            stage.showAndWait();
            if (studentController.isSaveClicked()) {
                Student student = studentController.handleSave();
                insertStudent(student);
                listStudentsBySchoolStage();
                listStudentsBySchoolStageOnSummary();
            }
        } catch (Exception e) {
            logger.error("Method newStudent in Controller failed: ", e);
            showAlert("Chyba", "Nepodařilo se vložit žáka");
        }
    }

    private void insertStudent(Student student) throws Exception {
        daoManager.insertStudent(student);
    }

    private FXMLLoader getLoaderWithSetResource(String resource) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Controller.class.getResource(resource));
        return loader;
    }

    private Stage prepareStage(String title, FXMLLoader loader) throws IOException {
        BorderPane page = loader.load();
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainWindow.getScene().getWindow());
        Scene scene = new Scene(page);
        stage.setScene(scene);
        return stage;
    }


    private void showAlert(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    @FXML
    public void editStudent() {
        final Student student = studentsTable.getSelectionModel().getSelectedItem();
        if (student == null) {
            showAlert("Chyba, není vybraný žák", "Musíte vybrat žáka");
        } else {
            try {
                FXMLLoader loader = getLoaderWithSetResource("studentDialog.fxml");
                Stage stage = prepareStage("Upravit žáka", loader);
                StudentController studentController = loader.getController();
                studentController.setStage(stage);
                studentController.setFields(student);
                stage.showAndWait();
                if (studentController.isSaveClicked()) {
                    Student editedStudent = studentController.handleSave();
                    daoManager.editStudent(student.getVS(), editedStudent);
                    listStudentsBySchoolStage();
                    listStudentsBySchoolStageOnSummary();
                }
            } catch (Exception e) {
                logger.error("Method editStudent in Controller failed: ", e);
                showAlert("Chyba", "Nepodařilo se upravit žáka");
            }
        }
    }

    @FXML
    public void deleteStudent() {
        final Student student = studentsTable.getSelectionModel().getSelectedItem();
        if (student == null) {
            showAlert("Chyba, není vybraný žák", "Musíte vybrat žáka");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Smazat žáka");
            alert.setContentText("Chcete smazat žáka: " + student.getLastName() + " " + student.getFirstName() +
                    "? \n pro potvrzení stiskni OK, pro zrušení stiskni Cancel");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    daoManager.deleteStudent(student.getVS());
                    listStudentsBySchoolStage();
                    listStudentsBySchoolStageOnSummary();
                } catch (Exception e) {
                    logger.error("Method deleteStudent in Controller failed: ", e);
                    showAlert("Chyba", "Nepodařilo se smazat studenta");
                }
            }
        }
    }

    @FXML
    public void showClasses() {
        try {
            FXMLLoader loader = getLoaderWithSetResource("classes.fxml");
            Stage stage = prepareStage("Třídy", loader);
            ClassesController controller = loader.getController();
            controller.setStage(stage);
            stage.showAndWait();
            setClassesChoiceBoxOnStudentsTab();
            listStudentsBySchoolStage();
        } catch (IOException e) {
            logger.error("Method showClasses in Controller failed: ", e);
        }
    }

    @FXML
    public void showFeesHistoryDialog() {
        final Student student = summaryTable.getSelectionModel().getSelectedItem();
        if (student == null) {
            showAlert("Chyba, není vybraný žák", "Musíte vybrat žáka");
        } else {
            try {
                FXMLLoader loader = getLoaderWithSetResource("feesHistoryDialog.fxml");
                Stage stage = prepareStage("Historie školného", loader);
                FeesHistoryDialogController controller = loader.getController();
                controller.setStage(stage);
                FeesHistory feesHistory = daoManager.queryFeesHistoryByStudentVs(student.getVS());
                controller.setFields(feesHistory);
                stage.showAndWait();
                if (controller.isSaveClicked()) {
                    FeesHistory editedFeesHistory = controller.handleSave();
                    editedFeesHistory.setStudentVs(feesHistory.getStudentVs());
                    editedFeesHistory.setLastUpdate(feesHistory.getLastUpdate());
                    daoManager.updateFeesHistoryByUser(editedFeesHistory);
                    listStudentsBySchoolStageOnSummary();
                }
            } catch (Exception e) {
                logger.error("Method showFeesHistoryDialog in Controller failed: ", e);
                showAlert("Chyba", "Nepodařilo se upravit historii školného");
            }
        }
    }

    @FXML
    public void importBankStatement() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Přidej bankovní výpis");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("csv soubory", "*.csv"));
        File file = fileChooser.showOpenDialog(stage);
        String path;
        CsvReader csvReader = new CsvReader();
        if (file != null) {
            path = file.getAbsolutePath();
            try {
                BankStatement bankStatement = csvReader.readNewBankStatement(path);
                daoManager.insertBankStatementWithAllTransactions(bankStatement);
                listBankStatements();
                listTransactions();
                listStudentsBySchoolStageOnSummary();
            } catch (Exception e) {
                logger.error("Method importBankStatement in Controller failed: ", e);
                showAlert("Chyba", "nepodařilo se nahrát bankovní účet");
            }
        }
    }

    @FXML
    public void newTransaction() {
        try {
            FXMLLoader loader = getLoaderWithSetResource("transactionDialog.fxml");
            Stage stage = prepareStage("Nová transakce", loader);
            TransactionDialogController controller = loader.getController();
            controller.setStage(stage);
            stage.showAndWait();
            if (controller.isSaveClicked()) {
                Transaction transaction = controller.handleSave();
                daoManager.insertTransaction(transaction);
                listTransactions();
                listStudentsBySchoolStageOnSummary();
            }
        } catch (Exception e) {
            logger.error("Method newTransaction in Controller failed: ", e);
            showAlert("Chyba", "Nepodařilo se vložit transakci");
        }
    }

    @FXML
    public void editTransaction() {
        final Transaction transaction = transactionsTable.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = getLoaderWithSetResource("transactionDialog.fxml");
            Stage stage = prepareStage("Upravit transakci", loader);
            TransactionDialogController controller = loader.getController();
            controller.setStage(stage);
            controller.setFields(transaction);
            stage.showAndWait();
            if (controller.isSaveClicked()) {
                Transaction editedTransaction = controller.handleSave();
                editedTransaction.setId(transaction.getId());
                daoManager.editTransaction(editedTransaction);
                listTransactions();
                listStudentsBySchoolStageOnSummary();
            }
        } catch (Exception e) {
            logger.error("Method newTransaction in Controller failed: ", e);
            showAlert("Chyba", "Nepodařilo se vložit transakci");
        }
    }

    @FXML
    public void deleteTransaction() {
        final Transaction transaction = transactionsTable.getSelectionModel().getSelectedItem();
        if (transaction == null) {
            showAlert("Chyba, není vybraná transakce", "Musíte vybrat transakci");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Smazat transakci");
            alert.setContentText("Chcete smazat transakci z  " + transaction.getDate() + "\n \tod: " +
                    transaction.getLastName() + " " + transaction.getFirstName() +
                    "? \n pro potvrzení stiskni OK, pro zrušení stiskni Cancel");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    daoManager.deleteTransaction(transaction);
                    listTransactions();
                    listStudentsBySchoolStageOnSummary();
                } catch (Exception e) {
                    logger.error("Method deleteTransaction in Controller failed: ", e);
                    showAlert("Chyba", "Nepodařilo se smazat transakci");
                }
            }
        }
    }

    @FXML
    public void listTransactionByVs() {
        try {
            int vs = Integer.parseInt(vsFieldOnTransactionTab.getText());
            ObservableList<Transaction> transactions = FXCollections.observableList(daoManager.listTransactionByVs(vs));
            transactionsTable.itemsProperty().set(transactions);
        } catch (Exception e) {
            logger.warn("List transactions by vs failed", e);
            showAlert("Chyba", "transakce se nepodařilo najít \nmusíte mít vypněný variabilní symbol");
        }
    }

    @FXML
    public void backupDatabase() {
        try {
            daoManager.backupDatabase();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Záloha databáze");
            alert.setContentText("Záloha databáze byla vytvořena");
            alert.showAndWait();
        } catch (SQLException e) {
            logger.error("Backup database failed", e);
            showAlert("Chyba", "Nepodařilo se vytvořit zálohu databáze");
        }
    }

    @FXML
    public void copyVs() {
        Student student = studentsTable.getSelectionModel().getSelectedItem();
        String vs = String.valueOf(student.getVS());
        final ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(vs);
        Clipboard.getSystemClipboard().setContent(clipboardContent);
    }

    @FXML
    public void exportDataToCsv() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("ulož soubor");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("csv soubory", "*.csv"));
            File file = fileChooser.showSaveDialog(new Stage());
            if (file != null) {
                CsvWriter csvWriter = new CsvWriter();
                csvWriter.writeNewCsv(file);
            }
        } catch (IOException e) {
            showAlert("Chyba", "Nepodařilo se exportovat a uložit data");
        }
    }
}
