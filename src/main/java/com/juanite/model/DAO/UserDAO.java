package com.juanite.model.DAO;

import com.juanite.connection.ConnectionMySQL;
import com.juanite.model.Genres;
import com.juanite.model.domain.Album;
import com.juanite.model.domain.Playlist;
import com.juanite.model.domain.Song;
import com.juanite.model.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Set;

import static jdk.internal.org.jline.reader.impl.LineReaderImpl.CompletionType.List;

public class UserDAO extends User implements iUserDAO {

    private final static String INSERT = "INSERT INTO user (id_person, photo) VALUES (?,?)";
    private final static String INSERTPERSON = "INSERT INTO person (name, email, password) VALUES (?,?,?)";
    private final static String UPDATE ="UPDATE user SET id_person=?, photo=? WHERE id_person=?";
    private final static String UPDATEPERSON ="UPDATE person SET id=?, name=?, email=? password=? WHERE id=?";

    private final static String DELETE="DELETE FROM user WHERE id_person=?";
    private final static String DELETEPERSON="DELETE FROM person WHERE id=?";
    private final static String SELECTBYID="SELECT id_person, photo FROM user WHERE id_person=?";
    private final static String SELECTPERSONBYID="SELECT id, name, email, password FROM person WHERE id=?";
    private final static String SELECTALL="SELECT id_person, photo FROM user";
    private final static String SELECTPERSONALL="SELECT id, name, email, password FROM person";



    public UserDAO(int id, String name, String email, String password, String photo, List<Playlist> playlist, List<Playlist> favoritePlaylist) {
        super(super(id, name, email, password),photo, List<Playlist>playlist, List<Playlist>favoritePlaylist);
    }

    public UserDAO(int id) {
        getById(id);
    }

    public UserDAO(User user) {
        super(super(user.getId(), user.getName(), user.getEmail(), user.getPassword()),user.getPhoto(), user.getPlaylists(), user.getFavoritePlaylists());
    }



