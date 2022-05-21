package com.tul.vacha.semestralproject.app.repositories.interfaces;

import com.tul.vacha.semestralproject.app.dto.InspectionAddDTO;
import com.tul.vacha.semestralproject.app.entities.Inspection;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author pvacha
 */
public interface IInspectionRepository {

    ArrayList<Inspection> getDeviceInspections(int deviceId) throws SQLException;

    Inspection getInspection(int id) throws SQLException;

    ArrayList<Inspection> getAllInspections() throws SQLException;

    boolean markAsDone(int idInspection) throws SQLException;

    // DTO přidat
    int addInspection(InspectionAddDTO data) throws SQLException;

    boolean delete(int idInspection) throws SQLException;
}
