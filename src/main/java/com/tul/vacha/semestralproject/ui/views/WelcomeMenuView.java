/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.ui.views;

import com.tul.vacha.semestralproject.app.core.navigation.Navigator;
import com.tul.vacha.semestralproject.app.core.View;
import com.tul.vacha.semestralproject.app.core.navigation.Menu;
import com.tul.vacha.semestralproject.app.core.navigation.MenuItem;
import com.tul.vacha.semestralproject.utils.IOUtils;
import com.tul.vacha.semestralproject.utils.MenuUtils;
import java.util.ArrayList;

/**
 *
 * @author pvacha
 */
public class WelcomeMenuView extends View {

    private final ArrayList<MenuItem> menuItems = new ArrayList<>() {
        {
            add(new MenuItem("Přihlásit se", (d) -> Navigator.pushNamed("/login")));
            add(new MenuItem("Registrovat se", (d) -> Navigator.pushNamed("/register")));
            add(new MenuItem("Ukončit aplikaci", (d) -> System.exit(0)));
        }
    };

    private final Menu menu = new Menu(menuItems);

    @Override
    public void display() {
        MenuUtils.askForCommand(menu);
    }

}
