/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.app.entities;

/**
 *
 * @author pvacha
 */
public class CodeListItem {

    private int id;
    private String label;
    private String code;

    public CodeListItem(int id, String label, String code) {
        this.id = id;
        this.label = label;
        this.code = code;
    }

    public CodeListItem() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.format("%s %s", this.id, this.label);
    }

}
