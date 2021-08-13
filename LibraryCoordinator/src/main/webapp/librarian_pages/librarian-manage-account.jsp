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

	<c:if test="${ type == 'username' || type == 'password'}">
		<%
		String action = "change-username/update";
		%>
		<c:if test="${ type == 'password' }">
			<%
			action = "change-password/update";
			%>
		</c:if>
		<form action=<%=action%> method="post">

			<c:if test="${ type == 'username' }">
				<div class="form-group">

					<label for="new-username">New Username</label>
					<input type="text" name="new-username" class="form-control" id="new-username" minlength="5"
						pattern="\w{6,}" title="Username must be at least 6 characters long." required>

				</div>

				<div class="form-group">

					<label for="password">Password</label>
					<input type="password" class="form-control" id="password" name="password" required>

				</div>
			</c:if>

			<c:if test="${ type == 'password' }">
				<div class="form-group">

					<label for="old-password">Old Password</label>
					<input type="password" class="form-control" id="old-password" name="old-password" required>

				</div>

				<div class="form-group">

					<label for="new-password">New Password</label>
					<input type="password" class="form-control" id="new-password" pattern="\w{4,}"
						name="new-password" title="Password must be at least 4 characters long." required>

				</div>

				<div class="form-group">

					<label for="confirm-password">Confirm Password</label>
					<input type="password" class="form-control" id="confirm-password" name="confirm-password"
						pattern="\w{4,}" title="Password must be at least 4 characters long." required>
				</div>
			</c:if>
			<button type="submit" class="btn btn-primary">Submit</button>
		</form>
	</c:if>
</div>

<%@ include file="../footer.jsp"%>