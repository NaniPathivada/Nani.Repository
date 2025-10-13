package com.example.demo.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    // Display login page
    @GetMapping("/")
    public String showLoginPage() {
        return "login"; // loads /WEB-INF/jsp/login.jsp
    }

    // Handle login form submission
    @PostMapping("/handleLogin")
    public String handleLogin(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Simple form validation
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Username and password are required!");
            request.setAttribute("username", username);
            return "login";
        }

        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            String url = "jdbc:mysql://localhost:3306/ecommerce_jsp";
            String dbUser = "root"; // update if different
            String dbPass = "9014840907@mysql"; // replace with your MySQL password
            Connection conn = DriverManager.getConnection(url, dbUser, dbPass);

            // Check if the user exists
            String sql = "SELECT id FROM users WHERE username=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Valid user -> store in session
                int userId = rs.getInt("id");
                HttpSession session = request.getSession();
                session.setAttribute("userId", userId);
                session.setAttribute("username", username);

                // Close DB resources
                rs.close();
                ps.close();
                conn.close();

                // Redirect to home page controller
                return "redirect:/home";
            } else {
                // Invalid login
                request.setAttribute("errorMessage", "Invalid username or password!");
                request.setAttribute("username", username);

                // Close DB resources
                rs.close();
                ps.close();
                conn.close();

                return "login";
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.setAttribute("username", username);
            return "login";
        }
    }
}