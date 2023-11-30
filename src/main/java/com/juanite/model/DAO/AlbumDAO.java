package com.juanite.model.DAO;

import com.juanite.connection.ConnectionMySQL;
import com.juanite.model.domain.Album;
import com.juanite.model.domain.Artist;
import com.juanite.model.domain.Song;
import com.juanite.util.AppData;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AlbumDAO extends Album implements AutoCloseable{

    public AlbumDAO(int id, String name, Date publication, String photo){
        super(id, name, publication, photo);
    }

    public AlbumDAO(int id){ getById(id);}

    public AlbumDAO(Album a){
        super(a.getId(), a.getName(), a.getPublication(), a.getPhoto());
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
            entityManager.persist(this); // Guardar el álbum actual en la base de datos
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Updates the current object's data in the database.
     * This method is used to modify the existing record in the database with the current object's data.
     *
     * @return true if the update operation is successful, false otherwise.
     */
    public void update() {
        EntityManager entityManager = AppData.getManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(this); // Actualizar el álbum actual en la base de datos
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
     * @return true if the removal operation is successful, false otherwise.
     */
    public void remove() {
        EntityManager entityManager = AppData.getManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(this) ? this : entityManager.merge(this)); // Borrar el álbum actual de la base de datos
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    /**
     * Retrieves all albums from the database.
     *
     * This method fetches all albums from the database and returns them as a set of Album objects.
     *
     * @return A set of Album objects if the retrieval is successful, or null if there was an error.
     */
    public List<Album> getAll() {
        EntityManager entityManager = AppData.getManager();
        List<Album> albums = null;
        try {
            TypedQuery<Album> query = entityManager.createQuery("SELECT a FROM Album a", Album.class);
            albums = query.getResultList(); // Obtener todos los álbumes de la base de datos
        } catch (Exception e) {
            e.printStackTrace();
        }
        return albums;
    }

    /**
     * Retrieves an object's data from the database using the specified ID.
     * This method fetches the data of the current object from the database based on the provided ID.
     *
     * @param id The unique identifier of the object to retrieve.
     * @return true if the data retrieval is successful, false otherwise.
     */
    public Album getById(int id) {
        EntityManager entityManager = AppData.getManager();
        Album album = null;
        try {
            album = entityManager.find(Album.class, id); // Obtener el álbum por su ID
        } catch (Exception e) {
            e.printStackTrace();
        }
        return album;
    }

    public List<Album> getBySong(Song song) {
        EntityManager entityManager = AppData.getManager();
        List<Album> albums = null;
        try {
            TypedQuery<Album> query = entityManager.createQuery("SELECT a FROM Album a JOIN a.songs s WHERE s = :song", Album.class);
            query.setParameter("song", song);
            albums = query.getResultList(); // Obtener álbumes asociados a una canción específica
        } catch (Exception e) {
            e.printStackTrace();
        }
        return albums;
    }

    public List<Album> getByArtist(Artist artist) {
        EntityManager entityManager = AppData.getManager();
        List<Album> albums = null;
        try {
            TypedQuery<Album> query = entityManager.createQuery("SELECT a FROM Album a JOIN a.artists ar WHERE ar = :artist", Album.class);
            query.setParameter("artist", artist);
            albums = query.getResultList(); // Obtener álbumes asociados a un artista específico
        } catch (Exception e) {
            e.printStackTrace();
        }
        return albums;
    }

    @Override
    public void close() throws Exception {

    }
}
