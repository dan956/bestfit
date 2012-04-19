package com.bestfit.data;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

//import org.apache.commons.lang.Validate;
import java.io.Serializable;
import java.util.ArrayList;

@PersistenceCapable
public class Meal implements Serializable {
	private static final long serialVersionUID = 1562994264835844109L;
	@PrimaryKey
	private String email;
    @Persistent
    private String label;
    @Persistent
    private ArrayList<FoodItem> foodItems;
    
    public Meal() {
		email = "DefaultEmailAddress";
		label = "DefaultMealLabel";
    	foodItems = new ArrayList<FoodItem>();
    }
    public Meal(String _email) {
    	this(_email, "DefaultMealLabel", new ArrayList<FoodItem>());
    }
    
	public Meal(String _email, String _label){
		this(_email, _label, new ArrayList<FoodItem>());
	}
	
	public Meal(String _email, String _label, ArrayList<FoodItem> _foodItems) {
//		Validate.notNull(_email, "email address cannot be null");
//		Validate.notNull(_label, "meal label cannot be null");
//		Validate.notNull(_foodItems, "FoodItems ArrayList cannot be null");
		email = _email;
		label = _label;
		foodItems = _foodItems;
	}
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String _email) {
//		Validate.notNull(_email, "email address cannot be null");
		email = _email;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String _label) {
//		Validate.notNull(_label, "meal label cannot be null");
		label = _label;
	}
	
	public void addFoodItem(FoodItem _foodItem) {
		foodItems.add(_foodItem);
	}
	
	public void addfoodItem(FoodItem _foodItem, int index) {
		foodItems.add(index, _foodItem);
	}
	
	public FoodItem removeFoodItem(int index) {
		return foodItems.remove(index);
	}
	
	public boolean removeFoodItem(FoodItem _foodItem) {
		return foodItems.remove(_foodItem);
	}
	
	public int numFoodItems() {
		return foodItems.size();
	}
	
	public int totalCalories() {
		int total = 0;
		for (FoodItem item : foodItems)
			total += item.getCalories();
		return total;
	}
	
	public int totalFatCalories() {
		int total = 0;
		for (FoodItem item : foodItems)
			total += item.getFatCalories();
		return total;
	}
	
	public double totalFatGrams() {
		double total = 0;
		for (FoodItem item : foodItems)
			total += item.getFatGrams();
		return total;
	}
	
	public double totalCarbohydrates() {
		double total = 0;
		for (FoodItem item : foodItems)
			total += item.getCarbohydrates();
		return total;
	}
	
	public boolean equals(Object o) {
		if (o == null) return false;
		Meal meal = (Meal)o;
		return email.equals(meal.email) && label.equals(meal.label) && foodItems.equals(meal.foodItems);
	}
}
