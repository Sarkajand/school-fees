package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import sample.model.DataSource;
import sample.model.Student;


public class Controller {

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











}
//
//    @FXML
//    private TableView artistTable;
//    @FXML
//    private TableView songTable;
//    @FXML
//    private ProgressBar progressBar;
//    @FXML
//    private BorderPane mainWindow;
//    @FXML
//    private Button updateArtistButton;
//    @FXML
//    private Button albumsButton;
//    @FXML
//    private Button songsButton;
//    @FXML
//    private Button deleteButton;
//
//    @FXML
//    public void listArtist() {
//        artistTable.setVisible(true);
//        updateArtistButton.setDisable(false);
//        albumsButton.setDisable(false);
//        songsButton.setDisable(true);
//        deleteButton.setDisable(true);
//        Task<ObservableList<Artist>> task = new GetAllArtistsTask();
//        artistTable.itemsProperty().bind(task.valueProperty());
//        progressBar.progressProperty().bind(task.progressProperty());
//
//        progressBar.setVisible(true);
//
//        task.setOnSucceeded(e -> progressBar.setVisible(false));
//        task.setOnFailed(e -> progressBar.setVisible(false));
//
//        new Thread(task).start();
//    }
//
//    @FXML
//    public void listAlbumsForArtist() {
//        updateArtistButton.setDisable(true);
//        albumsButton.setDisable(true);
//        songsButton.setDisable(false);
//        final Artist artist = (Artist) artistTable.getSelectionModel().getSelectedItem();
//        if (artist == null) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error no artist");
//            alert.setContentText("You have to chose artist");
//            alert.showAndWait();
//            return;
//        }
//        Task<ObservableList<Album>> task = new Task<ObservableList<Album>>() {
//            @Override
//            protected ObservableList<Album> call() throws Exception {
//                return FXCollections.observableArrayList(DataSource.getInstance().queryAlbumForArtistId(artist.getId()));
//            }
//        };
//        artistTable.itemsProperty().bind(task.valueProperty());
//
//        new Thread(task).start();
//    }
//
//    @FXML
//    public void listSongsForAlbum() {
//        artistTable.setVisible(false);
//        albumsButton.setDisable(true);
//        songsButton.setDisable(true);
//        deleteButton.setDisable(false);
//
//
//        final Album album = (Album) artistTable.getSelectionModel().getSelectedItem();
//        if (album == null) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error no album");
//            alert.setContentText("You have to chose album");
//            alert.showAndWait();
//        } else {
//            Task<ObservableList<Song>> task = new Task<ObservableList<Song>>() {
//                @Override
//                protected ObservableList<Song> call() throws Exception {
//                    return FXCollections.observableArrayList(DataSource.getInstance().querySongsForAlbumId(album.getId()));
//                }
//            };
//            songTable.itemsProperty().bind(task.valueProperty());
//
//            new Thread(task).start();
//        }
//    }
//
//    @FXML
//    public void updateArtist() {
//        final Artist artist = (Artist) artistTable.getSelectionModel().getSelectedItem();
//        if (artist == null) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error no artist");
//            alert.setContentText("You have to chose artist");
//            alert.showAndWait();
//        } else {
//            try {
//                FXMLLoader loader = new FXMLLoader();
//                loader.setLocation(Controller.class.getResource("sample/editArtist.fxml"));
//                GridPane page = loader.load();
//
//                Stage dialogStage = new Stage();
//                dialogStage.setTitle("Edit Artist");
//                dialogStage.initModality(Modality.WINDOW_MODAL);
//                dialogStage.initOwner(mainWindow.getScene().getWindow());
//                Scene scene = new Scene(page);
//                dialogStage.setScene(scene);
//
//                EditArtistController controller = loader.getController();
//                controller.setDialogStage(dialogStage);
//                controller.setField(artist);
//
//                dialogStage.showAndWait();
//
//                if (controller.isEditClicked()) {
//                    String name = controller.handleEdit();
//
//                    Task<Boolean> task = new Task<Boolean>() {
//                        @Override
//                        protected Boolean call() throws Exception {
//                            return DataSource.getInstance().updateArtistName(artist.getId(), name);
//                        }
//                    };
//                    task.setOnSucceeded(e -> {
//                        if (task.valueProperty().get()) {
//                            artist.setName(name);
//                            artistTable.refresh();
//                        }
//                    });
//
//                    new Thread(task).start();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @FXML
//    public void newSong() {
//        Dialog<ButtonType> dialog = new Dialog<>();
//        dialog.initModality(Modality.WINDOW_MODAL);
//        dialog.initOwner(mainWindow.getScene().getWindow());
//        dialog.setTitle("Add new song");
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("newSong.fxml"));
//
//
//        try {
//            dialog.getDialogPane().setContent(fxmlLoader.load());
//        } catch (IOException e) {
//            System.out.println("Couldn't load the dialog");
//            e.printStackTrace();
//        }
//
//
//        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
//        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
//
//        Optional<ButtonType> result = dialog.showAndWait();
//
//        if (result.isPresent() && result.get() == ButtonType.OK) {
//            NewSongController controller = fxmlLoader.getController();
//            NewSong newSong = controller.processResult();
//
//            Task<Boolean> task = new Task<Boolean>() {
//                @Override
//                protected Boolean call() throws Exception {
//                    return DataSource.getInstance().insertSong(newSong);
//                }
//            };
//            new Thread(task).start();
//        }
//    }
//
//    @FXML
//    public void deleteSong() {
//        final Song song = (Song) songTable.getSelectionModel().getSelectedItem();
//        int songId = song.getId();
//        if (song == null) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error no artist");
//            alert.setContentText("You have to chose artist");
//            alert.showAndWait();
//        } else {
//            Task<Boolean> task = new Task<Boolean>() {
//                @Override
//                protected Boolean call() throws Exception {
//                    return DataSource.getInstance().deleteSong(song);
//                }
//            };
//            new Thread(task).start();
//        }
//    }
//}
//
//class GetAllArtistsTask extends Task {
//    @Override
//    public ObservableList<Artist> call() {
//        return FXCollections.observableArrayList(DataSource.getInstance().queryArtists(DataSource.ORDER_BY_ASC));
//    }

