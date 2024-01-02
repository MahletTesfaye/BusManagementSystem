<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Display</title>
<link rel="stylesheet" href="webjars/bootstrap/5.3.0/css/bootstrap.min.css">
</head>
<body>
	<a href="Admin.jsp">Admin page</a>
	<div class="d-flex justify-content-between">
				<div class="container">
				  <table class="table table-striped">
				    <thead>
				      <tr>
				        <th>Bus ID</th>
				        <th>Bus Name</th>
				        <th>Bus Number</th>
				        <th>Destination</th>
				        <th>Latitude</th>
				        <th>Longitude</th>
				        
				        
				      </tr>
				    </thead>
				    <tbody id="busTable"></tbody>
				  </table>
	</div>
	  <script>
  		function updateTable(busData) {
	    var table = document.getElementById("busTable");

	    // Clear previous table rows
	    table.innerHTML = "";

	    // Add rows to the table for each bus
	    busData.forEach(function(bus) {
	      var row = table.insertRow();
	      row.innerHTML = 
	        '<td>'+ bus.busId + '</td>'+
	        '<td>'+ bus.busName + '</td>'+
	        '<td>'+ bus.busNumber + '</td>'+
	        '<td>'+ bus.destination + '</td>'+
	        '<td>'+ bus.latitude + '</td>'+
	        '<td>'+ bus.longitude + '</td>'+
	        '<button type="button" class="btn btn-primary" onclick="editButtonClicked()">Edit</button>'+
	        '<button type="button" class="btn btn-danger" onclick="deleteButtonClicked()">Delete</button>'
	      ;
	    });
	  }

	  fetch("bus")
	    .then(response => response.json())
	    .then(data => {
	      updateTable(data);
	    });
  </script>
</body>
</html>