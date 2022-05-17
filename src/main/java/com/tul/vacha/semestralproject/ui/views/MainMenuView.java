/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.ui.views;

import com.tul.vacha.semestralproject.app.core.Navigator;
import com.tul.vacha.semestralproject.app.core.View;
import com.tul.vacha.semestralproject.utils.InputUtils;

/**
 *
 * @author pvacha
 */
public class MainMenuView extends View {

    @Override
    public void display() {
        System.out.println("1. Seznam zařízení");
        System.out.println("2. Profil");

        // Check if admin 
        System.out.println("87. Administrace uživatelů");

        System.out.println("3. Kalendář");
        System.out.println("4. Odhlásit se");

        int choice = InputUtils.readInt();

        switch (choice) {
            case 1 ->
                Navigator.pushNamed("/medicalItemList");
            case 2 -> 
                Navigator.pushNamed("/profile");
            case 87 ->
                 Navigator.pushNamed("/users/list");
                
            case 4 -> // +Logout
                Navigator.pushNamed("/login");
            default -> {
            }
        }

    }

}
