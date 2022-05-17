/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.ui;

import com.tul.vacha.semestralproject.app.core.App;
import com.tul.vacha.semestralproject.app.core.Navigator;
import com.tul.vacha.semestralproject.app.core.View;
import java.util.Map;

/**
 *
 * @author pvacha
 */
public class ConsoleApp extends App {

    public ConsoleApp(String defaultRoute, Map<String, View> routes) {
        super(defaultRoute, routes);
    }

    @Override
    public void start() {
        Navigator.pushNamed(this.defaultRoute);
    }

}
