<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Properties" %>
<%@ page import="javax.mail.*" %>
<%@ page import="javax.mail.internet.MimeMessage" %>
<%@ page import="javax.mail.internet.InternetAddress" %>


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

        try {
            if (email != null && !email.trim().isEmpty()) {
                // Set up mail server properties
                Properties properties = new Properties();
                properties.put("mail.smtp.host", "smtp.gmail.com");
                properties.put("mail.smtp.port", "587");
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");

                // Create a mail session with the properties
                Session session2 = Session.getInstance(properties, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("mahlettenayeah94@gmail.com", "jkqc fugm ulnb mjpm");
                    }
                });

                // Create a new email message
                MimeMessage mimeMessage = new MimeMessage(session2);
                mimeMessage.setFrom(new InternetAddress("mahlettenayeah94@gmail.com"));
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                mimeMessage.setSubject("Thank you for contacting us");
                mimeMessage.setText("Dear " + name + ",\n\nThank you for reaching out to us. We have received your message and will get back to you shortly.\n\nBest regards,\nYour Company");

                // Send the email
                Transport.send(mimeMessage);

                // Redirect back to the contact page with a success message
                response.sendRedirect("ContactUs.jsp?success=true");
                System.out.println("Message successfully sent!!");
            } else {
                // Redirect back to the contact page with an error message
                response.sendRedirect("ContactUs.jsp?success=false");
            }
        } catch (Exception e) {
            // Handle any exceptions that occur during the email sending process
            e.printStackTrace();
            response.sendRedirect("ContactUs.jsp?success=false");
        }
    %>
</body>
</html>