// MapViewPanel.java
package com.kirinyaga.bustracking.ui.control;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class MapViewPanel extends JPanel {
    public MapViewPanel() {
        setBackground(Color.WHITE);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw map background
        g2d.setColor(new Color(200, 220, 240));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        // Draw routes
        drawRoutes(g2d);
        
        // Draw stops
        drawStops(g2d);
        
        // Draw buses
        drawBuses(g2d);
    }
    
    private void drawRoutes(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(3));
        
        // Route 1: Red
        g2d.setColor(new Color(255, 0, 0, 100));
        g2d.drawLine(100, 100, 300, 150);
        g2d.drawLine(300, 150, 400, 200);
        
        // Route 2: Blue
        g2d.setColor(new Color(0, 0, 255, 100));
        g2d.drawLine(100, 100, 150, 300);
        g2d.drawLine(150, 300, 200, 400);
        
        // Route 3: Green
        g2d.setColor(new Color(0, 200, 0, 100));
        g2d.drawLine(100, 100, 450, 100);
        g2d.drawLine(450, 100, 500, 150);
    }
    
    private void drawStops(Graphics2D g2d) {
        int[][] stops = {{100, 100}, {300, 150}, {400, 200}, {150, 300}, {200, 400}, {500, 150}};
        String[] names = {"Main Gate", "Library", "Town", "Hostel A", "Hostel B", "Shopping"};
        
        for (int i = 0; i < stops.length; i++) {
            g2d.setColor(new Color(255, 200, 0));
            g2d.fillOval(stops[i][0] - 8, stops[i][1] - 8, 16, 16);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(stops[i][0] - 8, stops[i][1] - 8, 16, 16);
            
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            g2d.drawString(names[i], stops[i][0] + 10, stops[i][1]);
        }
    }
    
    private void drawBuses(Graphics2D g2d) {
        int[][] positions = {{150, 120}, {350, 180}, {180, 320}, {100, 100}, {450, 120}};
        String[] ids = {"BUS-001", "BUS-002", "BUS-003", "BUS-004", "BUS-005"};
        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.GRAY, Color.RED};
        
        for (int i = 0; i < positions.length; i++) {
            g2d.setColor(colors[i]);
            g2d.fillRect(positions[i][0] - 10, positions[i][1] - 8, 20, 16);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 8));
            g2d.drawString(ids[i], positions[i][0] - 8, positions[i][1] + 2);
        }
    }
}
