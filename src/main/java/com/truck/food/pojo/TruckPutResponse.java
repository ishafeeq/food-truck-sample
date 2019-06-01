package com.truck.food.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TruckPutResponse extends BaseResponse {

	private List<Truck> successTrucks;
	private Integer successRecordCount;
	
	private List<Truck> failedTrucks;
	private Integer failedRecordCount;
	/**
	 * @return the successTrucks
	 */
	public List<Truck> getSuccessTrucks() {
		return successTrucks;
	}
	/**
	 * @param successTrucks the successTrucks to set
	 */
	public void setSuccessTrucks(List<Truck> successTrucks) {
		this.successTrucks = successTrucks;
	}
	/**
	 * @return the successRecordCount
	 */
	public Integer getSuccessRecordCount() {
		return successRecordCount;
	}
	/**
	 * @param successRecordCount the successRecordCount to set
	 */
	public void setSuccessRecordCount(Integer successRecordCount) {
		this.successRecordCount = successRecordCount;
	}
	/**
	 * @return the failedTrucks
	 */
	public List<Truck> getFailedTrucks() {
		return failedTrucks;
	}
	/**
	 * @param failedTrucks the failedTrucks to set
	 */
	public void setFailedTrucks(List<Truck> failedTrucks) {
		this.failedTrucks = failedTrucks;
	}
	/**
	 * @return the failedRecordCount
	 */
	public Integer getFailedRecordCount() {
		return failedRecordCount;
	}
	/**
	 * @param failedRecordCount the failedRecordCount to set
	 */
	public void setFailedRecordCount(Integer failedRecordCount) {
		this.failedRecordCount = failedRecordCount;
	}
	
}
