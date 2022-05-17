/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.app.repositories.implementation;

import com.tul.vacha.semestralproject.app.repositories.interfaces.IMedicalDeviceRepository;
import com.tul.vacha.semestralproject.app.enums.CodeList;
import com.tul.vacha.semestralproject.app.entities.CodeListItem;
import com.tul.vacha.semestralproject.app.entities.MedicalDevice;
import com.tul.vacha.semestralproject.app.entities.User;
import com.tul.vacha.semestralproject.utils.dbutils.Database;
import com.tul.vacha.semestralproject.utils.dbutils.ResultSetPropertiesSimplifyHelps;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pvacha
 */
public class MedicalDeviceRepository implements IMedicalDeviceRepository {

    private final Database db;

    public MedicalDeviceRepository() { // Předělat předávání závislostí...
        Database db = null;
        try {
            db = Database.getInstance();

        } catch (SQLException e) {
            System.out.println("Databáze ještě nebyla inicializována");
        }
        this.db = db;
    }

    @Override
    public List<MedicalDevice> getAll() throws SQLException {

        ArrayList<MedicalDevice> devices = ResultSetPropertiesSimplifyHelps.putResult(db.query("SELECT ozt_device_item.id, internalRegistrationNumber,name,isElectric,isDeleted,expirationDate,warrantyTo,ozt_device_cpvType.code as cpvDeviceType, ozt_device_type.code as deviceType , description, (ozt_device_mobility.label) as mobility FROM `ozt_device_item` INNER JOIN ozt_device_type ON ozt_device_type.id = ozt_device_item.mobility INNER JOIN ozt_device_mobility ON ozt_device_mobility.id = mobility INNER JOIN ozt_device_cpvType ON ozt_device_cpvType.id = cpvDeviceType"), MedicalDevice.class);

        //HANDLE stuff - errors
        return devices;

    }

    @Override
    public List<CodeListItem> getCodeList(CodeList codeListType) throws SQLException {
        // Prepared statements na tabulku nefungují :((
        ArrayList<CodeListItem> items = ResultSetPropertiesSimplifyHelps.putResult(db.query(String.format("SELECT * FROM %s", codeListType.getLabel()), new Object[]{}), CodeListItem.class);

        // Checky a tak...
        return items;

    }

    @Override
    public boolean deleteDevice(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
