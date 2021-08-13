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
		case "/change-username":
			// go to manage account page
			changeUsername(request, response);
			break;

		case "/change-username/update":
			// confirm username and password updates
			changeUsernameUpdate(request, response);
			break;

		case "/change-password":
			// go to manage account page
			changePassword(request, response);
			break;
		case "/change-password/update":
			// confirm username and password updates
			changePasswordUpdate(request, response);
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
			addBook(request, response);
			break;

		case "/book/add/submit":
			// go to add-book page
			addBookSubmit(request, response);
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
			home(request, response);
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

		int id = confirmAccount(username, password);

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

	public int confirmAccount(String username, String password) throws ServletException, IOException {
		Librarian librarian = lib.getLibrarian(username, password);

		int id = librarian != null ? librarian.getId() : -1;

		return id;
	}

	public void home(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Integer id = (Integer) req.getSession().getAttribute("id");
		if (id != null)
			loadBooks(req, res);
		else
			res.sendRedirect(req.getContextPath() + "/librarian_pages/librarian-login.jsp");
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

	public void changeUsername(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setAttribute("type", "username");
		req.getRequestDispatcher("/librarian_pages/librarian-manage-account.jsp").forward(req, res);
	}

	public void changeUsernameUpdate(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String oldUsername = (String) req.getSession().getAttribute("username");
		String pass = req.getParameter("password").trim();

		int id = confirmAccount(oldUsername, pass);

		String message;
		boolean success = false;

		if (id > 0) {
			String newUsername = req.getParameter("new-username").trim();

			if (newUsername.isEmpty())
				message = "Fields cannot be empty";

			else {
				success = lib.updateLibrarian(new Librarian(id, newUsername, pass));

				if (success) {
					message = "Username Updated.";
					req.getSession().setAttribute("username", newUsername);
				}

				else
					message = "Username '" + newUsername + "' is already in use.";
			}
		}

		else
			message = "Your password is incorrect.";

		req.setAttribute("message", message);
		req.setAttribute("success", success);
		rd = req.getRequestDispatcher("/admin/change-username");
		rd.forward(req, res);
	}

	public void changePassword(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setAttribute("type", "password");
		req.getRequestDispatcher("/librarian_pages/librarian-manage-account.jsp").forward(req, res);
	}

	public void changePasswordUpdate(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String username = (String) req.getSession().getAttribute("username");
		String oldPass = req.getParameter("old-password").trim();

		int id = confirmAccount(username, oldPass);

		String message;
		boolean success = false;

		if (id > 0) {
			String newPass = req.getParameter("new-password").trim();
			String confirmPass = req.getParameter("confirm-password").trim();

			if (newPass.isEmpty() || confirmPass.isEmpty())
				message = "Fields cannot be empty.";

			else {

				if (confirmPassword(newPass, confirmPass)) {
					success = lib.updateLibrarian(new Librarian(id, username, newPass));

					if (success)
						message = "Password Updated.";

					else
						message = "Could not update password.";
				}

				else
					message = "Passwords do not match.";
			}
		}

		else
			message = "Your old password is incorrect.";

		req.setAttribute("message", message);
		req.setAttribute("success", success);
		rd = req.getRequestDispatcher("/admin/change-password");
		rd.forward(req, res);
	}

	public boolean confirmPassword(String pass, String confirmPass) {
		return pass.equals(confirmPass);
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
		req.getRequestDispatcher("/librarian_pages/librarian-add-book.jsp").forward(req, res);
	}

	public void addBookSubmit(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String isbn = req.getParameter("isbn");
		String title = req.getParameter("title");
		String description = req.getParameter("description");
		boolean success = lib.addBook(isbn, title, description);
		String message;

		if (success)
			message = "'" + title + "' book has been added";
		else
			message = "A book with this ISBN (" + isbn + ") already exists.";

		req.setAttribute("message", message);
		req.setAttribute("success", success);
		rd = req.getRequestDispatcher("/admin/book/add");
		rd.forward(req, res);
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
			rd = req.getRequestDispatcher("/admin/home");
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
