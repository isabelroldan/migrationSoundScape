package com.juanite.model.DAO;

import com.juanite.connection.ConnectionMySQL;
import com.juanite.model.domain.*;
import com.juanite.util.AppData;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommentDAO extends Comment implements AutoCloseable {

    public CommentDAO(int id,String comment, LocalDateTime date_time, User user, Playlist playlist){
        super(id,comment, date_time, user, playlist);
    }

    public CommentDAO(int id){ getById(id);}

    public CommentDAO(Comment c){
        super(c.getId(), c.getComment(), c.getDate_time(), c.getUser(), c.getPlaylist());
    }

    /**
     * Saves a new comment to the database.
     *
     */
    public void save(Comment comment) {
        EntityManager em = AppData.getManager();
        em.getTransaction().begin();
        em.persist(comment);
        em.getTransaction().commit();
    }

    /**
     * Updates an existing comment in the database.
     *
     */
    public void update() {
        EntityManager em = AppData.getManager();
        em.getTransaction().begin();
        em.merge(this);
        em.getTransaction().commit();
    }

    /**
     * Deletes a comment from the database.
     *
     */
    public void remove() {
        EntityManager em = AppData.getManager();
        em.getTransaction().begin();
        em.remove(em.contains(this) ? this : em.merge(this));
        em.getTransaction().commit();
    }

    /**
     * Retrieves a comment from the database based on its ID.
     *
     * @param id The ID of the comment to retrieve.
     * @return The Comment object with the specified ID, or null if not found.
     */
    public Comment getById(int id) {
        EntityManager em = AppData.getManager();
        Comment comment = em.find(Comment.class, id);
        return comment;
    }

    /**
     * Retrieves all comments from the database.
     *
     * @return A List of Comment objects representing all comments in the database.
     */
    public List<Comment> getAll() {
        EntityManager em = AppData.getManager();
        Query query = em.createQuery("FROM Comment", Comment.class);
        List<Comment> comments = query.getResultList();
        return comments;
    }

    public List<Comment> getByPlaylist(Playlist playlist) {
        EntityManager entityManager = AppData.getManager();
        List<Comment> comments = null;
        try {
            TypedQuery<Comment> query = entityManager.createQuery("SELECT c FROM Comment c JOIN c.playlist p WHERE p = :playlist", Comment.class);
            query.setParameter("playlist", playlist);
            comments = query.getResultList(); // Obtener comentarios asociados a una playlist específica
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comments;
    }

    public List<Comment> getByUser(User user) {
        EntityManager entityManager = AppData.getManager();
        List<Comment> comments = null;
        try {
            TypedQuery<Comment> query = entityManager.createQuery("SELECT c FROM Comment c JOIN c.user u WHERE u = :user", Comment.class);
            query.setParameter("user", user);
            comments = query.getResultList(); // Obtener comentarios asociados a un user específico
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comments;
    }

    @Override
    public void close() throws Exception {

    }
}
