/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.app.dto;

import java.util.Date;

/**
 *
 * @author pvacha
 */
public class InspectionAddDTO {

    private int id;
    private final int idDevice;
    private final int idInspection;
    private final String supplier;
    private final Date inspectionDate;

    public InspectionAddDTO(int idDevice, int idInspection, String supplier, Date inspectionDate) {
        this.idDevice = idDevice;
        this.idInspection = idInspection;
        this.supplier = supplier;
        this.inspectionDate = inspectionDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getIdDevice() {
        return idDevice;
    }

    public Date getInspectionDate() {
        return inspectionDate;
    }

    public int getIdInspection() {
        return idInspection;
    }

    public String getSupplier() {
        return supplier;
    }

}
