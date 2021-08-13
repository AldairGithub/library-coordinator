<%@ include file="librarian-header.jsp"%>

<div class="container">

	<h1>Active / Frozen Patrons</h1>
	<br>
	<br>
	<c:if test="${ message != null}">
		<c:choose>
			<c:when test="${ !success }">
				<div class="alert alert-danger" role="alert">${ message }</div>
			</c:when>

			<c:otherwise>
				<div class="alert alert-success" role="alert">${ message }</div>
			</c:otherwise>
		</c:choose>
	</c:if>
	<table class="table table-striped text-center">

		<thead>
			<tr>
				<th>Patron ID</th>
				<th>Username</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Account Status</th>
				<th>Freeze / Unfreeze</th>
			</tr>
		</thead>

		<tbody>

			<c:forEach var="patron" items="${ patrons }">
				<% String frozen = "Unfrozen"; %>
				<% String action = "Freeze"; %>
				<c:if test="${ patron.account_frozen }">
					<% frozen = "Frozen"; %>
					<% action = "Unfreeze"; %>
				</c:if>

				<tr>
					<td>${ patron.id }</td>

					<td>${ patron.username }</td>

					<td>${ patron.first_name }</td>

					<td>${ patron.last_name }</td>

					<td><%= frozen %></td>

					<td>
						<a href="manage-patrons/update?id=${ patron.id }&frozen=<c:out value='${ !patron.account_frozen }' />">
							<button class="btn btn-primary btn-block"><%= action %></button>
						</a>
					</td>
				</tr>
			</c:forEach>

		</tbody>

	</table>

</div>

<%@ include file="../footer.jsp"%>