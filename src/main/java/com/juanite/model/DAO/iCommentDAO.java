package com.juanite.model.DAO;

import com.juanite.model.domain.Comment;
import com.juanite.model.domain.Playlist;
import com.juanite.model.domain.User;

import java.util.Set;

public interface iCommentDAO extends AutoCloseable {

    Set<Comment> getAll();
    boolean getById(int id);
    Set<Comment> getByPlaylist(Playlist playlist);
    Set<Comment> getByUser(User user);
}
