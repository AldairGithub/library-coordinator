<%@ include file="../header.jsp"%>

<nav class="navbar navbar-expand-lg navbar-light bg-light">

	<a class="navbar-brand" href="<%=request.getContextPath()%>/admin/home">Home</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
		aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse" id="navbarNav">
		<ul class="navbar-nav">

			<li class="nav-item">
				<a class="nav-link" href="<%= request.getContextPath() %>/admin/manage-patrons">Manage Patrons</a>
			</li>

		</ul>
		<ul class="navbar-nav">
			<li class="nav-item dropdown">
				<a class="nav-link dropdown-toggle" role="button" data-toggle="dropdown" href="#">${ username }</a>
				<div class="dropdown-menu">
					<a class="dropdown-item" href="<%= request.getContextPath() %>/admin/change-username">Change Username</a>
					<a class="dropdown-item" href="<%= request.getContextPath() %>/admin/change-password">Change Password</a>
					<a class="dropdown-item" href="<%= request.getContextPath() %>/admin/signout">Sign Out</a>
				</div>
			</li>
		</ul>
	</div>

</nav>

</header>