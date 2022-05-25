/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.app.services;

import com.tul.vacha.semestralproject.app.dto.UserChangePasswordDTO;
import com.tul.vacha.semestralproject.app.dto.UserLoginDTO;
import com.tul.vacha.semestralproject.app.dto.UserRegisterDTO;
import com.tul.vacha.semestralproject.app.repositories.implementation.UserRepository;
import com.tul.vacha.semestralproject.app.entities.User;
import com.tul.vacha.semestralproject.utils.AuthUtils;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * Servisa, která se stará o aktuální stav přihlášeného uživatele. Je to
 * singleton
 *
 * @author pvacha
 */
public class AuthService {

    private User user;
    private boolean isUserLoggedIn;
    private final UserRepository userRepository;
    private static AuthService instance = null;

    private AuthService(UserRepository userRepository) {
        this.isUserLoggedIn = false;
        this.user = null;
        this.userRepository = userRepository;
    }

    public static AuthService getInstance(UserRepository userRepository) {
        if (instance == null) {
            instance = new AuthService(userRepository);
        }

        return instance;
    }

    public static AuthService getInstance() {
        if (instance == null) {
            try {
                return getInstance(new UserRepository());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }

        return instance;
    }

    public boolean delete(String username) throws SQLException {
        return this.userRepository.delete(username);
    }

    public User getUser() {
        return new User(this.user);
    }

    public boolean register(UserRegisterDTO data) throws SQLException, NoSuchAlgorithmException {
        User dbUser = userRepository.getUserByUsername(data.getUsername());

        if (dbUser != null) {
            throw new IllegalArgumentException("Uživatel s tímto jménem již existuje");
        }

        return userRepository.registerUser(data);

    }

    public boolean canAccess(boolean adminNeeded) {
        return (isUserLoggedIn && adminNeeded && this.user.getIsAdmin()) || (isUserLoggedIn && !adminNeeded);
    }

    public boolean getIsUserLoggedIn() {
        return this.isUserLoggedIn;
    }

    public boolean login(UserLoginDTO loginDTO) throws SQLException {
        User user = userRepository.getUserByUsername(loginDTO.getUsername());
        if (user == null) {
            return false;
        }

        try {
            if (AuthUtils.getHash(loginDTO.getPassword()).equals(user.getPassword())) {
                this.isUserLoggedIn = true;
                this.user = user;
                return true;
            } else {
                return false;
            }
        } catch (NoSuchAlgorithmException e) {
            return false;
        }

    }

    public boolean changePassword(UserChangePasswordDTO data) throws SQLException, NoSuchAlgorithmException {
        return userRepository.changePassword(data);
    }

    /**
     * Zapíše data do binárního souboru. Data jsou uložena v pracovní složce
     * data
     *
     * @param filename - název souboru
     * @throws IOException
     */
    public void exportData(String filename) throws IOException {

        File dataDirectory = new File(System.getProperty("user.dir") + File.separator + "data");
        File data = new File(dataDirectory, filename);

        try ( DataOutputStream out = new DataOutputStream(new FileOutputStream(data, true))) {

            out.writeInt(this.user.getId());
            out.writeUTF(this.user.getName());
            out.writeUTF(this.user.getPassword());
            out.writeBoolean(this.user.getIsAdmin());
            out.close();

        }
    }

    /**
     * Načte data z binárního souboru
     *
     * @param filename
     * @return stringovou reprezentaci binárního souboru
     * @throws FileNotFoundException
     * @throws IOException
     */
    public String readData(String filename) throws FileNotFoundException, IOException {
        StringBuilder sb = new StringBuilder();

        int id;
        boolean isAdmin;
        String name;
        String password;

        File dataDirectory = new File(System.getProperty("user.dir") + File.separator + "data");
        File data = new File(dataDirectory, filename);

        try ( DataInputStream in = new DataInputStream(new FileInputStream(data))) {
            boolean end = false;
            while (!end) {
                try {
                    id = in.readInt();
                    name = in.readUTF();
                    password = in.readUTF();
                    isAdmin = in.readBoolean();

                    sb.append(String.format("Data ze souboru: %d %s %s %s ", id, isAdmin ? "admin" : "uživatel", name, password));
                    sb.append(System.lineSeparator());
                } catch (EOFException e) {
                    end = true;
                }
            }

            in.close();

        }

        return sb.toString();
    }

    public void logout() {
        this.user = null;
        this.isUserLoggedIn = false;
    }

}
