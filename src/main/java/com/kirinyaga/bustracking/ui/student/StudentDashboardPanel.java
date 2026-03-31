// StudentDashboardPanel.java
package com.kirinyaga.bustracking.ui.student;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class StudentDashboardPanel extends JPanel {
    private String studentId;
    private String studentName;
    private JTabbedPane tabbedPane;
    private MapPanel mapPanel;
    private RoutePanel routePanel;
    private TripPlannerPanel tripPlannerPanel;
    private NotificationPanel notificationPanel;
    
    public StudentDashboardPanel(String studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
        
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Tabbed Pane
        tabbedPane = new JTabbedPane();
        mapPanel = new MapPanel();
        routePanel = new RoutePanel();
        tripPlannerPanel = new TripPlannerPanel();
        notificationPanel = new NotificationPanel();
        
        tabbedPane.addTab("Live Map", mapPanel);
        tabbedPane.addTab("Routes", routePanel);
        tabbedPane.addTab("Trip Planner", tripPlannerPanel);
        tabbedPane.addTab("Notifications", notificationPanel);
        
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0, 102, 204));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel welcomeLabel = new JLabel("Welcome, " + studentName);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setForeground(Color.WHITE);
        
        JLabel idLabel = new JLabel("ID: " + studentId);
        idLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        idLabel.setForeground(Color.WHITE);
        
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setOpaque(false);
        leftPanel.add(welcomeLabel);
        leftPanel.add(idLabel);
        
        panel.add(leftPanel, BorderLayout.WEST);
        
        return panel;
    }
}
