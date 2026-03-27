// DriverLoginPanel.java
package com.kirinyaga.bustracking.ui.driver;

import javax.swing.*;
import java.awt.*;

public class DriverLoginPanel extends JPanel {
    private JTextField driverIdField;
    private JPasswordField passwordField;
    private JComboBox<String> busCombo;
    private DriverApp parentFrame;
    
    public DriverLoginPanel(DriverApp parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 240, 240));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Title
        JLabel titleLabel = new JLabel("Driver Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);
        
        // Driver ID
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        add(new JLabel("Driver ID:"), gbc);
        gbc.gridx = 1;
        driverIdField = new JTextField(20);
        add(driverIdField, gbc);
        
        // Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        add(passwordField, gbc);
        
        // Bus Assignment
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Assigned Bus:"), gbc);
        gbc.gridx = 1;
        String[] buses = {"BUS-001", "BUS-002", "BUS-003", "BUS-004", "BUS-005"};
        busCombo = new JComboBox<>(buses);
        add(busCombo, gbc);
        
        // Login Button
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(0, 102, 204));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(e -> handleLogin());
        add(loginButton, gbc);
    }
    
    private void handleLogin() {
        String driverId = driverIdField.getText().trim();
        String password = new String(passwordField.getPassword());
        String busId = (String) busCombo.getSelectedItem();
        
        if (driverId.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        parentFrame.showDashboard(driverId, busId);
    }
}
