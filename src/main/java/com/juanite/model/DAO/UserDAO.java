package com.juanite.model.DAO;

import com.juanite.model.domain.Playlist;
import com.juanite.model.domain.User;
import com.juanite.util.AppData;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;


public class UserDAO extends User implements AutoCloseable{

    public UserDAO(int id, String name, String email, String password, String photo, List<Playlist> playlist, List<Playlist> favoritePlaylist) {
        super(id, name, email, password,photo, playlist, favoritePlaylist);
    }

    public UserDAO(int id) {
        getById(id);
    }

    public UserDAO(User user) {
        super(user.getId(), user.getName(), user.getEmail(), user.getPassword(),user.getPhoto(), user.getPlaylists(), user.getFavoritePlaylists());
    }

    public UserDAO() {
    }

    /**
     * Saves a new user to the database.
     *
     */
    public void save(User user) {
        EntityManager em = AppData.getManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    /**
     * Updates an existing user in the database.
     *
     */
    public void update(User user) {
        EntityManager em = AppData.getManager();
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
    }

    public boolean updatePhoto() {
        if (getId() == -1 || getPlaylists() == null || getFavoritePlaylists() == null) {
            return false;
        }

        EntityManager entityManager = AppData.getManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            String UPDATE_QUERY = "UPDATE User e SET e.photo = :photo WHERE e.id = :id";
            entityManager.createQuery(UPDATE_QUERY)
                    .setParameter("photo", getPhoto())
                    .setParameter("id", getId())
                    .executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a user from the database.
     *
     */
    public void remove() {
        EntityManager em = AppData.getManager();
        em.getTransaction().begin();
        em.remove(em.contains(this) ? this : em.merge(this));
        em.getTransaction().commit();
    }

    /**
     * Retrieves all users from the database.
     *
     * @return A List of User objects representing all users in the database.
     */
    public static List<User> getAll() {
        EntityManager em = AppData.getManager();
        Query query = em.createQuery("FROM User", User.class);
        List<User> users = query.getResultList();
        return users;
    }



    // Método para obtener un usuario por su ID desde la base de datos.
    /**
     * Retrieves a user from the database based on their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The User object with the specified ID, or null if not found.
     */
    public static User getById(int id) {
        EntityManager em = AppData.getManager();
        User user = em.find(User.class, id);
        return user;
    }

    // Método para obtener un usuario por su nombre desde la base de datos.
    public static List<User> getByNameAndPassword(String name, String password) {
        EntityManager entityManager = AppData.getManager();
        List<User> users = null;
        try {
            TypedQuery<User> query = entityManager.createQuery(
                    "SELECT u FROM User u WHERE u.name = :name AND u.password = :password",
                    User.class
            );
            query.setParameter("name", name);
            query.setParameter("password", password);
            users = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }


    public boolean userExists(String username) {
        EntityManager entityManager = AppData.getManager();
        try {
            TypedQuery<Long> query = entityManager.createQuery(
                    "SELECT COUNT(u) FROM User u WHERE u.name = :username", Long.class);
            query.setParameter("username", username);
            Long count = query.getSingleResult();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean emailExists(String email) {
        EntityManager entityManager = AppData.getManager();
        try {
            TypedQuery<Long> query = entityManager.createQuery(
                    "SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class);
            query.setParameter("email", email);
            Long count = query.getSingleResult();
            return count <= 0;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    @Override
    public void close() throws Exception {

    }
}
