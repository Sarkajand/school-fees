package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import sample.model.NewSong;

public class NewSongController {

    @FXML
    private TextField artistNameField;
    @FXML
    private TextField albumNameField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField trackField;


    public NewSong processResult() {
        if (isInputValid()) {
            String artist = artistNameField.getText();
            String album = albumNameField.getText();
            String title = titleField.getText();
            int track = Integer.parseInt(trackField.getText());

            return new NewSong(artist, album, title, track);
        }
        return null;
    }

    private boolean isInputValid() {
        String errorMessage = "";
        if (artistNameField.getText() == null || artistNameField.getText().length() == 0) {
            errorMessage = "No valid Artist Name\n";
        }
        if (albumNameField.getText() == null || albumNameField.getText().length() == 0) {
            errorMessage = "No valid Album Name\n";
        }
        if (titleField.getText() == null || titleField.getText().length() == 0) {
            errorMessage = "No valid Title\n";
        }
        if (!trackField.getText().matches("\\d+")) {
            errorMessage = "No valid Track\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid name");
            alert.setHeaderText("Please correct invalid field");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            return false;
        }
    }
}
