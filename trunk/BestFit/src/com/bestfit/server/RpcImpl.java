package com.bestfit.server;

import javax.jdo.PersistenceManager;

import com.bestfit.client.*;
import com.bestfit.data.*;
import com.bestfit.shared.BridgeUsers;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class RpcImpl extends RemoteServiceServlet implements RpcServices  {

	@Override
	public BridgeUsers getUsers(String email) throws IllegalArgumentException {
		
		BridgeUsers tmpBUser = new BridgeUsers();
		tmpBUser.LastName = "aaa";
		
		Users a = new Users();
		a.setFirstName("Mohaa");
		a.setAge(22);
		a.setLastName("sss");
		a.setEmail("alrowaithy@gmail.com");
		
		//PersistenceManager pm = PMF.get().getPersistenceManager();
		//pm.makePersistent(a);
		//pm.close();
		
		return tmpBUser;
	}

}
