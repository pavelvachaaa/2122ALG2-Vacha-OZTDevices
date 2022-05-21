/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.app.core;

import com.tul.vacha.semestralproject.app.enums.Month;
import com.tul.vacha.semestralproject.app.enums.WeekDays;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

// Vymyslet kam s tím
/**
 *
 * @author pvacha
 */
public class InspectionCalendar {

    private int day;
    private Month month;
    private int year;
    private final int createdDay;
    private final Month createdMonth;
    private final int createdYear;
    //private static String[] daysInWeek = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
    //private static String[] months = {"", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    //private int[] nDaysInMonth = {0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private InspectionCalendar(int day, int month, int year) {
        checkDate(day, month, year);
        this.day = day;
        this.month = Month.of(month);
        setYear(year);

        this.createdDay = day;
        this.createdMonth = Month.of(month);
        this.createdYear = year;
    }

    private void checkDate(int day, int month, int year) {
        if (day > Month.of(month).getDaysInMonth() || day < 1 || month < 1 || month > 12) {
            throw new IllegalArgumentException("Neplatne datum");
        } else if (month == 2 && isLeapYear(year) && day > 29) {
            throw new IllegalArgumentException("Neplatne datum");
        } else if (month == 2 && !isLeapYear(year) && day > 28) {
            throw new IllegalArgumentException("Neplatne datum");
        }
    }

    private void setYear(int year) {
        this.year = year;
        if (isLeap()) {
            Month.setLeapYear();
        } else {
            Month.setNormalYear();
        }
    }

    public static InspectionCalendar getInstance(int day, int month, int year) {
        return new InspectionCalendar(day, month, year);
    }

    public static InspectionCalendar getInstance() {
        LocalDate localDate = now();
        return new InspectionCalendar(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
    }

    public static LocalDate now() {
        return new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    }

    public void previousMonth() {
        if (month == Month.JANUARY) {
            month = Month.DECEMBER;
            setYear(year - 1);
        } else {
            month = Month.of(month.getMonthNumber() - 1);
        }
    }

    public void nextMonth() {
        if (month == Month.of(12)) {
            month = Month.of(1);
            setYear(year + 1);
        } else {
            month = Month.of(month.getMonthNumber() + 1);
        }
    }

    public void today() {
        day = createdDay;
        month = createdMonth;
        setYear(createdYear);
    }

    //Zeller
    public static int getDayInWeek(int day, int month, int year) {
        if (month == 1 || month == 2) {
            month += 12;
            year--;
        }
        int k = year % 100;
        int j = (int) (year / 100.0);
        int q = day;
        int m = month;
        int h = (q + 13 * (m + 1) / 5 + k + k / 4 + j / 4 + 5 * j) % 7;
        int d = ((h + 5) % 7);

        return d;

    }

    public int getDayInWeekForFirst() {
        return getDayInWeek(1, month.getMonthNumber(), year);
    }

    public static boolean isLeapYear(int year) {
        return year % 4 == 0; //there are more conditions
    }

    private boolean isLeap() {
        return isLeapYear(year);
    }

    public Month getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getYear() {
        return year;
    }

}
