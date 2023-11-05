package com.juanite.model.DAO;

import com.juanite.model.domain.Playlist;
import com.juanite.model.domain.User;

import java.util.Set;

public interface iPlaylistDAO extends AutoCloseable {

    Set<Playlist> getAll();
    boolean getById(int id);
    Set<Playlist> getByUser(User user, boolean isOwner);
    Set<User> getPlaylistSubscribers();
}
