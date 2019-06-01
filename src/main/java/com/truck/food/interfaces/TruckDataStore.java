package com.truck.food.interfaces;

import java.util.List;

import com.truck.food.pojo.Truck;

public interface TruckDataStore {

	public Truck get(String key);
	public List<Truck> multiGet(List<String> key);
	public boolean put(String key, Truck truck);
	public boolean put(String key, Truck truck, Integer expiry);
	public boolean update(String key, Truck truck);
	public List<Truck> queryByName(String[] bin, String value);
	public List<Truck> queryByLocation(String binNameLatLonGeo, String[] bin, Double lat, Double lng, int radius);
	
	public default String getKey(Truck truck) {
		return truck.getLocationId().toString();
	}
	
	
	
	
	
}
