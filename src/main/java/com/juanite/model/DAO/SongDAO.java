package com.juanite.model.DAO;

import com.juanite.connection.ConnectionMySQL;
import com.juanite.model.Genres;
import com.juanite.model.domain.Album;
import com.juanite.model.domain.Song;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class SongDAO extends Song {

    private final static String INSERT = "INSERT INTO Song (name, duration, genre, url, album_id) VALUES(?, ?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE Song SET name=?, duration=?, genre=?, url=?, album_id=? WHERE id=?";
    private final static String DELETE = "DELETE FROM Song WHERE id=?";
    private final static String SELECTBYID = "SELECT id, name, duration, genre, url, album_id FROM Song WHERE id=?";
    private final static String SELECTBYNAME = "SELECT id, name, duration, genre, url, album_id FROM Song WHERE name=?";
    private final static String SELECTBYALBUM = "SELECT id, name, duration, genre, url, album_id FROM Song WHERE album_id=?";
    private final static String SELECTBYGENRE = "SELECT id, name, duration, genre, url, album_id FROM Song WHERE genre=?";


    public SongDAO(int id, String name, int duration, Genres genre, String url, Album album) {
        super(id, name, duration, genre, url, album);
    }

    public SongDAO(int id) {
        getById(id);
    }

    public SongDAO(Song song) {
        super(song.getId(), song.getName(), song.getDuration(), song.getGenre(), song.getUrl(), song.getAlbum());
    }


    public boolean save(Song song) {
        if (song.getId() != -1) {
            return update(song);
        } else {
            Connection conn = ConnectionMySQL.getConnect();
            if (conn == null) return false;

            try (PreparedStatement ps = conn.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, song.getName());
                ps.setInt(2, song.getDuration());
                ps.setString(3, song.getGenre().toString());
                ps.setString(4, song.getUrl());
                ps.setInt(5, song.getAlbum().getId());

                if (ps.executeUpdate() == 1) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            song.setId(rs.getInt(1));
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
                return false;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean update(Song song) {
        if (song.getId() == -1) return false;
        Connection conn = ConnectionMySQL.getConnect();
        if (conn == null) return false;

        try (PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setString(1, song.getName());
            ps.setInt(2, song.getDuration());
            ps.setString(3, song.getGenre().toString());
            ps.setString(4, song.getUrl());
            ps.setInt(5, song.getAlbum().getId());
            ps.setInt(6, song.getId());

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean remove(int id) {
        if (id == -1) return false;
        Connection conn = ConnectionMySQL.getConnect();
        if (conn == null) return false;

        try (PreparedStatement ps = conn.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean getById(int id) {
        Connection conn = ConnectionMySQL.getConnect();
        if (conn == null) return false;

        try (PreparedStatement ps = conn.prepareStatement(SELECTBYID)) {
            ps.setInt(1, id);
            if (ps.execute()) {
                try (ResultSet rs = ps.getResultSet()) {
                    if (rs.next()) {
                        setId(rs.getInt("id"));
                        setName(rs.getString("name"));
                        setDuration(rs.getInt("duration"));
                        setGenre(Genres.valueOf(rs.getString("genre")));
                        setUrl(rs.getString("url"));
                        album = new AlbumDAO(rs.getString("album_id"));
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Set<Song> getByName(String name) {
        Connection conn = ConnectionMySQL.getConnect();
        if (conn == null) return null;

        Set<Song> result = new HashSet<>();
        try (PreparedStatement ps = conn.prepareStatement(SELECTBYNAME)) {
            ps.setString(1, name);
            if (ps.execute()) {
                try (ResultSet rs = ps.getResultSet()) {
                    while (rs.next()) {
                        Song song = new Song();
                        song.setId(rs.getInt("id"));
                        song.setName(rs.getString("name"));
                        song.setDuration(rs.getInt("duration"));
                        song.setGenre(Genres.valueOf(rs.getString("genre")));
                        song.setUrl(rs.getString("url"));
                        album = new AlbumDAO(rs.getString("album_id"));
                        result.add(song);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Set<Song> getByAlbum(Album album) {
        Connection conn = ConnectionMySQL.getConnect();
        if (conn == null) return null;

        Set<Song> result = new HashSet<>();
        try (PreparedStatement ps = conn.prepareStatement(SELECTBYALBUM)) {
            ps.setInt(1, album.getId());
            if (ps.execute()) {
                try (ResultSet rs = ps.getResultSet()) {
                    while (rs.next()) {
                        Song song = new Song();
                        song.setId(rs.getInt("id"));
                        song.setName(rs.getString("name"));
                        song.setDuration(rs.getInt("duration"));
                        song.setGenre(Genres.valueOf(rs.getString("genre")));
                        song.setUrl(rs.getString("url"));
                        song.setAlbum(album);
                        result.add(song);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Set<Song> getByGenre(Genres genre) {
        Connection conn = ConnectionMySQL.getConnect();
        if (conn == null) return null;

        Set<Song> result = new HashSet<>();
        try (PreparedStatement ps = conn.prepareStatement(SELECTBYGENRE)) {
            ps.setString(1, genre.toString());
            if (ps.execute()) {
                try (ResultSet rs = ps.getResultSet()) {
                    while (rs.next()) {
                        Song song = new Song();
                        song.setId(rs.getInt("id"));
                        song.setName(rs.getString("name"));
                        song.setDuration(rs.getInt("duration"));
                        song.setGenre(genre);
                        song.setUrl(rs.getString("url"));
                        album = new AlbumDAO(rs.getString("album_id"));
                        result.add(song);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
