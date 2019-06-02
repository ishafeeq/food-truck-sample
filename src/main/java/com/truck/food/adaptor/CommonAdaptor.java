package com.truck.food.adaptor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTimeZone;
import org.springframework.http.HttpStatus;

import com.truck.food.constant.CommonConstant;
import com.truck.food.ftenum.FacilityType;
import com.truck.food.ftenum.ResponseStatus;
import com.truck.food.pojo.AddTruckRequest;
import com.truck.food.pojo.BaseResponse;
import com.truck.food.pojo.FTError;
import com.truck.food.pojo.Truck;

public class CommonAdaptor {

	private static Logger LOGGER = LogManager.getLogger(CommonAdaptor.class);
	
//	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
	private static SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
	private static TimeZone tzInAmerica = DateTimeZone.forID("PST8PDT").toTimeZone();
	private static TimeZone tzInDefault = DateTimeZone.getDefault().toTimeZone();
	
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

	public static AddTruckRequest getPutTruckRequest(String row) throws ParseException {
		AddTruckRequest request = new AddTruckRequest();
		List<Truck> trucks = new ArrayList<>();
//		String[] useVal = FTUtil.split(row, CommonConstant.COMMA);
		String[] vals = row.split(CommonConstant.COMMA);
		Truck truck = new Truck();
		truck.setApllicantName(vals[1]);
		truck.setLocationId(Long.valueOf(vals[0]));
		truck.setFacilityType(FacilityType.getFacility(vals[2]));
		truck.setLocationDescription(vals[4]);
		truck.setLatitude(Double.valueOf(vals[14]));
		truck.setLongitude(Double.valueOf((vals[15])));
		truck.setExpirationDate(getExpiryTime(vals[22].trim()));
		trucks.add(truck);
		request.setTrucks(trucks);
		return request;
	}

	private static Long getExpiryTime(String dateString) throws ParseException {
		Date date = null;
		try {
			date = formatter.parse(dateString);
		} catch (ParseException e) {
			LOGGER.error("Invalid date String: " + dateString);
			throw e;
		}
		long epochMili = date.toInstant().toEpochMilli();
		return (epochMili - tzInAmerica.getOffset(epochMili) + tzInDefault.getOffset(epochMili));
	}
}
