/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.ui;

import com.tul.vacha.semestralproject.app.repositories.user.UserRepository;
import com.tul.vacha.semestralproject.entities.User;
import com.tul.vacha.semestralproject.utils.AuthUtils;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import javax.naming.NamingException;
import com.tul.vacha.semestralproject.utils.dbutils.Database;
import com.tul.vacha.semestralproject.utils.dbutils.ResultSetPropertiesSimplifyHelps;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author pvacha
 */
public class App {

    public static void main(String[] args) {
        System.out.println("Ahoj světe");
        Database db;
        try ( InputStream input = App.class.getClassLoader().getResourceAsStream("db.properties")) {

            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find db.properties");
                return;
            }

            prop.load(input);

            db = new Database(prop.getProperty("mysql.url"), prop.getProperty("mysql.username"), prop.getProperty("mysql.password"));
        } catch (IOException ex) {
            ex.printStackTrace();
            db = null;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            db = null;
        }

        try {
            System.out.println(AuthUtils.getHash("gpasdklfpas"));

            User user = new UserRepository(db).getUserById(2);
           
            System.out.println(new UserRepository(db).getUserById(2));

            if (user == null) {
                System.out.println("Nemohli jsme najít uživatele");
            } else {
                System.out.println(user);
            }

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Nemohli jsme zahashovat heslo");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

}
