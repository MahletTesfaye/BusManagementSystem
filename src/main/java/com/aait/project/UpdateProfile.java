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

@WebServlet("/update")
public class UpdateProfile extends HttpServlet {
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
        
	        HttpSession session =  request.getSession(); 
	        if (session != null) {
	            int id2 = Integer.parseInt(session.getAttribute("id").toString());
	//          update input
	            String updatedName = request.getParameter("name");
	            String updatedEmail = request.getParameter("email");
	            String updatedPassword = request.getParameter("password");
	            
                session.setAttribute("name", updatedName);
	            
	            try {
		            PreparedStatement updateStmt = conn.prepareStatement("UPDATE users SET name=?, email=?, password=? WHERE id=?;");
		            updateStmt.setString(1,	updatedName);
		            updateStmt.setString(2, updatedEmail);
		            updateStmt.setString(3, updatedPassword);
		            updateStmt.setInt(4, id2); 
		            updateStmt.executeUpdate();
		            updateStmt.close();		   
		            
		            RequestDispatcher rd=request.getRequestDispatcher("Index.jsp");
		            rd.forward(request,response);
	            
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	
	        } else {
	            // Handle the case where the session is not found or expired
	            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Session not found");
	        }
	        
        }
    }

