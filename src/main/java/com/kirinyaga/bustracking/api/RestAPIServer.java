package com.kirinyaga.bustracking.api;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirinyaga.bustracking.service.GPSDataSimulator;

public class RestApiServer {
    private Server server;
    private int port;
    private static final ObjectMapper mapper = new ObjectMapper();

    public RestApiServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        server = new Server(port);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        // Add Servlets (API Endpoints)
        context.addServlet(new ServletHolder(new BusServlet()), "/api/buses/*");
        context.addServlet(new ServletHolder(new AuthServlet()), "/api/login");

        // Start GPS Simulation
        GPSDataSimulator.startSimulation();

        server.start();
        System.out.println("Jetty Server started on port " + port);
    }

    public void stop() throws Exception {
        if (server != null) server.stop();
    }
}
