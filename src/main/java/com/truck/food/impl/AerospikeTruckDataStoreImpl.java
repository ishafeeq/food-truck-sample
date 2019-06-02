package com.truck.food.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.AerospikeException;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.ResultCode;
import com.aerospike.client.Value.GeoJSONValue;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.RecordExistsAction;
import com.aerospike.client.policy.WritePolicy;
import com.aerospike.client.query.Filter;
import com.aerospike.client.query.IndexType;
import com.aerospike.client.query.PredExp;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.RegexFlag;
import com.aerospike.client.query.PredExp;
import com.aerospike.client.query.IndexCollectionType;
import com.aerospike.client.query.PredExp;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.RegexFlag;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.RegexFlag;
import com.aerospike.client.query.Statement;
import com.aerospike.client.task.IndexTask;
import com.truck.food.config.AerospikeConfig;
import com.truck.food.constant.AeroSpikeConstant;
import com.truck.food.constant.CommonConstant;
import com.truck.food.ftenum.FacilityType;
import com.truck.food.interfaces.TruckDataStore;
import com.truck.food.pojo.Truck;
import com.truck.food.util.AerospikeUtil;

@Component
public class AerospikeTruckDataStoreImpl implements TruckDataStore {

	private AerospikeClient aerospikeClient = null;

	@Autowired
	private AerospikeConfig aerospikeConfig;

	@PostConstruct
	public void init() {
		this.aerospikeClient = new AerospikeClient(AerospikeUtil.getAerospikePolicy(aerospikeConfig),
				AerospikeUtil.getVarArgs(aerospikeConfig));
		// Create Index of required columns here
		createIndexes();
	}

	private void createIndexes() {
		Policy policy = new Policy();
		policy.socketTimeout = 0; // Do not timeout on index create.
		String index_applicant_name = "index_applicant_name";
		String index_address = "index_address";
		String index_geo_2d = "index_geo_2d";
		try {
			IndexTask task1 = aerospikeClient.createIndex(policy, aerospikeConfig.getNamespace(),
					aerospikeConfig.getSet(), index_applicant_name, AeroSpikeConstant.BIN_NAME_APPLICANT_NAME,
					IndexType.STRING);
			IndexTask task2 = aerospikeClient.createIndex(policy, aerospikeConfig.getNamespace(),
					aerospikeConfig.getSet(), index_address, AeroSpikeConstant.BIN_NAME_LOC_DESC, IndexType.STRING);
			IndexTask task3 = aerospikeClient.createIndex(policy, aerospikeConfig.getNamespace(),
					aerospikeConfig.getSet(), index_geo_2d, AeroSpikeConstant.BIN_NAME_LAT_LON_GEO,
					IndexType.GEO2DSPHERE);
			task1.waitTillComplete();
			task2.waitTillComplete();
			task3.waitTillComplete();
		} catch (AerospikeException ae) {
			if (ae.getResultCode() != ResultCode.INDEX_ALREADY_EXISTS) {
				throw ae;
			}
		}
	}

	@Override
	public Truck get(String key) {
		Record record = null;
		Key aerospikeKey = new Key(aerospikeConfig.getNamespace(), aerospikeConfig.getSet(), key);
		try {
			record = this.aerospikeClient.get(null, aerospikeKey);
		} catch (AerospikeException e) {
			return null;
		}
		return getTruckFromRecord(record);
	}

	@Override
	public List<Truck> multiGet(List<String> keys) {
		Key[] aerospikeKeys = new Key[keys.size()];
		Record[] records = null;
		List<Truck> trucks = null;
		for (int i = 0; i < keys.size(); i++) {
			aerospikeKeys[i] = new Key(aerospikeConfig.getNamespace(), aerospikeConfig.getSet(), keys.get(i));
		}
		try {
			records = this.aerospikeClient.get(null, aerospikeKeys);
		} catch (AerospikeException e) {
		}
		if (null != records && records.length > 0) {
			trucks = new ArrayList<>();
			for (Record record : records) {
				if (null != record) {
					Truck truck = getTruckFromRecord(record);
					trucks.add(truck);
				}
			}
		}
		return trucks;
	}

