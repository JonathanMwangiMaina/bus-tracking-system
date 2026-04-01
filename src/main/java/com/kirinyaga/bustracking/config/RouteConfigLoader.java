package com.kirinyaga.bustracking.config;

import com.kirinyaga.bustracking.models.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class RouteConfigLoader {
    private static final String ROUTES_RESOURCE = "/data/routes.json";
    
    public static List<Route> loadRoutes() throws IOException {
        List<Route> routes = new ArrayList<>();
        
        try (InputStream is = RouteConfigLoader.class.getResourceAsStream(ROUTES_RESOURCE)) {
            if (is == null) {
                throw new FileNotFoundException("Resource not found: " + ROUTES_RESOURCE);
            }

            String content;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                content = reader.lines().collect(Collectors.joining("\n"));
            }

            JSONArray routesArray = new JSONArray(content);
            for (int i = 0; i < routesArray.length(); i++) {
                routes.add(parseRoute(routesArray.getJSONObject(i)));
            }
        }
        return routes;
    }

    private static Route parseRoute(JSONObject routeObj) {
        Route route = new Route(routeObj.getString("routeId"), routeObj.getString("routeName"), routeObj.getString("color"));
        JSONArray stopsArray = routeObj.getJSONArray("stops");
        for (int i = 0; i < stopsArray.length(); i++) {
            JSONObject stopObj = stopsArray.getJSONObject(i);
            BusStop stop = new BusStop(stopObj.getString("stopId"), stopObj.getString("stopName"), stopObj.getDouble("latitude"), stopObj.getDouble("longitude"));
            route.addStop(stop, (i == 0) ? 0 : stopObj.getDouble("distanceFromPrevious"), (i == 0) ? 0 : stopObj.getInt("travelTimeFromPrevious"));
        }
        JSONObject sch = routeObj.getJSONObject("schedule");
        route.setSchedule(new Schedule(LocalTime.parse(sch.getString("firstBusTime")), LocalTime.parse(sch.getString("lastBusTime")), sch.getInt("frequencyMinutes")));
        return route;
    }
}
