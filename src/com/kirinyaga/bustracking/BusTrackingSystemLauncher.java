// BusTrackingSystemLauncher.java - Final Updated
package com.kirinyaga.bustracking;

import com.kirinyaga.bustracking.config.RouteConfigLoader;
import com.kirinyaga.bustracking.models.*;
import com.kirinyaga.bustracking.server.TrackingServer;
import com.kirinyaga.bustracking.server.RestApiServer;
import com.kirinyaga.bustracking.simulation.BusSimulationEngine;
import com.kirinyaga.bustracking.ui.control.ControlCenterApp;
import com.kirinyaga.bustracking.ui.driver.DriverApp;
import com.kirinyaga.bustracking.ui.student.StudentApp;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class BusTrackingSystemLauncher {
    private static TrackingServer trackingServer;
    private static BusSimulationEngine simulationEngine;
    private static RestApiServer apiServer;

    public static void main(String[] args) {
        try {
            // Load routes
            List<Route> routes = RouteConfigLoader.loadRoutes();
            System.out.println("Loaded " + routes.size() + " routes");

            // Start tracking server
            trackingServer = new TrackingServer();
            new Thread(() -> {
                try {
                    trackingServer.start();
                } catch (IOException e) {
                    System.err.println("Failed to start tracking server: " + e.getMessage());
                }
            }).start();

            // Start REST API server
            int apiPort = Integer.parseInt(System.getenv().getOrDefault("API_PORT", "8080"));
            apiServer = new RestApiServer(apiPort);
            new Thread(() -> {
                try {
                    apiServer.start();
                } catch (IOException e) {
                    System.err.println("Failed to start API server: " + e.getMessage());
                }
            }).start();

            // Wait for servers to start
            Thread.sleep(2000);

            // Initialize and start simulation
            simulationEngine = new BusSimulationEngine(routes, trackingServer);
            for (int i = 0; i < 5; i++) {
                Route route = routes.get(i % routes.size());
                Bus bus = new Bus("BUS-" + String.format("%03d", i + 1), route, "DRIVER-" + String.format("%03d", i + 1));
                simulationEngine.addBus(bus);
            }
            simulationEngine.start();

            System.out.println("Bus Tracking System fully operational");

            // Launch GUI applications
            SwingUtilities.invokeLater(BusTrackingSystemLauncher::showLauncherMenu);

        } catch (Exception e) {
            System.err.println("Error starting system: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void showLauncherMenu() {
        JFrame launcherFrame = new JFrame("Bus Tracking System - Launcher");
        launcherFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        launcherFrame.setSize(400, 300);
        launcherFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Bus Tracking System");
        titleLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));

        JButton studentButton = new JButton("Launch Student App");
        studentButton.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        studentButton.addActionListener(e -> new StudentApp());
        panel.add(studentButton);
        panel.add(Box.createVerticalStrut(10));

        JButton driverButton = new JButton("Launch Driver App");
        driverButton.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        driverButton.addActionListener(e -> new DriverApp());
        panel.add(driverButton);
        panel.add(Box.createVerticalStrut(10));

        JButton controlButton = new JButton("Launch Control Center");
        controlButton.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        controlButton.addActionListener(e -> new ControlCenterApp());
        panel.add(controlButton);

        launcherFrame.setContentPane(panel);
        launcherFrame.setVisible(true);
    }
}