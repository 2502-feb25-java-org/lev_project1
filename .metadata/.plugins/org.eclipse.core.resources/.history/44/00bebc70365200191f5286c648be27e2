package com.revature.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.model.User;
import com.revature.servlets.LoginServlet;
import com.revature.util.DBConnection;

public class UserDOA {
	private static Logger log = Logger.getLogger(UserDOA.class);
	
	public static User getByUsername(String username) {
		String sql = "SELECT U.ERS_USERNAME, U.USER_FIRST_NAME, " +
				"U.USER_LAST_NAME, U.USER_EMAIL, R.USER_ROLES\r\n" + 
				"FROM ERS.USERS AS U\r\n" + 
				"INNER JOIN ERS.USER_ROLES AS R\r\n" + 
				"ON U.USER_ROLE_ID = R.ERS_USER_ROLE_ID\r\n" + 
				"WHERE U.ERS_USERNAME = LOWER(?)";
		User user = null;
		try(
				Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
		) {
			preparedStatement.setString(1, username.toLowerCase());
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
	        	username = resultSet.getString("ERS_USERNAME");
	        	String firstName = resultSet.getString("USER_FIRST_NAME");
	        	String lastName = resultSet.getString("USER_LAST_NAME");
	        	String email = resultSet.getString("USER_EMAIL");
	        	String role = resultSet.getString("USER_ROLES");
	        	user = new User(username, firstName, lastName, email, role);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
}
