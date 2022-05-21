/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.utils;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 *
 * @author pvacha
 */
public final class CommonUtils {

    private CommonUtils() {
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    
    public static <T> boolean idInList(int id, Function<T, Integer> searchPropertyAccessor, List<T> list ) {
        return list.stream().anyMatch(x -> Objects.equals(searchPropertyAccessor.apply(x), id));
    }
}
