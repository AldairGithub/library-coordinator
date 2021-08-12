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
	<table class="table table-striped">

		<thead>
			<tr>
				<th>ISBN</th>
				<th>Title</th>
				<th>Author</th>
				<th>Actions</th>
			</tr>
		</thead>

		<tbody>

			<c:forEach var="mybooks" items="${ books }">
				<tr>
					<c:forEach var="book" items="${ mybooks }">
						<td>
							<c:out value="${ book }" />
						</td>
					</c:forEach>
					<td>
						<a href="edit-book?id=<c:out value='${ mybooks[0] }' />">
							<button class="btn btn-primary">Edit</button>
						</a>
						
						&nbsp;&nbsp;
						
						<a href="delete-book?id='${ mybooks[0] }' />">
							<button class="btn btn-danger">Delete</button>
						</a>
					</td>
				</tr>
			</c:forEach>

		</tbody>

	</table>

</div>


<%@ include file="../footer.jsp"%>