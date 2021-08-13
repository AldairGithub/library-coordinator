<%@ include file="patron-header.jsp" %>

<p>Data passed: username = ${ username } usertype = ${ typeSelect } id = ${ id }</p>

<% String formType = "history"; %>

<c:if test="${ typeSelect == 'patron' }">
	<div class="container">
	
	
	
	<h1>History</h1>
	<br>
	<br>
	<table  class="table table-striped">
	
		<thead>
			<tr>
				<th>ISBN</th>
				<th>Title</th>
				<th>checked out date </th>
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
</c:if>

<%@ include file="../footer.jsp" %>