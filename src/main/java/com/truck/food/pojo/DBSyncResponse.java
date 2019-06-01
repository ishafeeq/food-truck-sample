package com.truck.food.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DBSyncResponse extends BaseResponse {
	
	Integer recordsInsertedSuccessCount;
	Integer recordsInsertedFailedCount;
	Integer recordsTotalCount;
	/**
	 * @return the recordsInsertedSuccessCount
	 */
	public Integer getRecordsInsertedSuccessCount() {
		return recordsInsertedSuccessCount;
	}
	/**
	 * @param recordsInsertedSuccessCount the recordsInsertedSuccessCount to set
	 */
	public void setRecordsInsertedSuccessCount(Integer recordsInsertedSuccessCount) {
		this.recordsInsertedSuccessCount = recordsInsertedSuccessCount;
	}
	/**
	 * @return the recordsTotalCount
	 */
	public Integer getRecordsTotalCount() {
		return recordsTotalCount;
	}
	/**
	 * @param recordsTotalCount the recordsTotalCount to set
	 */
	public void setRecordsTotalCount(Integer recordsTotalCount) {
		this.recordsTotalCount = recordsTotalCount;
	}
	/**
	 * @return the recordsInsertedFailedCount
	 */
	public Integer getRecordsInsertedFailedCount() {
		return recordsInsertedFailedCount;
	}
	/**
	 * @param recordsInsertedFailedCount the recordsInsertedFailedCount to set
	 */
	public void setRecordsInsertedFailedCount(Integer recordsInsertedFailedCount) {
		this.recordsInsertedFailedCount = recordsInsertedFailedCount;
	}
	
}
