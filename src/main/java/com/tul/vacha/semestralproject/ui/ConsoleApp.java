/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.ui;

import com.tul.vacha.semestralproject.app.core.App;
import com.tul.vacha.semestralproject.app.core.navigation.Navigator;
import com.tul.vacha.semestralproject.app.core.View;
import com.tul.vacha.semestralproject.app.dto.InspectionAddDTO;
import com.tul.vacha.semestralproject.app.dto.MedicalItemAddDTO;
import com.tul.vacha.semestralproject.app.dto.UserLoginDTO;
import com.tul.vacha.semestralproject.app.dto.UserRegisterDTO;
import com.tul.vacha.semestralproject.app.services.AuthService;
import com.tul.vacha.semestralproject.app.services.InspectionService;
import com.tul.vacha.semestralproject.app.services.MedicalDeviceService;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Jedna z možností na UI
 *
 * @author pvacha
 */
public class ConsoleApp extends App {

    public ConsoleApp(String defaultRoute, Map<String, View> routes) {
        super(defaultRoute, routes);
    }

    @Override
    public void start() {
        Navigator.pushNamed(this.defaultRoute);

     /*   try {
            test();
        } catch (SQLException | ParseException
                | NoSuchAlgorithmException ex) {

        }
*/
    }

    private void test() throws SQLException, ParseException, NoSuchAlgorithmException {
        MedicalDeviceService service = new MedicalDeviceService();
        InspectionService service2 = new InspectionService();
        AuthService authService = AuthService.getInstance();

        UserLoginDTO loginDTOSuccess = new UserLoginDTO("pavel", "pavel");
        UserLoginDTO loginDTOFail = new UserLoginDTO("pavel", "spatneheslo");

        UserRegisterDTO registerDTOSuccess = new UserRegisterDTO("Pavel Vácha", "pavelx", "test");
        UserRegisterDTO registerDTOfail = new UserRegisterDTO("Pavel Vácha", "pavelx", "test");

        expectOutput("1PřihlášeníÚspěch", true, authService.login(loginDTOSuccess));
        expectOutput("2PřihlášeníNeúspěch", false, authService.login(loginDTOFail));

        MedicalItemAddDTO dto = new MedicalItemAddDTO("zarizeniZTestu", "irnNumber", false, new SimpleDateFormat("dd/mm/yyyy").parse("3/12/2001"), new SimpleDateFormat("dd/mm/yyyy").parse("3/12/2001"), 2, 2, 120);
        int id = service.addDevice(dto);
        expectOutput("3VytvořeníZařízeníÚspěch", true, id >= 1);

        expectParseError("4NeÚspěchNaparsovano", "3ddas+ě/dasě+/s", false);
        expectParseError("5ÚspěchNaparsovano", "3/12/2OO1", true);

        InspectionAddDTO dto2 = new InspectionAddDTO(id, 2, "supplierDva", new SimpleDateFormat("dd/mm/yyyy").parse("3/12/2022"));
        int inspId = service2.addInspection(dto2);
        expectOutput("6VytvořeníInspekceÚspěch", true, inspId >= 1);

        boolean isDeleted = service2.delete(inspId);
        expectOutput("7ÚspěchSmazáníInspekce", true, isDeleted);

        isDeleted = service.delete(id);
        expectOutput("8ÚspěchSmazáníDevice", true, isDeleted);

        boolean registered = authService.register(registerDTOSuccess);
        expectOutput("9Úspěchregistrace", true, registered);

        expectIllegalArgument("10NeÚspěchregistrace", registerDTOfail);

        isDeleted = authService.delete("pavelx");
        expectOutput("11ÚspěchDeleteUzivatel", true, isDeleted);

    }

    private <T> void expectOutput(String testName, T expect, T output) {
        System.out.printf("%s - %s\n", testName, (expect.equals(output) ? "Test prošel" : "Test neprošel"));
    }

    private void expectParseError(String testName, String data, boolean shouldSuccess) {
        boolean pass = false;
        try {
            new SimpleDateFormat("dd/mm/yyyy").parse(data);
            pass = true;
        } catch (Exception e) {

        } finally {
            System.out.printf("%s - %s\n", testName, (pass == shouldSuccess ? "Test prošel" : "Test neprošel"));
        }

    }

    private void expectIllegalArgument(String testName, UserRegisterDTO data) {
        boolean pass = false;
        try {
            AuthService.getInstance().register(data);
            pass = true;
        } catch (Exception e) {

        } finally {
            System.out.printf("%s - %s\n", testName, (!pass ? "Test prošel" : "Test neprošel"));
        }

    }

}
