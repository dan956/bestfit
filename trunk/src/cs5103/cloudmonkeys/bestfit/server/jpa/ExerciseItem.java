package cs5103.cloudmonkeys.bestfit.server.jpa;



import java.io.Serializable;
import java.lang.String;

/**
 * Entity implementation class for Entity: ExerciseItem
 *
 */
//@Entity

public class ExerciseItem implements Serializable {

	   
	//@Id
	private long id;
	private String name;
	private double burnRate;
	private static final long serialVersionUID = 1L;

	public ExerciseItem() {
		super();
	}   
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}   
	public double getBurnRate() {
		return this.burnRate;
	}

	public void setBurnRate(double burnRate) {
		this.burnRate = burnRate;
	}   
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
   
}
