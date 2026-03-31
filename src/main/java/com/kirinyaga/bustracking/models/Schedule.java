// Schedule.java
package com.kirinyaga.bustracking.models;

import java.io.Serializable;
import java.time.LocalTime;

public class Schedule implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private LocalTime firstBusTime;
    private LocalTime lastBusTime;
    private int frequencyMinutes; // Minutes between buses
    
    public Schedule(LocalTime firstBusTime, LocalTime lastBusTime, int frequencyMinutes) {
        this.firstBusTime = firstBusTime;
        this.lastBusTime = lastBusTime;
        this.frequencyMinutes = frequencyMinutes;
    }
    
    public LocalTime getFirstBusTime() { return firstBusTime; }
    public LocalTime getLastBusTime() { return lastBusTime; }
    public int getFrequencyMinutes() { return frequencyMinutes; }
}
