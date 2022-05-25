/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.ui.views;

import com.tul.vacha.semestralproject.app.core.navigation.Navigator;
import com.tul.vacha.semestralproject.app.core.View;
import com.tul.vacha.semestralproject.app.core.navigation.Menu;
import com.tul.vacha.semestralproject.app.core.navigation.MenuItem;
import com.tul.vacha.semestralproject.app.dto.InspectionAddDTO;
import com.tul.vacha.semestralproject.app.entities.CodeListItem;
import com.tul.vacha.semestralproject.app.entities.Inspection;
import com.tul.vacha.semestralproject.app.entities.MedicalDevice;
import com.tul.vacha.semestralproject.app.enums.CodeList;
import com.tul.vacha.semestralproject.app.services.InspectionCalendarService;
import com.tul.vacha.semestralproject.app.services.InspectionService;
import com.tul.vacha.semestralproject.app.services.MedicalDeviceService;
import com.tul.vacha.semestralproject.utils.IOUtils;
import com.tul.vacha.semestralproject.utils.MenuUtils;
import com.tul.vacha.semestralproject.utils.exceptions.ErrorLinesException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author pvacha
 */
public class InspectionCalendarView extends View {

    private final MedicalDeviceService medicalService = new MedicalDeviceService();
    private final InspectionService inspService = new InspectionService();
    private final InspectionCalendarService service = new InspectionCalendarService();

    private final ArrayList<MenuItem> menuItems = new ArrayList<>() {
        {
            add(new MenuItem("Následující měsíc", "next", (d) -> service.nextMonth()));
            add(new MenuItem("Předcházející měsíc", "previous", (d) -> service.previousMonth()));
            add(new MenuItem("Dnes", "today", (d) -> service.today()));
            add(new MenuItem("Přidat položku", "add", (command) -> addCommand(command.toString()), true));
            add(new MenuItem("Zobrazit položku", "show", (command) -> showCommand(command.toString())));
            add(new MenuItem("Načíst ze souboru", "import", (command) -> addInspectionsFromFile(), true));
        }
    };

    private final Menu menu = new Menu(menuItems, true, true);

    @Override
    public void display() {

        while (true) {
            IOUtils.clearConsole();
            System.out.println(this.service.getCalendarString());
            
            System.out.println("Některé příkazy vyžadují parametr jako číslo (show, add, delete, ...)");
            MenuUtils.askForCommand(menu);

        }
    }

    private void addInspectionsFromFile() {
        while (true) {

            System.out.println("Zadejte název souboru, ve kterém jsou data");
            String filename = IOUtils.readLine();

            try {
                if (!filename.endsWith(".txt")) {
                    filename += ".txt";
                }

                this.service.addInspectionsFromFile(filename);
                this.display();

                return;
            } catch (IOException ex) {
                this.showMessage("Nastala chyba při práci se soubory, zkuste to znovu:");
            } catch (Exception e) {
                this.showMessage(e.getMessage());
                return;
            }

        }
    }

    private void showCommand(String command) {

        try {
            System.out.println(command);
            Inspection insp = service.showCommand(command);

            Navigator.push(new InspectionDetailView(insp, service)); // Catch parsing error

        } catch (NoSuchElementException ex) {
            System.out.println("V tento den nic není...");
        } catch (IllegalArgumentException ex) {
            System.out.println("Tak tohle naparsovat neumím");
        }
    }

    public boolean addCommand(String command) {
        command = command.replaceAll("\\s+", " ");
        String[] tokens = command.split(" ");

        try {

            List<CodeListItem> codeList = medicalService.getCodeList(CodeList.INSPECTION_TYPE);
            int idInspectionType = IOUtils.selectFromCodeList("Vyberte typ inspekce: (ID): ", CodeListItem::getId, codeList, true);

            StringBuilder sb = new StringBuilder();
            List<MedicalDevice> list = medicalService.getAll();

            IOUtils.clearConsole();
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
