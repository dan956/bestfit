
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
	private String name;
	private double burnRate;

	@NotPersistent
	private static final long serialVersionUID = 3881785407988537441L;

	public ExerciseItem() {
		super();
	}   

	public ExerciseItem(String _name, double _burnRate) {
		name = _name;
		burnRate = _burnRate;
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
	
}
