package com.example.willherofinal;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
    Timeline moveObstacles;
    private final double x_move = -10;

    Hero hero;

    @FXML
    private AnchorPane gamePane;

    @FXML
    void makeMove(MouseEvent event) throws FileNotFoundException {
        moveObstacles.play();
        hero.jump(obstaclesList);
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
        hero = new Hero(1, 150, 150, defaultHeroHelmet);
        hero.jump(obstaclesList);

        try {
            heroGroup.getChildren().add(hero.getImage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        gamePane.getChildren().add(heroGroup);
        gamePane.getChildren().add(obstacles);

        moveObstacles = new Timeline(new KeyFrame(Duration.millis(10) , e -> {
            try {
                moveScene(obstaclesList);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }));
        moveObstacles.setCycleCount(40);

    }

    private void moveScene(ArrayList<GameObj> obstaclesList) throws FileNotFoundException {
        for (GameObj obstacle : obstaclesList) {
            obstacle.getImage().setLayoutX(obstacle.getImage().getLayoutX() + x_move);
        }
    }
}
