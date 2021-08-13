<%@ include file="../header.jsp"%>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
	<div class="container-fluid">
		<a class="navbar-brand" href="<%= request.getContextPath() %>/patron/home">Home</a>

		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarNavAltMarkup">
			<div class="navbar-nav">

				<!-- <c:if test="${ (username == null) }">

						<a class="nav-link" href="contextpathhere/patron/login">LogIn</a>

					</c:if> -->

				<c:if test="${ (username != null) && (valid != null) }">

					<a class="nav-link" href="<%= request.getContextPath() %>/patron/list">List All Books</a>

				</c:if>

				<c:if test="${ (username != null) && (valid != null) }">

					<a class="nav-link" href="<%= request.getContextPath() %>/patron/accountSettings">AccountSettings</a>

				</c:if>

				<c:if test="${ (username != null) && (valid != null) }">

					<a class="nav-link" href="<%= request.getContextPath() %>/patron/history">Checkout History</a>

				</c:if>

				<c:if test="${ (username != null) && (valid != null) }">

					<a class="nav-link" href="<%= request.getContextPath() %>/patron/logoff">LogOff</a>

				</c:if>


			</div>
		</div>
	</div>
</nav>
