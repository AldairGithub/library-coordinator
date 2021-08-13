package com.cognixia.jump.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cognixia.jump.connector.ConnectionManager;
import com.cognixia.jump.model.Book;
import com.cognixia.jump.model.BookCheckout;
import com.cognixia.jump.model.Patron;

public class PatronDao {

	public static final Connection conn = ConnectionManager.getConnection();
	// account_frozen is declared on SQL statement
	private static String GET_ALL_PATRONS = "SELECT * FROM patron ORDER BY account_frozen DESC";
	private static String INSERT_PATRON = "INSERT INTO patron(first_name, last_name, username, password) VALUES(?, ?, ?, ?);";
	private static String SELECT_PATRON = "SELECT * FROM patron WHERE username = ? AND password = ?;";
	private static String UPDATE_PATRON = "UPDATE patron SET first_name = ?, last_name = ?, username = ?, password = ? WHERE id = ?;";

	private static String SELECT_CHECKED_OUT_BOOKS = "SELECT book.isbn, title, descr, rented, added_to_library, checkout_id, checkedout, due_date, returned FROM book INNER JOIN book_checkout ON book.isbn = book_checkout.isbn WHERE patron_id = ? AND returned IS null;";

	private static String SELECT_PREVIOUSLY_CHECKED_OUT_BOOKS = "SELECT book.isbn, title, descr, rented, added_to_library, checkout_id, checkedout, due_date, returned FROM book INNER JOIN book_checkout ON book.isbn = book_checkout.isbn WHERE patron_id = ? AND returned IS NOT null;";
	private static String SELECT_ALL_BOOKS = "SELECT * FROM book;";

	private static String INSERT_CHECK_OUT_BOOK = "INSERT INTO book_checkout(patron_id, isbn, checkedout, due_date, returned) VALUES(?, ?, CAST(GETDATE() AS Date), DATEADD(day, 7, CAST(GETDATE() AS Date)), null);";
	private static String UPDATE_RENT_STATUS_ON_BOOK = "UPDATE book SET rented = ? WHERE isbn = ?;";

	private static String UPDATE_CHECK_OUT_BOOK = "UPDATE book_checkout SET returned = CAST(GETDATE() AS Date) WHERE id = ?;";

