// BusSimulationEngine.java
package com.kirinyaga.bustracking.simulation;

import com.kirinyaga.bustracking.models.*;
import com.kirinyaga.bustracking.server.TrackingServer;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

public class BusSimulationEngine {
    private List<Bus> buses;
    private List<Route> routes;
    private TrackingServer trackingServer;
    private ExecutorService executorService;
    private ConcurrentHashMap<String, BusThread> busThreads;
    private volatile boolean running;
    
    public BusSimulationEngine(List<Route> routes, TrackingServer trackingServer) {
        this.routes = routes;
        this.trackingServer = trackingServer;
        this.buses = new CopyOnWriteArrayList<>();
        this.busThreads = new ConcurrentHashMap<>();
        this.executorService = Executors.newFixedThreadPool(20);
        this.running = false;
    }
    
    public void addBus(Bus bus) {
        buses.add(bus);
    }
    
    public void start() {
        running = true;
        
        // Start bus threads
        for (Bus bus : buses) {
            BusThread busThread = new BusThread(bus, trackingServer, this);
            busThreads.put(bus.getBusId(), busThread);
            executorService.execute(busThread);
        }
        
        // Start passenger generator
        executorService.execute(new PassengerGeneratorThread(routes));
        
        // Start ETA calculator
        executorService.execute(new ETACalculatorThread(buses, routes));
        
        // Start traffic simulator
        executorService.execute(new TrafficSimulatorThread(buses));
        
        // Start schedule monitor
        executorService.execute(new ScheduleMonitorThread(buses, trackingServer));
        
        System.out.println("Bus Simulation Engine started with " + buses.size() + " buses");
    }
    
    public void stop() {
        running = false;
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
    
    public List<Bus> getBuses() {
        return new ArrayList<>(buses);
    }
    
    public Bus getBusById(String busId) {
        return buses.stream()
            .filter(b -> b.getBusId().equals(busId))
            .findFirst()
            .orElse(null);
    }
}
