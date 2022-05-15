/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.app;

import com.tul.vacha.semestralproject.app.repositories.user.UserRepository;
import com.tul.vacha.semestralproject.app.entities.User;
import com.tul.vacha.semestralproject.utils.AuthUtils;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
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

    public void logout() {
        this.user = null;
        this.isUserLoggedIn = false;
    }

}