	public ArrayList<Patron> getAllPatrons() {
		ArrayList<Patron> patrons = null;
		
		try (PreparedStatement pstmt = conn.prepareStatement(GET_ALL_PATRONS)) {

			ResultSet rs = pstmt.executeQuery();
			patrons = new ArrayList<>();
			
			while (rs.next()) {
				int id = rs.getInt("patron_id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String first_name = rs.getString("first_name");
				String last_name = rs.getString("last_name");
				boolean account_frozen = rs.getInt("account_frozen") > 0;

				patrons.add(new Patron(id, first_name, last_name, username, password, account_frozen));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return patrons;
	}
	
	// sign up
	public boolean addPatron(Patron patron) {
		System.out.println(patron);
		try (PreparedStatement pstmt = conn.prepareStatement(INSERT_PATRON)) {

			pstmt.setString(1, patron.getFirst_name());
			pstmt.setString(2, patron.getLast_name());
			pstmt.setString(3, patron.getUsername());
			pstmt.setString(4, patron.getPassword());
			// pstmt.setBoolean(5, patron.isAccount_frozen());

			if (pstmt.executeUpdate() > 0) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	// log in
	public Patron getPatron(String username, String password) {
		Patron patron = null;

		try (PreparedStatement pstmt = conn.prepareStatement(SELECT_PATRON)) {

			pstmt.setString(1, username);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				int id = rs.getInt("patron_id");
				String first_name = rs.getString("first_name");
				String last_name = rs.getString("last_name");
				boolean account_frozen = rs.getBoolean("account_frozen");

				// should password be returned?
				patron = new Patron(id, first_name, last_name, username, password, account_frozen);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		System.out.println(patron);
		return patron;

	}

	// update patron
	public boolean updatePatron(Patron patron) {
		try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_PATRON)) {

			pstmt.setString(1, patron.getFirst_name());
			pstmt.setString(2, patron.getLast_name());
			pstmt.setString(3, patron.getUsername());
			pstmt.setString(4, patron.getPassword());
			pstmt.setInt(5, patron.getId());

			if (pstmt.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	// get all current checked out books from patron
	public List<BookCheckout> getCheckedOutBooks(int patronId) {

		List<BookCheckout> allBooksCheckedOut = new ArrayList<BookCheckout>();

		try (PreparedStatement pstmt = conn.prepareStatement(SELECT_CHECKED_OUT_BOOKS); ResultSet rs = pstmt.executeQuery();) {

			pstmt.setInt(1, patronId);

			while (rs.next()) {
				// not sure if we need to use the name of the column on checkout_id
				// or just use book_checkout.id
				int id = rs.getInt("checkout_id");
				String isbn = rs.getString("isbn");
				String title = rs.getString("title");
				String descr = rs.getString("descr");
				boolean rented = rs.getBoolean("rented");
				Date added_to_library = rs.getDate("added_to_library");
				Date checkedout = rs.getDate("checkedout");
				Date due_date = rs.getDate("due_date");
				Date returned = rs.getDate("returned");

				allBooksCheckedOut.add(new BookCheckout(id, isbn, title, descr, rented, added_to_library, checkedout, due_date, returned));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return allBooksCheckedOut;
	}

	// get all previously checked out books from patron
	public List<BookCheckout> getPreviouslyCheckedOutBooks(int patronId) {

		List<BookCheckout> allPreviouslyCheckedOutBooks = new ArrayList<BookCheckout>();

		try (PreparedStatement pstmt = conn.prepareStatement(SELECT_PREVIOUSLY_CHECKED_OUT_BOOKS); ResultSet rs = pstmt.executeQuery();) {

			pstmt.setInt(1, patronId);

			while (rs.next()) {
				// not sure if we need to use the name of the column on checkout_id
				// or just use book_checkout.id
				int id = rs.getInt("checkout_id");
				String isbn = rs.getString("isbn");
				String title = rs.getString("title");
				String descr = rs.getString("descr");
				boolean rented = rs.getBoolean("rented");
				Date added_to_library = rs.getDate("added_to_library");
				Date checkedout = rs.getDate("checkedout");
				Date due_date = rs.getDate("due_date");
				Date returned = rs.getDate("returned");

				allPreviouslyCheckedOutBooks
						.add(new BookCheckout(id, isbn, title, descr, rented, added_to_library, checkedout, due_date, returned));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return allPreviouslyCheckedOutBooks;

	}

	public List<Book> getAllBooks() {

		List<Book> allBooks = new ArrayList<Book>();

		try (PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_BOOKS); ResultSet rs = pstmt.executeQuery();) {

			while (rs.next()) {

				String isbn = rs.getString("isbn");
				String title = rs.getString("title");
				String descr = rs.getString("descr");
				boolean rented = rs.getBoolean("rented");
				Date added_to_library = rs.getDate("added_to_library");

				allBooks.add(new Book(isbn, title, descr, rented, added_to_library));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return allBooks;
	}

	public boolean insertCheckOutBook(int id, String isbn) {

		try (PreparedStatement pstmt = conn.prepareStatement(INSERT_CHECK_OUT_BOOK)) {

			pstmt.setInt(1, id);
			pstmt.setString(2, isbn);

			if (pstmt.executeUpdate() > 0) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateRentStatus(boolean rented, String isbn) {
		try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_RENT_STATUS_ON_BOOK)) {

			pstmt.setBoolean(1, rented);
			pstmt.setString(2, isbn);

			if (pstmt.executeUpdate() > 0) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	// rented should be true;
	public boolean checkOutBook(int patronId, String isbn, boolean rented) throws SQLException {

		if (insertCheckOutBook(patronId, isbn) && updateRentStatus(rented, isbn)) {
			return true;
		}

		return false;
	}

	public boolean insertReturnBook(int checkoutBookId) {

		try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_CHECK_OUT_BOOK)) {

			pstmt.setInt(1, checkoutBookId);

			if (pstmt.executeUpdate() > 0) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	public boolean checkoutBook(int checkoutId, String isbn, boolean rented) throws SQLException {

		if (insertReturnBook(checkoutId) && updateRentStatus(rented, isbn)) {
			return true;
		} else {
			return false;
		}

	}

}
