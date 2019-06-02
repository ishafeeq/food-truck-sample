package com.truck.food.pojo;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TruckQueryResponse extends BaseResponse {

	private List<Truck> trucks;
	private Map<String, List<Truck>> locationMap;
	private Integer numberOfResults;
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

	/**
	 * @return the locationMap
	 */
	public Map<String, List<Truck>> getLocationMap() {
		return locationMap;
	}

	/**
	 * @param locationMap the locationMap to set
	 */
	public void setLocationMap(Map<String, List<Truck>> locationMap) {
		this.locationMap = locationMap;
	}

	/**
	 * @return the numberOfResults
	 */
	public Integer getNumberOfResults() {
		return numberOfResults;
	}

	/**
	 * @param numberOfResults the numberOfResults to set
	 */
	public void setNumberOfResults(Integer numberOfResults) {
		this.numberOfResults = numberOfResults;
	}
	
}
