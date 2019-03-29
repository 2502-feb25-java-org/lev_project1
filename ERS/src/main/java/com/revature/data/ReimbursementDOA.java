package com.revature.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.model.Reimbursement;
import com.revature.util.DBConnection;

public class ReimbursementDOA {
	private static Logger log = Logger.getLogger(UserDOA.class);
	
	public static List<Reimbursement> getByUsername(String username) {
		String sql = "SELECT R.REIMB_SUBMITTED, T.REIMB_TYPE, R.REIMB_AMMOUNT,  S.REIMB_STATUS\r\n" + 
				"FROM ERS.REIMBURSEMENT AS R\r\n" + 
				"INNER JOIN ERS.USERS AS U\r\n" + 
				"ON R.REIMB_AUTHOR = U.ERS_USERS_ID\r\n" + 
				"INNER JOIN ERS.REIMBURSEMENT_TYPE AS T\r\n" + 
				"ON R.REIMB_TYPE_ID = T.REIMB_TYPE_ID\r\n" + 
				"INNER JOIN ERS.REIMBURSEMENT_STATUS AS S\r\n" + 
				"ON R.REIMB_STATUS_ID = S.REIMB_STATUS_ID\r\n" + 
				"WHERE U.ERS_USERNAME = LOWER(?);";
		List<Reimbursement> reimbursements = new ArrayList<Reimbursement>();
		try (	
			Connection connection = DBConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);				
		){
			log.info("Connecting to the database to get REIMBURSEMENTS");
			preparedStatement.setString(1, username.toLowerCase());
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Double ammount = resultSet.getDouble("REIMB_AMMOUNT");
				Date submitted = resultSet.getDate("REIMB_SUBMITTED");
				String status = resultSet.getString("REIMB_STATUS");
				String type = resultSet.getString("REIMB_TYPE");
				reimbursements.add(new Reimbursement(ammount, submitted, status, type));
				log.info("Connected successfully!");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		log.info(reimbursements);
		return reimbursements;
	}

}
