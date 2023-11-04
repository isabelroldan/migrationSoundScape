package com.juanite.model.DAO;

import com.juanite.model.domain.Album;

import java.util.Set;

public interface iAlbumDAO {
    Set<Album> getAll();
    boolean getById(int id);
    Set<Album> getByName(String name);
    Set<Album> getByArtist(String artistName);
}
