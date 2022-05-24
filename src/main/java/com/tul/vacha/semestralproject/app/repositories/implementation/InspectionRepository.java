/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.app.repositories.implementation;

import com.tul.vacha.semestralproject.app.dto.InspectionAddDTO;
import com.tul.vacha.semestralproject.app.entities.Inspection;
import com.tul.vacha.semestralproject.app.repositories.interfaces.IInspectionRepository;
import com.tul.vacha.semestralproject.utils.dbutils.Database;
import com.tul.vacha.semestralproject.utils.dbutils.ResultSetPropertiesSimplifyHelps;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author pvacha
 */
public class InspectionRepository implements IInspectionRepository {

    private final Database db;

    public InspectionRepository() throws SQLException {
        this.db = Database.getInstance();
    }

    @Override
    public ArrayList<Inspection> getDeviceInspections(int deviceId) throws SQLException {
        ArrayList<Inspection> inspections = ResultSetPropertiesSimplifyHelps.putResult(db.query("SELECT ozt_device_inspection.id, idDevice, supplier, inspectionDate, ozt_inspection.code  as label FROM `ozt_device_inspection` INNER JOIN ozt_inspection ON idInspection = ozt_inspection.id  INNER JOIN ozt_device_item ON ozt_device_item.id = ozt_device_inspection.idDevice WHERE isDone = 0 and idDevice = ?", new Object[]{deviceId}), Inspection.class);
        if (inspections.isEmpty()) {
            return new ArrayList<>();
        }

        return inspections;
    }

    @Override
    public ArrayList<Inspection> getAllInspections() throws SQLException {
        ArrayList<Inspection> inspections = ResultSetPropertiesSimplifyHelps.putResult(db.query("SELECT ozt_device_inspection.id, idDevice, supplier, ozt_inspection.label, inspectionDate, ozt_inspection.code  FROM `ozt_device_inspection` INNER JOIN ozt_inspection ON idInspection = ozt_inspection.id WHERE isDone = 0", new Object[]{}), Inspection.class);

        if (inspections.isEmpty()) {
            return new ArrayList<>();
        }

        return inspections;
    }

    @Override
    public boolean markAsDone(int inspectionId) throws SQLException {
        int result = db.queryExec("UPDATE ozt_device_inspection SET isDone = 1 WHERE id = ?", new Object[]{inspectionId});
        // +Výjimka atd...
        return result >= 1;
    }

    @Override
    public int addInspection(InspectionAddDTO data) throws SQLException {

        int id = db.queryExec("INSERT INTO ozt_device_inspection (idDevice, idInspection, supplier, inspectionDate) VALUES(?, ?, ?,?) ", new Object[]{data.getIdDevice(), data.getIdInspection(), data.getSupplier(), data.getInspectionDate()});

        return id;
    }

    @Override
    public Inspection getInspection(int id) throws SQLException {
        ArrayList<Inspection> inspections = ResultSetPropertiesSimplifyHelps.putResult(db.query("SELECT ozt_device_inspection.id, idDevice, supplier, ozt_inspection.label, inspectionDate  FROM `ozt_device_inspection` INNER JOIN ozt_inspection ON idInspection = ozt_inspection.id WHERE ozt_device_inspection.id=?", new Object[]{id}), Inspection.class);

        if (inspections.isEmpty()) {
            return null;
        }

        return inspections.get(0);
    }

    @Override
    public boolean delete(int idInspection) throws SQLException {
        int result = db.queryExec("DELETE FROM ozt_device_inspection WHERE id = ?", new Object[]{idInspection});
        return result >= 1;

    }

}
