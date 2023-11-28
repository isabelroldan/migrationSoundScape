package com.juanite.model.domain;

import com.juanite.model.Genres;
import com.juanite.util.AppData;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "song")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "duration")
    private int duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    private Genres genre;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "id_album")
    private Album album;


    @ManyToMany(mappedBy = "songs")
    private List<Playlist> playlists;


    public void saveSong() {
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

    public void updateSong() {
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

    public void removeSong() {
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

    public static Song getSongById(int id) {
        EntityManager entityManager = AppData.getManager();
        Song song = null;
        try {
            song = entityManager.find(Song.class, id); // Obtener la canción por su ID
        } catch (Exception e) {
            e.printStackTrace();
        }
        return song;
    }

    public static List<Song> getAllSongs() {
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

    public static List<Song> getSongByGenre(Genres genre) {
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

    public static List<Song> getSongByAlbum(Album album) {
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

    public static List<Song> getSongByPlaylist(Playlist playlist) {
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

    public static List<Song> getSongByName(String name) {
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

    public static List<Song> getSongsSearchResults(String searchTerm) {
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


    public Song() {
        this.id = -1;
        this.name = "";
        this.duration = -1;
        this.genre = Genres.NONE;
        this.url = "";
        this.album = null;
        this.playlists = new ArrayList<>();
    }

    public Song(int id, String name, int duration, Genres genre, String url, Album album) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.genre = genre;
        this.url = url;
        this.album = album;
        this.playlists = new ArrayList<>();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Genres getGenre() {
        return genre;
    }

    public void setGenre(Genres genre) {
        this.genre = genre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return id == song.id && Objects.equals(name, song.name) && Objects.equals(album, song.album);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, album);
    }

    @Override
    public String toString() {
        return name;
    }
}
