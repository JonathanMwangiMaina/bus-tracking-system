// DataPersistenceManager.java
package com.kirinyaga.bustracking.persistence;

import com.kirinyaga.bustracking.models.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataPersistenceManager {
    private static final String DATA_DIR = "data";
    private static final String BUSES_DIR = DATA_DIR + "/buses";
    private static final String TRIPS_DIR = DATA_DIR + "/trips";
    private static final String PASSENGERS_DIR = DATA_DIR + "/passengers";
    private static final String ALERTS_DIR = DATA_DIR + "/alerts";
    private static final String REPORTS_DIR = DATA_DIR + "/reports";
    
    static {
        try {
            Files.createDirectories(Paths.get(BUSES_DIR));
            Files.createDirectories(Paths.get(TRIPS_DIR));
            Files.createDirectories(Paths.get(PASSENGERS_DIR));
            Files.createDirectories(Paths.get(ALERTS_DIR));
            Files.createDirectories(Paths.get(REPORTS_DIR));
        } catch (IOException e) {
            System.err.println("Error creating data directories: " + e.getMessage());
        }
    }
    
    public static void saveBusState(List<Bus> buses) throws IOException {
        String filename = BUSES_DIR + "/buses_state.dat";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(buses);
        }
    }
    
    @SuppressWarnings("unchecked")
    public static List<Bus> loadBusState() throws IOException, ClassNotFoundException {
        String filename = BUSES_DIR + "/buses_state.dat";
        File file = new File(filename);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<Bus>) ois.readObject();
        }
    }
    
    public static void saveTripLog(String busId, String tripData) throws IOException {
        LocalDate today = LocalDate.now();
        String dateStr = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String filename = TRIPS_DIR + "/trips_" + dateStr + ".txt";
        
        try (FileWriter fw = new FileWriter(filename, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(busId + "|" + tripData);
            bw.newLine();
        }
    }
    
    public static void savePassengerCount(String routeId, int count) throws IOException {
        LocalDate today = LocalDate.now();
        String dateStr = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String filename = PASSENGERS_DIR + "/passengers_" + dateStr + ".csv";
        
        try (FileWriter fw = new FileWriter(filename, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(routeId + "," + count + "," + System.currentTimeMillis());
            bw.newLine();
        }
    }
    
    public static void saveAlert(String alert) throws IOException {
        LocalDate today = LocalDate.now();
        String dateStr = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String filename = ALERTS_DIR + "/alerts_" + dateStr + ".txt";
        
        try (FileWriter fw = new FileWriter(filename, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(System.currentTimeMillis() + "|" + alert);
            bw.newLine();
        }
    }
    
    public static void generateDailyReport() throws IOException {
        LocalDate today = LocalDate.now();
        String dateStr = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String filename = REPORTS_DIR + "/report_" + dateStr + ".txt";
        
        try (FileWriter fw = new FileWriter(filename);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write("=== DAILY BUS SYSTEM REPORT ===\n");
            bw.write("Date: " + today + "\n");
            bw.write("Total Buses: 5\n");
            bw.write("Total Students: 1,234\n");
            bw.write("On-Time Performance: 94.5%\n");
            bw.write("Average Occupancy: 72%\n");
        }
    }
}
