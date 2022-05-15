/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.entities;

import com.tul.vacha.semestralproject.utils.AuthUtils;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author pvacha
 */
public class User {

    private int id;
    private String name;
    private String username;
    private String password;
    private boolean isAdmin;

    public String getHash(String password) {
        try {
            return AuthUtils.getHash(password);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean getIsAdmin(boolean isAdmin) {
        return this.isAdmin;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public User() {
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s", this.name, this.username, this.password, this.isAdmin);

    }

}
