package com.truck.food.controller;

import static com.truck.food.constant.CommonConstant.GET_TRUCK_INFO_ENDPOINT;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "TRUCK-INFO")
@RestController
@RequestMapping(TruckQueryController.CONTROLLER_VERSION + "/")
public class TruckQueryController {

	public static final String CONTROLLER_VERSION = "v1";

	@ApiOperation(value = "get truck infor given truck id", response = String.class, notes = "get truck infor given truck id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrived details for given input request."),
			@ApiResponse(code = 400, message = "Bad request."),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found."),
			@ApiResponse(code = 502, message = "DownStream API responded with BAD_REQUEST"),
			@ApiResponse(code = 504, message = "DownStream Api is not responding"),
			@ApiResponse(code = 500, message = "Some internal error occured.") })
	@GetMapping(value = GET_TRUCK_INFO_ENDPOINT, produces = { MediaType.APPLICATION_JSON_VALUE })
	public DeferredResult<ResponseEntity<String>> getComboInfo(){
		return null;
	}
}
