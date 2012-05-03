
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
public class ExerciseItem implements IsSerializable, Serializable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	private String name;
	private double burnRate;
	private double time;

	@NotPersistent
	private static final long serialVersionUID = 3881785407988537441L;

	public ExerciseItem() {
		super();
	}   

 
	public double getBurnRate() {
		return burnRate;
	}

	public void setBurnRate(double _burnRate) {
		burnRate = _burnRate;
	}   
	public String getName() {
		return name;
	}

	public void setName(String _name) {
		name = _name;
	}
	
	public double getTime() {
		return time;
	}
   
	public void setTime(double _time) {
		time = _time;
	}
	
	public double getCaloriesBurned() {
		return time * burnRate;
	}
}
