/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.app.dto;

import com.tul.vacha.semestralproject.app.entities.Inspection;
import com.tul.vacha.semestralproject.app.entities.MedicalDevice;
import java.util.ArrayList;

/**
 *
 * @author pvacha
 */
public class MedicalItemDTO {

    private final MedicalDevice device;
    private final ArrayList<Inspection> inspections;

    public MedicalItemDTO(MedicalDevice device, ArrayList<Inspection> inspections) {
        this.device = device;
        this.inspections = inspections;
    }

    public MedicalDevice getDevice() {
        return device;
    }

    public ArrayList<Inspection> getInspections() {
        return inspections;
    }

}
