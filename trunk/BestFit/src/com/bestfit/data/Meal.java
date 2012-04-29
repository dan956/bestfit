package com.bestfit.data;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.NotPersistent;
import com.google.gwt.user.client.rpc.IsSerializable;

//import com.google.appengine.api.datastore.Key;
//import com.google.appengine.api.datastore.KeyFactory;

//import org.apache.commons.lang.Validate;
import java.io.Serializable;
import java.util.ArrayList;

@PersistenceCapable
public class Meal implements IsSerializable, Serializable {
    @Persistent
    @PrimaryKey
    private String label;
	@Persistent
	private String email;
    @Persistent
//    private ArrayList<Key> foodItemKeys;
    private ArrayList<String> foodItemNames;
    //private ArrayList<FoodItem> foodItems;
    @NotPersistent
    private ArrayList<FoodItem> foodItems;
    @NotPersistent
	private static final long serialVersionUID = -7885309053181899174L;
    
    public Meal() {
    	this("DefaultEmailAddress", "DefaultMealLabel");
    }
    public Meal(String _email) {
    	this(_email, "DefaultMealLabel");
    }
    
	public Meal(String _email, String _label){
//		Validate.notNull(_email, "email address cannot be null");
//		Validate.notNull(_label, "label cannot be null");
		email = _email;
		label = _label;
//		foodItemKeys = new ArrayList<Key>();
		foodItemNames = new ArrayList<String>();
		foodItems = new ArrayList<FoodItem>();
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
	
	public ArrayList<String> getFoodItemNamesList() {
		return foodItemNames;
	}
	
	public void setFoodItemNamesList(ArrayList<String> _foodItemNames) {
		foodItemNames = _foodItemNames;
	}
	
	public void addFoodItem(FoodItem _foodItem) {
		foodItems.add(_foodItem);
		foodItemNames.add(_foodItem.getName());
//		foodItemKeys.add(KeyFactory.createKey(FoodItem.keyKind, _foodItem.getName()));
	}
	
	public int indexOfFoodItem(FoodItem _foodItem){
		return foodItems.indexOf(_foodItem);
	}
	
	public void addfoodItem(int index, FoodItem _foodItem) {
		foodItems.add(index, _foodItem);
		foodItemNames.add(index, _foodItem.getName());
//		foodItemKeys.add(KeyFactory.createKey(FoodItem.keyKind, _foodItem.getName()));
	}
	
	public FoodItem removeFoodItem(int index) {
//		foodItemKeys.remove(index);
		foodItemNames.remove(index);
		return foodItems.remove(index);
//		return foodItemNames.remove(index);
	}
	
	public boolean removeFoodItem(FoodItem _foodItem) {
//		foodItemKeys.remove(foodItems.indexOf(_foodItem));
		foodItemNames.remove(_foodItem.getName());
//		return foodItems.remove(_foodItem);
		return foodItems.remove(_foodItem);
	}
	
	public FoodItem getFoodItemByName(String _foodItemName) {
		ArrayList<FoodItem> foodItems = getFoodItems();
		for (FoodItem item : foodItems) {
			if (item.getName().equals(_foodItemName))
				return item;
		}
		return new FoodItem();
	}
	
	public int numFoodItems() {
		return foodItems.size();
//		return foodItemNames.size();
	}
	
	public int totalCalories() {
		int total = 0;
		for (FoodItem item : getFoodItems())
			total += item.getCalories();
		return total;
	}
	
	public int totalFatCalories() {
		int total = 0;
		for (FoodItem item : getFoodItems())
			total += item.getFatCalories();
		return total;
	}
	
	public double totalFatGrams() {
		double total = 0;
		for (FoodItem item : getFoodItems())
			total += item.getFatGrams();
		return total;
	}
	
	public double totalCarbohydrates() {
		double total = 0;
		for (FoodItem item : getFoodItems())
			total += item.getCarbohydrates();
		return total;
	}
	
	public boolean equals(Object o) {
		if (o == null) return false;
		Meal meal = (Meal)o;
		return email.equals(meal.email) && label.equals(meal.label) && getFoodItems().equals(meal.getFoodItems());
	}
	
	private ArrayList<FoodItem> getFoodItems() {
		return foodItems;
	}
	
	public String toString() {
		String str = "[LABEL:\"" + label + "\":(";
		for (FoodItem item : getFoodItems())
			str += "ITEM:" + item.toString() + ",";
		if (str.length() <= 11 + label.length())
			str = str.substring(0, str.length() - 1);
		str += ")]";
		return str;
	}
}
