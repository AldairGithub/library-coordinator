package com.cognixia.jump.frontend;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cognixia.jump.connector.ConnectionManager;

public class LibrarianLoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// private Library lib;
	Connection conn;

	public void init() {
		// initialize LibraryDAO
		// lib = new LibraryDAO();
		// conn = ConnectionManager.getConnection();
	}
	
	public void destroy() {
		// destroy connection
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		
		switch (action) {

		case "/admin/login":
			login(request, response);
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
	
	
	
	
	
	
	public void login(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		RequestDispatcher rd;
		String username = req.getParameter("username").trim();
		String password = req.getParameter("password").trim();

		if (username.isEmpty() || password.isEmpty()) {
			String message = "Fields cannot be empty.";
			req.setAttribute("message", message);
			req.setAttribute("error", true);
			rd = req.getRequestDispatcher("/librarian_pages/librarian-login.jsp");
			rd.forward(req, res);
			return;
		}
		
		int id = login(username, password);

		if (id < 1) {
			String message = "Wrong username or password";
			req.setAttribute("message", message);
			req.setAttribute("error", true);
			rd = req.getRequestDispatcher("/librarian_pages/librarian-login.jsp");
			rd.forward(req, res);
			return;
		}
		
		HttpSession session = req.getSession();
		session.setAttribute("username", username);
		session.setAttribute("id", id);
		res.sendRedirect("home");
	}
	
	public int login(String username, String password) throws ServletException, IOException {
		
		int id = 1;
		//id = lib.getLibrarianId(username, password)
		
		return id;
	}
}
