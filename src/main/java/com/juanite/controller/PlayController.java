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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;

public class PlayController {
    @FXML

    public Button btn_addToPlaylist;
    @FXML
    public ChoiceBox<Playlist> selector_playlist;
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
    private ImageView image;

    @FXML
    private Text nameAlbum;

    @FXML
    private Text nameArtist;

    private String botonEstiloOriginal;

    private ObservableList<Playlist> playlists;

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

        if(AppData.getCurrentPL() != null) {
            playlists = FXCollections.observableArrayList();
            playlists.clear();
            playlists.addAll(AppData.getCurrentUser().getPlaylists());
            selector_playlist.setItems(playlists);
            selector_playlist.setVisible(true);
        }else{
            selector_playlist.setVisible(false);
        }

        Song song = AppData.getCurrentSong();

        if (song != null) {
            // Obtén la URL de la imagen y el nombre del álbum de la instancia de Song:
            String imageUrl = song.getAlbum().getPhoto();
            String albumName = song.getAlbum().getName();

            AppData.setCurrentAlbum(song.getAlbum());

            // Obtén el nombre del artista del álbum
            String artistName = song.getAlbum().getArtists().toString();

            AppData.setCurrentArtist(song.getAlbum().getArtists());

            // Actualiza el ImageView y el Text con los datos recuperados:
            if (imageUrl != null && !imageUrl.equals("")) {
                image.setImage(new Image(getClass().getResourceAsStream(AppData.getCurrentAlbum().getPhoto())));
            }

            if (albumName != null) {
                nameAlbum.setText(albumName);
            }

            // Actualiza el campo de texto para el artista del álbum
            if (artistName != null) {
                nameArtist.setText(artistName);
            }
        }

    }

    public void addToPlaylist() {
        if(selector_playlist.getSelectionModel().getSelectedItem() != null) {
            try(PlaylistDAO pdao = new PlaylistDAO(selector_playlist.getSelectionModel().getSelectedItem())) {
                pdao.saveSong(AppData.getCurrentSong());
                pdao.getById(pdao.getId());
                AppData.getCurrentUser().getPlaylists().remove(pdao);
                AppData.getCurrentUser().getPlaylists().add(pdao);
            } catch (Exception e) {
                throw new RuntimeException(e);
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
        AppData.setCurrentSong(null);
        AppData.setCurrentAlbum(null);
        AppData.setCurrentArtist(null);
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
        AppData.setCurrentSong(null);
        AppData.setCurrentAlbum(null);
        AppData.setCurrentArtist(null);
        try {
            App.setRoot("signup");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goToProfile() throws IOException {
        AppData.setCurrentSong(null);
        AppData.setCurrentAlbum(null);
        AppData.setCurrentArtist(null);
        App.setRoot("userProfile");
    }

    public void goToPlaylists() throws IOException {
        AppData.setCurrentSong(null);
        AppData.setCurrentAlbum(null);
        AppData.setCurrentArtist(null);
        App.setRoot("playlists");
    }

    /**
     * Handles a click on the album name text. Navigates to the "album" view and clears the current song data in AppData.
     */
    @FXML
    private void handleNameAlbumTextClick() {

        try {
            App.setRoot("album");
            AppData.setCurrentSong(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles a click on the artist name text. Navigates to the "artist" view and clears the current song and album data in AppData.
     */
    @FXML
    private void handleNameArtistTextClick() {

        try {
            App.setRoot("artist");
            AppData.setCurrentSong(null);
            AppData.setCurrentAlbum(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

