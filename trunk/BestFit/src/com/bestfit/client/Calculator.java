package com.bestfit.client;

import java.util.ArrayList;
import java.util.Random;

import com.google.gwt.core.client.EntryPoint;
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


public class Calculator implements EntryPoint {
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
	private ArrayList <String> foods = new ArrayList<String>();
	private ArrayList <String> exercises = new ArrayList<String>();
	private FlexTable FoodsFlexTable;
	private FlexTable ExercisesFlexTable;
	private VerticalPanel verticalPanel;
	private VerticalPanel verticalPanel_1;
	
	
	public void onModuleLoad() {
		rootPanel = RootPanel.get("calculatorCont");
		
		
		
		
		tabPanel = new TabPanel();
		rootPanel.add(tabPanel, 26, 0);
		tabPanel.setSize("382px", "214px");
		
		tabPanel.selectTab(0);
		tabPanel.addStyleName("table-center");
		
		verticalPanel = new VerticalPanel();
		tabPanel.add(verticalPanel, "New tab", false);
		verticalPanel.setSize("5cm", "3cm");
		
		MealFlexTable = new FlexTable();
		verticalPanel.add(MealFlexTable);
		
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
		NewFoodItem.addItem("Egg");
		NewFoodItem.addItem("Bacon");
		NewFoodItem.addItem("Pancake");
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
		
		Label lblTotalCalories = new Label("Total Calories:");
		lblTotalCalories.setStylePrimaryName("gwt-TotalCa");
		MealFlexTable.setWidget(3, 0, lblTotalCalories);
		lblTotalCalories.setWidth("103px");
		
		TotalCalsTextBox = new TextBox();
		TotalCalsTextBox.setReadOnly(true);
		MealFlexTable.setWidget(3, 1, TotalCalsTextBox);
		
		Label lblNewLabel = new Label("Meal Name:");
		MealFlexTable.setWidget(4, 0, lblNewLabel);
		
		MealNameTextBox = new TextBox();
		MealFlexTable.setWidget(4, 1, MealNameTextBox);
		
		pshbtnSave = new Button("Save");
		pshbtnSave.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// send array and meal name to server
				DialogBox dialog = new DialogBox();
				dialog.setText("hello world,\nyou are adding meal " + MealNameTextBox.getText());
				dialog.setModal(false);
				dialog.show();
			}
		});
		MealFlexTable.setWidget(4, 2, pshbtnSave);
		pshbtnSave.setSize("60px", "25px");
		
		FoodsFlexTable = new FlexTable();
		verticalPanel.add(FoodsFlexTable);
		FoodsFlexTable.setWidth("313px");
		
		verticalPanel_1 = new VerticalPanel();
		tabPanel.add(verticalPanel_1, "New tab", false);
		verticalPanel_1.setSize("5cm", "3cm");
		
		WorkoutFlexTable = new FlexTable();
		verticalPanel_1.add(WorkoutFlexTable);
		
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
		MealFlexTable.getFlexCellFormatter().setColSpan(2, 0, 5);
		MealFlexTable.getFlexCellFormatter().setColSpan(2, 0, 2);
		MealFlexTable.getFlexCellFormatter().setColSpan(2, 0, 5);

	}

	protected void addFood() {
	    final String FoodItem = NewFoodItem.getItemText(NewFoodItem.getSelectedIndex());
	    NewFoodItem.setFocus(true);
	     
	    NewFoodItem.setTitle("");
	    int cals = new Random().nextInt(200);
	    int calCount = Integer.parseInt(TotalCalsTextBox.getText());
	    String FoodCals = Integer.toString(cals);
	    calCount += cals;
	    TotalCalsTextBox.setText(Integer.toString(calCount));
	    // add the stock to the list
	    int row = FoodsFlexTable.getRowCount();
	    foods.add(FoodItem);
	    FoodsFlexTable.setText(row, 0, FoodItem);
	    FoodsFlexTable.setText(row, 1, FoodCals);

	    // add button to remove this stock from the list
	    Button removeFood = new Button("x");
	    removeFood.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {                    
	        int removedIndex = foods.lastIndexOf(FoodItem);
	        foods.remove(removedIndex);
	        int calCount = Integer.parseInt(TotalCalsTextBox.getText());
	        int cals = Integer.parseInt(FoodsFlexTable.getText(removedIndex, 1));
	        FoodsFlexTable.removeRow(removedIndex);
		    TotalCalsTextBox.setText(Integer.toString(calCount - cals));
	    }
	    });
	    FoodsFlexTable.setWidget(row, 2, removeFood); 
	}	

	protected void addWorkout() {
	    final String ExerciseItem = NewExerciseItem.getItemText(NewExerciseItem.getSelectedIndex());
	    NewExerciseItem.setFocus(true);
	     
	    NewExerciseItem.setTitle("");
	    int cals = new Random().nextInt(200);
	    String ExerciseCals = Integer.toString(cals);
	    int calsBurned = Integer.parseInt(TotalCalsBurnedTextBox.getText());
	    calsBurned += cals;
	    TotalCalsBurnedTextBox.setText(Integer.toString(calsBurned));
	    
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
	        int calsBurned = Integer.parseInt(TotalCalsBurnedTextBox.getText());
	        int cals = Integer.parseInt(ExercisesFlexTable.getText(removedIndex, 1));
	        ExercisesFlexTable.removeRow(removedIndex);
		    TotalCalsBurnedTextBox.setText(Integer.toString(calsBurned - cals));
	    }
	    });
	    ExercisesFlexTable.setWidget(row, 2, removeExercise); 
	}	
}
