/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.app.entities;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author pvacha
 */
public class Inspection implements Comparable<Inspection> {

    private int id;
    private int idDevice;
    private String label;
    private String supplier;
    private Date inspectionDate;

    public Inspection(int id, String label, String supplier, Date inspectionDate, int idDevice) {
        this.id = id;
        this.label = label;
        this.supplier = supplier;
        this.idDevice = idDevice;
        this.inspectionDate = inspectionDate;
    }

    public Inspection() {

    }

    public LocalDate getLocalDate() {
        Date d = new Date();
        d.setTime(this.inspectionDate.getTime()); // Java je retardovaná a nemůžu použít na Date toInstant, když je to SQL DATE :)
        return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    // Hodit do utils
    public LocalDate getLocalDate(Date date) {
        Date d = new Date();
        d.setTime(date.getTime()); // Java je retardovaná a nemůžu použít na Date toInstant, když je to SQL DATE :)
        return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public void setIdDevice(int idDevice) {
        this.idDevice = idDevice;
    }

    public int getIdDevice() {
        return idDevice;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getSupplier() {
        return supplier;
    }

    public Date getInspectionDate() {
        return inspectionDate;
    }

    public String getDetail() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Společnost %s bude provádět %s k datu %s", this.supplier, this.label, this.getLocalDate(this.inspectionDate).toString()));

        return sb.toString();
    }

    @Override
    public int compareTo(Inspection o) {
        Long number1 = this.inspectionDate.getTime();
        Long number2 = o.getInspectionDate().getTime();

        return number1.compareTo(number2);
    }

}
