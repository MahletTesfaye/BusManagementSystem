<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registration page</title>
</head>
<body>
	<h1>Welcome to our registration page!</h1>
	<h2>Registration Form</h2>
	
	<form action="register" method="post">
		Name: <input type="text" name= "name" placeholder="Alex" required/><br>
		<% 
		if (request.getAttribute("errors") != null) {
            List<String> errors = (List<String>) request.getAttribute("errors");
        	if (errors.contains("Name is required")){
        		out.println("Name is required!");
        	}
		}
        %>
		Email: <input type="email" name="email" placeholder="alex@gmail.com" required/><br>
		<% 
		if (request.getAttribute("errors") != null) {
            List<String> errors = (List<String>) request.getAttribute("errors");
        	if (errors.contains("Email is required")){
        		out.println("Email is required!");
        	}
        	else if(errors.contains("Invalid email format")){
        		out.println("Invalid- email format!");
        	}
		}
        %>
		Password: <input type="password" name="password" required/><br>
		<% 
		if (request.getAttribute("errors") != null) {
            List<String> errors = (List<String>) request.getAttribute("errors");
        	if (errors.contains("Password is required")){
        		out.println("Password is required!");
        	}
        	else if(errors.contains("Password must be at least 8 characters long")){
        		out.println("Invalid- email format!");
        	}
		}
        %>
		<input type="submit" value="Register"/>
	</form>
	
</body>
</html>