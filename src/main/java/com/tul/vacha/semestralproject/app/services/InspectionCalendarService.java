/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.app.services;

import com.tul.vacha.semestralproject.app.core.InspectionCalendar;
import com.tul.vacha.semestralproject.app.dto.InspectionAddDTO;
import com.tul.vacha.semestralproject.app.entities.Inspection;
import com.tul.vacha.semestralproject.app.enums.WeekDays;
import com.tul.vacha.semestralproject.app.repositories.implementation.InspectionRepository;
import com.tul.vacha.semestralproject.utils.IOUtils;
import com.tul.vacha.semestralproject.utils.exceptions.ErrorLinesException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author pvacha
 */
public class InspectionCalendarService {

    private InspectionCalendar cal = InspectionCalendar.getInstance();
    private InspectionRepository repo;

    private ArrayList<Inspection> inspections;

    public InspectionCalendarService() {
        try {
            this.repo = new InspectionRepository();
            inspections = repo.getAllInspections();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public int getYear() {
        return cal.getYear();
    }

    public int getMonth() {
        return cal.getMonth().getMonthNumber();
    }

    public int getDay() {
        return cal.getDay();
    }

    public void nextMonth() {
        this.cal.nextMonth();
    }

    public void previousMonth() {
        this.cal.previousMonth();
    }

    public void today() {
        this.cal.today();
    }

    public boolean removeFromList(int id) {
        return this.inspections.removeIf((insp) -> {
            return insp.getId() == id;
        });
    }

    /**
     * Naparsuje command
     *
     * @param command
     * @return na základě naparsovaného ID vrací inspekci
     */
    public Inspection showCommand(String command) {
        command = command.replaceAll("\\s+", " ");
        String[] tokens = command.split(" ");
        if (tokens.length > 1 && !tokens[1].isBlank()) {
            // Omezení na jednu inspekci na jeden den - nezbyl rozpočet
            Inspection insp;
            insp = inspections.stream().filter((ins) -> {
                return ins.getLocalDate().getDayOfMonth() == Integer.parseInt(tokens[1]) && this.cal.getMonth().getMonthNumber() == ins.getLocalDate().getMonthValue();
            }).findFirst().orElseThrow();

            return insp;

        } else {
            throw new IllegalArgumentException("Neplatný argument");
        }

    }

    /**
     * Přidává do aktuálního kalendáře inspekci
     *
     * @param id
     * @return zdali přidání bylo úspěšné
     * @throws SQLException
     */
    public boolean addInspection(int id) throws SQLException {
        return this.inspections.add(repo.getInspection(id));
    }

    private String getHeaderOfMonth() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s %d", cal.getMonth().toString().toUpperCase(), cal.getYear()));
        sb.append(System.getProperty("line.separator"));
        WeekDays[] weekdays = WeekDays.values();
        for (WeekDays wd : weekdays) {
            sb.append(wd.toString()).append(" ");
        }
        return sb.toString();
    }

    private ArrayList<Integer> getDaysWhereInspection() {
        LocalDate inspDate;

        ArrayList<Integer> days = new ArrayList<>();

        for (Inspection insp : inspections) {
            inspDate = insp.getLocalDate();

            if (cal.getMonth().getMonthNumber() == inspDate.getMonthValue() && cal.getYear() == inspDate.getYear()) {
                days.add(inspDate.getDayOfMonth());
            }

        }

        return days;
    }

    private String getBodyOfCalendar() {
        int startAt = cal.getDayInWeekForFirst();
        StringBuilder sb = new StringBuilder();
        sb.append("   ".repeat(startAt));
        ArrayList<Integer> days = getDaysWhereInspection();

        for (int i = 0, dayOfMonth = 1; dayOfMonth <= cal.getMonth().getDaysInMonth(); i++) {
            for (int j = ((i == 0) ? startAt : 0); j < 7 && (dayOfMonth <= cal.getMonth().getDaysInMonth()); j++) {
                if (days.contains(dayOfMonth)) {
                    sb.append("XX ");
                } else {
                    sb.append(String.format("%2d ", dayOfMonth));

                }
                dayOfMonth++;
            }
            sb.append(System.getProperty("line.separator"));
        }

        return sb.toString();
    }

    /**
     * Stringová reprezentace kalendáře
     *
     * @return stringovou reprezentaci kalendáře
     */
    public String getCalendarString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getHeaderOfMonth());
        sb.append(System.getProperty("line.separator"));
        sb.append(this.getBodyOfCalendar());
        return sb.toString();
    }

    /**
     * Načte do kalendáře data ze souboru ve správným formátu
     *
     * @param filename
     * @throws IOException
     */
    public void addInspectionsFromFile(String filename) throws IOException {
        File dataDirectory = new File(System.getProperty("user.dir") + File.separator + "data");
        File data = new File(dataDirectory, filename);
        List<Integer> errorLines = new ArrayList<>();

        try ( BufferedReader br = new BufferedReader(new FileReader(data))) {
            String line;
            String[] parts;
            InspectionAddDTO insp;
            br.readLine(); //preskoceni hlavicky
            int lineNumber = 2;

            while ((line = br.readLine()) != null) {
                parts = line.split("[ ]+");
                try {
                    insp = new InspectionAddDTO(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), parts[2], IOUtils.getInDateFormat().parse(parts[3]));

                    // Jsem si plně vědom, že to není efektivní a, že bych měl
                    // udělat jeden velký sql string sanitiznout a pak až executnout.
                    this.addInspection(repo.addInspection(insp));
                } catch (ParseException | SQLException ex) {
                    errorLines.add(lineNumber);
                }

            }

            br.close();
        }

        if (!errorLines.isEmpty()) {
            throw new ErrorLinesException("Nebyla načtena data na řádcích: " + errorLines.toString());
        }

    }

}
