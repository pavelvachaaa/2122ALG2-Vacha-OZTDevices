/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.ui.views;

import com.tul.vacha.semestralproject.app.AuthService;
import com.tul.vacha.semestralproject.app.Navigator;
import com.tul.vacha.semestralproject.app.UserLoginDTO;
import com.tul.vacha.semestralproject.app.View;
import com.tul.vacha.semestralproject.utils.InputUtils;
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
                username = InputUtils.readString();

                System.out.println("Zadejte vaše heslo: ");
                password = InputUtils.readString();

            } catch (Exception e) {
                InputUtils.clearConsole();
                System.out.println("Neplatný vstup. Zkuste to znovu!");
            }
        }

        return new UserLoginDTO(username, password);

    }

    private void login(UserLoginDTO loginDTO) {

        try {
            if (authService.login(loginDTO)) {
                InputUtils.clearConsole();
                this.showMessage("Úspěšně jsme vás přihlásili!");
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
        }

    }

}
