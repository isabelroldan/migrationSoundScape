package com.juanite.controller;

import com.juanite.App;
import com.juanite.model.DAO.UserDAO;
import com.juanite.util.AppData;
import com.juanite.util.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.SQLException;

public class UserProfileController {
    @FXML
    public TextField txtfld_searchBar;
    @FXML
    public ImageView img_alerts;
    @FXML
    public ImageView img_avatar;
    @FXML
    public Label lbl_username;
    @FXML
    public ImageView img_editUsername;
    @FXML
    public Label lbl_email;
    @FXML
    public ImageView img_editEmail;
    @FXML
    public Button btn_password;
    @FXML
    public ImageView img_changeAvatar;
    @FXML
    public Button btn_changeAvatar;
    @FXML
    public TextField txtfld_username;
    @FXML
    public TextField txtfld_email;
    @FXML
    public Button btn_username;
    @FXML
    public Button btn_email;
    @FXML
    public Button homeButton;
    @FXML
    public Button exploreButton;
    @FXML
    public Button myListsButton;
    @FXML
    public Button logOutButton;
    @FXML
    public Button profileButton;
    private String botonEstiloOriginal;

    public void initialize() {
        lbl_username.setText(AppData.getCurrentUser().getName());
        lbl_email.setText(AppData.getCurrentUser().getEmail());
        lbl_username.setVisible(true);
        lbl_email.setVisible(true);
        img_editUsername.setVisible(true);
        txtfld_username.setVisible(false);
        txtfld_email.setVisible(false);
        btn_username.setVisible(false);
        btn_email.setVisible(false);
        if(!AppData.getCurrentUser().getPhoto().equals("")) {
            img_avatar.setImage(new Image(getClass().getResourceAsStream(AppData.getCurrentUser().getPhoto())));
            img_changeAvatar.setImage(new Image(getClass().getResourceAsStream(AppData.getCurrentUser().getPhoto())));
        }else{
            img_avatar.setImage(new Image(getClass().getResourceAsStream("/com/juanite/images/default_avatar.png")));
            img_changeAvatar.setImage(new Image(getClass().getResourceAsStream("/com/juanite/images/default_avatar.png")));
        }
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

    public void editUsernameMode() {
        txtfld_username.setText(lbl_username.getText());
        txtfld_username.setVisible(true);
        lbl_username.setVisible(false);
        btn_username.setVisible(true);
        img_editUsername.setVisible(false);
    }

    public void editUsername() throws SQLException {
        if(!txtfld_username.getText().equals("")) {
            if(Validator.validateUsername(txtfld_username.getText())) {
                if(!((UserDAO)AppData.getCurrentUser()).userExists(txtfld_username.getText())) {
                    AppData.getCurrentUser().setName(txtfld_username.getText());
                    ((UserDAO) AppData.getCurrentUser()).save();
                }
            }
        }
        lbl_username.setText(AppData.getCurrentUser().getName());
        lbl_username.setVisible(true);
        txtfld_username.setVisible(false);
        btn_username.setVisible(false);
        img_editUsername.setVisible(true);
    }

    public void editEmailMode() {
        txtfld_email.setText(lbl_email.getText());
        txtfld_email.setVisible(true);
        lbl_email.setVisible(false);
        btn_email.setVisible(true);
        img_editEmail.setVisible(false);
    }

    public void editEmail() throws SQLException {
        if(!txtfld_email.getText().equals("")) {
            if(Validator.validateEmail(txtfld_email.getText())) {
                if(!((UserDAO)AppData.getCurrentUser()).emailExists(txtfld_email.getText())) {
                    AppData.getCurrentUser().setEmail(txtfld_email.getText());
                    ((UserDAO) AppData.getCurrentUser()).save();
                }
            }
        }
        lbl_email.setText(AppData.getCurrentUser().getEmail());
        lbl_email.setVisible(true);
        txtfld_email.setVisible(false);
        btn_email.setVisible(false);
        img_editEmail.setVisible(true);
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
            App.setRoot("login");
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
/*
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
        }*/
    }

    public void goToProfile() throws IOException {
        App.setRoot("userProfile");
    }
}
