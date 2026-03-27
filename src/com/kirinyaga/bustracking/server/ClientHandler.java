// ClientHandler.java
package com.kirinyaga.bustracking.server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ClientHandler implements Runnable {
    private Socket socket;
    private TrackingServer server;
    private PrintWriter out;
    private BufferedReader in;
    private String clientId;
    private String clientType; // "STUDENT", "DRIVER", "CONTROL"
    
    public ClientHandler(Socket socket, TrackingServer server) {
        this.socket = socket;
        this.server = server;
    }
    
    @Override
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            // Read client identification
            String clientInfo = in.readLine();
            if (clientInfo != null) {
                parseClientInfo(clientInfo);
                server.registerClient(clientId, this);
                
                // Process client messages
                String message;
                while ((message = in.readLine()) != null) {
                    handleMessage(message);
                }
            }
        } catch (SocketException e) {
            System.out.println("Client disconnected: " + clientId);
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            cleanup();
        }
    }
    
    private void parseClientInfo(String info) {
        String[] parts = info.split("\\|");
        if (parts.length >= 2) {
            clientType = parts[0];
            clientId = parts[1];
        }
    }
    
    private void handleMessage(String message) {
        String[] parts = message.split("\\|");
        String command = parts[0];
        
        switch (command) {
            case "UPDATE_BUS":
                if (parts.length >= 6) {
                    String busId = parts[1];
                    double lat = Double.parseDouble(parts[2]);
                    double lon = Double.parseDouble(parts[3]);
                    int passengers = Integer.parseInt(parts[4]);
                    String status = parts[5];
                    server.updateBusPosition(busId, lat, lon, passengers, status);
                }
                break;
                
            case "SUBSCRIBE_STOP":
                if (parts.length >= 2) {
                    String stopId = parts[1];
                    server.registerStopSubscriber(stopId, this);
                }
                break;
                
            case "UNSUBSCRIBE_STOP":
                if (parts.length >= 2) {
                    String stopId = parts[1];
                    server.unregisterStopSubscriber(stopId, this);
                }
                break;
                
            case "ALERT":
                if (parts.length >= 3) {
                    String stopId = parts[1];
                    String alertMessage = parts[2];
                    server.notifyStopSubscribers(stopId, "ALERT|" + alertMessage);
                }
                break;
        }
    }
    
    public synchronized void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }
    
    private void cleanup() {
        try {
            server.unregisterClient(clientId);
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing client socket: " + e.getMessage());
        }
    }
}
