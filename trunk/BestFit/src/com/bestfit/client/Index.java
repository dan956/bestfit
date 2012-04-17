package com.bestfit.client;

import com.bestfit.shared.Bridge;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.client.ui.IntegerBox;

public class Index implements EntryPoint {

	private TextBox WeightTextBox;
	private Button SaveButton;
	private TextBox FirstNameTextBox;
	private TextBox LastNameTextBox;
	private TextBox EmailAddressTextBox;
	private ListBox comboBox;
	private TextBox HeightTextBox;
	private IntegerBox integerBox;
	private DialogBox dialogBox;
	private final RpcServicesAsync rpc = GWT.create(RpcServices.class);
	
	@Override
	public void onModuleLoad() {
		
		//CheckLogin();
		
		RootPanel rootPanel = RootPanel.get("gd");
        FlexTable flexTable = new FlexTable();
		
		
		Label lblNewLabel = new Label("First Name");
		flexTable.setWidget(0, 0, lblNewLabel);
		
		FirstNameTextBox = new TextBox();
		flexTable.setWidget(0, 1, FirstNameTextBox);
		
		Label lblNewLabel_1 = new Label("Last Name");
		flexTable.setWidget(1, 0, lblNewLabel_1);
		
		LastNameTextBox = new TextBox();
		flexTable.setWidget(1, 1, LastNameTextBox);
		
		Label lblNewLabel_2 = new Label("Email Address");
		flexTable.setWidget(2, 0, lblNewLabel_2);
		
		EmailAddressTextBox = new TextBox();
		flexTable.setWidget(2, 1, EmailAddressTextBox);
		
		Label lblNewLabel_3 = new Label("Age");
		flexTable.setWidget(3, 0, lblNewLabel_3);
		
		integerBox = new IntegerBox();
		flexTable.setWidget(3, 1, integerBox);
		
		Label lblNewLabel_4 = new Label("Gender");
		flexTable.setWidget(4, 0, lblNewLabel_4);
		
		comboBox = new ListBox();
		comboBox.addItem("Male");
		comboBox.addItem("Female");
		flexTable.setWidget(4, 1, comboBox);
		
		Label lblNewLabel_5 = new Label("Height");
		flexTable.setWidget(5, 0, lblNewLabel_5);
		
		HeightTextBox = new TextBox();
		flexTable.setWidget(5, 1, HeightTextBox);
		
		Label lblNewLabel_6 = new Label("Weight");
		flexTable.setWidget(6, 0, lblNewLabel_6);
		
		WeightTextBox = new TextBox();
		flexTable.setWidget(6, 1, WeightTextBox);
		
		//rootPanel.add(flexTable);
		
		SaveButton = new Button("New button");
		SaveButton.setText("Register");

		flexTable.setWidget(7, 1, SaveButton);
		flexTable.getCellFormatter().setHorizontalAlignment(7, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		//rootPanel.add(flexTable);
	    dialogBox = new DialogBox();
		dialogBox.setText("Registration");
		dialogBox.setAnimationEnabled(true);
		
		dialogBox.center();
		
		
		dialogBox.setWidget(flexTable);
		
		SaveButton.addClickHandler( new ClickHandler(){
			
			public void onClick(ClickEvent event){
				//dialogBox.hide();
				//Window.Location.assign("landing.html");
				
				SaveButton.setEnabled(false);
				
				Bridge registreNewysers = new Bridge();
				registreNewysers.firstName = FirstNameTextBox.getText();
				registreNewysers.lastName = LastNameTextBox.getText();
				registreNewysers.email = EmailAddressTextBox.getText();
				registreNewysers.gender = comboBox.getItemText(comboBox.getSelectedIndex());
				registreNewysers.height = Double.valueOf(HeightTextBox.getText());
				registreNewysers.weight = Double.valueOf(WeightTextBox.getText());
				registreNewysers.age = integerBox.getValue();
				
				rpc.registerUser(registreNewysers, new AsyncCallback<String>(){

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(String result) {
						
						dialogBox.hide();
						Window.Location.assign("landing.html");
						
					}
					
				});
				
				
			}
			
		});

	}
	
	public void CheckLogin()
	{
		rpc.logIn(GWT.getHostPageBaseURL(), new AsyncCallback<Bridge>(){

			@Override
			public void onFailure(Throwable caught) {
				//WTextBox.setText(caught.getMessage());
				
			}

			@Override
			public void onSuccess(Bridge result) {
				//Window.Location.assign(result.LogginURL);
				//
				
				
				
			}
			
			
		});
	}

}
