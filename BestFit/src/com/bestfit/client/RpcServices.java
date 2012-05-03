package com.bestfit.client;


import java.util.Date;

import com.bestfit.shared.Bridge;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("services")
public interface RpcServices extends RemoteService {
	String registerUser(Bridge msg) throws IllegalArgumentException;
	String getCurrentWeight()throws IllegalArgumentException;
	boolean isNewUser() throws IllegalArgumentException;
	String getEmail()throws IllegalArgumentException;
	Bridge getUserProfile()throws IllegalArgumentException; 
	Bridge getUserMeals() throws IllegalArgumentException;
	boolean saveUserMeal(Bridge msg) throws IllegalArgumentException;
	Bridge getFoodItems() throws IllegalArgumentException;
	boolean saveFoodItem(Bridge msg) throws IllegalArgumentException;
	double getBMR() throws IllegalArgumentException;
	String storeGoal(Bridge msg)throws IllegalArgumentException;
	Bridge getGoalHistory()throws IllegalArgumentException;
	String storeNewWeight(Double weight,Date currentDate) throws IllegalArgumentException;
	Bridge getWeightHistory()throws IllegalArgumentException;
	Bridge getUserWorkouts() throws IllegalArgumentException;
	boolean saveUserWorkout(Bridge msg) throws IllegalArgumentException;
	Bridge getExerciseItems() throws IllegalArgumentException;
	boolean saveExerciseItem(Bridge msg) throws IllegalArgumentException;
}
