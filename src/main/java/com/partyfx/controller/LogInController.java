package com.partyfx.controller;

import com.partyfx.model.exceptions.UserException;
import com.partyfx.model.objects.User;
import com.partyfx.model.services.HashService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {
    @FXML
    private Button buttonLogIn;

    @FXML
    private TextField fieldEmail;

    @FXML
    private Label errorText;

    @FXML
    private PasswordField fieldPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonLogIn.setOnAction(this::logIn);
    }

    private void logIn(ActionEvent event) {
        HashService hashService = new HashService();
        User user = new User(fieldEmail.getText(), hashService.toHash(fieldPassword.getText()));

        try {
            user.logIn();
            loadMenu(event);
        } catch (UserException e) {
            errorText.setText(e.getMessage());
        } catch (Exception e) {
            errorText.setText("Something went wrong");
            throw new RuntimeException(e);
        }

    }

    private void loadMenu(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-menu.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        //MainMenuController controller = fxmlLoader.getController();
        //controller.initData("");
        stage.setScene(scene);
        stage.show();
    }
}