package com.bestfit.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.client.ui.ListBox;

public class Landing implements EntryPoint {

	private TextBox WeightTextBox;
	
	@Override
	public void onModuleLoad() {
		
		RootPanel rootPanel = RootPanel.get();
		
		FlexTable flexTable = new FlexTable();
		
		
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
		
		ListBox comboBox = new ListBox();
		comboBox.addItem("Male");
		comboBox.addItem("Female");
		flexTable.setWidget(4, 1, comboBox);
		
		Label lblNewLabel_5 = new Label("Height");
		flexTable.setWidget(5, 0, lblNewLabel_5);
		
		TextBox HeightTextBox = new TextBox();
		flexTable.setWidget(5, 1, HeightTextBox);
		
		Label lblNewLabel_6 = new Label("Weight");
		flexTable.setWidget(6, 0, lblNewLabel_6);
		
		WeightTextBox = new TextBox();
		flexTable.setWidget(6, 1, WeightTextBox);
		
		rootPanel.add(flexTable);
		
		Button btnNewButton = new Button("New button");
		flexTable.setWidget(7, 1, btnNewButton);
		
		Button btnNewButton_1 = new Button("New button");
		flexTable.setWidget(7, 2, btnNewButton_1);
		
		
	/*	final DialogBox dialogBox = new DialogBox();
	//	dialogBox.setText("Registration");
		dialogBox.setAnimationEnabled(true);
		dialogBox.setWidget(flexTable);
		
		dialogBox.center();*/

	}
}
