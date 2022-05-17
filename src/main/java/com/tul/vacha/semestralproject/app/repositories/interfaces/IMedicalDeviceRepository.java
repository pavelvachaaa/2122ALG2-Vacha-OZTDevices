/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.app.repositories.interfaces;

import com.tul.vacha.semestralproject.app.enums.CodeList;
import com.tul.vacha.semestralproject.app.entities.CodeListItem;
import com.tul.vacha.semestralproject.app.entities.MedicalDevice;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author pvacha
 */
public interface IMedicalDeviceRepository {

    List<MedicalDevice> getAll() throws SQLException;

    List<CodeListItem> getCodeList(CodeList codeListType) throws SQLException;

    boolean deleteDevice(int id) throws SQLException;

}
