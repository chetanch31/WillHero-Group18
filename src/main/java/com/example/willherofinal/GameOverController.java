package com.example.willherofinal;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class GameOverController implements Initializable {

    @FXML
    private ImageView goHome;

    @FXML
    private ProgressBar heroProgressBar;

    @FXML
    private Label textField;

    @FXML
    private ImageView restartGame;

    @FXML
    private ImageView resurrect;

    @FXML
    void restartGame(MouseEvent event) {
        GameMainController.startNewGame(event);
    }

    @FXML
    void resurrectHero(MouseEvent event) {

    }

    @FXML
    void returnHome(MouseEvent event) {
        GameMainController.goHome(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        heroProgressBar.setProgress(GameMainController.hero.getDistanceMoved()/10000);
        double percentage = (GameMainController.hero.getDistanceMoved()/10000) * 100;
        double rounded = (double) Math.round(percentage * 100)/100;
        textField.setText("You were " + rounded + "% there!");
    }
}
