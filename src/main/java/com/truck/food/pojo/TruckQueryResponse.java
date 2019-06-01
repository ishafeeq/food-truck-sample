package com.truck.food.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TruckQueryResponse extends BaseResponse {

	private List<Truck> trucks;

	/**
	 * @return the trucks
	 */
	public List<Truck> getTrucks() {
		return trucks;
	}

	/**
	 * @param trucks the trucks to set
	 */
	public void setTrucks(List<Truck> trucks) {
		this.trucks = trucks;
	}
	
}
