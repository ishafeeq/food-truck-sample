package com.truck.food.controller;

import static com.truck.food.constant.CommonConstant.DELETE_TRUCK_INFO_ENDPOINT;
import static com.truck.food.constant.CommonConstant.GET_TRUCK_INFO_ENDPOINT;
import static com.truck.food.constant.CommonConstant.PUT_TRUCK_FROM_ROW_INFO_ENDPOINT;
import static com.truck.food.constant.CommonConstant.PUT_TRUCK_INFO_ENDPOINT;
import static com.truck.food.constant.CommonConstant.QUERY_LOCATION_ENDPOINT;
import static com.truck.food.constant.CommonConstant.QUERY_NAME_ENDPOINT;
import static com.truck.food.constant.CommonConstant.QUERY_STREET_ENDPOINT;
import static com.truck.food.constant.CommonConstant.QUERY_EXPIRY_ENDPOINT;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

	private static final Logger LOGGER = LogManager.getLogger(TruckQueryController.class);

	public static final String CONTROLLER_VERSION = "v1";

	@Autowired
	private TruckQueryService truckService;

	@ApiOperation(value = "get truck info for given truck id or location Id", response = String.class, notes = "get truck infor given truck id or location Id")
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
		long startTime = System.currentTimeMillis();
		DeferredResult<ResponseEntity<String>> result = new DeferredResult<>();
		FTResponseEntity response = new FTResponseEntity();
		Observable.just(result).doOnNext(res -> {
			TruckQueryResponse resp = truckService.getTrucks(Arrays.asList(truckIds.split(CommonConstant.COMMA)));
			response.setEntity(FTUtil.buildResponse(resp, resp.getResponseCode(), true));
		}).doOnComplete(() -> {
			LOGGER.info(
					GET_TRUCK_INFO_ENDPOINT + " Total Time:" + String.valueOf(System.currentTimeMillis() - startTime));
			result.setResult(response.getEntity());
		}).doOnError(e -> {
			BaseResponse errResp = CommonAdaptor.getErrorResponse(e);
			response.setEntity(FTUtil.buildResponse(errResp, errResp.getResponseCode(), true));
			result.setErrorResult(response.getEntity());
		}).subscribe();
		return result;
	}

	@ApiOperation(value = "get trucks from Applicant name", response = String.class, notes = "get trucks from Applicant name")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrived details for given input request."),
			@ApiResponse(code = 400, message = "Bad request."),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found."),
			@ApiResponse(code = 502, message = "DownStream API responded with BAD_REQUEST"),
			@ApiResponse(code = 504, message = "DownStream Api is not responding"),
			@ApiResponse(code = 500, message = "Some internal error occured.") })
	@GetMapping(value = QUERY_NAME_ENDPOINT, produces = { MediaType.APPLICATION_JSON_VALUE })
	public DeferredResult<ResponseEntity<String>> getTruckByQueryName(
			@ApiParam(name = "param_value", required = false, defaultValue = "Paradise Catering", value = "param_value") @RequestParam(name = "param_value", required = false) String paramValue) {
		long startTime = System.currentTimeMillis();
		DeferredResult<ResponseEntity<String>> result = new DeferredResult<>();
		FTResponseEntity response = new FTResponseEntity();
		Observable.just(result).doOnNext(res -> {
			TruckQueryResponse resp = truckService.queryByName(paramValue);
			response.setEntity(FTUtil.buildResponse(resp, resp.getResponseCode(), true));
		}).doOnComplete(() -> {
			LOGGER.info(QUERY_NAME_ENDPOINT + " Total Time:" + String.valueOf(System.currentTimeMillis() - startTime));
			result.setResult(response.getEntity());
		}).doOnError(e -> {
			BaseResponse errResp = CommonAdaptor.getErrorResponse(e);
			response.setEntity(FTUtil.buildResponse(errResp, errResp.getResponseCode(), true));
			result.setErrorResult(response.getEntity());
		}).subscribe();
		return result;
	}

	@ApiOperation(value = "query truck info from given lat:long and given radius", response = String.class, notes = "query truck info from given lat:long and given radius")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrived details for given input request."),
			@ApiResponse(code = 400, message = "Bad request."),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found."),
			@ApiResponse(code = 502, message = "DownStream API responded with BAD_REQUEST"),
			@ApiResponse(code = 504, message = "DownStream Api is not responding"),
			@ApiResponse(code = 500, message = "Some internal error occured.") })
	@GetMapping(value = QUERY_LOCATION_ENDPOINT, produces = { MediaType.APPLICATION_JSON_VALUE })
	public DeferredResult<ResponseEntity<String>> getTruckByQueryLoc(
			@ApiParam(name = "locs", required = true, defaultValue = "37.7781283797338:-122.418652129997", value = "locs") @RequestParam(name = "locs", required = true) String locations,
			@ApiParam(name = "radius", required = true, defaultValue = "500", value = "radius") @RequestParam(name = "radius", required = true) String radius) {
		long startTime = System.currentTimeMillis();
		DeferredResult<ResponseEntity<String>> result = new DeferredResult<>();
		FTResponseEntity response = new FTResponseEntity();
		Observable.just(result).doOnNext(res -> {
			TruckQueryResponse resp = truckService.queryByLoc(locations, radius);
			response.setEntity(FTUtil.buildResponse(resp, resp.getResponseCode(), true));
		}).doOnComplete(() -> {
			LOGGER.info(
					QUERY_LOCATION_ENDPOINT + " Total Time:" + String.valueOf(System.currentTimeMillis() - startTime));
			result.setResult(response.getEntity());
		}).doOnError(e -> {
			BaseResponse errResp = CommonAdaptor.getErrorResponse(e);
			response.setEntity(FTUtil.buildResponse(errResp, errResp.getResponseCode(), true));
			result.setErrorResult(response.getEntity());
		}).subscribe();
		return result;
	}

	@ApiOperation(value = "query truck info from given lat:long and given radius", response = String.class, notes = "query truck info from given lat:long and given radius")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrived details for given input request."),
			@ApiResponse(code = 400, message = "Bad request."),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found."),
			@ApiResponse(code = 502, message = "DownStream API responded with BAD_REQUEST"),
			@ApiResponse(code = 504, message = "DownStream Api is not responding"),
			@ApiResponse(code = 500, message = "Some internal error occured.") })
	@GetMapping(value = QUERY_STREET_ENDPOINT, produces = { MediaType.APPLICATION_JSON_VALUE })
	public DeferredResult<ResponseEntity<String>> getTruckByQueryStreet(
			@ApiParam(name = "street", required = true, defaultValue = "MARKET", value = "street") @RequestParam(name = "street", required = true) String streetName) {
		long startTime = System.currentTimeMillis();
		DeferredResult<ResponseEntity<String>> result = new DeferredResult<>();
		FTResponseEntity response = new FTResponseEntity();
		Observable.just(result).doOnNext(res -> {
			TruckQueryResponse resp = truckService.queryByStreetName(streetName);
			response.setEntity(FTUtil.buildResponse(resp, resp.getResponseCode(), true));
		}).doOnComplete(() -> {
			LOGGER.info(
					QUERY_STREET_ENDPOINT + " Total Time:" + String.valueOf(System.currentTimeMillis() - startTime));
			result.setResult(response.getEntity());
		}).doOnError(e -> {
			BaseResponse errResp = CommonAdaptor.getErrorResponse(e);
			response.setEntity(FTUtil.buildResponse(errResp, errResp.getResponseCode(), true));
			result.setErrorResult(response.getEntity());
		}).subscribe();
		return result;
	}

	@ApiOperation(value = "query truck info from expiration status", response = String.class, notes = "query truck info from expiration status")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrived details for given input request."),
			@ApiResponse(code = 400, message = "Bad request."),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found."),
			@ApiResponse(code = 502, message = "DownStream API responded with BAD_REQUEST"),
			@ApiResponse(code = 504, message = "DownStream Api is not responding"),
			@ApiResponse(code = 500, message = "Some internal error occured.") })
	@GetMapping(value = QUERY_EXPIRY_ENDPOINT, produces = { MediaType.APPLICATION_JSON_VALUE })
	public DeferredResult<ResponseEntity<String>> getTruckByQueryExpiry(
			@ApiParam(name = "exprd", required = true, defaultValue = "true", value = "exprd") @RequestParam(name = "exprd", required = true) String isExpired,
			@ApiParam(name = "usTime", required = false, defaultValue = "1563177600000", value = "usTime") @RequestParam(name = "usTime", required = false) String expiryTimeUSinMillis) {
		long startTime = System.currentTimeMillis();
		DeferredResult<ResponseEntity<String>> result = new DeferredResult<>();
		FTResponseEntity response = new FTResponseEntity();
		Observable.just(result).doOnNext(res -> {
			TruckQueryResponse resp = truckService.queryByExpiry(isExpired, expiryTimeUSinMillis);
			response.setEntity(FTUtil.buildResponse(resp, resp.getResponseCode(), true));
		}).doOnComplete(() -> {
			LOGGER.info(
					QUERY_EXPIRY_ENDPOINT + " Total Time:" + String.valueOf(System.currentTimeMillis() - startTime));
			result.setResult(response.getEntity());
		}).doOnError(e -> {
			BaseResponse errResp = CommonAdaptor.getErrorResponse(e);
			response.setEntity(FTUtil.buildResponse(errResp, errResp.getResponseCode(), true));
			result.setErrorResult(response.getEntity());
		}).subscribe();
		return result;
	}

	@ApiOperation(value = "put truck info for given truck id or location id in db", response = String.class, notes = "put truck infor given truck id or location id in db")
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
		long startTime = System.currentTimeMillis();
		DeferredResult<ResponseEntity<String>> result = new DeferredResult<>();
		FTResponseEntity response = new FTResponseEntity();
		Observable.just(result).doOnNext(res -> {
			TruckPutResponse resp = truckService.putTrucks(request);
			response.setEntity(FTUtil.buildResponse(resp, resp.getResponseCode(), true));
		}).doOnComplete(() -> {
			LOGGER.info(
					PUT_TRUCK_INFO_ENDPOINT + " Total Time:" + String.valueOf(System.currentTimeMillis() - startTime));
			result.setResult(response.getEntity());
		}).doOnError(e -> {
			BaseResponse errResp = CommonAdaptor.getErrorResponse(e);
			response.setEntity(FTUtil.buildResponse(errResp, errResp.getResponseCode(), true));
			result.setErrorResult(response.getEntity());
		}).subscribe();
		return result;
	}

	@ApiOperation(value = "delete truck info for given truck id or location id in db", response = String.class, notes = "delete truck infor given truck id or location id in db")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrived details for given input request."),
			@ApiResponse(code = 400, message = "Bad request."),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found."),
			@ApiResponse(code = 502, message = "DownStream API responded with BAD_REQUEST"),
			@ApiResponse(code = 504, message = "DownStream Api is not responding"),
			@ApiResponse(code = 500, message = "Some internal error occured.") })
	@DeleteMapping(value = DELETE_TRUCK_INFO_ENDPOINT, produces = { MediaType.APPLICATION_JSON_VALUE })
	public DeferredResult<ResponseEntity<String>> deleteTruck(
			@ApiParam(name = "truckId", required = true, value = "truckId") @RequestParam(name = "truckId", required = true) String truckId) {
		long startTime = System.currentTimeMillis();
		DeferredResult<ResponseEntity<String>> result = new DeferredResult<>();
		FTResponseEntity response = new FTResponseEntity();
		Observable.just(result).doOnNext(res -> {
			BaseResponse resp = truckService.deleteTruck(truckId);
			response.setEntity(FTUtil.buildResponse(resp, resp.getResponseCode(), true));
		}).doOnComplete(() -> {
			LOGGER.info(DELETE_TRUCK_INFO_ENDPOINT + " Total Time:"
					+ String.valueOf(System.currentTimeMillis() - startTime));
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
		long startTime = System.currentTimeMillis();
		DeferredResult<ResponseEntity<String>> result = new DeferredResult<>();
		FTResponseEntity response = new FTResponseEntity();
		Observable.just(result).doOnNext(res -> {
			TruckPutResponse resp = truckService.putTrucksFromRow(row);
			response.setEntity(FTUtil.buildResponse(resp, resp.getResponseCode(), true));
		}).doOnComplete(() -> {
			LOGGER.info(PUT_TRUCK_FROM_ROW_INFO_ENDPOINT + " Total Time:"
					+ String.valueOf(System.currentTimeMillis() - startTime));
			result.setResult(response.getEntity());
		}).doOnError(e -> {
			BaseResponse errResp = CommonAdaptor.getErrorResponse(e);
			response.setEntity(FTUtil.buildResponse(errResp, errResp.getResponseCode(), true));
			result.setErrorResult(response.getEntity());
		}).subscribe();
		return result;
	}
}
