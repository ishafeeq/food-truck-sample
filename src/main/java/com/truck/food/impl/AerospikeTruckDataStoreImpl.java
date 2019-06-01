package com.truck.food.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.AerospikeException;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.policy.RecordExistsAction;
import com.aerospike.client.policy.WritePolicy;
import com.aerospike.client.query.Filter;
import com.aerospike.client.query.IndexCollectionType;
import com.aerospike.client.query.PredExp;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.RegexFlag;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;
import com.truck.food.config.AerospikeConfig;
import com.truck.food.constant.AeroSpikeConstant;
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
		stmt.setPredExp(
		        PredExp.stringBin(bin[0]),
		        PredExp.stringValue(value),
		        PredExp.stringRegex(RegexFlag.ICASE | RegexFlag.NEWLINE)
		        );
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

	private Truck getTruckFromRecord(Record record) {
		Truck truck = new Truck();
		truck.setTruckId((String) record.getValue(AeroSpikeConstant.BIN_NAME_TRUCK_ID));
		truck.setApllicantName((String) record.bins.get(AeroSpikeConstant.BIN_NAME_APPLICANT_NAME));
		truck.setFacilityType((FacilityType.valueOf((String)record.bins.get(AeroSpikeConstant.BIN_NAME_FACILITY_TYPE))));
		truck.setLocationId((Long) record.bins.get(AeroSpikeConstant.BIN_NAME_LOCATION_ID));
//		truck.setExpirationDate(new Date((String)record.bins.get(AeroSpikeConstant.BIN_NAME_EXPIRATION_DATE)));

		return truck;
	}

	private Bin[] getAllBins(Truck truck) {
		Bin[] bins = new Bin[5];

		bins[0] = new Bin(AeroSpikeConstant.BIN_NAME_TRUCK_ID, truck.getTruckId());
		bins[1] = new Bin(AeroSpikeConstant.BIN_NAME_LOCATION_ID, truck.getLocationId());
		bins[2] = new Bin(AeroSpikeConstant.BIN_NAME_APPLICANT_NAME, truck.getApllicantName());
		bins[3] = new Bin(AeroSpikeConstant.BIN_NAME_FACILITY_TYPE, truck.getFacilityType().name());
		bins[4] = new Bin(AeroSpikeConstant.BIN_NAME_EXPIRATION_DATE, truck.getExpirationDate().toString());

		return bins;
	}

}
