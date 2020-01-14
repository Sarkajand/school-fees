package cz.zsduhovacesta;

import cz.zsduhovacesta.controller.Controller;
import cz.zsduhovacesta.service.database.DaoManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {

    private final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        launch(args);
        BasicConfigurator.configure();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
            Parent root = loader.load();
            Controller controller = loader.getController();
            controller.listStudents();
            controller.setClassesChoiceBoxOnStudentsTab();
            controller.listBankStatements();
            controller.listTransactions();
            controller.listSummary();
            controller.setClassesChoiceBoxOnSummaryTab();

            primaryStage.setTitle("Školné");
            primaryStage.setScene(new Scene(root, 1500, 800));
            primaryStage.show();
        } catch (IOException e) {
            logger.error("load main page failed: ", e);
        } catch (Exception e) {
            logger.error("Unexpected exception", e);
        }
    }

    @Override
    public void init() {
        try {
            super.init();
            Connection connection = DriverManager.getConnection("jdbc:sqlite:src\\main\\resources\\database\\schoolFees.db");
            DaoManager.getInstance().open(connection);
            DaoManager.getInstance().automaticUpdateShouldPayAndFeesHistory();
        } catch (SQLException e) {
            logger.error("Open connection failed: ", e);
        } catch (Exception e) {
            logger.error("Exception in method init: ", e);
        }
    }

    @Override
    public void stop() {
        try {
            super.stop();
            DaoManager.getInstance().automaticDatabaseBackup();
            DaoManager.getInstance().close();
        } catch (Exception e) {
            logger.error("Exception in method stop: ", e);
        }
    }
}
