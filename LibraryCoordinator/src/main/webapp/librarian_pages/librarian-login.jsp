<%@ include file="login-header.jsp"%>

<section class="text-center container">
	<form action="<%=request.getContextPath()%>/admin/login" method="post">
		<div class="row">
			<div class="col-md-4 offset-md-4 mt-2 mb-5 p-5">
				<div class="row">
					<div class="col-md-12">
						<div>
							<h3>Librarian Login</h3>
						</div>
					</div>
				</div>

				<c:if test="${ message != null }">
					<div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<c:choose>
									<c:when test="${ error }">
										<div class="alert alert-danger" role="alert">${ message }</div>
									</c:when>

									<c:otherwise>
										<div class="alert alert-success" role="alert">${ message }</div>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
				</c:if>

				<div class="row">
					<div class="col-md-12">
						<div class="form-group">
							<label for="username" class="sr-only">Username</label>
							<input type="text" class="form-control" id="username" name="username" placeholder="Username" />
						</div>

						<div class="form-group">
							<label for="password" class="sr-only">Password</label>
							<input type="password" class="form-control" id="password" name="password"
								placeholder="Password" />
						</div>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-12">
						<button type="submit" class="btn btn-primary btn-block">Submit</button>
					</div>
				</div>
			</div>
		</div>
	</form>
</section>

<%@ include file="../footer.jsp"%>