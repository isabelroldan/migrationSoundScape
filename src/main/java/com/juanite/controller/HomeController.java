package com.juanite.controller;

import com.juanite.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {
    @FXML
    private Button homeButton;

    @FXML
    private Button exploreButton;

    @FXML
    private Button myListsButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Button profileButton;

    private String botonEstiloOriginal;

    public void initialize() {
        botonEstiloOriginal = homeButton.getStyle();

        homeButton.setOnMouseEntered(event -> changeColorButtonHome(true));
        homeButton.setOnMouseExited(event -> changeColorButtonHome(false));

        exploreButton.setOnMouseEntered(event -> changeColorButtonExplore(true));
        exploreButton.setOnMouseExited(event -> changeColorButtonExplore(false));

        myListsButton.setOnMouseEntered(event -> changeColorButtonmyLists(true));
        myListsButton.setOnMouseExited(event -> changeColorButtonmyLists(false));

        logOutButton.setOnMouseEntered(event -> changeColorButtonlogOut(true));
        logOutButton.setOnMouseExited(event -> changeColorButtonlogOut(false));

        profileButton.setOnMouseEntered(event -> changeColorButtonprofile(true));
        profileButton.setOnMouseExited(event -> changeColorButtonprofile(false));
    }

    /**
     * Change the color of the Home button based on mouse interaction.
     *
     * @param mouseEntered True if the mouse entered the button, false if it exited.
     */
    private void changeColorButtonHome(boolean mouseEntered) {
        if (mouseEntered) {
            homeButton.setStyle("-fx-background-color: #CCCCCC;");
        } else {
            homeButton.setStyle(botonEstiloOriginal);
        }
    }

    /**
     * Change the color of the Explore button based on mouse interaction.
     *
     * @param mouseEntered True if the mouse entered the button, false if it exited.
     */
    private void changeColorButtonExplore(boolean mouseEntered) {
        if (mouseEntered) {
            exploreButton.setStyle("-fx-background-color: #CCCCCC;");
        } else {
            exploreButton.setStyle(botonEstiloOriginal);
        }
    }

    /**
     * Change the color of the My Lists button based on mouse interaction.
     *
     * @param mouseEntered True if the mouse entered the button, false if it exited.
     */
    private void changeColorButtonmyLists(boolean mouseEntered) {
        if (mouseEntered) {
            myListsButton.setStyle("-fx-background-color: #CCCCCC;");
        } else {
            myListsButton.setStyle(botonEstiloOriginal);
        }
    }

    /**
     * Change the color of the Log Out button based on mouse interaction.
     *
     * @param mouseEntered True if the mouse entered the button, false if it exited.
     */
    private void changeColorButtonlogOut(boolean mouseEntered) {
        if (mouseEntered) {
            logOutButton.setStyle("-fx-background-color: #CCCCCC;");
        } else {
            logOutButton.setStyle(botonEstiloOriginal);
        }
    }

    /**
     * Change the color of the Profile button based on mouse interaction.
     *
     * @param mouseEntered True if the mouse entered the button, false if it exited.
     */
    private void changeColorButtonprofile(boolean mouseEntered) {
        if (mouseEntered) {
            profileButton.setStyle("-fx-background-color: #CCCCCC;");
        } else {
            profileButton.setStyle(botonEstiloOriginal);
        }
    }

    /**
     * Handles the action when the Home button is clicked.
     * Redirects the user to the "home" view.
     */
    @FXML
    private void handleHomeButton() {
        try {
            App.setRoot("home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action when the Log Out button is clicked.
     * Redirects the user to the "signup" view.
     */
    @FXML
    private void handleLogOutButton() {
        try {
            App.setRoot("signup");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}