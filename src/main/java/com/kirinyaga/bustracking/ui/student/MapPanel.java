// MapPanel.java
package com.kirinyaga.bustracking.ui.student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class MapPanel extends JPanel {
    private double zoomLevel = 1.0;
    private int panX = 0;
    private int panY = 0;
    
    public MapPanel() {
        setBackground(Color.WHITE);
        addMouseWheelListener(e -> {
            if (e.getWheelRotation() < 0) {
                zoomLevel *= 1.1;
            } else {
                zoomLevel /= 1.1;
            }
            repaint();
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw map background
        g2d.setColor(new Color(200, 220, 240));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        // Draw grid
        g2d.setColor(new Color(180, 200, 220));
        g2d.setStroke(new BasicStroke(1));
        for (int i = 0; i < getWidth(); i += 50) {
            g2d.drawLine(i, 0, i, getHeight());
        }
        for (int i = 0; i < getHeight(); i += 50) {
            g2d.drawLine(0, i, getWidth(), i);
        }
        
        // Draw routes (simplified)
        drawRoutes(g2d);
        
        // Draw bus stops
        drawBusStops(g2d);
        
        // Draw buses
        drawBuses(g2d);
        
        // Draw zoom level
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.drawString("Zoom: " + String.format("%.1f", zoomLevel) + "x", 10, 20);
    }
    
    private void drawRoutes(Graphics2D g2d) {
        // Route 1: Red
        g2d.setColor(new Color(255, 0, 0, 100));
        g2d.setStroke(new BasicStroke(3));
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
    
    private void drawBusStops(Graphics2D g2d) {
        // Draw stop icons
        int[][] stops = {
            {100, 100}, {300, 150}, {400, 200}, {150, 300}, {200, 400}, {500, 150}
        };
        String[] stopNames = {"Main Gate", "Library", "Town", "Hostel A", "Hostel B", "Shopping"};
        
        for (int i = 0; i < stops.length; i++) {
            g2d.setColor(new Color(255, 200, 0));
            g2d.fillOval(stops[i][0] - 8, stops[i][1] - 8, 16, 16);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(stops[i][0] - 8, stops[i][1] - 8, 16, 16);
            
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            g2d.drawString(stopNames[i], stops[i][0] + 10, stops[i][1]);
        }
    }
    
    private void drawBuses(Graphics2D g2d) {
        // Draw bus icons
        int[][] busPositions = {
            {150, 120}, {350, 180}, {180, 320}
        };
        String[] busIds = {"BUS-001", "BUS-002", "BUS-003"};
        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN};
        
        for (int i = 0; i < busPositions.length; i++) {
            g2d.setColor(colors[i]);
            g2d.fillRect(busPositions[i][0] - 10, busPositions[i][1] - 8, 20, 16);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 8));
            g2d.drawString(busIds[i], busPositions[i][0] - 8, busPositions[i][1] + 2);
        }
    }
}
