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
	        '<td><button type="button" class="btn btn-primary" onclick="editButtonClicked(\'' + bus.busId + '\')">Edit</button></td>'+
            '<td><button type="button" class="btn btn-danger" onclick="deleteButtonClicked(\'' + bus.busId + '\')">Delete</button></td>'
        ;
	    });
	  }

	  fetch("bus")
	    .then(response => response.json())
	    .then(data => {
	      updateTable(data);
	    });
	  
	  function editButtonClicked(busId) {
		    // Redirect to the edit page with the bus ID
		    location.href = "Edit.jsp?busId=" + busId;
		}

		function deleteButtonClicked(busId) {
		    if (confirm("Are you sure you want to delete this bus?")) {
		        // Send a DELETE request to the server to delete the bus
		        fetch("bus?busId=" + busId, { method: "DELETE" })
		            .then(response => {
		                if (response.ok) {
		                    // Reload the table after successful deletion
		                    fetch("bus")
		                        .then(response => response.json())
		                        .then(data => {
		                            updateTable(data);
		                        });
		                } else {
		                    alert("An error occurred while deleting the bus.");
		                }
		            })
		            .catch(error => {
		                alert("An error occurred while deleting the bus.");
		                console.error(error);
		            });
		    }
		}
  </script>
</body>
</html>