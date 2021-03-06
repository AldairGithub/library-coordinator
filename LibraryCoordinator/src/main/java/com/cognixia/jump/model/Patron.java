package com.cognixia.jump.model;

public class Patron {
	
	private int id;
	private String first_name;
	private String last_name;
	private String username;
	private String password;
	private boolean account_frozen;
	
	public Patron(int id, String first_name, String last_name, String username, String password, boolean account_frozen) {
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.username = username;
		this.password = password;
		this.account_frozen = account_frozen;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAccount_frozen() {
		return account_frozen;
	}

	public void setAccount_frozen(boolean account_frozen) {
		this.account_frozen = account_frozen;
	}

	@Override
	public String toString() {
		return "Patron [id=" + id + ", first_name=" + first_name + ", last_name=" + last_name + ", username=" + username
				+ ", password=" + password + ", account_frozen=" + account_frozen + "]";
	}
}
