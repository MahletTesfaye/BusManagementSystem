
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Bus</title>
    <link rel="stylesheet" href="webjars/bootstrap/5.3.0/css/bootstrap.min.css">
    <style>
  </style>
</head>
<body class="bg-secondary text-white w-50 ">
	
	<div><a href="javascript:history.back()" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a></div>
	<div class="p-5">
	    <%-- Retrieve the query parameters --%>
	    <% String busId = request.getParameter("busId");
	       String busName = request.getParameter("busName");
	       String busNumber = request.getParameter("busNumber");
	       String destination = request.getParameter("destination");
	       String latitude = request.getParameter("latitude");
	       String longitude = request.getParameter("longitude");
	    %>
	    <form id="updateForm" class="m-auto" action="bus" method="post"  >
	        <h2>Admin - Edit bus</h2>
	                <div class="mb-3">
	            <label for="busId" class="form-label">Bus ID: </label>
	            <input id="busId" name="updatedBusId" type="number" class="form-control" value="<%= busId %>">
	        </div>
	        <div class="mb-3">
	            <label for="busName" class="form-label">Bus Name: </label>
	            <input type="text" id="busName" name="updatedBusName" class="form-control" value="<%= busName %>">
	        </div>
	        <div class="mb-3">
	            <label for="busNumber" class="form-label">Bus Number: </label>
	            <input type="text" id="busNumber" name="updatedBusNumber" class="form-control" value="<%= busNumber %>">
	        </div>
	        <div class="mb-3">
	            <label for="destination" class="form-label">Destination: </label>
	            <input type="text" id="destination" name="updatedDestination" class="form-control" value="<%= destination %>">
	        </div>
	        <div class="mb-3">
	            <label for="latitude" class="form-label">Latitude: </label>
	            <input type="text" id="latitude" name="updatedLatitude" class="form-control" value="<%= latitude %>">
	        </div>
	        <div class="mb-3">
	            <label for="longitude" class="form-label">Longitude: </label>
	            <input type="text" id="longitude" name="updatedLongitude" class="form-control" value="<%= longitude %>">
	        </div>
	
	        <input id="updateButton" type="submit" class="btn btn-primary" value="Update">
	    </form>
	</div>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
	    // JavaScript code for handling form submission and AJAX request
	    document.getElementById('updateForm').addEventListener('submit', function(e) {
	        e.preventDefault();
	
	   	     // Collect the form data
	        var formData = {
	            busId: document.getElementById('busId').value,
	            busName: document.getElementById('busName').value,
	            busNumber: document.getElementById('busNumber').value,
	            destination: document.getElementById('destination').value,
	            latitude: document.getElementById('latitude').value,
	            longitude: document.getElementById('longitude').value
	        };
	     
	
	        
	</script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/js/all.min.js"></script>
</body>
</html>