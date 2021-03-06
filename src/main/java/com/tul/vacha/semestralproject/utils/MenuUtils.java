/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.utils;

import com.tul.vacha.semestralproject.app.core.navigation.Menu;
import com.tul.vacha.semestralproject.app.core.navigation.MenuItem;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

/**
 * 
 * @author pvacha
 */
public final class MenuUtils {

    private MenuUtils() {

    }

    public static boolean askForCommand(Menu menu) {
        boolean end = false;
        MenuItem item;
        while (!end) {

            System.out.println(menu.getMenu());
            System.out.printf("user@medicalapp:"); //Napodobenina shellu

            try {

                if (menu.isIsCMD()) {
                    String command = IOUtils.readLine();
                    command = command.replaceAll("\\s+", " ");
                    String[] tokens = command.split(" ");
                    item = menu.getItem(tokens[0]);

                    item.execute(command);
                } else {

                    int command = IOUtils.readInt();
                    item = menu.getItem(command - 1);
                    item.execute(command);

                }
                return true;
            } catch (InputMismatchException e) {
                System.out.println("Zadali jste neplatný formát");
            } catch (NoSuchElementException | NumberFormatException | IllegalStateException e) {
                System.out.println(e.getMessage());
            }

        }
        return false;
    }

}
