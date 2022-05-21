/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.ui.views;

import com.tul.vacha.semestralproject.app.core.InspectionCalendar;
import com.tul.vacha.semestralproject.app.core.navigation.Navigator;
import com.tul.vacha.semestralproject.app.core.View;
import com.tul.vacha.semestralproject.app.dto.InspectionAddDTO;
import com.tul.vacha.semestralproject.app.entities.CodeListItem;
import com.tul.vacha.semestralproject.app.entities.MedicalDevice;
import com.tul.vacha.semestralproject.app.enums.CodeList;
import com.tul.vacha.semestralproject.app.services.InspectionCalendarService;
import com.tul.vacha.semestralproject.app.services.InspectionService;
import com.tul.vacha.semestralproject.app.services.MedicalDeviceService;
import com.tul.vacha.semestralproject.utils.IOUtils;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;

/**
 *
 * @author pvacha
 */
public class InspectionCalendarView extends View {

    private final MedicalDeviceService medicalService = new MedicalDeviceService();
    private final InspectionService inspService = new InspectionService();
    private final InspectionCalendarService service = new InspectionCalendarService();

    @Override
    public void display() {
        IOUtils.clearConsole();

        String choice = "";
        boolean result = true;

        while (!choice.equals("q")) {
            service.execute("help");

            if (result) {
                System.out.println(service.displayMonth());
                choice = IOUtils.readLine();
            } else {
                break;
            }

            if (choice.startsWith("add")) {
                result = this.add(choice);
            } else {
                result = this.service.execute(choice);

            }

        }
        //}catch(IllegalArgumentException e){
        //    System.out.println(e.getMessage());
        //}

    }

    public boolean add(String command) {
        command = command.replaceAll("\\s+", " ");
        String[] tokens = command.split(" ");

        try {

            List<CodeListItem> codeList = medicalService.getCodeList(CodeList.INSPECTION_TYPE);
            int idInspectionType = IOUtils.selectFromCodeList("Vyberte typ inspekce: (ID): ", CodeListItem::getId, codeList, true);

            StringBuilder sb = new StringBuilder();
            List<MedicalDevice> list = medicalService.getAll();

            for (MedicalDevice item : list) {
                sb.append(String.format("%-5s %-20s %-50s %-10s\n", item.getId(), item.getInternalRegistrationNumber(), item.getName(), item.getCpvDeviceType()));
            }
            System.out.println(sb.toString());

            int idDevice = IOUtils.selectFromCodeList("Vyberte položku (ID): ", MedicalDevice::getId, list, false);

            System.out.println("Zadejte prosím název dodavatele: ");
            String supplier = IOUtils.readLine();

            Date d;
            if (tokens.length > 1) {
                d = java.sql.Date.valueOf(LocalDate.of(service.getYear(), service.getMonth(), Integer.parseInt(tokens[1])));
            } else {
                d = IOUtils.askForDate("Zadejte datum (dd/mm/yyy):");
            }

            int id = this.inspService.addInspection(new InspectionAddDTO(idDevice, idInspectionType, supplier, d));
            this.service.addInspection(id);
            IOUtils.clearConsole();

            return id >= 1;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

        return false;

    }

}
