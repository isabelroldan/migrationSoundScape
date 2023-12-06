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

public class PlaylistController {
    @FXML
    public ImageView img_avatar;
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
    public TextField searchTextField;
    @FXML
    public Button searchButton;
    @FXML
    public Label messageLabel;
    @FXML
    public ListView<Song> songListView;
    @FXML
    public Button btn_goSong;
    @FXML
    public Button btn_removeSong;
    @FXML
    public Button btn_changeName;
    @FXML
    public Label lbl_plName;
    @FXML
    public TextField txtfld_changeName;
    @FXML
    public Button btn_comments;
    private String botonEstiloOriginal;
    private ObservableList<Song> plSongs;

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

        plSongs = FXCollections.observableArrayList();
        List<Song> songs = new ArrayList<>();
        songs.addAll(new SongDAO(new Song()).getByPlaylist(AppData.getCurrentPL()));
        AppData.getCurrentPL().setSongs(songs);
        plSongs.addAll(AppData.getCurrentPL().getSongs());
        songListView.setItems(plSongs);
        songListView.refresh();
        lbl_plName.setText(AppData.getCurrentPL().getName());
        txtfld_changeName.setVisible(false);
        if(AppData.getCurrentPL().getOwner().getId() == AppData.getCurrentUser().getId()) {
            btn_changeName.setVisible(true);
        }else{
            btn_changeName.setVisible(false);
        }
    }

    public void goToSong() throws IOException {
        if(songListView.getSelectionModel().getSelectedItem() != null) {
            AppData.setCurrentSong(((Song)songListView.getSelectionModel().getSelectedItem()));
            App.setRoot("play");
        }
    }

    public void goToComments() {
        try {
            App.setRoot("comments");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove() {
        if(songListView.getSelectionModel().getSelectedItem() != null) {
            try(SongDAO sdao = new SongDAO(((Song)songListView.getSelectionModel().getSelectedItem()))) {
                plSongs.remove(sdao);
                Playlist currentPlaylist = AppData.getCurrentPL();
                try(PlaylistDAO pldao = new PlaylistDAO(AppData.getCurrentPL())) {
                    pldao.remove(currentPlaylist);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void changePlaylistName() {
        if (!txtfld_changeName.isVisible()) {
            txtfld_changeName.setVisible(true);
            txtfld_changeName.setText(AppData.getCurrentPL().getName());
        } else {
            if (!txtfld_changeName.getText().isEmpty()) {
                try (PlaylistDAO pdao = new PlaylistDAO()) {
                    AppData.getCurrentPL().setName(txtfld_changeName.getText());
                    pdao.update(AppData.getCurrentPL()); // Actualiza el nombre de la playlist en la base de datos

                    // Restablece la interfaz y la visibilidad del campo de texto
                    txtfld_changeName.setText("");
                    txtfld_changeName.setVisible(false);
                    initialize(); // Refresca la interfaz si es necesario
                } catch (Exception e) {
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

        String searchTerm = searchTextField.getText();
        if (!searchTerm.isEmpty()) {
            List<Playlist> playlistSet = new PlaylistDAO(new Playlist()).getSearchResults(searchTerm);
            List<Playlist> searchResults = new ArrayList<>();
            searchResults.addAll(playlistSet);
            if (!searchResults.isEmpty()) {
                AppData.setSearchResults(null);
                AppData.setSearchResultsPl(searchResults);
                try {
                    App.setRoot("searchResult");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                messageLabel.setText("Playlist no encontrada.");
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
