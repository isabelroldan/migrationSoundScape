package com.juanite.model.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Comment {
    private int id;
    private LocalDateTime date_time;
    public User user;
    public Playlist playlist;

    public Comment(int id, LocalDateTime date_time/*, Person person, Playlist playlist*/) {
        this.id = id;
        this.date_time = date_time;
        //this.person = person;
        //this.playlist = playlist;
    }

    public Comment(int id){ this(id, LocalDateTime.now()/*, null,null*/);}

    public Comment(){ this(-1, LocalDateTime.now()/*, null,null*/);}

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
        return "Comment: " +
                "\nid=" + id +
                "\n, date_time=" + date_time +
                "\n, person=" + user +
                "\n, playlist=" + playlist +
                '.';
    }
}
