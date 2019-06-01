package com.truck.food.pojo;

import java.math.BigDecimal;
import java.util.Date;
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
	
	private BigDecimal xCord;
	private BigDecimal yCord;
	
	private BigDecimal latitude;
	private BigDecimal longitude;
	
	private Date nOISent;
	private Date approved;
	private Date received;
	private Date priorPermit;
	private Date expirationDate;
	
	
	
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
	public BigDecimal getxCord() {
		return xCord;
	}
	/**
	 * @param xCord the xCord to set
	 */
	public void setxCord(BigDecimal xCord) {
		this.xCord = xCord;
	}
	/**
	 * @return the yCord
	 */
	public BigDecimal getyCord() {
		return yCord;
	}
	/**
	 * @param yCord the yCord to set
	 */
	public void setyCord(BigDecimal yCord) {
		this.yCord = yCord;
	}
	/**
	 * @return the latitude
	 */
	public BigDecimal getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public BigDecimal getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the nOISent
	 */
	public Date getnOISent() {
		return nOISent;
	}
	/**
	 * @param nOISent the nOISent to set
	 */
	public void setnOISent(Date nOISent) {
		this.nOISent = nOISent;
	}
	/**
	 * @return the approved
	 */
	public Date getApproved() {
		return approved;
	}
	/**
	 * @param approved the approved to set
	 */
	public void setApproved(Date approved) {
		this.approved = approved;
	}
	/**
	 * @return the received
	 */
	public Date getReceived() {
		return received;
	}
	/**
	 * @param received the received to set
	 */
	public void setReceived(Date received) {
		this.received = received;
	}
	/**
	 * @return the priorPermit
	 */
	public Date getPriorPermit() {
		return priorPermit;
	}
	/**
	 * @param priorPermit the priorPermit to set
	 */
	public void setPriorPermit(Date priorPermit) {
		this.priorPermit = priorPermit;
	}
	/**
	 * @return the expirationDate
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}
	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	
}
