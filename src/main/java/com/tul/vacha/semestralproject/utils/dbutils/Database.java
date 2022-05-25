/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.utils.dbutils;

/**
 * Databázový wrapper
 *
 * @version 1.0
 * @author pvacha
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private final List<Connection> availableConnections = new ArrayList<>();
    private final List<Connection> usedConnections = new ArrayList<>();
    private final static int MAX_CONNECTIONS = 15;
    private final String url;
    private final String user;
    private final String pass;

    private static Database instance = null;

    public static Database getInstance(String Url, String UserId, String password) throws SQLException {
        if (instance == null) {
            instance = new Database(Url, UserId, password);
        }

        return instance;
    }

    // Asi to ošetřit rovnou tedy, pak je to bolest všude jinde ošetřit
    public static Database getInstance() throws SQLException {
        if (instance == null) {
            throw new IllegalStateException("Databáze nebyla inicializována.");
        }

        return instance;
    }

    private Database(String Url, String UserId, String password) throws SQLException {
        this.url = Url;
        this.user = UserId;
        this.pass = password;

        // Přidáme jedno jediný do začátku, pak přidáváme dynamicky
        availableConnections.add(this.createConnection());
    }

    /**
     * Vytváří spojení mezi databází a klientem
     *
     * @return connection
     * @throws SQLException
     */
    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(this.url, this.user, this.pass);
    }

    /**
     * Vytváří spojení podle potřeby
     *
     * @return connection if there is any
     */
    public Connection getConnection() {
        if (this.availableConnections.isEmpty() && usedConnections.size() < MAX_CONNECTIONS) {
            try {
                availableConnections.add(this.createConnection());
            } catch (SQLException e) {
                System.out.println("Nemohli jsme vytvořit spojení.");
            }

        }

        if (this.availableConnections.isEmpty() && usedConnections.size() >= MAX_CONNECTIONS) {
            System.out.println("Všechna spojení jsou vyčerpána");
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
     * Když upravujeme data, tak se nevrací resultset ale int
     *
     * @param query
     * @param params
     * @return int - how many rows were affected
     * @throws SQLException
     */
    public int queryExec(String query, Object[] params) throws SQLException {
        Connection conn = this.getConnection();
        PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        if (params != null) {
            int index = 1;
            for (Object param : params) {
                ps.setObject(index, param);
                index++;
            }
        }
        ps.executeUpdate();
        try ( ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {

                this.releaseConnection(conn);
                return generatedKeys.getInt(1);
            } else if (query.contains("DELETE") || query.contains("UPDATE")) {
                return 1;
            } else {
                throw new SQLException("Chyba");

            }
        }

    }

    /**
     * Vrací data na základě query
     *
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

    public int queryExec(String query) throws SQLException {
        return this.queryExec(query, new Object[]{});
    }

    public ResultSet query(String query) throws SQLException {
        return this.query(query, new Object[]{});
    }

    /* public static void main(String[] args) {
        try {
            ResourceBundle props = ResourceBundle.getBundle("db", new Locale("cs", "CZ"));
            Database db = Database.getInstance(props.getString("mysql.url"), props.getString("mysql.username"), props.getString("mysql.password"));
            db.getConnection();
            db.getConnection();
            db.getConnection();
            db.getConnection();
            Connection conn = db.getConnection();
            db.getConnection();
            db.getConnection();
            db.getConnection();
            db.getConnection();
            db.getConnection();
            db.getConnection();
            db.getConnection();
            db.getConnection();

        } catch (MissingResourceException e) {
            System.out.println("Nemohli jsme najít údaje od DB. :(");
            // TODO -> try again 
        } catch (SQLException ex) {
            System.out.println("Údaje od DB jsou špatné.");
            // TODO -> try again 
        }
    }*/
}
