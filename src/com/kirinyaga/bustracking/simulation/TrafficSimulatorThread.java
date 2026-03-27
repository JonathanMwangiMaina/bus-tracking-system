// TrafficSimulatorThread.java
package com.kirinyaga.bustracking.simulation;

import com.kirinyaga.bustracking.models.*;
import java.util.*;

public class TrafficSimulatorThread implements Runnable {
    private List<Bus> buses;
    private Random random;
    
    public TrafficSimulatorThread(List<Bus> buses) {
        this.buses = buses;
        this.random = new Random();
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                for (Bus bus : buses) {
                    simulateTraffic(bus);
                }
                Thread.sleep(15000); // Update every 15 seconds
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private void simulateTraffic(Bus bus) {
        double rand = random.nextDouble();
        
        if (rand < 0.05) {
            // 5% chance of accident (major delay)
            bus.setCurrentSpeed(10.0);
            bus.setMinutesLate(bus.getMinutesLate() + 10);
        } else if (rand < 0.20) {
            // 15% chance of heavy traffic
            bus.setCurrentSpeed(15.0);
            bus.setMinutesLate(bus.getMinutesLate() + 3);
        } else if (rand < 0.40) {
            // 20% chance of moderate traffic
            bus.setCurrentSpeed(30.0);
            bus.setMinutesLate(bus.getMinutesLate() + 1);
        } else {
            // Normal conditions
            bus.setCurrentSpeed(40.0);
        }
    }
}
