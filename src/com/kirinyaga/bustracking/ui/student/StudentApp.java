// StudentApp.java
package com.kirinyaga.bustracking.ui.student;

import javax.swing.*;
import java.awt.*;

public class StudentApp extends JFrame {
    private StudentLoginPanel loginPanel;
    private StudentDashboardPanel dashboardPanel;
    
    public StudentApp() {
        setTitle("Kirinyaga University - Bus Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        
        loginPanel = new StudentLoginPanel(this);
        setContentPane(loginPanel);
        
        setVisible(true);
    }
    
    public void showDashboard(String studentId, String studentName) {
        dashboardPanel = new StudentDashboardPanel(studentId, studentName);
        setContentPane(dashboardPanel);
        revalidate();
        repaint();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentApp());
    }
}
