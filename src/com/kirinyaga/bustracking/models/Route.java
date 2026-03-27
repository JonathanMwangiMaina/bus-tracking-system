// Route.java
package com.kirinyaga.bustracking.models;

import java.io.Serializable;
import java.util.*;

public class Route implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String routeId;
    private String routeName;
    private String color;
    private List<BusStop> stops;
    private List<Double> distances; // km between consecutive stops
    private List<Integer> travelTimes; // minutes between stops
    private Schedule schedule;
    
    public Route(String routeId, String routeName, String color) {
        this.routeId = routeId;
        this.routeName = routeName;
        this.color = color;
        this.stops = new ArrayList<>();
        this.distances = new ArrayList<>();
        this.travelTimes = new ArrayList<>();
    }
    
    public void addStop(BusStop stop, double distanceFromPrevious, int travelTimeFromPrevious) {
        stops.add(stop);
        distances.add(distanceFromPrevious);
        travelTimes.add(travelTimeFromPrevious);
    }
    
    // Getters
    public String getRouteId() { return routeId; }
    public String getRouteName() { return routeName; }
    public String getColor() { return color; }
    public List<BusStop> getStops() { return new ArrayList<>(stops); }
    public List<Double> getDistances() { return new ArrayList<>(distances); }
    public List<Integer> getTravelTimes() { return new ArrayList<>(travelTimes); }
    public Schedule getSchedule() { return schedule; }
    
    public void setSchedule(Schedule schedule) { this.schedule = schedule; }
    
    public double getTotalDistance() {
        return distances.stream().mapToDouble(Double::doubleValue).sum();
    }
    
    public int getTotalTravelTime() {
        return travelTimes.stream().mapToInt(Integer::intValue).sum();
    }
    
    public BusStop getStopByIndex(int index) {
        return (index >= 0 && index < stops.size()) ? stops.get(index) : null;
    }
    
    public int getStopIndex(String stopId) {
        for (int i = 0; i < stops.size(); i++) {
            if (stops.get(i).getStopId().equals(stopId)) {
                return i;
            }
        }
        return -1;
    }
}
