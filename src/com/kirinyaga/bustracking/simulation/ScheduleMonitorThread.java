// ScheduleMonitorThread.java
package com.kirinyaga.bustracking.simulation;

import com.kirinyaga.bustracking.models.*;
import com.kirinyaga.bustracking.server.TrackingServer;
import java.util.*;

public class ScheduleMonitorThread implements Runnable {
    private List<Bus> buses;
    private TrackingServer trackingServer;
    
    public ScheduleMonitorThread(List<Bus> buses, TrackingServer trackingServer) {
        this.buses = buses;
        this.trackingServer = trackingServer;
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                for (Bus bus : buses) {
                    checkScheduleAdherence(bus);
                }
                Thread.sleep(30000); // Check every 30 seconds
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private void checkScheduleAdherence(Bus bus) {
        int minutesLate = bus.getMinutesLate();
        
        if (minutesLate > 5) {
            String alert = String.format("DELAY_ALERT|%s|%d minutes late", 
                bus.getBusId(), minutesLate);
            // Send alert to control center
        }
    }
}
