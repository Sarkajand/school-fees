package cz.zsduhovacesta;


import cz.zsduhovacesta.controller.Controller;
import cz.zsduhovacesta.model.Student;
import cz.zsduhovacesta.service.database.DaoManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import org.assertj.core.api.Assertions;
import org.testfx.api.FxRobot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import static org.testfx.api.FxAssert.verifyThat;


@ExtendWith(ApplicationExtension.class)
class MainFXTest {
    private final FxRobot robot = new FxRobot();


    @Start
    private void start(Stage stage) throws Exception{
        DaoManager.getInstance().open();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.listStudentsBySchoolStage();
        controller.setClassesChoiceBoxOnStudentsTab();
        stage.setScene(new Scene(root, 1500, 800));
        stage.show();
    }

    @Test
    void SortingStudentsBySchoolStage () {
//        studentsTable = (TableView) robot.lookup("#studentsTable");
//        Student student = (Student) studentsTable.getItems().get(0);
//        int vs = student.getVS();
//        Assertions.assertThat(vs).isEqualTo(254325);
        verifyThat("#studentsTable", Node::isVisible);

        robot.clickOn("#zsRadioButton");

    }

}