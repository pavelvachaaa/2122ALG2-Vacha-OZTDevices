/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.ui.views;

import com.tul.vacha.semestralproject.app.services.AuthService;
import com.tul.vacha.semestralproject.app.enums.CodeList;
import com.tul.vacha.semestralproject.app.core.Navigator;
import com.tul.vacha.semestralproject.app.core.View;
import com.tul.vacha.semestralproject.app.entities.CodeListItem;
import com.tul.vacha.semestralproject.app.entities.MedicalDevice;

import com.tul.vacha.semestralproject.app.repositories.implementation.MedicalDeviceRepository;
import com.tul.vacha.semestralproject.utils.InputUtils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pvacha
 */
public class MedicalItemListView extends View {

    private final MedicalDeviceRepository repo = new MedicalDeviceRepository();
    private final AuthService auth = AuthService.getInstance();

    @Override
    public void display() {
        InputUtils.clearConsole();
        displayItems();

        System.out.println("      ");
        System.out.printf("%s@ozt-app:", auth.getUser().getUsername());
        Navigator.execute(InputUtils.readString());
    }

    private void displayItems() {

        try {
            StringBuilder sb = new StringBuilder();
            sb.append("ID       INTERNÍ ČÍSLO       NÁZEV       CPV TYP");
            sb.append(System.lineSeparator());
            sb.append("=====================================================");
            sb.append(System.lineSeparator());

            List<MedicalDevice> list = repo.getAll();
            for (MedicalDevice item : list) {
                sb.append(String.format("%s       %s       %s       %s\n", item.getId(), item.getInternalRegistrationNumber(), item.getName(), item.getCpvDeviceType()));
            }
            System.out.println(sb.toString());

            // System.out.println(list.get(0).getName());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
