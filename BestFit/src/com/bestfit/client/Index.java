package com.bestfit.client;

import java.util.Date;

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
import com.google.gwt.user.client.ui.IntegerBox;

public class Index implements EntryPoint {

	private Button SaveButton;
	private TextBox WeightTextBox;
	private TextBox FirstNameTextBox;
	private TextBox LastNameTextBox;
	private TextBox EmailAddressTextBox;
	private ListBox comboBox;
	private TextBox HeightTextBox;
	private IntegerBox integerBox;
	private FlexTable flexTable;

	private final RpcServicesAsync rpc = GWT.create(RpcServices.class);

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void onModuleLoad() {
		CheckUser();
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public void CheckUser() {
		rpc.isNewUser(new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				// WTextBox.setText(caught.getMessage());

			}

			@Override
			public void onSuccess(Boolean result) {
				if (result) {
					rpc.getEmail(new AsyncCallback<String>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onSuccess(String result) {

							flexTable = new FlexTable();

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
							EmailAddressTextBox.setText(result);

							Label lblNewLabel_3 = new Label("Age");
							flexTable.setWidget(3, 0, lblNewLabel_3);

							integerBox = new IntegerBox();
							flexTable.setWidget(3, 1, integerBox);
							integerBox.setHeight("25px");

							Label lblNewLabel_4 = new Label("Gender");
							flexTable.setWidget(4, 0, lblNewLabel_4);

							comboBox = new ListBox();
							comboBox.addItem("Male");
							comboBox.addItem("Female");
							flexTable.setWidget(4, 1, comboBox);
							comboBox.setHeight("25px");

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

							SaveButton = new Button("New button");
							SaveButton.setText("Register");
							SaveButton.addClickHandler(new ClickHandler() {

								public void onClick(ClickEvent event) {

									SaveButton.setEnabled(false);

									Bridge registreNewUsers = new Bridge();
									registreNewUsers.firstName = FirstNameTextBox.getText();
									registreNewUsers.lastName = LastNameTextBox.getText();
									registreNewUsers.email = EmailAddressTextBox.getText();
									registreNewUsers.gender = comboBox.getItemText(comboBox
											.getSelectedIndex());
									registreNewUsers.height = Double.valueOf(HeightTextBox
											.getText());
									registreNewUsers.weight = Double.valueOf(WeightTextBox
											.getText());
									registreNewUsers.age = integerBox.getValue();

									rpc.registerUser(registreNewUsers, new AsyncCallback<String>() {

										@Override
										public void onFailure(Throwable caught) {
											// TODO Auto-generated method stub

										}

										@Override
										public void onSuccess(String result) {

											rpc.storeNewWeight(Double.valueOf(WeightTextBox.getText()), new Date(), new AsyncCallback<String>() {

												@Override
												public void onFailure(Throwable caught) {
													// TODO Auto-generated method stub
													
												}

												@Override
												public void onSuccess(String result) {
													
													Window.Location.assign("/landing.html");
												}
											} );
											
											
											
										}
									});
								}
							});

							flexTable.setWidget(7, 1, SaveButton);
							SaveButton.setSize("91px", "25px");
							flexTable.getCellFormatter().setHorizontalAlignment(7, 1,
									HasHorizontalAlignment.ALIGN_RIGHT);

							flexTable.setSize("245px", "290px");
							flexTable.getCellFormatter().setHorizontalAlignment(0, 1,
									HasHorizontalAlignment.ALIGN_RIGHT);
							flexTable.getCellFormatter().setHorizontalAlignment(1, 1,
									HasHorizontalAlignment.ALIGN_RIGHT);
							flexTable.getCellFormatter().setHorizontalAlignment(2, 1,
									HasHorizontalAlignment.ALIGN_RIGHT);
							flexTable.getCellFormatter().setHorizontalAlignment(3, 1,
									HasHorizontalAlignment.ALIGN_RIGHT);
							flexTable.getCellFormatter().setHorizontalAlignment(4, 1,
									HasHorizontalAlignment.ALIGN_RIGHT);
							flexTable.getCellFormatter().setHorizontalAlignment(5, 1,
									HasHorizontalAlignment.ALIGN_RIGHT);
							flexTable.getCellFormatter().setHorizontalAlignment(6, 1,
									HasHorizontalAlignment.ALIGN_RIGHT);

							RootPanel.get("regCont").add(flexTable);
							

						}

					});

				} else {
					Window.Location.assign("/landing.html");
				}

			}
		});
	}
}
