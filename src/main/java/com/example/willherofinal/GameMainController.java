package com.example.willherofinal;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameMainController implements Initializable {

    Group obstacles = new Group();
    Group heroGroup = new Group();
    public static ArrayList<GameObj> obstaclesList = new ArrayList<GameObj>();
    TranslateTransition moveAhead = new TranslateTransition();

    @FXML
    private AnchorPane gamePane;

    @FXML
    void makeMove(MouseEvent event) {
        moveAhead.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String gameMap = "src/main/java/com/example/willherofinal/gameMap.csv";
        BufferedReader reader = null;
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(gameMap));
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                int id = Integer.parseInt(row[0]);
                float x = Float.parseFloat(row[1]);
                float y = Float.parseFloat(row[2]);
                System.out.println("ID: " + id + " X: " + x + " Y: " + y);
                GameObj tempObj = ObstaclesGenerator.generateObstacle(id, x, y);
                obstaclesList.add(tempObj);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (GameObj obj : obstaclesList) {
            try {
                obstacles.getChildren().add(obj.getImage());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        String defaultHeroHelmet = "src/main/java/com/example/willherofinal/img/Helmet3.png";
        Hero hero = new Hero(1, 150, 210, defaultHeroHelmet);
        try {
            heroGroup.getChildren().add(hero.getImage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        gamePane.getChildren().add(heroGroup);
        gamePane.getChildren().add(obstacles);

        TranslateTransition translate = new TranslateTransition();
        translate.setNode(heroGroup);
        translate.setDuration(Duration.millis(700));
        translate.setByY(-150);
        translate.setCycleCount(TranslateTransition.INDEFINITE);
        translate.setAutoReverse(true);
        translate.play();

        moveAhead.setNode(obstacles);
        moveAhead.setByX(-300);

    }
}
