package com.juanite.model.DAO;

import com.juanite.model.domain.Album;
import com.juanite.model.domain.Artist;
import com.juanite.model.domain.Song;

import java.util.Set;

public interface iAlbumDAO extends AutoCloseable {
    Set<Album> getAll();
    boolean getById(int id);
    boolean getBySong(Song song);
    Set<Album> getByArtist(Artist artist);
}
