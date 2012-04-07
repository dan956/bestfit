package cs5103.cloudmonkeys.bestfit.server.jpa;

import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;

/**
 * Entity implementation class for Entity: DailyHistory
 *
 */
//@Entity

public class DailyHistory implements Serializable {

	   
	///@Id
	private long id;
	private Date date;
	private String item;
	private String itemType;
	private int qty;
	private String label;
	private static final long serialVersionUID = 1L;
	
	private ArrayList<ExerciseItem> exercises;
	private ArrayList<Meal> meals;

	public DailyHistory() {
		super();
	}   
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}   
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}   
	public String getItem() {
		return this.item;
	}

	public void setItem(String item) {
		this.item = item;
	}   
	public String getItemType() {
		return this.itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}   
	public int getQty() {
		return this.qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}   
	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
   
}
