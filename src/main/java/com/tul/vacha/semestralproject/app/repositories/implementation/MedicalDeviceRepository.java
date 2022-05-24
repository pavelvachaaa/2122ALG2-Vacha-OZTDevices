/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.app.repositories.implementation;

import com.tul.vacha.semestralproject.app.dto.MedicalItemAddDTO;
import com.tul.vacha.semestralproject.app.repositories.interfaces.IMedicalDeviceRepository;
import com.tul.vacha.semestralproject.app.enums.CodeList;
import com.tul.vacha.semestralproject.app.entities.CodeListItem;
import com.tul.vacha.semestralproject.app.entities.MedicalDevice;
import com.tul.vacha.semestralproject.utils.dbutils.Database;
import com.tul.vacha.semestralproject.utils.dbutils.ResultSetPropertiesSimplifyHelps;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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

        ArrayList<MedicalDevice> devices = ResultSetPropertiesSimplifyHelps.putResult(db.query("SELECT ozt_device_item.id, internalRegistrationNumber,name,isElectric,isDeleted,expirationDate,warrantyTo,ozt_device_cpvType.label as cpvDeviceType, ozt_device_type.code as deviceType , description, (ozt_device_mobility.label) as mobility FROM `ozt_device_item` INNER JOIN ozt_device_type ON ozt_device_type.id = ozt_device_item.mobility INNER JOIN ozt_device_mobility ON ozt_device_mobility.id = mobility INNER JOIN ozt_device_cpvType ON ozt_device_cpvType.id = cpvDeviceType"), MedicalDevice.class);

        return devices;

    }

    @Override
    public List<CodeListItem> getCodeList(CodeList codeListType) throws SQLException {
        // Prepared statements na tabulku nefungují :((
        ArrayList<CodeListItem> items = ResultSetPropertiesSimplifyHelps.putResult(db.query(String.format("SELECT * FROM %s", codeListType.getLabel()), new Object[]{}), CodeListItem.class);

        return items;

    }

    @Override
    public int deleteDevice(int id) throws SQLException {
        return db.queryExec("DELETE FROM ozt_device_item WHERE id = ?", new Object[]{id});
    }

    @Override
    public MedicalDevice getDevice(int id) throws SQLException {
        ArrayList<MedicalDevice> device = ResultSetPropertiesSimplifyHelps.putResult(db.query("SELECT ozt_device_item.id, internalRegistrationNumber,name,isElectric,isDeleted,expirationDate,warrantyTo,ozt_device_cpvType.code as cpvDeviceType, ozt_device_type.code as deviceType , description, (ozt_device_mobility.label) as mobility FROM `ozt_device_item` INNER JOIN ozt_device_type ON ozt_device_type.id = ozt_device_item.mobility INNER JOIN ozt_device_mobility ON ozt_device_mobility.id = mobility INNER JOIN ozt_device_cpvType ON ozt_device_cpvType.id = cpvDeviceType WHERE ozt_device_item.id = ?", new Object[]{(int) id}), MedicalDevice.class);

        if (device.isEmpty()) {
            throw new NoSuchElementException("Takové zařízení jsme nenašli");
        }

        return device.get(0);

    }

    @Override
    public int addDevice(MedicalItemAddDTO data) throws SQLException {
        return db.queryExec("INSERT INTO ozt_device_item (name, internalRegistrationNumber,isElectric, warrantyTo, expirationDate,deviceType, mobility, cpvDeviceType) VALUES (?,?,?,?,?,?,?,?)", new Object[]{data.getName(), data.getInternalRegistrationNumber(), data.isIsElectric(), data.getWarrantyTo(), data.getExpirationDate(), data.getDeviceType(), data.getMobility(), data.getCpvDeviceType()});
    }

}
