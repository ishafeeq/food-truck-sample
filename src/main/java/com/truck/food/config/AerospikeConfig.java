package com.truck.food.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AerospikeConfig {

    private String hostAndPort = "aerospike.mygate.com:3000";
    private String namespace = "test";
    private String set = "truck";
    private int readPolicySocketTimeout = 1000;
    private int readPolicyTotalTimeout = 1000;
    private int readPolicySleepBetweenRetries = 10;
    private int writePolicySocketTimeout = 1000;
    private int writePolicyTotalTimeout = 1000;
    private int writePolicySleepBetweenRetries = 10;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public int getReadPolicySocketTimeout() {
        return readPolicySocketTimeout;
    }

    public void setReadPolicySocketTimeout(int readPolicySocketTimeout) {
        this.readPolicySocketTimeout = readPolicySocketTimeout;
    }

    public int getReadPolicyTotalTimeout() {
        return readPolicyTotalTimeout;
    }

    public void setReadPolicyTotalTimeout(int readPolicyTotalTimeout) {
        this.readPolicyTotalTimeout = readPolicyTotalTimeout;
    }

    public int getReadPolicySleepBetweenRetries() {
        return readPolicySleepBetweenRetries;
    }

    public void setReadPolicySleepBetweenRetries(int readPolicySleepBetweenRetries) {
        this.readPolicySleepBetweenRetries = readPolicySleepBetweenRetries;
    }

    public int getWritePolicySocketTimeout() {
        return writePolicySocketTimeout;
    }

    public void setWritePolicySocketTimeout(int writePolicySocketTimeout) {
        this.writePolicySocketTimeout = writePolicySocketTimeout;
    }

    public int getWritePolicyTotalTimeout() {
        return writePolicyTotalTimeout;
    }

    public void setWritePolicyTotalTimeout(int writePolicyTotalTimeout) {
        this.writePolicyTotalTimeout = writePolicyTotalTimeout;
    }

    public int getWritePolicySleepBetweenRetries() {
        return writePolicySleepBetweenRetries;
    }

    public void setWritePolicySleepBetweenRetries(int writePolicySleepBetweenRetries) {
        this.writePolicySleepBetweenRetries = writePolicySleepBetweenRetries;
    }

    public String getHostAndPort() {
        return hostAndPort;
    }

    public void setHostAndPort(String hostAndPort) {
        this.hostAndPort = hostAndPort;
    }
    
}
