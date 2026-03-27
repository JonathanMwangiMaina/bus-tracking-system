// BusStop.java
package com.kirinyaga.bustracking.models;

import java.io.Serializable;

public class BusStop implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String stopId;
    private String stopName;
    private double latitude;
    private double longitude;
    private int waitingStudents;
    
    public BusStop(String stopId, String stopName, double latitude, double longitude) {
        this.stopId = stopId;
        this.stopName = stopName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.waitingStudents = 0;
    }
    
    // Getters & Setters
    public String getStopId() { return stopId; }
    public String getStopName() { return stopName; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public int getWaitingStudents() { return waitingStudents; }
    
    public void setWaitingStudents(int count) { this.waitingStudents = Math.max(0, count); }
    public void addWaitingStudent() { this.waitingStudents++; }
    public void removeWaitingStudent() { 
        if (this.waitingStudents > 0) this.waitingStudents--; 
    }
    
    public double distanceTo(BusStop other) {
        // Haversine formula for GPS distance
        final int R = 6371; // Earth radius in km
        double latDistance = Math.toRadians(other.latitude - this.latitude);
        double lonDistance = Math.toRadians(other.longitude - this.longitude);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(other.latitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
