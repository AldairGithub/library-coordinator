package com.cognixia.jump.frontend;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LibrarianLoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// private Library lib;

	public void init() {
		// initialize LibraryDAO
		// lib = new LibraryDAO();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		RequestDispatcher rd;
		
		switch (action) {

		case "/admin/login":
			String username = request.getParameter("username").trim();
			String password = request.getParameter("password").trim();

			if (username.isEmpty() || password.isEmpty()) {
				String message = "Fields cannot be empty.";
				request.setAttribute("message", message);
				request.setAttribute("error", true);
				rd = request.getRequestDispatcher("/librarian_pages/librarian-login.jsp");
				rd.forward(request, response);
				break;
			}
			
			int id = login(username, password);

			if (id < 1) {
				String message = "Wrong username or password";
				request.setAttribute("message", message);
				request.setAttribute("error", true);
				rd = request.getRequestDispatcher("/librarian_pages/librarian-login.jsp");
				rd.forward(request, response);
				break;
			}
			
			HttpSession session = request.getSession();
			session.setAttribute("username", username);
			session.setAttribute("id", id);
			response.sendRedirect("home");
			break;
		
		default:
			response.sendRedirect("/librarian_pages/librarian-login.jsp");
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	public int login(String username, String password) {
		int id = 1;
		//id = lib.getLibrarianId(username, password)
		
		return id;
	}
}
