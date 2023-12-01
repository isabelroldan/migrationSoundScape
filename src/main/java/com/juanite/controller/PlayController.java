package com.juanite.controller;

import com.juanite.App;
import com.juanite.model.DAO.PlaylistDAO;
import com.juanite.model.DAO.SongDAO;
import com.juanite.model.DAO.UserDAO;
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
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class PlayController {
    @FXML

    public Button btn_addToPlaylist;
    @FXML
    public ChoiceBox<Playlist> selector_playlist;
    @FXML
    public Button btn_next;
    @FXML
    public Button btn_previous;
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

        playlists = FXCollections.observableArrayList();
        playlists.clear();
        playlists.addAll(AppData.getCurrentUser().getPlaylists());
        selector_playlist.setItems(playlists);
        if(AppData.getCurrentPL()!=null && !AppData.getCurrentPL().getSongs().isEmpty()) {
            btn_next.setVisible(true);
            btn_previous.setVisible(true);
        }else{
            btn_next.setVisible(false);
            btn_previous.setVisible(false);
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
                pdao.getSongs().add(AppData.getCurrentSong());
                pdao.update();
                pdao.getById(pdao.getId());
                AppData.getCurrentUser().getPlaylists().remove(pdao);
                AppData.getCurrentUser().getPlaylists().add(pdao);
                ((UserDAO) AppData.getCurrentUser()).update();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void nextSong() {
        if(AppData.getCurrentPL().getSongs().indexOf(AppData.getCurrentSong()) != AppData.getCurrentPL().getSongs().size() - 1) {
            AppData.setCurrentSong(AppData.getCurrentPL().getSongs().get(AppData.getCurrentPL().getSongs().indexOf(AppData.getCurrentSong()) + 1));
        } else {
            AppData.setCurrentSong(AppData.getCurrentPL().getSongs().get(0));
        }
        initialize();
    }

    public void previousSong() {
        if(AppData.getCurrentPL().getSongs().indexOf(AppData.getCurrentSong()) != 0) {
            AppData.setCurrentSong(AppData.getCurrentPL().getSongs().get(AppData.getCurrentPL().getSongs().indexOf(AppData.getCurrentSong()) - 1));
        } else {
            AppData.setCurrentSong(AppData.getCurrentPL().getSongs().get(AppData.getCurrentPL().getSongs().size() - 1));
        }
        initialize();
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
    /*AppData.getCurrentSong().getUrl();*/
    @FXML
    public void playSong() {
        if (AppData.getCurrentSong() != null) {
            /*String songPath = "/com/juanite/songs/badbunny_vete.mp3";*/ // Ruta relativa al classpath
            String songPath = AppData.getCurrentSong().getUrl();;

            try {
                InputStream inputStream = getClass().getResourceAsStream(songPath);

                if (inputStream != null) {
                    AdvancedPlayer player = new AdvancedPlayer(inputStream);
                    player.play();
                } else {
                    // El archivo no se pudo cargar
                    System.err.println("No se pudo cargar el archivo de música.");
                }
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }
    }
}

