package com.bestfit.server;

import javax.jdo.Query;
import javax.jdo.PersistenceManager;

import com.bestfit.client.*;
import com.bestfit.data.*;
import com.bestfit.shared.Bridge;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
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
	public String getCurrentWeight() throws IllegalArgumentException {

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		String wt = "";

		if (user != null) {

			PersistenceManager pm = getPersistenceManager();

			List<Double> weight = new ArrayList<Double>();

			try {
				Query q = pm.newQuery(Users.class, "email == u");
				q.declareParameters("com.bestfit.data.Users u");

				List<Users> Users = (List<Users>) q.execute(user.getEmail());

				if (Users != null) {
					for (Users user2 : Users)
						weight.add(user2.getWeight());

					if (weight.size() > 0) {
						wt = String.valueOf(weight.get(0));
					} else {
						wt = "Sorry, something went wrong!";
					}

				}

			} finally {
				pm.close();
			}
		}
		return wt;
	}

	@Override
	public boolean isNewUser() throws IllegalArgumentException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		boolean Found = false;

		if (user != null) {

			PersistenceManager pm = getPersistenceManager();

			List<String> weight = new ArrayList<String>();

			try {
				Query q = pm.newQuery(Users.class, "email == u");
				q.declareParameters("com.bestfit.data.Users u");

				List<Users> Users = (List<Users>) q.execute(user.getEmail());

				for (Users user2 : Users)
					weight.add(user2.getEmail());

				if (weight.size() > 0) {
					Found = false;
				} else {
					Found = true;
				}

			} finally {
				pm.close();
			}
		}
		return Found;
	}

	@Override
	public String getEmail() throws IllegalArgumentException {

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String email = "";

		if (user != null) {
			email = user.getEmail();

		}
		return email;
	}

	@Override
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
					for (Users user2 : Users)
					{
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

	@Override
	public Bridge getUserMeals() throws IllegalArgumentException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		Bridge _msg = new Bridge();
		if (user != null) {
			_msg.email = user.getEmail();
			PersistenceManager pm = getPersistenceManager();
			try {
				Query q = pm.newQuery(Meal.class, "email == u");
				q.declareParameters("com.bestfit.data.Users u");
				List<Meal> meals = (List<Meal>) q.execute(user.getEmail());
				if (meals == null)
					_msg.meals = new ArrayList<Meal>();
				else
					_msg.meals = new ArrayList<Meal>(meals);
			} finally {
				pm.close();
			}
		}
		return _msg;
	}

	@Override
	public boolean saveUserMeal(Bridge msg) throws IllegalArgumentException {
		Meal meal = msg.meal;
		PersistenceManager pm = getPersistenceManager();
		if (meal != null)
			try {
				pm.makePersistent(meal);
				return true;
			} finally {
				pm.close();
			}
		return false;
	}

	@Override
	public Bridge getFoodItems() throws IllegalArgumentException {
		Bridge _msg = new Bridge();
		PersistenceManager pm = getPersistenceManager();
		try {
			Query q = pm.newQuery(FoodItem.class);
			List<FoodItem> foods = (List<FoodItem>) q.execute();
			if (foods == null)
				_msg.foods = new ArrayList<FoodItem>();
			else
				_msg.foods = new ArrayList<FoodItem>(foods);
			
		} finally {
			pm.close();
		}
		return _msg;
	}

	@Override
	public boolean saveFoodItem(Bridge msg) throws IllegalArgumentException {
		FoodItem foodItem = msg.foodItem;
		PersistenceManager pm = getPersistenceManager();
		if (foodItem != null)
			try {
				pm.makePersistent(foodItem);
				return true;
			} finally {
				pm.close();
			}
		return false;
	}

}
