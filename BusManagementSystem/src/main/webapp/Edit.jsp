<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Bus</title>
<link rel="stylesheet" href="webjars/bootstrap/5.3.0/css/bootstrap.min.css">
</head>
<body>
    
    <form action="bus" method="PUT" class=" m-5">
    	  <h2>Admin -> Edit bus</h2>
          <div class="mb-3">
		    <label for="busId" class="form-label">Bus ID</label>
		    <input name="busId" value="<%= request.getParameter("busId") %>">
		   </div>
           <div class="mb-3">
               <label for="busName" class="form-label">Bus Name</label>
               <input type="text" id="busName" name="busName" value="<%= request.getParameter("busName") %>"><br><br>
           </div>
           <div class="mb-3">
               <label for="busNumber" class="form-label">Bus Number</label>
               <input type="text" id="busNumber" name="busNumber" value="<%= request.getParameter("busNumber") %>"><br><br>
           </div>
           <div class="mb-3">
               <label for="destination" class="form-label">Destination</label>
               <input type="text" id="destination" name="destination" value="<%= request.getParameter("destination") %>"><br><br>
           </div>
           <div class="mb-3">
               <label for="latitude" class="form-label">Latitude</label>
               <input type="text" id="latitude" name="latitude" value="<%= request.getParameter("latitude") %>"><br><br>
           </div>
           <div class="mb-3">
               <label for="longitude" class="form-label">Longitude</label>
               <input type="text" id="longitude" name="longitude" value="<%= request.getParameter("longitude") %>"><br><br>
           </div>
           <input type="submit" value="Update">
       </form>
    </div>
    
</body>
</html>