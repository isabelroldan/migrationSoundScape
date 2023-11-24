package com.juanite.controller;

import com.juanite.model.domain.Album;
import com.juanite.model.domain.Artist;
import com.juanite.model.domain.Song;
import com.juanite.util.AppData;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AlbumController {
    @FXML
    private ListView<Song> songListView;

    private Stage stage;

    @FXML
    Text artistNameText;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {

        // Obtener el álbum actual desde AppData
        Album currentAlbum = AppData.getCurrentAlbum();

        /*songListView.getItems().addAll();

        songListView.setOnMouseClicked(this::handleSongClick);*/

        // Obtener las canciones del álbum actual desde AppData
        List<Song> albumSongs = AppData.getCurrentAlbum().getSongs();

        // Extraer los nombres de las canciones y agregarlos a la ListView
        List<String> songNames = albumSongs.stream().map(Song::getName).collect(Collectors.toList());
        songListView.getItems().addAll((Song) songNames);

        // Configurar el evento de clic en la ListView
        songListView.setOnMouseClicked(this::handleSongClick);

        // Configurar la imagen del ImageView con la imagen de la primera canción, si hay canciones en el álbum
        if (!albumSongs.isEmpty()) {
            Song firstSong = albumSongs.get(0);
            String imagePath = firstSong.getUrl();

            // Verificar si la ruta de la imagen no está vacía antes de establecerla en el ImageView
            if (imagePath != null && !imagePath.isEmpty()) {
                Image albumImage = new Image(imagePath);
                ImageView albumImageView = new ImageView(albumImage);
                albumImageView.setFitHeight(199.0);
                albumImageView.setFitWidth(206.0);
                albumImageView.setLayoutX(395.0);
                albumImageView.setLayoutY(18.0);

                // Reemplazar el ImageView existente con el nuevo ImageView que contiene la imagen de la primera canción
                AnchorPane anchorPane = (AnchorPane) songListView.getScene().getRoot();
                anchorPane.getChildren().removeIf(node -> node instanceof ImageView);
                anchorPane.getChildren().add(albumImageView);

                Text albumNameText = (Text) songListView.getScene().getRoot().lookup("#albumNameText");
                albumNameText.setText("Álbum: " + AppData.getCurrentAlbum().getName());

                // Mostrar el nombre del artista en el Text y agregar evento de clic
                if (!currentAlbum.getArtists().isEmpty()) {
                    Artist albumArtist = currentAlbum.getArtists().get(0); // Obtener el primer artista de la lista
                    artistNameText.setText("Artista: " + albumArtist.getName());
                    artistNameText.setOnMouseClicked(event -> navigateToArtistView(albumArtist));
                }
            }
        }


    }

    private void handleSongClick(MouseEvent event) {
        if (event.getClickCount() == 2) {  // Cambiado a doble clic
            Song selectedSong = songListView.getSelectionModel().getSelectedItem();
            if (selectedSong != null) {
                AppData.setCurrentSong(selectedSong);  // Establecer la canción seleccionada en AppData
                loadPlayView(selectedSong);
            }
        }
    }

    private void loadPlayView(Song song) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("play.fxml"));
            Parent root = loader.load();

            PlayController playController = loader.getController();
            //playController.setSong(song);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateToArtistView(Artist artist){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("artist.fxml"));
            Parent root = loader.load();

            PlayController playController = loader.getController();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
