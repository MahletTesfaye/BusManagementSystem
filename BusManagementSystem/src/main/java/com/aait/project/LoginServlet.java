package com.aait.project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;
import java.util.*;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
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
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        // Perform validation
        List<String> errors = new ArrayList<>();
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
        try {
            String query = "select * from users where email = ? and password = ?";
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1, email);
            stmnt.setString(2, password);

            ResultSet rs = stmnt.executeQuery();
            if(rs.next()) {
                // create session
                HttpSession session = request.getSession();
                String id = rs.getString("id");
                session.setAttribute("id", id);
                String name = rs.getString("name");
                session.setAttribute("name", name);
                String email2 = rs.getString("email");
                session.setAttribute("email", email2);
                String password2 = rs.getString("password");
                session.setAttribute("password", password2);
                
                // authentication successful
                response.sendRedirect("Index.jsp");
            } else {
                // authentication failed
                response.sendRedirect("Login.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Get the current session (do not create a new one if it doesn't exist)

        if (session != null) {
            // Retrieve the user ID from the session
            String name = (String) session.getAttribute("name");

            // Clear the session
            session.invalidate();

            if (name != null) {
                // Perform the database deletion for the user
                try {
                    // Connect to the database and execute the deletion query
                    String sql = "DELETE FROM users WHERE name = ?";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setString(1, name);
                    int rowsAffected = statement.executeUpdate();

                    // Close the database resources
                    statement.close();
                    
                    if (rowsAffected > 0) {
                        // Data deleted successfully
                        response.sendRedirect("Login.jsp"); // Redirect the user to the login page
                    } else {
                        // Data deletion failed
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while deleting the User.");
                }
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "User not found");
            }
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Session not found");
        }
    }
}