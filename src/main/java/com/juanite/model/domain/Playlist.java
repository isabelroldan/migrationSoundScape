package com.juanite.model.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="playlist")
public class Playlist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="description")
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_user")
    private User owner;
    @ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinTable(
            name = "song_playlist",
            joinColumns = { @JoinColumn(name = "id_song") },
            inverseJoinColumns = { @JoinColumn(name = "id_playlist") }
    )
    private List<Song> songs;
    @ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinTable(
            name = "user_playlist",
            joinColumns = { @JoinColumn(name = "id_person") },
            inverseJoinColumns = { @JoinColumn(name = "id_playlist") }
    )
    private List<User> subscribers;
    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public Playlist() {
        this.id = -1;
        this.name = "";
        this.description = "";
        this.owner = null;
        this.songs = new ArrayList<Song>();
        this.subscribers = new ArrayList<User>();
        this.comments = new ArrayList<Comment>();
    }

    public Playlist(String name, String description, User owner) {
        this.id = -1;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.songs = new ArrayList<Song>();
        this.subscribers = new ArrayList<User>();
        this.comments = new ArrayList<Comment>();
    }

    public Playlist(int id, String name, String description, User owner, List<Song> songs, List<User> subscribers, List<Comment> comments) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.songs = songs;
        this.subscribers = subscribers;
        this.comments = comments;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public List<User> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<User> subscribers) {
        this.subscribers = subscribers;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return id == playlist.id && Objects.equals(name, playlist.name) && Objects.equals(owner, playlist.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, owner);
    }

    @Override
    public String toString() {
        return name;
    }

}
