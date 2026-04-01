package com.kirinyaga.bustracking.util;

import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:/app/data/bus_tracking.db";

    static {
        try (Connection conn = DriverManager.getConnection(URL)) {
            Statement stmt = conn.createStatement();
            // Create tables if they don't exist
            stmt.execute("CREATE TABLE IF NOT EXISTS buses (id TEXT PRIMARY KEY, lat REAL, lon REAL, passengers INTEGER)");
            stmt.execute("CREATE TABLE IF NOT EXISTS students (username TEXT PRIMARY KEY, password TEXT, role TEXT)");
            
            // Seed data
            stmt.execute("INSERT OR IGNORE INTO buses VALUES ('BUS-001', -0.514, 37.281, 0)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
            stmt.execute("INSERT OR IGNORE INTO students VALUES ('admin', 'admin123', 'ADMIN')");
            stmt.execute("INSERT OR IGNORE INTO students VALUES ('student1', 'pass123', 'STUDENT')");
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
