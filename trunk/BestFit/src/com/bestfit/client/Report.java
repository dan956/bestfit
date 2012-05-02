package com.bestfit.client;

import java.io.Console;
import java.util.ArrayList;

import com.bestfit.data.*;
import com.bestfit.shared.Bridge;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.LineChart;
import com.google.gwt.visualization.client.visualizations.PieChart;
import com.google.gwt.visualization.client.visualizations.PieChart.Options;


public class Report implements EntryPoint {

	private final RpcServicesAsync rpc = GWT.create(RpcServices.class);

	double Protein=2.9;
	double Carbs=1.0;
	int Fat=0;
	private ArrayList<Weight> weights = new ArrayList<Weight>();
    
	public void DrawCaloriesChart()
	{
		rpc.getUserMeals(new AsyncCallback<Bridge>() {

			@Override
			public void onSuccess(Bridge result) {


				for(Meal meal:result.meals){
					Protein+=meal.totalCarbohydrates();
					Carbs+=meal.totalFatGrams();
					Fat+=meal.totalFatGrams();	  					  

				}	//WeightHistory
				Panel piePanel = RootPanel.get("calorieConsumption");
				

				// Create a pie chart visualization.
				PieChart pie = new PieChart(createTableCalorieConsumption(), createPieOptions());

				pie.addSelectHandler(createSelectHandler(pie));
				piePanel.add(pie);
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}
	
	public void DrawWeightHistory()	{
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
				DrawCaloriesChart();
				DrawWeightHistory();
			}
		};

		// Load the visualization api, passing the onLoadCallback to be called
		// when loading is done.
		VisualizationUtils.loadVisualizationApi(onLoadCallback, new String[]{PieChart.PACKAGE, LineChart.PACKAGE});
	}

	private Options createPieOptions() {
		Options options = Options.create();
		options.setWidth(300);
		options.setHeight(240);
		options.setBackgroundColor("");
		options.set3D(true);
		options.setTitle("calorie consumption");
		return options;
	}

	private SelectHandler createSelectHandler(final PieChart chart) {
		return new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
//				String message = "";
//
//				// May be multiple selections.
//				JsArray<Selection> selections = chart.getSelections();
//
//				for (int i = 0; i < selections.length(); i++) {
//					// add a new line for each selection
//					message += i == 0 ? "" : "\n";
//
//					Selection selection = selections.get(i);
//
//					if (selection.isCell()) {
//						// isCell() returns true if a cell has been selected.
//
//						// getRow() returns the row number of the selected cell.
//						int row = selection.getRow();
//						// getColumn() returns the column number of the selected cell.
//						int column = selection.getColumn();
//						message += "cell " + row + ":" + column + " selected";
//					} else if (selection.isRow()) {
//						// isRow() returns true if an entire row has been selected.
//
//						// getRow() returns the row number of the selected row.
//						int row = selection.getRow();
//						message += "row " + row + " selected";
//					} else {
//						// unreachable
//						message += "Pie chart selections should be either row selections or cell selections.";
//						message += "  Other visualizations support column selections as well.";
//					}
//				}
//
//				Window.alert(message);
			}
		};
	}

	private AbstractDataTable createTableCalorieConsumption() {

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
