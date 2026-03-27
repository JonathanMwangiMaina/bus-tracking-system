// ETACalculatorThread.java
package com.kirinyaga.bustracking.simulation;

import com.kirinyaga.bustracking.models.*;
import java.util.*;

public class ETACalculatorThread implements Runnable {
    private List<Bus> buses;
    private List<Route> routes;
    private Map<String, Integer> historicalDelays;
    
    public ETACalculatorThread(List<Bus> buses, List<Route> routes) {
        this.buses = buses;
        this.routes = routes;
        this.historicalDelays = new HashMap<>();
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                for (Bus bus : buses) {
                    calculateETAs(bus);
                }
                Thread.sleep(10000); // Update every 10 seconds
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private void calculateETAs(Bus bus) {
        Route route = bus.getRoute();
        int currentIndex = bus.getCurrentStopIndex();
        
        for (int i = currentIndex; i < route.getStops().size(); i++) {
            int eta = calculateETA(bus, i);
            // Store ETA (could be sent to clients)
        }
    }
    
    private int calculateETA(Bus bus, int stopIndex) {
        Route route = bus.getRoute();
        int currentIndex = bus.getCurrentStopIndex();
        
        if (stopIndex <= currentIndex) {
            return 0;
        }
        
        int eta = 0;
        List<Integer> travelTimes = route.getTravelTimes();
        
        for (int i = currentIndex + 1; i <= stopIndex; i++) {
            if (i < travelTimes.size()) {
                eta += travelTimes.get(i);
            }
        }
        
        // Factor in traffic and delays
        int delay = historicalDelays.getOrDefault(bus.getBusId(), 0);
        eta += delay;
        
        // Factor in current speed
        double speedFactor = bus.getCurrentSpeed() / 40.0; // 40 km/h is normal
        eta = (int) (eta / speedFactor);
        
        return eta;
    }
}
