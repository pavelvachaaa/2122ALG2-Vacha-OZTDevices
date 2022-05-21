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
public class MedicalItemAddDTO {

    private final String internalRegistrationNumber;
    private final String name;
    private final boolean isElectric;
    private final Date warrantyTo;
    private final Date expirationDate;
    private final int deviceType;
    private final int mobility;
    private final int cpvDeviceType;

    public MedicalItemAddDTO(String name, String internalRegistrationNumber, boolean isElectric, Date warrantyTo, Date expirationDate, int deviceType, int mobility, int cpvDeviceType) {
        this.internalRegistrationNumber = internalRegistrationNumber;
        this.name = name;
        this.isElectric = isElectric;
        this.warrantyTo = warrantyTo;
        this.expirationDate = expirationDate;
        this.deviceType = deviceType;
        this.mobility = mobility;
        this.cpvDeviceType = cpvDeviceType;
    }

    public String getInternalRegistrationNumber() {
        return internalRegistrationNumber;
    }

    public String getName() {
        return name;
    }

    public boolean isIsElectric() {
        return isElectric;
    }

    public Date getWarrantyTo() {
        return warrantyTo;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public int getMobility() {
        return mobility;
    }

    public int getCpvDeviceType() {
        return cpvDeviceType;
    }

}
