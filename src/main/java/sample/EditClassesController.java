package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.model.Classes;

public class EditClassesController {

    @FXML
    private TextField classNameTextField;
    @FXML
    private ChoiceBox stageChoiceBox;

    private boolean saveClicked = false;
    private Stage editClassStage;

    public void setStage(Stage stage) {
        this.editClassStage = stage;
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    public void setFields(Classes classes) {
        classNameTextField.setText(classes.getClassName());
        stageChoiceBox.setValue(classes.getStage());
    }

    private boolean isInputValid() {
        String errorMessage = "";
        if (!classNameTextField.getText().matches(".+")) {
            errorMessage += "Neplatné jméno třídy\n";
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
            classes.setStage((String) stageChoiceBox.getSelectionModel().getSelectedItem());
            editClassStage.close();
            return classes;
        }
        return null;
    }

    public void handleCancel() {
        editClassStage.close();
    }
}
