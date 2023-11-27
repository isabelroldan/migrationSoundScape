package com.juanite.model.domain;

import com.juanite.model.Countries;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="artist")
public class Artist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="nationality")
    private Countries nationality;
    @Column(name="photo")
    private String photo;
    private List<Album> albumList;

    public Artist() {
        this.id = -1;
        this.name = "";
        this.nationality = Countries.NONE;
        this.photo = "";
        this.albumList = new ArrayList<Album>();
    }

    public Artist(int id, String name, Countries nationality, String photo) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.photo = photo;
        this.albumList = new ArrayList<Album>();
    }

    public Artist(int id, String name, Countries nationality, String photo, List<Album> albumList) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.photo = photo;
        this.albumList = albumList;
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

    public Countries getNationality() {
        return nationality;
    }

    public void setNationality(Countries nationality) {
        this.nationality = nationality;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Album> getAlbumList() {
        return albumList;
    }

    public void setAlbumList(List<Album> albumList) {
        this.albumList = albumList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return id == artist.id && Objects.equals(name, artist.name) && nationality == artist.nationality;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, nationality);
    }

    @Override
    public String toString() {
        return name;
    }
}
