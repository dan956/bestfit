package com.bestfit.client;


import com.bestfit.shared.BridgeUsers;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class Profile implements EntryPoint {
	private Label lblNewLabel_7;
	private TextBox WeightTextBox;

	private final RpcServicesAsync rpc = GWT.create(RpcServices.class);
	
	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get("profCom");
		
		FlexTable flexTable = new FlexTable();
		rootPanel.add(flexTable);
		
		Label lblNewLabel = new Label("First Name");
		flexTable.setWidget(0, 0, lblNewLabel);
		
		TextBox FirstNameTextBox = new TextBox();
		flexTable.setWidget(0, 1, FirstNameTextBox);
		
		Label lblNewLabel_1 = new Label("Last Name");
		flexTable.setWidget(1, 0, lblNewLabel_1);
		
		TextBox LastNameTextBox = new TextBox();
		flexTable.setWidget(1, 1, LastNameTextBox);
		
		Label lblNewLabel_2 = new Label("Email Address");
		flexTable.setWidget(2, 0, lblNewLabel_2);
		
		TextBox EmailAddressTextBox = new TextBox();
		flexTable.setWidget(2, 1, EmailAddressTextBox);
		
		Label lblNewLabel_3 = new Label("Birthday");
		flexTable.setWidget(3, 0, lblNewLabel_3);
		
		DateBox BirthDayDateBox = new DateBox();
		flexTable.setWidget(3, 1, BirthDayDateBox);
		
		Label lblNewLabel_4 = new Label("Gender");
		flexTable.setWidget(4, 0, lblNewLabel_4);
		
		TextBox GenderTextBox = new TextBox();
		flexTable.setWidget(4, 1, GenderTextBox);
		
		Label lblNewLabel_5 = new Label("Height");
		flexTable.setWidget(5, 0, lblNewLabel_5);
		
		TextBox HeightTextBox = new TextBox();
		flexTable.setWidget(5, 1, HeightTextBox);
		
		Label lblNewLabel_6 = new Label("Weight");
		flexTable.setWidget(6, 0, lblNewLabel_6);
		
		WeightTextBox = new TextBox();
		flexTable.setWidget(6, 1, WeightTextBox);
		
		Button btnNewButton = new Button("New button");
		btnNewButton.setText("Save");
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				rpc.saveUsers("aaaa", new AsyncCallback<BridgeUsers>(){

					@Override
					public void onFailure(Throwable caught) {
						lblNewLabel_7.setText(caught.getMessage());
						
					}

					@Override
					public void onSuccess(BridgeUsers result) {
						lblNewLabel_7.setText(result.LastName);
						
					}
					
				});
			}
		});
		
		Button btnNewButton_1 = new Button("New button");
		btnNewButton_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				rpc.getUsers("aaaa", new AsyncCallback<BridgeUsers>(){

					@Override
					public void onFailure(Throwable caught) {
						lblNewLabel_7.setText(caught.getMessage());
						
					}

					@Override
					public void onSuccess(BridgeUsers result) {
						lblNewLabel_7.setText(result.Name);
						
					}
					
				});
				
			}
		});
		btnNewButton_1.setText("Get");
		flexTable.setWidget(7, 0, btnNewButton_1);
		flexTable.setWidget(7, 1, btnNewButton);
		
		lblNewLabel_7 = new Label("New label");
		flexTable.setWidget(8, 1, lblNewLabel_7);

	}
}
