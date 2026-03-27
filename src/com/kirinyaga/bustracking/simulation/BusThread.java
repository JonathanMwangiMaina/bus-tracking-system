// BusThread.java
package com.kirinyaga.bustracking.simulation;

import com.kirinyaga.bustracking.models.*;
import com.kirinyaga.bustracking.server.TrackingServer;
import java.time.LocalDateTime;
import java.util.Random;

public class BusThread implements Runnable {
    private Bus bus;
    private TrackingServer trackingServer;
    private BusSimulationEngine engine;
    private Random random;
    private volatile boolean running;
    
    public BusThread(Bus bus, TrackingServer trackingServer, BusSimulationEngine engine) {
        this.bus = bus;
        this.trackingServer = trackingServer;
        this.engine = engine;
        this.random = new Random();
        this.running = true;
    }
    
    @Override
    public void run() {
        try {
            while (running) {
                if (bus.getStatus() == Bus.BusStatus.ON_ROUTE) {
                    moveBus();
                    updateTrackingServer();
                    Thread.sleep(2000); // Update every 2 seconds
                } else if (bus.getStatus() == Bus.BusStatus.AT_STOP) {
                    handleStopLogic();
                    Thread.sleep(3000);
                } else {
                    Thread.sleep(5000);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Bus thread interrupted: " + bus.getBusId());
        }
    }
    
    private void moveBus() {
        Route route = bus.getRoute();
        int currentIndex = bus.getCurrentStopIndex();
        
        if (currentIndex >= route.getStops().size() - 1) {
            // Reached end of route, restart
            bus.setCurrentStopIndex(0);
            return;
        }
        
        BusStop currentStop = route.getStopByIndex(currentIndex);
        BusStop nextStop = route.getStopByIndex(currentIndex + 1);
        
        if (currentStop != null && nextStop != null) {
            // Simulate movement towards next stop
            double distance = currentStop.distanceTo(nextStop);
            double speed = bus.getCurrentSpeed();
            
            // Move bus incrementally
            double moveDistance = (speed / 3600.0) * 2; // 2 seconds of movement
            
            if (moveDistance >= distance) {
                // Reached next stop
                bus.setLatitude(nextStop.getLatitude());
                bus.setLongitude(nextStop.getLongitude());
                bus.setCurrentStopIndex(currentIndex + 1);
                bus.setStatus(Bus.BusStatus.AT_STOP);
            } else {
                // Interpolate position
                double ratio = moveDistance / distance;
                double newLat = currentStop.getLatitude() + 
                    (nextStop.getLatitude() - currentStop.getLatitude()) * ratio;
                double newLon = currentStop.getLongitude() + 
                    (nextStop.getLongitude() - currentStop.getLongitude()) * ratio;
                
                bus.setLatitude(newLat);
                bus.setLongitude(newLon);
            }
        }
    }
    
    private void handleStopLogic() {
        BusStop currentStop = bus.getCurrentStop();
        if (currentStop != null) {
            // Simulate passenger boarding/alighting
            int boardingPassengers = Math.min(
                currentStop.getWaitingStudents(),
                bus.getCapacity() - bus.getPassengerCount()
            );
            
            for (int i = 0; i < boardingPassengers; i++) {
                bus.addPassenger();
                currentStop.removeWaitingStudent();
            }
            
            // After 5 seconds at stop, depart
            if (bus.getCurrentStopIndex() < bus.getRoute().getStops().size() - 1) {
                bus.setStatus(Bus.BusStatus.ON_ROUTE);
            }
        }
    }
    
    private void updateTrackingServer() {
        trackingServer.updateBusPosition(
            bus.getBusId(),
            bus.getLatitude(),
            bus.getLongitude(),
            bus.getPassengerCount(),
            bus.getStatus().toString()
        );
    }
    
    public void stop() {
        running = false;
    }
}
