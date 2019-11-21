package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.model.DataSource;
import sample.model.Student;

import java.io.IOException;


public class Controller {

    @FXML
    private BorderPane mainWindow;
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

    @FXML
    public void newStudent() {
//        todo
        System.out.println("newStudent was called");

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Controller.class.getResource("editStudent.fxml"));
            BorderPane page = loader.load();

            Stage studentStage = new Stage();
            studentStage.setTitle("Nový žák");
            studentStage.initModality(Modality.WINDOW_MODAL);
            studentStage.initOwner(mainWindow.getScene().getWindow());
            Scene scene = new Scene(page);
            studentStage.setScene(scene);

            EditStudentController controller = loader.getController();
            controller.setStage(studentStage);

            studentStage.showAndWait();

            if (controller.isSaveClicked()) {
//                todo
                System.out.println("Save is clicked");

                Student student = controller.handleSave();
                String className = student.getClassName();
//                todo
                System.out.println("classname from created student is " + className);

                int classId = DataSource.getInstance().findClassIdByClassName(className);
//                todo
                System.out.println("class id = " + classId);

                Task<Boolean> task = new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
//                        todo
                        System.out.println("new theread is running and trying call insert student");

                        return DataSource.getInstance().insertStudent(student, classId);
                    }
                };
                task.setOnSucceeded(e -> {
                    if (task.valueProperty().get()) {
//                        todo
                        System.out.println("calling liststudents");
                        listStudents();
                    }
                });

                new Thread(task).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
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
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editStudent.fxml"));
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

