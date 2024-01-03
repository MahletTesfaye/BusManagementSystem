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
       
            // Execute the SQL query to retrieve the bus data
    	try {
    		Connection conn = DatabaseManager.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM buses");

            JSONArray busDataArray = new JSONArray();
         // Add the buttons data to the JSON objects
            for (int i = 0; i < busDataArray.length(); i++) {
                JSONObject busData = busDataArray.getJSONObject(i);
                busData.put("editButton", "<button type=\"button\" class=\"btn btn-primary\" onclick=\"editButtonClicked('" + busData.getString("busId") + "')\">Edit</button>");
                busData.put("deleteButton", "<button type=\"button\" class=\"btn btn-danger\" onclick=\"deleteButtonClicked('" + busData.getString("busId") + "')\">Delete</button>");
            }

            // Iterate over the result set and create a JSON object for each bus
            while (rs.next()) {
                JSONObject busData = new JSONObject();
                busData.put("busId", rs.getString("busId"));
                busData.put("busName", rs.getString("busName"));
                busData.put("busNumber", rs.getString("busNumber"));
                busData.put("destination", rs.getString("destination"));
                busData.put("latitude", rs.getString("latitude"));
                busData.put("longitude", rs.getString("longitude"));
                busDataArray.put(busData);
            }

            // Close the database resources
            rs.close();
            stmt.close();
            conn.close();

            // Set the response content type to JSON
            response.setContentType("application/json");
         // Inside the doGet method
            request.setAttribute("busId", request.getParameter("busId"));

            // Write the JSON array to the response
            PrintWriter out = response.getWriter();
            out.print(busDataArray.toString());
            out.flush();
           
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String busId = request.getParameter("busId");
        String busName = request.getParameter("busName");
        String busNumber = request.getParameter("busNumber");
        String destination = request.getParameter("destination");
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");
        
        // Store the bus data in the database
        try {
        	Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO buses (busId, busName, busNumber, destination, latitude, longitude) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, busId);
            stmt.setString(2, busName);
            stmt.setString(3, busNumber);
            stmt.setString(4, destination);
            stmt.setString(5, latitude);
            stmt.setString(6, longitude);
            stmt.executeUpdate();
            stmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any database errors
            // You can redirect the user to an error page or display an error message
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while storing the bus data.");
            return;
        }

        // Transfer the bus data to another page
        request.setAttribute("busId", busId);
        request.setAttribute("busName", busName);
        request.setAttribute("busNumber", busNumber);
        request.setAttribute("destination", destination);
        request.setAttribute("latitude", latitude);
        request.setAttribute("longitude", longitude);

        RequestDispatcher rd = request.getRequestDispatcher("/Index.jsp");
        rd.forward(request, response);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String busId = request.getParameter("busId");
        String busName = request.getParameter("busName");
        String busNumber = request.getParameter("busNumber");
        String destination = request.getParameter("destination");
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");

        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE buses SET busName = ?, busNumber = ?, destination = ?, latitude = ?, longitude = ? WHERE busId = ?");
            stmt.setString(1, busName);
            stmt.setString(2, busNumber);
            stmt.setString(3, destination);
            stmt.setString(4, latitude);
            stmt.setString(5, longitude);
            stmt.setString(6, busId);
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while updating the bus data.");
            return;
        }

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.print("Bus data updated successfully");
        out.flush();
    }
    
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String busId = request.getParameter("busId");

        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM buses WHERE busId = ?");
            stmt.setString(1, busId);
            int affectedRows = stmt.executeUpdate();
            stmt.close();

            if (affectedRows > 0) {
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
    
}