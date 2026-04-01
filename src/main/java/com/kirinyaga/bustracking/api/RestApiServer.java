package com.kirinyaga.bustracking.api;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirinyaga.bustracking.service.GPSDataSimulator;

public class RestApiServer {
    private Server server;
    private int port;

    public RestApiServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        // Initialize Jetty Server on the assigned Railway PORT
        server = new Server(port);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        // --- NEW: REGISTRATION OF THE /health ROUTE ---
        // This must match the 'Healthcheck Path' in your Railway Settings
        context.addServlet(new ServletHolder(new HealthCheckServlet()), "/health");

        // Existing Servlets
        context.addServlet(new ServletHolder(new BusServlet()), "/api/buses/*");
        context.addServlet(new ServletHolder(new AuthServlet()), "/api/login");

        // --- VERIFICATION: NON-BLOCKING STARTUP ---
        // We start the simulation BEFORE the blocking server.join() call
        // but AFTER setting up handlers to ensure we don't hang the thread.
        try {
            GPSDataSimulator.startSimulation();
            System.out.println("GPS Simulation started successfully.");
        } catch (Exception e) {
            System.err.println("Warning: GPS Simulation failed to start: " + e.getMessage());
            // We continue starting the server anyway so the Healthcheck doesn't fail
        }

        server.start();
        System.out.println("Jetty Server started on port " + port);
        
        // Use server.join() if you want the main thread to hang here 
        // until the server is stopped, preventing the JVM from exiting.
        // server.join(); 
    }

    public void stop() throws Exception {
        if (server != null) server.stop();
    }
}
