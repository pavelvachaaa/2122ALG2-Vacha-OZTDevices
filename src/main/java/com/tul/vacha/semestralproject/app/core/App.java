/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.app.core;

import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Hlavní bod aplikace
 *
 * @author pvacha
 */
public abstract class App {

    protected final String defaultRoute;
    protected static Map<String, View> routes;

    protected App(String defaultRoute, Map<String, View> routes) {
        App.routes = Map.copyOf(routes);
        this.defaultRoute = defaultRoute;
    }

    public String getDefaultRoute() {
        return defaultRoute;
    }

    public abstract void start();

    public static View getView(String route) {
        if (!routes.containsKey(route)) {
            throw new NoSuchElementException("Nemohli jsme najít požadovaný pohled");
        }

        return routes.get(route);
    }

}
