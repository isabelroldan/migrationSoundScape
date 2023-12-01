package com.juanite.controller;

import com.juanite.App;
import com.juanite.model.DAO.PlaylistDAO;
import com.juanite.model.DAO.SongDAO;
import com.juanite.model.domain.Playlist;
import com.juanite.model.domain.Song;
import com.juanite.util.AppData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SearchResultController {

    @FXML
    public ImageView img_avatar;
    @FXML
    public TextField searchTextField;
    @FXML
    public Button searchButton;
    @FXML
    public Label messageLabel;
    @FXML
    public Button showResult;
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
    @FXML
    public ListView resultListView;

    private String botonEstiloOriginal;
    private ObservableList<Song> songResults;
    private ObservableList<Playlist> playlistResults;
    private boolean isPlaylist;

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

        if(AppData.getSearchResults() == null) {
            isPlaylist = true;
            searchTextField.setPromptText("Buscar una playlist");
            playlistResults = FXCollections.observableArrayList();
            playlistResults.addAll(AppData.getSearchResultsPl());
            resultListView.setItems(playlistResults);
        }else{
            isPlaylist = false;
            searchTextField.setPromptText("Buscar una canción");
            songResults = FXCollections.observableArrayList();
            songResults.addAll(AppData.getSearchResults());
            resultListView.setItems(songResults);
        }

    }

    public void showResult() {
        if(isPlaylist) {
            if(resultListView.getSelectionModel().getSelectedItem() != null) {
                AppData.setCurrentPL(((Playlist)resultListView.getSelectionModel().getSelectedItem()));
                AppData.setSearchResults(null);
                AppData.setSearchResultsPl(null);
                try {
                    App.setRoot("playlist");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }else{
            if(resultListView.getSelectionModel().getSelectedItem() != null) {
                AppData.setCurrentSong(((Song)resultListView.getSelectionModel().getSelectedItem()));
                AppData.setSearchResults(null);
                AppData.setSearchResultsPl(null);
                try {
                    App.setRoot("play");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
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
        AppData.setSearchResults(null);
        AppData.setSearchResultsPl(null);
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
        AppData.setSearchResults(null);
        AppData.setSearchResultsPl(null);
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
        AppData.setSearchResults(null);
        AppData.setSearchResultsPl(null);

        String searchTerm = searchTextField.getText();
        if (!searchTerm.isEmpty()) {
            if(isPlaylist) {
                List<Playlist> playlistSet = new PlaylistDAO(new Playlist()).getSearchResults(searchTerm);
                List<Playlist> searchResults = new ArrayList<>();
                searchResults.addAll(playlistSet);
                if (!searchResults.isEmpty()) {
                    AppData.setSearchResultsPl(searchResults);
                    try {
                        App.setRoot("searchResult");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    messageLabel.setText("Playlist no encontrada.");
                }
            }else{
                List<Song> songsSet = new SongDAO(new Song()).getSearchResults(searchTerm);
                List<Song> searchResults = new ArrayList<>();
                searchResults.addAll(songsSet);
                if (!searchResults.isEmpty()) {
                    AppData.setSearchResults(searchResults);
                    try {
                        App.setRoot("searchResult");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    messageLabel.setText("Canción no encontrada.");
                }
            }
        }
    }

    @FXML
    public void goToProfile() throws IOException {
        App.setRoot("userProfile");
    }

    public void goToPlaylists() throws IOException {
        App.setRoot("playlists");
    }
}
