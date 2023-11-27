package com.juanite.util;

import com.juanite.model.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class AppData {

    private static PasswordAuthentication pa = new PasswordAuthentication();
    private static User currentUser;
    private static Song currentSong;
    private static Playlist currentPL;
    private static List<Artist> currentArtist;
    private static Album currentAlbum;
    private static List<Song> searchResults;
    private static List<Playlist> searchResultsPl;
    private static Comment currentComment;
    private static EntityManager manager;
    private static EntityManagerFactory emf;

    public static PasswordAuthentication getPa() {
        return pa;
    }

    public static void setPa(PasswordAuthentication pa2) {
        pa = pa2;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser2) {
        currentUser = currentUser2;
    }

    public static Song getCurrentSong() {
        return currentSong;
    }

    public static void setCurrentSong(Song currentSong2) {
        currentSong = currentSong2;
    }

    public static Playlist getCurrentPL() {
        return currentPL;
    }

    public static void setCurrentPL(Playlist currentPL2) {
        currentPL = currentPL2;
    }

    public static List<Artist> getCurrentArtist() {
        return currentArtist;
    }

    public static void setCurrentArtist(List<Artist> currentArtist2) {
        currentArtist = currentArtist2;
    }

    public static Album getCurrentAlbum() {
        return currentAlbum;
    }

    public static void setCurrentAlbum(Album currentAlbum2) {
        currentAlbum = currentAlbum2;
    }

    public static List<Song> getSearchResults() {
        return searchResults;
    }

    public static void setSearchResults(List<Song> searchResults) {
        AppData.searchResults = searchResults;
    }

    public static List<Playlist> getSearchResultsPl() {
        return searchResultsPl;
    }

    public static void setSearchResultsPl(List<Playlist> searchResultsPl) {
        AppData.searchResultsPl = searchResultsPl;
    }

    public static Comment getCurrentComment() {
        return currentComment;
    }

    public static void setCurrentComment(Comment currentComment) {
        AppData.currentComment = currentComment;
    }

    public static EntityManager getManager() {
        return manager;
    }

    public static void setManager(EntityManager manager) {
        AppData.manager = manager;
    }

    public static EntityManagerFactory getEmf() {
        return emf;
    }

    public static void setEmf(EntityManagerFactory emf) {
        AppData.emf = emf;
    }
}
