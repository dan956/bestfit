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
	private ArrayList <String> exercises = new ArrayList<String>();
	private FlexTable FoodsFlexTable;
	private FlexTable ExercisesFlexTable;
	
	private VerticalPanel MealVerticalPanel;
	private VerticalPanel WorkoutVerticalPanel;
	
	private FlexTable MetricsFlexTable;
	private Label lblNewLabel_1;
	private Label lblNewLabel_2;
	private Label lblNewLabel_3;
	private Label lblNewLabel_4;
	private Label lblNewLabel_5;
	
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
		
		MetricsFlexTable = new FlexTable();
		MealVerticalPanel.add(MetricsFlexTable);
		MetricsFlexTable.setWidth("451px");
		
		lblNewLabel_1 = new Label("Food Item");
		lblNewLabel_1.setWordWrap(false);
		MetricsFlexTable.setWidget(0, 0, lblNewLabel_1);
		
		lblNewLabel_2 = new Label("calories");
		MetricsFlexTable.setWidget(0, 1, lblNewLabel_2);
		
		lblNewLabel_3 = new Label("fat cals");
		MetricsFlexTable.setWidget(0, 2, lblNewLabel_3);
		
		lblNewLabel_4 = new Label("carbs");
		MetricsFlexTable.setWidget(0, 3, lblNewLabel_4);
		
		lblNewLabel_5 = new Label("protein");
		MetricsFlexTable.setWidget(0, 4, lblNewLabel_5);
		
		FoodsFlexTable = new FlexTable();
		MealVerticalPanel.add(FoodsFlexTable);
		FoodsFlexTable.setWidth("451px");
		
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
//				if (saveFoodItem(items[i]))
//					System.out.println("Saved item " + i + "(" + items[i].getName() + ") successfully.");
//				else
//					System.err.println("Could not save item " + i + "(" + items[i].getName() + ").");
//				NewFoodItem.addItem(items[i].getName());
//				foods.add(items[i]);
//			}
		}
		newMeal = new Meal(email);
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
	        int removedIndex = foods.indexOf(foodItem);
	        newMeal.removeFoodItem(removedIndex);
	        FoodsFlexTable.removeRow(removedIndex);
		    TotalCalsTextBox.setText(Integer.toString(newMeal.totalCalories()));
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
	
//	private void saveFoodItem(FoodItem item) {
//		Bridge msg = new Bridge();
//		msg.foodItem = item;
//		rpc.saveFoodItem(msg, new AsyncCallback<Boolean>() {
//			public void onFailure(Throwable caught) {}
//
//			public void onSuccess(Boolean result) {}
//		});
//	}
}
