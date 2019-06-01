package com.truck.food.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.truck.food.ftenum.FacilityType;
import com.truck.food.ftenum.LicenseStatus;

@JsonInclude(Include.NON_NULL)
public class Truck {

	private String truckId;
	private Long locationId;
	private String apllicantName;
	private FacilityType facilityType;
	private LicenseStatus status;
	private List<FoodItem> foodItems;
	
	private Double xCord;
	private Double yCord;
	
	private Double latitude;
	private Double longitude;
	
	private Long nOISent;
	private Long approved;
	private Long received;
	private Long priorPermit;
	private Long expirationDate;
	/**
	 * @return the truckId
	 */
	public String getTruckId() {
		return truckId;
	}
	/**
	 * @param truckId the truckId to set
	 */
	public void setTruckId(String truckId) {
		this.truckId = truckId;
	}
	/**
	 * @return the locationId
	 */
	public Long getLocationId() {
		return locationId;
	}
	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	/**
	 * @return the apllicantName
	 */
	public String getApllicantName() {
		return apllicantName;
	}
	/**
	 * @param apllicantName the apllicantName to set
	 */
	public void setApllicantName(String apllicantName) {
		this.apllicantName = apllicantName;
	}
	/**
	 * @return the facilityType
	 */
	public FacilityType getFacilityType() {
		return facilityType;
	}
	/**
	 * @param facilityType the facilityType to set
	 */
	public void setFacilityType(FacilityType facilityType) {
		this.facilityType = facilityType;
	}
	/**
	 * @return the status
	 */
	public LicenseStatus getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(LicenseStatus status) {
		this.status = status;
	}
	/**
	 * @return the foodItems
	 */
	public List<FoodItem> getFoodItems() {
		return foodItems;
	}
	/**
	 * @param foodItems the foodItems to set
	 */
	public void setFoodItems(List<FoodItem> foodItems) {
		this.foodItems = foodItems;
	}
	
	/**
	 * @return the xCord
	 */
	public Double getxCord() {
		return xCord;
	}
	/**
	 * @param xCord the xCord to set
	 */
	public void setxCord(Double xCord) {
		this.xCord = xCord;
	}
	/**
	 * @return the yCord
	 */
	public Double getyCord() {
		return yCord;
	}
	/**
	 * @param yCord the yCord to set
	 */
	public void setyCord(Double yCord) {
		this.yCord = yCord;
	}
	/**
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the nOISent
	 */
	public Long getnOISent() {
		return nOISent;
	}
	/**
	 * @param nOISent the nOISent to set
	 */
	public void setnOISent(Long nOISent) {
		this.nOISent = nOISent;
	}
	/**
	 * @return the approved
	 */
	public Long getApproved() {
		return approved;
	}
	/**
	 * @param approved the approved to set
	 */
	public void setApproved(Long approved) {
		this.approved = approved;
	}
	/**
	 * @return the received
	 */
	public Long getReceived() {
		return received;
	}
	/**
	 * @param received the received to set
	 */
	public void setReceived(Long received) {
		this.received = received;
	}
	/**
	 * @return the priorPermit
	 */
	public Long getPriorPermit() {
		return priorPermit;
	}
	/**
	 * @param priorPermit the priorPermit to set
	 */
	public void setPriorPermit(Long priorPermit) {
		this.priorPermit = priorPermit;
	}
	/**
	 * @return the expirationDate
	 */
	public Long getExpirationDate() {
		return expirationDate;
	}
	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(Long expirationDate) {
		this.expirationDate = expirationDate;
	}
	

	
}
