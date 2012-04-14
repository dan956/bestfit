package com.bestfit.client;


import com.bestfit.shared.BridgeUsers;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("services")
public interface RpcServices extends RemoteService {
	BridgeUsers getUsers(String email) throws IllegalArgumentException; 

}
