/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.app.core.navigation;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 *
 * @author pvacha
 */
public class Menu {

    private final boolean isCMD;
    private final ArrayList<MenuItem> menu;
    private final MenuItem back = new MenuItem("Zpět", "back", (d) -> Navigator.pop());
    private final MenuItem help = new MenuItem("Nápověda", "help", (d) -> System.out.println(this.getInlineMenu()));

    public Menu() {
        this(new ArrayList<MenuItem>(), false, false);
    }

    public Menu(ArrayList<MenuItem> menu) {
        this(menu, false, false);
    }

    public Menu(ArrayList<MenuItem> menu, boolean showBackItem) {
        this(menu, true, true);
    }

    public Menu(ArrayList<MenuItem> menu, boolean showBackItem, boolean isCMD) {
        this.menu = new ArrayList<>(menu);
        this.isCMD = isCMD;
        if (showBackItem) {
            this.menu.add(back);
        }
        if(isCMD) {
            this.menu.add(help);
        }
    }

    private String getMenuInRows() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < menu.size(); i++) {
            sb.append(String.format("%d. %s\n", (i + 1), menu.get(i).getLabel()));
        }

        return sb.toString();
    }

    private String getInlineMenu() {

        StringBuilder sb = new StringBuilder();
        sb.append("Dostupné příkazy: ");
        for (int i = 0; i < menu.size(); i++) {
            sb.append(String.format("%s,", menu.get(i).getCommand()));
        }

        String inlineMenu = sb.toString();
        return inlineMenu.substring(0, inlineMenu.length() - 1);
    }

    public String getMenu() {
        if (menu.size() < 1) {
            return "";
        }
        return this.isCMD ? getInlineMenu() : getMenuInRows();
    }

    public MenuItem getItem(String command) {
        return this.menu.stream().filter(i -> i.getCommand().toLowerCase().equals(command.toLowerCase())).findFirst().orElseThrow(() -> new NoSuchElementException("Tato položka v menu není"));
    }

    public MenuItem getItem(int command) {
        if (command >= 0 && command < menu.size()) {
            return menu.get(command);
        }

        throw new NoSuchElementException("Tato položka v menu není");
    }

    public boolean isIsCMD() {
        return isCMD;
    }

}
