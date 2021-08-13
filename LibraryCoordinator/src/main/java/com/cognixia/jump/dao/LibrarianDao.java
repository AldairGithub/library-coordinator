package com.cognixia.jump.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cognixia.jump.connector.ConnectionManager;
import com.cognixia.jump.model.Book;
import com.cognixia.jump.model.Librarian;

public class LibrarianDao {

	public static final Connection conn = ConnectionManager.getConnection();

	private static String SELECT_LIBRARIAN = "SELECT * FROM librarian WHERE username = ? AND password = ?;";
	private static String UPDATE_LIBRARIAN = "UPDATE librarian SET username = ?, password = ? WHERE librarian_id = ?;";
	private static String DELETE_BOOK = "DELETE FROM book WHERE isbn = ?";
	private static String INSERT_NEW_BOOK = "INSERT INTO book (title, descr, rented, added_to_library) VALUES(?, ?, false, CAST(GETDATE() AS Date);";
	private static String UPDATE_BOOK = "UPDATE book SET title = ?, descr = ? WHERE isbn = ?;";
	private static String UPDATE_PATRON = "UPDATE patron SET account_frozen = ? WHERE patron_id = ?;";
	private static String GET_BOOK_BY_ISBN = "SELECT * FROM book WHERE isbn = ?";
	private static String DELETE_BOOK_FROM_CHECKOUT = "DELETE FROM book_checkout WHERE isbn = ?";

	public Book getBookByIsbn(String isbn) {
		Book book = null;

		try (PreparedStatement pstmt = conn.prepareStatement(GET_BOOK_BY_ISBN)) {

			pstmt.setString(1, isbn);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				String ISBN = rs.getString("isbn");
				String title = rs.getString("title");
				String descr = rs.getString("descr");
				boolean rented = rs.getInt("rented") == 0;
				Date added_to_library = rs.getDate("added_to_library");

				book = new Book(ISBN, title, descr, rented, added_to_library);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return book;
	}

	public boolean deleteBook(String isbn) {
		try (PreparedStatement pstmt = conn.prepareStatement(DELETE_BOOK_FROM_CHECKOUT)) {
			pstmt.setString(1, isbn);
			pstmt.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try (PreparedStatement pstmt = conn.prepareStatement(DELETE_BOOK)) {
			pstmt.setString(1, isbn);
			return pstmt.executeUpdate() > 0; // true
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	// log in
	public Librarian getLibrarian(String username, String password) {
		Librarian librarian = null;

		try (PreparedStatement pstmt = conn.prepareStatement(SELECT_LIBRARIAN)) {

			pstmt.setString(1, username);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				int id = rs.getInt("librarian_id");

				librarian = new Librarian(id, username, password);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		System.out.println(librarian);
		return librarian;

	}

	// update patron
	public boolean updateLibrarian(Librarian librarian) {
		try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_LIBRARIAN)) {

			pstmt.setString(1, librarian.getUsername());
			pstmt.setString(2, librarian.getPassword());
			pstmt.setInt(3, librarian.getId());

			if (pstmt.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean addBook(Book book) {

		try (PreparedStatement pstmt = conn.prepareStatement(INSERT_NEW_BOOK)) {

			pstmt.setString(1, book.getTitle());
			pstmt.setString(2, book.getDescr());

			if (pstmt.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	public boolean updateBook(String isbn, String title, String descr) {

		try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_BOOK)) {

			pstmt.setString(1, title);
			pstmt.setString(2, descr);
			pstmt.setString(3, isbn);

			if (pstmt.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean updatePatron(int id, boolean frozen) {

		try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_PATRON)) {
			
			pstmt.setBoolean(1, frozen);
			pstmt.setInt(2, id);

			if (pstmt.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
