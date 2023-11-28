package com.juanite.model.domain;

import com.juanite.util.AppData;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "USER")
public class User extends Person {
    @Column(name = "PHOTO")
    private String photo;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Playlist> playlists;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Playlist> favoritePlaylists;

    public User() {
        super();
        this.photo = "/com/juanite/images/default_avatar.png";
        this.playlists = new ArrayList<Playlist>();
        this.favoritePlaylists = new ArrayList<Playlist>();
    }
    public User(int id) {
        super();
        this.id = id;
        this.photo = "/com/juanite/images/default_avatar.png";
        this.playlists = new ArrayList<Playlist>();
        this.favoritePlaylists = new ArrayList<Playlist>();
    }
    public User(int id, String name, String email, String password, String photo) {
        super(id,name,email,password);
        this.photo = "/com/juanite/images/default_avatar.png";
        this.playlists = new ArrayList<Playlist>();
        this.favoritePlaylists = new ArrayList<Playlist>();
    }

    public User(int id, String name, String email, String password, String photo, List<Playlist> playlists, List<Playlist> favoritePlaylists) {
        super(id,name,email,password);
        this.photo = "/com/juanite/images/default_avatar.png";
        this.playlists = playlists;
        this.favoritePlaylists = favoritePlaylists;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public List<Playlist> getFavoritePlaylists() {
        return favoritePlaylists;
    }

    public void setFavoritePlaylists(List<Playlist> favoritePlaylists) {
        this.favoritePlaylists = favoritePlaylists;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return this.id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Retrieves all users from the database.
     *
     * @return A List of User objects representing all users in the database.
     */
    public static List<User> selectAllUsers() {
        EntityManager em = AppData.getManager();
        Query query = em.createQuery("FROM User", User.class);
        List<User> users = query.getResultList();
        return users;
    }

    /**
     * Retrieves a user from the database based on their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The User object with the specified ID, or null if not found.
     */
    public static User selectUserById(int id) {
        EntityManager em = AppData.getManager();
        User user = em.find(User.class, id);
        return user;
    }

    /**
     * Saves a new user to the database.
     *
     * @param user The User object to be saved.
     */
    public static void saveUser(User user) {
        EntityManager em = AppData.getManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    /**
     * Updates an existing user in the database.
     *
     * @param user The User object to be updated.
     */
    public static void updateUser(User user) {
        EntityManager em = AppData.getManager();
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
    }

    /**
     * Deletes a user from the database.
     *
     * @param user The User object to be deleted.
     */
    public static void deleteUser(User user) {
        EntityManager em = AppData.getManager();
        em.getTransaction().begin();
        user = em.merge(user);
        em.remove(user);
        em.getTransaction().commit();
    }
}
