package com.bestfit.client;

import java.util.ArrayList;
import java.util.Random;
import com.bestfit.data.FoodItem;
import com.bestfit.data.Meal;
import com.bestfit.shared.Bridge;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
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


public class CalculatorOld implements EntryPoint {
	private final RpcServicesAsync rpc = GWT.create(RpcServices.class);
	private String email;
	private TextBox WorkoutNameTextBox;
	private FlexTable WorkoutFlexTable;
	private FlexTable MealFlexTable;
	private TabPanel tabPanel;
	private RootPanel rootPanel;
	private TextBox MealNameTextBox;
	private ListBox NewFoodItem;
	private ListBox NewExerciseItem;
	private Button AddFoodPshbtnAdd;
	private TextBox TotalCalsTextBox;
	private TextBox TotalCalsBurnedTextBox;
	private Button pshbtnSave;
	private ArrayList<Meal> meals;
	private ArrayList<FoodItem> foods;
	private Meal newMeal;
	private ArrayList <String> exercises = new ArrayList<String>();
	private FlexTable FoodsFlexTable;
	private FlexTable ExercisesFlexTable;
	
	private VerticalPanel MealVerticalPanel;
	private VerticalPanel WorkoutVerticalPanel;
	
	private boolean success;
	
	public void onModuleLoad() {
		rootPanel = RootPanel.get("calculatorCont");
		
		tabPanel = new TabPanel();
		rootPanel.add(tabPanel, 26, 0);
		tabPanel.setSize("382px", "214px");
		
		tabPanel.addStyleName("table-center");
		
		MealVerticalPanel = new VerticalPanel();
		tabPanel.add(MealVerticalPanel, "| Meal |", false);
		tabPanel.selectTab(0);
		MealVerticalPanel.setSize("5cm", "3cm");
		
		tabPanel.addStyleName("table-center");
		
		MealFlexTable = new FlexTable();
		MealVerticalPanel.add(MealFlexTable);
		
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
		FoodsFlexTable.setWidth("382px");
		
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
		
		MealNameTextBox = new TextBox();
		MealFlexTable.setWidget(4, 1, MealNameTextBox);
		
		pshbtnSave = new Button("Save");
		pshbtnSave.setEnabled(true);
		pshbtnSave.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				saveUserMeal(newMeal);
				Window.alert("Your meal has been saved.");
				Window.Location.reload();
			}
		});
		MealFlexTable.setWidget(4, 2, pshbtnSave);
		pshbtnSave.setSize("60px", "25px");
		
		FoodsFlexTable = new FlexTable();
		MealVerticalPanel.add(FoodsFlexTable);
		FoodsFlexTable.setWidth("313px");
		
		WorkoutVerticalPanel = new VerticalPanel();
		tabPanel.add(WorkoutVerticalPanel, "| Workout |", false);
		WorkoutVerticalPanel.setSize("5cm", "3cm");
		
		WorkoutFlexTable = new FlexTable();
		WorkoutVerticalPanel.add(WorkoutFlexTable);
		
		Label lblExercise = new Label("  Exercise:");
		WorkoutFlexTable.setWidget(0, 0, lblExercise);
		lblExercise.setWidth("69px");
		
		ListBox ExerciseListBox = new ListBox();
		WorkoutFlexTable.setWidget(0, 1, ExerciseListBox);
		
		Button btnNewButton = new Button("New button");
		btnNewButton.setText("Add");
		WorkoutFlexTable.setWidget(0, 2, btnNewButton);
		btnNewButton.setSize("43px", "23px");
		
		Label lblTotalCalories_1 = new Label("  Total Calories:");
		WorkoutFlexTable.setWidget(4, 0, lblTotalCalories_1);
		lblTotalCalories_1.setWidth("85px");
		
		TotalCalsBurnedTextBox = new TextBox();
		TotalCalsBurnedTextBox.setReadOnly(true);
		TotalCalsBurnedTextBox.setValue(Integer.toString(0));
		WorkoutFlexTable.setWidget(4, 1, TotalCalsBurnedTextBox);
		
		Label lblWorkoutName = new Label("Workout Name:");
		WorkoutFlexTable.setWidget(5, 0, lblWorkoutName);
		lblWorkoutName.setWidth("94px");
		
		WorkoutNameTextBox = new TextBox();
		WorkoutFlexTable.setWidget(5, 1, WorkoutNameTextBox);
		
		Button btnNewButton_1 = new Button("New button");
		btnNewButton_1.setText("Save");
		WorkoutFlexTable.setWidget(5, 2, btnNewButton_1);
		btnNewButton_1.setSize("47px", "24px");
		WorkoutFlexTable.getFlexCellFormatter().setColSpan(1, 0, 2);
		WorkoutFlexTable.getFlexCellFormatter().setColSpan(1, 0, 5);
		
		// one time run to populate FoodItem persistence
		{
			// name, cals, fatcals, fatgrams, carbs, protein
			FoodItem items[] = new FoodItem[10];
			items[0] = new FoodItem("Egg, Whole, Cooked, Medium", 71, 40, 5.15, .48, 5.28);
			items[1] = new FoodItem("Egg, Whole, Cooked, Large", 81, 45, 5.86, .55, 6);
			items[2] = new FoodItem("Egg, Whole, Cooked, Extra Large", 94, 51, 6.79, .64, 6.96);
			items[3] = new FoodItem("Bacon, Medium Slice", 43, 40, 3.34, .11, 2.96);
			items[4] = new FoodItem("Pancake, Buttermilk, 5-1/2\" dia", 110, 28, 2.53, 19.25, 2.61);
			items[5] = new FoodItem("Waffle, Round, 4\" dia", 121, 36, 3.72, 19.05, 2.85);
			items[6] = new FoodItem("Bread, White, Toasted, 1 Slice", 73, 17, 1, 13.6, 2.25);
			items[7] = new FoodItem("Bread, Wheat, Toasted, 1 Slice", 76, 16, 1.02, 12.79, 4.07);
			items[8] = new FoodItem("Milk, 2%, 1 Cup", 122, 68, 4.83, 11.71, 8.05);
			items[9] = new FoodItem("Orange Juice, 1 Cup", 122, 7, .3, 28.73, 1.69);
			foods = new ArrayList<FoodItem>();
			for (int i = 0; i < items.length; i++) {
				saveFoodItem(items[i]);
				NewFoodItem.addItem(items[i].getName());
				foods.add(items[i]);
			}
		}
