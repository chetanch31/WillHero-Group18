package com.example.willherofinal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HighScoreController {

    @FXML
    private ImageView returnButton;

    @FXML
    void goHome(MouseEvent event) throws IOException {
        Group parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("WillHeroGame.fxml")));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent, 854, 480);
        stage.setScene(scene);
        stage.show();
    }
}
