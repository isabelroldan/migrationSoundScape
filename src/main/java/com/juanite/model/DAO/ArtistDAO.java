package com.juanite.model.DAO;

import com.juanite.model.Countries;
import com.juanite.model.domain.Album;
import com.juanite.model.domain.Artist;
import com.juanite.util.AppData;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class ArtistDAO extends Artist implements AutoCloseable {

    public ArtistDAO(int id, String name, Countries nationality, String photo){
        super(id, name, nationality, photo);
    }

    public ArtistDAO(int id){ getById(id);}

    public ArtistDAO(Artist a){
        super(a.getId(), a.getName(), a.getNationality(), a.getPhoto());
    }

    /**
     * Saves the current object to the database.
     * If the object already has an ID, it updates the existing record; otherwise, it inserts a new record.
     *
     * @return true if the save operation is successful, false otherwise.
     */
    public void save() {
        EntityManager entityManager = AppData.getManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(this);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void update() {
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

    public void remove() {
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
     * Retrieves an object's data from the database using the specified ID.
     * This method fetches the data of the current object from the database based on the provided ID.
     *
     * @param id The unique identifier of the object to retrieve.
     */
    public Artist getById(int id){
        EntityManager entityManager = AppData.getManager();
        Artist a = null;
        try {
            a = entityManager.find(Artist.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }

    /**
     * Retrieves all artists from the database.
     *
     * This method fetches all artists from the database and returns them as a set of Artist objects.
     *
     * @return A set of Artist objects if the retrieval is successful, or null if there was an error.
     */
    public List<Artist> getAll() {
        EntityManager entityManager = AppData.getManager();
        List<Artist> a = null;
        try {
            TypedQuery<Artist> query = entityManager.createQuery("SELECT s FROM Artist s", Artist.class);
            a = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }

    public List<Artist> getByName(String name) {
        EntityManager entityManager = AppData.getManager();
        List<Artist> a = null;
        try {
            TypedQuery<Artist> query = entityManager.createQuery("SELECT s FROM Artist s WHERE name = :name", Artist.class);
            query.setParameter("name", name);
            a = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }
    public List<Artist> getByAlbum(Album album) {
        EntityManager entityManager = AppData.getManager();
        List<Artist> artists = null;
        try {
            TypedQuery<Artist> query = entityManager.createQuery("SELECT a FROM Artist a JOIN a.albumList s WHERE s = :album", Artist.class);
            query.setParameter("album", album);
            artists = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return artists;
    }

    @Override
    public void close() throws Exception {

    }
}
