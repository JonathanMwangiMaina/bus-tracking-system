// DriverDashboardPanel.java
package com.kirinyaga.bustracking.ui.driver;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class DriverDashboardPanel extends JPanel {
    private String driverId;
    private String busId;
    private JLabel busInfoLabel;
    private JLabel statusLabel;
    private JLabel nextStopLabel;
    private JLabel passengerCountLabel;
    
    public DriverDashboardPanel(String driverId, String busId) {
        this.driverId = driverId;
        this.busId = busId;
        
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Center: Map and info
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(createMapPanel(), BorderLayout.CENTER);
        centerPanel.add(createInfoPanel(), BorderLayout.EAST);
        add(centerPanel, BorderLayout.CENTER);
        
        // Bottom: Control panel
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0, 102, 204));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel driverLabel = new JLabel("Driver: " + driverId + " | Bus: " + busId);
        driverLabel.setFont(new Font("Arial", Font.BOLD, 16));
        driverLabel.setForeground(Color.WHITE);
        
        panel.add(driverLabel, BorderLayout.WEST);
        
        return panel;
    }
    
    private JPanel createMapPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                
                // Draw simplified route map
                g2d.setColor(new Color(200, 220, 240));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Draw route
                g2d.setColor(Color.RED);
                g2d.setStroke(new BasicStroke(3));
                g2d.drawLine(50, 100, 200, 150);
                g2d.drawLine(200, 150, 350, 200);
                
                // Draw stops
                int[][] stops = {{50, 100}, {200, 150}, {350, 200}};
                for (int[] stop : stops) {
                    g2d.setColor(new Color(255, 200, 0));
                    g2d.fillOval(stop[0] - 8, stop[1] - 8, 16, 16);
                    g2d.setColor(Color.BLACK);
                    g2d.drawOval(stop[0] - 8, stop[1] - 8, 16, 16);
                }
                
                // Draw current position
                g2d.setColor(Color.RED);
                g2d.fillOval(195, 145, 10, 10);
            }
        };
        panel.setBackground(Color.WHITE);
        return panel;
    }
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(240, 240, 240));
        
        busInfoLabel = new JLabel("Bus: " + busId + " | Route: Route 1");
        busInfoLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(busInfoLabel);
        
        statusLabel = new JLabel("Status: ON_ROUTE");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(statusLabel);
        
        nextStopLabel = new JLabel("Next Stop: Library Junction (5 km)");
        nextStopLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(nextStopLabel);
        
        passengerCountLabel = new JLabel("Passengers: 35/50");
        passengerCountLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(passengerCountLabel);
        
        JLabel scheduleLabel = new JLabel("Schedule: On Time");
        scheduleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        scheduleLabel.setForeground(new Color(0, 150, 0));
        panel.add(scheduleLabel);
        
        return panel;
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JButton arrivedButton = new JButton("Arrived at Stop");
        arrivedButton.setFont(new Font("Arial", Font.BOLD, 12));
        arrivedButton.setBackground(new Color(0, 150, 0));
        arrivedButton.setForeground(Color.WHITE);
        arrivedButton.addActionListener(e -> handleArrived());
        panel.add(arrivedButton);
        
        JButton departingButton = new JButton("Departing");
        departingButton.setFont(new Font("Arial", Font.BOLD, 12));
        departingButton.setBackground(new Color(0, 102, 204));
        departingButton.setForeground(Color.WHITE);
        departingButton.addActionListener(e -> handleDeparting());
        panel.add(departingButton);
        
        JButton delayButton = new JButton("Report Delay");
        delayButton.setFont(new Font("Arial", Font.BOLD, 12));
        delayButton.setBackground(new Color(200, 100, 0));
        delayButton.setForeground(Color.WHITE);
        delayButton.addActionListener(e -> handleDelay());
        panel.add(delayButton);
        
        JButton emergencyButton = new JButton("Emergency");
        emergencyButton.setFont(new Font("Arial", Font.BOLD, 12));
        emergencyButton.setBackground(new Color(200, 0, 0));
        emergencyButton.setForeground(Color.WHITE);
        emergencyButton.addActionListener(e -> handleEmergency());
        panel.add(emergencyButton);
        
        return panel;
    }
    
    private void handleArrived() {
        JOptionPane.showMessageDialog(this, "Bus marked as arrived at stop", "Success", JOptionPane.INFORMATION_MESSAGE);
        statusLabel.setText("Status: AT_STOP");
    }
    
    private void handleDeparting() {
        JOptionPane.showMessageDialog(this, "Bus marked as departing", "Success", JOptionPane.INFORMATION_MESSAGE);
        statusLabel.setText("Status: ON_ROUTE");
    }
    
    private void handleDelay() {
        String[] reasons = {"Traffic", "Mechanical Issue", "Accident", "Road Works", "Other"};
        String reason = (String) JOptionPane.showInputDialog(this, "Select delay reason:", "Report Delay", 
            JOptionPane.QUESTION_MESSAGE, null, reasons, reasons[0]);
        
        if (reason != null) {
            JOptionPane.showMessageDialog(this, "Delay reported: " + reason, "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void handleEmergency() {
        int result = JOptionPane.showConfirmDialog(this, "Confirm emergency alert?", "Emergency", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Emergency alert sent to control center", "Alert Sent", JOptionPane.INFORMATION_MESSAGE);
            statusLabel.setText("Status: EMERGENCY");
        }
    }
}
