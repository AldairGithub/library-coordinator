package com.cognixia.jump.web;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cognixia.jump.connector.ConnectionManager;
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


	public void destroy() {
		
		try {
			ConnectionManager.getConnection().close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String action = request.getServletPath();
		
		switch (action)
		{
			case "/login": // go to product login page
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