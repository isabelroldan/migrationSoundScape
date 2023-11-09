package com.juanite.controller;

import com.juanite.model.domain.Song;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PlayController {
    @FXML
    private Label songLabel;

    private Song currentSong;

    public void setSong(Song song) {
        currentSong = song;
        songLabel.setText(song.getName());
        // Lógica para reproducir la canción
    }
}

