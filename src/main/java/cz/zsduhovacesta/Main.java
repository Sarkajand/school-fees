package cz.zsduhovacesta;

import cz.zsduhovacesta.service.database.DaoManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.BasicConfigurator;


import cz.zsduhovacesta.controller.*;

import java.sql.SQLException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
        BasicConfigurator.configure();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.listStudents();
        controller.setClassesChoiceBoxOnStudentsTab();

        primaryStage.setTitle("Školné");
        primaryStage.setScene(new Scene(root, 1500, 800));
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
        try {
            DaoManager.getInstance().open();
        } catch (SQLException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Chyba spojení s databází");
//            alert.setContentText("Nepodařilo se navázat spojení s databází");
//            alert.showAndWait();
//            Platform.exit();
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        DaoManager.getInstance().close();
    }

}
