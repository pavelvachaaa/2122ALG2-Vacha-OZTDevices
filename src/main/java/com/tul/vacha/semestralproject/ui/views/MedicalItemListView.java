/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.ui.views;

import com.tul.vacha.semestralproject.app.enums.CodeList;
import com.tul.vacha.semestralproject.app.core.navigation.Navigator;
import com.tul.vacha.semestralproject.app.core.View;
import com.tul.vacha.semestralproject.app.core.navigation.Menu;
import com.tul.vacha.semestralproject.app.core.navigation.MenuItem;
import com.tul.vacha.semestralproject.app.dto.MedicalItemAddDTO;
import com.tul.vacha.semestralproject.app.entities.CodeListItem;
import com.tul.vacha.semestralproject.app.entities.MedicalDevice;
import com.tul.vacha.semestralproject.app.services.MedicalDeviceService;
import com.tul.vacha.semestralproject.utils.CommonUtils;
import com.tul.vacha.semestralproject.utils.IOUtils;
import com.tul.vacha.semestralproject.utils.MenuUtils;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

// TODO: Export, import
/**
 *
 * @author pvacha
 */
public class MedicalItemListView extends View {

    private final MedicalDeviceService service = new MedicalDeviceService();
    private List<MedicalDevice> devices = new ArrayList<>();

    private final ArrayList<MenuItem> menuItems = new ArrayList<>() {
        {
            add(new MenuItem("Přidat položku", "add", (d) -> {
                try {
                    addDevice();
                } catch (SQLException ex) {
                }
            }, true));
            add(new MenuItem("Zobrazit položku", "show", (command) -> {
                try {
                    System.out.println(command.toString());
                    int id = parseCommandId(command.toString());

                    Navigator.push(new MedicalItemDetailView(service.getDeviceDetail(id)));
                } catch (SQLException ex) {

                }
            }));

            add(new MenuItem("Smazat položku", "delete", (command) -> {
                try {
                    int id = parseCommandId(command.toString());
                    delete(id);
                } catch (SQLException ex) {
                }

            }, true));

            add(new MenuItem("Exportovat položky", "export", (command) -> exportData(),
                    true));
        }
    };

    private final Menu menu = new Menu(menuItems, true);

    @Override
    public void display() {
        IOUtils.clearConsole();
        try {
            devices = service.getAll();
            Collections.sort(devices); // Sortování zde, abychs splnil požadavky.. Jinak by to bylo na straně DB
            displayItems();

            while (true) {
                
                System.out.println("Některé příkazy vyžadují parametr jako číslo (show, add, delete, ...)");
                MenuUtils.askForCommand(menu);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void exportData() {
        while (true) {

            System.out.println("Zadejte název souboru, do kterého chcete importovat:");
            String filename = IOUtils.readLine();

            try {
                if (!filename.endsWith(".txt")) {
                    filename += ".txt";
                }

                this.service.exportData(filename);
                this.showMessage("Úspěšně jsme uložili soubor");
                return;
            } catch (IOException ex) {
                this.showMessage("Nastala chyba při práci se soubory, zkuste to znovu");
            } catch (SQLException ex) {
                this.showMessage("Nemohli jsme načíst data k exportu");
            }

        }
    }

    private int parseCommandId(String command) throws SQLException {

        command = command.replaceAll("\\s+", " ");
        String[] tokens = command.split(" ");

        if (tokens.length > 1) {
            if (!CommonUtils.idInList(Integer.parseInt(tokens[1]), MedicalDevice::getId, devices)) {
                throw new NoSuchElementException("Takový to item neexistuje");
            }
            return Integer.parseInt(tokens[1]);
        } else {
            return IOUtils.selectFromCodeList("Zadejte id předmětu: ", MedicalDevice::getId, devices, false);
        }

    }

    private void delete(int id) throws SQLException {
        if (!IOUtils.askForYesNo("Opravdu chcete zařízení smazat?")) {
            this.display();
            return;
        }
        boolean res = this.service.delete(id);

        if (res) {
            this.showMessage("Úspšně jsme smazali předmět");
            this.devices.removeIf(x -> x.getId() == id);
        } else {
            this.showMessage("Nepodařilo se nám smazat předmět. Zkuste to znovu");
        }

        display();

    }

    private void displayItems() {

        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%-5s %-20s %-50s %-10s", "ID", "INTERNÍ ČÍSLO", "NÁZEV", "CPV TYP"));
        sb.append(System.lineSeparator());
        sb.append("=".repeat(150));
        sb.append(System.lineSeparator());

        for (MedicalDevice item : devices) {
            sb.append(item.toString());
        }

        System.out.println(sb.toString());

    }

    private void addDevice() throws SQLException {

        List<CodeListItem> codeList = service.getCodeList(CodeList.DEVICE_TYPE);
        int deviceTypeId = IOUtils.selectFromCodeList("Vyberte typ zařízení (ID): ", CodeListItem::getId, codeList, true);

        codeList = service.getCodeList(CodeList.MOBILITY);
        int mobilityId = IOUtils.selectFromCodeList("Vyberte typ mobility (ID): ", CodeListItem::getId, codeList, true);
        IOUtils.clearConsole();

        System.out.println("Zadejte název zařízení: ");
        String name = IOUtils.readLine();

        System.out.println("Zadejte interní číslo zařízení: ");
        String internalRegistrationNumber = IOUtils.readLine();

        // Těch údajů je 200 a našeptávač fakt dělat nebudu.
        int cpvDeviceType = CommonUtils.getRandomNumber(1, 199);

        boolean isElectric = IOUtils.askForYesNo("Je zařízení elektrické");
        Date warrantyTo = IOUtils.askForDate("Kdy končí záruka");
        Date expirationTo = IOUtils.askForDate("Vyřazení zařízení");

        MedicalItemAddDTO data = new MedicalItemAddDTO(name, internalRegistrationNumber, isElectric, warrantyTo, expirationTo, deviceTypeId, mobilityId, cpvDeviceType);

        int id = service.addDevice(data);

        // Až nebudu líný tak přidat do arrayListu - zatím znova
        // zavoláme display()
        this.display();

    }

}
