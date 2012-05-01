package com.bestfit.client;

import java.util.Date;

import com.bestfit.shared.Bridge;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RpcServicesAsync {
	void registerUser(Bridge msg, AsyncCallback<String> callback);
	void getCurrentWeight(AsyncCallback<String> callback);
	void isNewUser(AsyncCallback<Boolean> callback);
	void getEmail(AsyncCallback<String> callback);
	void getUserProfile(AsyncCallback<Bridge> callback);
	void getUserMeals(AsyncCallback<Bridge> callback);
	void saveUserMeal(Bridge msg, AsyncCallback<Boolean> callback);
	void getFoodItems(AsyncCallback<Bridge> callback);
	void saveFoodItem(Bridge msg, AsyncCallback<Boolean> callback);
	void getBMR(AsyncCallback<Double> callback);
	void storeGoal(Bridge msg, AsyncCallback<String> callback);
	void getGoalHistory(AsyncCallback<Bridge> callback);
	void storeNewWeight(Double weight, Date currentDate,
			AsyncCallback<String> callback);
	void getWeightHistory(AsyncCallback<Bridge> callback);
}
