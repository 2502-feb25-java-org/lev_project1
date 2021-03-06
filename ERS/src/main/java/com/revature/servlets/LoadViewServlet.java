package com.revature.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.revature.data.UserDOA;

@WebServlet("*.view")
public class LoadViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(UserDOA.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("REQUEST SENT TO URI: " + request.getRequestURI());

		String uri = request.getRequestURI();
		String name = uri.substring(5, (uri.length() - 5));
		request.getRequestDispatcher("partials/" + name + ".html").forward(request, response);
	}
}
