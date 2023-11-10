package com.juanite.model.DAO;

import com.juanite.connection.ConnectionMySQL;
import com.juanite.model.Countries;
import com.juanite.model.Genres;
import com.juanite.model.domain.*;
import com.juanite.util.AppData;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlaylistDAO extends Playlist implements iPlaylistDAO {

    private final static String INSERT = "INSERT INTO playlist (name, description, id_person) VALUES (?,?,?)";
    private final static String INSERTSUBSCRIBER = "INSERT INTO user_playlist (id_person, id_playlist) VALUES (?,?)";
    private final static String INSERTSONG = "INSERT INTO song_playlist (id_song, id_playlist) VALUES (?,?)";
    private final static String UPDATE ="UPDATE playlist SET name=?, description=?, id_person=? WHERE id=?";
    private final static String DELETE="DELETE FROM playlist WHERE id=?";
    private final static String DELETESUBSCRIBERS = "DELETE FROM user_playlist WHERE id_playlist=?";
    private final static String DELETEUSERPLAYLISTS = "DELETE FROM user_playlist WHERE id_person=?";
    private final static String DELETEUSERPLAYLIST = "DELETE FROM user_playlist WHERE id_person=? AND id_playlist=?";
    private final static String DELETESONGS = "DELETE FROM song_playlist WHERE id_song=?";
    private final static String DELETEPLAYLISTSONGS = "DELETE FROM song_playlist WHERE id_playlist=?";
    private final static String SELECTBYID="SELECT id, name, description, id_person FROM playlist WHERE id=?";
    private final static String SELECTALL="SELECT id, name, description, id_person FROM playlist";
    private final static String SELECTBYNAME="SELECT id, name, description, id_person FROM playlist WHERE name=?";
    private final static String SELECTBYOWNER="SELECT id, name, description, id_person FROM playlist WHERE id_person=?";
    private final static String SELECTBYSONG = "SELECT id_playlist FROM song_playlist WHERE id_song=?";
    private final static String SELECTBYSUBSCRIBER="SELECT id_playlist FROM user_playlist WHERE id_person=?";
    private final static String SELECTSUBSCRIBERS="SELECT id_person FROM user_playlist WHERE id_playlist=?";
    private final static String SELECTCONTAININGPLAYLISTNAMES = "SELECT id, name, description, id_person FROM playlist WHERE name LIKE ?";


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
     * @return true if the save operation is successful, false otherwise.
     */
    public boolean save(){
        if(getId()!=-1){
            return update();
        }else{
            if(getOwner()==null ) return false;
            if(getSongs()==null ) return false;
            if(getSubscribers()==null ) return false;
            if(getComments()==null ) return false;
            Connection conn = ConnectionMySQL.getConnect();
            if(conn==null) return false;

            try(PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)){
                ps.setString(1, getName());
                ps.setString(2, getDescription());
                ps.setInt(3, getOwner().getId());

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
    public boolean saveSubscriber(User user){
        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null)
            return false;
        try(PreparedStatement ps = conn.prepareStatement(INSERTSUBSCRIBER, Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1, user.getId());
            ps.setInt(2, getId());

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
     * Saves the current object to the database.
     * If the object already has an ID, it updates the existing record; otherwise, it inserts a new record.
     *
     * @return true if the save operation is successful, false otherwise.
     */
    public boolean saveSong(Song song){
        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null)
            return false;
        try(PreparedStatement ps = conn.prepareStatement(INSERTSONG, Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1, song.getId());
            ps.setInt(2, getId());

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
        if(getOwner()==null ) return false;
        if(getSongs()==null ) return false;
        if(getSubscribers()==null ) return false;
        if(getComments()==null ) return false;

        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null) return false;

        try(PreparedStatement ps = conn.prepareStatement(UPDATE)){

            ps.setString(1, getName());
            ps.setString(2, getDescription());
            ps.setInt(3, getOwner().getId());
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

        removeSubscribers();
        removePlaylistSongs();

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
    public boolean removeSubscribers(){

        if(getId()==-1) return false;

        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null) return false;

        try(PreparedStatement ps = conn.prepareStatement(DELETESUBSCRIBERS)){
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
    public boolean removeUserPlaylist(){

        if(getId()==-1) return false;

        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null) return false;

        removeSubscribers();
        removePlaylistSongs();

        try(PreparedStatement ps = conn.prepareStatement(DELETEUSERPLAYLIST)){
            ps.setInt(1, AppData.getCurrentUser().getId());
            ps.setInt(2,getId());
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
    public boolean removeSong(Song song){

        if(getId()==-1) return false;

        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null) return false;

        try(PreparedStatement ps = conn.prepareStatement(DELETESONGS)){
            ps.setInt(1, song.getId());
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
    public boolean removePlaylistSongs(){

        if(getId()==-1) return false;

        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null) return false;

        try(PreparedStatement ps = conn.prepareStatement(DELETEPLAYLISTSONGS)){
            ps.setInt(1, getId());
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
    public boolean removeUserPlaylists(User user){

        if(getId()==-1) return false;

        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null) return false;

        try(PreparedStatement ps = conn.prepareStatement(DELETEUSERPLAYLISTS)){
            ps.setInt(1, user.getId());
            if(ps.executeUpdate()==1)
                return true;

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Retrieves all artists from the database.
     *
     * This method fetches all artists from the database and returns them as a set of Artist objects.
     *
     * @return A set of Artist objects if the retrieval is successful, or null if there was an error.
     */
    @Override
    public Set<Playlist> getAll() {
        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null) return null;
        Set<Playlist> result=new HashSet<>();
        try(PreparedStatement ps = conn.prepareStatement(SELECTALL)){
            result = internalGetPlaylists(ps, result);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    @Override
    public boolean getById(int id) {
        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null) return false;
        try(PreparedStatement ps = conn.prepareStatement(SELECTBYID)){
            ps.setInt(1, id);
            if(ps.execute()){
                try(ResultSet rs = ps.getResultSet()){
                    if(rs.next()){
                        setId(rs.getInt("id"));
                        setName(rs.getString("name"));
                        setDescription(rs.getString("description"));
                        setOwner(new User(rs.getInt("id_person")));
                        List<Song> songs = new ArrayList<>();
                        try(SongDAO sdao = new SongDAO(new Song())) {
                            Set<Song> songSet = sdao.getByPlaylist(this);
                            songs.addAll(songSet);
                        } catch (Exception e) {
                            return false;
                        }
                        setSongs(songs);
                        List<User> subscribers = new ArrayList<>();
                        Set<User> subscriberSet = getPlaylistSubscribers();
                        subscribers.addAll(subscriberSet);
                        setSubscribers(subscribers);
                        List<Comment> comments = new ArrayList<>();
                        try(CommentDAO cdao = new CommentDAO(new Comment())) {
                            Set<Comment> commentSet = cdao.getByPlaylist(this);
                            comments.addAll(commentSet);
                        } catch (Exception e) {
                            return false;
                        }
                        setComments(comments);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Set<Playlist> getByName(String name) {
        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null) return null;
        Set<Playlist> result=new HashSet<>();
        try (PreparedStatement ps = conn.prepareStatement(SELECTBYNAME)) {
            ps.setString(1, name);
            result = internalGetPlaylists(ps, result);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    @Override
    public Set<Playlist> getByUser(User user, boolean isOwner) {
        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null) return null;
        Set<Playlist> result=new HashSet<>();
        if(isOwner) {
            try (PreparedStatement ps = conn.prepareStatement(SELECTBYOWNER)) {
                ps.setInt(1, user.getId());
                result = internalGetPlaylists(ps, result);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            try (PreparedStatement ps = conn.prepareStatement(SELECTBYSUBSCRIBER)) {
                ps.setInt(1, user.getId());
                if(ps.execute()) {
                    try (ResultSet rs = ps.getResultSet()) {
                        while (rs.next()) {
                            result.add(new PlaylistDAO(rs.getInt("id_playlist")));
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return result;
    }

    private Set<Playlist> internalGetPlaylists(PreparedStatement ps, Set<Playlist> result) throws SQLException {
        if (ps.execute()) {
            try (ResultSet rs = ps.getResultSet()) {
                while (rs.next()) {
                    Playlist a = new Playlist();
                    a.setId(rs.getInt("id"));
                    a.setName(rs.getString("name"));
                    a.setDescription(rs.getString("description"));
                    a.setOwner(new User(rs.getInt("id_person")));
                    List<Song> songs = new ArrayList<>();
                    try (SongDAO sdao = new SongDAO(new Song())) {
                        Set<Song> songSet = sdao.getByPlaylist(this);
                        songs.addAll(songSet);
                    } catch (Exception e) {
                        return null;
                    }
                    a.setSongs(songs);
                    List<User> subscribers = new ArrayList<>();
                    Set<User> subscriberSet = getPlaylistSubscribers();
                    subscribers.addAll(subscriberSet);
                    a.setSubscribers(subscribers);
                    List<Comment> comments = new ArrayList<>();
                    try (CommentDAO cdao = new CommentDAO(new Comment())) {
                        Set<Comment> commentSet = cdao.getByPlaylist(this);
                        comments.addAll(commentSet);
                    } catch (Exception e) {
                        return null;
                    }
                    a.setComments(comments);
                    result.add(a);
                }
            }
        }
        return result;
    }

    @Override
    public Set<User> getPlaylistSubscribers() {
        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null) return null;
        Set<User> result=new HashSet<>();
        try (PreparedStatement ps = conn.prepareStatement(SELECTSUBSCRIBERS)) {
            ps.setInt(1, getId());
            if(ps.execute()) {
                try(ResultSet rs = ps.getResultSet()) {
                    while (rs.next()) {
                        result.add(new User(rs.getInt("id_person")));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    public Set<Playlist> getBySong(Song song) {
        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null) return null;
        Set<Playlist> result = new HashSet<>();
        try(PreparedStatement ps = conn.prepareStatement(SELECTBYSONG)){
            ps.setInt(1, song.getId());
            if(ps.execute()){
                try(ResultSet rs = ps.getResultSet()){
                    while(rs.next()){
                        Playlist p = new Playlist();
                        p.setId(rs.getInt("id"));
                        p.setName(rs.getString("name"));
                        p.setDescription(rs.getString("description"));
                        p.setOwner(new User(rs.getInt("id_person")));
                        List<Song> songs = new ArrayList<>();
                        try(SongDAO sdao = new SongDAO(new Song())) {
                            Set<Song> songSet = sdao.getByPlaylist(this);
                            songs.addAll(songSet);
                        } catch (Exception e) {
                            return null;
                        }
                        p.setSongs(songs);
                        List<User> subscribers = new ArrayList<>();
                        Set<User> subscriberSet = getPlaylistSubscribers();
                        subscribers.addAll(subscriberSet);
                        p.setSubscribers(subscribers);
                        List<Comment> comments = new ArrayList<>();
                        try(CommentDAO cdao = new CommentDAO(new Comment())) {
                            Set<Comment> commentSet = cdao.getByPlaylist(this);
                            comments.addAll(commentSet);
                        } catch (Exception e) {
                            return null;
                        }
                        p.setComments(comments);
                        result.add(p);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    public Set<Playlist> getSearchResults(String searchInput) {
        Connection conn = ConnectionMySQL.getConnect();
        Set<Playlist> result = new HashSet<>();
        try (PreparedStatement ps = conn.prepareStatement(SELECTCONTAININGPLAYLISTNAMES)) {
            ps.setString(1, "%" + searchInput + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Playlist pl = new Playlist();
                    pl.setId(rs.getInt("id"));
                    pl.setName(rs.getString("name"));
                    pl.setDescription(rs.getString("description"));
                    pl.setOwner(AppData.getCurrentUser());
                    result.add(pl);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void close() throws Exception {

    }
}
