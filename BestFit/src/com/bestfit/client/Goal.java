package com.bestfit.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.google.gwt.i18n.client.DateTimeFormat;
import java.util.Date;
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

	
	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get("goalCont");
		FlexTable flexTable = new FlexTable();
		rootPanel.add(flexTable, 0, 0);
		flexTable.setSize("374px", "259px");
		
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
		btnNewButton_1.setSize("76px", "25px");
		btnNewButton_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				DialogBox dialog = new DialogBox();
				dialog.setText("You are saving goal weight of " + textBox_1.getText() + " by " + dateBox.getValue());
				dialog.setModal(false);
				dialog.show();
			}
		});
		btnNewButton_1.setText("Save");
		flexTable.getCellFormatter().setHorizontalAlignment(4, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.getCellFormatter().setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.getCellFormatter().setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.getCellFormatter().setHorizontalAlignment(3, 1, HasHorizontalAlignment.ALIGN_RIGHT);
	}
}
