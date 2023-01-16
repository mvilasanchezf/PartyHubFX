package com.partyfx.controller;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterUserController implements Initializable {
    @FXML
    private Button cancelButton;

    @FXML
    private Label emailError;

    @FXML
    private TextField emailInput;

    @FXML
    private Label nameError;

    @FXML
    private TextField nameInput;

    @FXML
    private Label passwordError;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Button registerButton;

    @FXML
    private Label repeatPasswordError;

    @FXML
    private PasswordField repeatPasswordInput;

    private void loadScreen(ActionEvent event, String target) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(target));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void registerUser(){
        HashService hashService = new HashService();
        User user = new User(nameInput.getText(), emailInput.getText(), hashService.toHash(passwordInput.getText()));
        try{
            user.register();
        }catch (Exception e){
            emailError.setText(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private boolean validateRegister(){
        boolean result = true;
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        Pattern patternPassword = Pattern.compile("^(?:(?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$");
        Matcher matcher = pattern.matcher(emailInput.getText());

        if (!matcher.matches()){
            result = false;
            emailError.setText("Invalid email");
        }

        if (nameInput.getText().length() < 4){
            result = false;
            nameError.setText("Name must have at least 4 characters");
        }

        Matcher matcherPassword = patternPassword.matcher(passwordInput.getText());

        if (!(matcherPassword.matches() && passwordInput.getText().length() >= 5)){
            result = false;
            System.out.println("Password invalid: " + passwordInput.getText());
            passwordError.setText("Password must contain at least 1 letter upperCase, 1 number and  5 characters");
        }

        if (!passwordInput.getText().equals(repeatPasswordInput.getText())){
            result = false;
            repeatPasswordError.setText("Password doesnt match");
        }

        return result;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.cancelButton.setOnAction(actionEvent -> {
            try {
                loadScreen(actionEvent, "main-menu.fxml");

            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        this.registerButton.setOnAction(actionEvent-> {
            if (validateRegister()){
                registerUser();
                try {
                    loadScreen(actionEvent, "main-menu.fxml");

                }catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
