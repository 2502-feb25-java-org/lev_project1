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

@WebServlet("/UpdateStatus")
public class UpdateStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String[] updateInfo = mapper.readValue(request.getInputStream(), String[].class);
		
		HttpSession session = request.getSession();	
		User user = (User) session.getAttribute("user");
		
		Integer id = Integer.parseInt(updateInfo[0]);
		String status = updateInfo[1];
		String resolved = Timestamp.valueOf(LocalDateTime.now()).toString();
		
		Reimbursement reimbursement = new Reimbursement(id, resolved, status);
		
		boolean succeded = ReimbursementService.updateReinbursement(reimbursement, user);
		String out = "";
		if (succeded)
			 out = "true";
		else
			out = "false";
		PrintWriter writer = response.getWriter();
		writer.write(out);
	}

}
