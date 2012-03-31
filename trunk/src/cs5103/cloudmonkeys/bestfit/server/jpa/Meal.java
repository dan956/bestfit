package cs5103.cloudmonkeys.bestfit.server.jpa;



import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;
import java.util.*;

/**
 * Entity implementation class for Entity: Meal
 *
 */
@Entity

public class Meal implements Serializable {

	@Id
	private long id;
	private String label;
	private ArrayList<FoodItem> foods;
	private static final long serialVersionUID = 1L;

	public Meal() {
		super();
	}   
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}   
	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}   
	public ArrayList<FoodItem> getFoods() {
		return this.foods;
	}

	public void setFoods(ArrayList<FoodItem> foods) {
		this.foods = foods;
	}
   
}
