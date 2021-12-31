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
    private ImageView img;
    private Coordinates coordinates;

    public GameObj(int id, double x, double y, String imageAddr) {
        setId(id);
        setX(x);
        setY(y);
        coordinates = new Coordinates(x, y);
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

    public void moveAhead(double moveBy) {
        this.x += moveBy;
    }

    public ImageView getImage() throws FileNotFoundException {
        if (img == null) {
            FileInputStream inputStream = new FileInputStream(getImageAddr());
            Image image = new Image(inputStream);
            ImageView img = new ImageView();
            img.setImage(image);
        }
        return img;
    }
}

class Island extends GameObj {
    private ImageView islandImage;

    public Island(int id, double x, double y, String imageAddr) {
        super(id, x, y, imageAddr);
    }

    @Override
    public ImageView getImage() throws FileNotFoundException {
        if (islandImage == null) {
            FileInputStream inputStream = new FileInputStream(getImageAddr());
            Image image = new Image(inputStream);
            islandImage = new ImageView();
            islandImage.setImage(image);
            islandImage.setX(getX());
            islandImage.setY(getY());
            islandImage.setFitHeight(115);
            islandImage.setPreserveRatio(true);
        }
        return islandImage;
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