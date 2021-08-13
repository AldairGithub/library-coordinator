<%@ include file="librarian-header.jsp"%>

<div class="container">

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
	
	<form action="add/submit" method="post">

		<div class="form-group">
			<label for="isbn">ISBN</label>
			<input type="text" class="form-control" id="isbn" name="isbn" pattern="\d{10}"
				title="ISBN must be 10 digits" required>
		</div>

		<div class="form-group">
			<label for="title">Title</label>
			<input type="text" class="form-control" id="title" name="title" required>
		</div>

		<div class="form-group">
			<label for="description">Description</label>
			<input type="text" class="form-control" id="description" name="description" required>
		</div>

		<button type="submit" class="btn btn-primary">Submit</button>

	</form>
</div>

<%@ include file="../footer.jsp"%>