/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.ui;

import com.tul.vacha.semestralproject.app.App;
import com.tul.vacha.semestralproject.app.AuthService;
import com.tul.vacha.semestralproject.app.View;
import com.tul.vacha.semestralproject.app.repositories.user.UserRepository;
import com.tul.vacha.semestralproject.ui.views.HomeView;
import com.tul.vacha.semestralproject.ui.views.LoginView;
import com.tul.vacha.semestralproject.ui.views.MainMenuView;
import com.tul.vacha.semestralproject.ui.views.MedicalItemListView;
import com.tul.vacha.semestralproject.ui.views.WelcomeMenuView;
import com.tul.vacha.semestralproject.utils.dbutils.Database;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 *
 * @author pvacha
 */
public class AppRunner {

    public static void main(String[] args) {
        init();

        String defaultRoute = "/welcomeMenu";

        Map<String, View> routes = Map.of(
                "/login", new LoginView(),
                "/home", new HomeView(),
                "/medicalItemList", new MedicalItemListView(),
                "/mainMenu", new MainMenuView(),
                "/welcomeMenu", new WelcomeMenuView()
        );

        App app = new ConsoleApp(defaultRoute, routes);
        // App app = new WebApp();    // App app = new GUIApp();F

        app.start();

    }

    /**
     * Inicializace databáze
     */
    private static void init() {
        try {
            ResourceBundle props = ResourceBundle.getBundle("db", new Locale("cs", "CZ"));
            Database.getInstance(props.getString("mysql.url"), props.getString("mysql.username"), props.getString("mysql.password"));

        } catch (MissingResourceException e) {
            System.out.println("Nemohli jsme najít údaje od DB. :(");
            // TODO -> try again 
        } catch (SQLException ex) {
            System.out.println("Údaje od DB jsou špatné.");
            // TODO -> try again 
        }
    }
}
