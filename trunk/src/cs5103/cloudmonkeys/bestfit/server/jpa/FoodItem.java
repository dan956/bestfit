package cs5103.cloudmonkeys.bestfit.server.jpa;



import java.io.Serializable;

/**
 * Entity implementation class for Entity: FoodItem
 *
 */
//@Entity

public class FoodItem implements Serializable {

	   
	//@Id
	private long id;
	private String name;
	private double calories;
	private int protein;
	private int fat;
	private int carbs;
	private static final long serialVersionUID = 1L;
	
	public FoodItem() {
		super();
	}   
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}   
	public double getCalories() {
		return this.calories;
	}

	public void setCalories(double calories) {
		this.calories = calories;
	}   
	public int getProtein() {
		return this.protein;
	}

	public void setProtein(int protein) {
		this.protein = protein;
	}   
	public int getFat() {
		return this.fat;
	}

	public void setFat(int fat) {
		this.fat = fat;
	}   
	public int getCarbs() {
		return this.carbs;
	}

	public void setCarbs(int carbs) {
		this.carbs = carbs;
	}
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
