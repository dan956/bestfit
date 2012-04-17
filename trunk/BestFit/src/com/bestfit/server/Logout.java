package com.bestfit.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.core.client.GWT;

@SuppressWarnings("serial")
public class Logout extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		resp.setContentType("text/html");
		//PrintWriter out = resp.getWriter();

		if (user != null) {

			resp.sendRedirect(userService.createLogoutURL(req.getRequestURI().replaceFirst("logout", "registration.html")));

		} else {

			resp.sendRedirect("registration.html");
			//String redirectURL = "index.html";
			//resp.sendRedirect(userService.createLoginURL(req.getRequestURI().replaceFirst("logout", "registration.html")));

		}
	}

}
