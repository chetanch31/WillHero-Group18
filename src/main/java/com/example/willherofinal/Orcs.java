package com.example.willherofinal;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Orcs extends GameObj{
    private ImageView orcImage;
    private Timeline orcJumpingTimeline;
    private double x_speed, y_speed;
    private double gravity;
    private double max_jump;


    public Orcs(int id, double x, double y, String imageAddr) {
        super(id, x, y, imageAddr);

        if (id == 5) {
            this.gravity = 0.5;
            this.max_jump = 10;
        } else if (id == 6) {
            this.gravity = 0.3;
            this.max_jump = 8;
        }
    }

    @Override
    public ImageView getImage() throws FileNotFoundException {
        if (orcImage == null) {
            FileInputStream inputStream = new FileInputStream(getImageAddr());
            Image image = new Image(inputStream);
            orcImage = new ImageView();
            orcImage.setImage(image);
            orcImage.setX(getX());
            orcImage.setY(getY());
            orcImage.setFitHeight(40);
            orcImage.setPreserveRatio(true);
        }
        return orcImage;
    }

    public void jump(ArrayList<GameObj> gameObjs) {
        if (orcJumpingTimeline == null) {
            orcJumpingTimeline = new Timeline(new KeyFrame(Duration.millis(24), e -> {
                try {
                    makeHeroJump(gameObjs);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }));
            orcJumpingTimeline.setCycleCount(Timeline.INDEFINITE);
        }

        //Plays the jumping animation
        orcJumpingTimeline.play();
    }

    private void makeHeroJump(ArrayList<GameObj> gameObjs) throws FileNotFoundException {
        orcImage.setLayoutY(orcImage.getLayoutY() + y_speed);
        y_speed += gravity;

        //function reverses motion if hero collides
        if (isColliding(gameObjs)) {
            y_speed = -max_jump;
        }
    }

    private boolean isColliding(ArrayList<GameObj> gameObjs) throws FileNotFoundException {
        for (GameObj obj : gameObjs) {
            if (obj.getId() == 1 | obj.getId() == 2 | obj.getId() == 3 | obj.getId() == 4) {
                if (obj.getImage().getBoundsInParent().intersects(getImage().getBoundsInParent())) {
                    return true;
                }
            }
        }
        return false;
    }
}

class GreenOrc extends Orcs {

    public GreenOrc(int id, double x, double y, String imageAddr) {
        super(id, x, y, imageAddr);
    }
}

class RedOrc extends Orcs {

    public RedOrc(int id, double x, double y, String imageAddr) {
        super(id, x, y, imageAddr);
    }
}