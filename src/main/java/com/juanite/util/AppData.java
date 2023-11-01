package com.juanite.util;

import com.juanite.model.domain.*;

public class AppData {

    private PasswordAuthentication pa = new PasswordAuthentication();
    private User currentUser;
    private Song currentSong;
    private Playlist currentPL;
    private Artist currentArtist;
    private Album currentAlbum;

    public PasswordAuthentication getPa() {
        return pa;
    }

    public void setPa(PasswordAuthentication pa) {
        this.pa = pa;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public void setCurrentSong(Song currentSong) {
        this.currentSong = currentSong;
    }

    public Playlist getCurrentPL() {
        return currentPL;
    }

    public void setCurrentPL(Playlist currentPL) {
        this.currentPL = currentPL;
    }

    public Artist getCurrentArtist() {
        return currentArtist;
    }

    public void setCurrentArtist(Artist currentArtist) {
        this.currentArtist = currentArtist;
    }

    public Album getCurrentAlbum() {
        return currentAlbum;
    }

    public void setCurrentAlbum(Album currentAlbum) {
        this.currentAlbum = currentAlbum;
    }
}
