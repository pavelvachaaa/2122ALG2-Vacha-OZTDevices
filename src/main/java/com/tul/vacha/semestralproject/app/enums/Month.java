/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.app.enums;

/**
 *
 * @author pvacha
 */
public enum Month {
    JANUARY(1, 31),
    FEBRUARY(2, 28),
    MARCH(3, 31),
    APRIL(4, 30),
    MAY(5, 31),
    JUNE(6, 30),
    JULY(7, 31),
    AUGUST(8, 31),
    SEPTEMBER(9, 30),
    OCTOBER(10, 31),
    NOVEMBER(11, 30),
    DECEMBER(12, 31);

    private final int monthNumber;
    private int daysInMonth;

    private Month(int monthNumber, int daysInMonth) {
        this.monthNumber = monthNumber;
        this.daysInMonth = daysInMonth;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public int getDaysInMonth() {
        return daysInMonth;
    }

    public static Month of(int monthNumber) {
        for (Month m : Month.values()) {
            if (m.getMonthNumber() == monthNumber) {
                return m;
            }
        }
        return null;
    }

    public static void setLeapYear() {
        Month.FEBRUARY.daysInMonth = 29;
    }

    public static void setNormalYear() {
        Month.FEBRUARY.daysInMonth = 28;
    }

    @Override
    public String toString() {
        return this.name();
    }

}
