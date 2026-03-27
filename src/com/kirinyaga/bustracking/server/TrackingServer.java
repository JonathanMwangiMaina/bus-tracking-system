// TrackingServer.java
package com.kirinyaga.bustracking.server;

import com.kirinyaga.bustracking.models.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class TrackingServer {
    private static final int PORT = 7070;
    private static final int THREAD_POOL_SIZE = 20;
    
    private ServerSocket serverSocket;
    private ExecutorService threadPool;
    private ConcurrentHashMap<String, BusPosition> busPositions;
    private ConcurrentHashMap<String, List<ClientHandler>> stopSubscribers;
    private ConcurrentHashMap<String, ClientHandler> connectedClients;
    private volatile boolean running;
    
    public TrackingServer() {
        this.threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        this.busPositions = new ConcurrentHashMap<>();
        this.stopSubscribers = new ConcurrentHashMap<>();
        this.connectedClients = new ConcurrentHashMap<>();
        this.running = false;
    }
    
    public void start() throws IOException {
        serverSocket = new ServerSocket(PORT);
        running = true;
        System.out.println("Tracking Server started on port " + PORT);
        
        // Start broadcast thread
        threadPool.execute(this::broadcastUpdates);
        
        // Accept client connections
        while (running) {
            try {
                Socket clientSocket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(clientSocket, this);
                threadPool.execute(handler);
            } catch (SocketException e) {
                if (running) {
                    System.err.println("Server socket error: " + e.getMessage());
                }
            }
        }
    }
    
    private void broadcastUpdates() {
        while (running) {
            try {
                Thread.sleep(5000); // Broadcast every 5 seconds
                
                // Send bus positions to all connected clients
                String updateMessage = buildUpdateMessage();
                
                for (ClientHandler client : connectedClients.values()) {
                    client.sendMessage(updateMessage);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    private String buildUpdateMessage() {
        StringBuilder sb = new StringBuilder("UPDATE|");
        for (BusPosition pos : busPositions.values()) {
            sb.append(pos.toBusString()).append(";");
        }
        return sb.toString();
    }
    
    public void updateBusPosition(String busId, double latitude, double longitude, 
                                  int passengers, String status) {
        BusPosition pos = new BusPosition(busId, latitude, longitude, passengers, status);
        busPositions.put(busId, pos);
    }
    
    public void registerStopSubscriber(String stopId, ClientHandler client) {
        stopSubscribers.computeIfAbsent(stopId, k -> new CopyOnWriteArrayList<>()).add(client);
    }
    
    public void unregisterStopSubscriber(String stopId, ClientHandler client) {
        List<ClientHandler> subscribers = stopSubscribers.get(stopId);
        if (subscribers != null) {
            subscribers.remove(client);
        }
    }
    
    public void registerClient(String clientId, ClientHandler handler) {
        connectedClients.put(clientId, handler);
    }
    
    public void unregisterClient(String clientId) {
        connectedClients.remove(clientId);
    }
    
    public void notifyStopSubscribers(String stopId, String message) {
        List<ClientHandler> subscribers = stopSubscribers.get(stopId);
        if (subscribers != null) {
            for (ClientHandler client : subscribers) {
                client.sendMessage(message);
            }
        }
    }
    
    public void shutdown() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing server socket: " + e.getMessage());
        }
        threadPool.shutdown();
    }
    
    public static void main(String[] args) {
        TrackingServer server = new TrackingServer();
        try {
            server.start();
        } catch (IOException e) {
            System.err.println("Failed to start server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
