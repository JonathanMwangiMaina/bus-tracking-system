package com.kirinyaga.bustracking.service;

import com.kirinyaga.bustracking.util.DatabaseManager;
import java.sql.*;
import java.util.concurrent.*;

public class GPSDataSimulator {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void startSimulation() {
        scheduler.scheduleAtFixedRate(() -> {
            try (Connection conn = DatabaseManager.getConnection()) {
                // Simulate slight movement (jitter)
                String sql = "UPDATE buses SET lat = lat + (0.0001 * (random() % 10 - 5)), " +
                             "lon = lon + (0.0001 * (random() % 10 - 5))";
                conn.createStatement().executeUpdate(sql);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 2, TimeUnit.SECONDS);
    }
}