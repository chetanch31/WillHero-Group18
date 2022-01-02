package com.example.willherofinal;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class GameMainController implements Initializable {

    static Group obstacles = new Group();
    static Group heroGroup = new Group();
    static Group coinsGroup = new Group();
    public static ArrayList<GameObj> obstaclesList = new ArrayList<GameObj>();
    Timeline moveObstacles;
    private final double x_move = -7;
    public static int numberOfCoins = 130;
    private static Label coinsLabel;

    static Hero hero;

    @FXML
    private AnchorPane gamePane;

    @FXML
    private AnchorPane settingsButton;

    @FXML
    private AnchorPane pauseMenuPane;

    @FXML
    private ProgressBar progressBar;

    @FXML
    void makeMove(MouseEvent event) throws FileNotFoundException {
        moveObstacles.play();
        hero.jump(obstaclesList);
        progressBar.setProgress(hero.getDistanceMoved()/10000);
    }

    @FXML
    void settingsButtonClick(MouseEvent event) {
        TranslateTransition moveUp = new TranslateTransition();
        gamePane.setOpacity(0.5);
        moveUp.setByY(-600);
        moveUp.setDuration(Duration.millis(100));
        moveUp.setNode(pauseMenuPane);
        moveUp.play();
    }

    public static void playGameOver() throws InterruptedException, IOException {
        TimeUnit.SECONDS.sleep(1);
        Group parent = FXMLLoader.load(Objects.requireNonNull(GameMainController.class.getResource("GameOver.fxml")));
        Scene scene = new Scene(parent, 854, 480);
        WillHeroGame.mainStage.setScene(scene);
        WillHeroGame.mainStage.show();
    }

    public static int collisionType(ImageView first, ImageView second) throws FileNotFoundException {

        float wy = (float) ((first.getBoundsInParent().getWidth() + second.getBoundsInParent().getWidth()) * (first.getBoundsInParent().getCenterY() - second.getBoundsInParent().getCenterY()));
        float hx = (float) ((first.getBoundsInParent().getHeight() + second.getBoundsInParent().getHeight()) * (first.getBoundsInParent().getCenterX() - second.getBoundsInParent().getCenterX()));

        if (wy > hx) {
            if (wy > -hx) {
                return 0; //bottom
            }
            else {
                return -1; //left
            }
        }
        else  {
            if (wy > -hx) {
                return 1; //right
            }
        }
        return 2; //top
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

        ImageView settingsButtonImage = new ImageView();
        try {
            settingsButtonImage.setImage(new Image(new FileInputStream("src/main/java/com/example/willherofinal/img/settings.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        settingsButtonImage.setFitHeight(60);
        settingsButtonImage.setPreserveRatio(true);
        settingsButton.getChildren().add(settingsButtonImage);

        try {
            createPauseMenu();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String defaultHeroHelmet = "src/main/java/com/example/willherofinal/img/Helmet3.png";
        hero = new Hero(1, 150, 150, defaultHeroHelmet);
        hero.jump(obstaclesList);

        try {
            createProgressBar();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        progressBar.setProgress(hero.getDistanceMoved()/10000);

        coinsLabel = new Label();
        coinsLabel.setText(String.valueOf(numberOfCoins));
        coinsLabel.setLayoutX(70);
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

    public static void pushBack(ArrayList<GameObj> gameObjs, double move_x) throws FileNotFoundException {
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

    public static void startNewGame(MouseEvent event) {

        obstacles = new Group();
        heroGroup = new Group();
        coinsGroup = new Group();
        obstaclesList = new ArrayList<GameObj>();
        Group parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(GameMainController.class.getResource("GameMain.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent, 854, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void goHome(MouseEvent event) {
        obstacles = new Group();
        heroGroup = new Group();
        coinsGroup = new Group();
        obstaclesList = new ArrayList<GameObj>();
        Stage stage;
        WillHeroGame game = new WillHeroGame();
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        try {
            game.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void moveScene(ArrayList<GameObj> obstaclesList, double move_x) throws FileNotFoundException {
        hero.setDistanceMoved(Math.negateExact((int) move_x));
        for (GameObj obstacle : obstaclesList) {
            obstacle.getImage().setLayoutX(obstacle.getImage().getLayoutX() + move_x);
        }
    }

    private void createProgressBar() throws FileNotFoundException {
        ImageView heroImage = new ImageView(new Image(new FileInputStream("src/main/java/com/example/willherofinal/img/Helmet3.png")));
        heroImage.setLayoutX(179);
        heroImage.setLayoutY(14);
        heroImage.setFitWidth(28);
        heroImage.setFitHeight(37);

        ImageView orcImage = new ImageView(new Image(new FileInputStream("src/main/java/com/example/willherofinal/img/OrcBoss.png")));
        orcImage.setLayoutX(631);
        orcImage.setLayoutY(14);
        orcImage.setFitWidth(42);
        orcImage.setFitHeight(37);

        gamePane.getChildren().add(heroImage);
        gamePane.getChildren().add(orcImage);
    }

    private void createPauseMenu() throws FileNotFoundException {
        ImageView backGroundImage = new ImageView(new Image(new FileInputStream("src/main/java/com/example/willherofinal/img/gamePauseScreen.png")));
        backGroundImage.setLayoutX(219);
        backGroundImage.setLayoutY(67);
        backGroundImage.setFitWidth(415);
        backGroundImage.setFitHeight(346);

        ImageView resumeImage = new ImageView(new Image(new FileInputStream("src/main/resources/com/example/willherofinal/static/startButton.png")));
        resumeImage.setLayoutX(361);
        resumeImage.setLayoutY(243);
        resumeImage.setFitWidth(132);
        resumeImage.setFitHeight(127);
        resumeImage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gamePane.setOpacity(1);
                TranslateTransition moveUp = new TranslateTransition();
                moveUp.setByY(600);
                moveUp.setNode(pauseMenuPane);
                moveUp.play();
            }
        });

        ImageView restartImage = new ImageView(new Image(new FileInputStream("src/main/java/com/example/willherofinal/img/restart.png")));
        restartImage.setLayoutX(530);
        restartImage.setLayoutY(307);
        restartImage.setFitWidth(83);
        restartImage.setFitHeight(77);
        restartImage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                startNewGame(event);
            }
        });

        ImageView returnImage = new ImageView(new Image(new FileInputStream("src/main/java/com/example/willherofinal/img/return.png")));
        returnImage.setLayoutX(248);
        returnImage.setLayoutY(307);
        returnImage.setFitWidth(78);
        returnImage.setFitHeight(77);
        returnImage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                goHome(event);
            }
        });

        Button saveButton = new Button();
        saveButton.setText("Save");
        saveButton.setLayoutX(384);
        saveButton.setLayoutY(162);
        saveButton.setPrefWidth(85);
        saveButton.setPrefHeight(25);

        Button loadButton = new Button();
        loadButton.setText("Load");
        loadButton.setLayoutX(384);
        loadButton.setLayoutY(202);
        loadButton.setPrefWidth(85);
        loadButton.setPrefHeight(25);

        pauseMenuPane.getChildren().add(backGroundImage);
        pauseMenuPane.getChildren().add(resumeImage);
        pauseMenuPane.getChildren().add(restartImage);
        pauseMenuPane.getChildren().add(returnImage);
        pauseMenuPane.getChildren().add(saveButton);
        pauseMenuPane.getChildren().add(loadButton);
    }

}
