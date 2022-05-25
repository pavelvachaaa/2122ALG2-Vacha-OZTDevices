/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.app.core;

/**
 * Abstraktní třída pro UI aplikace
 *
 * @author pvacha
 */
public abstract class View {

    public abstract void display();

    public void showMessage(String msg) {
        System.out.println(msg);
    }

}
