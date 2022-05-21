/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Function;

/**
 *
 * @author pvacha
 */
public final class IOUtils {

    private IOUtils() {
    }

    private static final String CHARSET_NAME = "UTF-8";
    private static final Locale LOCALE = new Locale("cs", "CZ");
    private static final SimpleDateFormat inDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat outDateFormat = new SimpleDateFormat("dd. MM. yyyy");

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
            int i = sc.nextInt();
            sc.nextLine();
            return i;
        } catch (InputMismatchException e) {
            String token = sc.next();
            throw new InputMismatchException("Nemohli jsme načíst integer");
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Nemohli jsme načíst integer");
        }

    }

    public static boolean askForYesNo(String prompt) {
        final String[] yesOption = {"a", "ano", "y", "yes"};
        final String[] noOption = {"n", "ne", "no"};

        while (true) {

            System.out.print(prompt + " [" + yesOption[1] + "/" + noOption[1] + "]: ");
            String option = sc.nextLine();
            if (Arrays.stream(yesOption).anyMatch(x -> x.equalsIgnoreCase(option))) {
                return true; // Hloupý if, protože else chci opakovat tohle dokola
            } else if (Arrays.stream(noOption).anyMatch(x -> x.equalsIgnoreCase(option))) {
                return false;
            }
        }
    }

    public static Date askForDate(String prompt) {
        String cindate;
        while (true) {

            System.out.printf("%s (dd/mm/yy):", prompt);
            cindate = sc.nextLine();
            if (null != cindate && cindate.trim().length() > 0) {
                try {
                    return inDateFormat.parse(cindate);
                } catch (ParseException ex) {
                    System.out.println("Chybně zadané datum, zkuste to znovu");
                }
            }
        }
    }

    /**
     * Generická metoda pro vyhledávání v listu
     *
     * @param <T>
     * @param prompt - Parametr pro výpis uživatelovi
     * @param searchPropertyAccessor - Metoda podle které se vyhledává
     * @param codeList - kde hledám
     * @return většínou idčko
     */
    public static <T> int selectFromCodeList(String prompt, Function<T, Integer> searchPropertyAccessor, List<T> codeList, boolean printList) {
        if (printList) {
            clearConsole();
        }

        String msg = "";
        while (true) {
            if (printList) {
                for (var item : codeList) {
                    System.out.println(item);
                }
            }
            System.out.println(msg);
            System.out.println(prompt);

            try {

                int choice = readInt();
                if (codeList.stream().anyMatch(x -> Objects.equals(searchPropertyAccessor.apply(x), choice))) {
                    return choice;
                } else {
                    if (printList) {
                        clearConsole();
                    }
                    msg = "Tato položka není v seznamu. Zkuste to znova!";
                }
            } catch (InputMismatchException e) {
                if (printList) {
                    clearConsole();
                }
                msg = "Měl jsi zadat číslo, víš to, viď?";

            }

        }

    }

    public static String getFormattedDate(Date d) {
        return outDateFormat.format(d);
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
        IOUtils.sc = sc;
        IOUtils.sc.useLocale(LOCALE);
    }

    /*  public static void main(String[] args) {
        System.out.println("Číslo:");
        int x = readInt();

        System.out.println("Stringeríno:");
        String test = readString();

        Date d = IOUtils.askForDate("Zadejte datum: ");

        System.out.println(getFormattedDate(d));
    }
     */
}
