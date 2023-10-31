package com.juanite.model.domain;

import javafx.scene.control.DateCell;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class Comment {
    private int id;
    private LocalDateTime date_time;
    public Person person;
    public Playlist playlist;

    public Comment(int id, LocalDateTime date_time, Person person, Playlist playlist) {
        this.id = id;
        this.date_time = date_time;
        this.person = person;
        this.playlist = playlist;
    }

    public Comment(int id){ this(id, LocalDateTime.now(), null,null);}

    public Comment(){ this(-1, LocalDateTime.now(), null,null);}

    public int getId() {
        return id;
    }

    public LocalDateTime getDate_time() {
        return date_time;
    }

    public Person getPerson() {
        return person;
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

    public void setPerson(Person person) {
        this.person = person;
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
                "\n, person=" + person +
                "\n, playlist=" + playlist +
                '.';
    }
}
