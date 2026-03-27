// FleetViewPanel.java
package com.kirinyaga.bustracking.ui.control;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class FleetViewPanel extends JPanel {
    private JTable fleetTable;
    
    public FleetViewPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title
        JLabel titleLabel = new JLabel("Live Fleet Status");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(titleLabel, BorderLayout.NORTH);
        
        // Fleet table
        String[] columnNames = {"Bus ID", "Route", "Status", "Location", "Speed", "Passengers", "Schedule", "Last Update"};
        Object[][] data = {
            {"BUS-001", "Route 1", "ON_ROUTE", "Library Junction", "40 km/h", "35/50", "On Time", "14:32:15"},
            {"BUS-002", "Route 2", "AT_STOP", "Hostel A", "0 km/h", "42/50", "2 min late", "14:32:10"},
            {"BUS-003", "Route 3", "ON_ROUTE", "Shopping Centre", "35 km/h", "28/50", "On Time", "14:32:12"},
            {"BUS-004", "Route 1", "BREAK", "Main Gate", "0 km/h", "0/50", "N/A", "14:30:00"},
            {"BUS-005", "Route 2", "ON_ROUTE", "Hostel B", "40 km/h", "38/50", "On Time", "14:32:14"}
        };
        
        fleetTable = new JTable(data, columnNames);
        fleetTable.setFont(new Font("Arial", Font.PLAIN, 11));
        fleetTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        fleetTable.setRowHeight(25);
        fleetTable.setDefaultRenderer(Object.class, new FleetCellRenderer());
        
        JScrollPane scrollPane = new JScrollPane(fleetTable);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private static class FleetCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                                                      boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (!isSelected) {
                String status = (String) table.getValueAt(row, 2);
                if ("ON_ROUTE".equals(status)) {
                    c.setBackground(new Color(200, 255, 200));
                } else if ("AT_STOP".equals(status)) {
                    c.setBackground(new Color(255, 255, 200));
                } else if ("BREAK".equals(status)) {
                    c.setBackground(new Color(200, 200, 200));
                } else if ("EMERGENCY".equals(status)) {
                    c.setBackground(new Color(255, 200, 200));
                }
            }
            
            return c;
        }
    }
}
