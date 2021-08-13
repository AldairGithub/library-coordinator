<%@ include file="patron-header.jsp"%>

<%
String formType = "history";
%>

<div class="container">



	<h1>History</h1>
	<br>
	<br>
	<table class="table table-striped">

		<thead>
			<tr>
				<th>ISBN</th>
				<th>Title</th>
				<th>checked out date</th>
				<th>Returned Date</th>
			</tr>
		</thead>
		<c:forEach var="date" items="${ dates}">
			<tbody>

				<tr>
					<td>
						<c:out value="${ date.isbn }" />
					</td>
					<td>
						<c:out value="${ date.title }" />
					</td>
					<td>
						<c:out value="${ date.checkedout }" />
					</td>

					<td>
						<c:out value="${ date.returned }" />
					</td>

				</tr>
			</tbody>
		</c:forEach>
	</table>

</div>

<%@ include file="../footer.jsp"%>