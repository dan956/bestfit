package com.bestfit.client;

import java.util.ArrayList;
import java.util.Date;
import com.bestfit.data.ExerciseItem;
import com.bestfit.data.FoodItem;
import com.bestfit.data.Meal;
import com.bestfit.data.Workout;
import com.bestfit.shared.Bridge;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.user.client.ui.VerticalPanel;


public class Calculator implements EntryPoint {
	private final RpcServicesAsync rpc = GWT.create(RpcServices.class);
	private String email;
	private RootPanel rootPanel;
	private TabPanel tabPanel;
	private VerticalPanel MealVerticalPanel;
	private VerticalPanel WorkoutVerticalPanel;
	private FlexTable MealFlexTable;
	private FlexTable WorkoutFlexTable;
	private FlexTable FoodsFlexTable;
	private FlexTable ExercisesFlexTable;
	private TextBox MealLabelTextBox;
	private TextBox WorkoutLabelTextBox;
	private ListBox NewFoodItemList;
	private ListBox NewExerciseItemList;
	private Button AddFoodPshBtn;
	private Button AddExercisePshBtn;
	private Button SaveMealPshBtn;
	private Button SaveWorkoutPshBtn;
	private TextBox TotalCalsTextBox;
	private TextBox TotalCalsBurnedTextBox;
	
