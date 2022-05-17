/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.ui.views;

import com.tul.vacha.semestralproject.app.core.Navigator;
import com.tul.vacha.semestralproject.app.core.View;
import com.tul.vacha.semestralproject.app.dto.UserLoginDTO;
import com.tul.vacha.semestralproject.app.dto.UserRegisterDTO;
import com.tul.vacha.semestralproject.app.services.AuthService;
import com.tul.vacha.semestralproject.utils.InputUtils;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pvacha
 */
public class RegisterView extends View {

    private final AuthService authService = AuthService.getInstance();

    @Override
    public void display() {
        register(this.askForCredentials());
    }

    private UserRegisterDTO askForCredentials() {
        String username = "";
        String password = "";
        String name = "";
        boolean end = false;

        while (!end && username.isBlank() && password.isBlank()) {
            try {
                System.out.println("Zadejte vaše celé jméno: ");
                name = InputUtils.readLine();

                System.out.println("Zadejte vaše uživ. jméno: ");
                username = InputUtils.readString();

                System.out.println("Zadejte vaše heslo: ");
                password = InputUtils.readString();

            } catch (Exception e) {
                InputUtils.clearConsole();
                System.out.println("Neplatný vstup. Zkuste to znovu!");
            }
        }

        return new UserRegisterDTO(name, username, password);

    }

    private void register(UserRegisterDTO registerDTO) {

        try {
            if (authService.register(registerDTO)) {
                InputUtils.clearConsole();
                this.showMessage("Úspěšně jsme vás registrovali!");
                Navigator.pushNamed("/mainMenu");
            } else {
                InputUtils.clearConsole();
                this.showMessage("Chybně zadané údaje, šéfe!");

                display();
            }
        } catch (SQLException e) {

            InputUtils.clearConsole();
            System.out.println("Někde nastala chyba. Opakujte váš pokus!");

            display();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Nepodporovaný algoritmus");
        } catch (IllegalArgumentException e) {
            System.out.println("Uživatel s tímto heslem již existuje.");
            // Repeat until asi???
            // Nebo clearnout a displayout znova register
        }

    }
}
