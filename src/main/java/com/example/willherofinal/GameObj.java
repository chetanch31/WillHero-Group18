package com.example.willherofinal;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

abstract class GameObj {
    private int id;
    private double x;
    private double y;
    private String imageAddr;

    public GameObj(int id, double x, double y, String imageAddr) {
        setId(id);
        setX(x);
        setY(y);
        setImageAddr(imageAddr);
    }

    private void setId(int id) { this.id = id; }
    private void setX(double x) { this.x = x; }
    private void setY(double y) { this.y = y; }
    private void setImageAddr(String imageAddr) { this.imageAddr = imageAddr; }

    public int getId() { return this.id; }
    public double getX() { return this.x; }
    public double getY() { return this.y; }
    public String getImageAddr() { return this.imageAddr; }

    public ImageView getImage() throws FileNotFoundException {

        FileInputStream inputStream = new FileInputStream(getImageAddr());
        Image image = new Image(inputStream);
        ImageView obj = new ImageView();
        obj.setImage(image);

        return obj;
    }
}

class Island extends GameObj {
    public Island(int id, double x, double y, String imageAddr) {
        super(id, x, y, imageAddr);
    }

    @Override
    public ImageView getImage() throws FileNotFoundException {

        FileInputStream inputStream = new FileInputStream(getImageAddr());
        Image image = new Image(inputStream);
        ImageView obj = new ImageView();
        obj.setImage(image);
        obj.setX(getX());
        obj.setY(getY());
        obj.setFitHeight(115);
        obj.setPreserveRatio(true);

        return obj;
    }
}

class Tree extends GameObj {

    public Tree(int id, double x, double y, String imageAddr) {
        super(id, x, y, imageAddr);
    }

    @Override
    public ImageView getImage() throws FileNotFoundException {

        FileInputStream inputStream = new FileInputStream(getImageAddr());
        Image image = new Image(inputStream);
        ImageView obj = new ImageView();
        obj.setImage(image);
        obj.setX(getX());
        obj.setY(getY());
        obj.setFitHeight(115);
        obj.setPreserveRatio(true);

        return obj;
    }
}