package com.juanite.controller;

import com.juanite.App;
import com.juanite.model.domain.Song;
import com.juanite.util.AppData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

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

    @FXML
    private TextField searchTextField;

    @FXML
    private Button searchButton;

    @FXML
    private Label messageLabel;

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

    /**
     * Handles the search button event. Performs a search in the database and displays the results in the "play" view.
     */
    @FXML
    private void handleSearchButton() {
        /*String searchTerm = searchTextField.getText();
        if (!searchTerm.isEmpty()) {
            List<Song> searchResults = searchInDatabase(searchTerm);
            if (!searchResults.isEmpty()) {
                int songId = searchResults.get(0).getId(); // Supongo que tomas la primera canción encontrada
                FXMLLoader loader = new FXMLLoader(getClass().getResource("play.fxml"));
                Parent playRoot = loader.load();
                PlayController playController = loader.getController();
                playController.setSongId(songId); // Establece el ID seleccionado en el controlador de "play"
                Scene playScene = new Scene(playRoot);
                Stage stage = (Stage) searchButton.getScene().getWindow();
                stage.setScene(playScene);
            } else {
                messageLabel.setText("Canción no encontrada.");
            }
        }*/

        String searchTerm = searchTextField.getText();
        if (!searchTerm.isEmpty()) {
            List<Song> searchResults = searchInDatabase(searchTerm);
            if (!searchResults.isEmpty()) {
                Song selectedSong = searchResults.get(0); // Supongo que tomas la primera canción encontrada

                AppData.setCurrentSong(selectedSong);

                // Luego, cambia a la vista "play" como lo hacías antes
                try {
                    App.setRoot("play");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                messageLabel.setText("Canción no encontrada.");
            }
        }
    }
}