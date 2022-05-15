/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author pvacha
 */
public final class AuthUtils {

    private AuthUtils() {
    }

    /**
     * Převede výstup hashovacího algoritmu na String
     * @param hash
     * @return hash
     */
    public static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
    
    /**
     * Zahashuje heslo pomocí SHA-256
     * @param phrase
     * @return hashed phrase
     * @throws NoSuchAlgorithmException 
     */
    public static String getHash(String phrase) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return bytesToHex(digest.digest(phrase.getBytes(StandardCharsets.UTF_8)));
    }

}
