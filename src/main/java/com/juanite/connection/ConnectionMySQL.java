package com.juanite.connection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMySQL {

    private String file = "connection.xml";
    private static ConnectionMySQL _newInstance;
    private static Connection con = null;
    private static ConnectionData cd = null;

    private ConnectionMySQL() {
        cd = loadXML();

        try {
            con = DriverManager.getConnection(cd.getServer() + "/" + cd.getDatabase(), cd.getUsername(), cd.getPassword());
        } catch (SQLException e) {
            con = null;
            e.printStackTrace();
        }
    }
    public static Connection getConnect(){
        if(con == null){
            _newInstance = new ConnectionMySQL();
        }
        return con;
    }

    public static void close() throws SQLException {
        if(con != null) {
            con.close();
            con = null;
        }
    }

    public ConnectionData loadXML(){
        ConnectionData con = new ConnectionData();
        JAXBContext jaxbContext;
        try{
            jaxbContext = JAXBContext.newInstance(ConnectionData.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            con = (ConnectionData) jaxbUnmarshaller.unmarshal(new File(file));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return con;
    }



}