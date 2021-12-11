package com.example.willherofinal;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Hero extends GameObj {

    private double jump;

    public Hero(int id, double x, double y, String imgAddr) {

        super(id, x, y, imgAddr);
    }

    private void setJump(double jump) { this.jump = jump; }

    public double getJump() { return this.jump; }

    @Override
    public ImageView getImage() throws FileNotFoundException {

        FileInputStream inputStream = new FileInputStream(getImageAddr());
        Image image = new Image(inputStream);
        ImageView obj = new ImageView();
        obj.setImage(image);
        obj.setX(getX());
        obj.setY(getY());
        obj.setFitHeight(45);
        obj.setPreserveRatio(true);
        return obj;
    }

}
