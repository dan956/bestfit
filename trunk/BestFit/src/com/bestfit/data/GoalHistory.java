package com.bestfit.data;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import java.lang.String;
import java.util.Date;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class GoalHistory {

	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private String email;
	@Persistent
	private Date startDate;
	@Persistent
	private Date targetDate;
	@Persistent
	private double targetWeight;   

	public GoalHistory(String _email,Date _startDate, Date _targetDate,double _targetWeight) {
		
		email=_email;
		startDate=_startDate;
		targetDate=_targetDate;
		targetWeight=_targetWeight;
		
	}   

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}   
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}   
	public Date getTargetDate() {
		return this.targetDate;
	}

	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}   
	public double getTargetWeight() {
		return this.targetWeight;
	}

	public void setTargetWeight(double targetWeight) {
		this.targetWeight = targetWeight;
	}   
   
}
