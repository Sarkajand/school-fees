package cz.zsduhovacesta.controller;

import cz.zsduhovacesta.model.Classes;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClassDialogController {

    @FXML
    private TextField classNameTextField;
    @FXML
    private ChoiceBox<String> stageChoiceBox;

    private boolean saveClicked = false;
    private Stage editClassStage;

    public void setStage(Stage stage) {
        this.editClassStage = stage;
    }

    boolean isSaveClicked() {
        return saveClicked;
    }

    void setFields(Classes classes) {
        classNameTextField.setText(classes.getClassName());
        stageChoiceBox.setValue(classes.getStage());
    }

    private boolean isInputValid() {
        String errorMessage = "";
        if (!classNameTextField.getText().matches(".+")) {
            errorMessage += "Musíte napsat jméno třídy\n";
        }
        if (stageChoiceBox.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Musíte vybrat stupeň\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert((Alert.AlertType.ERROR));
            alert.initOwner(editClassStage);
            alert.setTitle("Neplatný vstup");
            alert.setHeaderText("Prosím opravte neplatná pole");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            return false;
        }
    }

    public Classes handleSave() {
        if (isInputValid()) {
            saveClicked = true;
            Classes classes = new Classes();
            classes.setClassName(classNameTextField.getText());
            classes.setStage(stageChoiceBox.getSelectionModel().getSelectedItem());
            editClassStage.close();
            return classes;
        }
        return null;
    }

    public void handleCancel() {
        editClassStage.close();
    }
}

