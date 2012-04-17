package com.bestfit.shared;

import javax.jdo.annotations.Persistent;

import com.google.gwt.user.client.rpc.IsSerializable;


public class Bridge implements IsSerializable {
	public String email;
	public String firstName;
	public String lastName;
	public int age;
	public double height;
	public double weight;	
	public String gender;
	
	public boolean IsloggedIN;
	public String LogginURL;
	public String LogoutURL;
}
