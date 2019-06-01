package com.truck.food.pojo;

import java.util.List;

public class FTError {

	private String errorCode;
	private String errorMessage;
	private String errorDescription;
	private List<FTError> downStreamErrors;
	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}
	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	/**
	 * @return the errorDescription
	 */
	public String getErrorDescription() {
		return errorDescription;
	}
	/**
	 * @param errorDescription the errorDescription to set
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	/**
	 * @return the downStreamErrors
	 */
	public List<FTError> getDownStreamErrors() {
		return downStreamErrors;
	}
	/**
	 * @param downStreamErrors the downStreamErrors to set
	 */
	public void setDownStreamErrors(List<FTError> downStreamErrors) {
		this.downStreamErrors = downStreamErrors;
	}
	
	
}
