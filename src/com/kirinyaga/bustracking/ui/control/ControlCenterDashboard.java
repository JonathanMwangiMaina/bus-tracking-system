// ControlCenterDashboard.java
package com.kirinyaga.bustracking.ui.control;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class ControlCenterDashboard extends JPanel {
    private JTabbedPane tabbedPane;
    private FleetViewPanel fleetViewPanel;
    private MapViewPanel mapViewPanel;
    private RouteManagementPanel routeManagementPanel;
    private AlertManagementPanel alertManagementPanel;
    private AnalyticsPanel analyticsPanel;
    
    public ControlCenterDashboard() {
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Tabbed pane
        tabbedPane = new JTabbedPane();
        
        fleetViewPanel = new FleetViewPanel();
        mapViewPanel = new MapViewPanel();
        routeManagementPanel = new RouteManagementPanel();
        alertManagementPanel = new AlertManagementPanel();
        analyticsPanel = new AnalyticsPanel();
        
        tabbedPane.addTab("Fleet View", fleetViewPanel);
        tabbedPane.addTab("Map View", mapViewPanel);
        tabbedPane.addTab("Route Management", routeManagementPanel);
        tabbedPane.addTab("Alerts", alertManagementPanel);
        tabbedPane.addTab("Analytics", analyticsPanel);
        
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0, 51, 102));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Bus Tracking Control Center");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel statusLabel = new JLabel("System Status: OPERATIONAL | Active Buses: 5 | Total Students: 1,234");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setForeground(Color.WHITE);
        
        panel.add(titleLabel, BorderLayout.WEST);
        panel.add(statusLabel, BorderLayout.EAST);
        
        return panel;
    }
}
