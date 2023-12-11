package com.juanite.controller;

import java.io.IOException;
import java.util.List;

import com.juanite.App;
import com.juanite.model.DAO.UserDAO;
import com.juanite.model.domain.User;
import com.juanite.util.AppData;
import com.juanite.util.PasswordAuthentication;
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
        String username = usernameField.getText();
        String passwordText = passwordField.getText();
        PasswordAuthentication passwordAuthentication = new PasswordAuthentication();

        try (UserDAO udao = new UserDAO(new User())) {
            if (Validator.validateUsername(username) && Validator.validatePassword(passwordText)) {
                List<User> users = UserDAO.getByNameAndPassword(username, passwordAuthentication.hash(passwordText));

                for (User user : users) {
                    if (AppData.getPa().authenticate(passwordText, user.getPassword())) {
                        System.out.println("Contraseña correcta");
                        AppData.setCurrentUser(user);
                        App.setRoot("home");
                        return;
                    }
                }
                System.out.println("Usuario no encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void signUpView() throws IOException {
        App.setRoot("signup");
    }
}
