<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>library</title>

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
	crossorigin="anonymous">

</head>
<body>
	
	<div class="container">

		<h1 class="display-4">library Website</h1>
	
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<div class="container-fluid">
				<a class="navbar-brand" href="<%= request.getContextPath() %>/home">Home</a>
				
				<button class="navbar-toggler" type="button"
					data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup"
					aria-controls="navbarNavAltMarkup" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				
				<div class="collapse navbar-collapse" id="navbarNavAltMarkup">
					<div class="navbar-nav">
					 
						<c:if test="${ (username == null) }">
							
							<a class="nav-link" 
						   href="<%= request.getContextPath() %>/login">LogIn</a> 
						   
						</c:if>
						
						<c:if test="${ (username != null) && (valid != null) }">
							
							<a class="nav-link" 
						   href="<%= request.getContextPath() %>/list">List All Books</a> 
						   
						</c:if>
						
						<c:if test="${ (username != null) && (valid != null) }">
							
							<a class="nav-link" 
						   href="<%= request.getContextPath() %>/accountSettings">AccountSettings</a> 
						   
						</c:if>
						
						<c:if test="${ (username != null) && (valid != null) }">
							
							<a class="nav-link" 
						   href="<%= request.getContextPath() %>/history">Checkout History</a> 
						   
						</c:if>
						
						<c:if test="${ (username != null) && (valid != null) }">
							
							<a class="nav-link" 
						   href="<%= request.getContextPath() %>/logoff">LogOff</a> 
						   
						</c:if>
						

					</div>
				</div>
			</div>
		</nav>

