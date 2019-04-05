package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.service.ReimbursementService;

@WebServlet("/AddExpense")
public class AddExpenseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		String[] reimbInfo = mapper.readValue(request.getInputStream(), String[].class);
		
		HttpSession session = request.getSession();	
		User user = (User) session.getAttribute("user");
		
		String type = reimbInfo[0];
		Double amount = Double.parseDouble(reimbInfo[1]);
		String description = reimbInfo[2];
		if (description == "")
			description = null;
		String status = "Pending";
		String author = user.getFirstName() + " " + user.getLastName();
		String submitted = Timestamp.valueOf(LocalDateTime.now()).toString();
		Reimbursement reimbursement = new Reimbursement(amount, submitted, description, author, status, type);
		
		boolean succeded = ReimbursementService.addExpense(reimbursement, user);
		String out = "";
		if (succeded)
			 out = "true";
		else
			out = "false";
		PrintWriter writer = response.getWriter();
		writer.write(out);
	}
}
