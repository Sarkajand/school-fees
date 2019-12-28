package cz.zsduhovacesta.controller;

import cz.zsduhovacesta.model.FeesHistory;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FeesHistoryDialogController {

    @FXML
    private TextField januaryTextField;
    @FXML
    private TextField februaryTextField;
    @FXML
    private TextField marchTextField;
    @FXML
    private TextField aprilTextField;
    @FXML
    private TextField mayTextField;
    @FXML
    private TextField juneTextField;
    @FXML
    private TextField julyTextField;
    @FXML
    private TextField augustTextField;
    @FXML
    private TextField septemberTextField;
    @FXML
    private TextField octoberTextField;
    @FXML
    private TextField novemberTextField;
    @FXML
    private TextField decemberTextField;

    private boolean saveClicked = false;
    private Stage stage;

    public void setStage (Stage stage) {
        this.stage = stage;
    }

    public boolean isSaveClicked () {
        return saveClicked;
    }

    public void setFields (FeesHistory feesHistory) {
        januaryTextField.setText(String.valueOf(feesHistory.getJanuary()));
        februaryTextField.setText(String.valueOf(feesHistory.getFebruary()));
        marchTextField.setText(String.valueOf(feesHistory.getMarch()));
        aprilTextField.setText(String.valueOf(feesHistory.getApril()));
        mayTextField.setText(String.valueOf(feesHistory.getMay()));
        juneTextField.setText(String.valueOf(feesHistory.getJune()));
        julyTextField.setText(String.valueOf(feesHistory.getJuly()));
        augustTextField.setText(String.valueOf(feesHistory.getAugust()));
        septemberTextField.setText(String.valueOf(feesHistory.getSeptember()));
        octoberTextField.setText(String.valueOf(feesHistory.getOctober()));
        novemberTextField.setText(String.valueOf(feesHistory.getNovember()));
        decemberTextField.setText(String.valueOf(feesHistory.getDecember()));
    }

    private boolean isInputValid () {
        String errorMessage = "";
        if (!januaryTextField.getText().matches("\\d+")) {
            errorMessage += "Neplatné školné za leden, musí být číslo\n";
        }
        if (!februaryTextField.getText().matches("\\d+")) {
            errorMessage += "Neplatné školné za únor, musí být číslo\n";
        }
        if (!marchTextField.getText().matches("\\d+")) {
            errorMessage += "Neplatné školné za březen, musí být číslo\n";
        }
        if (!aprilTextField.getText().matches("\\d+")) {
            errorMessage += "Neplatné školné za duben, musí být číslo\n";
        }
        if (!mayTextField.getText().matches("\\d+")) {
            errorMessage += "Neplatné školné za květen, musí být číslo\n";
        }
        if (!juneTextField.getText().matches("\\d+")) {
            errorMessage += "Neplatné školné za červen, musí být číslo\n";
        }
        if (!julyTextField.getText().matches("\\d+")) {
            errorMessage += "Neplatné školné za červenec, musí být číslo\n";
        }
        if (!augustTextField.getText().matches("\\d+")) {
            errorMessage += "Neplatné školné za srpen, musí být číslo\n";
        }
        if (!septemberTextField.getText().matches("\\d+")) {
            errorMessage += "Neplatné školné za září, musí být číslo\n";
        }
        if (!octoberTextField.getText().matches("\\d+")) {
            errorMessage += "Neplatné školné za říjen, musí být číslo\n";
        }
        if (!novemberTextField.getText().matches("\\d+")) {
            errorMessage += "Neplatné školné za listopad, musí být číslo\n";
        }
        if (!decemberTextField.getText().matches("\\d+")) {
            errorMessage += "Neplatné školné za prosinec, musí být číslo\n";
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
    public FeesHistory handleSave () {
        if (isInputValid()) {
            saveClicked = true;
            FeesHistory feesHistory = new FeesHistory();
            feesHistory.setJanuary(Integer.parseInt(januaryTextField.getText()));
            feesHistory.setFebruary(Integer.parseInt(februaryTextField.getText()));
            feesHistory.setMarch(Integer.parseInt(marchTextField.getText()));
            feesHistory.setApril(Integer.parseInt(aprilTextField.getText()));
            feesHistory.setMay(Integer.parseInt(mayTextField.getText()));
            feesHistory.setJune(Integer.parseInt(juneTextField.getText()));
            feesHistory.setJuly(Integer.parseInt(julyTextField.getText()));
            feesHistory.setAugust(Integer.parseInt(augustTextField.getText()));
            feesHistory.setSeptember(Integer.parseInt(septemberTextField.getText()));
            feesHistory.setOctober(Integer.parseInt(octoberTextField.getText()));
            feesHistory.setNovember(Integer.parseInt(novemberTextField.getText()));
            feesHistory.setDecember(Integer.parseInt(decemberTextField.getText()));

            stage.close();
            return feesHistory;
        }
        return null;
    }

    @FXML
    public void handleCancel () {
        stage.close();
    }
}
