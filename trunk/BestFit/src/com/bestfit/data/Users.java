package com.bestfit.data;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable
public class Users {
	
    @PrimaryKey
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

	public Users(String _email,String _firstName,String _lastName,int _age,double _height,String _gender){
		
		email= _email;
		firstName= _firstName;
		lastName= _lastName;
		age= _age;
		height= _height;
		gender= _gender;
	}
	

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
