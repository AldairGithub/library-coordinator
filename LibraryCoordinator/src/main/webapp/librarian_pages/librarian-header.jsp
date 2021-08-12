<%@ include file="../header.jsp"%>

<nav class="navbar navbar-expand-lg navbar-light bg-light">

	<a class="navbar-brand" href="<%=request.getContextPath()%>/admin/home">Library</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
		aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse" id="navbarNav">
		<ul class="navbar-nav">

			<li class="nav-item">
				<a class="nav-link" href="manage-patrons">Manage Patrons</a>
			</li>

		</ul>
		<ul class="navbar-nav">
			<li class="nav-item dropdown">
				<a class="nav-link dropdown-toggle" role="button" data-toggle="dropdown" href="#">${ username }</a>
				<div class="dropdown-menu">
					<a class="dropdown-item" href="manage-account">Manage Account</a>
					<a class="dropdown-item" href="signout">Sign Out</a>
				</div>
			</li>
		</ul>
	</div>

</nav>

</header>