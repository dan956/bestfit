package com.bestfit.data;

import java.util.ArrayList;
import javax.jdo.annotations.*;

import com.google.gwt.user.client.rpc.IsSerializable;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Users {
	
    @PrimaryKey 
	@Persistent
	private String email;
	@Persistent
	private String firstName;
	@Persistent
	private String lastName;
	@Persistent
	private int age;
	@Persistent
	private double height;
	@Persistent
	private String gender;

	
	//private ArrayList<Weight> weights;
	
//	private ArrayList<GoalHistory> goals;
	
	//private ArrayList<DailyHistory> history;
 

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}   
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}   
	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}
   
	public double getHeight() {
		return this.height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
   
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
   
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
