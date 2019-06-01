package com.truck.food.util;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class FTUtil {

	public static ResponseEntity<String> buildResponse() {
		return null;
	}

	public static <T> ResponseEntity<String> buildResponse(T reponseObject, HttpStatus status, boolean shortHandProps) {
		String response = null;
		try {
			if (shortHandProps) {
				response = JsonConvertor.convertObjectToJson(reponseObject);
			} else {
				response = JsonConvertor.convertObjectToJsonFullNameProps(reponseObject);
			}
		} catch (IOException e) {
		}
		return new ResponseEntity<>(response, status);
	}

	public static String[] split(String row, String comma) {
		
		return null;
	}
}
