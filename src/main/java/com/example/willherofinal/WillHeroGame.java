package com.example.willherofinal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WillHeroGame extends Application {

    Group root;
    public static Stage mainStage;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(WillHeroGame.class.getResource("WillHeroGame.fxml"));

        mainStage = stage;
        root = fxmlLoader.load();
        Scene scene = new Scene(root, 854, 480);
        stage.setScene(scene);
        stage.setTitle("Will Hero");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
