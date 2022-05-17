/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.utils;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 * @author pvacha
 */
public final class InputUtils {

    private InputUtils() {
    }

    private static final String CHARSET_NAME = "UTF-8";
    private static final Locale LOCALE = new Locale("cs", "CZ");
    private static Scanner sc;

    // Fakové clearování pro účely IDEčka
    public static void clearConsole() {
        System.out.println("\n".repeat(10));
    }
    
    public static boolean hasNextLine() {
        return sc.hasNextLine();
    }

    public static boolean isEmpty() {
        return !sc.hasNext();
    }

    public static String readString() {
        try {
            return sc.next();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Nemohli jsme načíst vstup!");
        }
    }

    public static double readDouble() {
        try {
            return sc.nextDouble();
        } catch (InputMismatchException e) {
            String token = sc.next();
            throw new InputMismatchException("Nemohli jsme načíst vstup");
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Nemohli jsme načíst vstup");
        }
    }

    public static String readLine() {
        String line;
        try {
            line = sc.nextLine();
        } catch (NoSuchElementException e) {
            line = null;
        }
        return line;
    }

    public static int readInt() {
        try {
            return sc.nextInt();
        } catch (InputMismatchException e) {
            String token = sc.next();
            throw new InputMismatchException("Nemohli jsme načíst integer");
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Nemohli jsme načíst integer");
        }

    }

    // Když se InputUtils incializují
    static {
        resync();
    }

    /**
     * Když se změní InputUtils, udělat znovu inicialici
     */
    private static void resync() {
        setScanner(new Scanner(new java.io.BufferedInputStream(System.in), CHARSET_NAME));
    }

    private static void setScanner(Scanner sc) {
        InputUtils.sc = sc;
        InputUtils.sc.useLocale(LOCALE);
    }
}
