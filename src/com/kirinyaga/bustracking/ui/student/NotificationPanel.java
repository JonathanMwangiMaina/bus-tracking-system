// NotificationPanel.java
package com.kirinyaga.bustracking.ui.student;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NotificationPanel extends JPanel {
    private JList<String> notificationList;
    private DefaultListModel<String> listModel;
    
    public NotificationPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title
        JLabel titleLabel = new JLabel("Notifications");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(titleLabel, BorderLayout.NORTH);
        
        // Notification list
        listModel = new DefaultListModel<>();
        addSampleNotifications();
        
        notificationList = new JList<>(listModel);
        notificationList.setFont(new Font("Arial", Font.PLAIN, 12));
        notificationList.setCellRenderer(new NotificationCellRenderer());
        
        JScrollPane scrollPane = new JScrollPane(notificationList);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void addSampleNotifications() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        
        listModel.addElement("[" + LocalDateTime.now().format(formatter) + "] BUS-001 arriving at Main Gate in 5 minutes");
        listModel.addElement("[" + LocalDateTime.now().minusMinutes(10).format(formatter) + "] BUS-002 delayed by 10 minutes");
        listModel.addElement("[" + LocalDateTime.now().minusMinutes(30).format(formatter) + "] Heavy traffic on Route 1");
    }
    
    private static class NotificationCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                                                     boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            String text = (String) value;
            if (text.contains("arriving")) {
                label.setForeground(new Color(0, 150, 0));
            } else if (text.contains("delayed")) {
                label.setForeground(new Color(200, 0, 0));
            } else if (text.contains("traffic")) {
                label.setForeground(new Color(200, 100, 0));
            }
            
            return label;
        }
    }
}
