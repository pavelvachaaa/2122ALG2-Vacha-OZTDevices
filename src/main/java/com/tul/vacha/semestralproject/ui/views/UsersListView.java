/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.ui.views;

import com.tul.vacha.semestralproject.app.core.View;
import com.tul.vacha.semestralproject.app.entities.User;
import com.tul.vacha.semestralproject.app.repositories.implementation.UserRepository;
import java.sql.SQLException;
import java.util.ArrayList;

// TODO: Command smazat
// TODO: Command add
// TODO: Fancy výpis
/**
 *
 * @author pvacha
 */
public class UsersListView extends View {

    // Check admin again
    @Override
    public void display() {
        System.out.println("Seznam profilů");
        System.out.println("===================");
        listOfUsers();

        // Zeptat se na akci - delete, view, export,  možná import
        // pak se  zeptat sna číslo uživatele
    }

    // pozor ŠPATNĚ - měl bych pracovat jen se servisou
    private void listOfUsers() {
        UserRepository repo;
        try {
            repo = new UserRepository();

            ArrayList<User> users = repo.getUsers();
            for (int i = 0; i < users.size(); i++) {
                System.out.printf("%d %s", (i + 1), users.get(i));
            }

        } catch (SQLException ex) {
        }

    }

}
