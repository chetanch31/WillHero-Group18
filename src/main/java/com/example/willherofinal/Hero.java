package com.example.willherofinal;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class Hero extends GameObj{

    private double jump;
    private double x_speed, y_speed;
    private static final double GRAVITY = 0.5;
    private static final double max_jump = 10;
    private Timeline heroJumpingTimeline;
    private ImageView heroImage;
    private double distanceMoved;
    private boolean knifeEquipped;
    private boolean shurikenEquipped;

    public Hero(int id, double x, double y, String imgAddr) {

        super(id, x, y, imgAddr);
        distanceMoved = x;
    }

    private void setJump(double jump) { this.jump = jump; }

    public double getJump() { return this.jump; }

    public boolean isKnifeEquipped() {
        return knifeEquipped;
    }

    public boolean isShurikenEquipped() {
        return shurikenEquipped;
    }

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
            GameMainController.playGameOver();
        }
    }

    private boolean isColliding(ArrayList<GameObj> gameObjs) throws IOException, InterruptedException {
        for (GameObj obj : gameObjs) {
            if (obj.getId() == 1 | obj.getId() == 2 | obj.getId() == 3 | obj.getId() == 4) {
                //if hero collides with any island
                if (obj.getImage().getBoundsInParent().intersects(getImage().getBoundsInParent())) {
                    int type = GameMainController.collisionType(heroImage, obj.getImage());
                    if (type == 2) {
                        return true;
                    }
                }
            } else if (obj.getId() == 5 | obj.getId() == 6 | obj.getId() == 7) {
                //if hero collides with any orc
                if (obj.getImage().getBoundsInParent().intersects(getImage().getBoundsInParent())) {
                    int collisionType = GameMainController.collisionType(getImage(), obj.getImage());
                    if ( collisionType == -1 ) {
                        if (obj.getId() == 7) {
                            GameMainController.pushBack(gameObjs, 1);
                        } else {
                            GameMainController.pushBack(gameObjs, 2);
                        }
                            ((Orcs) obj).pushOrc(gameObjs);
                        return false;
                    } else if ( collisionType == 0 ) {
                        GameMainController.playGameOver();
                        return false;
                    } else {
                        return true;
                    }
                }
            } else if ( obj.getId() == 8 ) {
                //if hero collides with any coins
                if (obj.getImage().getBoundsInParent().intersects(getImage().getBoundsInParent())) {
                    GameMainController.numberOfCoins += 1;
                    GameMainController.updateCoins();
                    obj.getImage().setImage(null);
                    gameObjs.remove(obj);
                    return false;
                }
            } else if (obj.getId() == 9 ) {
                //if hero collides with chest
                if (obj.getImage().getBoundsInParent().intersects(getImage().getBoundsInParent())) {
                    ((TreasureChest)obj).updateImage();
                    GameMainController.numberOfCoins += ((MoneyChest)obj).getCoins();
                    GameMainController.updateCoins();
                    return false;
                }
            } else if ( obj.getId() == 10 ) {
                //if hero collides with knife chest
                if (obj.getImage().getBoundsInParent().intersects(getImage().getBoundsInParent())) {
                    ((TreasureChest)obj).updateImage();
                    knifeEquipped = true;
                    return false;
                }
            } else if ( obj.getId() == 11 ){
                //if hero collides with shuriken
                if (obj.getImage().getBoundsInParent().intersects(getImage().getBoundsInParent())) {
                    ((TreasureChest)obj).updateImage();
                    knifeEquipped = false;
                    shurikenEquipped = true;
                    return false;
                }
            } else if ( obj.getId() == 12 ) {
                if (obj.getImage().getBoundsInParent().intersects(getImage().getBoundsInParent())) {
                    ((Bomb)obj).explode(gameObjs);
                    GameMainController.obstaclesList.remove(obj);
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
