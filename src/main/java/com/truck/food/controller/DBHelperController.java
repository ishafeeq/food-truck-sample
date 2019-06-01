package com.truck.food.controller;

import static com.truck.food.constant.CommonConstant.GET_TRUCK_INFO_ENDPOINT;
import static com.truck.food.constant.CommonConstant.SYNC_DB_ENDPOINT;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.truck.food.adaptor.CommonAdaptor;
import com.truck.food.pojo.BaseResponse;
import com.truck.food.pojo.DBSyncResponse;
import com.truck.food.pojo.FTResponseEntity;
import com.truck.food.service.TruckQueryService;
import com.truck.food.util.FTUtil;

import io.reactivex.Observable;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class DBHelperController {

	private static final Logger LOGGER = LogManager.getLogger(DBHelperController.class);

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
	@GetMapping(value = SYNC_DB_ENDPOINT, produces = { MediaType.APPLICATION_JSON_VALUE })
	public DeferredResult<ResponseEntity<String>> getTruckByQuery(
			@ApiParam(name = "path", required = false, defaultValue = "/home/mmt7147/Documents/AeroSpk/Mobile_Food_Facility_Permit.csv", value = "path") @RequestParam(name = "path", required = false) String filePath,
			@ApiParam(name = "count", required = false, defaultValue = "10", value = "path") @RequestParam(name = "count", required = false) String recordCount) {
		long startTime = System.currentTimeMillis();
		DeferredResult<ResponseEntity<String>> result = new DeferredResult<>();
		FTResponseEntity response = new FTResponseEntity();
		Observable.just(result).doOnNext(res -> {
			DBSyncResponse resp = truckService.syncDB(filePath, Integer.valueOf(recordCount));
			response.setEntity(FTUtil.buildResponse(resp, resp.getResponseCode(), true));
		}).doOnComplete(() -> {
			LOGGER.info(SYNC_DB_ENDPOINT + " Total Time:" + String.valueOf(System.currentTimeMillis() - startTime));
			result.setResult(response.getEntity());
		}).doOnError(e -> {
			BaseResponse errResp = CommonAdaptor.getErrorResponse(e);
			response.setEntity(FTUtil.buildResponse(errResp, errResp.getResponseCode(), true));
			result.setErrorResult(response.getEntity());
		}).subscribe();
		return result;
	}

}
