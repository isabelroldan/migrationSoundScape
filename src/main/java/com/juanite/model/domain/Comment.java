package com.juanite.model.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Comment {
    private int id;
    private String comment;
    private LocalDateTime date_time;
    private User user;
    private Playlist playlist;

    public Comment(int id, String comment, LocalDateTime date_time, User user, Playlist playlist) {
        this.id = id;
        this.comment = comment;
        this.date_time = date_time;
        this.user = user;
        this.playlist = playlist;
    }

    public Comment(int id){
        this(id,"" ,LocalDateTime.now(), new User(), new Playlist());
    }

    public Comment(){
        this(-1,"" ,LocalDateTime.now(), new User(), new Playlist());
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDate_time() {
        return date_time;
    }

    public Person getUser() {
        return user;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate_time(LocalDateTime date_time) {
        this.date_time = date_time;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id == comment.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return comment;
    }
}
