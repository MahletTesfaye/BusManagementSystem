<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User profile</title>
<link rel="stylesheet" href="webjars/bootstrap/5.3.0/css/bootstrap.min.css">
</head>
<body class="bg-secondary">

	 <div><a href="./Index.jsp" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a></div>
	 <div class="container">
		 <div class="text-white w-50">
		 <h2>Manage user profile</h2>
			
			<form action="register" method="post" id="updateForm" class="border border-secondary p-3 ">
				<div class="mb-3">
					<label for="name" class="form-label">Name:</label>
					<input type="text" class="form-control" id="name" name="updatedName" value = "<%=session.getAttribute("name")%>"/>
				</div>
				<div class="mb-3">
					<label for="email" class="form-label">Email:</label>
					<input type="email" class="form-control" id="email" name="updatedEmail" value = "<%=session.getAttribute("email")%>""/>
				
				</div>
				<div class="mb-3">
					<label for="password" class="form-label">Password:</label>
					<input type="password" class="form-control" id="password" name="updatedPassword" value = "<%=session.getAttribute("password")%>" />
				</div>
				<button type="submit" class="btn btn-primary">Update</button>
				
			</form>
			</div>
		
		 <div class="text-white">Do you want to logout? <a class="btn btn-primary" href="#" onclick="logout();">Logout</a></div>
	 </div>
	 <script>
	    // JavaScript code for handling form submission and AJAX request
	    document.getElementById('updateForm').addEventListener('submit', function(e) {
	        e.preventDefault();
	
	        // Collect the form data
	        var formData = {
	            name: document.getElementById('name').value,
	            email: document.getElementById('email').value,
	            password: document.getElementById('password').value,
	        };
			
	</script>
	 <script>
	    function logout() {
	    if (confirm('Are you sure you want to log out?')) {
	        var xhr = new XMLHttpRequest();
	        xhr.open('DELETE', './login', true);
	        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	        xhr.addEventListener('load', function() {
	            if (xhr.status === 200) {
	                // Successful logout
	                window.location.href = 'Login.jsp'; // Redirect to the login page
	                
	            } else {
	                // Handle error
	                console.error(xhr.status + ': ' + xhr.statusText);
	                
	            }
	            location.reload();
	            alert('Successfully logged out!')
	            
	        });
	        xhr.send();
	    	}
		}
	 </script>
	 <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/js/all.min.js"></script>
	 <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="webjars/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>

