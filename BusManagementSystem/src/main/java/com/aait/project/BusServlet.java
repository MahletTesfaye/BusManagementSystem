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
    
}