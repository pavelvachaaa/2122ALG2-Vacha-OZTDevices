/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.app.entities;

import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 *
 * @author pvacha
 */
public class MedicalDevice implements Comparable<MedicalDevice> {

    private int id;

    private String internalRegistrationNumber;
    private String name;
    private boolean isElectric;
    private Date warrantyTo;
    private Date expirationDate;
    private String deviceType;
    private String mobility;
    private String cpvDeviceType;
    private String description;

    public MedicalDevice() {
    }

    public MedicalDevice(MedicalDevice d) {
        this.id = d.id;
        this.internalRegistrationNumber = d.internalRegistrationNumber;
        this.name = d.name;
        this.isElectric = d.isElectric;
        this.warrantyTo = d.warrantyTo;
        this.expirationDate = d.expirationDate;
        this.deviceType = d.deviceType;
        this.mobility = d.mobility;
        this.cpvDeviceType = d.cpvDeviceType;
        this.description = d.description;
    }

    public MedicalDevice(int id, String internalRegistrationNumber, String name, boolean isElectric, Date warrantyTo, Date expirationDate, String deviceType, String mobility, String cpvDeviceType, String description) {
        this.id = id;
        this.internalRegistrationNumber = internalRegistrationNumber;
        this.name = name;
        this.isElectric = isElectric;
        this.warrantyTo = warrantyTo;
        this.expirationDate = expirationDate;
        this.deviceType = deviceType;
        this.mobility = mobility;
        this.cpvDeviceType = cpvDeviceType;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInternalRegistrationNumber(String internalRegistrationNumber) {
        this.internalRegistrationNumber = internalRegistrationNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIsElectric(boolean isElectric) {
        this.isElectric = isElectric;
    }

    public void setWarrantyTo(Date warrantyTo) {
        this.warrantyTo = warrantyTo;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public void setMobility(String mobility) {
        this.mobility = mobility;
    }

    public void setCpvDeviceType(String cpvDeviceType) {
        this.cpvDeviceType = cpvDeviceType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
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

    public String getDeviceType() {
        return deviceType;
    }

    public String getMobility() {
        return mobility;
    }

    public String getCpvDeviceType() {
        return cpvDeviceType;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getLocalDate(Date date) {
        Date d = new java.util.Date();
        d.setTime(date.getTime()); // Java je retardovaná a nemůžu použít na Date toInstant, když je to SQL DATE :)
        return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Override
    public String toString() {
        return String.format("%-5s %-20s %-50s %-10s\n", this.id, this.internalRegistrationNumber, this.name, this.cpvDeviceType);

    }

    @Override
    public int compareTo(MedicalDevice o) {
        return this.name.compareTo(o.name);
    }

}
