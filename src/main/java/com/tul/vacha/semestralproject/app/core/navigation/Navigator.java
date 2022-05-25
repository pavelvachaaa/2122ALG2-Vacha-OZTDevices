/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.app.core.navigation;

import com.tul.vacha.semestralproject.app.core.App;
import com.tul.vacha.semestralproject.app.core.View;
import com.tul.vacha.semestralproject.utils.IOUtils;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Třída, která se stará o "routování" v aplikaci
 *
 * @author pvacha
 */
public final class Navigator {

    // Zásobník, který si ukládá aktuální pohledy
    private static final Stack<View> stack = new Stack<>();

    private Navigator() {
    }

    /**
     * Vezme string, podívá se do mapy s cestama a podle toho ji zobrazí
     *
     * @param route
     */
    public static void pushNamed(String route) {
        try {
            push(App.getView(route));
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Pop
     *
     * @return vrací true, když se může uživatel ještě vrátit, false otherwises
     */
    public static boolean canPop() {
        return stack.size() > 1;
    }

    /**
     * Například přechod z loginu na mainmenu Vymaže, ze zásobníku všechny cesty
     * a zobrazí požadovanou -> nelze se vrátit
     *
     * @param route
     */
    public static void pushReplacementNamed(String route) {
        stack.clear();
        pushNamed(route);
    }

    /**
     * Pushne libovolný view, který dědí z View
     *
     * @param view
     */
    public static void push(View view) {
        stack.push(view);
        view.display();
    }

    public static void pushReplacement(View view) {
        stack.clear();
        push(view);
    }

    /**
     * Pokusí se vrátít o jeden pohled zpět
     */
    public static void pop() {
        // Možná to hodit příznakově
        IOUtils.clearConsole();

        if (!stack.isEmpty()) {
            stack.pop();
            stack.lastElement().display();
        } else {
            throw new IllegalStateException("Už nemám kam dál popnout, kamaráde");
        }
    }

}
