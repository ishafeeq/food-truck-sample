package com.truck.food.util;

import com.aerospike.client.Host;
import com.aerospike.client.policy.ClientPolicy;
import com.truck.food.config.AerospikeConfig;

public class AerospikeUtil {

	
	public static ClientPolicy getAerospikePolicy(AerospikeConfig aerospikeConfig) {
		ClientPolicy policy = new ClientPolicy();
		policy.readPolicyDefault.socketTimeout = aerospikeConfig.getReadPolicySocketTimeout();
		policy.readPolicyDefault.totalTimeout = aerospikeConfig.getReadPolicyTotalTimeout();
		policy.readPolicyDefault.sleepBetweenRetries = aerospikeConfig.getReadPolicySleepBetweenRetries();
		policy.writePolicyDefault.socketTimeout = aerospikeConfig.getWritePolicySocketTimeout();
		policy.writePolicyDefault.totalTimeout = aerospikeConfig.getWritePolicyTotalTimeout();
		policy.writePolicyDefault.sleepBetweenRetries = aerospikeConfig.getWritePolicySleepBetweenRetries();
		return policy;
	}
	
	public static Host[] getVarArgs(AerospikeConfig aerospikeConfig) {
		String[] hostnames = aerospikeConfig.getHostAndPort().split(",");
		Host[] varArgsHosts = new Host[hostnames.length];
		for (int i = 0; i < hostnames.length; i++) {
			String[] hostAndPort = hostnames[i].split(":");
			varArgsHosts[i] = new Host(hostAndPort[0], Integer.parseInt(hostAndPort[1]));
		}
		return varArgsHosts;
	}

}
