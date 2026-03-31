// BusPosition.java
package com.kirinyaga.bustracking.server;

import java.time.LocalDateTime;

public class BusPosition {
    private String busId;
    private double latitude;
    private double longitude;
    private int passengers;
    private String status;
    private LocalDateTime timestamp;
    
    public BusPosition(String busId, double latitude, double longitude, 
                      int passengers, String status) {
        this.busId = busId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.passengers = passengers;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
    
    public String toBusString() {
        return String.format("%s,%.4f,%.4f,%d,%s,%s", 
            busId, latitude, longitude, passengers, status, timestamp);
    }
    
    // Getters
    public String getBusId() { return busId; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public int getPassengers() { return passengers; }
    public String getStatus() { return status; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
