package com.juanite.model.DAO;

import com.juanite.model.domain.Comment;
import com.juanite.model.domain.Playlist;
import com.juanite.model.domain.Song;
import com.juanite.model.domain.User;
import com.juanite.util.AppData;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class PlaylistDAO extends Playlist implements AutoCloseable {

    public PlaylistDAO(int id, String name, String description, User owner, List<Song> songs, List<User> subscribers, List<Comment> comments){
        super(id, name, description, owner, songs, subscribers, comments);
    }

    public PlaylistDAO(int id){ getById(id);}

    public PlaylistDAO(Playlist p){
        super(p.getId(), p.getName(), p.getDescription(), p.getOwner(), p.getSongs(), p.getSubscribers(), p.getComments());
    }

    /**
     * Saves the current object to the database.
     * If the object already has an ID, it updates the existing record; otherwise, it inserts a new record.
     *
     */
    public void save(){
        EntityManager entityManager = AppData.getManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(this);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }else {
            }
            e.printStackTrace();
        }
    }
    /**
     * Updates the current object's data in the database.
     * This method is used to modify the existing record in the database with the current object's data.
     *
     */
    public void update(){
        EntityManager entityManager = AppData.getManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(this);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Removes the current object's data from the database.
     * This method is used to delete the database record associated with the current object.
     *
     */
    public void remove(){

        EntityManager entityManager = AppData.getManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(this) ? this : entityManager.merge(this));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all artists from the database.
     *
     * This method fetches all artists from the database and returns them as a set of Artist objects.
     *
     * @return A set of Artist objects if the retrieval is successful, or null if there was an error.
     */
    public List<Playlist> getAll() {
        EntityManager entityManager = AppData.getManager();
        List<Playlist> p = null;
        try {
            TypedQuery<Playlist> query = entityManager.createQuery("SELECT s FROM Playlist s", Playlist.class);
            p = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    public Playlist getById(int id) {
        EntityManager entityManager = AppData.getManager();
        Playlist p = null;
        try {
            p = entityManager.find(Playlist.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    public List<Playlist> getByName(String name) {
        EntityManager entityManager = AppData.getManager();
        List<Playlist> p = null;
        try {
            TypedQuery<Playlist> query = entityManager.createQuery("SELECT s FROM Playlist s WHERE name = :name", Playlist.class);
            query.setParameter("name", name);
            p = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    public List<Playlist> getByUser(User user, boolean isOwner) {
        EntityManager entityManager = AppData.getManager();
        List<Playlist> p = null;
        if(isOwner) {
            try {
                TypedQuery<Playlist> query = entityManager.createQuery("SELECT s FROM Playlist s WHERE s.owner = :user", Playlist.class);
                query.setParameter("user", user);
                p = query.getResultList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                TypedQuery<Playlist> query = entityManager.createQuery("SELECT s FROM Playlist s WHERE :user MEMBER OF s.subscribers", Playlist.class);
                query.setParameter("user", user);
                p = query.getResultList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return p;
    }

    public List<Playlist> getSearchResults(String searchTerm) {
        EntityManager entityManager = AppData.getManager();
        List<Playlist> p = null;
        try {
            TypedQuery<Playlist> query = entityManager.createQuery("SELECT s FROM Playlist s WHERE s.name LIKE :searchTerm", Playlist.class);
            query.setParameter("searchTerm", "%" + searchTerm + "%");
            p = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    @Override
    public void close() throws Exception {

    }
}
