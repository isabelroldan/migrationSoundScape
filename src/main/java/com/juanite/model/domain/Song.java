package com.juanite.model.domain;

import java.util.Objects;

public class Song {
    private int id;
    private String name;
    private int duration;
    private String genre;
    private String url;
    private Album album;

    public Song(int id, String name, int duration, String genre, String url, Album album) {
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
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
        return id == song.id && duration == song.duration && Objects.equals(name, song.name) && Objects.equals(genre, song.genre) && Objects.equals(url, song.url) && Objects.equals(album, song.album);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, duration, genre, url, album);
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", genre='" + genre + '\'' +
                ", url='" + url + '\'' +
                ", album=" + album +
                '}';
    }
}
