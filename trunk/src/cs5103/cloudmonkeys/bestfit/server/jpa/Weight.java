package cs5103.cloudmonkeys.bestfit.server.jpa;



import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * Entity implementation class for Entity: Weight
 *
 */
@Entity

public class Weight implements Serializable {

	   
	@Id
	private long id;
	private double weight;
	private Date date;
	private static final long serialVersionUID = 1L;

	public Weight() {
		super();
	}   
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
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
   
}
