package com.juanite.controller;

import java.io.IOException;

import com.juanite.App;
import com.juanite.model.DAO.UserDAO;
import com.juanite.model.domain.User;
import com.juanite.util.AppData;
import com.juanite.util.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void Login() throws Exception {
        try (UserDAO udao = new UserDAO(new User())) {
            if(Validator.validateUsername(usernameField.getText()) || Validator.validateUsername(usernameField.getText())) {
                if(Validator.validatePassword(passwordField.getText())) {
                    udao.getByName(usernameField.getText());
                    if(udao != null) {
                        if (AppData.getPa().authenticate(passwordField.getText(), udao.getPassword())) {
                            AppData.setCurrentUser(udao);
                            App.setRoot("home");
                        }
                    }
                }
            }
        }
    }
    @FXML
    public void signUpView() throws IOException {
        App.setRoot("signup");
    }
}
