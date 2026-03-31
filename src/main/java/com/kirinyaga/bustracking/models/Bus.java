// Bus.java
package com.kirinyaga.bustracking.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Bus implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum BusStatus {
        ON_ROUTE, AT_STOP, BREAK, OFF_DUTY, EMERGENCY
    }
    
    private String busId;
    private Route route;
    private int currentStopIndex;
    private int passengerCount;
    private static final int CAPACITY = 50;
    private double currentSpeed; // km/h
    private BusStatus status;
    private LocalDateTime lastUpdate;
    private double latitude;
    private double longitude;
    private String driverId;
    private int minutesLate;
    
    public Bus(String busId, Route route, String driverId) {
        this.busId = busId;
        this.route = route;
        this.driverId = driverId;
        this.currentStopIndex = 0;
        this.passengerCount = 0;
        this.currentSpeed = 40.0;
        this.status = BusStatus.ON_ROUTE;
        this.lastUpdate = LocalDateTime.now();
        this.minutesLate = 0;
        
        // Initialize position at first stop
        BusStop firstStop = route.getStopByIndex(0);
        if (firstStop != null) {
            this.latitude = firstStop.getLatitude();
            this.longitude = firstStop.getLongitude();
        }
    }
    
    // Getters & Setters
    public String getBusId() { return busId; }
    public Route getRoute() { return route; }
    public int getCurrentStopIndex() { return currentStopIndex; }
    public int getPassengerCount() { return passengerCount; }
    public int getCapacity() { return CAPACITY; }
    public double getCurrentSpeed() { return currentSpeed; }
    public BusStatus getStatus() { return status; }
    public LocalDateTime getLastUpdate() { return lastUpdate; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getDriverId() { return driverId; }
    public int getMinutesLate() { return minutesLate; }
    
    public void setCurrentStopIndex(int index) { this.currentStopIndex = index; }
    public void setPassengerCount(int count) { this.passengerCount = Math.min(count, CAPACITY); }
    public void setCurrentSpeed(double speed) { this.currentSpeed = speed; }
    public void setStatus(BusStatus status) { this.status = status; }
    public void setLastUpdate(LocalDateTime time) { this.lastUpdate = time; }
    public void setLatitude(double lat) { this.latitude = lat; }
    public void setLongitude(double lon) { this.longitude = lon; }
    public void setMinutesLate(int minutes) { this.minutesLate = minutes; }
    
    public void addPassenger() {
        if (passengerCount < CAPACITY) passengerCount++;
    }
    
    public void removePassenger() {
        if (passengerCount > 0) passengerCount--;
    }
    
    public double getOccupancyPercentage() {
        return (passengerCount * 100.0) / CAPACITY;
    }
    
    public BusStop getCurrentStop() {
        return route.getStopByIndex(currentStopIndex);
    }
    
    public BusStop getNextStop() {
        return route.getStopByIndex(currentStopIndex + 1);
    }
    
    public String getOccupancyStatus() {
        double occupancy = getOccupancyPercentage();
        if (occupancy < 50) return "GREEN";
        else if (occupancy < 80) return "YELLOW";
        else return "RED";
    }
}
