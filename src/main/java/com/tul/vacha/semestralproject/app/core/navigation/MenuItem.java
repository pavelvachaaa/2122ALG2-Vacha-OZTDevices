/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.app.core.navigation;

import java.util.function.Consumer;

/**
 *
 * @author pvacha
 */
public class MenuItem<T> {

    private final String label;
    private final String command;
    private final Consumer<T> action;
    private final boolean restricted; // admin restrikce

    public MenuItem(String label, Consumer<T> action, boolean restricted) {
        this(label, "", action, restricted);

    }

    public MenuItem(String label, Consumer<T> action) {
        this(label, "", action, false);
    }

    public MenuItem(String label, String command, Consumer<T> action) {
        this(label, command, action, false);
    }

    public String getCommand() {
        return command;
    }

    public boolean isRestricted() {
        return restricted;
    }

    public MenuItem(String label, String command, Consumer<T> action, boolean restricted) {
        this.label = label;
        this.command = command;
        this.action = action;
        this.restricted = restricted;
    }

    public void execute(T t) {
        this.action.accept(t);
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return this.label;
    }

}
