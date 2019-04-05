package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.Reimbursement;
import com.revature.service.ReimbursementService;

@WebServlet("/manager")
public class ManagerServlet extends HttpServlet {
	private static Logger log = Logger.getLogger(ManagerServlet.class);
	
	@Override   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Reimbursement> reimbursements = ReimbursementService.getAll();
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(reimbursements);
		response.setContentType("application/json");
		
		PrintWriter writer = response.getWriter();
		writer.write(json);
	}

}
