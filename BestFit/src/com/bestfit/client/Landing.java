package com.bestfit.client;
import java.util.Date;

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
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class Landing implements EntryPoint {


	
	private final RpcServicesAsync rpc = GWT.create(RpcServices.class);
	private TextBox textBox;
	
	@Override
	public void onModuleLoad() {
		
		
		getCurrentWeight();

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
				
				DecoratorPanel decoratorPanel = new DecoratorPanel();
				rootPanel.add(decoratorPanel);
				
				FlexTable flexTable = new FlexTable();
				decoratorPanel.setWidget(flexTable);
				
				Label lblNewLabel = new Label("Your current weight");
				flexTable.setWidget(0, 0, lblNewLabel);
				
				textBox = new TextBox();
				textBox.setText(result);
				flexTable.setWidget(1, 0, textBox);
				
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
				flexTable.setWidget(2, 0, btnNewButton);
				flexTable.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_RIGHT);
				
			}
			
			
		});
	}
}
