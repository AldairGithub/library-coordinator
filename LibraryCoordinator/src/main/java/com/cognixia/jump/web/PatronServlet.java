package com.cognixia.jump.web;

<<<<<<< HEAD
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.LoggingMXBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
=======

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.RequestDispatcher;
>>>>>>> development
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cognixia.jump.connector.ConnectionManager;
<<<<<<< HEAD
import com.cognixia.jump.dao.PatronDao;
import com.cognixia.jump.model.Book;
import com.cognixia.jump.model.BookCheckout;
import com.cognixia.jump.model.Patron;

@WebServlet("/")
public class PatronServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private PatronDao patronDao;
	
	public void init(ServletConfig config) throws ServletException
	{
		patronDao = new PatronDao();
		
	}

=======
//import com.cognixia.jump.dao.LibrarianDao;
import com.cognixia.jump.dao.PatronDao;
//import com.cognixia.jump.model.Librarian;
import com.cognixia.jump.model.Patron;


@WebServlet("/")
public class PatronServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//private LibrarianDao librarianDao;
	private PatronDao patronDao;
	
	
	public void init() {
		
		//librarianDao = new LibrarianDao();
		patronDao = new PatronDao();
	}


>>>>>>> development
	public void destroy() {
		
		try {
			ConnectionManager.getConnection().close();
<<<<<<< HEAD
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
=======
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
>>>>>>> development
		String action = request.getServletPath();
		
		switch (action)
		{
			case "/login": // go to product login page
<<<<<<< HEAD
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
			case "/list":
				System.out.println("made it to list");
				response.sendRedirect("home");
				break;
			case "/newpatron":
				System.out.println("made it to newpatron");
				response.sendRedirect("newPatron-form.jsp");
				break;
			case "/rent":
				HttpSession session = request.getSession();
				System.out.println("made it to rent");
				System.out.println("isbn: " + request.getParameter("isbn"));
				System.out.println("patron_id: " + session.getAttribute("id"));
				response.sendRedirect("search.jsp");
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
=======
				System.out.println("reaches this point");
				goToLoginForm(request, response);
				break;
				
			case "/newPatron": // go to product login page
				System.out.println("reaches this point");
				newPatron(request, response);
				break;
				
			default:
				
				response.sendRedirect("/");
				break;
		}
		}
	
	private void newPatron(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String firstName = request.getParameter("firstName").trim();
		String lastName = request.getParameter("lastName").trim();
		String username = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();
		
		boolean added = false;
		
			added = patronDao.addPatron(new Patron(0, firstName, lastName, username, password, false));
			
		
		
		if(added) {
			response.sendRedirect("login-form.jsp");
		}
	}


	private void goToLoginForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//List<Librarian> allLibrarians = new ArrayList();
		//List<Patron> allPatrons = new ArrayList();
		
		
		String username = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();
		System.out.println(username + " " + password);
		Patron patron =  patronDao.getPatron(username, password);
		System.out.println(patron);
		
		//allLibrarians = librarianDao.getAllLibrarians();
		
		
		
		//System.out.println(allLibrarians);
		
//		Librarian matchingLibrarian = allLibrarians.stream().
//				filter(p -> p.getUsername().equals(username)).
//				findFirst().orElse(null);
		
//		Patron matchingPatron = allPatrons.stream().
//				filter(p -> p.getUsername().equals(username)).
//				findFirst().orElse(null);
		
		
		
//		if ( !matchingLibrarian.equals(null) && matchingLibrarian.getPassword().equals(password)) {
//			
//			request.setAttribute("librarianDao", librarianDao);
//			request.setAttribute("librarian", matchingLibrarian);
//			RequestDispatcher dispatcher = request.getRequestDispatcher("librarian.jsp");
//			
//			
//			dispatcher.forward(request, response);
//			
//		}
		if (patron != null) {
			
			HttpSession session = request.getSession();
			session.setAttribute("patronDao", patronDao);
			session.setAttribute("patron", patron);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("Patron.jsp");
		
			dispatcher.forward(request, response);
			
			
		}
		else {
			//send to sign-up page 
			response.sendRedirect("newPatron-form.jsp");
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
>>>>>>> development
