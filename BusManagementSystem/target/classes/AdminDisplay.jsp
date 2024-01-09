<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Display</title>
<link rel="stylesheet" href="webjars/bootstrap/5.3.0/css/bootstrap.min.css">
</head>
<body class="p-5">
  <a class="btn btn-secondary" href="Admin.jsp" role="button" > <i class="fas fa-arrow-left"></i>  Admin Home page</a>
  <div class="d-flex justify-content-between">
        <div class="container">
        <form action="bus" method="get">
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
        </form>
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
            '<td><button type="button" class="btn btn-primary" onclick="editButtonClicked(\'' + bus.busId + '\', \'' + bus.busName + '\', \'' + bus.busNumber + '\', \'' + bus.destination + '\', \'' + bus.latitude + '\', \'' + bus.longitude + '\')">Edit</button></td>'+
            '<td><button type="button" class="btn btn-danger" onclick="deleteButtonClicked(\'' + bus.busId + '\')">Delete</button></td>';
        });
      }

      fetch("bus")
        .then(response => response.json())
        .then(data => {
          updateTable(data);
        });

      function editButtonClicked(busId, busName, busNumber, destination, latitude, longitude) {
        var url = "Edit.jsp";

        // Append the query parameters to the URL
        url += "?busId=" + busId;
        url += "&busName=" + encodeURIComponent(busName);
        url += "&busNumber=" + encodeURIComponent(busNumber);
        url += "&destination=" + encodeURIComponent(destination);
        url += "&latitude=" + encodeURIComponent(latitude);
        url += "&longitude=" + encodeURIComponent(longitude);

        // Redirect to the new URL
        location.href = url;
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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/js/all.min.js"></script>
</body>
</html>