package com.bestfit.data;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;
import java.util.Date;

@PersistenceCapable
public class Weight implements IsSerializable, Serializable{

	@NotPersistent
	private static final long serialVersionUID = 6195847148928854147L;
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private double weight;
	@Persistent
	private Date date;
	@Persistent
	private String email;
	
	public Weight()
	{
		this("", 10, new Date());
	}

	public Weight(String _email, double _weight, Date _currentDate) {
		weight = _weight;
		date = _currentDate;
		email = _email;
	}

	public double getWeight() {
		return this.weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return this.email;
	}

}
