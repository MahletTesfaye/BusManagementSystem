package com.aait.project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/bus")
public class BusServlet extends HttpServlet {
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
        // Retrieve the search input and search option from the request parameters
        String searchInput = request.getParameter("searchInput");
        String searchOption = request.getParameter("searchOption");

        // Execute the SQL query to retrieve all bus data
        JSONArray busDataArray = new JSONArray();
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM buses");
            ResultSet rs = stmt.executeQuery();

            // Iterate over the result set and create a JSON object for each bus
            while (rs.next()) {
                JSONObject busData = new JSONObject();
                busData.put("busId", rs.getString("busId"));
                busData.put("busName", rs.getString("busName"));
                busData.put("busNumber", rs.getString("busNumber"));
                busData.put("destination", rs.getString("destination"));
                busData.put("latitude", rs.getString("latitude"));
                busData.put("longitude", rs.getString("longitude"));
                busData.put("highlighted", false); // Flag to indicate whether the bus should be highlighted
                busDataArray.put(busData);
            }

            // Close the database resources
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

       // Perform search if search input and option are provided
        if (searchInput != null && searchOption != null) {
            // Filter the bus data based on the search criteria
            JSONArray filteredBusDataArray = new JSONArray();

            for (int i = 0; i < busDataArray.length(); i++) {
                JSONObject busData = busDataArray.getJSONObject(i);
                String cellValue = "";

                if (searchOption.equals("busNumber")) {
                    cellValue = busData.getString("busNumber");
                } else if (searchOption.equals("destination")) {
                    cellValue = busData.getString("destination");
                }

                if (cellValue.toLowerCase().contains(searchInput.toLowerCase())) {
//                    busData.put("highlighted", true);
                    filteredBusDataArray.put(busData);
                }
            }

            busDataArray = filteredBusDataArray;
        }

        // Set the response content type to JSON
        response.setContentType("application/json");

        // Write the JSON array to the response
        PrintWriter out = response.getWriter();
        out.print(busDataArray.toString());
        out.flush();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String busName = request.getParameter("busName");
        String busNumber = request.getParameter("busNumber");
        String destination = request.getParameter("destination");
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");

        try {
            Connection conn = DatabaseManager.getConnection();

            // Get the current maximum ID value from the table
            int maxId = getMaxIdFromBuses(conn);

            // Increment the maxId value to get the next ID
            int newId = maxId + 1;

            PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO buses (busId, busName, busNumber, destination, latitude, longitude) VALUES (?, ?, ?, ?, ?, ?)");

            insertStmt.setInt(1, newId);
            insertStmt.setString(2, busName);
            insertStmt.setString(3, busNumber);
            insertStmt.setString(4, destination);
            insertStmt.setString(5, latitude);
            insertStmt.setString(6, longitude);
            insertStmt.executeUpdate();
            insertStmt.close();
            conn.close();
            RequestDispatcher rds = request.getRequestDispatcher("AdminDisplay.jsp");
            rds.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while storing the bus data.");
        }
    }

    private int getMaxIdFromBuses(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT MAX(busId) FROM buses");

        int maxId = 0;

        if (rs.next()) {
            maxId = rs.getInt(1);
        }

        rs.close();
        stmt.close();

        return maxId;
    }
    
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String busId = request.getParameter("busId");

        try {
            // Retrieve the current ID of the row to be deleted
            int currentId = getCurrentId(busId);

            // Perform the delete operation on the row
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM buses WHERE busId = ?");
            stmt.setString(1, busId);
            int affectedRows = stmt.executeUpdate();
            stmt.close();

            if (affectedRows > 0) {
                // Adjust the IDs of the remaining rows if necessary
                adjustRemainingIds(currentId);

                // Return success response
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                // Return error response
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Bus not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while deleting the bus.");
        }
    }

    private int getCurrentId(String busId) throws SQLException {
        int currentId = 0;
        PreparedStatement stmt = conn.prepareStatement("SELECT busId FROM buses WHERE busId = ?");
        stmt.setString(1, busId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            currentId = rs.getInt("busId");
        }
        rs.close();
        stmt.close();
        return currentId;
    }

    private void adjustRemainingIds(int currentId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE buses SET busId = busId - 1 WHERE busId > ?");
        stmt.setInt(1, currentId);
        stmt.executeUpdate();
        stmt.close();
    }
}
    