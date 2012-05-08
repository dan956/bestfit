package com.bestfit.client;


import com.bestfit.shared.Bridge;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class Profile implements EntryPoint {

	private final RpcServicesAsync rpc = GWT.create(RpcServices.class);
	private TextBox FirstNameTextBox;
	private TextBox LastNameTextBox;
	private TextBox EmailAddressTextBox;
	private TextBox BirthDayTextBox;
	private TextBox GenderTextBox;
	private TextBox HeightTextBox;
	private TextBox WeightTextBox;
	
	public void onModuleLoad() {
		rpc.isNewUser(new AsyncCallback<Boolean>() {
			
			@Override
			public void onSuccess(Boolean result) {
				if(!result)
				{
					loadPage();

				}
				else
				{
					Window.Location.assign("/registration.html");
				}
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	private void loadPage() {
		setPageHeader();
		
		
		RootPanel rootPanel = RootPanel.get("profilecont");
		
		FlexTable flexTable = new FlexTable();
		rootPanel.add(flexTable, 0, 0);
		flexTable.setSize("277px", "232px");
		
		Label lblNewLabel = new Label("First Name");
		flexTable.setWidget(0, 0, lblNewLabel);
		
		FirstNameTextBox = new TextBox();
		flexTable.setWidget(0, 1, FirstNameTextBox);
		FirstNameTextBox.setHeight("25px");
		
		Label lblNewLabel_1 = new Label("Last Name");
		flexTable.setWidget(1, 0, lblNewLabel_1);
		
		LastNameTextBox = new TextBox();
		flexTable.setWidget(1, 1, LastNameTextBox);
		LastNameTextBox.setHeight("25px");
		
		Label lblNewLabel_2 = new Label("Email Address");
		flexTable.setWidget(2, 0, lblNewLabel_2);
		
		EmailAddressTextBox = new TextBox();
		flexTable.setWidget(2, 1, EmailAddressTextBox);
		EmailAddressTextBox.setHeight("25px");
		
		Label lblNewLabel_3 = new Label("Age");
		flexTable.setWidget(3, 0, lblNewLabel_3);
		
		BirthDayTextBox = new TextBox();
		flexTable.setWidget(3, 1, BirthDayTextBox);
		BirthDayTextBox.setHeight("25px");
		
		Label lblNewLabel_4 = new Label("Gender");
		flexTable.setWidget(4, 0, lblNewLabel_4);
		
		GenderTextBox = new TextBox();
		flexTable.setWidget(4, 1, GenderTextBox);
		GenderTextBox.setHeight("25px");
		
		Label lblNewLabel_5 = new Label("Height");
		flexTable.setWidget(5, 0, lblNewLabel_5);
		
		HeightTextBox = new TextBox();
		flexTable.setWidget(5, 1, HeightTextBox);
		HeightTextBox.setHeight("25px");
		
		Label lblNewLabel_6 = new Label("Weight");
		flexTable.setWidget(6, 0, lblNewLabel_6);
		
		WeightTextBox = new TextBox();
		flexTable.setWidget(6, 1, WeightTextBox);
		WeightTextBox.setHeight("25px");
		
		getUserProfile();
	}
	
	private void setPageHeader() {
		RootPanel rpanel = RootPanel.get("profileheader");
		
		HTML html = new HTML("<h3><b><font color=\"#308A4D\">Your Personal information!</font></b></h3>");
		
		rpanel.add(html);
		
	}

	public void getUserProfile()
	{
		rpc.getUserProfile(new AsyncCallback<Bridge>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Bridge result) {
				// TODO Auto-generated method stub
				
				FirstNameTextBox.setText(result.firstName);
				LastNameTextBox.setText(result.lastName);
				EmailAddressTextBox.setText(result.email);
				GenderTextBox.setText(result.gender);
				HeightTextBox.setText(String.valueOf(result.height));
				WeightTextBox.setText(String.valueOf(result.weight));
				BirthDayTextBox.setText(String.valueOf(result.age));

				
				
			}
			
			
			
		});
	
	}
}
