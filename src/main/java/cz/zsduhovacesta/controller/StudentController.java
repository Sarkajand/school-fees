package cz.zsduhovacesta.controller;

import cz.zsduhovacesta.model.Student;
import cz.zsduhovacesta.service.database.DaoManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StudentController {

    @FXML
    private TextField lastNameField;
    @FXML
    private TextField firstNameField;
    @FXML
    private ChoiceBox<String> editStudentClassesChoiceBox;
    @FXML
    private TextField VSField;
    @FXML
    private TextField feesField;
    @FXML
    private TextField motherPhoneField;
    @FXML
    private TextField fatherPhoneField;
    @FXML
    private TextField motherEmailField;
    @FXML
    private TextField fatherEmailField;
    @FXML
    private TextArea notesArea;
    @FXML
    private TextArea paymentNotesArea;

    private boolean saveClicked = false;
    private Stage studentStage;

    public void initialize() {
        editStudentClassesChoiceBox.setItems(FXCollections.observableArrayList(DaoManager.getInstance().listClassesNames()));
    }

    public void setStage(Stage stage) {
        this.studentStage = stage;
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    public void setFields(Object object) {
        Student student = (Student) object;
        lastNameField.setText(student.getLastName());
        firstNameField.setText(student.getFirstName());
        VSField.setText(Integer.toString(student.getVS()));
        feesField.setText(Integer.toString(student.getFees()));
        motherPhoneField.setText(student.getMotherPhone());
        fatherPhoneField.setText(student.getFatherPhone());
        motherEmailField.setText(student.getMotherEmail());
        fatherEmailField.setText(student.getFatherEmail());
        notesArea.setText(student.getNotes());
        paymentNotesArea.setText(student.getPaymentNotes());
        editStudentClassesChoiceBox.setValue(student.getClassName());
    }

    private boolean isInputValid() {
        String errorMessage = "";
        if (!lastNameField.getText().matches(".+")) {
            errorMessage += "Neplatné příjmení\n";
        }
        if (!firstNameField.getText().matches(".+")) {
            errorMessage += "Neplatné jméno\n";
        }
        if (!VSField.getText().matches("\\d+")) {
            errorMessage += "Neplatný variabilní symbol, musí být číslo\n";
        }
        if (!feesField.getText().matches("\\d+")) {
            errorMessage += "Neplatné školné, musí být číslo\n";
        }
        if (!motherPhoneField.getText().matches(".+") && !fatherPhoneField.getText().matches(".+")) {
            errorMessage += "Neplatný telefon, zadej alespoň jeden telefon\n";
        }
        if (!motherEmailField.getText().matches(".+[@].+[.].+") && !fatherEmailField.getText().matches(".+[@].+[.].+")) {
            errorMessage += "Neplatný email, zadej alespoň jeden email\n";
        }
        if (editStudentClassesChoiceBox.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Musíte vybrat třídu\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert((Alert.AlertType.ERROR));
            alert.initOwner(studentStage);
            alert.setTitle("Neplatný vstup");
            alert.setHeaderText("Prosím opravte neplatná pole");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            return false;
        }
    }

    @FXML
    public Student handleSave() {
        if (isInputValid()) {
            saveClicked = true;
            Student student = new Student();
            student.setLastName(lastNameField.getText());
            student.setFirstName(firstNameField.getText());
            student.setVS(Integer.parseInt(VSField.getText()));
            student.setFees(Integer.parseInt(feesField.getText()));
            student.setMotherPhone(motherPhoneField.getText());
            student.setFatherPhone(fatherPhoneField.getText());
            student.setMotherEmail(motherEmailField.getText());
            student.setFatherEmail(fatherEmailField.getText());
            student.setNotes(notesArea.getText());
            student.setPaymentNotes(paymentNotesArea.getText());
            student.setClassName(editStudentClassesChoiceBox.getSelectionModel().getSelectedItem());
            student.setClassId(DaoManager.getInstance().queryClassIdByClassName(student.getClassName()));

            studentStage.close();
            return student;
        }
        return null;
    }

    @FXML
    public void handleCancel() {
        studentStage.close();
    }
}

