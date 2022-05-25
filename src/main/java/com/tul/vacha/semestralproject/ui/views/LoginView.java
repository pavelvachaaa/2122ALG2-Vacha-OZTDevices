/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.ui.views;

import com.tul.vacha.semestralproject.app.services.AuthService;
import com.tul.vacha.semestralproject.app.core.navigation.Navigator;
import com.tul.vacha.semestralproject.app.dto.UserLoginDTO;
import com.tul.vacha.semestralproject.app.core.View;
import com.tul.vacha.semestralproject.utils.IOUtils;
import java.sql.SQLException;

/**
 *
 * @author pvacha
 */
public class LoginView extends View {

    private final AuthService authService = AuthService.getInstance();

    @Override
    public void display() {
        this.login(this.askForCredentials());
    }

    private UserLoginDTO askForCredentials() {
        String username = "";
        String password = "";

        boolean end = false;

        while (!end && username.isBlank() && password.isBlank()) {
            try {

                System.out.println("Zadejte vaše uživ. jméno: ");
                username = IOUtils.readString();

                System.out.println("Zadejte vaše heslo: ");
                password = IOUtils.readString();

            } catch (Exception e) {
                IOUtils.clearConsole();
                this.showMessage("Neplatný vstup. Zkuste to znovu!");
            }
        }

        return new UserLoginDTO(username, password);

    }
    
    private void login(UserLoginDTO loginDTO) {

        try {
            if (authService.login(loginDTO)) {
                IOUtils.clearConsole();
                this.showMessage("Úspěšně jsme vás přihlásili!");
                Navigator.pushNamed("/mainMenu");
            } else {
                IOUtils.clearConsole();
                this.showMessage("Chybně zadané údaje, šéfe!");

                display();
            }
        } catch (SQLException e) {

            IOUtils.clearConsole();
            System.out.println("Někde nastala chyba. Opakujte váš pokus!");

            display();
        }

    }
    
}
