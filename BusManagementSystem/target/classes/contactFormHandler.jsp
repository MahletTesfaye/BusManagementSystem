<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Contact Form Handler</title>
</head>
<body>
    <%-- Process the form submission and perform necessary actions --%>
    <%
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String message = request.getParameter("message");

        // Perform actions like sending an email, saving to a database, etc.
        // ...

        // Redirect back to the contact page with a success message
        response.sendRedirect("ContactUs.jsp?success=true");
		System.out.println("Message successfuly sent!!");   
     %>
   
</body>
</html>