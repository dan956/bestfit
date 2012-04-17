package com.bestfit.client;

import com.bestfit.shared.Bridge;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RpcServicesAsync {
	void getUsers(String email, AsyncCallback<Bridge> callback) throws IllegalArgumentException;
	void saveUsers(String email, AsyncCallback<Bridge> callback);
	void logIn(String url, AsyncCallback<Bridge> callback);
	void registerUser(Bridge msg, AsyncCallback<String> callback);
	void getCurrentWeight(AsyncCallback<String> callback);
	void isNewUser(AsyncCallback<Boolean> callback);
	void getEmail(AsyncCallback<String> callback);
}
