package com.partyfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML
    private Button listUsersButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button registerAdminButton;

    void initData(String text) {
        System.out.println(text);
    }

    private void loadNextScreen(ActionEvent event, String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listUsersButton.setOnAction(actionEvent -> {
            try {
                loadNextScreen(actionEvent, "list-users.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        registerAdminButton.setOnAction(actionEvent -> {
            try {
                loadNextScreen(actionEvent, "register-user.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        logoutButton.setOnAction(actionEvent -> {
            try {
                loadNextScreen(actionEvent, "critter-index.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
