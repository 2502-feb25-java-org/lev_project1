package com.revature.service;

import java.util.List;

import com.revature.data.ReimbursementDOA;
import com.revature.model.Reimbursement;

public class ReimbursementService {	
	public static List<Reimbursement> getByUsername(String username) {
		List<Reimbursement> reimbursements = ReimbursementDOA.getByUsername(username);
		return reimbursements;
	}
}
