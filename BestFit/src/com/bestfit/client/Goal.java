package com.bestfit.client;

import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.james.mime4j.field.datetime.DateTime;

import com.bestfit.shared.Bridge;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.google.gwt.i18n.client.CurrencyData;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class Goal implements EntryPoint {
	private Label lblNewLabel;
	private Label lblNewLabel_1;
	private Label lblNewLabel_2;
	private Label lblNewLabel_3;
	private TextBox textBox;
	private TextBox textBox_1;
	private TextBox textBox_3;
	private Button btnNewButton_1;
	private DateBox dateBox;
	private  double BMR= 0.0;

	private final RpcServicesAsync rpc = GWT.create(RpcServices.class);
	
	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get("goalCont");
		FlexTable flexTable = new FlexTable();
		rootPanel.add(flexTable, 0, 0);
		flexTable.setSize("398px", "259px");
		
		lblNewLabel = new Label("Current Weight:");
		flexTable.setWidget(0, 0, lblNewLabel);
		
		textBox = new TextBox();
		textBox.setReadOnly(true);
		flexTable.setWidget(0, 1, textBox);
		textBox.setHeight("25px");
		
		lblNewLabel_1 = new Label("Target Weight:");
		flexTable.setWidget(1, 0, lblNewLabel_1);
		
		textBox_1 = new TextBox();
		flexTable.setWidget(1, 1, textBox_1);
		textBox_1.setHeight("25px");
		
		lblNewLabel_2 = new Label("Target Date: (MM-DD-YYYY)");
		flexTable.setWidget(2, 0, lblNewLabel_2);
		
		dateBox = new DateBox();
		dateBox.setFormat(new DefaultFormat(DateTimeFormat.getFormat("MM-dd-yyyy")));
		dateBox.getDatePicker().setStyleName("gwt-DatePicker");
		
		flexTable.setWidget(2, 1, dateBox);
		dateBox.setHeight("25px");
		
		lblNewLabel_3 = new Label("Daily net calories to achieve goal:");
		flexTable.setWidget(3, 0, lblNewLabel_3);
		
		textBox_3 = new TextBox();
		textBox_3.setReadOnly(true);
		flexTable.setWidget(3, 1, textBox_3);
		textBox_3.setHeight("25px");
		
		btnNewButton_1 = new Button("New button");
		flexTable.setWidget(4, 1, btnNewButton_1);
		btnNewButton_1.setSize("80px", "31px");
		btnNewButton_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				// First get BMR
				
				
				rpc.getBMR(new AsyncCallback<Double>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(Double result) {
						
						BMR =result;
						
					}
				});
				
				
				// calc time
				Date today = new Date();

				long diff =   dateBox.getValue().getTime() - today.getTime();
				
				diff /=  (1000 * 60 * 60 * 24);
				
				
				// CalsPerDay = BMR + 3500 * (Target_Weight - Current_Weight) / Days_Remaining
				double CalsPerDay = BMR + 3500 * (Double.valueOf(textBox_1.getText())- Double.valueOf(textBox.getText()))/diff;
				
				
				textBox_3.setText( String.valueOf(CalsPerDay));
				
				
				

			}
		});
		btnNewButton_1.setText("Calculate");
		flexTable.getCellFormatter().setHorizontalAlignment(4, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.getCellFormatter().setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.getCellFormatter().setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.getCellFormatter().setHorizontalAlignment(3, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		Button btnNewButton = new Button("New button");
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				Date currentTime = new Date(); 
				
				Bridge msg = new Bridge();
				msg.targetWeight = Double.valueOf(textBox_1.getValue());
				msg.startDate = currentTime;
				msg.targetDate = dateBox.getValue();
				
				rpc.storeGoal(msg, new AsyncCallback<String>() {
					
					@Override
					public void onSuccess(String result) {
						System.out.print(result);
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						System.out.print(caught.getMessage());
						
					}
				});
				
			}
		});
		btnNewButton.setText("Save");
		flexTable.setWidget(4, 2, btnNewButton);
		btnNewButton.setSize("80px", "31px");
		flexTable.getCellFormatter().setHorizontalAlignment(4, 2, HasHorizontalAlignment.ALIGN_LEFT);
		
		getCurrentWeight();
	}
	
	public void getCurrentWeight()
	{
		rpc.getCurrentWeight(new AsyncCallback<String>(){

			@Override
			public void onFailure(Throwable caught) {
				textBox.setText(caught.getMessage());
				
			}

			@Override
			public void onSuccess(String result) {
				
				textBox.setText(result);
			
				
			}
			
			
		});
	}
}
