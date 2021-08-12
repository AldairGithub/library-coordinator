<%@ include file="header.jsp" %>

<p>Data passed: username = ${ username } usertype = ${ typeSelect } id = ${ id }</p>

<% String formType = "search"; %>

<c:if test="${ typeSelect == 'patron' }">
	<div class="container">
	
	<h1>Welcome ${ username } </h1>
	
	<form action="<%= formType %>" method="get" >
	
		<div class="input-group mb-3">
			<div class="input-group-text">
	  		</div>
	  		<input id="input" type="text" class="form-control" name="input"> <button id="searchButton" class="btn btn-primary" type="submit">Search</button>
		</div>
		
	</form>
	
	
	<h1>Rented Books</h1>
	<br>
	<br>
	<table class="table table-striped">
	
		<thead>
			<tr>
				<th>ISBN</th>
				<th>Title</th>
				<th>Description</th>
				<th>Return Date</th>
			</tr>
		</thead>
		<c:forEach var="book" items="${ book }">
			<tbody>
					
					<tr>
						<td>
							<c:out value="${ book.isbn }" />
						</td>
						
						<td>
							<c:out value="${ book.title }" />
						</td>
						
						<td>
							<c:out value="${ book.descr }" />
						</td>
						
						<td>
							<c:out value="${ book.due_date }" />
						</td>
						
						<td>
							
							<a href="return?isbn=<c:out value='${ book.isbn }' />&checkout_id=<c:out value='${ book.id }' />"> <button class="btn btn-primary">Return</button> </a>
							
						</td>
						
					</tr>
			</tbody>
		</c:forEach>
	</table>
	
</div>
</c:if>

<c:if test="${ typeSelect != 'patron' }">
	<div class="container">
		<p>Page is for patrons ONLY</p>
		<a class="nav-link" href="<%= request.getContextPath() %>/login">Go To LogIn Page</a>
	</div>
</c:if>

<%@ include file="footer.jsp" %>
