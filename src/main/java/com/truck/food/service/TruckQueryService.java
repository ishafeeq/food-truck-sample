package com.truck.food.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.truck.food.adaptor.CommonAdaptor;
import com.truck.food.constant.AeroSpikeConstant;
import com.truck.food.ftenum.ResponseStatus;
import com.truck.food.impl.AerospikeTruckDataStoreImpl;
import com.truck.food.pojo.AddTruckRequest;
import com.truck.food.pojo.Truck;
import com.truck.food.pojo.TruckPutResponse;
import com.truck.food.pojo.TruckQueryResponse;

@Service
public class TruckQueryService {

	@Autowired
	private AerospikeTruckDataStoreImpl asTruckDataStore;

	public TruckQueryResponse getTrucks(List<String> ids) {
		TruckQueryResponse response = new TruckQueryResponse();
		List<Truck> trucks = asTruckDataStore.multiGet(ids);
		if (CollectionUtils.isEmpty(trucks)) {
			response.setResponseStatus(ResponseStatus.FAILED);
			response.setResponseCode(HttpStatus.NOT_FOUND);
		} else {
			response.setTrucks(trucks);
			response.setResponseCode(HttpStatus.OK);
			response.setResponseStatus(ResponseStatus.SUCCESS);
		}
		return response;
	}

	public TruckQueryResponse queryByName(String value) {
		TruckQueryResponse response = new TruckQueryResponse();
		List<Truck> trucks = asTruckDataStore.queryByName(AeroSpikeConstant.BIN_NAME_APPLICANT_NAME, value);
		response.setTrucks(trucks);
		response.setResponseCode(HttpStatus.OK);
		response.setResponseStatus(ResponseStatus.SUCCESS);
		return response;

	}

	public TruckPutResponse putTrucks(AddTruckRequest trucksReq) {
		TruckPutResponse response = new TruckPutResponse();
		List<Truck> trucks = trucksReq.getTrucks();
		List<Truck> successTrucks = new ArrayList<>();
		List<Truck> failedTrucks = new ArrayList<>();
		for (Truck truck : trucks) {
			String key = asTruckDataStore.getKey(truck);// UUID.randomUUID().toString();
			truck.setTruckId(key);
			boolean isWriteSuccessful = asTruckDataStore.put(key, truck);
			if (isWriteSuccessful) {
				successTrucks.add(truck);
			} else {
				failedTrucks.add(truck);
			}
		}
		if (CollectionUtils.isEmpty(successTrucks) && CollectionUtils.isEmpty(failedTrucks)) {
			response.setResponseStatus(ResponseStatus.FAILED);
			response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (CollectionUtils.isEmpty(failedTrucks)) {
			response.setSuccessRecordCount(successTrucks.size());
			response.setSuccessTrucks(successTrucks);
			response.setResponseStatus(ResponseStatus.SUCCESS);
			response.setResponseCode(HttpStatus.ACCEPTED);
		} else if (CollectionUtils.isEmpty(successTrucks)) {
			response.setFailedRecordCount(failedTrucks.size());
			response.setFailedTrucks(failedTrucks);
			response.setResponseStatus(ResponseStatus.FAILED);
			response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			// This debatable, we can chose partial failure as success or failure or some
			// other state, I am taking it as success
			response.setSuccessRecordCount(successTrucks.size());
			response.setFailedRecordCount(failedTrucks.size());
			response.setSuccessTrucks(successTrucks);
			response.setFailedTrucks(failedTrucks);
			response.setResponseStatus(ResponseStatus.SUCCESS);
			response.setResponseCode(HttpStatus.ACCEPTED);
		}
		return response;
	}

	public TruckPutResponse putTrucksFromRow(String row) {
		AddTruckRequest request = CommonAdaptor.getPutTruckRequest(row);
		return putTrucks(request);
	}
}
