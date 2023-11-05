package com.juanite.model.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Album {
    private int id;
    private String name;
    private Date publication;
    private String photo;
    private List<Artist> artists;
    private List<Song> songs;


    // Constructor
    public Album() {
        this.id = -1;
        this.name = "";
        this.publication = Date.from(Instant.now());
        this.photo = "";
        this.artists = new ArrayList<>();
        this.songs = new ArrayList<>();
    }
    public Album(int id, String name, Date publication, String photo) {
        this.id = id;
        this.name = name;
        this.publication = publication;
        this.photo = photo;
        this.artists = new ArrayList<>();
        this.songs = new ArrayList<>();
    }

    public Album(int id, String name, Date publication, String photo, List<Artist> artists, List<Song> songs) {
        this.id = id;
        this.name = name;
        this.publication = publication;
        this.photo = photo;
        this.artists = artists;
        this.songs = songs;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPublication() {
        return publication;
    }

    public void setPublication(Date publication) {
        this.publication = publication;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Album)) return false;
        Album album = (Album) o;
        return id == album.id && Objects.equals(name, album.name) && Objects.equals(publication, album.publication) && Objects.equals(photo, album.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, publication, photo);
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", publication=" + publication +
                ", photo='" + photo + '\'' +
                '}';
    }
}
