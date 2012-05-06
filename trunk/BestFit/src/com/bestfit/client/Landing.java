package com.bestfit.client;
import java.util.Date;

import com.bestfit.data.ExerciseItem;
import com.bestfit.data.FoodItem;
import com.bestfit.data.Meal;
import com.bestfit.data.Workout;
import com.bestfit.shared.Bridge;
import com.google.gwt.thirdparty.guava.common.primitives.UnsignedBytes;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class Landing implements EntryPoint {

	private final RpcServicesAsync rpc = GWT.create(RpcServices.class);
	private TextBox textBox;
	static double mealCal;
	static double workoutCal;

	@Override
	public void onModuleLoad() {

		getCurrentWeight();
		getCurrentGoal();
		getUserMeals();
		getUserWorkout();
		getUserName();
		calculateCalorios();

	}


	static double bmr=0.0;



	public void calculateCalorios()
	{
		rpc.getBMR(new AsyncCallback<Double>() {

			@Override
			public void onSuccess(Double result) {
				Landing.bmr = result;
				rpc.getGoalHistory(new AsyncCallback<Bridge>() {

					@Override
					public void onSuccess(Bridge result) {
						Date today = new Date();

						Date TargetDate = result.goals.get(result.goals.size()-1).getTargetDate();
						long diff =   TargetDate.getTime() - today.getTime();

						diff /=  (1000 * 60 * 60 * 24);

						double TargetWeight = result.goals.get(result.goals.size()-1).getTargetWeight();
						double CurrentWeight = Double.valueOf(textBox.getText());
						double CalsPerDay = Landing.bmr + 3500 * (TargetWeight - CurrentWeight ) / diff;


						RootPanel root = RootPanel.get("CalPerDay");
						
						DecoratorPanel perDayDecoratorPanel = new DecoratorPanel();
						perDayDecoratorPanel.setWidth("180px");
						root.add(perDayDecoratorPanel);
						
						FlexTable cPerDayFlexTable = new FlexTable();

						
						String message="";
						double gainedToday = Landing.mealCal - Landing.workoutCal;
						
						int a = (int) CalsPerDay;
						
						message = "<b>Daily net calorie: </b></br><pre>        "+ a +" cal/day</pre>";						
						
						message += "<b>Regular activitiy burns: </b></br><pre>        "+2000 +" cal/day</pre>";						
					
						message += "<b>From today's meal/workout: </b></br><pre>        "+gainedToday +" cal</pre>";							
						
						double resultCals = (int)(CalsPerDay - 2000 + gainedToday);
						
						if(resultCals>0){
							message += "<b>You still need to burn: </b></br><pre>        "+resultCals +" cal</pre>";							
						}else{
							gainedToday = 0 - gainedToday;
							message += "<b>Exceeded your goal by: </b></br><pre>        "+resultCals +" cal</pre>";
						}
						
						
						HTML html = new HTML(message);						
						cPerDayFlexTable.setWidget(0, 0, html);
						cPerDayFlexTable.getCellFormatter().addStyleName(0, 0, "calorieSummary");
						perDayDecoratorPanel.add(cPerDayFlexTable);

					}

					@Override
					public void onFailure(Throwable caught) {

					}
				});				

			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void getCurrentWeight()
	{
		rpc.getCurrentWeight(new AsyncCallback<String>(){

			@Override
			public void onFailure(Throwable caught) {
				//WTextBox.setText(caught.getMessage());

			}

			@Override
			public void onSuccess(String result) {

				RootPanel rootPanel = RootPanel.get("WeightCon");

				DecoratorPanel weightDecoratorPanel = new DecoratorPanel();
				weightDecoratorPanel.setWidth("180px");
				rootPanel.add(weightDecoratorPanel);

				FlexTable weightFlexTable = new FlexTable();
				weightDecoratorPanel.setWidget(weightFlexTable);

				Label lblNewLabel = new Label("Your current weight");
				weightFlexTable.setWidget(0, 0, lblNewLabel);

				textBox = new TextBox();
				textBox.setText(result);
				weightFlexTable.setWidget(1, 0, textBox);

				Button btnNewButton = new Button("New button");
				btnNewButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {

						rpc.storeNewWeight(Double.valueOf(textBox.getValue()), new Date(), new AsyncCallback<String>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(String result) {
								Window.alert("Your weight has been successfully submittted!");

							}
						} );

					}
				});
				btnNewButton.setText("Update");
				weightFlexTable.setWidget(2, 0, btnNewButton);
				weightFlexTable.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_RIGHT);

			}


		});
	}

	public void getCurrentGoal()
	{
		rpc.getGoalHistory(new AsyncCallback<Bridge>() {

			@Override
			public void onSuccess(Bridge result) {
				if(result.goals.size()>0){
					RootPanel rootPanel = RootPanel.get("goalHistory");

					DecoratorPanel goalDecoratorPanel = new DecoratorPanel();
					rootPanel.add(goalDecoratorPanel);

					FlexTable weightFlexTable = new FlexTable();
					weightFlexTable.setSize("380px", "80px");
					goalDecoratorPanel.setWidget(weightFlexTable);

					String displayGoal ="";

					Date today = new Date();

					long diff =  result.goals.get(result.goals.size()-1).getTargetDate().getTime() - today.getTime();

					diff /=  (1000 * 60 * 60 * 24);
					
					displayGoal+="Your current goal is to maintain "+result.goals.get(result.goals.size()-1).getTargetWeight()
							+" pounds in "+ String.valueOf(diff) +" days";

					Label lblNewLabel = new Label(displayGoal);
					weightFlexTable.setWidget(0, 0, lblNewLabel);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void getUserMeals()
	{
		rpc.getUserMeals(new AsyncCallback<Bridge>() {

			@Override
			public void onSuccess(Bridge result) {
				if(result.meals.size()>0){


					RootPanel rootPanel = RootPanel.get("UserMeals");

					//	DecoratorPanel goalDecoratorPanel = new DecoratorPanel();
					//rootPanel.add(goalDecoratorPanel);

					FlexTable mealsFlexTable = new FlexTable();
					//mealsFlexTable.setStyleName("cw-FlexTable");
					//mealsFlexTable.setSize("270px", "300px");
					mealsFlexTable.setWidth("390px");

					mealsFlexTable.setText(0, 0, "Name");
					mealsFlexTable.setText(0, 1, "Meal Items");
					mealsFlexTable.setText(0, 2, "Calories");

					mealsFlexTable.getCellFormatter().addStyleName(0, 0, "mealsListHeader");
					mealsFlexTable.getCellFormatter().addStyleName(0, 1, "mealsListHeader");
					mealsFlexTable.getCellFormatter().addStyleName(0, 2, "mealsListHeader");

					double mealCalToday =0.0;
					int row=0;

					for(Meal meal: result.meals){
						row++;
						mealsFlexTable.setText(row, 0, meal.getLabel());
						mealsFlexTable.getCellFormatter().addStyleName(row, 0, "mealsListRow");
						String itemTitle="";
						for(FoodItem item: meal.getFoodItems()){
							itemTitle+=item.getName() +"</br>";							
						}
						
						HTML html = new HTML(itemTitle);
						
						mealsFlexTable.setWidget(row, 1, html);
						mealsFlexTable.getCellFormatter().addStyleName(row, 1, "mealsListRow");
						mealsFlexTable.setText(row, 2, String.valueOf(meal.totalCalories()));
						mealsFlexTable.getCellFormatter().addStyleName(row, 2, "mealsListRow");

						mealCalToday +=  meal.totalCalories();

					}
					
					Landing.mealCal = mealCalToday;
					
					HTML html = new HTML("</br>Total calories consumed = "+mealCalToday);
					mealsFlexTable.setWidget(row+1, 1, html);
					mealsFlexTable.getCellFormatter().addStyleName(row+1, 1, "mealsTotalCalories");

					rootPanel.add(mealsFlexTable);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});

	}

	public void getUserWorkout(){


		rpc.getUserWorkouts(new AsyncCallback<Bridge>() {

			@Override
			public void onSuccess(Bridge result) {
				if(result.workouts.size()>0){
					RootPanel rootPanel = RootPanel.get("UserWorkout");

					//DecoratorPanel goalDecoratorPanel = new DecoratorPanel();
					//rootPanel.add(goalDecoratorPanel);

					FlexTable workoutFlexTable = new FlexTable();
					workoutFlexTable.setWidth("390px");

					workoutFlexTable.setText(0, 0, "Name");
					workoutFlexTable.setText(0, 1, "Workout");
					workoutFlexTable.setText(0, 2, "Calories");


					workoutFlexTable.getCellFormatter().addStyleName(0, 0, "mealsListHeader");
					workoutFlexTable.getCellFormatter().addStyleName(0, 1, "mealsListHeader");
					workoutFlexTable.getCellFormatter().addStyleName(0, 2, "mealsListHeader");


					double workOutCalToday =0.0;
					int row=0;
					
					for(Workout work : result.workouts)
					{
						row++;
						workoutFlexTable.setText(row, 0, work.getLabel());
						workoutFlexTable.getCellFormatter().addStyleName(row, 0, "mealsListRow");
						String itemTitle="";
						for(ExerciseItem item: work.getExerciseItems()){
							itemTitle+=item.getName() +"</br>";
						}
						
						HTML html = new HTML(itemTitle);

						workoutFlexTable.setWidget(row, 1, html);
						workoutFlexTable.getCellFormatter().addStyleName(row, 1, "mealsListRow");
						workoutFlexTable.setText(row, 2, String.valueOf(work.totalCaloriesBurned()));
						workoutFlexTable.getCellFormatter().addStyleName(row, 2, "mealsListRow");

						workOutCalToday +=  work.totalCaloriesBurned();


					}

					Landing.workoutCal = workOutCalToday;
					HTML html = new HTML("</br>Total calories burned= "+workOutCalToday);
					workoutFlexTable.setWidget(row+1, 1, html);
					
					workoutFlexTable.getCellFormatter().addStyleName(row+1, 1, "mealsTotalCalories");
					
					rootPanel.add(workoutFlexTable);
				}

			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});


	}

	public void getUserName()
	{
		rpc.getUserName(new AsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				RootPanel rootPanel = RootPanel.get("userName");

				FlexTable userNameFlexTable = new FlexTable();
				userNameFlexTable.setSize("200px", "60px");
				userNameFlexTable.setWidget(0, 0,new HTML("Welcome <i>"+ result+"!</i>"));
				userNameFlexTable.setText(1, 0, "Below is your data for Today");

				rootPanel.add(userNameFlexTable);
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}

}
