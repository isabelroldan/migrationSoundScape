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

public class AlbumDAO extends Album implements iAlbumDAO {
    private final static String INSERT = "INSERT INTO album (name, publication, photo) VALUES (?,?,?)";
    private final static String UPDATE ="UPDATE album SET name=?, publication=?, photo=? WHERE id=?";
    private final static String DELETE="DELETE FROM album WHERE id=?";
    private final static String SELECTBYID="SELECT id, name, publication, photo FROM album WHERE id=?";
    private final static String SELECTALL="SELECT id, name, publication, photo FROM album";
    private final static String SELECTBYNAME="SELECT id, name, publication, photo FROM album WHERE name=?";
    private final static String SELECTBYARTIST="SELECT id_album FROM artist_album WHERE id_artist=?";

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
    public boolean save(){
        if(getId()!=-1){
            return update();
        }else{
            if(getArtists()==null ) return false;
            if(getSongs()==null ) return false;
            Connection conn = ConnectionMySQL.getConnect();
            if(conn==null) return false;

            try(PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)){
                ps.setString(1, getName());
                ps.setDate(2, getPublication());
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
     * Updates the current object's data in the database.
     * This method is used to modify the existing record in the database with the current object's data.
     *
     * @return true if the update operation is successful, false otherwise.
     */
    public boolean update(){
        if(getId()==-1) return false;
        if(getArtists()==null ) return false;
        if(getSongs()==null ) return false;

        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null) return false;

        try(PreparedStatement ps = conn.prepareStatement(UPDATE)){

            ps.setString(1, getName());
            ps.setDate(2, getPublication());
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
     * Retrieves all albums from the database.
     *
     * This method fetches all albums from the database and returns them as a set of Album objects.
     *
     * @return A set of Album objects if the retrieval is successful, or null if there was an error.
     */
    @Override
    public Set<Album> getAll() {
        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null) return null;
        Set<Album> result=new HashSet<>();
        try(PreparedStatement ps = conn.prepareStatement(SELECTALL)){
            if(ps.execute()){
                try(ResultSet rs = ps.getResultSet()){
                    while(rs.next()){
                        Album a = new Album();
                        a.setId(rs.getInt("id"));
                        a.setName(rs.getString("name"));
                        a.setPublication(rs.getDate("publication"));
                        a.setPhoto(rs.getString("photo"));
                        List<Artist> artists = new ArrayList<>();
                        try(ArtistDAO adao = new ArtistDAO(new Artist())) {
                            Set<Album> albumSet = adao.getByArtist(this.getName());
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
                            Set<Album> albumSet = adao.getByArtist(this.getName());
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

    @Override
    public Set<Album> getByName(String name) {
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
                            Set<Album> albumSet = adao.getByArtist(this.getName());
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
    public Set<Album> getByArtist(String artistName) {
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
                            Set<Album> albumSet = adao.getByArtist(this.getName());
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
    public void close() throws Exception {

    }
}
