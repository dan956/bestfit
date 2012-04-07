package cs5103.cloudmonkeys.bestfit.server.jpa;



import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;

/**
 * Entity implementation class for Entity: User
 *
 */
//@Entity

public class User implements Serializable {

	//@Id
	private long id;
	private String email;
	private String firstName;
	private String lastName;
	private int age;
	private double height;
	private String gender;
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Weight> weights;
	
	private ArrayList<GoalHistory> goals;
	
	private ArrayList<DailyHistory> history;

	public User() {
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
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}   
	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}
   
	public double getHeight() {
		return this.height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
   
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
   
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
   
}
