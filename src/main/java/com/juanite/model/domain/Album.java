package com.juanite.model.domain;

import com.juanite.util.AppData;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Album")
public class Album {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "publication")
    private Date publication;

    @Column(name = "photo")
    private String photo;

    @ManyToMany
    @JoinTable(
            name = "artist_album",
            joinColumns = @JoinColumn(name = "id_album"),
            inverseJoinColumns = @JoinColumn(name = "id_artist")
    )
    private List<Artist> artists;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private List<Song> songs;

    // Constructor
    public Album() {
        this.id = -1;
        this.name = "";
        this.publication = Date.valueOf(LocalDate.now());
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
        return name;
    }
}
