/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.app.repositories.user;

import com.tul.vacha.semestralproject.app.entities.User;
import com.tul.vacha.semestralproject.utils.dbutils.Database;
import com.tul.vacha.semestralproject.utils.dbutils.ResultSetPropertiesSimplifyHelps;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author pvacha
 */
public class UserRepository implements IUserRepository {

    private final Database db;

    public UserRepository() throws SQLException {
        this.db = Database.getInstance();
    }

    @Override
    public User getUserById(int id) throws SQLException {
        ArrayList<User> users = ResultSetPropertiesSimplifyHelps.putResult(db.query("SELECT * FROM users WHERE id = ?", new Object[]{id}), User.class);

        if (users.isEmpty()) {
            return null;
        }

        return users.get(0);
    }

    @Override
    public User getUserByUsername(String username) throws SQLException {
        ArrayList<User> users = ResultSetPropertiesSimplifyHelps.putResult(db.query("SELECT * FROM users WHERE username = ?", new Object[]{username}), User.class);

        if (users.isEmpty()) {
            return null;
        }

        return users.get(0);
    }

    @Override
    public void addUser() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteUser() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
