<%@ include file="librarian-header.jsp"%>

<!-- TODO
		1) Home page should show book management options:
			1 - Add new books
			2 - Update book title
			3 - Delete book
			Comment: Make a table similar to the crud_project's, displaying rows of books with edit/delete options.
					Also add an "Add Book" button above the table. Add and Edit should redirect to their corresponding .jsp;
					delete should delete on the spot and refresh the page.
		2) Manage Patrons navigation link - options:
			1 - Unfreeze/Freeze button
			Comment: Make a table displaying all patron accounts status
		3) Update account link:
			1 - change username
			2 - change password
	 -->

<div class="container">

	<h1>Books in Inventory</h1>
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
	<table class="table table-striped">

		<thead class="text-center">
			<tr>
				<th>ISBN</th>
				<th>Title</th>
				<th>Description</th>
				<th>Rented</th>
				<th>Added To Library</th>
				<th colspan="2">Actions</th>
			</tr>
		</thead>

		<tbody>

			<c:forEach var="book" items="${ books }">
				<% String rented = "No"; %>
				<c:if test="${ book.rented }">
					<% rented = "Yes"; %>
				</c:if>

				<tr>
					<td>${ book.isbn }</td>

					<td>${ book.title }</td>

					<td>${ book.descr }</td>

					<td><%= rented %></td>

					<td>${ book.added_to_library }</td>

					<td>
						<a href="book/edit?isbn=<c:out value='${ book.isbn }' />">
							<button class="btn btn-primary">Edit</button>
						</a>
					</td>

					<td>
						<c:choose>
							<c:when test="${ book.rented }">
								<a href="#">
									<button class="btn btn-danger" <c:if test="${ book.rented }">disabled</c:if>>Delete</button>
								</a>
							</c:when>
							<c:otherwise>
								<a href="book/delete?isbn=<c:out value='${ book.isbn }' />">
									<button class="btn btn-danger" <c:if test="${ book.rented }">disabled</c:if>>Delete</button>
								</a>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>

		</tbody>

	</table>

</div>


<%@ include file="../footer.jsp"%>