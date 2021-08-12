package com.cognixia.jump.frontend;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LibrarianServlet extends HttpServlet {

	RequestDispatcher dispatcher;

	private static final long serialVersionUID = 1L;
	private ArrayList<String[]> bookArr;

	public void init() {
		bookArr = new ArrayList<>();
		bookArr.add(new String[] { "12345", "Book1", "Author1" });
		bookArr.add(new String[] { "22346", "Book2", "Author2" });
		bookArr.add(new String[] { "32347", "Book3", "Author3" });
		bookArr.add(new String[] { "42348", "Book4", "Author4" });
		bookArr.add(new String[] { "52349", "Book5", "Author5" });
		bookArr.add(new String[] { "62341", "Book6", "Author6" });
		bookArr.add(new String[] { "72345", "Book7", "Author7" });
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		switch (action) {

		/******* Sign Out Case *******/
		case "/admin/signout":
			signout(request, response);
			break;

		/******* Manage Account Cases *******/
		case "/manage-account":
			// go to manage account page
			break;
		case "/manage-account/update":
			// confirm username and password updates
			break;

		/******* Manage Patrons Cases *******/
		case "/manage-patrons":
			// go to manage patrons page
			break;

		case "/unfreeze":
			// confirm patron account unfreeze
			break;

		/******* Book Actions Cases *******/
		case "/book/add":
			// go to add-book page
			break;

		case "/book/add/submit":
			// go to add-book page
			break;

		case "/book/edit":
			// go to edit-book page
			break;

		case "/book/edit/update":
			// confirm book updates
			break;

		case "/book/delete":
			// grab book id and delete it
			break;

		default: // /admin/home
			request.setAttribute("books", bookArr);
			dispatcher = request.getRequestDispatcher("/librarian_pages/librarian-home.jsp");
			dispatcher.forward(request, response);
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void signout(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.removeAttribute("username");
		session.removeAttribute("id");

		String message = "You have been signed out.";
		req.setAttribute("message", message);
		req.setAttribute("error", false);

		RequestDispatcher rd = req.getRequestDispatcher("/librarian_pages/librarian-login.jsp");
		rd.forward(req, res);
	}

	public void manageAccount(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	}

	public void manageAccountUpdate(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	}

	public void managePatrons(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	}

	public void unfreeze(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	}

	public void addBook(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	}

	public void addBookSubmit(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	}

	public void editBook(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	}

	public void editBookUpdate(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	}

	public void deleteBook(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	}

}
