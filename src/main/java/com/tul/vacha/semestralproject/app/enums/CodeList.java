/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.app.enums;

/**
 * Standartizované  číselníky
 * @author pvacha
 */
public enum CodeList {
    MOBILITY("ozt_device_mobility"),
    DEVICE_TYPE("ozt_device_type"),
    CPV_DEVICE_TYPE("ozt_device_cpvType"),
    INSPECTION_TYPE("ozt_inspection");

    private final String label;

    private CodeList(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

};
