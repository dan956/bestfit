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
import com.google.gwt.user.client.ui.IntegerBox;


public class Calculator implements EntryPoint {
	private final RpcServicesAsync rpc = GWT.create(RpcServices.class);
	private String email;
	private TextBox WorkoutNameTextBox;
	private FlexTable WorkoutFlexTable;
	private FlexTable MealFlexTable;
	private TabPanel tabPanel;
	private RootPanel rootPanel;
	private TextBox MealLabelTextBox;
	private ListBox NewFoodItem;
	private ListBox NewExerciseItem;
	private Button AddFoodPshbtnAdd;
	private TextBox TotalCalsTextBox;
	private TextBox TotalCalsBurnedTextBox;
	private Button pshbtnSave;
	private ArrayList<Meal> meals = new ArrayList<Meal>();
	private ArrayList<FoodItem> foods = new ArrayList<FoodItem>();
	private Meal newMeal;
	private ArrayList <Workout> workouts = new ArrayList<Workout>();
	private ArrayList <ExerciseItem> exercises = new ArrayList<ExerciseItem>();
	private Workout newWorkout;
	private FlexTable FoodsFlexTable;
	private FlexTable ExercisesFlexTable;
	
	private VerticalPanel MealVerticalPanel;
	private VerticalPanel WorkoutVerticalPanel;
	private Label lblNewLabel_1;
	private Label lblNewLabel_2;
	
	public void onModuleLoad() {
		rootPanel = RootPanel.get("calculatorCont");
		rootPanel.setSize("600px", "450px");
		
		tabPanel = new TabPanel();
		rootPanel.add(tabPanel);
		tabPanel.setSize("450px", "350px");
		
		tabPanel.addStyleName("table-center");
		
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
		AddFoodPshbtnAdd = new Button("Add");
		AddFoodPshbtnAdd.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addFood();
			}
		});
		
		NewFoodItem = new ListBox();
		NewFoodItem.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == KeyCodes.KEY_ENTER){
					addFood();
				}
			}
		});
		MealFlexTable.setWidget(0, 1, NewFoodItem);
		MealFlexTable.setWidget(0, 2, AddFoodPshbtnAdd);
		AddFoodPshbtnAdd.setWidth("50px");
		
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
		
		pshbtnSave = new Button("Save");
		pshbtnSave.setEnabled(true);
		pshbtnSave.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (MealLabelTextBox.getText().trim().equals(""))
					Window.alert("Please name this meal.");
				else {
					newMeal.setLabel(MealLabelTextBox.getText());
					newMeal.setDate(new Date());
					meals.add(newMeal);
					saveUserMeal();
				}
			}
		});
		MealFlexTable.setWidget(4, 2, pshbtnSave);
		pshbtnSave.setSize("60px", "25px");
		
		WorkoutVerticalPanel = new VerticalPanel();
		tabPanel.add(WorkoutVerticalPanel, " Workout ", false);
		WorkoutVerticalPanel.setSize("5cm", "3cm");
		
		WorkoutFlexTable = new FlexTable();
		WorkoutVerticalPanel.add(WorkoutFlexTable);
		WorkoutFlexTable.setWidth("451px");

		
		Label lblExercise = new Label("Exercise:");
		WorkoutFlexTable.setWidget(0, 0, lblExercise);
		lblExercise.setWidth("69px");
		
		ListBox ExerciseListBox = new ListBox();
		WorkoutFlexTable.setWidget(0, 1, ExerciseListBox);
		
		Button btnNewButton = new Button("New button");
		btnNewButton.setText("Add");
		WorkoutFlexTable.setWidget(0, 2, btnNewButton);
		btnNewButton.setSize("43px", "23px");
		
		lblNewLabel_1 = new Label("Time");
		lblNewLabel_1.setWordWrap(false);
		WorkoutFlexTable.setWidget(2, 0, lblNewLabel_1);
		lblNewLabel_1.setWidth("30px");
		
		IntegerBox timeIntegerBox = new IntegerBox();
		WorkoutFlexTable.setWidget(2, 1, timeIntegerBox);
		
		lblNewLabel_2 = new Label("Minute");
		WorkoutFlexTable.setWidget(2, 2, lblNewLabel_2);
		
		Label lblTotalCalories_1 = new Label("Total Calories:");
		lblTotalCalories_1.setWordWrap(false);
		WorkoutFlexTable.setWidget(4, 0, lblTotalCalories_1);
		lblTotalCalories_1.setWidth("100px");
		
		TotalCalsBurnedTextBox = new TextBox();
		TotalCalsBurnedTextBox.setReadOnly(true);
		TotalCalsBurnedTextBox.setValue(Integer.toString(0));
		WorkoutFlexTable.setWidget(4, 1, TotalCalsBurnedTextBox);
		
		Label lblWorkoutName = new Label("Workout Name:");
		lblWorkoutName.setWordWrap(false);
		WorkoutFlexTable.setWidget(5, 0, lblWorkoutName);
		lblWorkoutName.setWidth("94px");
		
		WorkoutNameTextBox = new TextBox();
		WorkoutFlexTable.setWidget(5, 1, WorkoutNameTextBox);
		
		Button btnNewButton_1 = new Button("New button");
		btnNewButton_1.setText("Save");
		WorkoutFlexTable.setWidget(5, 2, btnNewButton_1);
		btnNewButton_1.setSize("47px", "24px");
		
		ExercisesFlexTable = new FlexTable();
		WorkoutVerticalPanel.add(ExercisesFlexTable);
		ExercisesFlexTable.setWidth("451px");
		ExercisesFlexTable.setText(0, 0, "Exercise Items");
		ExercisesFlexTable.setText(0, 1, "Time Interval (mins)");
		ExercisesFlexTable.setText(0, 2, "Calorie Burned / Interval");
		ExercisesFlexTable.setStyleName("cw-FlexTable");
		WorkoutFlexTable.getFlexCellFormatter().setColSpan(1, 0, 2);
		WorkoutFlexTable.getFlexCellFormatter().setColSpan(1, 0, 5);
		
		newMeal = new Meal(email);
		newWorkout = new Workout(email);
		startAsynchronous();
	}
	
	private void startAsynchronous() {
		getFoodItems();
	}

	protected void addFood() {
		try {
	    final FoodItem foodItem = foods.get(NewFoodItem.getSelectedIndex());
	    NewFoodItem.setFocus(true);
	    newMeal.addFoodItem(foodItem);
	    TotalCalsTextBox.setText(Integer.toString(newMeal.totalCalories()));
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
		    TotalCalsTextBox.setText(Integer.toString(newMeal.totalCalories()));
	    }
	    });
	    FoodsFlexTable.setWidget(row, 5, removeFood);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	protected void addWorkout() {
		try {
		    final ExerciseItem exerciseItem = exercises.get(NewExerciseItem.getSelectedIndex());
		    NewExerciseItem.setFocus(true);
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
			public void onFailure(Throwable caught) {}

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
	
	private void saveUserMeal() {
		Bridge msg = new Bridge();
		msg.meal = newMeal;
		rpc.saveUserMeal(msg, new AsyncCallback<Boolean>() {
			public void onFailure(Throwable caught) {
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
	
	private void getFoodItems() {
		rpc.getFoodItems(new AsyncCallback<Bridge>() {
			public void onFailure(Throwable caught) {
				getUserMeals();
			}

			public void onSuccess(Bridge result) {
				if (result.foods != null)
					foods.addAll(result.foods);
				System.out.println("Retreived " + foods.size() + " items.");
				for (FoodItem item : foods)
					NewFoodItem.addItem(item.getName());
				getUserMeals();
			}
		});
	}
	

}
