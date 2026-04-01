package com.kirinyaga.bustracking.api;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import com.kirinyaga.bustracking.service.GPSDataSimulator;

public class RestApiServer {
    private Server server;
    private final int port;

    public RestApiServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        // 1. Initialize Server
        server = new Server(port);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        // 2. Register Routes
        context.addServlet(new ServletHolder(new HealthCheckServlet()), "/health");
        context.addServlet(new ServletHolder(new BusServlet()), "/api/buses/*");
        context.addServlet(new ServletHolder(new AuthServlet()), "/api/login");

        // 3. Start the Web Server FIRST
        // This ensures the /health endpoint is live immediately
        server.start();
        System.out.println("Jetty Server successfully started and listening on port: " + port);

        // 4. Start Simulation in a BACKGROUND THREAD
        // This prevents the simulation from blocking the web server
        Thread simulationThread = new Thread(() -> {
            try {
                System.out.println("Starting background GPS Simulation...");
                GPSDataSimulator.startSimulation();
            } catch (Exception e) {
                System.err.println("Critical Error in GPS Simulation: " + e.getMessage());
                e.printStackTrace();
            }
        });
        simulationThread.setDaemon(false); // Ensure simulation keeps JVM alive if needed
        simulationThread.start();

        // 5. Join the server to the main thread
        // This keeps the process running and responsive to Jetty events
        server.join();
    }

    public void stop() throws Exception {
        if (server != null) {
            server.stop();
        }
    }
}
