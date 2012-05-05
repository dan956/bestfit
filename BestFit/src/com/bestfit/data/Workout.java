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
import java.util.ArrayList;
import java.util.Date;

@PersistenceCapable
public class Workout implements IsSerializable, Serializable {
	@PrimaryKey
    @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
    Long id;
    @Persistent
    private String label;
	@Persistent
	private String email;
	@Persistent
	private Date dateOfWorkout;
    @Persistent
    private ArrayList<String> exerciseItemNames;
    @Persistent
    private ArrayList<Integer> exerciseItemTimes;
    
    @NotPersistent
    private ArrayList<ExerciseItem> exerciseItems;
    @NotPersistent
	private static final long serialVersionUID = -7885309053181899174L;
    
    public Workout() {
    	super();
    }
    
    public Workout(String _email) {
    	this(_email, "DefaultWorkoutLabel", new Date());
    }
    
	public Workout(String _email, String _label, Date _dateOfWorkout) {
		email = _email;
		label = _label;
		dateOfWorkout = _dateOfWorkout;
		exerciseItemNames = new ArrayList<String>();
		exerciseItems = new ArrayList<ExerciseItem>();
		exerciseItemTimes = new ArrayList<Integer>();
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
	
	public Date getDateOfWorkout() {
		return dateOfWorkout;
	}
	
	public void setDateOfWorkout(Date _dateOfWorkout) {
		dateOfWorkout = _dateOfWorkout;
	}
	
	public ArrayList<String> getExerciseItemNamesList() {
		return exerciseItemNames;
	}
	
	public void setExerciseItemNamesList(ArrayList<String> _exerciseItemNames) {
		exerciseItemNames = _exerciseItemNames;
	}
	
	/**
	 * @return true if exerciseItem was added as a new item, returns false if exerciseItem
	 * was already in list and only quantity was incremented
	 */
	public boolean addExerciseItem(ExerciseItem _exerciseItem) {
		int index = exerciseItems.indexOf(_exerciseItem);
		if (index < 0) {
			exerciseItems.add(_exerciseItem);
			exerciseItemNames.add(_exerciseItem.getName());
			exerciseItemTimes.add(5);
			return true;
		}
		else {
			exerciseItemTimes.add(index, exerciseItemTimes.remove(index) + 5);
			return false;
		}
	}

	public int indexOfExerciseItem(ExerciseItem _exerciseItem){
		return exerciseItems.indexOf(_exerciseItem);
	}
	
	public int indexOfExerciseItemByName(String _exerciseItemName) {
		return exerciseItemNames.indexOf(_exerciseItemName);
	}
	
	public ExerciseItem removeExerciseItem(int _index) {
		exerciseItemTimes.remove(_index);
		exerciseItemNames.remove(_index);
		return exerciseItems.remove(_index);
	}
	
	public boolean removeExerciseItem(ExerciseItem _exerciseItem) {
		try {
			exerciseItemTimes.remove(exerciseItems.indexOf(_exerciseItem));
		} catch (Exception e) { return false; }
		exerciseItemNames.remove(_exerciseItem.getName());
		return exerciseItems.remove(_exerciseItem);
	}
	
	public ExerciseItem getExerciseItemByName(String _exerciseItemName) {
		for (ExerciseItem item : exerciseItems) {
			if (item.getName().equals(_exerciseItemName))
				return item;
		}
		return null;
	}
	
	public int numExerciseItems() {
		return exerciseItems.size();
	}
	
	public double totalCaloriesBurned() {
		double total = 0;
		for (ExerciseItem item : getExerciseItems())
			total += ((double)(item.getBurnRate30() * getDuration(item))) / (double)30;
		if ((((double)Math.round(total * 10)) / (double)10) == 0)
			System.out.println(" totalCaloriesBurned = 0");
		else
			System.out.println(" totalCaloriesBurned <> 0");
		return ((double)Math.round(total * 10)) / (double)10;
	}
	
	public int getDuration(ExerciseItem _exerciseItem) {
		try {
			return exerciseItemTimes.get(exerciseItems.indexOf(_exerciseItem));
		} catch (IndexOutOfBoundsException e) { return 0; }
	}
	
	public int getDuration(int _index) {
		try {
			return exerciseItemTimes.get(_index);
		} catch (IndexOutOfBoundsException e) { return 0; }
	}
	
	public boolean equals(Object o) {
		if (o == null) return false;
		Workout workout = (Workout)o;
		return email.equals(workout.email) && id.equals(workout.id);
	}
	
	public ArrayList<ExerciseItem> getExerciseItems() {
		return exerciseItems;
	}
	
	public String toString() {
		String str = "[LABEL:\"" + label + "\"[" + dateOfWorkout + "]:(";
		for (ExerciseItem item : getExerciseItems())
			str += "EXERCISE:" + item.toString() + "[" + getDuration(item) + "mins],";
		if (exerciseItems.size() > 0)
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
	
}
