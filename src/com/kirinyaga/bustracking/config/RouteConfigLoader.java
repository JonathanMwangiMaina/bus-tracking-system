// RouteConfigLoader.java
package com.kirinyaga.bustracking.config;

import com.kirinyaga.bustracking.models.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.*;

public class RouteConfigLoader {
    private static final String ROUTES_FILE = "data/routes.json";
    
    public static List<Route> loadRoutes() throws IOException {
        List<Route> routes = new ArrayList<>();
        
        String content = new String(Files.readAllBytes(Paths.get(ROUTES_FILE)));
        JSONArray routesArray = new JSONArray(content);
        
        for (int i = 0; i < routesArray.length(); i++) {
            JSONObject routeObj = routesArray.getJSONObject(i);
            Route route = parseRoute(routeObj);
            routes.add(route);
        }
        
        return routes;
    }
    
    private static Route parseRoute(JSONObject routeObj) {
        String routeId = routeObj.getString("routeId");
        String routeName = routeObj.getString("routeName");
        String color = routeObj.getString("color");
        
        Route route = new Route(routeId, routeName, color);
        
        // Parse stops
        JSONArray stopsArray = routeObj.getJSONArray("stops");
        for (int i = 0; i < stopsArray.length(); i++) {
            JSONObject stopObj = stopsArray.getJSONObject(i);
            BusStop stop = new BusStop(
                stopObj.getString("stopId"),
                stopObj.getString("stopName"),
                stopObj.getDouble("latitude"),
                stopObj.getDouble("longitude")
            );
            
            double distance = (i == 0) ? 0 : stopObj.getDouble("distanceFromPrevious");
            int travelTime = (i == 0) ? 0 : stopObj.getInt("travelTimeFromPrevious");
            
            route.addStop(stop, distance, travelTime);
        }
        
        // Parse schedule
        JSONObject scheduleObj = routeObj.getJSONObject("schedule");
        Schedule schedule = new Schedule(
            LocalTime.parse(scheduleObj.getString("firstBusTime")),
            LocalTime.parse(scheduleObj.getString("lastBusTime")),
            scheduleObj.getInt("frequencyMinutes")
        );
        route.setSchedule(schedule);
        
        return route;
    }
    
    public static void saveRoutes(List<Route> routes) throws IOException {
        JSONArray routesArray = new JSONArray();
        
        for (Route route : routes) {
            JSONObject routeObj = new JSONObject();
            routeObj.put("routeId", route.getRouteId());
            routeObj.put("routeName", route.getRouteName());
            routeObj.put("color", route.getColor());
            
            JSONArray stopsArray = new JSONArray();
            List<BusStop> stops = route.getStops();
            List<Double> distances = route.getDistances();
            List<Integer> travelTimes = route.getTravelTimes();
            
            for (int i = 0; i < stops.size(); i++) {
                BusStop stop = stops.get(i);
                JSONObject stopObj = new JSONObject();
                stopObj.put("stopId", stop.getStopId());
                stopObj.put("stopName", stop.getStopName());
                stopObj.put("latitude", stop.getLatitude());
                stopObj.put("longitude", stop.getLongitude());
                
                if (i > 0) {
                    stopObj.put("distanceFromPrevious", distances.get(i));
                    stopObj.put("travelTimeFromPrevious", travelTimes.get(i));
                }
                
                stopsArray.put(stopObj);
            }
            routeObj.put("stops", stopsArray);
            
            Schedule schedule = route.getSchedule();
            JSONObject scheduleObj = new JSONObject();
            scheduleObj.put("firstBusTime", schedule.getFirstBusTime().toString());
            scheduleObj.put("lastBusTime", schedule.getLastBusTime().toString());
            scheduleObj.put("frequencyMinutes", schedule.getFrequencyMinutes());
            routeObj.put("schedule", scheduleObj);
            
            routesArray.put(routeObj);
        }
        
        Files.write(Paths.get(ROUTES_FILE), routesArray.toString(2).getBytes());
    }
}
