package com.juanite.model.domain;

import com.juanite.util.AppData;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "COMMENT")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "COMMENT_TEXT")
    private String comment;

    @Column(name = "DATE_TIME")
    private LocalDateTime date_time;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "PLAYLIST_ID")
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

    public User getUser() {
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

    /**
     * Retrieves all comments from the database.
     *
     * @return A List of Comment objects representing all comments in the database.
     */
    public static List<Comment> selectAllComments() {
        EntityManager em = AppData.getManager();
        Query query = em.createQuery("FROM Comment", Comment.class);
        List<Comment> comments = query.getResultList();
        return comments;
    }


    /**
     * Retrieves a comment from the database based on its ID.
     *
     * @param id The ID of the comment to retrieve.
     * @return The Comment object with the specified ID, or null if not found.
     */
    public static Comment selectCommentById(int id) {
        EntityManager em = AppData.getManager();
        Comment comment = em.find(Comment.class, id);
        return comment;
    }

    /**
     * Saves a new comment to the database.
     *
     * @param comment The Comment object to be saved.
     */
    public static void saveComment(Comment comment) {
        EntityManager em = AppData.getManager();
        em.getTransaction().begin();
        em.persist(comment);
        em.getTransaction().commit();
    }

    /**
     * Updates an existing comment in the database.
     *
     * @param comment The Comment object to be updated.
     */
    public static void updateComment(Comment comment) {
        EntityManager em = AppData.getManager();
        em.getTransaction().begin();
        em.merge(comment);
        em.getTransaction().commit();
    }

    /**
     * Deletes a comment from the database.
     *
     * @param comment The Comment object to be deleted.
     */
    public static void deleteComment(Comment comment) {
        EntityManager em = AppData.getManager();
        em.getTransaction().begin();
        comment = em.merge(comment);
        em.remove(comment);
        em.getTransaction().commit();
    }
}
