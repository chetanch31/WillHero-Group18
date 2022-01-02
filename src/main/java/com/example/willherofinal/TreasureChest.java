package com.example.willherofinal;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TreasureChest extends GameObj {

    private ImageView chestImage;
    private String chestOpen = "src/main/java/com/example/willherofinal/img/ChestOpen.png";

    public TreasureChest(int id, double x, double y, String imageAddr) {
        super(id, x, y, imageAddr);
    }

    @Override
    public ImageView getImage() throws FileNotFoundException {
        if (chestImage == null) {
            FileInputStream inputStream = new FileInputStream(getImageAddr());
            Image image = new Image(inputStream);
            chestImage = new ImageView();
            chestImage.setImage(image);
            chestImage.setX(getX());
            chestImage.setY(getY());
            chestImage.setFitHeight(50);
            chestImage.setPreserveRatio(true);
        }
        return chestImage;
    }

    private String getChestOpen() { return this.chestOpen; }

    public void updateImage() throws FileNotFoundException {
        chestImage.setImage(new Image(new FileInputStream(getChestOpen())));
    }
}

class MoneyChest extends TreasureChest {

    private int coins = 5;

    public MoneyChest(int id, double x, double y, String imageAddr) {
        super(id, x, y, imageAddr);
    }

    public int getCoins() {
        return coins;
    }
}

class KnifeChest extends TreasureChest {

    public KnifeChest(int id, double x, double y, String imageAddr) {
        super(id, x, y, imageAddr);
    }

}

class Shuriken extends TreasureChest {

    public Shuriken(int id, double x, double y, String imageAddr) {
        super(id, x, y, imageAddr);
    }
}