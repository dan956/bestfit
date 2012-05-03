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
public class Workout implements IsSerializable, Serializable {
    @Persistent
    @PrimaryKey
    private String label;
	@Persistent
	private String email;
    @Persistent
    private ArrayList<String> exerciseItemNames;
    @Persistent
    private ArrayList<Integer> exerciseTimes;
    @NotPersistent
    private ArrayList<ExerciseItem> exerciseItems;
    @NotPersistent
	private static final long serialVersionUID = -7885309053181899174L;
    
    public Workout() {
    	super();
    }
    public Workout(String _email) {
    	this(_email, "DefaultWorkoutLabel");
    }
    
	public Workout(String _email, String _label){
		email = _email;
		label = _label;
		exerciseItemNames = new ArrayList<String>();
		exerciseItems = new ArrayList<ExerciseItem>();
		exerciseTimes = new ArrayList<Integer>();
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String _email) {
		email = _email;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String _label) {
		label = _label;
	}
	
	public ArrayList<String> getExerciseItemNamesList() {
		return exerciseItemNames;
	}
	
	public void setExerciseItemNamesList(ArrayList<String> _exerciseItemNames) {
		exerciseItemNames = _exerciseItemNames;
	}
	
	public void addExerciseItem(ExerciseItem _exerciseItem) {
		exerciseItems.add(_exerciseItem);
		exerciseItemNames.add(_exerciseItem.getName());
	}
	
	public int indexOfExerciseItem(ExerciseItem _exerciseItem){
		return exerciseItems.indexOf(_exerciseItem);
	}
	
	public void addexerciseItem(int index, ExerciseItem _exerciseItem) {
		exerciseItems.add(index, _exerciseItem);
		exerciseItemNames.add(index, _exerciseItem.getName());
	}
	
	public ExerciseItem removeExerciseItem(int index) {
		exerciseItemNames.remove(index);
		return exerciseItems.remove(index);
	}
	
	public boolean removeExerciseItem(ExerciseItem _exerciseItem) {
		exerciseItemNames.remove(_exerciseItem.getName());
		return exerciseItems.remove(_exerciseItem);
	}
	
	public ExerciseItem getExerciseItemByName(String _exerciseItemName) {
		ArrayList<ExerciseItem> exerciseItems = getExerciseItems();
		for (ExerciseItem item : exerciseItems) {
			if (item.getName().equals(_exerciseItemName))
				return item;
		}
		return new ExerciseItem();
	}
	
	public int numExerciseItems() {
		return exerciseItems.size();
	}
	
	public double totalCalories() {
		double total = 0;
		for (ExerciseItem item : getExerciseItems())
			total += item.getBurnRate() * exerciseTimes.get(getExerciseItems().indexOf(item));
		return total;
	}
	
	public boolean equals(Object o) {
		if (o == null) return false;
		Workout workout = (Workout)o;
		return email.equals(workout.email) && label.equals(workout.label) && getExerciseItems().equals(workout.getExerciseItems());
	}
	
	public ArrayList<ExerciseItem> getExerciseItems() {
		return exerciseItems;
	}
	
	public String toString() {
		String str = "[LABEL:\"" + label + "\":(";
		for (ExerciseItem item : getExerciseItems())
			str += "ITEM:" + item.toString() + ",";
		if (str.length() <= 11 + label.length())
			str = str.substring(0, str.length() - 1);
		str += ")]";
		return str;
	}
	
	public ArrayList<Integer> getExerciseTimes() {
		return exerciseTimes;
	}
	
	public void setExerciseTimes(ArrayList<Integer> exercisetimes) {
		this.exerciseTimes = exercisetimes;
	}
}
