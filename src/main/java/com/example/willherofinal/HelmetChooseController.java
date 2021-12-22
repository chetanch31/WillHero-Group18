package com.example.willherofinal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelmetChooseController implements Initializable {

    @FXML
    private ChoiceBox<String> helmetChoice;

    @FXML
    private ImageView homeButton;

    private String[] choices = {"Knight", "Queen"};

    @FXML
    void goHome(MouseEvent event) throws IOException {
        Group parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("WillHeroGame.fxml")));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent, 854, 480);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        helmetChoice.getItems().addAll(choices);
    }
}
