/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.ui.views;

import com.tul.vacha.semestralproject.app.services.AuthService;
import com.tul.vacha.semestralproject.app.core.Navigator;
import com.tul.vacha.semestralproject.app.core.View;
import com.tul.vacha.semestralproject.app.dto.UserChangePasswordDTO;
import com.tul.vacha.semestralproject.app.entities.User;
import com.tul.vacha.semestralproject.utils.InputUtils;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pvacha
 */
public class ProfileView extends View {

    private final AuthService auth = AuthService.getInstance();

    @Override
    public void display() {
        InputUtils.clearConsole();
        System.out.println("Můj profil");
        System.out.println("=================");
        displayProfile();

        // Už je fakt třeba tohle zautomatizovat nebo udělat cmd class
        // Každý view by mohlo mít v abstraktní třídě menu -> na základě toho 
        // invokovat askCommand -> zde by se dalo definovat menu + runnable
        System.out.println("1. Změnit heslo");
        System.out.println("2. Smazat účet");
        System.out.println("3. Zpět");

        int choice = InputUtils.readInt();

        switch (choice) {
            case 1 ->
                changePassword();
            case 2 ->
                Navigator.pop();
            case 3 ->
                Navigator.pop();

            default -> {
            }
        }

        System.out.println("  ");

        System.out.printf("%s@ozt-app:", auth.getUser().getUsername());
        Navigator.execute(InputUtils.readString());
    }

    private void changePassword() {
        System.out.println("Nové heslo: ");
        String password = InputUtils.readString();

        System.out.println("Nové heslo znovu: ");
        String passwordConfirm = InputUtils.readString();

        try {
            // Repeat until ty dvě hesla nejsou stejné
            // udělat z toho hvězdičky...
            auth.changePassword(new UserChangePasswordDTO(auth.getUser().getId(), password));
            InputUtils.clearConsole();
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
