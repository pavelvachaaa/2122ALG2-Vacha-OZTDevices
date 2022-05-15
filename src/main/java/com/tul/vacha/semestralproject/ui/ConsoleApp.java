/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.ui;

import com.tul.vacha.semestralproject.app.App;
import com.tul.vacha.semestralproject.app.AuthService;
import com.tul.vacha.semestralproject.app.Navigator;
import com.tul.vacha.semestralproject.app.View;

import com.tul.vacha.semestralproject.app.repositories.user.UserRepository;
import com.tul.vacha.semestralproject.ui.views.WelcomeMenuView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import com.tul.vacha.semestralproject.utils.dbutils.Database;

import java.sql.SQLException;
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
