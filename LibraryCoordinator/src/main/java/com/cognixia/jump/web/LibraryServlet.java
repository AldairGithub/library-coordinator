package com.cognixia.jump.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.LoggingMXBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cognixia.jump.connector.ConnectionManager;
import com.cognixia.jump.dao.PatronDao;
import com.cognixia.jump.model.Book;
import com.cognixia.jump.model.BookCheckout;
import com.cognixia.jump.model.Patron;

@WebServlet("/")
public class LibraryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private PatronDao patronDao;
	
	public void init(ServletConfig config) throws ServletException
	{
		patronDao = new PatronDao();
		
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
				response.sendRedirect("login-form.jsp");
				break;
			case "/loggingin":
				login(request, response);
				break;
			case "/logoff": // go to product login page
				loggingOff(request, response);
				goToLogoffForm(request, response);
				break;
			case "/home":
				getRented(request, response);
				RequestDispatcher dispatcher = request.getRequestDispatcher("patronHome.jsp");
				dispatcher.forward(request, response);
				break;
			case "/search":
				searchBooks(request, response);
				break;
			case "/accountSettings":
				request.setAttribute("userChange", true);
				request.setAttribute("userSuccess", false);
				request.setAttribute("passChange", true);
				request.setAttribute("passSuccess", false);
				System.out.println("value: " + request.getParameter("passSuccess"));
				dispatcher = request.getRequestDispatcher("accountSettings-form.jsp");
				dispatcher.forward(request, response);
				break;
			case "/return":
				System.out.println("isbn passed: " + request.getParameter("isbn"));
				System.out.println("checkout_id: " + request.getParameter("checkout_id"));
				response.sendRedirect("home");
				break;
			default:
				// redirect the the url: localhost:8080/LibraryCoordinator
				// display index.js page
				response.sendRedirect("/");
				break;
		}
	}
	
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Patron valid = patronDao.getPatron(username, password);
		if(valid == null)
		{
			HttpSession session = request.getSession();
			session.setAttribute("valid", false);
			RequestDispatcher dispatcher = request.getRequestDispatcher("login-form.jsp");
			dispatcher.forward(request, response);
		}
		if(valid != null)
		{
			HttpSession session = request.getSession();
			session.setAttribute("valid", true);
			session.setAttribute("username", request.getParameter("username"));
			session.setAttribute("typeSelect", request.getParameter("typeSelect"));
			session.setAttribute("id", valid.getId());
			RequestDispatcher dispatcher = request.getRequestDispatcher("home");
			dispatcher.forward(request, response);
		}
		
		
		
	}
	
	private void searchBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String searchInput = request.getParameter("input");
		List<Book> book = patronDao.searchBooks(searchInput);
		
		// add in data you're going to send, through the request object
		request.setAttribute("book", book);
		
		// redirect to jsp page and send data we just pulled
		RequestDispatcher dispatcher = request.getRequestDispatcher("search.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void loggingOff(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		session.setAttribute("username", request.getParameter("username"));
		session.setAttribute("typeSelect", request.getParameter("typeSelect"));
		session.setAttribute("id", request.getParameter("id"));
		
	}
	
	private void goToLogoffForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		RequestDispatcher dispatcher = request.getRequestDispatcher("logoff-form.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void getRented(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		if(session.getAttribute("id") != null)
		{
			int id = (Integer) session.getAttribute("id");
			System.out.println("getting " + id);
			List<BookCheckout> book = patronDao.getCheckedOutBooks(id);
			
			// add in data you're going to send, through the request object
			request.setAttribute("book", book);
		}
	}
	private void sendHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
//		String userName = request.getParameter("username");
//		String userType = request.getParameter("typeSelect");
//		
//		request.setAttribute("username", userName);
//		request.setAttribute("typeSelect", userType);
		RequestDispatcher dispatcher = request.getRequestDispatcher("patronHome.jsp");
		dispatcher.forward(request, response);
		
	}
	

}
