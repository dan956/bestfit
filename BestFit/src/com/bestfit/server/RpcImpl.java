package com.bestfit.server;
import java.util.Calendar;
import javax.jdo.Query;
import javax.jdo.PersistenceManager;

import com.bestfit.client.*;
import com.bestfit.data.*;
import com.bestfit.shared.Bridge;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import java.util.Date;
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

		String currentWeight = "";

		PersistenceManager pm = getPersistenceManager();

		try {
			Query q = pm.newQuery(Weight.class, "email == u");
			q.declareParameters("com.bestfit.data.Weight u");

			List<Weight> weights = (List<Weight>) q.execute(getLoggedinUserEmail());

			if (weights != null) {

				if (weights.size() > 0) {
					currentWeight = String.valueOf(weights.get(
							weights.size() - 1).getWeight());
				} else {
					currentWeight = "New User";
				}

			}

		} finally {
			pm.close();
		}

		return currentWeight;
	}

	@Override
	public boolean isNewUser() throws IllegalArgumentException {

		boolean Found = false;

		PersistenceManager pm = getPersistenceManager();

		try {
			Query q = pm.newQuery(Users.class, "email == u");
			q.declareParameters("com.bestfit.data.Users u");

			List<Users> Users = (List<Users>) q.execute(getLoggedinUserEmail());

			if (Users.size() > 0) {
				Found = false;
			} else {
				Found = true;
			}

		} finally {
			pm.close();
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

		Bridge _msg = new Bridge();

		PersistenceManager pm = getPersistenceManager();

		try {
			Query q = pm.newQuery(Users.class, "email == u");
			q.declareParameters("com.bestfit.data.Users u");

			List<Users> Users = (List<Users>) q.execute(getLoggedinUserEmail());

			if (Users != null) {
				for (Users user2 : Users) {
					_msg.firstName = user2.getFirstName();
					_msg.lastName = user2.getLastName();
					_msg.email = user2.getEmail();
					_msg.gender = user2.getGender();
					_msg.height = user2.getHeight();

					if (getCurrentWeight().equals("New User"))
						_msg.weight = user2.getWeight();
					else
						_msg.weight = Double.valueOf(getCurrentWeight());

					_msg.age = user2.getAge();
				}
			}

		} finally {
			pm.close();
		}

		return _msg;

	}

	@Override
	public Bridge getUserMeals() throws IllegalArgumentException {
		Bridge _msg = new Bridge();
		_msg.email = getLoggedinUserEmail();
		PersistenceManager pm = getPersistenceManager();

		try {
			
			Calendar calender = Calendar.getInstance();
			calender.set(Calendar.HOUR_OF_DAY, 0);
			calender.set(Calendar.MINUTE, 0);
			calender.set(Calendar.SECOND, 0);
			calender.set(Calendar.MILLISECOND, 0);
			
			
			Query q = pm.newQuery(Meal.class, "email == e && dateOfMeal >= today");
			q.declareParameters("java.lang.String e, java.util.Date today");

			List<Meal> meals = (List<Meal>) q.execute(getLoggedinUserEmail(),calender.getTime());

			q = pm.newQuery(FoodItem.class);
			List<FoodItem> foods = (List<FoodItem>) q.execute();

			ArrayList<Meal> newMeals = new ArrayList<Meal>();
			for (Meal meal : meals) {
				Meal newMeal = new Meal(meal.getEmail(), meal.getLabel(), meal.getDateOfMeal());
				for (String name : meal.getFoodItemNamesList())
					for (FoodItem item : foods)
						if (item.getName().equals(name)) {
							newMeal.addFoodItem(item);
							break;
						}
				newMeals.add(newMeal);
			}
			System.out.println("Server(RpcImpl.getUserMeals): Query returned "
					+ newMeals.size() + " results.");
			_msg.meals = newMeals;
		} finally {
			pm.close();
		}

		return _msg;
	}

	@Override
	public boolean saveUserMeal(Bridge msg) throws IllegalArgumentException {

		Meal meal = msg.meal;
		meal.setEmail(getLoggedinUserEmail());
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

		Bridge _msg = new Bridge();

		double BMR = 0.0;

		PersistenceManager pm = getPersistenceManager();

		try {
			Query q = pm.newQuery(Users.class, "email == u");
			q.declareParameters("com.bestfit.data.Users u");

			List<Users> Users = (List<Users>) q.execute(getLoggedinUserEmail());

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

		return BMR;
	}

	@Override
	public String storeGoal(Bridge msg) throws IllegalArgumentException {
			    
		GoalHistory a = new GoalHistory(getLoggedinUserEmail(),msg.startDate,msg.targetDate,msg.targetWeight);

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

		Bridge _msg = new Bridge();

		PersistenceManager pm = getPersistenceManager();

		try {
			Query q = pm.newQuery(GoalHistory.class, "email == u");
			q.declareParameters("com.bestfit.data.GoalHistory u");

			List<GoalHistory> goals = (List<GoalHistory>) q.execute(getLoggedinUserEmail());

			if (goals != null) {

				System.out.print(goals.size());
				_msg.goals = new ArrayList<GoalHistory>(goals);
			}

		} finally {
			pm.close();
		}

		return _msg;
	}

	@Override
	public String storeNewWeight(Double weight, Date currentDate)
			throws IllegalArgumentException {
		
		Weight a = new Weight(getLoggedinUserEmail(),weight,currentDate);

		PersistenceManager pm = getPersistenceManager();

		try {
			pm.makePersistent(a);
		} finally {
			pm.close();
		}

		return "success";
	}

	@Override
	public Bridge getWeightHistory() throws IllegalArgumentException {

		Bridge _msg = new Bridge();

		PersistenceManager pm = getPersistenceManager();

		try {
			Query q = pm.newQuery(Weight.class, "email == u");
			q.declareParameters("java.lang.String u");

			List<Weight> weights = (List<Weight>) q.execute(getLoggedinUserEmail());

			if (weights != null) {
				_msg.weightHistory = new ArrayList<Weight>(weights);
			}

		} finally {
			pm.close();
		}

		return _msg;
	}
	
	public String getLoggedinUserEmail(){
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		return user.getEmail();
	}
	
	@Override
	public Bridge getUserWorkouts() throws IllegalArgumentException {
		Bridge _msg = new Bridge();

		_msg.email = getLoggedinUserEmail();
		PersistenceManager pm = getPersistenceManager();
		try {
			Query q = pm.newQuery(Workout.class, "email == e");
			q.declareParameters("java.lang.String e");
			List<Workout> workouts = (List<Workout>) q.execute(getLoggedinUserEmail());

			q = pm.newQuery(ExerciseItem.class);
			List<ExerciseItem> exercises = (List<ExerciseItem>) q.execute();

			ArrayList<Workout> newWorkouts = new ArrayList<Workout>();
			for (Workout workout : workouts) {
				Workout newWorkout = new Workout(workout.getEmail(), workout.getLabel(), workout.getDateOfWorkout());
				for (String name : workout.getExerciseItemNamesList())
					for (ExerciseItem item : exercises)
						if (item.getName().equals(name)) {
							newWorkout.addExerciseItem(item);
							break;
						}
				newWorkouts.add(newWorkout);
			}
			System.out.println("Server(RpcImpl.getUserWorkouts): Query returned "
					+ newWorkouts.size() + " results.");
			_msg.workouts = newWorkouts;
		} finally {
			pm.close();
		}

		return _msg;
	}

	@Override
	public boolean saveUserWorkout(Bridge msg) throws IllegalArgumentException {

		Workout workout = msg.workout;
		workout.setEmail(getLoggedinUserEmail());
		System.out
				.println("Server(RpcImpl.saveUserWorkout): Newest workout received is "
						+ workout.toString());

		PersistenceManager pm = getPersistenceManager();
		if (workout != null)
			try {
				pm.makePersistent(workout);
				System.out
						.println("Server(RpcImpl.saveUserWorkout): Workout made persistent.");
				return true;
			} finally {
				pm.close();
			}
		System.err
				.println("Server(RpcImpl.saveUserWorkout): Workout could not be made persistent.");
		return false;
	}

	@Override
	public Bridge getExerciseItems() throws IllegalArgumentException {
		Bridge _msg = new Bridge();
		PersistenceManager pm = getPersistenceManager();
		try {
			Query q = pm.newQuery(ExerciseItem.class);
			List<ExerciseItem> exercises = (List<ExerciseItem>) q.execute();
			if (exercises == null) {
				_msg.exercises = new ArrayList<ExerciseItem>();
				System.err
						.println("Server(RpcImpl.getExerciseItems): Query returned null.");
			} else {
				System.out
						.println("Server(RpcImpl.getExerciseItems): Query returned "
								+ exercises.size() + " results.");
				_msg.exercises = new ArrayList<ExerciseItem>(exercises);
			}

		} finally {
			pm.close();
		}
		return _msg;
	}

	@Override
	public boolean saveExerciseItem(Bridge msg) throws IllegalArgumentException {
		ExerciseItem exerciseItem = msg.exercise;
		System.out.println("Server(RpcImpl.saveExerciseItem): Item received is "
				+ exerciseItem.toString());
		PersistenceManager pm = getPersistenceManager();
		if (exerciseItem != null)
			try {
				pm.makePersistent(exerciseItem);
				System.out
						.println("Server(RpcImpl.saveExerciseItem): Item made persistent.");
				return true;
			} finally {
				pm.close();
			}
		System.err
				.println("Server(RpcImpl.saveExerciseItem): Item could not be made persistent.");
		return false;
	}

	@Override
	public String getUserName() throws IllegalArgumentException {
		String Name = "";

		PersistenceManager pm = getPersistenceManager();

		try {
			Query q = pm.newQuery(Users.class, "email == u");
			q.declareParameters("com.bestfit.data.Users u");

			List<Users> users = (List<Users>) q.execute(getLoggedinUserEmail());

			if (users != null) {

				if (users.size() > 0) {
					Name = users.get(users.size() - 1).getFirstName();
				} else {
					Name = "New User";
				}

			}

		} finally {
			pm.close();
		}

		return Name;
	}


}
