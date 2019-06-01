package com.truck.food.pojo;

import java.math.BigDecimal;

public class FoodItem {

	private String foodName;
	private BigDecimal foodPrice;
	/**
	 * @return the foodName
	 */
	public String getFoodName() {
		return foodName;
	}
	/**
	 * @param foodName the foodName to set
	 */
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	/**
	 * @return the foodPrice
	 */
	public BigDecimal getFoodPrice() {
		return foodPrice;
	}
	/**
	 * @param foodPrice the foodPrice to set
	 */
	public void setFoodPrice(BigDecimal foodPrice) {
		this.foodPrice = foodPrice;
	}
	
	
}