    public boolean save(){
        if(getId()!=-1){
            return update();
        }else{
            if(playlists==null ) return false;
            if( favoritePlaylists==null ) return false;
            Connection conn = ConnectionMySQL.getConnect();
            if(conn==null) return false;

            try(PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)){

                ps.setInt(1, getId());
                ps.setString(2,getPhoto());

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
            try(PreparedStatement ps2 = conn.prepareStatement(INSERTPERSON, Statement.RETURN_GENERATED_KEYS)){

                ps2.setString(1, getName());
                ps2.setString(2,getEmail());
                ps2.setString(3,getPassword());

                if(ps2.executeUpdate()==1) {
                    try (ResultSet rs = ps2.getGeneratedKeys()) {
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
    public boolean update(){
        if(getId()==-1) return false;
        if(playlists==null ) return false;
        if( favoritePlaylists==null ) return false;

        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null) return false;
        try(PreparedStatement ps2 = conn.prepareStatement(UPDATEPERSON)){

            ps2.setInt(1, getId());
            ps2.setString(2,getName());
            ps2.setString(3,getEmail());
            ps2.setString(4,getPassword());
            if(ps2.executeUpdate()==1)
                return true;
            setId(-1);
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        try(PreparedStatement ps = conn.prepareStatement(UPDATE)){

            ps.setInt(1, getId());
            ps.setString(2,getPhoto());
            if(ps.executeUpdate()==1)
                return true;
            setId(-1);
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

     @Override
    public Set<User> getAll() {
        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null) return null;
        Set<User> result=new HashSet<>();
        try(PreparedStatement ps = conn.prepareStatement(SELECTALL)){
            if(ps.execute()){
                try(ResultSet rs = ps.getResultSet()){
                    while(rs.next()){
                        User u = new User();
                        u.setId(rs.getInt("id_person"));
                        u.setPhoto(rs.getString("photo"));
                        try(PreparedStatement ps2 = conn.prepareStatement(SELECTPERSONALL)){
                            if(ps2.execute()){
                                try(ResultSet rs2 = ps2.getResultSet()){
                                    while(rs2.next()){
                                        u.setName(rs2.getString("name"));
                                        u.setEmail(rs2.getString("email"));
                                        u.setPassword(rs2.getString("password"));
                                        List<Playlist> playlists = new ArrayList<>();
                                        try(PlaylistDAO pdao=new playlistDAO(new Playlist())){
                                            Set<Playlist> pset=pdao.getByUser(u, true);
                                            playlists.addAll(pset);

                                        }catch (Exception e) {
                                            return null;

                                        }
                                        u.setPlaylists(playlists);
                                        playlists= new ArrayList<>();
                                        try(PlaylistDAO pdao=new playlistDAO(new Playlist())){
                                            Set<Playlist> pset=pdao.getByUser(u, false);
                                            playlists.addAll(pset);

                                        }catch (Exception e) {
                                            return null;

                                        }
                                        u.setFavoritePlaylist(playlists);
                                    }
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                            return null;
                        }

                        result.add(u);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }



    // Método para obtener un usuario por su ID desde la base de datos.
    @Override
    public boolean getById(int id) {
        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null) return null;
        try(PreparedStatement ps = conn.prepareStatement(SELECTBYID)){
            ps.setInt(1, id);
            if(ps.execute()){
                try(ResultSet rs = ps.getResultSet()){
                   if (rs.next()){

                        setId(rs.getInt("id_person"));
                        setPhoto(rs.getString("photo"));
                        try(PreparedStatement ps2 = conn.prepareStatement(SELECTPERSONBYID)){
                            ps.setInt(1, id);
                            if(ps2.execute()){
                                try(ResultSet rs2 = ps2.getResultSet()){
                                    if (rs2.next()){
                                        setName(rs2.getString("name"));
                                        setEmail(rs2.getString("email"));
                                        setPassword(rs2.getString("password"));
                                        List<Playlist> playlists = new ArrayList<>();
                                        try(PlaylistDAO pdao=new playlistDAO(new Playlist())){
                                            Set<Playlist> pset=pdao.getByUser(this, true);
                                            playlists.addAll(pset);

                                        }catch (Exception e) {
                                            return null;

                                        }
                                        setPlaylists(playlists);
                                        playlists= new ArrayList<>();
                                        try(PlaylistDAO pdao=new playlistDAO(new Playlist())){
                                            Set<Playlist> pset=pdao.getByUser(this, false);
                                            playlists.addAll(pset);

                                        }catch (Exception e) {
                                            return null;

                                        }
                                        setFavoritePlaylist(playlists);
                                    }
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                            return null;
                        }

                        result.add(u);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    // Método para obtener un usuario por su nombre desde la base de datos.
    @Override
    public boolean getByName(String name) {
        Connection conn = ConnectionMySQL.getConnect();
        if(conn==null) return null;
        try(PreparedStatement ps = conn.prepareStatement(SELECTBYNAME)){
            ps.setString(1, name);
            if(ps.execute()){
                try(ResultSet rs = ps.getResultSet()){
                    if (rs.next()){

                        setId(rs.getInt("id_person"));
                        setPhoto(rs.getString("photo"));
                        try(PreparedStatement ps2 = conn.prepareStatement(SELECTPERSONBYNAME)){
                            ps.setString(1, name);
                            if(ps2.execute()){
                                try(ResultSet rs2 = ps2.getResultSet()){
                                    if (rs2.next()){
                                        setName(rs2.getString("name"));
                                        setEmail(rs2.getString("email"));
                                        setPassword(rs2.getString("password"));
                                        List<Playlist> playlists = new ArrayList<>();
                                        try(PlaylistDAO pdao=new playlistDAO(new Playlist())){
                                            Set<Playlist> pset=pdao.getByUser(this, true);
                                            playlists.addAll(pset);

                                        }catch (Exception e) {
                                            return null;

                                        }
                                        setPlaylists(playlists);
                                        playlists= new ArrayList<>();
                                        try(PlaylistDAO pdao=new playlistDAO(new Playlist())){
                                            Set<Playlist> pset=pdao.getByUser(this, false);
                                            playlists.addAll(pset);

                                        }catch (Exception e) {
                                            return null;

                                        }
                                        setFavoritePlaylist(playlists);
                                    }
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                            return null;
                        }

                        result.add(u);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    // Método para cerrar la conexión a la base de datos.
    public void close() { }


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
        try(PreparedStatement ps2 = conn.prepareStatement(DELETEPERSON)){
            ps2.setInt(1,getId());
            if(ps2.executeUpdate()==1)
                return true;

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
