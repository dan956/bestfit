
package com.bestfit.data;

import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

@PersistenceCapable
public class ExerciseItem implements IsSerializable, Serializable {

	@Id
	@GeneratedValue
	long id;
	@Persistent
	private String name;
	@Persistent
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
	
	public boolean equals(Object o) {
		if (o == null) return false;
		ExerciseItem item = (ExerciseItem)o;
		return name.equals(item.name) && burnRate == item.burnRate;
	}

	public String toString() {
		return "[NAME:\"" + name + "\":(BURNRATE:" + burnRate + ")]";
	}
}