	@Override
	public boolean put(String key, Truck truck) {
		Key aerospikeKey = new Key(aerospikeConfig.getNamespace(), aerospikeConfig.getSet(), key);
		WritePolicy writePolicy = new WritePolicy();
		writePolicy.recordExistsAction = RecordExistsAction.UPDATE;
		Bin[] bins = getAllBins(truck);
		try {
			this.aerospikeClient.put(writePolicy, aerospikeKey, bins);
		} catch (AerospikeException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean put(String key, Truck truck, Integer expiry) {
		Key aerospikeKey = new Key(aerospikeConfig.getNamespace(), aerospikeConfig.getSet(), key);
		WritePolicy writePolicy = new WritePolicy();
		writePolicy.recordExistsAction = RecordExistsAction.UPDATE;
		if (null != expiry) {
			writePolicy.expiration = expiry;
		}
		Bin truckId = new Bin(AeroSpikeConstant.BIN_NAME_TRUCK_ID, truck.getTruckId());
		Bin locId = new Bin(AeroSpikeConstant.BIN_NAME_LOCATION_ID, truck.getLocationId());
		Bin applicantName = new Bin(AeroSpikeConstant.BIN_NAME_APPLICANT_NAME, truck.getApllicantName());
		try {
			this.aerospikeClient.put(writePolicy, aerospikeKey, truckId, locId, applicantName);
		} catch (AerospikeException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean update(String key, Truck truck) {
		return false;
	}

	@Override
	public List<Truck> queryByName(String[] bin, String value) {
		Statement stmt = new Statement();
		List<Truck> trucks = new ArrayList<>();
		stmt.setNamespace(aerospikeConfig.getNamespace());
		stmt.setSetName(aerospikeConfig.getSet());
		stmt.setBinNames(bin);
		stmt.setPredExp(PredExp.stringBin(bin[0]), PredExp.stringValue(value),
				PredExp.stringRegex(RegexFlag.ICASE | RegexFlag.NEWLINE));
		RecordSet records = aerospikeClient.query(null, stmt);
		try {
			while (records.next()) {
				Record record = records.getRecord();
				Truck truck = getTruckFromRecord(record);
				trucks.add(truck);
			}
		} finally {
			records.close();
		}
		return trucks;
	}

	@Override
	public List<Truck> queryByLocation(String binNameLatLonGeo, String[] bin, Double lat, Double lng, int radius) {
		Statement stmt = new Statement();
		List<Truck> trucks = new ArrayList<>();
		stmt.setNamespace(aerospikeConfig.getNamespace());
		stmt.setSetName(aerospikeConfig.getSet());
		stmt.setBinNames(bin);
		stmt.setFilter(Filter.geoWithinRadius(binNameLatLonGeo, lng, lat, radius));
		RecordSet records = aerospikeClient.query(null, stmt);
		try {
			while (records.next()) {
				Record record = records.getRecord();
				Truck qTruck = getTruckFromRecord(record);
				trucks.add(qTruck);
			}
		} finally {
			records.close();
		}
		return trucks;
	}

	@Override
	public List<Truck> queryByExpiry(String binExpDate, String[] bin, long begin, long end) {
		Statement stmt = new Statement();
		List<Truck> trucks = new ArrayList<>();
		stmt.setNamespace(aerospikeConfig.getNamespace());
		stmt.setSetName(aerospikeConfig.getSet());
		stmt.setBinNames(bin);
		stmt.setFilter(Filter.range(binExpDate, begin, end));
		RecordSet records = aerospikeClient.query(null, stmt);
		try {
			while (records.next()) {
				Record record = records.getRecord();
				Truck qTruck = getTruckFromRecord(record);
				trucks.add(qTruck);
			}
		} finally {
			records.close();
		}
		return trucks;
	}

	private Truck getTruckFromRecord(Record record) {
		Truck truck = new Truck();
		truck.setTruckId((String) record.getValue(AeroSpikeConstant.BIN_NAME_TRUCK_ID));
		truck.setApllicantName((String) record.bins.get(AeroSpikeConstant.BIN_NAME_APPLICANT_NAME));
		truck.setFacilityType(
				(FacilityType.valueOf((String) record.bins.get(AeroSpikeConstant.BIN_NAME_FACILITY_TYPE))));
		truck.setLocationId((Long) record.bins.get(AeroSpikeConstant.BIN_NAME_LOCATION_ID));
		truck.setLocationDescription((String) record.getValue(AeroSpikeConstant.BIN_NAME_LOC_DESC));
		String[] loc = getLatLong(((GeoJSONValue) record.bins.get(AeroSpikeConstant.BIN_NAME_LAT_LON_GEO)).toString());
		if (null != loc && loc.length > 0) {
			truck.setLatitude(Double.valueOf(loc[1].trim()));
			truck.setLongitude(Double.valueOf(loc[0].trim()));
		}
		truck.setExpirationDate(((Long) record.bins.get(AeroSpikeConstant.BIN_NAME_EXPIRATION_DATE)));
		return truck;
	}

	private String[] getLatLong(String latLong) {
		if (null != latLong) {
			String str1 = latLong.split(CommonConstant.OPEN_SQUARE_BRACKET)[1];
			String str2 = str1.split(CommonConstant.CLOSE_SQUARE_BRACKET)[0];
			return str2.split(CommonConstant.COMMA);
		} else {
			return null;
		}

	}

	private Bin[] getAllBins(Truck truck) {
		Bin[] bins = new Bin[7];

		bins[0] = new Bin(AeroSpikeConstant.BIN_NAME_TRUCK_ID, truck.getTruckId());
		bins[1] = new Bin(AeroSpikeConstant.BIN_NAME_LOCATION_ID, truck.getLocationId());
		bins[2] = new Bin(AeroSpikeConstant.BIN_NAME_APPLICANT_NAME, truck.getApllicantName());
		bins[3] = new Bin(AeroSpikeConstant.BIN_NAME_FACILITY_TYPE, truck.getFacilityType().name());
		bins[4] = new Bin(AeroSpikeConstant.BIN_NAME_EXPIRATION_DATE, truck.getExpirationDate());
		bins[5] = Bin.asGeoJSON(AeroSpikeConstant.BIN_NAME_LAT_LON_GEO, getGeo2DString(truck).toString());
		bins[6] = new Bin(AeroSpikeConstant.BIN_NAME_LOC_DESC, truck.getLocationDescription());
		return bins;
	}

	private StringBuilder getGeo2DString(Truck truck) {
		return AerospikeUtil.getGeo2DString(String.valueOf(truck.getLatitude()), String.valueOf(truck.getLongitude()));
	}

	@Override
	public List<Truck> queryByStreetName(String locDescBin, String[] defaultBins, String streetName) {
		Statement stmt = new Statement();
		List<Truck> trucks = new ArrayList<>();
		stmt.setNamespace(aerospikeConfig.getNamespace());
		stmt.setSetName(aerospikeConfig.getSet());
		stmt.setBinNames(defaultBins);
		stmt.setPredExp(PredExp.stringBin(locDescBin), PredExp.stringValue(streetName),
				PredExp.stringRegex(RegexFlag.ICASE | RegexFlag.NEWLINE));
		RecordSet records = aerospikeClient.query(null, stmt);
		try {
			while (records.next()) {
				Record record = records.getRecord();
				Truck truck = getTruckFromRecord(record);
				trucks.add(truck);
			}
		} finally {
			records.close();
		}
		return trucks;
	}

	@Override
	public boolean delete(String truckId) {
		Key aerospikeKey = new Key(aerospikeConfig.getNamespace(), aerospikeConfig.getSet(), truckId);
		aerospikeClient.delete(null, aerospikeKey);
		return true;
	}

}
