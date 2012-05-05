package com.bestfit.data;

//import javax.jdo.annotations.Extension;
import java.io.Serializable;

import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.gwt.user.client.rpc.IsSerializable;

//import com.google.appengine.api.datastore.Key;
//import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable
public class FoodItem implements IsSerializable, Serializable {
	@Persistent
	@PrimaryKey
	String name;
	@Persistent
	double calories;
	@Persistent
	double fatCalories;
	@Persistent
	double fatGrams;
	@Persistent
	double carbohydrates;
	@Persistent
	double protein;
	@NotPersistent
	private static final long serialVersionUID = 7072184204766644798L;
	
	public FoodItem() {
		this("DefaultFoodItemName", 0, 0, 0, 0, 0);
	}
	
	public FoodItem(String _name, int _calories, int _fatCalories, double _fatGrams, double _carbohydrates, double _protein) {
//		Validate.notNull(_name, "FoodItem name cannot be null.");
//		Validate.isTrue(_calories >= 0, "calories cannot be negative");
//		Validate.isTrue(_fatCalories >= 0, "fat calories cannot be negative");
//		Validate.isTrue(_fatGrams >= 0, "fat grams cannot be negative");
//		Validate.isTrue(_carbohydrates >= 0, "carbohydrates cannot be negative ");
//		Validate.isTrue(_protein >= 0, "protein cannot be negative ");
		name = _name;
		calories = _calories;
		fatCalories = _fatCalories;
		fatGrams = _fatGrams;
		carbohydrates = _carbohydrates;
		protein = _protein;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String _name) {
//		Validate.notNull(_name, "FoodItem name cannot be null.");
//		key = KeyFactory.createKey(keyKind, _name);
		name = _name;
	}
	
	public double getCalories() {
		return calories;
	}
	
	public void setCalories(int _calories) {
//		Validate.isTrue(_calories >= 0, "calories cannot be negative");
		calories = _calories;
	}
	
	public double getFatCalories() {
		return fatCalories;
	}
	
	public void setFatCalories(int _fatCalories) {
//		Validate.isTrue(_fatCalories >= 0, "fat calories cannot be negative");
		fatCalories = _fatCalories;
	}
	
	public double getFatGrams() {
		return fatGrams;
	}
	
	public void setFatGrams(double _fatGrams) {
//		Validate.isTrue(_fatGrams >= 0, "fat grams cannot be negative");
		fatGrams = _fatGrams;
	}
	
	public double getCarbohydrates() {
		return carbohydrates;
	}
	
	public void setCarbohydrates(double _carbohydrates) {
//		Validate.isTrue(_carbohydrates >= 0, "carbohydrates cannot be negative ");
		carbohydrates = _carbohydrates;
	}
	
	public double getProtein() {
		return protein;
	}
	
	public void setProtein(double _protein) {
//		Validate.isTrue(_protein >= 0, "protein cannot be negative");
		protein = _protein;
	}
	
	public boolean equals(Object o) {
		if (o == null) return false;
		FoodItem item = (FoodItem)o;
		return name.equals(item.name);
	}

	public String toString() {
		return "[NAME:\"" + name + "\":(CALS:" + calories + ",FATCALS:" + fatCalories + ",FATGRAMS:" + fatGrams + ",CARBS:" + carbohydrates + ",PROTEIN:" + protein + ")]";
	}
}
