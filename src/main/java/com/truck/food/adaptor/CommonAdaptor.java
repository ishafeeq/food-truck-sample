package com.truck.food.adaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.truck.food.constant.CommonConstant;
import com.truck.food.ftenum.FacilityType;
import com.truck.food.ftenum.ResponseStatus;
import com.truck.food.pojo.AddTruckRequest;
import com.truck.food.pojo.BaseResponse;
import com.truck.food.pojo.FTError;
import com.truck.food.pojo.Truck;

public class CommonAdaptor {

	public static BaseResponse getSuccessResponse() {
		return null;
	}

	public static BaseResponse getErrorResponse(Throwable e) {
		BaseResponse errResp = new BaseResponse();
		errResp.setErrors(getFTErrors(e));
		errResp.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR);
		errResp.setResponseStatus(ResponseStatus.FAILED);
		return errResp;
	}

	private static FTError getFTErrors(Throwable e) {
		FTError error = new FTError();
		error.setErrorCode(e.getMessage());
		error.setErrorMessage(null != e.getCause() ? e.getCause().toString() : "");
		return error;
	}

	public static BaseResponse getNoDataFoundError() {
		return null;
	}

	public static AddTruckRequest getPutTruckRequest(String row) {
		String[] vals = row.split(CommonConstant.COMMA);
		AddTruckRequest request = new AddTruckRequest();
		List<Truck> trucks = new ArrayList<>();
		Truck truck = new Truck();
		truck.setApllicantName(vals[1]);
		truck.setLocationId(Long.valueOf(vals[0]));
		truck.setFacilityType(FacilityType.getFacility(vals[2]));
		truck.setExpirationDate(new Date(vals[22]));
		
		trucks.add(truck);
		request.setTrucks(trucks);
		return request;
	}
}
