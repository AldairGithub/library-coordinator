<%@ include file="header.jsp" %>

<h1>Edit User Info</h1>

<% String formType1 = "updateUser"; %>

<% String formType2 = "updatePass"; %>

<div class="container">

	<form action="<%= formType1 %>" method="post" >
	
		<div class="form-group">
	
			<label for="username">Change Username</label>
	    	<input type="text" class="form-control" id="username" name="username" required>
	    	<button type="submit" class="btn btn-primary" style="margin:10px">Submit</button>
	    	<c:if test="${ (userChange == true) }">
	    		
	    		<c:if test="${ (userSuccess == true) }">
	    			<p>Username was successfully updated</p>
	    		</c:if>
	    		
	    		<c:if test="${ (userSuccess == false) }">
	    			<p>Username was NOT updated</p>
	    		</c:if>
	    	
	    	</c:if>
		</div>
		
	</form>
	
	<form action="<%= formType2 %>" method="post" >
		<div class="form-group">
	
			<label for="password">Change Password</label>
	    	<input type="text" class="form-control" id="username" name="password" required>
	    	<button type="submit" class="btn btn-primary" style="margin:10px">Submit</button>
	    	
	    	<c:if test="${ (passChange == true) }">
	    		
	    		<c:if test="${ (passSuccess == true) }">
	    			<p>Username was successfully updated</p>
	    		</c:if>
	    		
	    		<c:if test="${ (passSuccess == false) }">
	    			<p>Username was NOT updated</p>
	    		</c:if>
	    	
	    	</c:if>
		</div>
		
	</form>

</div>


<%@ include file="footer.jsp" %>