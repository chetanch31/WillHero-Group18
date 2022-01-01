package com.example.willherofinal;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
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
    Group coinsGroup = new Group();
    public static ArrayList<GameObj> obstaclesList = new ArrayList<GameObj>();
    Timeline moveObstacles;
    private final double x_move = -7;
    public static int numberOfCoins = 130;
    private static Label coinsLabel;


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

        coinsLabel = new Label();
        coinsLabel.setText(String.valueOf(numberOfCoins));
        coinsLabel.setLayoutX(750);
        coinsLabel.setLayoutY(20);

        coinsGroup.getChildren().add(coinsLabel);
        gamePane.getChildren().add(coinsGroup);

        try {
            heroGroup.getChildren().add(hero.getImage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        gamePane.getChildren().add(heroGroup);
        gamePane.getChildren().add(obstacles);

        for (GameObj obj : obstaclesList) {
            if (obj.getId() == 5 | obj.getId() == 6) {
                ((Orcs)obj).jump(obstaclesList);
            }
        }

        moveObstacles = new Timeline(new KeyFrame(Duration.millis(10) , e -> {
            try {
                moveScene(obstaclesList, x_move);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }));
        moveObstacles.setCycleCount(40);

    }

    public static void pushBack(ArrayList<GameObj> gameObjs, double move_x) {
        Timeline push = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            try {
                moveScene(gameObjs, move_x);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }));

        push.setCycleCount(20);
        push.play();
    }

    public static void updateCoins() {
        coinsLabel.setText(String.valueOf(numberOfCoins));
    }

    private static void moveScene(ArrayList<GameObj> obstaclesList, double move_x) throws FileNotFoundException {
        for (GameObj obstacle : obstaclesList) {
            obstacle.getImage().setLayoutX(obstacle.getImage().getLayoutX() + move_x);
        }
    }
}
