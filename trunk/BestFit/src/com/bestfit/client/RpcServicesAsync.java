package com.bestfit.client;

import com.bestfit.shared.BridgeUsers;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RpcServicesAsync {
	void getUsers(String email, AsyncCallback<BridgeUsers> callback) throws IllegalArgumentException;
	void saveUsers(String email, AsyncCallback<BridgeUsers> callback);;
}
