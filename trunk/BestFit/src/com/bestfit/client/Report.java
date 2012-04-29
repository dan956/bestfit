package com.bestfit.client;

import com.bestfit.shared.Bridge;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.core.client.GWT;

public class Report implements EntryPoint {

	private final RpcServicesAsync rpc = GWT.create(RpcServices.class);
	
	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get();
		FlexTable flexTable = new FlexTable();
		rootPanel.add(flexTable);
		
		rpc.getGoalHistory(new AsyncCallback<Bridge>() {
			
			@Override
			public void onSuccess(Bridge result) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.err.println(caught.getMessage());
				
			}
		});
	}

}
