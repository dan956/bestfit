package com.bestfit.client;

import java.util.ArrayList;
import java.util.Random;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;

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
	private Button pshbtnSave;
	private ArrayList <String> foods = new ArrayList<String>();
	private ArrayList <String> exercises = new ArrayList<String>();
	private FlexTable FoodsFlexTable;
	private FlexTable ExercisesFlexTable;
	
	private int calCount = 0;
	private int calsBurnedCount = 0;

	
	public void onModuleLoad() {
		rootPanel = RootPanel.get("profCom");
		
		tabPanel = new TabPanel();
		rootPanel.add(tabPanel, 0, 0);
		tabPanel.setSize("", "");
		
		MealFlexTable = new FlexTable();
		tabPanel.add(MealFlexTable, "Meal", false);
		MealFlexTable.setSize("5cm", "3cm");
		
		Label lblFoodItem = new Label("Food Item:");
		MealFlexTable.setWidget(0, 0, lblFoodItem);
		lblFoodItem.setWidth("84px");
		
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
		MealFlexTable.setWidget(1, 0, NewFoodItem);
		
		AddFoodPshbtnAdd = new Button("Add");
		AddFoodPshbtnAdd.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addFood();
			}
		});
		MealFlexTable.setWidget(1, 1, AddFoodPshbtnAdd);
		
		FoodsFlexTable = new FlexTable();
		MealFlexTable.setWidget(2, 0, FoodsFlexTable);
		FoodsFlexTable.setWidth("188px");
		
		Label lblTotalCalories = new Label("Total Calories:");
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
		MealFlexTable.getFlexCellFormatter().setColSpan(2, 0, 5);
		MealFlexTable.getFlexCellFormatter().setColSpan(2, 0, 2);
		MealFlexTable.getFlexCellFormatter().setColSpan(2, 0, 5);
		
		WorkoutFlexTable = new FlexTable();
		tabPanel.add(WorkoutFlexTable, "Workout", false);
		WorkoutFlexTable.setSize("5cm", "3cm");
		
		Label lblExercise = new Label("Exercise:");
		WorkoutFlexTable.setWidget(0, 0, lblExercise);
		lblExercise.setWidth("84px");
		
		ListBox ExerciseListBox = new ListBox();
		WorkoutFlexTable.setWidget(1, 0, ExerciseListBox);
		
		PushButton pushButton = new PushButton("Add");
		WorkoutFlexTable.setWidget(1, 1, pushButton);
		
		Label label_2 = new Label("Total Calories:");
		WorkoutFlexTable.setWidget(4, 0, label_2);
		label_2.setWidth("103px");
		
		TextBox TotalCaloriesTextBox = new TextBox();
		TotalCaloriesTextBox.setReadOnly(true);
		TotalCaloriesTextBox.setValue(Integer.toString(calCount));
		WorkoutFlexTable.setWidget(4, 1, TotalCaloriesTextBox);
		
		PushButton pushButton_1 = new PushButton("Save");
		WorkoutFlexTable.setWidget(4, 4, pushButton_1);
		
		Label lblWorkoutName = new Label("Workout Name:");
		WorkoutFlexTable.setWidget(5, 0, lblWorkoutName);
		
		WorkoutNameTextBox = new TextBox();
		WorkoutFlexTable.setWidget(5, 1, WorkoutNameTextBox);
		WorkoutFlexTable.getFlexCellFormatter().setColSpan(1, 0, 2);
		WorkoutFlexTable.getFlexCellFormatter().setColSpan(1, 0, 5);

	}

	protected void addFood() {
	    final String FoodItem = NewFoodItem.getItemText(NewFoodItem.getSelectedIndex());
	    NewFoodItem.setFocus(true);
	     
	    NewFoodItem.setTitle("");
	    int cals = new Random().nextInt(200);
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
	        FoodsFlexTable.removeRow(removedIndex);
		    TotalCalsTextBox.setText(Integer.toString(calCount));
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
	    calsBurnedCount += cals;
	    TotalCalsTextBox.setText(Integer.toString(calsBurnedCount));
	    
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
	        ExercisesFlexTable.removeRow(removedIndex);
		    TotalCalsTextBox.setText(Integer.toString(calsBurnedCount));
	    }
	    });
	    ExercisesFlexTable.setWidget(row, 2, removeExercise); 
	}	
}
