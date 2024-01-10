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
        if (request.getParameter("name")!= null) {
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
	        
	        try {
	            PreparedStatement stmnt = conn.prepareStatement("insert into users (name, email, password) values(?, ?, ?);");
	            stmnt.setString(1, name);
	            stmnt.setString(2, email);
	            stmnt.setString(3, password);
	            stmnt.executeUpdate();
	            stmnt.close();
	            
	            response.sendRedirect("Login.jsp");
	                   } catch (SQLException e) {
	            e.printStackTrace();
	        }
        }
        else {
	        HttpSession session =  request.getSession(false); 
	        if (session != null) {
	            int id2 = Integer.parseInt(session.getAttribute("id").toString());
	//          update input
	            String updatedName = request.getParameter("updatedName");
	            String updatedEmail = request.getParameter("updatedEmail");
	            String updatedPassword = request.getParameter("updatedPassword");
	            
	            
	            try {
		            PreparedStatement updateStmt = conn.prepareStatement("UPDATE users SET name=?, email=?, password=? WHERE id=?;");
		            updateStmt.setString(1,	updatedName);
		            updateStmt.setString(2, updatedEmail);
		            updateStmt.setString(3, updatedPassword);
		            updateStmt.setInt(4, id2); 
		            updateStmt.executeUpdate();
		            updateStmt.close();		   
		            
		            RequestDispatcher rd=request.getRequestDispatcher("Login.jsp");
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
}
