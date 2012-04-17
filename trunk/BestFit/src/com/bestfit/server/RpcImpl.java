package com.bestfit.server;

import javax.jdo.Query;
import javax.jdo.PersistenceManager;

import com.bestfit.client.*;
import com.bestfit.data.*;
import com.bestfit.shared.Bridge;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class RpcImpl extends RemoteServiceServlet implements RpcServices {

	// private static final Logger LOG =
	// Logger.getLogger(RpcImpl.class.getName());

	private static final PersistenceManagerFactory PMF = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	private PersistenceManager getPersistenceManager() {
		return PMF.getPersistenceManager();
	}

	public Bridge getUsers(String email) throws IllegalArgumentException {
		Bridge tmpBUser = new Bridge();

		tmpBUser.lastName = "aaa";

		PersistenceManager pm = getPersistenceManager();

		List<String> us = new ArrayList<String>();

		try {
			Query q = pm.newQuery(Users.class, "email == u");
			q.declareParameters("com.bestfit.data.Users u");

			List<Users> a = (List<Users>) q.execute("alrowaithy@gmail.com");

			for (Users uu : a)
				us.add(uu.getFirstName());

			tmpBUser.firstName = us.get(0);
		} finally {
			pm.close();
		}

		return tmpBUser;

	}

	public Bridge saveUsers(String email) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		Bridge tmpBUser = new Bridge();

		tmpBUser.firstName = "aaa";

		Users a = new Users("alrowaithy@gmail.com", "Mohammad", "Alrowaithy",
				27, 345, 123, "Male");

		PersistenceManager pm = getPersistenceManager();

		try {
			pm.makePersistent(a);
		} finally {
			pm.close();
		}

		return tmpBUser;
	}

	public Bridge logIn(String url) throws IllegalArgumentException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		Bridge msg = new Bridge();

		if (user != null) {
			msg.IsloggedIN = true;
			msg.email = user.getEmail();
			msg.LogoutURL = userService.createLogoutURL(url);

		} else {
			msg.IsloggedIN = false;
			msg.LogginURL = userService.createLoginURL(url);
		}

		return msg;
	}

	public String registerUser(Bridge msg) throws IllegalArgumentException {

		Users a = new Users(msg.email, msg.firstName, msg.lastName, msg.age,
				msg.height, msg.weight, msg.gender);

		PersistenceManager pm = getPersistenceManager();

		try {
			pm.makePersistent(a);
		} finally {
			pm.close();
		}

		return "success";
	}

	@Override
	public String getCurrentWeight(String email)
			throws IllegalArgumentException {
	
		String wt = "";
		PersistenceManager pm = getPersistenceManager();

		List<Double> weight = new ArrayList<Double>();

		try {
			Query q = pm.newQuery(Users.class, "email == u");
			q.declareParameters("com.bestfit.data.Users u");

			List<Users> Users = (List<Users>) q.execute(email);

			for (Users user : Users)
				weight.add(user.getWeight());

			wt = String.valueOf(weight.get(0));
			
		} finally {
			pm.close();
		}

		return wt;
	}

}
