package com.bestfit.client;


import com.bestfit.shared.Bridge;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("services")
public interface RpcServices extends RemoteService {
	Bridge getUsers(String email) throws IllegalArgumentException;
	Bridge saveUsers(String email) throws IllegalArgumentException;
	Bridge logIn(String url) throws IllegalArgumentException;
	String registerUser(Bridge msg) throws IllegalArgumentException;
	String getCurrentWeight()throws IllegalArgumentException;
	boolean isNewUser() throws IllegalArgumentException;
	String getEmail()throws IllegalArgumentException;
	Bridge getUserProfile()throws IllegalArgumentException; 

}
