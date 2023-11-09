package com.juanite.controller;

import com.juanite.model.domain.Song;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class AlbumController {
    @FXML
    private ListView<Song> songListView;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {

        songListView.getItems().addAll();

        songListView.setOnMouseClicked(this::handleSongClick);
    }

    private void handleSongClick(MouseEvent event) {
        if (event.getClickCount() == 1) {
            Song selectedSong = songListView.getSelectionModel().getSelectedItem();
            if (selectedSong != null) {
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
}
