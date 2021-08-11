package com.cognixia.jump.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cognixia.jump.connector.ConnectionManager;

@WebServlet("/")
public class LibraryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	public void init(ServletConfig config) throws ServletException
	{
		//
		
	}

	public void destroy() {
		
		try {
			ConnectionManager.getConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// based on the ending url -> perform some request/response through servlet
		// localhost:8080/LibraryCoordinator/LibraryServlet -> domain url
		// localhost:8080/LibraryCoordinator/LibraryServlet/abc -> "abc" is the ending url
		String action = request.getServletPath();
		
		switch (action)
		{
			case "/login": // go to product login page
				goToLoginForm(request, response);
				break;
				
			default:
				// redirect the the url: localhost:8080/LibraryCoordinator
				// display index.js page
				response.sendRedirect("/");
				break;
		}
	}
	
	private void goToLoginForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		RequestDispatcher dispatcher = request.getRequestDispatcher("login-form.jsp");
		dispatcher.forward(request, response);
		
	}
	

}
