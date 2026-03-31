// RouteManagementPanel.java
package com.kirinyaga.bustracking.ui.control;

import javax.swing.*;
import java.awt.*;

public class RouteManagementPanel extends JPanel {
    public RouteManagementPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title
        JLabel titleLabel = new JLabel("Route Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(titleLabel, BorderLayout.NORTH);
        
        // Control panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        JButton addStopButton = new JButton("Add Temporary Stop");
        addStopButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Add stop functionality"));
        controlPanel.add(addStopButton);
        
        JButton adjustScheduleButton = new JButton("Adjust Schedule");
        adjustScheduleButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Adjust schedule functionality"));
        controlPanel.add(adjustScheduleButton);
        
        JButton addBusButton = new JButton("Add Extra Bus");
        addBusButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Add bus functionality"));
        controlPanel.add(addBusButton);
        
        add(controlPanel, BorderLayout.CENTER);
    }
}
