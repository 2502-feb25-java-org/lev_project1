package com.revature.service;

import java.util.List;

import com.revature.data.ReimbursementDOA;
import com.revature.model.Reimbursement;
import com.revature.model.User;

public class ReimbursementService {	
	
	public static List<Reimbursement> getAll() {
		List<Reimbursement> reimbursements = ReimbursementDOA.getAll();
		return reimbursements;
	}
	
	public static List<Reimbursement> getByUsername(String username) {
		List<Reimbursement> reimbursements = ReimbursementDOA.getByUsername(username);
		return reimbursements;
	}
	
	public static boolean addExpense(Reimbursement reimbursement, User author) {
		return ReimbursementDOA.addReimbursement(reimbursement, author);
	}
	
	public static boolean updateReinbursement(Reimbursement reimbursement, User resolver) {
		return ReimbursementDOA.updateReinbursement(reimbursement, resolver);
	}
}
