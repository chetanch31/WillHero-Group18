package com.example.willherofinal;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Hero extends GameObj{

    private double jump;
    private double x_speed, y_speed;
    private static final double GRAVITY = 0.5;
    private static final double max_jump = 10;
    private Timeline heroJumpingTimeline;
    private ImageView heroImage;

    public Hero(int id, double x, double y, String imgAddr) {

        super(id, x, y, imgAddr);
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
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }));
            heroJumpingTimeline.setCycleCount(Timeline.INDEFINITE);
        }

        //Plays the jumping animation
        heroJumpingTimeline.play();
    }

    private void makeHeroJump(ArrayList<GameObj> gameObjs) throws FileNotFoundException {
        heroImage.setLayoutY(heroImage.getLayoutY() + y_speed);
        y_speed += GRAVITY;

        //function reverses motion if hero collides
        if (isColliding(gameObjs)) {
            y_speed = -max_jump;
        }
    }

    private boolean isColliding(ArrayList<GameObj> gameObjs) throws FileNotFoundException {
        for (GameObj obj : gameObjs) {
            if (obj.getImage().getBoundsInParent().intersects(getImage().getBoundsInParent())) {
                return true;
            }
        }
        return false;
    }
}
