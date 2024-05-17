<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>UserData</title>
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
              	<th>UserId</th>
                <th>UserName</th>
                <th>Email</th>                
              </tr>
            </thead>
            <tbody id="userTable"></tbody>
          </table>
        </form>
  </div>
    <script>
      function updateTable(userData) {
        var table = document.getElementById("userTable");

        // Clear previous table rows
        table.innerHTML = "";

        // Add rows to the table for each bus
        userData.forEach(function(user) {
          var row = table.insertRow();
          row.innerHTML = 
        	'<td>'+ user.id + '</td>'+
            '<td>'+ user.name + '</td>'+
            '<td>'+ user.email + '</td>';
          // '<td><button type="button" class="btn btn-danger" onclick="deleteButtonClicked(\'' + user.id + '\')">Delete</button></td>';

        });
      }

      fetch("login")
        .then(response => response.json())
        .then(data => {
          updateTable(data);
        });


      /** function deleteButtonClicked(id) {
        if (confirm("Are you sure you want to delete this user?")) {
          // Send a DELETE request to the server to delete the bus
          fetch("login?id=" + id, { method: "DELETE" })
            .then(response => {
              if (response.ok) {
                // Reload the table after successful deletion
                fetch("login")
                  .then(response => response.json())
                  .then(data => {
                    updateTable(data);
                  });
              } else {
                alert("An error occurred while deleting the user.");
              }
            })
            .catch(error => {
              alert("An error occurred while deleting the user.");
              console.error(error);
            });
        }
      } **/
      
    </script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/js/all.min.js"></script>
</body>
</html>