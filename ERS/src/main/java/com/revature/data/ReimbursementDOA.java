package com.revature.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.util.DBConnection;

public class ReimbursementDOA {
	private static Logger log = Logger.getLogger(ReimbursementDOA.class);
	
	public static List<Reimbursement> getAll() {
		List<Reimbursement> reimbursements = new ArrayList<Reimbursement>();
		String sql = "SELECT R.REIMB_ID, R.REIMB_SUBMITTED, E.USER_FIRST_NAME, \r\n" + 
				"E.USER_LAST_NAME, T.REIMB_TYPE, R.REIMB_AMOUNT, \r\n" + 
				"R.REIMB_DESCRIPTION ,S.REIMB_STATUS, R.REIMB_RESOLVED, \r\n" + 
				"M.USER_FIRST_NAME, M.USER_LAST_NAME \r\n" + 
				"FROM ERS.REIMBURSEMENT AS R\r\n" + 
				"JOIN ERS.USERS AS E\r\n" + 
				"ON R.REIMB_AUTHOR = E.ERS_USERS_ID\r\n" + 
				"JOIN ERS.REIMBURSEMENT_TYPE AS T\r\n" + 
				"ON R.REIMB_TYPE_ID = T.REIMB_TYPE_ID\r\n" + 
				"JOIN ERS.REIMBURSEMENT_STATUS AS S\r\n" + 
				"ON R.REIMB_STATUS_ID = S.REIMB_STATUS_ID\r\n" + 
				"LEFT JOIN ERS.USERS AS M\r\n" + 
				"ON R.REIMB_RESOLVER = M.ERS_USERS_ID;";
		try (	
				Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);				
			){
				log.info("Connecting to the database to get REIMBURSEMENTS");
				ResultSet resultSet = preparedStatement.executeQuery();
				log.info("Connected successfully!");
				reimbursements = getFromResultset(resultSet);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			return reimbursements;
	}
	
	public static List<Reimbursement> getByUsername(String username) {
		List<Reimbursement> reimbursements = new ArrayList<Reimbursement>();
		String sql = "SELECT R.REIMB_ID, R.REIMB_SUBMITTED, E.USER_FIRST_NAME, \r\n" + 
				"E.USER_LAST_NAME, T.REIMB_TYPE, R.REIMB_AMOUNT, \r\n" + 
				"R.REIMB_DESCRIPTION ,S.REIMB_STATUS, R.REIMB_RESOLVED, \r\n" + 
				"M.USER_FIRST_NAME, M.USER_LAST_NAME \r\n" + 
				"FROM ERS.REIMBURSEMENT AS R\r\n" + 
				"JOIN ERS.USERS AS E\r\n" + 
				"ON R.REIMB_AUTHOR = E.ERS_USERS_ID\r\n" + 
				"JOIN ERS.REIMBURSEMENT_TYPE AS T\r\n" + 
				"ON R.REIMB_TYPE_ID = T.REIMB_TYPE_ID\r\n" + 
				"JOIN ERS.REIMBURSEMENT_STATUS AS S\r\n" + 
				"ON R.REIMB_STATUS_ID = S.REIMB_STATUS_ID\r\n" + 
				"LEFT JOIN ERS.USERS AS M\r\n" + 
				"ON R.REIMB_RESOLVER = M.ERS_USERS_ID\r\n" + 
				"WHERE E.ERS_USERNAME = LOWER(?);";
		try (	
			Connection connection = DBConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);				
		){
			log.info("Connecting to the database to get REIMBURSEMENTS");
			preparedStatement.setString(1, username.toLowerCase());
			ResultSet resultSet = preparedStatement.executeQuery();
			log.info("Connected successfully!");
			reimbursements = getFromResultset(resultSet);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return reimbursements;
	}
	
