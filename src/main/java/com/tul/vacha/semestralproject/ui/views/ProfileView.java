/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.ui.views;

import com.tul.vacha.semestralproject.app.services.AuthService;
import com.tul.vacha.semestralproject.app.core.navigation.Navigator;
import com.tul.vacha.semestralproject.app.core.View;
import com.tul.vacha.semestralproject.app.core.navigation.Menu;
import com.tul.vacha.semestralproject.app.core.navigation.MenuItem;
import com.tul.vacha.semestralproject.app.dto.UserChangePasswordDTO;
import com.tul.vacha.semestralproject.app.entities.User;
import com.tul.vacha.semestralproject.utils.IOUtils;
import com.tul.vacha.semestralproject.utils.MenuUtils;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pvacha
 */
public class ProfileView extends View {

    private final AuthService auth = AuthService.getInstance();
    private final ArrayList<MenuItem> menuItems = new ArrayList<>() {
        {
            add(new MenuItem("Změnit heslo", (d) -> changePassword()));
        }
    };

    private final Menu menu = new Menu(menuItems, true, false);

    @Override
    public void display() {
        IOUtils.clearConsole();
        System.out.println("Můj profil");
        System.out.println("=================");
        displayProfile();

        MenuUtils.askForCommand(menu);

    }

    private void changePassword() {
        System.out.println("Nové heslo: ");
        String password = IOUtils.readString();

        System.out.println("Nové heslo znovu: ");
        String passwordConfirm = IOUtils.readString();

        try {
            // Repeat until ty dvě hesla nejsou stejné
            // udělat z toho hvězdičky...
            auth.changePassword(new UserChangePasswordDTO(auth.getUser().getId(), password));
            IOUtils.clearConsole();
            System.out.println("Úspěšně jsme Vám změnili heslo");
            this.display();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Nepodařilo se nám změnit heslo.");
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Někde nastala chyba.");
        }

    }

    private void displayProfile() {
        User u = auth.getUser();

        System.out.printf("Jméno: %s\n", u.getName());
        System.out.printf("Přezdívka: %s\n", u.getUsername());
        System.out.printf("Práva: %s\n", (u.getIsAdmin() ? "administrátorská" : "uživatelská"));
        System.out.println("           ");
    }

}
