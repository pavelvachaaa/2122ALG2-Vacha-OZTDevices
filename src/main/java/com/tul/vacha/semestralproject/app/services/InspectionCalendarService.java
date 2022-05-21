/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tul.vacha.semestralproject.app.services;

import com.tul.vacha.semestralproject.app.core.InspectionCalendar;
import com.tul.vacha.semestralproject.app.core.navigation.Navigator;
import com.tul.vacha.semestralproject.app.dto.InspectionAddDTO;
import com.tul.vacha.semestralproject.app.entities.Inspection;
import com.tul.vacha.semestralproject.app.enums.WeekDays;
import com.tul.vacha.semestralproject.app.repositories.implementation.InspectionRepository;
import com.tul.vacha.semestralproject.ui.views.InspectionDetailView;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pvacha
 */
public class InspectionCalendarService {

    private InspectionCalendar cal = InspectionCalendar.getInstance();
    private InspectionRepository repo;

    private ArrayList<Inspection> inspections;
    private Map<Integer, Map<Integer, List<Integer>>> inspectionMap;

    public InspectionCalendarService() {
        try {
            this.repo = new InspectionRepository();
            inspections = repo.getAllInspections();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    /*private void createMap() {
        LocalDate localDate;
        int month, year, day;
        for (Inspection inspection : inspections) {
            localDate = inspection.getInspectionDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            month = localDate.getMonthValue();
            year = localDate.getYear();
            day = localDate.getDayOfMonth();
            month
            if (inspectionMap.containsKey(year)) {
                if (inspectionMap.get(year).containsKey(month)) {
                    inspectionMap.get(year).get(month).add(day);
                } else {
                    inspectionMap.keySet(year);
                    
                }
            } else {
                inspectionMap.
            }
        }

    }*/
    public int getYear() {
        return cal.getYear();
    }

    public int getMonth() {
        return cal.getMonth().getMonthNumber();
    }

    public int getDay() {
        return cal.getDay();
    }

    public boolean execute(String command) {
        if ("next".equals(command)) {
            cal.nextMonth();
            return true;
        }
        if ("previous".equals(command)) {
            cal.previousMonth();
            return true;
        }

        if ("today".equals(command)) {
            cal.today();
            return true;
        }

        if ("help".equals(command)) {
            System.out.println("Dostupné příkazy: next,previous,today,back,help, show");
            return true;
        }
        if ("back".equals(command)) {
            Navigator.pop();
            return true;
        }
        if (command.startsWith("show")) {
            return showCommand(command);
        }

        return true;
    }

    public boolean removeFromList(int id) {
        return this.inspections.removeIf((insp) -> {
            return insp.getId() == id;
        });
    }

    private boolean showCommand(String command) {
        command = command.replaceAll("\\s+", " ");
        String[] tokens = command.split(" ");
        if (tokens.length > 1 && !tokens[1].isBlank()) {
            try {
                // Omezení na jednu inspekci na jeden den - mimo rozsah této práce už..

                Inspection insp;
                insp = inspections.stream().filter((ins) -> {
                    return ins.getLocalDate().getDayOfMonth() == Integer.parseInt(tokens[1]) && this.cal.getMonth().getMonthNumber() == ins.getLocalDate().getMonthValue();
                }).findFirst().orElseThrow();
                Navigator.push(new InspectionDetailView(insp, this)); // Catch parsing error
                return false;
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
                return false;
            } // Parsing error ještě

        }

        return false;
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

    private ArrayList<Integer> getDaysWhereInspection(int year, int month) {
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
        ArrayList<Integer> days = getDaysWhereInspection(cal.getYear(), cal.getMonth().getMonthNumber());

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

    public String displayMonth() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getHeaderOfMonth());
        sb.append(System.getProperty("line.separator"));
        sb.append(this.getBodyOfCalendar());
        //sb.append(System.getProperty("line.separator"));
        return sb.toString();
    }

    public boolean addInspection(int id) throws SQLException {
        return this.inspections.add(repo.getInspection(id));
    }

}
