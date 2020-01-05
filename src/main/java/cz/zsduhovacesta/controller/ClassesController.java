package cz.zsduhovacesta.controller;

import cz.zsduhovacesta.model.Classes;
import cz.zsduhovacesta.service.database.DaoManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ClassesController {

    @FXML
    private TableView<Classes> classesTableView;

    private Stage classStage;
    private DaoManager daoManager = DaoManager.getInstance();

    public void initialize() {
        listClasses();
    }

    public void setStage(Stage classStage) {
        this.classStage = classStage;
    }

    private void listClasses() {
        ObservableList<Classes> classes = FXCollections.observableList(daoManager.listAllClasses());
        classesTableView.itemsProperty().set(classes);
    }

    public void newClass() {
        try {
            ClassDialogController controller = setClassDialogAndGetController("nová třída", null);
            if (controller.isSaveClicked()) {
                Classes newClass = controller.handleSave();
                daoManager.insertClass(newClass);
                listClasses();
            }
        } catch (Exception e) {
            showAlert("Chyba", "Nepodařilo se vložit třídu");
        }
    }

    private ClassDialogController setClassDialogAndGetController(String title, Classes classes) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Controller.class.getResource("classDialog.fxml"));
        BorderPane page = loader.load();

        Stage classDialogStage = new Stage();
        classDialogStage.setTitle(title);
        classDialogStage.initModality(Modality.WINDOW_MODAL);
        classDialogStage.initOwner(classStage);
        Scene scene = new Scene(page);
        classDialogStage.setScene(scene);

        ClassDialogController controller = loader.getController();
        controller.setStage(classDialogStage);
        if (classes != null) {
            controller.setFields(classes);
        }
        classDialogStage.showAndWait();
        return controller;
    }

    private void showAlert(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void editClass() {
        final Classes classToEdit = classesTableView.getSelectionModel().getSelectedItem();
        if (classToEdit == null) {
            showAlert("Chyba, není vybraná třída", "Musíte vybrat třídu");
        } else {
            try {
                int classId = classToEdit.getClassId();
                ClassDialogController controller = setClassDialogAndGetController("Upravit třídu", classToEdit);
                if (controller.isSaveClicked()) {
                    Classes editedClass = controller.handleSave();
                    editedClass.setClassId(classId);
                    daoManager.editClass(editedClass);
                    listClasses();
                }
            } catch (Exception e) {
                showAlert("Chyba", "Nepodařilo se upravit třídu");
            }
        }
    }

    public void deleteClass() {
        final Classes classToDelete = classesTableView.getSelectionModel().getSelectedItem();
        if (classToDelete == null) {
            showAlert("Chyba, není vybraná třída", "Musíte vybrat třídu");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Smazat třídu");
            alert.setContentText("Chcete smazat třídu: " + classToDelete.getClassName() + "? " +
                    "\nSmaže i všechny žáky třídy \nPřed smazáním je vhodné udělat zálohu " +
                    "\nPro potvrzení stiskni OK, pro zrušení stiskni Cancel");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    daoManager.deleteClassWithAllStudents(classToDelete);
                    listClasses();
                } catch (Exception e) {
                    showAlert("Chyba", "Nepodařilo se smazat třídu");
                }
            }
        }
    }
}

