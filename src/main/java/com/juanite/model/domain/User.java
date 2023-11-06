package com.juanite.model.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User extends Person {
    private String photo;
    private List<Playlist> playlists;
    private List<Playlist> favoritePlaylists;

    public User() {
        super();
        this.photo = "";
        this.playlists = new ArrayList<Playlist>();
        this.favoritePlaylists = new ArrayList<Playlist>();
    }
    public User(int id, String name, String email, String password, String photo) {
        super(id,name,email,password);
        this.photo = photo;
        this.playlists = new ArrayList<Playlist>();
        this.favoritePlaylists = new ArrayList<Playlist>();
    }

    public User(int id, String name, String email, String password, String photo, List<Playlist> playlists, List<Playlist> favoritePlaylists) {
        super(id,name,email,password);
        this.photo = photo;
        this.playlists = playlists;
        this.favoritePlaylists = favoritePlaylists;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public List<Playlist> getFavoritePlaylists() {
        return favoritePlaylists;
    }

    public void setFavoritePlaylists(List<Playlist> favoritePlaylists) {
        this.favoritePlaylists = favoritePlaylists;
    }

    @Override
    public String toString() {
        return "User{" +
                "id_user=" + this.id +
                ", photo='" + photo + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return this.id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
