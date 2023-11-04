package com.juanite.model.domain;

import java.util.Date;
import java.util.Objects;

public class Album {
    protected int id;
    protected String name;
    protected Date publication;
    protected String photo;

    // Constructor
    public Album(int id, String name, Date publication, String photo) {
        this.id = id;
        this.name = name;
        this.publication = publication;
        this.photo = photo;
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
