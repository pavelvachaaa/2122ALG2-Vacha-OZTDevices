/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.ui;

import com.tul.vacha.semestralproject.app.core.App;
import com.tul.vacha.semestralproject.app.core.View;
import com.tul.vacha.semestralproject.ui.views.HomeView;
import com.tul.vacha.semestralproject.ui.views.InspectionCalendarView;
import com.tul.vacha.semestralproject.ui.views.LoginView;
import com.tul.vacha.semestralproject.ui.views.MainMenuView;
import com.tul.vacha.semestralproject.ui.views.MedicalItemListView;
import com.tul.vacha.semestralproject.ui.views.ProfileView;
import com.tul.vacha.semestralproject.ui.views.UsersListView;
import com.tul.vacha.semestralproject.ui.views.WelcomeMenuView;
import com.tul.vacha.semestralproject.utils.dbutils.Database;

import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 * @author pvacha
 */
public class AppRunner {

    public static void main(String[] args) {
        init();

        //TODO: Při ukončení aplikace ukončit db spojení !!!!okú
        String defaultRoute = "/inspectionCaledar";
        defaultRoute = "/welcomeMenu";

        // TODO: Chtělo by to nějaký router a ne to inicializovat zde - asi
        // + líbila by se mi runtime inicializace, jaksi na to nemám kapacitu
        Map<String, View> routes = Map.of("/login", new LoginView(),
                "/home", new HomeView(),
                "/medicalItemList", new MedicalItemListView(),
                "/mainMenu", new MainMenuView(),
                "/welcomeMenu", new WelcomeMenuView(),
                "/users/list", new UsersListView(),
                "/profile", new ProfileView(),
                "/inspectionCaledar", new InspectionCalendarView()
        );

        App app = new ConsoleApp(defaultRoute, routes);
        // App app = new WebApp();   
        // App app = new GUIApp();

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
