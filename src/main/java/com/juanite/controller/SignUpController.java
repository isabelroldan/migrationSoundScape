package com.juanite.controller;

import com.juanite.App;
import com.juanite.model.DAO.UserDAO;
import com.juanite.model.domain.User;
import com.juanite.util.AppData;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.juanite.util.Validator;

import java.io.IOException;

public class SignUpController {

    @FXML
    private TextField emailField;
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void signUp() throws Exception {
        if(!usernameField.getText().equals("") && !passwordField.getText().equals("") && !emailField.getText().equals("")) {
                if(Validator.validatePassword(passwordField.getText())) {
                    if(Validator.validateEmail(emailField.getText())) {
                        if(Validator.validateUsername(usernameField.getText())) {
                            try (UserDAO udao = new UserDAO(new User())) {
                                if(!udao.emailExists(emailField.getText())) {
                                    if(!udao.userExists(usernameField.getText())) {
                                            AppData.setCurrentUser(new User(-1,usernameField.getText(), passwordField.getText(), emailField.getText(),""));
                                            AppData.getCurrentUser().create();
                                            home();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @FXML
    public void home() throws IOException {
        App.setRoot("login");
    }


}
