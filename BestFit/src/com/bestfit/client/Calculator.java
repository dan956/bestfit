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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
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
	private FlexTable MealsListFlexTable;
	private FlexTable WorkoutsListFlexTable;
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
	private TextBox DateOfMealTextBox;
	private TextBox DateOfWorkoutTextBox;
	private ListBox PreviousMeals;
	private ListBox PreviousWorkouts;
	
	private ArrayList<Meal> meals = new ArrayList<Meal>();
	private ArrayList <Workout> workouts = new ArrayList<Workout>();
	private ArrayList<FoodItem> foods = new ArrayList<FoodItem>();
	private ArrayList <ExerciseItem> exercises = new ArrayList<ExerciseItem>();
	private Meal newMeal;
	private Workout newWorkout;
	
	
	private void setPageHeader() {
		RootPanel headPanel = RootPanel.get("calculatorHeader");
		headPanel.add(new HTML("<h3><b><font color=\"#308A4D\">Enter your meals and workouts!</font></b></h3>"));
	}

	public void onModuleLoad() {
		setPageHeader();
		rootPanel = RootPanel.get("calculatorCont");
		rootPanel.setSize("600px", "450px");
		
		tabPanel = new TabPanel();
		rootPanel.add(tabPanel);
		tabPanel.setSize("500px", "350px");
		tabPanel.setAnimationEnabled(true);
		
		tabPanel.addStyleName("table-center");
		
		/* Meal / Food */
		
		MealVerticalPanel = new VerticalPanel();
		tabPanel.add(MealVerticalPanel, " Meal ", false);
		tabPanel.selectTab(0);
		MealVerticalPanel.setSize("6cm", "4cm");
		
		tabPanel.addStyleName("table-center");
		
		MealFlexTable = new FlexTable();
		MealVerticalPanel.add(MealFlexTable);
		MealFlexTable.setWidth("500px");
		
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
		MealVerticalPanel.add(new HTML("<br/><br/>"));
		MealVerticalPanel.add(FoodsFlexTable);
		FoodsFlexTable.setWidth("500px");
	    FoodsFlexTable.setText(0, 0, "Food Items");
	    FoodsFlexTable.setText(0, 1, "Qty");
	    FoodsFlexTable.setText(0, 2, "Cals");
	    FoodsFlexTable.setText(0, 3, "Fat Cals");
	    FoodsFlexTable.setText(0, 4, "Fat");
	    FoodsFlexTable.setText(0, 5, "Carbs");
	    FoodsFlexTable.setText(0, 6, "Protein");
		//FoodsFlexTable.setStyleName("cw-FlexTable");
		
		FoodsFlexTable.getCellFormatter().addStyleName(0, 0, "calculatorlistheader");
		FoodsFlexTable.getCellFormatter().addStyleName(0, 1, "calculatorlistheader");
		FoodsFlexTable.getCellFormatter().addStyleName(0, 2, "calculatorlistheader");
		FoodsFlexTable.getCellFormatter().addStyleName(0, 3, "calculatorlistheader");
		FoodsFlexTable.getCellFormatter().addStyleName(0, 4, "calculatorlistheader");
		FoodsFlexTable.getCellFormatter().addStyleName(0, 5, "calculatorlistheader");
		FoodsFlexTable.getCellFormatter().addStyleName(0, 6, "calculatorlistheader");
		FoodsFlexTable.setVisible(false);
		
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
		
		/* Previously stored Meals */
		
		MealVerticalPanel.add(new HTML("</br></br>"));
		FlexTable PreviousMealsFlexTable = new FlexTable();
		MealVerticalPanel.add(PreviousMealsFlexTable);
		PreviousMealsFlexTable.setText(0, 0, "Previous Meals:");
		PreviousMeals = new ListBox();
		
		PreviousMealsFlexTable.setWidget(0, 1, PreviousMeals);
		Button PreviousMealsPshBtn = new Button("Copy Meal");
		PreviousMealsFlexTable.setWidget(0, 2, PreviousMealsPshBtn);
		PreviousMeals.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				Meal meal = meals.get(PreviousMeals.getSelectedIndex());
				FlexTable flexTable = new FlexTable();
				flexTable.setWidth("300px");
				MealsListFlexTable.setWidget(0, 0, flexTable);
//				flexTable.setText(0, 0, meal.getLabel());
//				flexTable.setText(0, 1, "" + meal.totalCalories());
//				flexTable.setText(0, 2, "" + meal.totalFatCalories());
//				flexTable.setText(0, 3, "" + meal.totalFatGrams());
//				flexTable.setText(0, 4, "" + meal.totalCarbohydrates());
//				flexTable.setText(0, 5, "" + meal.totalProtein());
				flexTable.setText(0, 0, "Items");
				flexTable.setText(0, 1, "Qty");
				flexTable.getCellFormatter().addStyleName(0, 0, "calculatorlistheader");
				flexTable.getCellFormatter().addStyleName(0, 1, "calculatorlistheader");
				for (int i = 1; i < meal.getFoodItems().size(); i++) {
					flexTable.setText(i, 0, "-" + meal.getFoodItems().get(i).getName());
					flexTable.setText(i, 1, "x" + meal.getQuantity(i));
				}
			}
		});
		PreviousMealsPshBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (PreviousMeals.getValue(PreviousMeals.getSelectedIndex()) == null || PreviousMeals.getValue(PreviousMeals.getSelectedIndex()).trim().equals(""))
					return;
				FoodsFlexTable.setVisible(true);
				Meal meal = meals.get(PreviousMeals.getSelectedIndex());
				newMeal = new Meal();
				for (FoodItem foodItem : meal.getFoodItems()) {
					for (int i = 0; i < meal.getQuantity(foodItem); i++)
						newMeal.addFoodItem(foodItem);
			    	int row = newMeal.indexOfFoodItem(foodItem) + 1;
			    	int qty = newMeal.getQuantity(foodItem);
			    	final FoodItem foodItem2 = foodItem; 
			    	FoodsFlexTable.setText(row, 0, foodItem.getName());
				    FoodsFlexTable.setText(row, 1, "x" + qty);
				    FoodsFlexTable.setText(row, 2, Double.toString((int)foodItem.getCalories() * qty));
				    FoodsFlexTable.setText(row, 3, Double.toString((int)foodItem.getFatCalories() * qty));
				    FoodsFlexTable.setText(row,	4, Double.toString((int)foodItem.getFatGrams() * qty));
				    FoodsFlexTable.setText(row, 5, Double.toString((int)foodItem.getCarbohydrates() * qty));
				    FoodsFlexTable.setText(row, 6, Double.toString((int)foodItem.getProtein() * qty));
				    TotalCalsTextBox.setText(Double.toString((int)newMeal.totalCalories()));
				    Button removeFood = new Button("x");
				    removeFood.addClickHandler(new ClickHandler() {
				    public void onClick(ClickEvent event) {
				        int removedIndex = newMeal.indexOfFoodItem(foodItem2);
				        newMeal.removeFoodItem(removedIndex);
				        FoodsFlexTable.removeRow(removedIndex+1);
					    TotalCalsTextBox.setText(Double.toString((int)newMeal.totalCalories()));
				    }
				    });
				    FoodsFlexTable.setWidget(row, 7, removeFood);
				}
			}
		});
		
		
		MealsListFlexTable = new FlexTable();
		MealVerticalPanel.add(MealsListFlexTable);
		MealsListFlexTable.setWidth("500px");
		//MealsListFlexTable.setStyleName("cw-FlexTable");
		
		/* Workout / Exercise */
		
		WorkoutVerticalPanel = new VerticalPanel();
		tabPanel.add(WorkoutVerticalPanel, " Workout ", false);
		tabPanel.selectTab(0);
		WorkoutVerticalPanel.setSize("6cm", "4cm");
		
		tabPanel.addStyleName("table-center");
		
		WorkoutFlexTable = new FlexTable();
		WorkoutVerticalPanel.add(WorkoutFlexTable);
		WorkoutFlexTable.setWidth("500px");
		
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
		WorkoutVerticalPanel.add(new HTML("<br/><br/>"));
		WorkoutVerticalPanel.add(ExercisesFlexTable);
		ExercisesFlexTable.setWidth("500px");
	    ExercisesFlexTable.setText(0, 0, "Exercise Items");
	    ExercisesFlexTable.setText(0, 1, "Time (min)");
	    ExercisesFlexTable.setText(0, 2, "Burn Rate (/ 30 mins)");
		ExercisesFlexTable.setStyleName("cw-FlexTable");
		
		ExercisesFlexTable.getCellFormatter().addStyleName(0, 0, "calculatorlistheader");
		ExercisesFlexTable.getCellFormatter().addStyleName(0, 1, "calculatorlistheader");
		ExercisesFlexTable.getCellFormatter().addStyleName(0, 2, "calculatorlistheader");
		ExercisesFlexTable.setVisible(false);
		
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

		/* Previously stored Workouts */
		
		WorkoutVerticalPanel.add(new HTML("</br></br>Previous Workouts:"));
		
		WorkoutsListFlexTable = new FlexTable();
		WorkoutVerticalPanel.add(WorkoutsListFlexTable);
		WorkoutsListFlexTable.setWidth("500px");
		WorkoutsListFlexTable.setStyleName("cw-FlexTable");
		
		/* */
		
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
			FoodsFlexTable.setVisible(true);
		    final FoodItem foodItem = foods.get(NewFoodItemList.getSelectedIndex());
		    NewFoodItemList.setFocus(true);
		    int row;
		    if (newMeal.addFoodItem(foodItem)) {
		    	row =  FoodsFlexTable.getRowCount();
			    FoodsFlexTable.setText(row, 0, foodItem.getName());
			    FoodsFlexTable.setText(row, 1, "x1");
			    FoodsFlexTable.setText(row, 2, Double.toString((int)foodItem.getCalories()));
			    FoodsFlexTable.setText(row, 3, Double.toString((int)foodItem.getFatCalories()));
			    FoodsFlexTable.setText(row,	4, Double.toString((int)foodItem.getFatGrams()));
			    FoodsFlexTable.setText(row, 5, Double.toString((int)foodItem.getCarbohydrates()));
			    FoodsFlexTable.setText(row, 6, Double.toString((int)foodItem.getProtein()));
		    }
		    else {
		    	row = newMeal.indexOfFoodItem(foodItem) + 1;
		    	int qty = newMeal.getQuantity(foodItem);
			    FoodsFlexTable.setText(row, 1, "x" + qty);
			    FoodsFlexTable.setText(row, 2, Double.toString((int)foodItem.getCalories() * qty));
			    FoodsFlexTable.setText(row, 3, Double.toString((int)foodItem.getFatCalories() * qty));
			    FoodsFlexTable.setText(row,	4, Double.toString((int)foodItem.getFatGrams() * qty));
			    FoodsFlexTable.setText(row, 5, Double.toString((int)foodItem.getCarbohydrates() * qty));
			    FoodsFlexTable.setText(row, 6, Double.toString((int)foodItem.getProtein() * qty));
		    }
		    TotalCalsTextBox.setText(Double.toString((int)newMeal.totalCalories()));
		    Button removeFood = new Button("x");
		    removeFood.addClickHandler(new ClickHandler() {
		    public void onClick(ClickEvent event) {
		        int removedIndex = newMeal.indexOfFoodItem(foodItem);
		        newMeal.removeFoodItem(removedIndex);
		        FoodsFlexTable.removeRow(removedIndex+1);
			    TotalCalsTextBox.setText(Double.toString((int)newMeal.totalCalories()));
		    }
		    });
		    FoodsFlexTable.setWidget(row, 7, removeFood);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	protected void addExercise() {
		try {
			ExercisesFlexTable.setVisible(true);
		    final ExerciseItem exerciseItem = exercises.get(NewExerciseItemList.getSelectedIndex());
		    NewExerciseItemList.setFocus(true);
		    int row, qty;
		    if (newWorkout.addExerciseItem(exerciseItem)) {
		    	row =  ExercisesFlexTable.getRowCount();
		    	qty = 5;
			    ExercisesFlexTable.setText(row, 0, exerciseItem.getName());
		    }
		    else {
		    	row = newWorkout.indexOfExerciseItem(exerciseItem) + 1;
		    	qty = newWorkout.getDuration(exerciseItem);
		    }
		    ExercisesFlexTable.setText(row, 1, qty + " mins");
		    ExercisesFlexTable.setText(row, 2, Double.toString((int)(exerciseItem.getBurnRate30() * qty) / 30));
		    TotalCalsBurnedTextBox.setText(Double.toString((int)(int)newWorkout.totalCaloriesBurned()));
		    Button removeExercise = new Button("x");
		    removeExercise.addClickHandler(new ClickHandler() {
		    public void onClick(ClickEvent event) {
		        int removedIndex = newWorkout.indexOfExerciseItem(exerciseItem);
		        newWorkout.removeExerciseItem(removedIndex);
		        ExercisesFlexTable.removeRow(removedIndex+1);
			    TotalCalsBurnedTextBox.setText(Double.toString((int)newWorkout.totalCaloriesBurned()));
		    }
		    });
		    ExercisesFlexTable.setWidget(row, 3, removeExercise);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public void getUserMeals() {
		Bridge msg = new Bridge();
		msg.startDate = new Date(0);
		rpc.getUserMeals(msg, new AsyncCallback<Bridge>() {
			public void onFailure(Throwable caught) {
				MealsListFlexTable.setText(0, 0, "There are no meals to display.");
//				Window.alert("Failed to retrieve user meals (communication error)");
			}

			public void onSuccess(Bridge result) {
				email = result.email;
				if (result.meals != null)
					meals.addAll(result.meals);
				System.out.println("Retreived " + meals.size() + " meals.");
				for (Meal meal : meals)
					PreviousMeals.addItem(meal.getLabel());
				Meal meal = meals.get(PreviousMeals.getSelectedIndex());
				FlexTable flexTable = new FlexTable();
				flexTable.setWidth("300px");
				MealsListFlexTable.setWidget(0, 0, flexTable);
//				flexTable.setText(0, 0, meal.getLabel());
//				flexTable.setText(0, 1, "" + meal.totalCalories());
//				flexTable.setText(0, 2, "" + meal.totalFatCalories());
//				flexTable.setText(0, 3, "" + meal.totalFatGrams());
//				flexTable.setText(0, 4, "" + meal.totalCarbohydrates());
//				flexTable.setText(0, 5, "" + meal.totalProtein());
				flexTable.setText(0, 0, "Items");
				flexTable.setText(0, 1, "Qty");
				flexTable.getCellFormatter().addStyleName(0, 0, "calculatorlistheader");
				flexTable.getCellFormatter().addStyleName(0, 1, "calculatorlistheader");
				for (int i = 1; i < meal.getFoodItems().size(); i++) {
					flexTable.setText(i, 0, "-" + meal.getFoodItems().get(i).getName());
					flexTable.setText(i, 1, "x" + meal.getQuantity(i));
				}
			}
		});
	}
	
	public void getUserWorkouts() {
		Bridge msg = new Bridge();
		msg.startDate = new Date(0);
		rpc.getUserWorkouts(msg, new AsyncCallback<Bridge>() {
			public void onFailure(Throwable caught) {
				WorkoutsListFlexTable.setText(0, 0, "There are no workouts to display.");
//				Window.alert("Failed to retrieve user workouts (communication error)");
			}

			public void onSuccess(Bridge result) {
				email = result.email;
				if (result.workouts != null)
					workouts.addAll(result.workouts);
				System.out.println("Retreived " + workouts.size() + " workouts.");
//				String str = "Workouts restored from datastore:\n";
//				for (Workout workout : workouts)
//					str += workout.toString() + "\n";
//				Window.alert(str);
				for (Workout workout : workouts) {
					FlexTable flexTable = new FlexTable();
					WorkoutsListFlexTable.setWidget(workouts.indexOf(workout), 0, flexTable);
					flexTable.setText(0, 0, workout.getLabel() + "[" + workout.getDateOfWorkout() + "]:");
					flexTable.setText(0, 1, "(" + workout.totalCaloriesBurned() + ")");
					for (int i = 0; i < workout.getExerciseItems().size(); i++) {
						flexTable.setText(i + 1, 0, "-" + workout.getExerciseItems().get(i).getName());
						flexTable.setText(i + 1, 1, workout.getDuration(i) + " mins");
					}
				}
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
				Window.alert("Failed to retrive food items (communication error).");
			}

			public void onSuccess(Bridge result) {
				if (result.foods != null)
					foods.addAll(result.foods);
				System.out.println("Retreived " + foods.size() + " foods.");
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
				System.out.println("Retreived " + exercises.size() + " exercises.");
				for (ExerciseItem item : exercises)
					NewExerciseItemList.addItem(item.getName());
			}
		});
	}
	
	
}
