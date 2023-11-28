package com.juanite.model.domain;

import com.juanite.model.Genres;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "song")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "duration")
    private int duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    private Genres genre;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "id_album")
    private Album album;


    @ManyToMany(mappedBy = "songs")
    private List<Playlist> playlists;

    public Song() {
        this.id = -1;
        this.name = "";
        this.duration = -1;
        this.genre = Genres.NONE;
        this.url = "";
        this.album = null;
    }

    public Song(int id, String name, int duration, Genres genre, String url, Album album) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.genre = genre;
        this.url = url;
        this.album = album;
    }

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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Genres getGenre() {
        return genre;
    }

    public void setGenre(Genres genre) {
        this.genre = genre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return id == song.id && Objects.equals(name, song.name) && Objects.equals(album, song.album);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, album);
    }

    @Override
    public String toString() {
        return name;
    }
}
