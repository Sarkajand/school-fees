package cz.zsduhovacesta.controller;

import cz.zsduhovacesta.model.Student;
import cz.zsduhovacesta.model.Transaction;
import cz.zsduhovacesta.service.database.DaoManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class TransactionDialogController {

    @FXML
    DatePicker datePicker;

    @FXML
    TextField vsField;

    @FXML
    TextField amountField;

    @FXML
    ChoiceBox <String> paymentMethodChoiceBox;

    @FXML
    TextField bankStatementNumberField;

    @FXML
    TextArea notesArea;

    private boolean saveClicked = false;
    private Stage stage;

    public void initialize () {
        paymentMethodChoiceBox.setValue("hotově");
        datePicker.setValue(LocalDate.now());
        bankStatementNumberField.setText("0");
    }

    public void setStage (Stage stage) {
        this.stage = stage;
    }

    public boolean isSaveClicked () {
        return saveClicked;
    }

    public void setFields (Object object) {
        Transaction transaction = (Transaction) object;
        vsField.setText(Integer.toString(transaction.getVs()));
        amountField.setText(Integer.toString(transaction.getAmount()));
        bankStatementNumberField.setText(Integer.toString(transaction.getBankStatement()));
        notesArea.setText(transaction.getTransactionNotes());
    }

    private boolean isInputValid () {
        String errorMessage = "";
        if (!vsField.getText().matches("\\d+")) {
            errorMessage += "musíte napsat variabilní symbol žáka\n";
        }else {
            int vs = Integer.parseInt(vsField.getText());
            Student student = DaoManager.getInstance().queryStudentByVs(vs);
            if (student == null) {
                errorMessage += "Variabilní symbol nepatří žádnému žákovi\n" +
                        "\tTransakce nemůže být přiřazena\n";
            }
        }
        if (!amountField.getText().matches("\\d+")) {
            errorMessage += "Neplatná částka, musí být číslo\n";
        }
        if (!bankStatementNumberField.getText().matches("\\d+")) {
            errorMessage += "Neplatné číslo bankovního výpisu\n" +
                    "\t u platby v hotovosti nechte 0\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert((Alert.AlertType.ERROR));
            alert.initOwner(stage);
            alert.setTitle("Neplatný vstup");
            alert.setHeaderText("Prosím opravte neplatná pole");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            return false;
        }
    }

    @FXML
    public Transaction handleSave () {
        if (isInputValid()) {
            saveClicked = true;
            Transaction transaction = new Transaction();
            LocalDate date = datePicker.getValue();
            String strDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            transaction.setDate(strDate);
            transaction.setVs(Integer.parseInt(vsField.getText()));
            transaction.setAmount(Integer.parseInt(amountField.getText()));
            transaction.setPaymentMethod(paymentMethodChoiceBox.getSelectionModel().getSelectedItem());
            transaction.setBankStatement(Integer.parseInt(bankStatementNumberField.getText()));
            transaction.setTransactionNotes(notesArea.getText());
            stage.close();
            return transaction;
        }
        return null;
    }

    @FXML
    public void handleCancel() {
        stage.close();
    }
}
