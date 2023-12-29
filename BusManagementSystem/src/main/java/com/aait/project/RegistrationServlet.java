package com.aait.project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection conn; // Declare the Connection object as an instance variable
    
    @Override
    public void init() throws ServletException {
        super.init();
        try {
        	conn = DatabaseManager.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
     // Perform validation
        List<String> errors = new ArrayList<>();

        if (name == null || name.trim().isEmpty()) {
            errors.add("Name is required");
        }

        if (email == null || email.trim().isEmpty()) {
            errors.add("Email is required");
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            errors.add("Invalid email format");
        }

        if (password == null || password.trim().isEmpty()) {
            errors.add("Password is required");
        } else if (password.length() < 8) {
            errors.add("Password must be at least 8 characters long");
        }
//        if (!errors.isEmpty()) {
//            // If there are validation errors, store the errors in request scope and forward to the registration page
//            request.setAttribute("errors", errors);
//            request.getRequestDispatcher("Registration.jsp").forward(request, response);
//            return;
//        }
      
        try {
            String query = "insert into users (name, email, password) values(?, ?, ?);";
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1, name);
            stmnt.setString(2, email);
            stmnt.setString(3, password);

            stmnt.executeUpdate();
            response.sendRedirect("Confirmation.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}