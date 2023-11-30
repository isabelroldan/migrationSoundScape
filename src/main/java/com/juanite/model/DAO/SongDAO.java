package com.juanite.model.DAO;

import com.juanite.connection.ConnectionMySQL;
import com.juanite.model.Genres;
import com.juanite.model.domain.Album;
import com.juanite.model.domain.Playlist;
import com.juanite.model.domain.Song;
import com.juanite.util.AppData;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SongDAO extends Song implements AutoCloseable{

    public SongDAO(int id, String name, int duration, Genres genre, String url, Album album) {
        super(id, name, duration, genre, url, album);
    }

    public SongDAO(int id) {
        getById(id);
    }

    public SongDAO(Song song) {
        super(song.getId(), song.getName(), song.getDuration(), song.getGenre(), song.getUrl(), song.getAlbum());
    }


    public void save() {
        EntityManager entityManager = AppData.getManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(this); // Guardar la canción actual en la base de datos
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
            entityManager.merge(this); // Actualizar la canción actual en la base de datos
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
            entityManager.remove(entityManager.contains(this) ? this : entityManager.merge(this)); // Borrar la canción actual de la base de datos
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static List<Song> getAll() {
        EntityManager entityManager = AppData.getManager();
        List<Song> songs = null;
        try {
            TypedQuery<Song> query = entityManager.createQuery("SELECT s FROM Song s", Song.class);
            songs = query.getResultList(); // Obtener todos los álbumes de la base de datos
        } catch (Exception e) {
            e.printStackTrace();
        }
        return songs;
    }

    public static Song getById(int id) {
        EntityManager entityManager = AppData.getManager();
        Song song = null;
        try {
            song = entityManager.find(Song.class, id); // Obtener la canción por su ID
        } catch (Exception e) {
            e.printStackTrace();
        }
        return song;
    }

    public static List<Song> getByName(String name) {
        EntityManager entityManager = AppData.getManager();
        List<Song> songs = null;
        try {
            TypedQuery<Song> query = entityManager.createQuery("SELECT s FROM Song s WHERE s.name = :name", Song.class);
            query.setParameter("name", name);
            songs = query.getResultList(); // Obtener canciones por nombre
        } catch (Exception e) {
            e.printStackTrace();
        }
        return songs;
    }

    public static List<Song> getByAlbum(Album album) {
        EntityManager entityManager = AppData.getManager();
        List<Song> songs = null;
        try {
            TypedQuery<Song> query = entityManager.createQuery("SELECT s FROM Song s WHERE s.album = :album", Song.class);
            query.setParameter("album", album);
            songs = query.getResultList(); // Obtener canciones por álbum
        } catch (Exception e) {
            e.printStackTrace();
        }
        return songs;
    }

    public static List<Song> getByGenre(Genres genre) {
        EntityManager entityManager = AppData.getManager();
        List<Song> songs = null;
        try {
            TypedQuery<Song> query = entityManager.createQuery("SELECT s FROM Song s WHERE s.genre = :genre", Song.class);
            query.setParameter("genre", genre);
            songs = query.getResultList(); // Obtener canciones por género
        } catch (Exception e) {
            e.printStackTrace();
        }
        return songs;
    }

    public static List<Song> getByPlaylist(Playlist playlist) {
        EntityManager entityManager = AppData.getManager();
        List<Song> songs = null;
        try {
            TypedQuery<Song> query = entityManager.createQuery("SELECT s FROM Song s JOIN s.playlists p WHERE p = :playlist", Song.class);
            query.setParameter("playlist", playlist);
            songs = query.getResultList(); // Obtener álbumes asociados a una canción específica
        } catch (Exception e) {
            e.printStackTrace();
        }
        return songs;
    }

    public static List<Song> getSearchResults(String searchTerm) {
        EntityManager entityManager = AppData.getManager();
        List<Song> songs = null;
        try {
            TypedQuery<Song> query = entityManager.createQuery("SELECT s FROM Song s WHERE s.name LIKE :searchTerm", Song.class);
            query.setParameter("searchTerm", "%" + searchTerm + "%");
            songs = query.getResultList(); // Obtener canciones que coincidan parcialmente con el nombre
        } catch (Exception e) {
            e.printStackTrace();
        }
        return songs;
    }

    @Override
    public void close() throws Exception {

    }
}
