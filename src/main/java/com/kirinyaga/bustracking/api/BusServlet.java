package com.kirinyaga.bustracking.api;

import jakarta.servlet.http.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirinyaga.bustracking.util.DatabaseManager;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class BusServlet extends HttpServlet {
    private final ObjectMapper mapper = new ObjectMapper();

    // GET: Fetch all buses
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Map<String, Object>> buses = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM buses");
            while (rs.next()) {
                Map<String, Object> bus = new HashMap<>();
                bus.put("busId", rs.getString("id"));
                bus.put("lat", rs.getDouble("lat"));
                bus.put("lng", rs.getDouble("lon"));
                bus.put("passengers", rs.getInt("passengers"));
                buses.add(bus);
            }
        } catch (SQLException e) { resp.sendError(500); return; }
        
        resp.setContentType("application/json");
        mapper.writeValue(resp.getWriter(), buses);
    }

    // POST: Check-in a student
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String busId = req.getParameter("busId");
        String action = req.getParameter("action"); // "increment" or "decrement"

        try (Connection conn = DatabaseManager.getConnection()) {
            // Validation Logic: Ensure no negative passenger count
            String sql = action.equals("increment") ? 
                "UPDATE buses SET passengers = passengers + 1 WHERE id = ?" :
                "UPDATE buses SET passengers = MAX(0, passengers - 1) WHERE id = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, busId);
            pstmt.executeUpdate();
            
            resp.setStatus(200);
            resp.getWriter().write("{\"status\":\"success\"}");
        } catch (SQLException e) {
            resp.sendError(500);
        }
    }
}
