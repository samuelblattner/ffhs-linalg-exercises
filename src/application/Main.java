package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("application.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("LinAlg Semestararbeit");
        primaryStage.setScene(new Scene(root, 1600, 1200));
        primaryStage.show();
        ((ApplicationController) loader.getController()).onApplicationReady();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
