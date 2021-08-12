package com.cognixia.jump.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cognixia.jump.connector.ConnectionManager;
import com.cognixia.jump.model.Book;
import com.cognixia.jump.model.Librarian;

public class LibrarianDao {

	public static final Connection conn = ConnectionManager.getConnection();

	private static String SELECT_LIBRARIAN = "SELECT * FROM librarian WHERE username = ? AND password = ?;";
	private static String UPDATE_LIBRARIAN = "UPDATE librarian SET username = ?, password = ? WHERE id = ?;";

	private static String INSERT_NEW_BOOK = "INSERT INTO book (title, descr, rented, added_to_library) VALUES(?, ?, false, CAST(GETDATE() AS Date);";
	private static String UPDATE_BOOK = "UPDATE book SET title = ?, descr = ? WHERE isbn = ?;";
	private static String UPDATE_PATRON = "UPDATE patron SET account_frozen = false WHERE id = ?;";
	
	// log in
	public Librarian getLibrarian(String username, String password) {
		Librarian librarian = null;
		
		try(PreparedStatement pstmt = conn.prepareStatement(SELECT_LIBRARIAN)) {
			
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int id = rs.getInt("librarian_id");
	
				librarian = new Librarian(id, username, password);
			}
		} catch(SQLException e) {
			
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
			
			if(pstmt.executeUpdate() > 0) {
				return true;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean addBook(Book book) {
		
		try (PreparedStatement pstmt = conn.prepareStatement(INSERT_NEW_BOOK)) {
			
			pstmt.setString(1, book.getTitle());
			pstmt.setString(2, book.getDescr());
			
			if(pstmt.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	public boolean updateBook(Book book) {
		
		try(PreparedStatement pstmt = conn.prepareStatement(UPDATE_BOOK)) {
			
			pstmt.setString(1, book.getTitle());
			pstmt.setString(2, book.getDescr());
			pstmt.setString(3, book.getIsbn());
			
			if(pstmt.executeUpdate() > 0) {
				return true;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean updatePatron(int id) {
		
		try(PreparedStatement pstmt = conn.prepareStatement(UPDATE_PATRON)) {
			
			pstmt.setInt(1, id);
			
			if(pstmt.executeUpdate() > 0) {
				return true;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
