/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.app.repositories.user;

import com.tul.vacha.semestralproject.entities.User;
import java.sql.SQLException;

/**
 *
 * @author pvacha
 */
public interface IUserRepository {

    User getUserById(int id) throws SQLException;

    User getUserByUsername(String username) throws SQLException;

    //  User getAllUsers();
    void addUser() throws SQLException;

    void deleteUser() throws SQLException;
}