	private ArrayList<Meal> meals = new ArrayList<Meal>();
	private ArrayList <Workout> workouts = new ArrayList<Workout>();
	private ArrayList<FoodItem> foods = new ArrayList<FoodItem>();
	private ArrayList <ExerciseItem> exercises = new ArrayList<ExerciseItem>();
	private Meal newMeal;
	private Workout newWorkout;
	
	
	public void onModuleLoad() {
		rootPanel = RootPanel.get("calculatorCont");
		rootPanel.setSize("600px", "450px");
		
		tabPanel = new TabPanel();
		rootPanel.add(tabPanel);
		tabPanel.setSize("450px", "350px");
		
		tabPanel.addStyleName("table-center");
		
		/* Meal / Food */
		
		MealVerticalPanel = new VerticalPanel();
		tabPanel.add(MealVerticalPanel, " Meal ", false);
		tabPanel.selectTab(0);
		MealVerticalPanel.setSize("6cm", "4cm");
		
		tabPanel.addStyleName("table-center");
		
		MealFlexTable = new FlexTable();
		MealVerticalPanel.add(MealFlexTable);
		MealFlexTable.setWidth("451px");
		
		Label lblFoodItem = new Label("Food Item:");
		MealFlexTable.setWidget(0, 0, lblFoodItem);
		lblFoodItem.setWidth("121px");
		AddFoodPshBtn = new Button("Add");
		AddFoodPshBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addFood();
			}
		});
		
		NewFoodItemList = new ListBox();
		NewFoodItemList.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == KeyCodes.KEY_ENTER){
					addFood();
				}
			}
		});
		MealFlexTable.setWidget(0, 1, NewFoodItemList);
		MealFlexTable.setWidget(0, 2, AddFoodPshBtn);
		AddFoodPshBtn.setWidth("50px");
		
		FoodsFlexTable = new FlexTable();
		MealVerticalPanel.add(FoodsFlexTable);
		FoodsFlexTable.setWidth("451px");
	    FoodsFlexTable.setText(0, 0, "Food Items");
	    FoodsFlexTable.setText(0, 1, "Calories");
	    FoodsFlexTable.setText(0, 2, "Fat");
	    FoodsFlexTable.setText(0,	3, "Carbs");
	    FoodsFlexTable.setText(0, 4, "Protein");
		FoodsFlexTable.setStyleName("cw-FlexTable");
		
		Label lblTotalCalories = new Label("Total Calories:");
		lblTotalCalories.setStylePrimaryName("gwt-TotalCa");
		MealFlexTable.setWidget(3, 0, lblTotalCalories);
		lblTotalCalories.setWidth("103px");
		
		TotalCalsTextBox = new TextBox();
		TotalCalsTextBox.setReadOnly(true);
		TotalCalsTextBox.setText(Integer.toString(0));
		MealFlexTable.setWidget(3, 1, TotalCalsTextBox);
		
		Label lblNewLabel = new Label("Meal Name:");
		MealFlexTable.setWidget(4, 0, lblNewLabel);
		
		MealLabelTextBox = new TextBox();
		MealFlexTable.setWidget(4, 1, MealLabelTextBox);
		
		SaveMealPshBtn = new Button("Save");
		SaveMealPshBtn.setEnabled(true);
		SaveMealPshBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (MealLabelTextBox.getText().trim().equals(""))
					Window.alert("Please name this meal.");
				else {
					newMeal.setLabel(MealLabelTextBox.getText());
					newMeal.setDateOfMeal(new Date());
					meals.add(newMeal);
					saveUserMeal();
				}
			}
		});
		MealFlexTable.setWidget(4, 2, SaveMealPshBtn);
		SaveMealPshBtn.setSize("60px", "25px");
		
		/* Workout / Exercise */
		
		WorkoutVerticalPanel = new VerticalPanel();
		tabPanel.add(WorkoutVerticalPanel, " Workout ", false);
		tabPanel.selectTab(0);
		WorkoutVerticalPanel.setSize("6cm", "4cm");
		
		tabPanel.addStyleName("table-center");
		
		WorkoutFlexTable = new FlexTable();
		WorkoutVerticalPanel.add(WorkoutFlexTable);
		WorkoutFlexTable.setWidth("451px");
		
		Label lblExerciseItem = new Label("Exercise Item:");
		WorkoutFlexTable.setWidget(0, 0, lblExerciseItem);
		lblExerciseItem.setWidth("121px");
		AddExercisePshBtn = new Button("Add");
		AddExercisePshBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addExercise();
			}
		});
		
		NewExerciseItemList = new ListBox();
		NewExerciseItemList.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == KeyCodes.KEY_ENTER){
					addExercise();
				}
			}
		});
		WorkoutFlexTable.setWidget(0, 1, NewExerciseItemList);
		WorkoutFlexTable.setWidget(0, 2, AddExercisePshBtn);
		AddExercisePshBtn.setWidth("50px");
		
		ExercisesFlexTable = new FlexTable();
		WorkoutVerticalPanel.add(ExercisesFlexTable);
		ExercisesFlexTable.setWidth("451px");
	    ExercisesFlexTable.setText(0, 0, "Exercise Items");
	    ExercisesFlexTable.setText(0, 1, "Time Interval");
	    ExercisesFlexTable.setText(0, 2, "Burn Rate");
		ExercisesFlexTable.setStyleName("cw-FlexTable");
		
		
		Label lblTotalCaloriesBurned = new Label("Total Calories:");
		lblTotalCaloriesBurned.setStylePrimaryName("gwt-TotalCa");
		WorkoutFlexTable.setWidget(3, 0, lblTotalCaloriesBurned);
		lblTotalCaloriesBurned.setWidth("103px");
		
		TotalCalsBurnedTextBox = new TextBox();
		TotalCalsBurnedTextBox.setReadOnly(true);
		TotalCalsBurnedTextBox.setText(Integer.toString(0));
		WorkoutFlexTable.setWidget(3, 1, TotalCalsBurnedTextBox);
		
		WorkoutFlexTable.setWidget(4, 0, new Label("Workout Name:"));
		
		WorkoutLabelTextBox = new TextBox();
		WorkoutFlexTable.setWidget(4, 1, WorkoutLabelTextBox);
		
		SaveWorkoutPshBtn = new Button("Save");
		SaveWorkoutPshBtn.setEnabled(true);
		SaveWorkoutPshBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (WorkoutLabelTextBox.getText().trim().equals(""))
					Window.alert("Please name this workout.");
				else {
					newWorkout.setLabel(WorkoutLabelTextBox.getText());
					newWorkout.setDateOfWorkout(new Date());
					workouts.add(newWorkout);
					saveUserWorkout();
				}
			}
		});
		WorkoutFlexTable.setWidget(4, 2, SaveWorkoutPshBtn);
		SaveWorkoutPshBtn.setSize("60px", "25px");
		
		
		// one time run to populate FoodItem persistence
