/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.ui.views;

import com.tul.vacha.semestralproject.app.services.AuthService;
import com.tul.vacha.semestralproject.app.core.View;
import com.tul.vacha.semestralproject.app.core.navigation.Menu;
import com.tul.vacha.semestralproject.app.core.navigation.MenuItem;
import com.tul.vacha.semestralproject.app.dto.UserChangePasswordDTO;
import com.tul.vacha.semestralproject.app.entities.User;
import com.tul.vacha.semestralproject.utils.IOUtils;
import com.tul.vacha.semestralproject.utils.MenuUtils;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author pvacha
 */
public class ProfileView extends View {

    private final AuthService auth = AuthService.getInstance();
    private final ArrayList<MenuItem> menuItems = new ArrayList<>() {
        {
            add(new MenuItem("Změnit heslo", (d) -> changePassword()));
            add(new MenuItem("Export dat", (d) -> exportData()));
            add(new MenuItem("Přečtení dat", (d) -> readData()));

        }
    };

    private final Menu menu = new Menu(menuItems, true, false);

    @Override
    public void display() {
        IOUtils.clearConsole();
        System.out.println("Můj profil");
        System.out.println("=================");
        displayProfile();

        while (true) {
            MenuUtils.askForCommand(menu);
        }

    }

    private void exportData() {
        while (true) {

            System.out.println("Zadejte název souboru, do kterého chcete importovat:");
            String filename = IOUtils.readLine();

            try {
                if (!filename.endsWith(".dat")) {
                    filename += ".dat";
                }
                this.auth.exportData(filename);
                this.showMessage("Úspěšně jsme uložili soubor");
                return;
            } catch (IOException ex) {
                this.showMessage("Nastala chyba při práci se soubory, zkuste to znovu");
            }

        }
    }

    private void readData() {
        while (true) {

            System.out.println("Zadejte název souboru, ve kterém jsou uživatelská data");
            String filename = IOUtils.readLine();

            try {
                if (!filename.endsWith(".dat")) {
                    filename += ".dat";
                }

                String data = this.auth.readData(filename);
                this.showMessage(data);

                return;
            } catch (IOException ex) {
                this.showMessage("Nastala chyba při práci se soubory, zkuste to znovu");
            }

        }
    }

    private void changePassword() {
        System.out.println("Nové heslo: ");
        String password = IOUtils.readString();

        System.out.println("Nové heslo znovu: ");
        String passwordConfirm = IOUtils.readString();

        if (!password.equals(passwordConfirm)) {
            System.out.println("Hesla se neshodují");
            return;
        }

        try {

            auth.changePassword(new UserChangePasswordDTO(auth.getUser().getId(), password));
            IOUtils.clearConsole();
            this.showMessage("Úspěšně jsme vám změnili heslo");
            this.display();
        } catch (SQLException ex) {
            this.showMessage("Nepodařilo se nám změnit heslo");
        } catch (NoSuchAlgorithmException ex) {
            this.showMessage("Někde nastala chyba");
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
