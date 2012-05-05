package com.bestfit.data;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.gwt.user.client.rpc.IsSerializable;

//import com.google.appengine.api.datastore.Key;
//import com.google.appengine.api.datastore.KeyFactory;

//import org.apache.commons.lang.Validate;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

@PersistenceCapable
public class Meal implements IsSerializable, Serializable {
	@PrimaryKey
    @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
    Long id;
	@Persistent
	private String label;
	@Persistent
	private String email;
	@Persistent
	private ArrayList<String> foodItemNames;
	@Persistent
	private Date dateOfMeal;
	@Persistent
	private ArrayList<Integer> foodItemQuantities;
	
	@NotPersistent
	private ArrayList<FoodItem> foodItems;
	@NotPersistent
	private static final long serialVersionUID = -7885309053181899174L;


	public Meal() {
		this("DefaultEmail", "DefaultMealLabel", new Date());
	}

	public Meal(String _email) {
		this(_email, "DefaultMealLabel", new Date());
	}

	public Meal(String _email, String _label, Date _dateOfMeal) {
		super();
		// Validate.notNull(_email, "email address cannot be null");
		// Validate.notNull(_label, "label cannot be null");
		email = _email;
		label = _label;
		foodItemNames = new ArrayList<String>();
		foodItems = new ArrayList<FoodItem>();
		dateOfMeal = _dateOfMeal;
		foodItemQuantities = new ArrayList<Integer>();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String _email) {
		// Validate.notNull(_email, "email address cannot be null");
		email = _email;
	}

	public Date getDateOfMeal() {
		return dateOfMeal;
	}

	public void setDateOfMeal(Date _dateOfMeal) {
		dateOfMeal = _dateOfMeal;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String _label) {
		// Validate.notNull(_label, "meal label cannot be null");
		label = _label;
	}

	public ArrayList<String> getFoodItemNamesList() {
		return foodItemNames;
	}

	public void setFoodItemNamesList(ArrayList<String> _foodItemNames) {
		foodItemNames = _foodItemNames;
	}

	/**
	 * @return true if foodItem was added as a new item, returns false if foodItem
	 * was already in list and only quantity was incremented
	 */
	public boolean addFoodItem(FoodItem _foodItem) {
		int index = foodItems.indexOf(_foodItem);
		if (index < 0) {
			foodItems.add(_foodItem);
			foodItemNames.add(_foodItem.getName());
			foodItemQuantities.add(new Integer(1));
			return true;
		}
		else {
			foodItemQuantities.add(index, new Integer(foodItemQuantities.remove(index) + 1));
			return false;
		}
	}

	public int indexOfFoodItem(FoodItem _foodItem) {
		return foodItems.indexOf(_foodItem);
	}

	public int indexOfFoodItemByName(String _foodItemName) {
		return foodItemNames.indexOf(_foodItemName);
	}

	public FoodItem removeFoodItem(int _index) {
		foodItemQuantities.remove(_index);
		foodItemNames.remove(_index);
		return foodItems.remove(_index);
	}

	public boolean removeFoodItem(FoodItem _foodItem) {
		try {
			foodItemQuantities.remove(foodItems.indexOf(_foodItem));
		} catch (Exception e) { return false; }
		foodItemNames.remove(_foodItem.getName());
		return foodItems.remove(_foodItem);
	}

	public FoodItem getFoodItemByName(String _foodItemName) {
		for (FoodItem item : foodItems) {
			if (item.getName().equals(_foodItemName))
				return item;
		}
		return null;
	}

	public int numFoodItems() {
		return foodItems.size();
	}
	
	public int getQuantity(FoodItem _foodItem) {
		try {
			return foodItemQuantities.get(foodItems.indexOf(_foodItem));
		} catch (IndexOutOfBoundsException e) { return 0; }
	}
	
	public int getQuantity(int _index) {
		try {
			return foodItemQuantities.get(_index);
		} catch (IndexOutOfBoundsException e) { return 0; }
	}

	public double totalCalories() {
		double total = 0;
		for (FoodItem item : foodItems)
			total += item.getCalories() * getQuantity(item);
		return ((double)Math.round(total * 10)) / 10.0;
	}

	public double totalFatCalories() {
		double total = 0;
		for (FoodItem item : foodItems)
			total += item.getFatCalories() * getQuantity(item);
		return ((double)Math.round(total * 10)) / 10.0;
	}

	public double totalFatGrams() {
		double total = 0;
		for (FoodItem item : foodItems)
			total += item.getFatGrams() * getQuantity(item);
		return ((double)Math.round(total * 10)) / 10.0;
	}

	public double totalCarbohydrates() {
		double total = 0;
		for (FoodItem item : foodItems)
			total += item.getCarbohydrates() * getQuantity(item);
		return ((double)Math.round(total * 10)) / 10.0;
	}
	
	public double totalProtein() {
		double total = 0;
		for (FoodItem item : foodItems)
			total += item.getProtein() * getQuantity(item);
		return ((double)Math.round(total * 10)) / 10.0;
	}

	public boolean equals(Object o) {
		if (o == null)
			return false;
		Meal meal = (Meal) o;
		return email.equals(meal.email) && id.equals(meal.id);
	}

	public ArrayList<FoodItem> getFoodItems() {
		return foodItems;
	}

	public String toString() {
		String str = "[LABEL:\"" + label + "\"[" + dateOfMeal + "]:(";
		for (FoodItem item : foodItems)
			str += "FOOD:" + item.toString() + "[x" + getQuantity(item) + "],";
		if (foodItems.size() > 0)
			str = str.substring(0, str.length() - 1);
		str += ")]";
		return str;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long _id) {
		id = _id;
	}

	public ArrayList<String> getFoodItemNames() {
		return foodItemNames;
	}

	public void setFoodItemNames(ArrayList<String> foodItemNames) {
		this.foodItemNames = foodItemNames;
	}

	public ArrayList<Integer> getFoodItemQuantities() {
		return foodItemQuantities;
	}

	public void setFoodItemQuantities(ArrayList<Integer> foodItemQuantities) {
		this.foodItemQuantities = foodItemQuantities;
	}

	public void setFoodItems(ArrayList<FoodItem> foodItems) {
		this.foodItems = foodItems;
	}

}
