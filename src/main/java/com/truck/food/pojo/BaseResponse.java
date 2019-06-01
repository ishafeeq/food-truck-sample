package com.truck.food.pojo;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.truck.food.ftenum.ResponseStatus;

@JsonInclude(Include.NON_NULL)
public class BaseResponse {

	private HttpStatus responseCode;
	private ResponseStatus responseStatus;
	private FTError errors;
	
	
	/**
	 * @return the responseCode
	 */
	public HttpStatus getResponseCode() {
		return responseCode;
	}
	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(HttpStatus responseCode) {
		this.responseCode = responseCode;
	}
	/**
	 * @return the responseStatus
	 */
	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}
	/**
	 * @param responseStatus the responseStatus to set
	 */
	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}
	/**
	 * @return the errors
	 */
	public FTError getErrors() {
		return errors;
	}
	/**
	 * @param errors the errors to set
	 */
	public void setErrors(FTError errors) {
		this.errors = errors;
	}
	
}
