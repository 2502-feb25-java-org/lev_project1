package com.revature.model;

public class User {
	private Integer id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String role;

	public User(Integer id, String username, String password, String firstName, String lastName, String email,
			String role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getRole() {
		return role;
	}
}
