/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.ui.views;

import com.tul.vacha.semestralproject.app.core.navigation.Navigator;
import com.tul.vacha.semestralproject.app.core.View;
import com.tul.vacha.semestralproject.app.core.navigation.Menu;
import com.tul.vacha.semestralproject.app.core.navigation.MenuItem;
import com.tul.vacha.semestralproject.app.entities.Inspection;
import com.tul.vacha.semestralproject.app.entities.MedicalDevice;
import com.tul.vacha.semestralproject.app.services.AuthService;
import com.tul.vacha.semestralproject.app.services.InspectionCalendarService;
import com.tul.vacha.semestralproject.app.services.InspectionService;
import com.tul.vacha.semestralproject.app.services.MedicalDeviceService;
import com.tul.vacha.semestralproject.utils.IOUtils;
import com.tul.vacha.semestralproject.utils.MenuUtils;
import java.sql.SQLException;
import java.util.ArrayList;

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
    private final Menu menu;

    public InspectionDetailView(Inspection inspection, InspectionCalendarService service) {
        this.inspection = inspection;
        this.calendarService = service;

        this.menu = new Menu(new ArrayList<>() {
            {
                add(new MenuItem("Smazat položku", "delete", (d) -> {
                    try {
                        delete();
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }, true));
                add(new MenuItem("Označit za provedeno", "done", (command) -> {
                    try {
                        markAsDone();
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }, true));

            }
        }, true, true);
    }

    @Override
    public void display() {
        IOUtils.clearConsole();

        System.out.println("Detail inspekce");
        System.out.println("=====================");

        try {

            MedicalDevice device = medicalService.getDevice(inspection.getIdDevice());

            System.out.println(inspection.getDetail());
            System.out.printf("Zařízení k inspekci (ID: %s): %s\n", device.getId(), device.getName());
            System.out.println("  ");

            while (true) {
                MenuUtils.askForCommand(menu);
            }

        } catch (SQLException ex) {
            this.showMessage("Někde nastala chyba, zkuste to znovu.");
        }

    }

    private void delete() throws SQLException {
        boolean x = inspService.delete(this.inspection.getId());
        if (x) {
            calendarService.removeFromList(this.inspection.getId());
            this.showMessage("Úspěšně jsme odstranili tuto inspekci");
            Navigator.pop();
        } else {
            this.showMessage("Někde nastala chyba, zkuste to znovu.");
            this.display();
        }
    }

    private void markAsDone() throws SQLException {
        boolean x = inspService.markAsDone(this.inspection.getId());

        if (x) {
            calendarService.removeFromList(this.inspection.getId());
            this.showMessage("Úspěšně jsme označili za hotovo tuto inspekci");
            Navigator.pop();
        } else {
            this.showMessage("Někde nastala chyba, zkuste to znovu.");
            this.display();
        }
    }

}
