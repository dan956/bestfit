package com.bestfit.client;

import java.util.ArrayList;
import java.util.Date;

import com.bestfit.data.*;
import com.bestfit.shared.Bridge;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.visualizations.LineChart;
import com.google.gwt.visualization.client.visualizations.PieChart;
import com.google.gwt.visualization.client.visualizations.PieChart.Options;


public class Report implements EntryPoint {

	private final RpcServicesAsync rpc = GWT.create(RpcServices.class);

	double Protein=2.9;
	double Carbs=1.0;
	int Fat=0;
	private ArrayList<Weight> weights = new ArrayList<Weight>();
	private ArrayList<Workout> workouts = new ArrayList<Workout>();
	
	private void setPageHeader() {
		RootPanel rpanel = RootPanel.get("reportheader");
		
		HTML html = new HTML("<h3><b><font color=\"#308A4D\">Your Personal statistics!</font></b></h3>");
		
		rpanel.add(html);
		
	}
	
	public void drawDailyWorkoutComposition()
	{
		Bridge bridge = new Bridge();
		bridge.startDate = new Date();
		rpc.getUserWorkouts(bridge, new AsyncCallback<Bridge>() {

			@Override
			public void onSuccess(Bridge result) {
				
				workouts.addAll(result.workouts);
				
				Panel piePanel = RootPanel.get("WorkoutHistory");
				PieChart pie = new PieChart(createTableDailyWorkout(), createWorkoutPieOptions());

				piePanel.add(pie);
			}

			

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}
	
	private Options createWorkoutPieOptions() {
		Options options = Options.create();
		options.setWidth(300);
		options.setHeight(240);
		options.set3D(true);
		options.setTitle("Daily Workouts");
		return options;
	}

	private AbstractDataTable createTableDailyWorkout() {
		DataTable data = DataTable.create();
		data.addColumn(ColumnType.STRING, "Workouts");
		data.addColumn(ColumnType.NUMBER, "CalorieBurned");
		data.addRows(workouts.size());
		
		int row=0;
		for(Workout wk : workouts){
			data.setValue(row, 0, wk.getLabel());			
			data.setValue(row, 1, wk.totalCaloriesBurned());
			
			row++;
		}
		
		return data;
	}
    
	public void drawDailyMealComposition()
	{
		Bridge msg = new Bridge();
		msg.startDate = new Date(); // get today's data
		rpc.getUserMeals(msg, new AsyncCallback<Bridge>() {

			@Override
			public void onSuccess(Bridge result) {


				for(Meal meal:result.meals){
					Protein+=meal.totalProtein();
					Carbs+=meal.totalCarbohydrates();
					Fat+=meal.totalFatGrams();


				}	
				Panel piePanel = RootPanel.get("DailyMealComposition");
				

				// Create a pie chart visualization.
				PieChart pie = new PieChart(createTableDailyMealComposition(), createDailyMealCompositionPieOptions());

				piePanel.add(pie);
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}

	
	double totalCalc, totalFatCalc;
	public void DrawDailyCaloriesComposition()
	{

		
		Bridge msg = new Bridge();
		msg.startDate = new Date();
		rpc.getUserMeals(msg, new AsyncCallback<Bridge>() {

			@Override
			public void onSuccess(Bridge result) {


				for(Meal meal:result.meals){
					totalCalc+=meal.totalCalories();
					totalFatCalc+=meal.totalFatCalories();
					
				}	
				Panel piePanel = RootPanel.get("DailyCalorieComposition");
				

				// Create a pie chart visualization.
				PieChart pie = new PieChart(createTableDailyCaloriesComposition(), createDailyCaloriesCompositionPieOptions());

				
				piePanel.add(pie);
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
		
		
	}

	public void drawWeightHistory()	{
		rpc.getWeightHistory(new AsyncCallback<Bridge>() {

			@Override
			public void onSuccess(Bridge result) {
								
				Panel linePanel = RootPanel.get("WeightHistory");
				
				weights.addAll(result.weightHistory);
				LineChart linechart = new LineChart(createTableWeightHistory(), createLineOptions());
				
				linePanel.add(linechart);
			}

			

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}
	
	public void onModuleLoad() {


		// Create a callback to be called when the visualization API
		// has been loaded.
		Runnable onLoadCallback = new Runnable() {
			public void run() {
				setPageHeader();
				drawDailyMealComposition();
				drawWeightHistory();
				DrawDailyCaloriesComposition();
				drawDailyWorkoutComposition();
			}
		};

		// Load the visualization api, passing the onLoadCallback to be called
		// when loading is done.
		VisualizationUtils.loadVisualizationApi(onLoadCallback, new String[]{PieChart.PACKAGE, LineChart.PACKAGE});
	}

	private Options createDailyMealCompositionPieOptions() {
		Options options = Options.create();
		options.setWidth(300);
		options.setHeight(240);
		options.set3D(true);
		options.setTitle("Daily Meal Composition");
		return options;
	}

	private Options createDailyCaloriesCompositionPieOptions() {
		Options options = Options.create();
		options.setWidth(300);
		options.setHeight(240);
		options.set3D(true);
		options.setTitle("Daily Calories Composition");
		return options;
	}
	
	
	
	
	
	private AbstractDataTable createTableDailyMealComposition() {

		DataTable data = DataTable.create();
		data.addColumn(ColumnType.STRING, "FoodItems");
		data.addColumn(ColumnType.NUMBER, "TotalCalorie");
		data.addRows(3);
		data.setValue(0, 0, "Fat");
		data.setValue(0, 1, Fat);
		data.setValue(1, 0, "Carbs");
		data.setValue(1, 1, Carbs);
		data.setValue(2, 0, "Protein");
		data.setValue(2, 1, Protein);
		return data;
	}

	private AbstractDataTable createTableDailyCaloriesComposition() {

		DataTable data = DataTable.create();
		data.addColumn(ColumnType.STRING, "FoodItems");
		data.addColumn(ColumnType.NUMBER, "TotalCalorie");
		data.addRows(2);
		data.setValue(0, 0, "Fat");
		data.setValue(0, 1, totalFatCalc);
		data.setValue(1, 0, "Non Fat");
		double nonFat = totalCalc - totalFatCalc;	
		data.setValue(1, 1, nonFat);
		return data;
	}
	
	
	
	private LineChart.Options createLineOptions() {
		LineChart.Options options = LineChart.Options.create();
		options.setWidth(300);
		options.setHeight(240);
		
		options.setSmoothLine(true);
		options.setLineSize(2);
		
		options.setTitle("Weight History");
		return options;
	}

	private AbstractDataTable createTableWeightHistory() {
		DataTable data = DataTable.create();
		data.addColumn(ColumnType.DATE, "Date");
		data.addColumn(ColumnType.NUMBER, "Weight");
		
		data.addRows(weights.size());
		
		int row=0;
		
		for(Weight wt:weights){
			data.setValue(row, 0, wt.getDate());
			data.setValue(row, 1, wt.getWeight());
			row++;			
		}
		
		return data;
	}

}
