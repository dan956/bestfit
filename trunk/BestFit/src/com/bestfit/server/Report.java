package com.bestfit.server;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bestfit.client.RpcServices;
import com.bestfit.client.RpcServicesAsync;
import com.bestfit.data.Users;
import com.bestfit.shared.Bridge;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.core.client.GWT;
import com.google.gwt.rpc.server.RPC;
import com.google.gwt.user.client.rpc.AsyncCallback;

@SuppressWarnings("serial")
public class Report extends HttpServlet {

	
	private static final PersistenceManagerFactory PMF = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		resp.setContentType("text/html");
		//PrintWriter out = resp.getWriter();

		if (user != null) {

			final PrintWriter out = resp.getWriter();
			

				//	Br
				//	out.write("");
	
			
			
			
			//resp.sendRedirect(userService.createLogoutURL(req.getRequestURI().replaceFirst("logout", "registration.html")));

		} else {

			resp.sendRedirect("registration.html");
			//String redirectURL = "index.html";
			//resp.sendRedirect(userService.createLoginURL(req.getRequestURI().replaceFirst("logout", "registration.html")));

		}
	}
	
	private PersistenceManager getPersistenceManager() {
		return PMF.getPersistenceManager();
	}
	
	public Bridge getUserProfile() throws IllegalArgumentException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		Bridge _msg = new Bridge();

		if (user != null) {

			PersistenceManager pm = getPersistenceManager();

			try {
				Query q = pm.newQuery(Users.class, "email == u");
				q.declareParameters("com.bestfit.data.Users u");

				List<Users> Users = (List<Users>) q.execute(user.getEmail());

				if (Users != null) {
					for (Users user2 : Users) {
						_msg.firstName = user2.getFirstName();
						_msg.lastName = user2.getLastName();
						_msg.email = user2.getEmail();
						_msg.gender = user2.getGender();
						_msg.height = user2.getHeight();
						_msg.weight = user2.getWeight();
						_msg.age = user2.getAge();

					}

				}

			} finally {
				pm.close();
			}
		}
		return _msg;

	}
	

}
