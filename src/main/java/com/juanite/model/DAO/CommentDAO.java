package com.juanite.model.DAO;

import com.juanite.connection.ConnectionMySQL;
import com.juanite.model.domain.Comment;
import com.juanite.model.domain.Playlist;
import com.juanite.model.domain.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommentDAO extends Comment implements iCommentDAO{

    private final static String INSERT = "INSERT INTO Comment (date_time, id_person, id_playlist) VALUES (?,?,?)";
    private final static String UPDATE ="UPDATE Comment SET date_time=?,id_person=?,id_playlist=? WHERE id=?";
    private final static String DELETE="DELETE FROM Comment WHERE id=?";
    private final static String SELECTBYID="SELECT id,date_time,id_person,id_playlist FROM Comment WHERE id=?";
    private final static String SELECTALL="SELECT id,date_time,id_person,id_playlist FROM Comment";
    private final static String SELECTBYPERSON="SELECT id,date_time,id_person,id_playlist FROM Comment WHERE id_person=?";
    private final static String SELECTBYPLAYLIST="SELECT id,date_time,id_person,id_playlist FROM Comment WHERE id_playlist=?";

    public CommentDAO(int id, LocalDateTime date_time){
        super(id, date_time);
    }

    //public CommentDAO(int id){ getById(id);}

    public CommentDAO(Comment c){
        super(c.getId(), c.getDate_time());
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
            if(user==null ) return false;
            if( playlist==null ) return false;
            Connection conn = ConnectionMySQL.getConnect();
            if(conn==null) return false;

            try(PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)){
                LocalDateTime dateTime = getDate_time();
                String formattedDateTime = dateTime.toString();

                ps.setString(1, formattedDateTime);
                ps.setInt(2,user.getId());
                ps.setInt(3,playlist.getId());

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
        if(user==null ) return false;
        if( playlist==null ) return false;

        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null) return false;

        try(PreparedStatement ps = conn.prepareStatement(UPDATE)){
            LocalDateTime dateTime = getDate_time();
            String formattedDateTime = dateTime.toString();

            ps.setString(1, formattedDateTime);
            ps.setInt(2,user.getId());
            ps.setInt(3,playlist.getId());
            ps.setInt(4, getId());
            if(ps.executeUpdate()==1)
                return true;
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
                        String dateTimeString = rs.getString("date_time");
                        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString);
                        setDate_time(dateTime);
                        user = new UserDAO(rs.getInt("id_person"));
                        playlist = new PlaylistDAO(rs.getInt("id_playlist"));
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
     * Retrieves all comments from the database.
     *
     * This method fetches all comments from the database and returns them as a set of Comment objects.
     *
     * @return A set of Comment objects if the retrieval is successful, or null if there was an error.
     */
    @Override
    public Set<Comment> getAll() {
        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null) return null;
        Set<Comment> result=new HashSet<>();
        try(PreparedStatement ps = conn.prepareStatement(SELECTALL)){
            if(ps.execute()){
                try(ResultSet rs = ps.getResultSet()){
                    while(rs.next()){
                        Comment c = new Comment();
                        c.setId(rs.getInt("id"));
                        String dateTimeString = rs.getString("date_time");
                        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString);
                        c.setDate_time(dateTime);
                        c.setUser(new UserDAO(rs.getInt("id_person")));
                        c.setPlaylist(new PlaylistDAO(rs.getInt("id_playlist")));

                        result.add(c);
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
     * Retrieves all comments associated with a specific playlist from the database.
     * This method fetches all comments that are related to the provided playlist and returns them as a set of Comment objects.
     *
     * @param playlist The playlist for which comments should be retrieved.
     * @return A set of Comment objects if the retrieval is successful, or null if there was an error.
     */
    @Override
    public Set<Comment> getByPlaylist(Playlist playlist) {
        Connection conn = ConnectionMySQL.getConnect();
        if (conn == null) return null;
        Set<Comment> result = new HashSet<>();
        try (PreparedStatement ps = conn.prepareStatement(SELECTBYPLAYLIST)) {
            ps.setInt(1, playlist.getId());
            if (ps.execute()) {
                try (ResultSet rs = ps.getResultSet()) {
                    while (rs.next()) {
                        Comment c = new Comment();
                        c.setId(rs.getInt("id"));
                        String dateTimeString = rs.getString("date_time");
                        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString);
                        c.setDate_time(dateTime);
                        c.setUser(new UserDAO(rs.getInt("id_person")));
                        c.setPlaylist(playlist);

                        result.add(c);
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
     * Retrieves all comments created by a specific user from the database.
     * This method fetches all comments that have been created by the provided user and returns them as a set of Comment objects.
     *
     * @param user The user for whom comments should be retrieved.
     * @return A set of Comment objects if the retrieval is successful, or null if there was an error.
     */
    @Override
    public Set<Comment> getByUser(User user) {
        Connection conn = ConnectionMySQL.getConnect();
        if (conn == null) return null;
        Set<Comment> result = new HashSet<>();
        try (PreparedStatement ps = conn.prepareStatement(SELECTBYPERSON)) {
            ps.setInt(1, user.getId()); // Accede al ID del usuario a través del objeto User
            if (ps.execute()) {
                try (ResultSet rs = ps.getResultSet()) {
                    while (rs.next()) {
                        Comment c = new Comment();
                        c.setId(rs.getInt("id"));
                        String dateTimeString = rs.getString("date_time");
                        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString);
                        c.setDate_time(dateTime);
                        c.setUser(user);
                        c.setPlaylist(new PlaylistDAO(rs.getInt("id_playlist")));

                        result.add(c);
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
     * Closes any associated resources.
     */
    @Override
    public void close() throws Exception {
    }
}
