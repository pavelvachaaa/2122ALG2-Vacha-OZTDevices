/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.app;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Vymyslet kam to umístit 
 * Třída, co se stará o veškeré routování v aplikaci
 *
 * @author pvacha
 */
public final class Navigator {

    private static final Stack<View> stack = new Stack<>();

    private Navigator() {
    }

    public static void pushNamed(String route) {
        try {
            push(App.getView(route));
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void pushReplacementNamed(String route) {
        stack.clear();
        pushNamed(route);
    }

    public static void push(View view) {
        stack.push(view);
        view.display();
    }

    public static void pushReplacement(View view) {
        stack.clear();
        push(view);
    }

    public static void pop() {
        if (!stack.isEmpty()) {
            stack.pop().display();
        } else {
            throw new IllegalStateException("Už nemám kam dál popnout, kamaráde");
        }
    }

}
