
<%@ include file="header.jsp" %>

<h1>Login Page</h1>

<div class="container">

	<form action="" method="get" >
	
		<div class="form-group">
	
			<label for="username">Username</label>
	    	<input type="text" class="form-control" id="username" name="username" required>
	    	
		</div>
	
		<div class="form-group">
		
			<label for="password">Password</label>
		    <input type="password" class="form-control" id="password" name="password" required>
		    
		</div>
		
		<div class="form-group">
			
			<label class="visually-hidden" for="typeSelect">Type</label>
			
			<select class="form-select" id="typeSelect" name="typeSelect">
			
		      <option selected value="patron">patron</option>
		      <option value="librarian">librarian</option>
		      
		    </select>
		    
		</div>
		
		<br></br><a href="<%= request.getContextPath() %>/newPatron-form.jsp">Create a new patron account</a><br></br>
		
		<button type="submit" class="btn btn-primary" style="margin:10px">Submit</button>
	
	</form>
	
</div>


<%@ include file="footer.jsp" %>