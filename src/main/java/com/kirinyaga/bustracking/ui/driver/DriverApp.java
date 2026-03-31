// DriverApp.java
package com.kirinyaga.bustracking.ui.driver;

import javax.swing.*;
import java.awt.*;

public class DriverApp extends JFrame {
    private DriverLoginPanel loginPanel;
    private DriverDashboardPanel dashboardPanel;
    
    public DriverApp() {
        setTitle("Bus Driver - Control Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        
        loginPanel = new DriverLoginPanel(this);
        setContentPane(loginPanel);
        
        setVisible(true);
    }
    
    public void showDashboard(String driverId, String busId) {
        dashboardPanel = new DriverDashboardPanel(driverId, busId);
        setContentPane(dashboardPanel);
        revalidate();
        repaint();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DriverApp());
    }
}
