package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.model.Artist;

public class EditArtistController {

    @FXML
    private TextField nameField;

    private Stage dialogStage;
    private boolean editClicked = false;
    private Artist artist;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setField(Artist artist) {
        this.artist = artist;
        this.nameField.setText(artist.getName());
    }

    public boolean isEditClicked() {
        return editClicked;
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";
        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage = "No valid Name";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid name");
            alert.setHeaderText("Please correct invalid field");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            return false;
        }
    }

    public String handleEdit() {
        if (isInputValid()) {
            editClicked = true;
            dialogStage.close();
            return nameField.getText();
        } else return null;
    }
}
