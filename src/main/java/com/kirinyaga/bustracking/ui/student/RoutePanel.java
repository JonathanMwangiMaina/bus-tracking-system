// RoutePanel.java
package com.kirinyaga.bustracking.ui.student;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class RoutePanel extends JPanel {
    private JComboBox<String> routeCombo;
    private JTable stopsTable;
    private JLabel routeInfoLabel;
    
    public RoutePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Route selector
        JPanel selectorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selectorPanel.add(new JLabel("Select Route:"));
        String[] routes = {"Route 1: Main Campus - Town", "Route 2: Campus - Hostels", "Route 3: Campus - Shopping"};
        routeCombo = new JComboBox<>(routes);
        routeCombo.addActionListener(e -> updateRouteInfo());
        selectorPanel.add(routeCombo);
        add(selectorPanel, BorderLayout.NORTH);
        
        // Route info
        routeInfoLabel = new JLabel("Route Information");
        routeInfoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(routeInfoLabel, BorderLayout.WEST);
        
        // Stops table
        String[] columnNames = {"Stop", "ETA (min)", "Waiting", "Next Bus"};
        Object[][] data = {
            {"Main Gate", "0", "12", "BUS-001"},
            {"Library Junction", "5", "8", "BUS-001"},
            {"Town Center", "13", "5", "BUS-001"}
        };
        
        stopsTable = new JTable(data, columnNames);
        stopsTable.setFont(new Font("Arial", Font.PLAIN, 12));
        stopsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        stopsTable.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(stopsTable);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void updateRouteInfo() {
        String selected = (String) routeCombo.getSelectedItem();
        routeInfoLabel.setText("Route: " + selected + " | Total Distance: 6.2 km | Total Time: 18 min");
    }
}
