package com.truck.food.ftenum;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum FacilityType {

	TRUCK("Truck"), PUSH_CART("Push Cart");
	
	private String fType;
	private static final Map<String, FacilityType> revMap = new HashMap<>();
	static {
		for(FacilityType fTyp : EnumSet.allOf(FacilityType.class)) {
			revMap.put(fTyp.getfType(), fTyp);
		}
	}
	
	private FacilityType(String fType) {
		this.fType = fType;
	}

	/**
	 * @return the fType
	 */
	public String getfType() {
		return fType;
	}
	
	public static FacilityType getFacility(String fType) {
		return revMap.get(fType);
	}

}
