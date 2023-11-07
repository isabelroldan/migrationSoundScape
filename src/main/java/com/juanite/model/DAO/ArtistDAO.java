package com.juanite.model.DAO;

import com.juanite.connection.ConnectionMySQL;
import com.juanite.model.Countries;
import com.juanite.model.domain.Album;
import com.juanite.model.domain.Artist;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArtistDAO extends Artist implements iArtistDAO {
    private final static String INSERT = "INSERT INTO artist (name, nationality, photo) VALUES (?,?,?)";
    private final static String INSERTALBUM = "INSERT INTO artist_album (id_artist, id_album) VALUES (?,?,?)";
    private final static String UPDATE ="UPDATE artist SET name=?, nationality=?, photo=? WHERE id=?";
    private final static String DELETE="DELETE FROM artist WHERE id=?";
    private final static String DELETEALBUMS = "DELETE FROM artist_album WHERE id_artist=?";
    private final static String SELECTBYID="SELECT id, name, nationality, photo FROM artist WHERE id=?";
    private final static String SELECTALL="SELECT id, name, nationality, photo FROM artist";
    private final static String SELECTBYNAME="SELECT id, name, nationality, photo FROM artist WHERE name=?";
    private final static String SELECTBYNATIONALITY="SELECT id, name, nationality, photo FROM artist WHERE nationality=?";
    private final static String SELECTBYALBUM = "SELECT id_artist FROM artist_album WHERE id_album=?";

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
    public boolean save(){
        if(getId()!=-1){
            return update();
        }else{
            if(getAlbumList()==null ) return false;
            Connection conn = ConnectionMySQL.getConnect();
            if(conn==null) return false;

            try(PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)){
                ps.setString(1, getName());
                ps.setString(2, getNationality().toString());
                ps.setString(3, getPhoto());

                if(ps.executeUpdate()==1) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            setId(rs.getInt(1));
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
                setId(-1);
                return false;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    /**
     * Saves the current object to the database.
     * If the object already has an ID, it updates the existing record; otherwise, it inserts a new record.
     *
     * @return true if the save operation is successful, false otherwise.
     */
    public boolean saveAlbum(Album album){
        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null)
            return false;
        try(PreparedStatement ps = conn.prepareStatement(INSERTALBUM, Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1, getId());
            ps.setInt(2, album.getId());

                if(ps.executeUpdate()==1) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            setId(rs.getInt(1));
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
                setId(-1);
                return false;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
    }

    /**
     * Updates the current object's data in the database.
     * This method is used to modify the existing record in the database with the current object's data.
     *
     * @return true if the update operation is successful, false otherwise.
     */
    public boolean update(){
        if(getId()==-1) return false;
        if(getAlbumList()==null ) return false;

        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null) return false;

        try(PreparedStatement ps = conn.prepareStatement(UPDATE)){

            ps.setString(1, getName());
            ps.setString(2, getNationality().toString());
            ps.setString(3, getPhoto());
            ps.setInt(4, getId());
            if(ps.executeUpdate()==1) {
                return true;
            }
            setId(-1);
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Removes the current object's data from the database.
     * This method is used to delete the database record associated with the current object.
     *
     * @return true if the removal operation is successful, false otherwise.
     */
    public boolean remove(){

        if(getId()==-1) return false;

        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null) return false;

        try(PreparedStatement ps = conn.prepareStatement(DELETE)){
            ps.setInt(1,getId());
            if(ps.executeUpdate()==1)
                return true;

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Removes the current object's data from the database.
     * This method is used to delete the database record associated with the current object.
     *
     * @return true if the removal operation is successful, false otherwise.
     */
    public boolean removeAlbums(){

        if(getId()==-1) return false;

        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null) return false;

        try(PreparedStatement ps = conn.prepareStatement(DELETEALBUMS)){
            ps.setInt(1,getId());
            if(ps.executeUpdate()==1)
                return true;

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves an object's data from the database using the specified ID.
     * This method fetches the data of the current object from the database based on the provided ID.
     *
     * @param id The unique identifier of the object to retrieve.
     * @return true if the data retrieval is successful, false otherwise.
     */
    @Override
    public boolean getById(int id){
        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null) return false;
        try(PreparedStatement ps = conn.prepareStatement(SELECTBYID)){
            ps.setInt(1,id);
            if(ps.execute()){
                try(ResultSet rs = ps.getResultSet()){
                    if(rs.next()){
                        setId(rs.getInt("id"));
                        setName(rs.getString("name"));
                        setNationality(Countries.valueOf(rs.getString("nationality")));
                        setPhoto(rs.getString("photo"));
                        List<Album> albums = new ArrayList<>();
                        try(AlbumDAO adao = new AlbumDAO(new Album())) {
                            Set<Album> albumSet = adao.getByArtist(this);
                            albums.addAll(albumSet);
                        } catch (Exception e) {
                            return false;
                        }
                        setAlbumList(albums);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Retrieves all artists from the database.
     *
     * This method fetches all artists from the database and returns them as a set of Artist objects.
     *
     * @return A set of Artist objects if the retrieval is successful, or null if there was an error.
     */
    @Override
    public Set<Artist> getAll() {
        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null) return null;
        Set<Artist> result=new HashSet<>();
        try(PreparedStatement ps = conn.prepareStatement(SELECTALL)){
            if(ps.execute()){
                try(ResultSet rs = ps.getResultSet()){
                    while(rs.next()){
                        Artist a = new Artist();
                        a.setId(rs.getInt("id"));
                        a.setName(rs.getString("name"));
                        a.setNationality(Countries.valueOf(rs.getString("nationality")));
                        a.setPhoto(rs.getString("photo"));
                        List<Album> albums = new ArrayList<>();
                        try(AlbumDAO adao = new AlbumDAO(new Album())) {
                            Set<Album> albumSet = adao.getByArtist(this);
                            albums.addAll(albumSet);
                        } catch (Exception e) {
                            return null;
                        }
                        a.setAlbumList(albums);
                        result.add(a);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    @Override
    public Set<Artist> getByName(String name) {
        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null) return null;
        Set<Artist> result=new HashSet<>();
        try(PreparedStatement ps = conn.prepareStatement(SELECTBYNAME)){
            ps.setString(1, name);
            if(ps.execute()){
                try(ResultSet rs = ps.getResultSet()){
                    while(rs.next()){
                        Artist a = new Artist();
                        a.setId(rs.getInt("id"));
                        a.setName(rs.getString("name"));
                        a.setNationality(Countries.valueOf(rs.getString("nationality")));
                        a.setPhoto(rs.getString("photo"));
                        List<Album> albums = new ArrayList<>();
                        try(AlbumDAO adao = new AlbumDAO(new Album())) {
                            Set<Album> albumSet = adao.getByArtist(this);
                            albums.addAll(albumSet);
                        } catch (Exception e) {
                            return null;
                        }
                        a.setAlbumList(albums);
                        result.add(a);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    @Override
    public Set<Artist> getByCountry(Countries country) {
        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null) return null;
        Set<Artist> result=new HashSet<>();
        try(PreparedStatement ps = conn.prepareStatement(SELECTBYNATIONALITY)){
            ps.setString(1, country.toString());
            if(ps.execute()){
                try(ResultSet rs = ps.getResultSet()){
                    while(rs.next()){
                        Artist a = new Artist();
                        a.setId(rs.getInt("id"));
                        a.setName(rs.getString("name"));
                        a.setNationality(Countries.valueOf(rs.getString("nationality")));
                        a.setPhoto(rs.getString("photo"));
                        List<Album> albums = new ArrayList<>();
                        try(AlbumDAO adao = new AlbumDAO(new Album())) {
                            Set<Album> albumSet = adao.getByArtist(this);
                            albums.addAll(albumSet);
                        } catch (Exception e) {
                            return null;
                        }
                        a.setAlbumList(albums);
                        result.add(a);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }
    public Set<Artist> getByAlbum(Album album) throws SQLException {
        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null) return null;
        Set<Artist> result=new HashSet<>();
        List<Integer> artistIds = new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement(SELECTBYALBUM)){
            ps.setInt(1, album.getId());
            if(ps.execute()){
                try(ResultSet rs = ps.getResultSet()){
                    while(rs.next()) {
                        artistIds.add(rs.getInt("id_artist"));

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        try(PreparedStatement ps2 = conn.prepareStatement(SELECTBYID)) {
            for(Integer i : artistIds) {
                ps2.setInt(1, i);
                if (ps2.execute()) {
                    try (ResultSet rs2 = ps2.getResultSet()) {
                        while (rs2.next()) {
                            Artist a = new Artist();
                            a.setId(rs2.getInt("id"));
                            a.setName(rs2.getString("name"));
                            a.setNationality(Countries.valueOf(rs2.getString("nationality")));
                            a.setPhoto(rs2.getString("photo"));
                            List<Album> albums = new ArrayList<>();
                            a.setAlbumList(albums);
                            result.add(a);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void close() throws Exception {

    }
}
