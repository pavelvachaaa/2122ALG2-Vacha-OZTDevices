/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.utils.dbutils;

/**
 *
 * @author pvacha
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A Connection Pool with 5 Available Connections *
 */
public class Database {

    private final List<Connection> availableConnections = new ArrayList<>();
    private final List<Connection> usedConnections = new ArrayList<>();
    private final int MAX_CONNECTIONS = 5;

    private final String URL;
    private final String USERID;
    private final String PASSWORD;

    // TODO: Změnit implementaci. přidávat spojení do poolu postupně a pak je odstraňovat
    // Max connection třeba 10 - ale přidávat je dynamicky, ne hned na začátku
    public Database(String Url, String UserId, String password) throws SQLException {
        this.URL = Url;
        this.USERID = UserId;
        this.PASSWORD = password;

        for (int count = 0; count < MAX_CONNECTIONS; count++) {
            availableConnections.add(this.createConnection());
        }

    }

    /**
     * Vytváří spojení mezi databází a klientem
     *
     * @return connection
     * @throws SQLException
     */
    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(this.URL, this.USERID, this.PASSWORD);
    }

    /**
     * Vrací spojení, které je volné z poolu
     *
     * @return connection if there is any
     */
    public Connection getConnection() {
        if (this.availableConnections.isEmpty()) {
            System.out.println("All connections are Used !!");
            return null;
        }

        Connection con = this.availableConnections.remove(this.availableConnections.size() - 1);
        this.usedConnections.add(con);
        return con;

    }

    /**
     * Vrací spojení zpátky do poolu
     *
     * @param con
     * @return true if it was successful, false otherwise
     */
    public boolean releaseConnection(Connection con) {
        if (null != con) {
            usedConnections.remove(con);
            availableConnections.add(con);
            return true;
        }
        return false;
    }

 
    public int getFreeConnectionCount() {
        return availableConnections.size();
    }

    /**
     * Vrací data na základě query
     * @param query - SQL dotaz
     * @param params - paramerty pro prepared statements
     * @return resultset when success, otherwise throws error
     * @throws SQLException 
     */
    public ResultSet query(String query, Object[] params) throws SQLException {
        Connection conn = this.getConnection();
        PreparedStatement ps = conn.prepareStatement(query);

        if (params != null) {
            int index = 1;
            for (Object param : params) {
                ps.setObject(index, param);
                index++;
            }
        }

        this.releaseConnection(conn);
        ResultSet rs = ps.executeQuery();

        return rs;
    }

    public ResultSet query(String query) throws SQLException {
        return this.query(query, new Object[]{});
    }
}
