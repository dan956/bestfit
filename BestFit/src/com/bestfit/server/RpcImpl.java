package com.bestfit.server;

import javax.jdo.Query;
import javax.jdo.PersistenceManager;


import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

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

	// for test
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

	// for test
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

	// for test
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

	@Override
	public Bridge getUserMeals() throws IllegalArgumentException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		Bridge _msg = new Bridge();
		if (user != null) {
			_msg.email = user.getEmail();
			PersistenceManager pm = getPersistenceManager();
			// System.out.println("Server(RpcImpl.getUserMeals): Just got PersistenceManager.");
			try {
				Query q = pm.newQuery(Meal.class, "email == e");
				q.declareParameters("java.lang.String e");
				// System.out.println("Server(RpcImpl.getUserMeals): Initialized and about to execute query.");
				List<Meal> meals = (List<Meal>) q.execute(user.getEmail());

				q = pm.newQuery(FoodItem.class);
				List<FoodItem> foods = (List<FoodItem>) q.execute();

				// System.out.println("Server(RpcImpl.getUserMeals): Query complete.");
				// this is necessary because what is returned is
				// 'org.datanucleus.sco.backed.List' which is not serializable
				ArrayList<Meal> newMeals = new ArrayList<Meal>();
				for (Meal meal : meals) {
					Meal newMeal = new Meal(meal.getEmail(), meal.getLabel());
					for (String name : meal.getFoodItemNamesList())
						for (FoodItem item : foods)
							if (item.getName().equals(name)) {
								newMeal.addFoodItem(item);
								break;
							}
					newMeals.add(newMeal);
				}
				System.out
						.println("Server(RpcImpl.getUserMeals): Query returned "
								+ newMeals.size() + " results.");
				_msg.meals = newMeals;
			} finally {
				pm.close();
			}
		}
		return _msg;
	}

	@Override
	public boolean saveUserMeal(Bridge msg) throws IllegalArgumentException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		Meal meal = msg.meal;
		meal.setEmail(user.getEmail());
		System.out
				.println("Server(RpcImpl.saveUserMeal): Newest meal received is "
						+ meal.toString());
		// meal.setLabel(meal.getLabel() + ":" + meal.getEmail());

		PersistenceManager pm = getPersistenceManager();
		if (meal != null)
			try {

				pm.makePersistent(meal);
				// pm.makeTransient(meal);
				System.out
						.println("Server(RpcImpl.saveUserMeal): Meal made persistent.");
				return true;
			} finally {
				pm.close();
			}
		System.err
				.println("Server(RpcImpl.saveUserMeal): Meal could not be made persistent.");
		return false;
	}

	@Override
	public Bridge getFoodItems() throws IllegalArgumentException {
		Bridge _msg = new Bridge();
		PersistenceManager pm = getPersistenceManager();
		try {
			Query q = pm.newQuery(FoodItem.class);
			List<FoodItem> foods = (List<FoodItem>) q.execute();
			if (foods == null) {
				_msg.foods = new ArrayList<FoodItem>();
				System.err
						.println("Server(RpcImpl.getFoodItems): Query returned null.");
			} else {
				System.out
						.println("Server(RpcImpl.getFoodItems): Query returned "
								+ foods.size() + " results.");
				_msg.foods = new ArrayList<FoodItem>(foods);
			}

		} finally {
			pm.close();
		}
		return _msg;
	}

	@Override
	public boolean saveFoodItem(Bridge msg) throws IllegalArgumentException {
		FoodItem foodItem = msg.foodItem;
		System.out.println("Server(RpcImpl.saveFoodItem): Item received is "
				+ foodItem.toString());
		PersistenceManager pm = getPersistenceManager();
		if (foodItem != null)
			try {
				pm.makePersistent(foodItem);
				System.out
						.println("Server(RpcImpl.saveFoodItem): Item made persistent.");
				return true;
			} finally {
				pm.close();
			}
		System.err
				.println("Server(RpcImpl.saveFoodItem): Item could not be made persistent.");
		return false;
	}

	@Override
	public double getBMR() throws IllegalArgumentException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		Bridge _msg = new Bridge();

		double BMR = 0.0;

		if (user != null) {

			PersistenceManager pm = getPersistenceManager();

			try {
				Query q = pm.newQuery(Users.class, "email == u");
				q.declareParameters("com.bestfit.data.Users u");

				List<Users> Users = (List<Users>) q.execute(user.getEmail());

				if (Users != null) {
					for (Users user2 : Users) {

						_msg.gender = user2.getGender();
						_msg.height = user2.getHeight();
						_msg.weight = user2.getWeight();
						_msg.age = user2.getAge();

						// Men: BMR = (Weight x 42) + (Height x 17.5) - (Age x
						// 9.5) + 93
						// Women: BMR = (Weight x 29.5) + (Height x 6.5) - (Age
						// x 6.5) + 917

						if (user2.getGender().equals("Male")) {
							BMR = (user2.getWeight() * 8.7)
									+ (user2.getHeight() * 17.5)
									- (user2.getAge() * 9.5) + 93;
						} else {
							
							BMR = (user2.getWeight() * 6)
									+ (user2.getHeight() * 6.5)
									- (user2.getAge() * 6.5) + 917;
						}
					}
				}

			} finally {
				pm.close();
			}
		}
		return BMR;
	}

	@Override
	public String storeGoal(Bridge msg) throws IllegalArgumentException {
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		    
		GoalHistory a = new GoalHistory(user.getEmail(),msg.startDate,msg.targetDate,msg.targetWeight);


		PersistenceManager pm = getPersistenceManager();

		try {
			pm.makePersistent(a);
		} finally {
			pm.close();
		}

		return "success";
	}

	@Override
	public Bridge getGoalHistory() throws IllegalArgumentException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		Bridge _msg = new Bridge();


		if (user != null) {

			PersistenceManager pm = getPersistenceManager();

			try {
				Query q = pm.newQuery(GoalHistory.class, "email == u");
				q.declareParameters("com.bestfit.data.GoalHistory u");

				List<GoalHistory> goals = (List<GoalHistory>) q.execute(user.getEmail());

				if (goals != null) {
					
					System.out.print(goals.size());
				}

			} finally {
				pm.close();
			}
		}
		return _msg;
	}

}
