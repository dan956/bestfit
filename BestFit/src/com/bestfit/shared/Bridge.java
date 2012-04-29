package com.bestfit.shared;

import java.util.ArrayList;
import java.util.Date;

import javax.jdo.annotations.Persistent;

import com.bestfit.data.FoodItem;
import com.bestfit.data.Meal;
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
	
	public ArrayList<Meal> meals;
	public Meal meal;
	
	public ArrayList<FoodItem> foods;
	public FoodItem foodItem;
	
	
	public Date startDate;
	public Date targetDate;
	public double targetWeight;   

}
