/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements.
 * See the NOTICE file distributed with this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 *
 * =========================================================================================================
 *
 * This software consists of voluntary contributions made by many individuals on behalf of the
 * Apache Software Foundation. For more information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 * +-------------------------------------------------------------------------------------------------------+
 * | License: http://www.apache.org/licenses/LICENSE-2.0.txt 										       |
 * | Author: Yong.Teng <webmaster@buession.com> 													       |
 * | Copyright @ 2013-2020 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.cache.redis.core;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @author Yong.Teng
 */
public class PoolConfig {

	private boolean lifo = GenericObjectPoolConfig.DEFAULT_LIFO;

	private long maxWaitMillis = GenericObjectPoolConfig.DEFAULT_MAX_WAIT_MILLIS;

	private long minEvictableIdleTimeMillis = GenericObjectPoolConfig.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS;

	private long softMinEvictableIdleTimeMillis = GenericObjectPoolConfig.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS;

	private int numTestsPerEvictionRun = GenericObjectPoolConfig.DEFAULT_NUM_TESTS_PER_EVICTION_RUN;

	private String evictionPolicyClassName = GenericObjectPoolConfig.DEFAULT_EVICTION_POLICY_CLASS_NAME;

	private boolean testOnBorrow = GenericObjectPoolConfig.DEFAULT_TEST_ON_BORROW;

	private boolean testOnReturn = GenericObjectPoolConfig.DEFAULT_TEST_ON_RETURN;

	private boolean testWhileIdle = GenericObjectPoolConfig.DEFAULT_TEST_WHILE_IDLE;

	private long timeBetweenEvictionRunsMillis = GenericObjectPoolConfig.DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;

	private boolean blockWhenExhausted = GenericObjectPoolConfig.DEFAULT_BLOCK_WHEN_EXHAUSTED;

	private boolean jmxEnabled = GenericObjectPoolConfig.DEFAULT_JMX_ENABLE;

	private String jmxNamePrefix = GenericObjectPoolConfig.DEFAULT_JMX_NAME_PREFIX;

	private int maxTotal = GenericObjectPoolConfig.DEFAULT_MAX_TOTAL;

	private int minIdle = GenericObjectPoolConfig.DEFAULT_MIN_IDLE;

	private int maxIdle = GenericObjectPoolConfig.DEFAULT_MAX_IDLE;

	public boolean isLifo(){
		return getLifo();
	}

	public boolean getLifo(){
		return lifo;
	}

	public void setLifo(boolean lifo){
		this.lifo = lifo;
	}

	public long getMaxWaitMillis(){
		return maxWaitMillis;
	}

	public void setMaxWaitMillis(long maxWaitMillis){
		this.maxWaitMillis = maxWaitMillis;
	}

	public long getMinEvictableIdleTimeMillis(){
		return minEvictableIdleTimeMillis;
	}

	public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis){
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public long getSoftMinEvictableIdleTimeMillis(){
		return softMinEvictableIdleTimeMillis;
	}

	public void setSoftMinEvictableIdleTimeMillis(long softMinEvictableIdleTimeMillis){
		this.softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
	}

	public int getNumTestsPerEvictionRun(){
		return numTestsPerEvictionRun;
	}

	public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun){
		this.numTestsPerEvictionRun = numTestsPerEvictionRun;
	}

	public String getEvictionPolicyClassName(){
		return evictionPolicyClassName;
	}

	public void setEvictionPolicyClassName(String evictionPolicyClassName){
		this.evictionPolicyClassName = evictionPolicyClassName;
	}

	public boolean isTestOnBorrow(){
		return getTestOnBorrow();
	}

	public boolean getTestOnBorrow(){
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow){
		this.testOnBorrow = testOnBorrow;
	}

	public boolean isTestOnReturn(){
		return getTestOnReturn();
	}

	public boolean getTestOnReturn(){
		return testOnReturn;
	}

	public void setTestOnReturn(boolean testOnReturn){
		this.testOnReturn = testOnReturn;
	}

	public boolean isTestWhileIdle(){
		return getTestWhileIdle();
	}

	public boolean getTestWhileIdle(){
		return testWhileIdle;
	}

	public void setTestWhileIdle(boolean testWhileIdle){
		this.testWhileIdle = testWhileIdle;
	}

	public long getTimeBetweenEvictionRunsMillis(){
		return timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis){
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public boolean isBlockWhenExhausted(){
		return getBlockWhenExhausted();
	}

	public boolean getBlockWhenExhausted(){
		return blockWhenExhausted;
	}

	public void setBlockWhenExhausted(boolean blockWhenExhausted){
		this.blockWhenExhausted = blockWhenExhausted;
	}

	public boolean isJmxEnabled(){
		return getJmxEnabled();
	}

	public boolean getJmxEnabled(){
		return jmxEnabled;
	}

	public void setJmxEnabled(boolean jmxEnabled){
		this.jmxEnabled = jmxEnabled;
	}

	public String getJmxNamePrefix(){
		return jmxNamePrefix;
	}

	public void setJmxNamePrefix(String jmxNamePrefix){
		this.jmxNamePrefix = jmxNamePrefix;
	}

	public int getMaxTotal(){
		return maxTotal;
	}

	public void setMaxTotal(int maxTotal){
		this.maxTotal = maxTotal;
	}

	public int getMinIdle(){
		return minIdle;
	}

	public void setMinIdle(int minIdle){
		this.minIdle = minIdle;
	}

	public int getMaxIdle(){
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle){
		this.maxIdle = maxIdle;
	}

}