	private static List<Reimbursement> getFromResultset(ResultSet resultSet) throws SQLException{
		List<Reimbursement> reimbursements = new ArrayList<Reimbursement>();
		while(resultSet.next()) {
			Integer id = resultSet.getInt(1);
			String submitted = timeToString(resultSet.getTimestamp(2));
			String author = getName(resultSet.getString(3), resultSet.getString(4));
			String type = resultSet.getString(5);
			Double amount = resultSet.getDouble(6);
			String description = resultSet.getString(7);
			String status = resultSet.getString(8);
			String resolved = timeToString(resultSet.getTimestamp(9));
			String resolver = getName(resultSet.getString(10), resultSet.getString(11));	
			reimbursements.add(new Reimbursement(id, amount, submitted, resolved, 
					description, author, resolver, status, type));
		}
		return reimbursements;	
	}
	
	private static String timeToString(Timestamp time) {
		if (time != null)
			return time.toString().substring(0, 16);
		else
			return "N/A";
	}
	
	private static String getName (String firstName, String lastName){
		if (firstName == null && lastName == null)
			return "N/A";
		else
			return firstName + " " + lastName;
	}
	
	public static boolean addReimbursement(Reimbursement reimbursement, User author) {
		boolean succeded = false;
		String sql = "INSERT INTO ERS.REIMBURSEMENT (REIMB_AMOUNT, REIMB_SUBMITTED,\r\n" + 
				"REIMB_DESCRIPTION, REIMB_AUTHOR, REIMB_STATUS_ID, REIMB_TYPE_ID)\r\n" + 
				"SELECT ?, ?, ?, ?,\r\n" + 
				"S.REIMB_STATUS_ID, T.REIMB_TYPE_ID\r\n" + 
				"FROM ERS.REIMBURSEMENT_STATUS AS S, ERS.REIMBURSEMENT_TYPE AS T\r\n" + 
				"WHERE S.REIMB_STATUS = ? AND T.REIMB_TYPE = ?;";
		String amount = reimbursement.getAmount().toString();
		String submitted = reimbursement.getSubmitted();
		String description = reimbursement.getDescription();
		Integer authorId = author.getId();
		String status = reimbursement.getStatus();
		String type = reimbursement.getType();
		try (	
				Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);				
		){
			log.info("Connecting to the database to add an expense");
			preparedStatement.setString(1, amount);
			preparedStatement.setString(2, submitted);
			preparedStatement.setString(3, description);
			preparedStatement.setInt(4, authorId);
			preparedStatement.setString(5, status);
			
			preparedStatement.setString(6, type);
			int numRowsAffected = preparedStatement.executeUpdate();
			if (numRowsAffected == 1)
				succeded = true;
			else
				succeded = false;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return succeded;
	}
	
	public static boolean updateReinbursement(Reimbursement reimbursement, User resolver) {
		boolean succeded = false;
		String sql = "UPDATE ERS.REIMBURSEMENT\r\n" + 
				"SET  REIMB_RESOLVED = ?, \r\n" + 
				"REIMB_RESOLVER = ?, REIMB_STATUS_ID = S.REIMB_STATUS_ID \r\n" + 
				"FROM ERS.REIMBURSEMENT_STATUS AS S\r\n" + 
				"WHERE REIMB_ID = ? AND S.REIMB_STATUS = ?;";
		String resolved = reimbursement.getResolved();
		Integer resolverId = resolver.getId();
		Integer id = reimbursement.getId();
		String status = reimbursement.getStatus();
		try (	
				Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);				
		){
			log.info("Connecting to the database to change the status.");
			preparedStatement.setString(1, resolved);
			preparedStatement.setInt(2, resolverId);
			preparedStatement.setInt(3, id);
			preparedStatement.setString(4, status);
			int numRowsAffected = preparedStatement.executeUpdate();
			if (numRowsAffected == 1)
				succeded = true;
			else
				succeded = false;
		}
		catch (Exception e) {
			e.printStackTrace();
		}		
		return succeded;
	}
}

