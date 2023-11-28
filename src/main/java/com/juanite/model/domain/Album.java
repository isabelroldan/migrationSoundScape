package com.juanite.model.domain;

import com.juanite.util.AppData;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Album")
public class Album {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "publication")
    private Date publication;

    @Column(name = "photo")
    private String photo;

    @ManyToMany
    @JoinTable(
            name = "artist_album",
            joinColumns = @JoinColumn(name = "id_album"),
            inverseJoinColumns = @JoinColumn(name = "id_artist")
    )
    private List<Artist> artists;

    @OneToMany(mappedBy = "album")
    private List<Song> songs;



    public void saveAlbum() {
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

    public void updateAlbum() {
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

    public void removeAlbum() {
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

    public static Album getAlbumById(int id) {
        EntityManager entityManager = AppData.getManager();
        Album album = null;
        try {
            album = entityManager.find(Album.class, id); // Obtener el álbum por su ID
        } catch (Exception e) {
            e.printStackTrace();
        }
        return album;
    }

    public static List<Album> getAllAlbums() {
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

    public static List<Album> getAlbumBySong(Song song) {
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

    public static List<Album> getAlbumByArtist(Artist artist) {
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


    // Constructor
    public Album() {
        this.id = -1;
        this.name = "";
        this.publication = Date.valueOf(LocalDate.now());
        this.photo = "";
        this.artists = new ArrayList<>();
        this.songs = new ArrayList<>();
    }
    public Album(int id, String name, Date publication, String photo) {
        this.id = id;
        this.name = name;
        this.publication = publication;
        this.photo = photo;
        this.artists = new ArrayList<>();
        this.songs = new ArrayList<>();
    }

    public Album(int id, String name, Date publication, String photo, List<Artist> artists, List<Song> songs) {
        this.id = id;
        this.name = name;
        this.publication = publication;
        this.photo = photo;
        this.artists = artists;
        this.songs = songs;
    }

    // Getters and Setters
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

    public Date getPublication() {
        return publication;
    }

    public void setPublication(Date publication) {
        this.publication = publication;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Album)) return false;
        Album album = (Album) o;
        return id == album.id && Objects.equals(name, album.name) && Objects.equals(publication, album.publication) && Objects.equals(photo, album.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, publication, photo);
    }

    @Override
    public String toString() {
        return name;
    }
}
