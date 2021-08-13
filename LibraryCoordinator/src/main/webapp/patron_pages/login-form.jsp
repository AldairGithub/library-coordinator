<%@ include file="login-header.jsp" %>

<% String formType = "loggingin"; %>



<div class="container">

	<form action="<%= request.getContextPath() %>/patron/<%= formType %>" method="post" >
		<h2>Patron Login</h2>
		<div class="form-group">
	
			<label for="username">Username</label>
	    	<input type="text" class="form-control" id="username" name="username" required>
	    	
		</div>
	
		<div class="form-group">
		
			<label for="password">Password</label>
		    <input type="password" class="form-control" id="password" name="password" required>
		    
		</div>
		
		<br></br><a href="<%= request.getContextPath() %>/patron_pages/newPatron-form.jsp">Create a new patron account</a><br></br>
		
		<button type="submit" class="btn btn-primary" style="margin:10px">Submit</button>
	
	</form>
	
</div>


<%@ include file="../footer.jsp" %>