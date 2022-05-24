/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.app.services;

import com.tul.vacha.semestralproject.app.dto.InspectionAddDTO;
import com.tul.vacha.semestralproject.app.repositories.implementation.InspectionRepository;
import java.sql.SQLException;

/**
 *
 * @author pvacha
 */
public class InspectionService {

    private InspectionRepository repo;

    public InspectionService() {
        try {
            this.repo = new InspectionRepository();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int addInspection(InspectionAddDTO insp) throws SQLException {
        return this.repo.addInspection(insp);
    }

    public boolean delete(int idInspection) throws SQLException {
        return this.repo.delete(idInspection);
    }

    public boolean markAsDone(int idInspection) throws SQLException {
        return this.repo.markAsDone(idInspection);
    }

    

}
