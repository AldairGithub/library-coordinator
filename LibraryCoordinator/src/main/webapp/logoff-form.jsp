
<%@ include file="header.jsp" %>

<h1>Logoff Page</h1>

<div class="container">

	<p>You have successfully logged off</p>
	${ username = null}
	${ typeSelect = null }
	<a class="nav-link" href="<%= request.getContextPath() %>/login">Go To LogIn Page</a> 
	
</div>


<%@ include file="footer.jsp" %>