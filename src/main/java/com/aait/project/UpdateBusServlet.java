package com.aait.project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.sql.*;


@WebServlet("/edit")
public class UpdateBusServlet extends HttpServlet {
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
            String updatedBusId = request.getParameter("updatedBusId");
	        String updatedBusName = request.getParameter("updatedBusName");
	        String updatedBusNumber = request.getParameter("updatedBusNumber");
	        String updatedDestination = request.getParameter("updatedDestination");
	        String updatedLatitude = request.getParameter("updatedLatitude");
	        String updatedLongitude = request.getParameter("updatedLongitude");
	           //Store the bus data in the database
	        try {
	            Connection conn = DatabaseManager.getConnection();
	               PreparedStatement updateStmt=conn.prepareStatement("UPDATE buses SET busName = ?, busNumber = ?, destination = ?, latitude = ?, longitude = ? WHERE busId = ?");
	               updateStmt.setString(1, updatedBusName);
	               updateStmt.setString(2, updatedBusNumber);
	               updateStmt.setString(3, updatedDestination);
	               updateStmt.setString(4, updatedLatitude);
	               updateStmt.setString(5, updatedLongitude);
	               updateStmt.setString(6, updatedBusId);
	               updateStmt.executeUpdate();
	               updateStmt.close();
	               RequestDispatcher rd=request.getRequestDispatcher("AdminDisplay.jsp");
	               rd.forward(request,response);
	        }catch (SQLException e) {
	            e.printStackTrace();
	            // Handle any database errors
	            // You can redirect the user to an error page or display an error message
	            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while storing the bus data.");
	        }
        }
    }
 
    