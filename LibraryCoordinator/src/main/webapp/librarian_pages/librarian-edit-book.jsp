<%@ include file="librarian-header.jsp"%>

<div class="container">

	<c:if test="${ book != null }">
		<form action="edit/update" method="post">
		
			<input type="hidden" value='${ book.isbn }' name="isbn" />

			<div class="form-group">

				<label for="isbn">ISBN</label>
				<input type="text" disabled class="form-control" id="isbn"
					value="<c:out value='${ book.isbn }' />" required>

			</div>

			<div class="form-group">

				<label for="title">Title</label>
				<input type="text" class="form-control" id="title" name="title"
					value="<c:out value='${ book.title }' />" required>

			</div>

			<div class="form-group">

				<label for="description">Description</label>
				<input type="text" class="form-control" id="description" name="description"
					value="<c:out value='${ book.descr }' />" required>
			</div>

			<button type="submit" class="btn btn-primary">Submit</button>

		</form>
	</c:if>

</div>

<%@ include file="../footer.jsp"%>