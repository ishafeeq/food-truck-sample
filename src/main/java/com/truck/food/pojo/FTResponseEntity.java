package com.truck.food.pojo;

import org.springframework.http.ResponseEntity;

public class FTResponseEntity {

	ResponseEntity<String> entity ;

	/**
	 * @return the entity
	 */
	public ResponseEntity<String> getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(ResponseEntity<String> entity) {
		this.entity = entity;
	}
	
}