//		getFoodItems();
//		foods = new ArrayList<FoodItem>();
//		for (FoodItem item : foods)
//			NewFoodItem.addItem(item.getName());
		getUserMeals();
		newMeal = new Meal(email);
		newMeal.setEmail(email);
	}

	protected void addFood() {
		try {
	    final FoodItem foodItem = foods.get(NewFoodItem.getSelectedIndex());
	    NewFoodItem.setFocus(true);
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
	        int removedIndex = foods.indexOf(foodItem);
	        newMeal.removeFoodItem(removedIndex);
	        int calCount = Integer.parseInt(TotalCalsTextBox.getText());
	        int cals = Integer.parseInt(FoodsFlexTable.getText(removedIndex, 1));
	        FoodsFlexTable.removeRow(removedIndex);
		    TotalCalsTextBox.setText(Double.toString(newMeal.totalCalories()));
	    }
	    });
	    FoodsFlexTable.setWidget(row, 5, removeFood);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	protected void addWorkout() {
	    final String ExerciseItem = NewExerciseItem.getItemText(NewExerciseItem.getSelectedIndex());
	    NewExerciseItem.setFocus(true);
	     
	    NewExerciseItem.setTitle("");
	    int totalCals = Integer.parseInt(TotalCalsBurnedTextBox.getText());
	    int cals = new Random().nextInt(200);
	    String ExerciseCals = Integer.toString(cals);
	    TotalCalsBurnedTextBox.setText(Integer.toString(totalCals + cals));
	    
	    // add the stock to the list
	    int row = ExercisesFlexTable.getRowCount();
	    exercises.add(ExerciseItem);
	    ExercisesFlexTable.setText(row, 0, ExerciseItem);
	    ExercisesFlexTable.setText(row, 1, ExerciseCals);
	    

	    // add button to remove this stock from the list
	    Button removeExercise = new Button("x");
	    removeExercise.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {                    
	        int removedIndex = exercises.lastIndexOf(ExerciseItem);
	        exercises.remove(removedIndex);
		    int totalCals = Integer.parseInt(TotalCalsTextBox.getText());
		    int cals = Integer.parseInt(ExercisesFlexTable.getText(removedIndex, 1));
	        ExercisesFlexTable.removeRow(removedIndex);
		    TotalCalsBurnedTextBox.setText(Integer.toString(totalCals - cals));
	    }
	    });
	    ExercisesFlexTable.setWidget(row, 2, removeExercise); 
	}	
	
	public void getUserMeals() {
		rpc.getUserMeals(new AsyncCallback<Bridge>() {
			public void onFailure(Throwable caught) {
				meals = new ArrayList<Meal>();
			}

			public void onSuccess(Bridge result) {
				email = result.email;
				if (result.meals != null)
					meals = result.meals;
				else
					meals = new ArrayList<Meal>();
			}
		});
	}
	
	private synchronized boolean saveUserMeal(Meal meal) {
		success = true;
		Bridge msg = new Bridge();
		msg.meal = meal;
		rpc.saveUserMeal(msg, new AsyncCallback<Boolean>() {
			public void onFailure(Throwable caught) {
				success = false;
			}

			@Override
			public void onSuccess(Boolean result) {
				success = result;
			}
		
		});
		if (success)
			meals.add(meal);
		return success;
	}
	
	private void getFoodItems() {
		rpc.getFoodItems(new AsyncCallback<Bridge>() {
			public void onFailure(Throwable caught) {
				foods = new ArrayList<FoodItem>();
			}

			public void onSuccess(Bridge result) {
				if (result.foods != null)
					foods = result.foods;
				else
					foods = new ArrayList<FoodItem>();
			}
		});
	}
	
	private synchronized boolean saveFoodItem(FoodItem item) {
		success = true;
		Bridge msg = new Bridge();
		msg.foodItem = item;
		rpc.saveFoodItem(msg, new AsyncCallback<Boolean>() {
			public void onFailure(Throwable caught) {
				success = false;
			}

			@Override
			public void onSuccess(Boolean result) {
				success = result;
			}
		
		});
		return success;
	}
}
