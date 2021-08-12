
<%@ include file="login-header.jsp" %>

<h1>New Patron</h1>

<div class="container">

	<form class="row g-3" action="newPatron" method="post">
	
  		<div class="col-md-6">
		    <label for="firstName" class="form-label">First Name</label>
		    <input type="text" class="form-control" id="firstName" name="firstName" maxlength="50" required>
		</div>
		
	  	<div class="col-md-6">
	    	<label for="lastName" class="form-label">Last Name</label>
	    	<input type="text" class="form-control" name="lastName" name="lastName" maxlength="50" required>
	  	</div>

		<div class="col-12">
		    <label for="username" class="form-label">Username</label>
		    <input type="text" class="form-control" id="username" name="username" maxlength="50" required>
		</div>

		<div class="col-12">
		    <label for="password" class="form-label">Password</label>
		    <input type="password" class="form-control" id="password" name="password" maxlength="50" required>
		</div>

		<div class="col-12">
		    <button type="submit" class="btn btn-primary">Create</button>
		</div>
		
	</form>

</div>


<%@ include file="../footer.jsp" %>