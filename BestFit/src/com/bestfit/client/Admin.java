package com.bestfit.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

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


public class Admin implements EntryPoint {

	private RootPanel rootPanel;
	private final RpcServicesAsync rpc = GWT.create(RpcServices.class);
	private Button MealButton;
	private Button WorkoutButton;
	@Override
	public void onModuleLoad() {
		rootPanel = RootPanel.get("adminpanel");
		
		FlexTable flexTable = new FlexTable();
		rootPanel.add(flexTable);
		
		MealButton = new Button("New button");
		MealButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				 addFoodItems();
			}
		});
		MealButton.setText("Add Food Items");
		flexTable.setWidget(0, 0, MealButton);
		MealButton.setHeight("48px");
		
		WorkoutButton = new Button("New button");
		WorkoutButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addWorkout();
			}
		});
		WorkoutButton.setText("Add Workout");
		flexTable.setWidget(1, 0, WorkoutButton);
		WorkoutButton.setSize("111px", "45px");

	}
	
	public void addFoodItems()
	{
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
		
		for (int i = 0; i < items.length; i++) {
			saveFoodItem(items[i]);
		}
		
	}
	
		private void saveFoodItem(FoodItem item) {
			Bridge msg = new Bridge();
			msg.foodItem = item;
			rpc.saveFoodItem(msg, new AsyncCallback<Boolean>() {
				public void onFailure(Throwable caught) {}

				public void onSuccess(Boolean result) {}
			});
		}
		
		
	public void addWorkout()
	{
		ExerciseItem items[] = new ExerciseItem[13];
		items[0] = new ExerciseItem("Basketball (leisurely)", 130);
		items[1] = new ExerciseItem("Bicycling (10 mph)", 125);
		items[2] = new ExerciseItem("Bicycling (13 mph)", 200);
		items[3] = new ExerciseItem("Ping pong", 90);
		items[4] = new ExerciseItem("Running (8 mph)", 305);
		items[5] = new ExerciseItem("Running (9 mph)", 330);
		items[6] = new ExerciseItem("Running (10 mph)", 350);
		items[7] = new ExerciseItem("Soccer", 195);
		items[8] = new ExerciseItem("Swimming", 120);
		items[9] = new ExerciseItem("Tennis", 160);
		items[10] = new ExerciseItem("Walking (3 mph)", 80);
		items[11] = new ExerciseItem("Walking (4 mph)", 100);
		items[12] = new ExerciseItem("Weight training", 190);
		for (int i = 0; i < items.length; i++) {
			saveExerciseItem(items[i]);
		}
		
	}
	private void saveExerciseItem(ExerciseItem item) {
		Bridge msg = new Bridge();
		msg.exercise = item;
		rpc.saveExerciseItem(msg, new AsyncCallback<Boolean>() {
			public void onFailure(Throwable caught) {}

			public void onSuccess(Boolean result) {}
		});
	}
}
