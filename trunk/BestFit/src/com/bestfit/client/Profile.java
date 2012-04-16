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
	private TextBox WeightTextBox;

	private final RpcServicesAsync rpc = GWT.create(RpcServices.class);
	private TextBox BirthDayTextBox;
	
	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get("profilecont");
		
		FlexTable flexTable = new FlexTable();
		rootPanel.add(flexTable, 0, 0);
		flexTable.setSize("277px", "232px");
		
		Label lblNewLabel = new Label("First Name");
		flexTable.setWidget(0, 0, lblNewLabel);
		
		TextBox FirstNameTextBox = new TextBox();
		flexTable.setWidget(0, 1, FirstNameTextBox);
		FirstNameTextBox.setHeight("25px");
		
		Label lblNewLabel_1 = new Label("Last Name");
		flexTable.setWidget(1, 0, lblNewLabel_1);
		
		TextBox LastNameTextBox = new TextBox();
		flexTable.setWidget(1, 1, LastNameTextBox);
		LastNameTextBox.setHeight("25px");
		
		Label lblNewLabel_2 = new Label("Email Address");
		flexTable.setWidget(2, 0, lblNewLabel_2);
		
		TextBox EmailAddressTextBox = new TextBox();
		flexTable.setWidget(2, 1, EmailAddressTextBox);
		EmailAddressTextBox.setHeight("25px");
		
		Label lblNewLabel_3 = new Label("Birthday");
		flexTable.setWidget(3, 0, lblNewLabel_3);
		
		BirthDayTextBox = new TextBox();
		flexTable.setWidget(3, 1, BirthDayTextBox);
		BirthDayTextBox.setHeight("25px");
		
		Label lblNewLabel_4 = new Label("Gender");
		flexTable.setWidget(4, 0, lblNewLabel_4);
		
		TextBox GenderTextBox = new TextBox();
		flexTable.setWidget(4, 1, GenderTextBox);
		GenderTextBox.setHeight("25px");
		
		Label lblNewLabel_5 = new Label("Height");
		flexTable.setWidget(5, 0, lblNewLabel_5);
		
		TextBox HeightTextBox = new TextBox();
		flexTable.setWidget(5, 1, HeightTextBox);
		HeightTextBox.setHeight("25px");
		
		Label lblNewLabel_6 = new Label("Weight");
		flexTable.setWidget(6, 0, lblNewLabel_6);
		
		WeightTextBox = new TextBox();
		flexTable.setWidget(6, 1, WeightTextBox);
		WeightTextBox.setHeight("25px");

	}
}
