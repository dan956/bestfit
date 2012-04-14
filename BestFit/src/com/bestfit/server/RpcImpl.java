package com.bestfit.server;

import com.bestfit.client.*;
import com.bestfit.shared.Users;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class RpcImpl extends RemoteServiceServlet implements RpcServices  {

	@Override
	public Users getUsers(String email) throws IllegalArgumentException {
		Users _u = new Users();
		_u.LastName = "sss";
		_u.Name = "ggg";
		
		return _u;
	}

}
