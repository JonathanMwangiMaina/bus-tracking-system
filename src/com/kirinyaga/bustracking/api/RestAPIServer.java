package com.kirinyaga.bustracking.api;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.*;

public class RestApiServer {
    private HttpServer server;
    private int port;
    
    public RestApiServer(int port) {
        this.port = port;
    }
    
    public void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        
        // Health check endpoint
        server.createContext("/health", exchange -> {
            String response = "{\"status\":\"UP\",\"timestamp\":\"" + System.currentTimeMillis() + "\"}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        });
        
        // API endpoints
        server.createContext("/api/buses", new BusesHandler());
        server.createContext("/api/routes", new RoutesHandler());
        server.createContext("/api/stops", new StopsHandler());
        server.createContext("/api/students", new StudentsHandler());
        server.createContext("/api/notifications", new NotificationsHandler());
        server.createContext("/api/alerts", new AlertsHandler());
        
        server.setExecutor(java.util.concurrent.Executors.newFixedThreadPool(10));
        server.start();
        
        System.out.println("REST API Server started on port " + port);
    }
    
    public void stop() {
        if (server != null) {
            server.stop(0);
        }
    }
    
    // Handler classes
    static class BusesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                String response = "[{\"busId\":\"BUS-001\",\"status\":\"ON_ROUTE\",\"passengers\":35}]";
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                exchange.getResponseBody().write(response.getBytes());
            }
            exchange.close();
        }
    }
    
    static class RoutesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "[{\"routeId\":\"ROUTE-001\",\"routeName\":\"Main Campus - Town\"}]";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        }
    }
    
    static class StopsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "[{\"stopId\":\"STOP-001\",\"stopName\":\"Main Gate\"}]";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        }
    }
    
    static class StudentsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, 0);
            exchange.close();
        }
    }
    
    static class NotificationsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, 0);
            exchange.close();
        }
    }
    
    static class AlertsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, 0);
            exchange.close();
        }
    }
}