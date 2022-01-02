package com.example.willherofinal;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Weapon extends GameObj{
    public Weapon(int id, double x, double y, String imageAddr) {
        super(id, x, y, imageAddr);
    }
}

class Knife extends Weapon implements Initializable {

    private ImageView knifeImage;
    private Timeline shootingTimeline;

    public Knife(int id, double x, double y, String imageAddr) {
        super(id, x, y, imageAddr);
    }

    @Override
    public ImageView getImage() throws FileNotFoundException {
        if (knifeImage == null) {
            FileInputStream inputStream = new FileInputStream(getImageAddr());
            Image image = new Image(inputStream);
            knifeImage = new ImageView();
            knifeImage.setImage(image);
            knifeImage.setX(getX());
            knifeImage.setY(getY());
            knifeImage.setFitHeight(10);
            knifeImage.setPreserveRatio(true);
        }
        return knifeImage;
    }

    public void shoot(ArrayList<GameObj> obstacles) {
        shootingTimeline = new Timeline(new KeyFrame(Duration.millis(2), e -> {
            try {
                shootAndKill(obstacles);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }));
        shootingTimeline.setCycleCount(200);
        shootingTimeline.play();

        shootingTimeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                knifeImage.setOpacity(0);
            }
        });
    }


    private void shootAndKill(ArrayList<GameObj> obstacles) throws FileNotFoundException {
        knifeImage.setLayoutX(knifeImage.getLayoutX() + 1);

        if (isCollidingOrc(obstacles)) {
            System.out.println("Killed orc!");
        }
    }

    private boolean isCollidingOrc(ArrayList<GameObj> obstacles) throws FileNotFoundException {
        for (GameObj obj : obstacles) {
            if (obj.getId() == 5 | obj.getId() == 6 | obj.getId() == 7) {
                if (obj.getImage().getBoundsInParent().intersects(knifeImage.getBoundsInParent())) {
                    ((Orcs)obj).pushOrc(obstacles);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (knifeImage == null) {
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(getImageAddr());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Image image = new Image(inputStream);
            knifeImage = new ImageView();
            knifeImage.setImage(image);
            knifeImage.setX(getX());
            knifeImage.setY(getY());
            knifeImage.setFitHeight(25);
            knifeImage.setPreserveRatio(true);
        }
    }
}
