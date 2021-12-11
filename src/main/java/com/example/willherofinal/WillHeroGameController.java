package com.example.willherofinal;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class WillHeroGameController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private ImageView helmetChoose;

    @FXML
    private ImageView highScore;

    @FXML
    private ImageView playButton;

    @FXML
    void chooseHelmet(MouseEvent event) throws IOException {
        Group parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HelmetChoose.fxml")));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent, 854, 480);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void startGame(MouseEvent event) throws IOException {
        Group parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("GameMain.fxml")));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent, 854, 480);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void viewHighScore(MouseEvent event) throws IOException {
        Group parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HighScore.fxml")));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent, 854, 480);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String defaultHeroHelmet = "src/main/java/com/example/willherofinal/img/Helmet3.png";
        String island1img = "src/main/java/com/example/willherofinal/img/island1.png";
        String island2img = "src/main/java/com/example/willherofinal/img/island2.png";

        GameObj island1 = new Island(9, 100, 250, island1img);
        GameObj island2 = new Island(10, 500, 210, island2img);
        Hero hero = new Hero(1, 150, 210, defaultHeroHelmet);


        Group images = null;
        Group obstacles = null;
        try {
            images = new Group(hero.getImage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            obstacles = new Group(island1.getImage(), island2.getImage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        root.getChildren().add(images);
        root.getChildren().add(obstacles);

        TranslateTransition translate = new TranslateTransition();
        translate.setNode(images);
        translate.setDuration(Duration.millis(700));
        translate.setByY(-150);
        translate.setCycleCount(TranslateTransition.INDEFINITE);
        translate.setAutoReverse(true);
        translate.play();
    }
}
