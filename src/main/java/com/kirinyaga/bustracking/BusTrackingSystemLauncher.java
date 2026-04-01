package com.kirinyaga.bustracking;

import com.kirinyaga.bustracking.api.RestApiServer;
import com.kirinyaga.bustracking.config.RouteConfigLoader;
import com.kirinyaga.bustracking.models.Route;
import java.util.List;

public class BusTrackingSystemLauncher {
    public static void main(String[] args) {
        try {
            System.out.println("Initializing Bus Tracking System...");
            
            // 1. Load configuration
            List<Route> routes = RouteConfigLoader.loadRoutes();
            System.out.println("Loaded " + routes.size() + " routes.");

            // 2. Determine Port (Railway uses the PORT env var)
            String portStr = System.getenv("PORT");
            int port = (portStr != null) ? Integer.parseInt(portStr) : 8080;

            // 3. Start Server
            RestApiServer server = new RestApiServer(port);
            server.start();
            
        } catch (Exception e) {
            System.err.println("FATAL: System failed to start.");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
