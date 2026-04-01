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

        // 2. Register API Routes
        context.addServlet(new ServletHolder(new HealthCheckServlet()), "/health");
        context.addServlet(new ServletHolder(new BusServlet()), "/api/buses/*");
        context.addServlet(new ServletHolder(new AuthServlet()), "/api/login");

        // 3. Register Static Resources (index.html as landing page)
        java.net.URL resourceUrl = RestApiServer.class.getClassLoader().getResource("index.html");
        if (resourceUrl != null) {
            String webRootPath = resourceUrl.toExternalForm().replace("index.html", "");
            context.setResourceBase(webRootPath);

            ServletHolder staticHolder = new ServletHolder("default", org.eclipse.jetty.servlet.DefaultServlet.class);
            staticHolder.setInitParameter("dirAllowed", "false"); 
            staticHolder.setInitParameter("welcomeServlets", "true");
            
            context.addServlet(staticHolder, "/");
        } else {
            System.err.println("Warning: index.html not found in 1 resource directory.");
        }

        // 4. Start the Web Server FIRST
        server.start();
        System.out.println("Jetty Server successfully started and listening on port: " + port);

        // 5. Start Simulation in a BACKGROUND THREAD
        Thread simulationThread = new Thread(() -> {
            try {
                System.out.println("Starting background GPS Simulation...");
                GPSDataSimulator.startSimulation();
            } catch (Exception e) {
                System.err.println("Critical Error in GPS Simulation: " + e.getMessage());
                e.printStackTrace();
            }
        });
        simulationThread.setDaemon(false); 
        simulationThread.start();

        // 6. Join the server to the main thread
        server.join();
    }

    public void stop() throws Exception {
        if (server != null) {
            server.stop();
        }
    }
}
