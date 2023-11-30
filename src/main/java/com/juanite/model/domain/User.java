package com.juanite.model.domain;

import com.juanite.util.AppData;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "USER")
public class User extends Person {
    @Column(name = "PHOTO")
    private String photo;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Playlist> playlists;

    @ManyToMany(mappedBy = "subscribers")
    private List<Playlist> favoritePlaylists;

    public User() {
        super();
        this.photo = "/com/juanite/images/default_avatar.png";
        this.playlists = new ArrayList<Playlist>();
        this.favoritePlaylists = new ArrayList<Playlist>();
    }
    public User(int id) {
        super();
        this.id = id;
        this.photo = "/com/juanite/images/default_avatar.png";
        this.playlists = new ArrayList<Playlist>();
        this.favoritePlaylists = new ArrayList<Playlist>();
    }
    public User(int id, String name, String email, String password, String photo) {
        super(id,name,email,password);
        this.photo = "/com/juanite/images/default_avatar.png";
        this.playlists = new ArrayList<Playlist>();
        this.favoritePlaylists = new ArrayList<Playlist>();
    }

    public User(int id, String name, String email, String password, String photo, List<Playlist> playlists, List<Playlist> favoritePlaylists) {
        super(id,name,email,password);
        this.photo = "/com/juanite/images/default_avatar.png";
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
        return super.toString();
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
