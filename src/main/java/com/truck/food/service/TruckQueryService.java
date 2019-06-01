package com.truck.food.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.truck.food.adaptor.CommonAdaptor;
import com.truck.food.constant.AeroSpikeConstant;
import com.truck.food.constant.CommonConstant;
import com.truck.food.ftenum.ResponseStatus;
import com.truck.food.impl.AerospikeTruckDataStoreImpl;
import com.truck.food.pojo.AddTruckRequest;
import com.truck.food.pojo.BaseResponse;
import com.truck.food.pojo.DBSyncResponse;
import com.truck.food.pojo.Truck;
import com.truck.food.pojo.TruckPutResponse;
import com.truck.food.pojo.TruckQueryResponse;

@Service
public class TruckQueryService {

	private static Logger LOGGER = LogManager.getLogger(TruckQueryService.class);

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
		List<Truck> trucks = asTruckDataStore.queryByName(getDefaultBins(), value);
		response.setTrucks(trucks);
		response.setResponseCode(HttpStatus.OK);
		response.setResponseStatus(ResponseStatus.SUCCESS);
		return response;

	}

	private String[] getDefaultBins() {
		return new String[] { AeroSpikeConstant.BIN_NAME_APPLICANT_NAME, AeroSpikeConstant.BIN_NAME_TRUCK_ID,
				AeroSpikeConstant.BIN_NAME_LOCATION_ID, AeroSpikeConstant.BIN_NAME_FACILITY_TYPE,
				AeroSpikeConstant.BIN_NAME_EXPIRATION_DATE, AeroSpikeConstant.BIN_NAME_LAT_LON_GEO,
				AeroSpikeConstant.BIN_NAME_LOC_DESC };
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

	public DBSyncResponse syncDB(String filePath, int range) {
		DBSyncResponse response = new DBSyncResponse();
		String line;
		FileReader fr;
		BufferedReader bufferreader = null;
		int success = 0, failed = 0;
		try {
			fr = new FileReader(filePath);
			bufferreader = new BufferedReader(fr);
			line = bufferreader.readLine();// Escaping first line
			while ((line = bufferreader.readLine()) != null && (success + failed) < range) {
				try {
					putTrucksFromRow(line);
					success++;
				} catch (Exception ex) {
					LOGGER.error(line);
					failed++;
				}
			}
			bufferreader.close();
			fr.close();
		} catch (FileNotFoundException ex) {
			LOGGER.error(ex);
			ex.printStackTrace();
		} catch (IOException ex) {
			LOGGER.error(ex);
			ex.printStackTrace();
		}
		response.setRecordsInsertedSuccessCount(success);
		response.setRecordsInsertedFailedCount(failed);
		response.setRecordsTotalCount(success + failed);
		response.setResponseCode(HttpStatus.OK);
		response.setResponseStatus(ResponseStatus.SUCCESS);
		return response;
	}

	public TruckQueryResponse queryByLoc(String locations, String radius) {
		TruckQueryResponse response = new TruckQueryResponse();
		String[] locs = locations.split(CommonConstant.COMMA);
		Map<String, List<Truck>> truckMap = new HashMap<>();
		for (String loc : locs) {
			String[] latLong = loc.split(CommonConstant.COLON);
			List<Truck> trucks = asTruckDataStore.queryByLocation(AeroSpikeConstant.BIN_NAME_LAT_LON_GEO,
					getDefaultBins(), Double.valueOf(latLong[0]), Double.valueOf(latLong[1]), Integer.valueOf(radius));
			truckMap.put(loc, trucks);
		}
		response.setLocationMap(truckMap);
		response.setResponseStatus(ResponseStatus.SUCCESS);
		response.setResponseCode(HttpStatus.OK);
		return response;
	}

	public TruckQueryResponse queryByStreetName(String streetName) {
		TruckQueryResponse response = new TruckQueryResponse();
		List<Truck> trucks = asTruckDataStore.queryByStreetName(AeroSpikeConstant.BIN_NAME_LOC_DESC, getDefaultBins(), streetName);
		response.setTrucks(trucks);
		response.setResponseStatus(ResponseStatus.SUCCESS);
		response.setResponseCode(HttpStatus.OK);
		return response;
	}

	public BaseResponse deleteTruck(String truckId) {
		BaseResponse response = new BaseResponse();
		boolean isSuccessful = asTruckDataStore.delete(truckId);
		if(isSuccessful) {
			response.setResponseStatus(ResponseStatus.SUCCESS);
			response.setResponseCode(HttpStatus.OK);
		} else {
			response.setResponseStatus(ResponseStatus.FAILED);
			response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.setResponseStatus(ResponseStatus.SUCCESS);
		response.setResponseCode(HttpStatus.OK);
		return response;
	}

}
