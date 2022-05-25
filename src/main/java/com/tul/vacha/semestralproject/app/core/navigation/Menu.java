/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.app.core.navigation;

import com.tul.vacha.semestralproject.app.services.AuthService;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Třída, obstarávající menu i s autorizací
 *
 * @author pvacha
 */
public class Menu {

    private final boolean isCMD;
    private final ArrayList<MenuItem> menu;
    private final MenuItem back = new MenuItem("Zpět", "back", (d) -> Navigator.pop());

    // Příjde mi, že je to příliš dependency coupled..
    private final AuthService auth = AuthService.getInstance();

    public Menu() {
        this(new ArrayList<MenuItem>(), false, false);
    }

    public Menu(ArrayList<MenuItem> menu) {
        this(menu, false, false);
    }

    public Menu(ArrayList<MenuItem> menu, boolean showBackItem) {
        this(menu, true, true);
    }

    public boolean isIsCMD() {
        return isCMD;
    }

    public Menu(ArrayList<MenuItem> menu, boolean showBackItem, boolean isCMD) {
        this.menu = new ArrayList<>(menu);
        this.isCMD = isCMD;
        if (showBackItem) {
            this.menu.add(back);
        }

    }

    /**
     * Vytvoří menu v řádcích
     *
     * @return stringové menu, ve kterém se vybírá pomocí čísel
     */
    private String getMenuInRows() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < menu.size(); i++) {
            if (menu.get(i).isRestricted() && !auth.canAccess(true)) {
                continue;
            }

            sb.append(String.format("%d. %s\n", (i + 1), menu.get(i).getLabel()));
        }

        return sb.toString();
    }

    /**
     * Vytváří menu jako cmd
     *
     * @return stringovou reprezentaci menu, který vypadá jak cmd
     */
    private String getInlineMenu() {

        StringBuilder sb = new StringBuilder();
        sb.append("Dostupné příkazy: ");
        for (int i = 0; i < menu.size(); i++) {
            if (menu.get(i).isRestricted() && !auth.canAccess(true)) {
                continue;
            }
            sb.append(String.format("%s,", menu.get(i).getCommand()));

        }

        String inlineMenu = sb.toString();
        return inlineMenu.substring(0, inlineMenu.length() - 1);
    }

    /**
     *  
     * Stringová reprzentace menu
     *
     * @return Vrací jedno z meníček na základě příznaku
     */
    public String getMenu() {
        if (menu.size() < 1) {
            return "";
        }
        return this.isCMD ? getInlineMenu() : getMenuInRows();
    }

    public MenuItem getItem(String command) {

        MenuItem item = this.menu.stream().filter(i -> i.getCommand().toLowerCase().equals(command.toLowerCase())).findFirst().orElseThrow(() -> new NoSuchElementException("Tato položka v menu není"));
        if (item.isRestricted() && !auth.canAccess(true)) {
            throw new IllegalStateException("Nejste oprávněn k tomu dělat tuto akci.");
        }

        return item;

    }

    public MenuItem getItem(int command) {

        if (command >= 0 && command < menu.size()) {

            MenuItem item = menu.get(command);
            if (item.isRestricted() && !auth.canAccess(true)) {
                throw new IllegalStateException("Nejste oprávněn k tomu dělat tuto akci.");
            }
            return item;
        }

        throw new NoSuchElementException("Tato položka v menu není");
    }

}
