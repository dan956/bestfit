package com.bestfit.client;

import com.bestfit.shared.Users;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RpcServicesAsync {
	public void getUsers(String email, AsyncCallback<Users> callback) throws IllegalArgumentException;;
}
