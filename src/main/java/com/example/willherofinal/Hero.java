package com.example.willherofinal;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Hero extends GameObj{

    private double jump;
    private double x_speed, y_speed;
    private static final double GRAVITY = 0.5;
    private static final double max_jump = 10;
    private Timeline heroJumpingTimeline;
    private ImageView heroImage;
    private double distanceMoved;

    public Hero(int id, double x, double y, String imgAddr) {

        super(id, x, y, imgAddr);
        distanceMoved = x;
    }

    private void setJump(double jump) { this.jump = jump; }

    public double getJump() { return this.jump; }

    @Override
    public ImageView getImage() throws FileNotFoundException {
        if (heroImage == null) {
            FileInputStream inputStream = new FileInputStream(getImageAddr());
            Image image = new Image(inputStream);
            heroImage = new ImageView();
            heroImage.setImage(image);
            heroImage.setX(getX());
            heroImage.setY(getY());
            heroImage.setFitHeight(45);
            heroImage.setPreserveRatio(true);
        }

        return heroImage;
    }

    public void jump(ArrayList<GameObj> gameObjs) {
        if (heroJumpingTimeline == null) {
            heroJumpingTimeline = new Timeline(new KeyFrame(Duration.millis(24), e -> {
                try {
                    makeHeroJump(gameObjs);
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }));
            heroJumpingTimeline.setCycleCount(Timeline.INDEFINITE);
        }

        //Plays the jumping animation
        heroJumpingTimeline.play();
    }

    private void makeHeroJump(ArrayList<GameObj> gameObjs) throws IOException, InterruptedException {
        heroImage.setLayoutY(heroImage.getLayoutY() + y_speed);
        y_speed += GRAVITY;

        //function reverses motion if hero collides
        if (isColliding(gameObjs)) {
            y_speed = -max_jump;
        }

        if (heroImage.getBoundsInParent().getMinY() > 440 ) {
            heroJumpingTimeline.pause();
            TimeUnit.SECONDS.sleep(1);
            Group parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("GameOver.fxml")));
            Scene scene = new Scene(parent, 854, 480);
            WillHeroGame.mainStage.setScene(scene);
            WillHeroGame.mainStage.show();
        }
    }

    private int typeOfCollision(Orcs obj) throws FileNotFoundException {
        double heroXStart = getImage().getBoundsInParent().getMinX();
        double heroXEnd = heroXStart + getImage().getFitWidth();

        double orcXStart = obj.getImage().getBoundsInParent().getMinX();
        double orcxEnd = orcXStart + obj.getImage().getFitWidth();

        double heroYStart = getImage().getBoundsInParent().getMinY();
        double heroYEnd = heroYStart + getImage().getFitHeight();

        double orcYStart = obj.getImage().getBoundsInParent().getMinY();
        double orcYEnd = orcYStart + obj.getImage().getFitHeight();

        double xOverlap = Math.min(heroXEnd - orcXStart, orcxEnd - heroXStart);
        double yOverlap = Math.min(heroYEnd - orcYStart, orcYEnd - heroYStart);

        if (yOverlap > xOverlap) { return 0; }

        if (heroYStart < orcYStart) { return 1; }

        return -1;
    }

    private boolean isColliding(ArrayList<GameObj> gameObjs) throws FileNotFoundException {
        for (GameObj obj : gameObjs) {
            if (obj.getId() == 1 | obj.getId() == 2 | obj.getId() == 3 | obj.getId() == 4) {
                //if hero collides with any island
                if (obj.getImage().getBoundsInParent().intersects(getImage().getBoundsInParent())) {
                    return true;
                }
            } else if (obj.getId() == 5 | obj.getId() == 6) {
                //if hero collides with any orc
                if (obj.getImage().getBoundsInParent().intersects(getImage().getBoundsInParent())) {
                    int collisionType = typeOfCollision((Orcs) obj);
                    if ( collisionType == 0 ) {
                        GameMainController.pushBack(gameObjs, 2);
                        ((Orcs) obj).pushOrc(gameObjs);
                        return false;
                    } else if ( collisionType == 1 ) {
                        System.out.println("Crushed");
                    } else {
                        return true;
                    }
                }
            } else if ( obj.getId() == 8 ) {
                //if hero collides with any coint
                if (obj.getImage().getBoundsInParent().intersects(getImage().getBoundsInParent())) {
                    GameMainController.numberOfCoins += 1;
                    GameMainController.updateCoins();
                    obj.getImage().setImage(null);
                    gameObjs.remove(obj);
                    return false;
                }
            }
        }
        return false;
    }

    public void setDistanceMoved(double movedBy) {
        distanceMoved += movedBy;
    }

    public double getDistanceMoved() {
        return distanceMoved;
    }
}
