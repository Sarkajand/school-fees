package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
//        controller.listArtist();

        primaryStage.setTitle("Školné");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.show();
    }

//    @Override
//    public void init() throws Exception {
//        super.init();
//        if (!DataSource.getInstance().open()) {
//            System.out.println("Fatal Error: Couldn't connect database");
//            Platform.exit();
//        }
//    }
//
//    @Override
//    public void stop() throws Exception {
//        super.stop();
//        DataSource.getInstance().close();
//    }
}
