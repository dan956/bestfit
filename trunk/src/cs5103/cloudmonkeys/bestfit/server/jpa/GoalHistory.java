package cs5103.cloudmonkeys.bestfit.server.jpa;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;
import java.util.Date;

/**
 * Entity implementation class for Entity: GoalHistory
 *
 */
@Entity

public class GoalHistory implements Serializable {

	
	@Id
	private long id;
	private String email;
	private Date startDate;
	private Date targetDate;
	private double targetWeight;   
	private static final long serialVersionUID = 1L;

	public GoalHistory() {
		super();
	}   
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
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
