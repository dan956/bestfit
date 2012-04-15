package com.bestfit.server;
import javax.jdo.Query;
import javax.jdo.PersistenceManager;

import com.bestfit.client.*;
import com.bestfit.data.*;
import com.bestfit.shared.BridgeUsers;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("serial")
public class RpcImpl extends RemoteServiceServlet implements RpcServices  {

	 private static final Logger LOG = Logger.getLogger(RpcImpl.class.getName());
	 private static final PersistenceManagerFactory PMF =
	      JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	  private PersistenceManager getPersistenceManager() {
		    return PMF.getPersistenceManager();
		  }
	 
	
	public BridgeUsers getUsers(String email) throws IllegalArgumentException {
		BridgeUsers tmpBUser = new BridgeUsers();
		
		tmpBUser.LastName = "aaa";
		
		PersistenceManager pm = getPersistenceManager();
		
		List<String> us = new ArrayList<String>();
		
		try {
		 Query q = pm.newQuery(Users.class, "email == u");
		 q.declareParameters("com.bestfit.data.Users u");
		 
		 List<Users> a = (List<Users>)q.execute("alrowaithy@gmail.com");
		 
		 for(Users uu : a)
			 us.add(uu.getFirstName());
		 
		 tmpBUser.Name = us.get(0);
		}finally {
		      pm.close();
		    }
		
		
		
		return tmpBUser;

	}

	
	public BridgeUsers saveUsers(String email) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		BridgeUsers tmpBUser = new BridgeUsers();
		
		tmpBUser.LastName = "aaa";
		
		Users a = new Users("alrowaithy@gmail.com","Mohammad","Alrowaithy",27,345,"Male");
	
		PersistenceManager pm = getPersistenceManager();
		
		try {
		      pm.makePersistent(a);
		    } finally {
		      pm.close();
		    }
		
		return tmpBUser;
	}

}
