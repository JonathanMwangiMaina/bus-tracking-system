// ControlCenterApp.java
package com.kirinyaga.bustracking.ui.control;

import javax.swing.*;
import java.awt.*;

public class ControlCenterApp extends JFrame {
    private ControlCenterDashboard dashboard;
    
    public ControlCenterApp() {
        setTitle("Bus Tracking Control Center");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 900);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        dashboard = new ControlCenterDashboard();
        setContentPane(dashboard);
        
        setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ControlCenterApp());
    }
}
