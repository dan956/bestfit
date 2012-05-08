package com.bestfit.client;

import java.util.Date;
import com.bestfit.shared.Bridge;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
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
	private Button btnNewButton;
	private DateBox dateBox;

	private final RpcServicesAsync rpc = GWT.create(RpcServices.class);
	
	public void onModuleLoad() {

		getCurrentWeight();
		setPageHeader();
	}
	
	private void setPageHeader() {
		RootPanel rpanel = RootPanel.get("goalheader");
		
		HTML html = new HTML("<h3><b><font color=\"#308A4D\">Set up your new goal!</font></b></h3>");
		
		rpanel.add(html);
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
				RootPanel rootPanel = RootPanel.get("goalCont");				
				
				
				FlexTable flexTable = new FlexTable();
				rootPanel.add(flexTable, 0, 0);
				flexTable.setSize("398px", "259px");
												
				lblNewLabel = new Label("Current Weight:");
				flexTable.setWidget(0, 0, lblNewLabel);
				
				textBox = new TextBox();
				textBox.setText(result);
				textBox.setReadOnly(true);
				flexTable.setWidget(0, 1, textBox);
				textBox.setSize("155px", "25px");
				
				lblNewLabel_1 = new Label("Target Weight:");
				flexTable.setWidget(1, 0, lblNewLabel_1);
				
				textBox_1 = new TextBox();
				flexTable.setWidget(1, 1, textBox_1);
				textBox_1.setSize("155px", "25px");
				
				lblNewLabel_2 = new Label("Target Date: (MM-DD-YYYY)");
				flexTable.setWidget(2, 0, lblNewLabel_2);
				
				dateBox = new DateBox();
				dateBox.setFormat(new DefaultFormat(DateTimeFormat.getFormat("MM-dd-yyyy")));
				dateBox.getDatePicker().setStyleName("gwt-DatePicker");
				
				flexTable.setWidget(2, 1, dateBox);
				dateBox.setSize("150px", "25px");
				
				lblNewLabel_3 = new Label("Daily net calories to achieve goal:");
				flexTable.setWidget(3, 0, lblNewLabel_3);
				
				textBox_3 = new TextBox();
				textBox_3.setReadOnly(true);
				flexTable.setWidget(3, 1, textBox_3);
				textBox_3.setSize("155px", "25px");
				flexTable.setWidget(4, 1,new HTML("</br>"));
				btnNewButton_1 = new Button("New button");
				flexTable.setWidget(5, 1, btnNewButton_1);
				btnNewButton_1.setSize("80px", "31px");
				btnNewButton_1.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						
						rpc.getBMR(new AsyncCallback<Double>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void onSuccess(Double result) {
								
							Date today = new Date();

								long diff =   dateBox.getValue().getTime() - today.getTime();
								
								diff /=  (1000 * 60 * 60 * 24);								
								
								// CalsPerDay = BMR + 3500 * (Target_Weight - Current_Weight) / Days_Remaining
								double CalsPerDay = result + 3500 * (Double.valueOf(textBox_1.getText())- Double.valueOf(textBox.getText()))/diff;
								
								textBox_3.setText( (int)CalsPerDay+"");								
								
							}
						});

					}
				});
				btnNewButton_1.setText("Calculate");
				flexTable.getCellFormatter().setHorizontalAlignment(4, 1, HasHorizontalAlignment.ALIGN_RIGHT);
				flexTable.getCellFormatter().setHorizontalAlignment(5, 1, HasHorizontalAlignment.ALIGN_RIGHT);
				flexTable.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT);
				flexTable.getCellFormatter().setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_RIGHT);
				flexTable.getCellFormatter().setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_RIGHT);
				flexTable.getCellFormatter().setHorizontalAlignment(3, 1, HasHorizontalAlignment.ALIGN_RIGHT);
				
				btnNewButton = new Button("New button");
				btnNewButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						
						btnNewButton.setEnabled(false);
						
						Date currentTime = new Date(); 
						
						Bridge msg = new Bridge();
						msg.targetWeight = Double.valueOf(textBox_1.getValue());
						msg.startDate = currentTime;
						msg.targetDate = dateBox.getValue();
						
						rpc.storeGoal(msg, new AsyncCallback<String>() {
							
							@Override
							public void onSuccess(String result) {
								Window.Location.assign("/landing.html");
								
							}
							
							@Override
							public void onFailure(Throwable caught) {
								System.out.print(caught.getMessage());
								
							}
						});
						
					}
				});
				btnNewButton.setText("Save");
				flexTable.setWidget(5, 2, btnNewButton);
				btnNewButton.setSize("80px", "31px");
				flexTable.getCellFormatter().setHorizontalAlignment(5, 2, HasHorizontalAlignment.ALIGN_LEFT);
				
			}
			
			
		});
	}
}
