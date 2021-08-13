package com.cognixia.jump.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.LoggingMXBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
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
public class PatronServlet extends HttpServlet {
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
			case "/login": 
				response.sendRedirect("login-form.jsp");
				break;
			case "/loggingin":
				login(request, response);
				break;
			case "/logoff": 
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
				response.sendRedirect("accountSettings-form.jsp");
				break;
			case "/updateUser": 
				System.out.println("reaches this point");
				updateUsername(request, response);
				break;
			case "/list":
				System.out.println("made it to list");
				getAllBooks(request, response);
				break;
			case "/updatePass": 
				System.out.println("reaches this point");
				updatePassword(request, response);
				break;
			case "/newPatron": 
				System.out.println("reaches this point");
				newPatron(request, response);
				break;
			case "/displayBooks": // not being used currently 
				System.out.println("reaches this point");
				display(request, response);
				break;
			case "/history": 
				System.out.println("reaches this point");
				history(request, response);
				break;
			case "/return":
			try {
				returning(request,response);
			} catch (SQLException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				response.sendRedirect("home");
				break;
			case "/rent":
			try {
				renting(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
			
			default:
				// redirect the the url: localhost:8080/LibraryCoordinator
				// display index.js page
				response.sendRedirect("/");
				break;
		}
	}
	
	private void getAllBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		List<Book> book = patronDao.getAllBooks();
		
		// add in data you're going to send, through the request object
		request.setAttribute("book", book);
		
		// redirect to jsp page and send data we just pulled
		RequestDispatcher dispatcher = request.getRequestDispatcher("listAllBooks-form.jsp");
		dispatcher.forward(request, response);
		
	}

	private void history(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("In HISTORY!!!!!!!!");
		
		HttpSession session = request.getSession();
		
		int id = (int)session.getAttribute("id");
		System.out.println(id);
		List<BookCheckout> historyList = patronDao.getPreviouslyCheckedOutBooks(id);
		
		request.setAttribute("dates", historyList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("history.jsp");
		dispatcher.forward(request, response);
		
	}

	private void renting(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		// TODO Auto-generated method stub
		String isbn = request.getParameter("isbn");
		
		System.out.println(isbn);
		HttpSession session = request.getSession();
		
		Integer id = (Integer)session.getAttribute("id");
		id = (int)id;
		System.out.println(id);
		
		
		boolean checkout = patronDao.checkOutBook(id, isbn, true);
	
		System.out.println(checkout);
		response.sendRedirect("home");
		
		
		
	}

	private void returning(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		// TODO Auto-generated method stub
		String isbn = request.getParameter("isbn");
		String checkout_id1 = request.getParameter("checkout_id");
		
		int checkout_id = Integer.parseInt(checkout_id1);
		
		System.out.println(isbn + " " + checkout_id);
		
		boolean result = patronDao.checkoutBook(checkout_id, isbn, false);
		System.out.println(result);
		
	}

	private void display(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		List<Book> allBooks = patronDao.getAllBooks();
		
		request.setAttribute("allBooks", allBooks);
		RequestDispatcher dispatcher = request.getRequestDispatcher("patronHome.jsp");
		dispatcher.forward(request, response);
		
	}

	private void newPatron(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String firstName = request.getParameter("firstName").trim();
		String lastName = request.getParameter("lastName").trim();
		String username = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();
		
		boolean added = false;
		
			added = patronDao.addPatron(new Patron(0, firstName, lastName, username, password, false));
			
		System.out.println(added);
		
		if(added) {
			response.sendRedirect("login-form.jsp");
		}
		else {
			response.sendRedirect("newPatron-form.jsp");
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
			boolean frozen = valid.isAccount_frozen();
			session.setAttribute("valid", true);
			session.setAttribute("frozen", frozen);
			session.setAttribute("username", request.getParameter("username"));
			session.setAttribute("password", request.getParameter("password"));
			session.setAttribute("typeSelect", request.getParameter("typeSelect"));
			session.setAttribute("id", valid.getId());
			RequestDispatcher dispatcher = request.getRequestDispatcher("home");
			dispatcher.forward(request, response);
		}
		
		
		
	}
	
	private void updatePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		String newpassword = request.getParameter("password");
		System.out.println(newpassword);
		boolean success = false;
		
		if(!newpassword.equals("")) {
		
		String username = (String)session.getAttribute("username");
		String password = (String)session.getAttribute("password");
		 
		System.out.println(username + " " + password);
		
		Patron updateThisPatron = patronDao.getPatron(username, password);
		
		updateThisPatron.setPassword(newpassword);
		System.out.println(updateThisPatron);
		
		patronDao.updatePatron(updateThisPatron);
		
		
		session.setAttribute("password", newpassword);
		
		request.setAttribute("passChange", true);
		request.setAttribute("passSuccess", true);
		RequestDispatcher dispatcher = request.getRequestDispatcher("accountSettings-form.jsp");
		dispatcher.forward(request, response);
	}
	else {
		request.setAttribute("passChange", false);
		request.setAttribute("passSuccess", false);
		RequestDispatcher dispatcher = request.getRequestDispatcher("accountSettings-form.jsp");
		dispatcher.forward(request, response);
	}
	
	}


	private void updateUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String newusername = request.getParameter("username");
		
		System.out.println(newusername);
		
		boolean success = false;
		
		if(!newusername.equals("")) {
		
		String username = (String)session.getAttribute("username");
		String password = (String)session.getAttribute("password");
		 
		System.out.println(username + " " + password);
		
		Patron updateThisPatron = patronDao.getPatron(username, password);
		System.out.println(updateThisPatron);
		
		updateThisPatron.setUsername(newusername);
		
		patronDao.updatePatron(updateThisPatron);
		
		//session.setAttribute("valid", true);
		session.setAttribute("username", newusername);
		
		
		request.setAttribute("passChange", true);
		request.setAttribute("passSuccess", true);
		RequestDispatcher dispatcher = request.getRequestDispatcher("accountSettings-form.jsp");
		dispatcher.forward(request, response);
	}
	else {
		request.setAttribute("passChange", false);
		request.setAttribute("passSuccess", false);
		RequestDispatcher dispatcher = request.getRequestDispatcher("accountSettings-form.jsp");
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