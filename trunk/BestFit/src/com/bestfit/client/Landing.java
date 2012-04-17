package com.bestfit.client;
import com.bestfit.shared.Bridge;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class Landing implements EntryPoint {

	private TextBox WeightTextBox;
	private Button SaveButton;
	private TextBox FirstNameTextBox;
	private TextBox LastNameTextBox;
	private TextBox EmailAddressTextBox;
	private DateBox BirthDayDateBox;
	private ListBox comboBox;
	private TextBox HeightTextBox;
	private TextBox WTextBox;
	
	private final RpcServicesAsync rpc = GWT.create(RpcServices.class);
	
	@Override
	public void onModuleLoad() {
		
		//CheckLogin();
		
		RootPanel rootPanel = RootPanel.get("WeightCon");
		
		FlexTable flexTable_1 = new FlexTable();
		flexTable_1.setStyleName("WeightWedg");
		
		
		Label lblYourCurrentWeight = new Label("Your current weight");
		flexTable_1.setWidget(0, 0, lblYourCurrentWeight);
		
		WTextBox = new TextBox();
		flexTable_1.setWidget(1, 0, WTextBox);
		WTextBox.setHeight("26px");
		
		Button btnNewButton = new Button("New button");
		btnNewButton.setText("Update");
		flexTable_1.setWidget(2, 0, btnNewButton);
		btnNewButton.setSize("124px", "25px");
		
		rootPanel.add(flexTable_1);
		flexTable_1.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable_1.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
		
		getCurrentWeight();

	}
	
	public void CheckLogin()
	{
		/*rpc.logIn(GWT.getHostPageBaseURL(), new AsyncCallback<Bridge>(){

			@Override
			public void onFailure(Throwable caught) {
			//	WTextBox.setText(caught.getMessage());
				
			}

			@Override
			public void onSuccess(Bridge result) {
				//Window.Location.assign(result.LogginURL);
				//WTextBox.setText(result.LogginURL);
				
			}
			
			
		});*/
	}
	
	public void getCurrentWeight()
	{
		rpc.getCurrentWeight("alrowaithy@gmail.com",new AsyncCallback<String>(){

			@Override
			public void onFailure(Throwable caught) {
				WTextBox.setText(caught.getMessage());
				
			}

			@Override
			public void onSuccess(String result) {
				
				WTextBox.setText(result);
				
			}
			
			
		});
	}
	
	
}