//		{
//			// name, cals, fatcals, fatgrams, carbs, protein
//			FoodItem items[] = new FoodItem[10];
//			items[0] = new FoodItem("Egg, Whole, Cooked, Medium", 71, 40, 5.15, .48, 5.28);
//			items[1] = new FoodItem("Egg, Whole, Cooked, Large", 81, 45, 5.86, .55, 6);
//			items[2] = new FoodItem("Egg, Whole, Cooked, Extra Large", 94, 51, 6.79, .64, 6.96);
//			items[3] = new FoodItem("Bacon, Medium Slice", 43, 40, 3.34, .11, 2.96);
//			items[4] = new FoodItem("Pancake, Buttermilk, 5-1/2\" dia", 110, 28, 2.53, 19.25, 2.61);
//			items[5] = new FoodItem("Waffle, Round, 4\" dia", 121, 36, 3.72, 19.05, 2.85);
//			items[6] = new FoodItem("Bread, White, Toasted, 1 Slice", 73, 17, 1, 13.6, 2.25);
//			items[7] = new FoodItem("Bread, Wheat, Toasted, 1 Slice", 76, 16, 1.02, 12.79, 4.07);
//			items[8] = new FoodItem("Milk, 2%, 1 Cup", 122, 68, 4.83, 11.71, 8.05);
//			items[9] = new FoodItem("Orange Juice, 1 Cup", 122, 7, .3, 28.73, 1.69);
//			for (int i = 0; i < items.length; i++) {
//				saveFoodItem(items[i]);
//				if (saveFoodItem(items[i]))
//					System.out.println("Saved item " + i + "(" + items[i].getName() + ") successfully.");
//				else
//					System.err.println("Could not save item " + i + "(" + items[i].getName() + ").");
//				NewFoodItemList.addItem(items[i].getName());
//				foods.add(items[i]);
//			}
//		}
		
		
		
		// one time run to populate ExerciseItem persistence
		{
			// name, burnrate
//			ExerciseItem items[] = new ExerciseItem[13];
//			items[0] = new ExerciseItem("Basketball (leisurely)", 130);
//			items[1] = new ExerciseItem("Bicycling (10 mph)", 125);
//			items[2] = new ExerciseItem("Bicycling (13 mph)", 200);
//			items[3] = new ExerciseItem("Ping pong", 90);
//			items[4] = new ExerciseItem("Running (8 mph)", 305);
//			items[5] = new ExerciseItem("Running (9 mph)", 330);
//			items[6] = new ExerciseItem("Running (10 mph)", 350);
//			items[7] = new ExerciseItem("Soccer", 195);
//			items[8] = new ExerciseItem("Swimming", 120);
//			items[9] = new ExerciseItem("Tennis", 160);
//			items[10] = new ExerciseItem("Walking (3 mph)", 80);
//			items[11] = new ExerciseItem("Walking (4 mph)", 100);
//			items[12] = new ExerciseItem("Weight training", 190);
//			for (int i = 0; i < items.length; i++) {
//				saveExerciseItem(items[i]);
//				saveExerciseItem(items[i]);
//				NewExerciseItem.addItem(items[i].getName());
//				exercises.add(items[i]);
//			}
		}
		
		
		newMeal = new Meal(email);
		newWorkout = new Workout(email);
		startAsynchronous();
	}
	
	private void startAsynchronous() {
		getFoodItems();
		getExerciseItems();
		getUserMeals();
		getUserWorkouts();
	}

	protected void addFood() {
		try {
	    final FoodItem foodItem = foods.get(NewFoodItemList.getSelectedIndex());
	    NewFoodItemList.setFocus(true);
	    newMeal.addFoodItem(foodItem);
	    TotalCalsTextBox.setText(Double.toString(newMeal.totalCalories()));
	    // add the stock to the list
	    int row = FoodsFlexTable.getRowCount();
	    FoodsFlexTable.setText(row, 0, foodItem.getName());
	    FoodsFlexTable.setText(row, 1, Integer.toString(foodItem.getCalories()));
	    FoodsFlexTable.setText(row, 2, Integer.toString(foodItem.getFatCalories()));
	    FoodsFlexTable.setText(row,	3, Double.toString(foodItem.getFatGrams()));
	    FoodsFlexTable.setText(row, 4, Double.toString(foodItem.getCarbohydrates()));

	    // add button to remove this stock from the list
	    Button removeFood = new Button("x");
	    removeFood.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {
	        int removedIndex = newMeal.indexOfFoodItem(foodItem);
	        newMeal.removeFoodItem(removedIndex);
	        FoodsFlexTable.removeRow(removedIndex+1);
		    TotalCalsTextBox.setText(Double.toString(newMeal.totalCalories()));
	    }
	    });
	    FoodsFlexTable.setWidget(row, 5, removeFood);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	protected void addExercise() {
		try {
		    final ExerciseItem exerciseItem = exercises.get(NewExerciseItemList.getSelectedIndex());
		    NewExerciseItemList.setFocus(true);
		    newWorkout.addExerciseItem(exerciseItem);
		    TotalCalsTextBox.setText(Double.toString(newWorkout.totalCalories()));
		    // add the stock to the list
		    int row = ExercisesFlexTable.getRowCount();
		    ExercisesFlexTable.setText(row, 0, exerciseItem.getName());
		    ExercisesFlexTable.setText(row, 1, Integer.toString(10));
		    ExercisesFlexTable.setText(row, 2, Double.toString(exerciseItem.getBurnRate()));

		    // add button to remove this stock from the list
		    Button removeExercise = new Button("x");
		    removeExercise.addClickHandler(new ClickHandler() {
		    public void onClick(ClickEvent event) {
		        int removedIndex = newWorkout.indexOfExerciseItem(exerciseItem);
		        newWorkout.removeExerciseItem(removedIndex);
		        ExercisesFlexTable.removeRow(removedIndex+1);
			    TotalCalsTextBox.setText(Double.toString(newWorkout.totalCalories()));
		    }
		    });
		    ExercisesFlexTable.setWidget(row, 5, removeExercise);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}	
	
	public void getUserMeals() {
		rpc.getUserMeals(new AsyncCallback<Bridge>() {
			public void onFailure(Throwable caught) {
				Window.alert("Failed to retrieve user meals (communication error)");
			}

			public void onSuccess(Bridge result) {
				email = result.email;
				if (result.meals != null)
					meals.addAll(result.meals);
				System.out.println("Retreived " + meals.size() + " meals.");
				String str = "Meals restored from datastore:\n";
				for (Meal meal : meals)
					str += meal.toString() + "\n";
				Window.alert(str);
			}
		});
	}
	
	public void getUserWorkouts() {
		rpc.getUserWorkouts(new AsyncCallback<Bridge>() {
			public void onFailure(Throwable caught) {
				Window.alert("Failed to retrieve user workouts (communication error)");
			}

			public void onSuccess(Bridge result) {
				email = result.email;
				if (result.workouts != null)
					workouts.addAll(result.workouts);
				System.out.println("Retreived " + workouts.size() + " workouts.");
				String str = "Workouts restored from datastore:\n";
				for (Workout workout : workouts)
					str += workout.toString() + "\n";
				Window.alert(str);
			}
		});
	}
	
	private void saveUserMeal() {
		Bridge msg = new Bridge();
		msg.meal = newMeal;
		rpc.saveUserMeal(msg, new AsyncCallback<Boolean>() {
			public void onFailure(Throwable caught) {
				Window.alert("Failed to save meal (communication error).");
				Window.Location.reload();
			}

			public void onSuccess(Boolean result) {
				if (result) {
					System.out.println("Saved new meal to datastore.");
					Window.alert("Your meal has been saved.");
				}
				else {
					System.err.println("Unable to save new meal.");
					Window.alert("Your meal was not saved.");
				}
				Window.Location.reload();
			}
		});
	}
	
	private void saveUserWorkout() {
		Bridge msg = new Bridge();
		msg.workout = newWorkout;
		rpc.saveUserWorkout(msg, new AsyncCallback<Boolean>() {
			public void onFailure(Throwable caught) {
				Window.alert("Your workout could not be saved (communication error).");
				Window.Location.reload();
			}

			public void onSuccess(Boolean result) {
				if (result) {
					System.out.println("Saved new workout to datastore.");
					Window.alert("Your workout has been saved.");
				}
				else {
					System.err.println("Unable to save new workout.");
					Window.alert("Your workout was not saved.");
				}
				Window.Location.reload();
			}
		});
	}
	
	private void getFoodItems() {
		rpc.getFoodItems(new AsyncCallback<Bridge>() {
			public void onFailure(Throwable caught) {
				Window.alert("Unable to retrive food items (communication error).");
			}

			public void onSuccess(Bridge result) {
				if (result.foods != null)
					foods.addAll(result.foods);
				System.out.println("Retreived " + foods.size() + " items.");
				for (FoodItem item : foods)
					NewFoodItemList.addItem(item.getName());
			}
		});
	}
	
	private void getExerciseItems() {
		rpc.getExerciseItems(new AsyncCallback<Bridge>() {
			public void onFailure(Throwable caught) {
				Window.alert("Failed to retrieve exercise items (communication error).");
			}

			public void onSuccess(Bridge result) {
				if (result.exercises != null)
					exercises.addAll(result.exercises);
				System.out.println("Retreived " + exercises.size() + " items.");
				for (ExerciseItem item : exercises)
					NewExerciseItemList.addItem(item.getName());
			}
		});
	}
	
//	private void saveFoodItem(FoodItem item) {
//		Bridge msg = new Bridge();
//		msg.foodItem = item;
//		rpc.saveFoodItem(msg, new AsyncCallback<Boolean>() {
//			public void onFailure(Throwable caught) { }
//			
//			public void onSuccess(Boolean result) { }
//		});
//	}	
	
//	private void saveExerciseItem(ExerciseItem item) {
//		Bridge msg = new Bridge();
//		msg.exercise = item;
//		rpc.saveExerciseItem(msg, new AsyncCallback<Boolean>() {
//			public void onFailure(Throwable caught) { }
//			
//			public void onSuccess(Boolean result) { }
//		});
//	}
	
}
