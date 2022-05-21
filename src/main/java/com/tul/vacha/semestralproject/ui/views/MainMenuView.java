/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.ui.views;

import com.tul.vacha.semestralproject.app.core.navigation.Navigator;
import com.tul.vacha.semestralproject.app.core.View;
import com.tul.vacha.semestralproject.app.core.navigation.Menu;
import com.tul.vacha.semestralproject.app.core.navigation.MenuItem;
import com.tul.vacha.semestralproject.app.services.AuthService;
import com.tul.vacha.semestralproject.utils.IOUtils;
import com.tul.vacha.semestralproject.utils.MenuUtils;
import java.util.ArrayList;

/**
 *
 * @author pvacha
 */
public class MainMenuView extends View {

    private final AuthService authService = AuthService.getInstance();
    private final ArrayList<MenuItem> menuItems = new ArrayList<>() {
        {
            add(new MenuItem("Seznam zařízení", (d) -> Navigator.pushNamed("/medicalItemList")));
            add(new MenuItem("Profil", (d) -> Navigator.pushNamed("/profile")));
            add(new MenuItem("Kalendář", (d) -> Navigator.pushNamed("/inspectionCaledar")));
            add(new MenuItem("Seznam uživatelů", (d) -> Navigator.pushNamed("/users/list")));
            add(new MenuItem("Odhlásit se", (d) -> {
                authService.logout();
                Navigator.pushNamed("/welcomeMenu");
            }));
        }
    };

    private final Menu menu = new Menu(menuItems);

    @Override
    public void display() {
        MenuUtils.askForCommand(menu);
    }

}
