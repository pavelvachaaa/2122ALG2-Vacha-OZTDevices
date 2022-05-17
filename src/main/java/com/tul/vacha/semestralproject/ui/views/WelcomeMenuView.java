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
public class WelcomeMenuView extends View {

    @Override
    public void display() {
        System.out.println("1. Přihlásit se");
        System.out.println("2. Registrovat se");
        System.out.println("3. Ukončit aplikaci");

        int choice = InputUtils.readInt();

        switch (choice) {
            case 1 ->
                Navigator.pushNamed("/login");
            case 2 -> // +Logout
                Navigator.pushNamed("/register");
            case 3 -> // +Logout
                System.exit(0);
            default ->
                System.out.println("Znova plsky");

        }
    }

}
