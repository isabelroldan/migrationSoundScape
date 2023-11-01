package com.juanite.model.DAO;

import com.juanite.model.Genres;
import com.juanite.model.domain.Album;
import com.juanite.model.domain.Song;

import java.util.Set;

public interface iSongDAO extends AutoCloseable {

    Set<Song> getAll();
    boolean getById(int id);
    Set<Song> getByName(String name);
    Set<Song> getByAlbum(Album album);
    Set<Song> getByGenre(Genres genre);
}
