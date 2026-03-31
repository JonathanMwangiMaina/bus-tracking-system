package com.kirinyaga.bustracking.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirinyaga.bustracking.util.DatabaseManager;
import com.kirinyaga.bustracking.util.JwtUtil;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.Map;

public class AuthServlet extends HttpServlet {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Read JSON body: {"username": "john", "password": "123"}
        Map<String, String> credentials = mapper.readValue(req.getReader(), Map.class);
        String user = credentials.get("username");
        String pass = credentials.get("password");

        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                "SELECT * FROM students WHERE username = ? AND password = ?");
            pstmt.setString(1, user);
            pstmt.setString(2, pass);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Login Success -> Create Token
                String token = JwtUtil.generateToken(user);
                resp.setContentType("application/json");
                resp.getWriter().write("{\"token\":\"" + token + "\", \"status\":\"success\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.getWriter().write("{\"error\":\"Invalid credentials\"}");
            }
        } catch (SQLException e) {
            resp.sendError(500);
        }
    }
}
