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

import org.json.JSONArray;
import org.json.JSONObject;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Execute the SQL query to retrieve all bus data
        JSONArray userDataArray = new JSONArray();
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users");
            ResultSet rs = stmt.executeQuery();

            // Iterate over the result set and create a JSON object for each bus
            while (rs.next()) {
                JSONObject userData = new JSONObject();
                userData.put("id", rs.getString("id"));
                userData.put("name", rs.getString("name"));
                userData.put("email", rs.getString("email"));
                
                userData.put("highlighted", false); // Flag to indicate whether the bus should be highlighted
                userDataArray.put(userData);
            }

            // Close the database resources
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
     // Set the response content type to JSON
        response.setContentType("application/json");

        // Write the JSON data to the response
        response.getWriter().write(userDataArray.toString());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
       
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
                
                if (email.equals("Admin@gmail.com") && password.equals("admin2024")) {
                	response.sendRedirect("Index.jsp");                
                } else {
                // authentication successful
                response.sendRedirect("Index.jsp");
                }
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
                    // Get the ID of the user to be deleted
                    int userId = getUserId(name);

                    // Connect to the database and execute the deletion query
                    String deleteSql = "DELETE FROM users WHERE name = ?";
                    PreparedStatement deleteStatement = conn.prepareStatement(deleteSql);
                    deleteStatement.setString(1, name);
                    int rowsAffected = deleteStatement.executeUpdate();

                    // Adjust the IDs of the remaining users if necessary
                    adjustRemainingIds(userId);

                    // Close the database resources
                    deleteStatement.close();

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

    private int getUserId(String name) throws SQLException {
        int userId = 0;
        String selectSql = "SELECT id FROM users WHERE name = ?";
        PreparedStatement selectStatement = conn.prepareStatement(selectSql);
        selectStatement.setString(1, name);
        ResultSet resultSet = selectStatement.executeQuery();
        if (resultSet.next()) {
            userId = resultSet.getInt("id");
        }
        resultSet.close();
        selectStatement.close();
        return userId;
    }

    private void adjustRemainingIds(int deletedUserId) throws SQLException {
        String updateSql = "UPDATE users SET id = id - 1 WHERE id > ?";
        PreparedStatement updateStatement = conn.prepareStatement(updateSql);
        updateStatement.setInt(1, deletedUserId);
        updateStatement.executeUpdate();
        updateStatement.close();
    }
}