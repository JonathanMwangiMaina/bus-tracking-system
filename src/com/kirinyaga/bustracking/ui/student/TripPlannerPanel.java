// TripPlannerPanel.java
package com.kirinyaga.bustracking.ui.student;

import javax.swing.*;
import java.awt.*;

public class TripPlannerPanel extends JPanel {
    private JComboBox<String> startStopCombo;
    private JComboBox<String> endStopCombo;
    private JLabel resultLabel;
    private JTextArea resultArea;
    
    public TripPlannerPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        
        String[] stops = {"Main Gate", "Library Junction", "Town Center", "Hostel A", "Hostel B", "Shopping Centre"};
        
        inputPanel.add(new JLabel("From:"));
        startStopCombo = new JComboBox<>(stops);
        inputPanel.add(startStopCombo);
        
        inputPanel.add(new JLabel("To:"));
        endStopCombo = new JComboBox<>(stops);
        inputPanel.add(endStopCombo);
        
        JButton planButton = new JButton("Plan Trip");
        planButton.addActionListener(e -> planTrip());
        inputPanel.add(planButton);
        
        add(inputPanel, BorderLayout.NORTH);
        
        // Result area
        resultArea = new JTextArea();
        resultArea.setFont(new Font("Arial", Font.PLAIN, 12));
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void planTrip() {
        String from = (String) startStopCombo.getSelectedItem();
        String to = (String) endStopCombo.getSelectedItem();
        
        if (from.equals(to)) {
            JOptionPane.showMessageDialog(this, "Start and end stops must be different", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        StringBuilder result = new StringBuilder();
        result.append("Trip Plan: ").append(from).append(" → ").append(to).append("\n\n");
        result.append("Route: Route 1 (Direct)\n");
        result.append("Estimated Travel Time: 18 minutes\n");
        result.append("Fare: KES 50\n");
        result.append("Next Bus: BUS-001 (ETA: 5 minutes)\n");
        result.append("Occupancy: 65% (Yellow)\n");
        
        resultArea.setText(result.toString());
    }
}
