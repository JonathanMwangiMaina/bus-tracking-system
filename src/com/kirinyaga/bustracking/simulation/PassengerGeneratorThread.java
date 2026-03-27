// PassengerGeneratorThread.java
package com.kirinyaga.bustracking.simulation;

import com.kirinyaga.bustracking.models.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class PassengerGeneratorThread implements Runnable {
    private List<Route> routes;
    private Random random;
    
    public PassengerGeneratorThread(List<Route> routes) {
        this.routes = routes;
        this.random = new Random();
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                LocalTime now = LocalTime.now();
                int arrivalRate = calculateArrivalRate(now);
                
                // Generate passengers for each stop
                for (Route route : routes) {
                    for (BusStop stop : route.getStops()) {
                        // Poisson distribution for realistic arrivals
                        int newPassengers = poissonRandom(arrivalRate);
                        for (int i = 0; i < newPassengers; i++) {
                            stop.addWaitingStudent();
                        }
                    }
                }
                
                Thread.sleep(10000); // Generate every 10 seconds
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private int calculateArrivalRate(LocalTime time) {
        // Peak hours: 7-9 AM and 4-7 PM
        if ((time.getHour() >= 7 && time.getHour() < 9) ||
            (time.getHour() >= 16 && time.getHour() < 19)) {
            return 8; // Higher rate during peak hours
        } else if (time.getHour() >= 6 && time.getHour() < 22) {
            return 3; // Normal rate
        } else {
            return 1; // Low rate at night
        }
    }
    
    private int poissonRandom(double lambda) {
        double L = Math.exp(-lambda);
        int k = 0;
        double p = 1.0;
        
        do {
            k++;
            p *= random.nextDouble();
        } while (p > L);
        
        return k - 1;
    }
}
