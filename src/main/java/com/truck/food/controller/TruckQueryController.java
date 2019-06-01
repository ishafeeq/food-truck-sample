package com.truck.food.controller;

import static com.truck.food.constant.CommonConstant.GET_TRUCK_INFO_ENDPOINT;
import static com.truck.food.constant.CommonConstant.PUT_TRUCK_FROM_ROW_INFO_ENDPOINT;
import static com.truck.food.constant.CommonConstant.PUT_TRUCK_INFO_ENDPOINT;
import static com.truck.food.constant.CommonConstant.QUERY_TRUCK_INFO_ENDPOINT;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.truck.food.adaptor.CommonAdaptor;
import com.truck.food.constant.CommonConstant;
import com.truck.food.pojo.AddTruckRequest;
import com.truck.food.pojo.BaseResponse;
import com.truck.food.pojo.FTResponseEntity;
import com.truck.food.pojo.TruckPutResponse;
import com.truck.food.pojo.TruckQueryResponse;
import com.truck.food.service.TruckQueryService;
import com.truck.food.util.FTUtil;

import io.reactivex.Observable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "TRUCK-INFO")
@RestController
@RequestMapping(TruckQueryController.CONTROLLER_VERSION + "/")
public class TruckQueryController {

	public static final String CONTROLLER_VERSION = "v1";

	@Autowired
	private TruckQueryService truckService;

	@ApiOperation(value = "get truck info for given truck id", response = String.class, notes = "get truck infor given truck id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrived details for given input request."),
			@ApiResponse(code = 400, message = "Bad request."),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found."),
			@ApiResponse(code = 502, message = "DownStream API responded with BAD_REQUEST"),
			@ApiResponse(code = 504, message = "DownStream Api is not responding"),
			@ApiResponse(code = 500, message = "Some internal error occured.") })
	@GetMapping(value = GET_TRUCK_INFO_ENDPOINT, produces = { MediaType.APPLICATION_JSON_VALUE })
	public DeferredResult<ResponseEntity<String>> getTruck(
			@ApiParam(name = "ids", required = false, defaultValue = "1", value = "truck_ids") @RequestParam(name = "ids", required = false) String truckIds) {
		DeferredResult<ResponseEntity<String>> result = new DeferredResult<>();
		FTResponseEntity response = new FTResponseEntity();
		Observable.just(result).doOnNext(res -> {
			TruckQueryResponse resp = truckService.getTrucks(Arrays.asList(truckIds.split(CommonConstant.COMMA)));
			response.setEntity(FTUtil.buildResponse(resp, resp.getResponseCode(), true));
		}).doOnComplete(() -> {
			result.setResult(response.getEntity());
		}).doOnError(e -> {
			BaseResponse errResp = CommonAdaptor.getErrorResponse(e);
			response.setEntity(FTUtil.buildResponse(errResp, errResp.getResponseCode(), true));
			result.setErrorResult(response.getEntity());
		}).subscribe();
		return result;
	}

	@ApiOperation(value = "get truck info for given truck id", response = String.class, notes = "get truck infor given truck id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrived details for given input request."),
			@ApiResponse(code = 400, message = "Bad request."),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found."),
			@ApiResponse(code = 502, message = "DownStream API responded with BAD_REQUEST"),
			@ApiResponse(code = 504, message = "DownStream Api is not responding"),
			@ApiResponse(code = 500, message = "Some internal error occured.") })
	@GetMapping(value = QUERY_TRUCK_INFO_ENDPOINT, produces = { MediaType.APPLICATION_JSON_VALUE })
	public DeferredResult<ResponseEntity<String>> getTruckByQuery(
			@ApiParam(name = "param_name", required = false, defaultValue = "app_n", value = "truck_ids") @RequestParam(name = "ids", required = false) String paramName,
			@ApiParam(name = "param_value", required = false, defaultValue = "Mayor", value = "param_value") @RequestParam(name = "param_value", required = false) String paramValue) {
		DeferredResult<ResponseEntity<String>> result = new DeferredResult<>();
		FTResponseEntity response = new FTResponseEntity();
		Observable.just(result).doOnNext(res -> {
			TruckQueryResponse resp = truckService.queryByName(paramValue);
			response.setEntity(FTUtil.buildResponse(resp, resp.getResponseCode(), true));
		}).doOnComplete(() -> {
			result.setResult(response.getEntity());
		}).doOnError(e -> {
			BaseResponse errResp = CommonAdaptor.getErrorResponse(e);
			response.setEntity(FTUtil.buildResponse(errResp, errResp.getResponseCode(), true));
			result.setErrorResult(response.getEntity());
		}).subscribe();
		return result;
	}

	@ApiOperation(value = "put truck info for given truck id in db", response = String.class, notes = "put truck infor given truck id in db")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrived details for given input request."),
			@ApiResponse(code = 400, message = "Bad request."),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found."),
			@ApiResponse(code = 502, message = "DownStream API responded with BAD_REQUEST"),
			@ApiResponse(code = 504, message = "DownStream Api is not responding"),
			@ApiResponse(code = 500, message = "Some internal error occured.") })
	@PutMapping(value = PUT_TRUCK_INFO_ENDPOINT, produces = { MediaType.APPLICATION_JSON_VALUE })
	public DeferredResult<ResponseEntity<String>> putTruck(
			@ApiParam(name = "request", required = true, value = "request") @RequestBody AddTruckRequest request) {
		DeferredResult<ResponseEntity<String>> result = new DeferredResult<>();
		FTResponseEntity response = new FTResponseEntity();
		Observable.just(result).doOnNext(res -> {
			TruckPutResponse resp = truckService.putTrucks(request);
			response.setEntity(FTUtil.buildResponse(resp, resp.getResponseCode(), true));
		}).doOnComplete(() -> {
			result.setResult(response.getEntity());
		}).doOnError(e -> {
			BaseResponse errResp = CommonAdaptor.getErrorResponse(e);
			response.setEntity(FTUtil.buildResponse(errResp, errResp.getResponseCode(), true));
			result.setErrorResult(response.getEntity());
		}).subscribe();
		return result;
	}

	@ApiOperation(value = "put truck info for given truck id in db", response = String.class, notes = "put truck infor given truck id in db")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrived details for given input request."),
			@ApiResponse(code = 400, message = "Bad request."),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found."),
			@ApiResponse(code = 502, message = "DownStream API responded with BAD_REQUEST"),
			@ApiResponse(code = 504, message = "DownStream Api is not responding"),
			@ApiResponse(code = 500, message = "Some internal error occured.") })
	@PutMapping(value = PUT_TRUCK_FROM_ROW_INFO_ENDPOINT, produces = { MediaType.APPLICATION_JSON_VALUE })
	public DeferredResult<ResponseEntity<String>> putTruckFromRow(
			@ApiParam(name = "row", required = true, value = "row") @RequestBody String row) {
		DeferredResult<ResponseEntity<String>> result = new DeferredResult<>();
		FTResponseEntity response = new FTResponseEntity();
		Observable.just(result).doOnNext(res -> {
			TruckPutResponse resp = truckService.putTrucksFromRow(row);
			response.setEntity(FTUtil.buildResponse(resp, resp.getResponseCode(), true));
		}).doOnComplete(() -> {
			result.setResult(response.getEntity());
		}).doOnError(e -> {
			BaseResponse errResp = CommonAdaptor.getErrorResponse(e);
			response.setEntity(FTUtil.buildResponse(errResp, errResp.getResponseCode(), true));
			result.setErrorResult(response.getEntity());
		}).subscribe();
		return result;
	}
}
