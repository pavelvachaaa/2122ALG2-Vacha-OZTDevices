/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.ui.views;

import com.tul.vacha.semestralproject.app.Navigator;
import com.tul.vacha.semestralproject.app.View;
import com.tul.vacha.semestralproject.utils.InputUtils;

/**
 *
 * @author pvacha
 */
public class MainMenuView extends View {


    @Override
    public void display() {
        System.out.println("1. Seznam zařízení");
        System.out.println("2. Odhlásit se");

        int choice = InputUtils.readInt();

        switch (choice) {
            case 1 ->
                Navigator.pushNamed("/medicalItemList");
            case 2 -> // +Logout
                Navigator.pushNamed("/login");
            default -> {
            }
        }

    }

}
