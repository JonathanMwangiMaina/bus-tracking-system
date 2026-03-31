// AnalyticsPanel.java
package com.kirinyaga.bustracking.ui.control;

import javax.swing.*;
import java.awt.*;

public class AnalyticsPanel extends JPanel {
    public AnalyticsPanel() {
        setLayout(new GridLayout(2, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(createChartPanel("Daily Ridership", "1,234 students"));
        add(createChartPanel("On-Time Performance", "94.5%"));
        add(createChartPanel("Peak Hours", "7-9 AM, 4-7 PM"));
        add(createChartPanel("Average Occupancy", "72%"));
    }
    
    private JPanel createChartPanel(String title, String value) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.setBackground(new Color(240, 240, 240));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setHorizontalAlignment(JLabel.CENTER);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);
        
        return panel;
    }
}
