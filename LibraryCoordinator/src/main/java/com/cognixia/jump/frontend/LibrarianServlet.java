package com.cognixia.jump.frontend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cognixia.jump.dao.LibrarianDao;
import com.cognixia.jump.dao.PatronDao;
import com.cognixia.jump.model.Book;
import com.cognixia.jump.model.Librarian;
import com.cognixia.jump.model.Patron;

@WebServlet("/admin/*")
public class LibrarianServlet extends HttpServlet {

	RequestDispatcher rd;

	private static final long serialVersionUID = 1L;
	private PatronDao patron;
	private LibrarianDao lib;

	public void init() {
		patron = new PatronDao();
		lib = new LibrarianDao();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		if (path == null)
			path = "";

		switch (path) {
		case "/login":
			login(request, response);
			break;

		/******* Sign Out Case *******/
		case "/signout":
			signout(request, response);
			break;

		/******* Manage Account Cases *******/
		case "/manage-account":
			// go to manage account page
			manageAccount(request, response);
			break;
		case "/manage-account/update":
			// confirm username and password updates
			manageAccountUpdate(request, response);
			break;

		/******* Manage Patrons Cases *******/
		case "/manage-patrons":
			// go to manage patrons page
			managePatrons(request, response);
			break;

		case "/manage-patrons/update":
			// confirm patron account (un)freeze
			freezeUnfreezePatron(request, response);
			break;

		/******* Book Actions Cases *******/
		case "/book/add":
			// go to add-book page
			break;

		case "/book/add/submit":
			// go to add-book page
			break;

		case "/book/edit":
			editBook(request, response);
			break;

		case "/book/edit/update":
			editBookUpdate(request, response);
			break;

		case "/book/delete":
			deleteBook(request, response);
			break;

		case "/home":
			home(request, response);

			break;

		default: // /admin/home if logged in, else login form
			response.sendRedirect(request.getContextPath() + "/librarian_pages/librarian-login.jsp");
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void login(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		RequestDispatcher rd;
		String username = req.getParameter("username");
		String password = req.getParameter("password");

		if ((username == null || password == null) || (username = username.trim()).isEmpty() || (password = password.trim()).isEmpty()) {
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
		Librarian librarian = lib.getLibrarian(username, password);

		int id = librarian != null ? librarian.getId() : -1;

		return id;
	}

	public void home(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Integer id = (Integer) req.getSession().getAttribute("id");
		if (id != null)
			loadBooks(req, res);
		else {
			res.sendRedirect(req.getContextPath() + "/admin");
		}
	}

	public void signout(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.removeAttribute("username");
		session.removeAttribute("id");

		String message = "You have been signed out.";
		req.setAttribute("message", message);
		req.setAttribute("error", false);

		rd = req.getRequestDispatcher("/librarian_pages/librarian-login.jsp");
		rd.forward(req, res);
	}

	public void manageAccount(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	}

	public void manageAccountUpdate(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	}

	public void managePatrons(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ArrayList<Patron> patrons = patron.getAllPatrons();
		req.setAttribute("patrons", patrons);
		rd = req.getRequestDispatcher("/librarian_pages/librarian-manage-patrons.jsp");
		rd.forward(req, res);
	}

	public void freezeUnfreezePatron(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		boolean frozen = Boolean.parseBoolean(req.getParameter("frozen"));
		int patronId = Integer.parseInt(req.getParameter("id"));
		boolean success = lib.updatePatron(patronId, frozen);
		String message;

		if (success) {
			message = "Patron with ID " + patronId + " was " + (frozen ? "frozen" : "unfrozen");
		} else {
			message = "Patron with ID " + patronId + " could not be " + (frozen ? "frozen" : "unfrozen");
		}

		req.setAttribute("message", message);
		req.setAttribute("success", success);

		rd = req.getRequestDispatcher("/admin/manage-patrons");
		rd.forward(req, res);
	}

	public void addBook(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	}

	public void addBookSubmit(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	}

	public void editBook(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String isbn = req.getParameter("isbn");
		Book book = lib.getBookByIsbn(isbn);
		if (book != null) {
			req.setAttribute("book", book);
			rd = req.getRequestDispatcher("/librarian_pages/librarian-edit-book.jsp");
			rd.forward(req, res);
		} else {
			req.setAttribute("message", "Book does not exist");
			rd = req.getRequestDispatcher("/admin");
			rd.forward(req, res);
		}
	}

	public void editBookUpdate(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String isbn = req.getParameter("isbn");
		String title = req.getParameter("title");
		String descr = req.getParameter("description");
		boolean success = lib.updateBook(isbn, title, descr);
		String message;

		if (success) {
			message = "Successfully update book with ISBN: " + isbn;
		}

		else {
			message = "Could not update book with ISBN: " + isbn;
		}

		req.setAttribute("message", message);
		req.setAttribute("success", success);
		rd = req.getRequestDispatcher("/admin/home");
		rd.forward(req, res);
	}

	public void deleteBook(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String isbn = req.getParameter("isbn");
		Book book = lib.getBookByIsbn(isbn);
		String message;
		boolean deleted = false;

		if (book != null) {
			deleted = lib.deleteBook(isbn);
			if (deleted)
				message = "Deleted book with ISBN: " + isbn;

			else
				message = "Could not deleted book with ISBN: " + isbn;
		} else {
			message = "Book does not exist.";
		}

		req.setAttribute("message", message);
		req.setAttribute("success", deleted);
		rd = req.getRequestDispatcher("/admin/home");
		rd.forward(req, res);
	}

	public void loadBooks(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ArrayList<Book> books;
		books = (ArrayList<Book>) patron.getAllBooks();
		req.setAttribute("books", books);
		rd = req.getRequestDispatcher("/librarian_pages/librarian-home.jsp");
		rd.forward(req, res);
	}

}
