package com.aait.project;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import jakarta.servlet.http.HttpSession;

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

        if (errors.isEmpty()) {
            try {
                // Get the current maximum ID value from the table
                int maxId = getMaxIdFromUsers(conn);

                // Adjust the ID if the database is empty
                int newId;
                if (maxId == 0) {
                    newId = 1; // Start ID from 1 if the database is empty
                } else {
                    newId = maxId + 1; // Increment the maximum ID to generate the next ID
                }

                PreparedStatement stmnt = conn.prepareStatement("insert into users (id, name, email, password) values (?, ?, ?, ?);");
                stmnt.setInt(1, newId);
                stmnt.setString(2, name);
                stmnt.setString(3, email);
                stmnt.setString(4, password);
                stmnt.executeUpdate();
                stmnt.close();

                response.sendRedirect("Login.jsp");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
        	 request.setAttribute("errors", errors);
        	 request.getRequestDispatcher("/Registration.jsp").forward(request, response);
        }
    }

    private int getMaxIdFromUsers(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COALESCE(MAX(id), 0) FROM users");

        int maxId = 0;

        if (rs.next()) {
            maxId = rs.getInt(1);
        }

        rs.close();
        stmt.close();

        return maxId;
    }
    
}

