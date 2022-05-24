/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.app.services;

import com.tul.vacha.semestralproject.app.dto.MedicalItemAddDTO;
import com.tul.vacha.semestralproject.app.dto.MedicalItemDTO;
import com.tul.vacha.semestralproject.app.entities.CodeListItem;
import com.tul.vacha.semestralproject.app.entities.Inspection;
import com.tul.vacha.semestralproject.app.entities.MedicalDevice;
import com.tul.vacha.semestralproject.app.enums.CodeList;
import com.tul.vacha.semestralproject.app.repositories.implementation.InspectionRepository;
import com.tul.vacha.semestralproject.app.repositories.implementation.MedicalDeviceRepository;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pvacha
 */
public class MedicalDeviceService {

    private MedicalDeviceRepository repoMedical;
    private InspectionRepository repoInspection;

    public MedicalDeviceService() {
        repoMedical = new MedicalDeviceRepository();
        try {
            repoInspection = new InspectionRepository();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public MedicalItemDTO getDeviceDetail(int id) throws SQLException {
        MedicalDevice device = this.getDevice(id);
        ArrayList<Inspection> inspections = repoInspection.getDeviceInspections(id);

        return new MedicalItemDTO(device, inspections);
    }

    public List<MedicalDevice> getAll() throws SQLException {
        return repoMedical.getAll();
    }

    public MedicalDevice getDevice(int id) throws SQLException {
        return repoMedical.getDevice(id);
    }

    public List<CodeListItem> getCodeList(CodeList codeList) throws SQLException {
        return repoMedical.getCodeList(codeList);
    }

    public int addDevice(MedicalItemAddDTO data) throws SQLException {
        return repoMedical.addDevice(data);
    }

    public boolean delete(int id) throws SQLException {
        return repoMedical.deleteDevice(id) >= 1;
    }

    public void exportData(String filename) throws IOException, SQLException {
        List<MedicalDevice> devices = this.getAll();

        File dataDirectory = new File(System.getProperty("user.dir") + File.separator + "data");
        File data = new File(dataDirectory, filename);

        try ( PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(data, true)))) {
            pw.println(String.format("%-5s %-20s %-50s %-10s",
                    "ID", "IRN", "NAME", "CPV_DEVICE_TYPE"));
            for (MedicalDevice device : devices) {
                pw.println(device);
            }
            pw.close();
        }

    }

}
