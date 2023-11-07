package com.juanite.controller;

import java.io.IOException;

import com.juanite.App;
import javafx.fxml.FXML;

public class LoginController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
