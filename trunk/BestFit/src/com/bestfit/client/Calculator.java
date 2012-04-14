package com.bestfit.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RootPanel;

public class Calculator implements EntryPoint {

	
	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get();
		FlexTable flexTable = new FlexTable();
		rootPanel.add(flexTable);

	}

}
