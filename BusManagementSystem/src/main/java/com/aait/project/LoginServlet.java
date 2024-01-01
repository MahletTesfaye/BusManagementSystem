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
            	String name = rs.getString("name");
            	session.setAttribute("name", name);
            	
            	//authentication successful
	            response.sendRedirect("Index.jsp");
            }else {
            //authentication failed
	            response.sendRedirect("Login.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}