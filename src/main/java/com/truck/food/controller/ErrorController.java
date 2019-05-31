package com.truck.food.controller;

import static com.truck.food.constant.CommonConstant.ERROR_ENDPOINT;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {

	@GetMapping(value = ERROR_ENDPOINT, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> error() {
		return new ResponseEntity<>("{\"status\":\"UP\"}", HttpStatus.OK);
	}
}
