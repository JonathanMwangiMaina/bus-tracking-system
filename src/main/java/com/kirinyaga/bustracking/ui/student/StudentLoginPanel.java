// StudentLoginPanel.java
package com.kirinyaga.bustracking.ui.student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudentLoginPanel extends JPanel {
    private JTextField studentNumberField;
    private JTextField nameField;
    private JTextField phoneField;
    private JComboBox<String> favoriteStopCombo;
    private StudentApp parentFrame;
    
    public StudentLoginPanel(StudentApp parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 240, 240));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Title
        JLabel titleLabel = new JLabel("Kirinyaga University Bus Tracker");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);
        
        // Student Number
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        add(new JLabel("Student Number:"), gbc);
        gbc.gridx = 1;
        studentNumberField = new JTextField(20);
        add(studentNumberField, gbc);
        
        // Name
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        add(nameField, gbc);
        
        // Phone
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Phone Number:"), gbc);
        gbc.gridx = 1;
        phoneField = new JTextField(20);
        add(phoneField, gbc);
        
        // Favorite Stop
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Favorite Stop:"), gbc);
        gbc.gridx = 1;
        String[] stops = {"Main Gate", "Library Junction", "Town Center", "Hostel A", "Hostel B", "Shopping Centre"};
        favoriteStopCombo = new JComboBox<>(stops);
        add(favoriteStopCombo, gbc);
        
        // Login Button
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(0, 102, 204));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(e -> handleLogin());
        add(loginButton, gbc);
    }
    
    private void handleLogin() {
        String studentNumber = studentNumberField.getText().trim();
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        
        if (studentNumber.isEmpty() || name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        parentFrame.showDashboard(studentNumber, name);
    }
}
