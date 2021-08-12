<%@ include file="header.jsp" %>

<p>Data passed: username = ${ username } usertype = ${ typeSelect }</p>

<% String formType = "search"; %>

	<div class="container">
	
	<h1>Welcome ${ username } </h1>
	
	<form action="<%= formType %>" method="get" >
	
		<div class="input-group mb-3">
	  		<div class="input-group-text">
	  		</div>
	  		<input id="input" type="text" class="form-control" name="input"> <button id="searchButton" class="btn btn-primary" type="submit">Search</button>
		</div>
		
	</form>
	
	
	
	<c:forEach var="book" items="${ book }">
	<table class="table table-striped">
	
		<thead>
			<tr>
				<th>ISBN</th>
				<th>Title</th>
				<th>Description</th>
				<th>Rented</th>
			</tr>
		</thead>
		
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
						<c:out value="${ book.rented }" />
					</td>
					
					<c:if test="${ (book.rented == false) }">
						<td>
						
							<a href="edit?id=<c:out value='${ book.isbn }' />">
								<button class="btn btn-primary">Rent</button>
							</a>
						</td>
					</c:if>

				</tr>
		
		</tbody>
	
	</table>
	</c:forEach>
	
</div>

<%@ include file="footer.jsp" %>
