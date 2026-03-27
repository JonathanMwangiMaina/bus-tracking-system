// ErrorLogger.java
package com.kirinyaga.bustracking.persistence;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ErrorLogger {
    private static final String LOG_FILE = "logs/bus_system_errors.log";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    static {
        try {
            new File("logs").mkdirs();
            new File(LOG_FILE).createNewFile();
        } catch (IOException e) {
            System.err.println("Error creating log file: " + e.getMessage());
        }
    }
    
    public static void logError(String message, Exception e) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            
            bw.write("[" + LocalDateTime.now().format(formatter) + "] ERROR: " + message);
            bw.newLine();
            
            if (e != null) {
                bw.write("Exception: " + e.getClass().getName() + " - " + e.getMessage());
                bw.newLine();
                
                for (StackTraceElement element : e.getStackTrace()) {
                    bw.write("  at " + element);
                    bw.newLine();
                }
            }
            
            bw.write("---");
            bw.newLine();
        } catch (IOException ioException) {
            System.err.println("Error writing to log file: " + ioException.getMessage());
        }
    }
    
    public static void logInfo(String message) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            
            bw.write("[" + LocalDateTime.now().format(formatter) + "] INFO: " + message);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}
