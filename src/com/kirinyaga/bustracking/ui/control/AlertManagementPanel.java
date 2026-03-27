// AlertManagementPanel.java
package com.kirinyaga.bustracking.ui.control;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class AlertManagementPanel extends JPanel {
    private JTable alertTable;
    
    public AlertManagementPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title
        JLabel titleLabel = new JLabel("Active Alerts");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(titleLabel, BorderLayout.NORTH);
        
        // Alert table
        String[] columnNames = {"Time", "Severity", "Bus", "Message", "Action"};
        Object[][] data = {
            {"14:32", "WARNING", "BUS-002", "2 minutes late", "Acknowledge"},
            {"14:25", "INFO", "BUS-001", "Heavy traffic on Route 1", "Dismiss"},
            {"14:15", "CRITICAL", "BUS-004", "Mechanical issue reported", "Respond"}
        };
        
        alertTable = new JTable(data, columnNames);
        alertTable.setFont(new Font("Arial", Font.PLAIN, 11));
        alertTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        alertTable.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(alertTable);
        add(scrollPane, BorderLayout.CENTER);
        
        // Action panel
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        JButton broadcastButton = new JButton("Broadcast Message");
        broadcastButton.addActionListener(e -> broadcastMessage());
        actionPanel.add(broadcastButton);
        
        JButton contactButton = new JButton("Contact Driver");
        contactButton.addActionListener(e -> contactDriver());
        actionPanel.add(contactButton);
        
        add(actionPanel, BorderLayout.SOUTH);
    }
    
    private void broadcastMessage() {
        String message = JOptionPane.showInputDialog(this, "Enter message to broadcast:");
        if (message != null && !message.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Message broadcast to all students", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void contactDriver() {
        String driverId = JOptionPane.showInputDialog(this, "Enter driver ID:");
        if (driverId != null && !driverId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Message sent to driver " + driverId, "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
