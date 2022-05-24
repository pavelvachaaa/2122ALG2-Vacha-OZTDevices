package com.tul.vacha.semestralproject.ui.views;

import com.tul.vacha.semestralproject.app.core.View;
import com.tul.vacha.semestralproject.app.core.navigation.Menu;
import com.tul.vacha.semestralproject.app.dto.MedicalItemDTO;
import com.tul.vacha.semestralproject.app.entities.Inspection;
import com.tul.vacha.semestralproject.app.entities.MedicalDevice;
import com.tul.vacha.semestralproject.app.services.AuthService;
import com.tul.vacha.semestralproject.app.services.InspectionService;
import com.tul.vacha.semestralproject.app.services.MedicalDeviceService;
import com.tul.vacha.semestralproject.utils.IOUtils;
import com.tul.vacha.semestralproject.utils.MenuUtils;
import java.util.ArrayList;
import java.util.Collections;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author pvacha
 */
public class MedicalItemDetailView extends View {

    private final MedicalItemDTO medicalItem;

    public MedicalItemDetailView(MedicalItemDTO medicalItem) {
        this.medicalItem = new MedicalItemDTO(medicalItem);
    }

    @Override
    public void display() {
        IOUtils.clearConsole();
        System.out.printf("Detail zařízení (ID: %d)\n", this.medicalItem.getDevice().getId());
        System.out.println("=========================================");
        printItemDetail();
        System.out.println(" ");
        System.out.printf("Nejbližší inspekce (Celkový počet insp. %d)\n", this.medicalItem.getInspections().size());
        System.out.println("==========================================");
        printClosestInspection();

        System.out.println("  ");

        while (true) {
            MenuUtils.askForCommand(new Menu(new ArrayList<>(), true, true));
        }
    }

    // asi nějaký defenzivní kopie dodělat
    private void printClosestInspection() {
        ArrayList<Inspection> inspections = this.medicalItem.getInspections();
        Collections.sort(inspections);

        for (Inspection inspection : inspections) {
            System.out.println(inspection.getDetail());
        }
    }

    private void printItemDetail() {
        MedicalDevice device = this.medicalItem.getDevice();
        StringBuilder sb = new StringBuilder();

        sb.append("Název: ").append(device.getName());
        sb.append(System.lineSeparator());
        sb.append("Interní číslo: ").append(device.getInternalRegistrationNumber());
        sb.append(System.lineSeparator());
        sb.append("Typ zařízení: ").append(device.getDeviceType());
        sb.append(System.lineSeparator());
        sb.append("CPV typ: ").append(device.getCpvDeviceType());
        sb.append(System.lineSeparator());
        sb.append("Mobilita: ").append(device.getMobility());
        sb.append(System.lineSeparator());
        sb.append("Interní číslo: ").append(device.getInternalRegistrationNumber());
        sb.append(System.lineSeparator());
        sb.append("El. zařízení: ").append(device.isIsElectric());
        sb.append(System.lineSeparator());
        sb.append("Záruka do: ").append(IOUtils.getFormattedDate(device.getWarrantyTo()));
        sb.append(System.lineSeparator());
        sb.append("Expirace: ").append(IOUtils.getFormattedDate(device.getExpirationDate()));
        sb.append(System.lineSeparator());
        sb.append("Poznámka: ").append(device.getDescription());
        sb.append(System.lineSeparator());

        System.out.println(sb.toString());

    }

}
