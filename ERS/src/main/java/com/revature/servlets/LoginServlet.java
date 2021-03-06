package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.data.UserDOA;
import com.revature.model.User;
import com.revature.service.UserService;
import com.revature.util.DBConnection;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(LoginServlet.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// take in info from request, and return user if logged in properly, return null
		// if not proper credentials

		// get request body -- req.getInputStream() and use objectmapper to read value
		ObjectMapper mapper = new ObjectMapper();
		log.info(request.getInputStream());
		String[] loginInfo = mapper.readValue(request.getInputStream(), String[].class);
		log.info("attempting to log in user -- " + loginInfo[0]);
		User logged = UserService.logIn(loginInfo[0], loginInfo[1]);
		String out = "";
		if (logged == null) {
			out = "invalid";
		} else {
			out = logged.getFirstName() + " " + logged.getLastName() + " " + logged.getRole();
			HttpSession session = request.getSession();
			log.info("CREATED SESSION " + session.getId() + " AT " + session.getCreationTime());
			session.setAttribute("user", logged);
		}
		PrintWriter writer = response.getWriter();
		writer.write(out);
	}
}
