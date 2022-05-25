/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.app.repositories.interfaces;

import com.tul.vacha.semestralproject.app.dto.UserChangePasswordDTO;
import com.tul.vacha.semestralproject.app.dto.UserRegisterDTO;
import com.tul.vacha.semestralproject.app.entities.User;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author pvacha
 */
public interface IUserRepository {

    User getUserById(int id) throws SQLException;

    User getUserByUsername(String username) throws SQLException;

    ArrayList getUsers() throws SQLException;

    boolean changePassword(UserChangePasswordDTO user) throws SQLException, NoSuchAlgorithmException;

    boolean registerUser(UserRegisterDTO user) throws SQLException, NoSuchAlgorithmException;

    boolean delete(String username) throws SQLException;

}
