package com.truck.food.controller;

import static com.truck.food.constant.CommonConstant.HEALTH_INFO_ENDPOINT;
import static com.truck.food.constant.CommonConstant.LICENSE_ENDPOINT;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class FTHealthStatusController {

	/**
	 * @return application health with status as up
	 */
	@ApiOperation(value = "return server health", response = String.class, notes = "generate health status app")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "server heath is UP") })
	@GetMapping(value = HEALTH_INFO_ENDPOINT, produces = { "application/json" })
	public ResponseEntity<?> health() {
		return new ResponseEntity<>("{\"status\":\"UP\"}", HttpStatus.OK);
	}
	
	@ApiOperation(hidden = true, value = "return server health", response = String.class, notes = "generate health status app")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "server heath is UP") })
	@GetMapping(value = LICENSE_ENDPOINT, produces = { "application/json" })
	public ResponseEntity<?> license() {
		return new ResponseEntity<>("Call Licensing team", HttpStatus.OK);
	}
}
