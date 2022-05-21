/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.ui.views;

import com.tul.vacha.semestralproject.app.core.navigation.Navigator;
import com.tul.vacha.semestralproject.app.core.View;
import com.tul.vacha.semestralproject.app.entities.Inspection;
import com.tul.vacha.semestralproject.app.entities.MedicalDevice;
import com.tul.vacha.semestralproject.app.services.AuthService;
import com.tul.vacha.semestralproject.app.services.InspectionCalendarService;
import com.tul.vacha.semestralproject.app.services.InspectionService;
import com.tul.vacha.semestralproject.app.services.MedicalDeviceService;
import com.tul.vacha.semestralproject.utils.IOUtils;
import java.sql.SQLException;
import java.util.NoSuchElementException;

// TODO: detail inspekce
// TODO: Mark as done
// TODO: Delete
// TODO: GOback
/**
 *
 * @author pvacha
 */
public class InspectionDetailView extends View {
    
    private final Inspection inspection;
    private final AuthService auth = AuthService.getInstance();
    private final MedicalDeviceService medicalService = new MedicalDeviceService();
    private final InspectionService inspService = new InspectionService();
    private final InspectionCalendarService calendarService;
    
    public InspectionDetailView(Inspection inspection, InspectionCalendarService service) {
        this.inspection = inspection;
        this.calendarService = service;
    }
    
    @Override
    public void display() {
        try {
            System.out.println("Detail inspekce");
            System.out.println("=====================");
            MedicalDevice device = medicalService.getDevice(inspection.getIdDevice());
            System.out.println(inspection.getDetail());
            System.out.printf("Zařízení k inspekci (ID: %s): %s\n", device.getId(), device.getName());
            System.out.println("  ");
            
            System.out.println("Dostupné příkazy: help,back, delete, done");
            
            System.out.printf("%s@ozt-app:", auth.getUser().getUsername());
            String command = IOUtils.readString();
            
            switch (command) {
                case "back" ->
                    Navigator.pop();
                case "delete" ->
                    this.delete();
                case "help" ->
                    System.out.println("Dostupné příkazy: help,back, delete,done");
                case "done" ->
                    this.markAsDone();
                default ->
                    System.out.println("Dostupné příkazy: help,back, delete, done");
                
            }
        } catch (SQLException e) {
            System.out.println("SQLEX" + e.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println("Nemohli jsme najít zařízení, sry");
        }
        
    }
    
    private void delete() throws SQLException {
        boolean x = inspService.delete(this.inspection.getId());
        if (x) {        // Přepsat samotřejme a vymslet něco intelignetnejšího na výpis
            calendarService.removeFromList(this.inspection.getId());
            this.showMessage("Úspěšně jsme odstranili tuto inspekci");
            Navigator.pop();
        } else {
            System.out.println("Někde nastala chyba, zkuste to znovu.");
            this.display();
        }
    }
    
    private void markAsDone() throws SQLException {
        boolean x = inspService.markAsDone(this.inspection.getId());

        // Přepsat samotřejme a vymslet něco intelignetnejšího na výpis
        if (x) {
            calendarService.removeFromList(this.inspection.getId());
            this.showMessage("Úspěšně jsme označili za hotovo tuto inspekci");
            Navigator.pop();
        } else {
            System.out.println("Někde nastala chyba, zkuste to znovu.");
            this.display();
        }
    }
    
}
