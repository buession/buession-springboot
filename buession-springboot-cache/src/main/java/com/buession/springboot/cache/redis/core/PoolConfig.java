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
 * | Copyright @ 2013-2021 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.cache.redis.core;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;

/**
 * 连接池配置
 *
 * @author Yong.Teng
 */
public class PoolConfig {

	/**
	 * 池模式，为 true 时，后进先出；为 false 时，先进先出
	 */
	private boolean lifo = GenericObjectPoolConfig.DEFAULT_LIFO;

	/**
	 * 当从池中获取资源或者将资源还回池中时，是否使用；
	 * java.util.concurrent.locks.ReentrantLock.ReentrantLock 的公平锁机制。
	 */
	private boolean fairness = GenericObjectPoolConfig.DEFAULT_FAIRNESS;

	/**
	 * 当连接池资源用尽后，调用者获取连接时的最大等待时间（单位 ：毫秒）
	 */
	@Deprecated
	private long maxWaitMillis = GenericObjectPoolConfig.DEFAULT_MAX_WAIT_MILLIS;

	/**
	 * 当连接池资源用尽后，调用者获取连接时的最大等待时间（单位 ：毫秒）
	 */
	private long maxWait = GenericObjectPoolConfig.DEFAULT_MAX_WAIT_MILLIS;

	/**
	 * 连接的最小空闲时间，达到此值后且已达最大空闲连接数该空闲连接可能会被移除（单位 ：毫秒）
	 */
	@Deprecated
	private long minEvictableIdleTimeMillis = GenericObjectPoolConfig.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS;

	/**
	 * 连接的最小空闲时间，达到此值后且已达最大空闲连接数该空闲连接可能会被移除（单位 ：毫秒）
	 */
	private long minEvictableIdleTime = GenericObjectPoolConfig.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS;

	/**
	 * 连接空闲的最小时间，达到此值后空闲链接将会被移除，且保留 minIdle 个空闲连接数（单位 ：毫秒）
	 */
	@Deprecated
	private long softMinEvictableIdleTimeMillis = GenericObjectPoolConfig.DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS;

	/**
	 * 连接空闲的最小时间，达到此值后空闲链接将会被移除，且保留 minIdle 个空闲连接数（单位 ：毫秒）
	 */
	private long softMinEvictableIdleTime = GenericObjectPoolConfig.DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS;

	/**
	 * 驱逐策略的类名
	 */
	private String evictionPolicyClassName = GenericObjectPoolConfig.DEFAULT_EVICTION_POLICY_CLASS_NAME;

	/**
	 * 关闭驱逐线程的超时时间
	 */
	@Deprecated
	private long evictorShutdownTimeoutMillis = GenericObjectPoolConfig.DEFAULT_EVICTOR_SHUTDOWN_TIMEOUT_MILLIS;

	/**
	 * 关闭驱逐线程的超时时间
	 */
	private long evictorShutdownTimeout = GenericObjectPoolConfig.DEFAULT_EVICTOR_SHUTDOWN_TIMEOUT_MILLIS;

	/**
	 * 检测空闲对象线程每次运行时检测的空闲对象的数量；
	 * 如果 numTestsPerEvictionRun >= 0, 则取 numTestsPerEvictionRun 和池内的连接数的较小值作为每次检测的连接数；
	 * 如果 numTestsPerEvictionRun < 0，则每次检查的连接数是检查时池内连接的总数除以这个值的绝对值再向上取整的结果
	 */
	private int numTestsPerEvictionRun = GenericObjectPoolConfig.DEFAULT_NUM_TESTS_PER_EVICTION_RUN;

	/**
	 * 在创建对象时检测对象是否有效，配置 true 会降低性能
	 */
	private boolean testOnCreate = GenericObjectPoolConfig.DEFAULT_TEST_ON_CREATE;

	/**
	 * 在从对象池获取对象时是否检测对象有效，配置 true 会降低性能
	 */
	private boolean testOnBorrow = GenericObjectPoolConfig.DEFAULT_TEST_ON_BORROW;

	/**
	 * 在向对象池中归还对象时是否检测对象有效，配置 true 会降低性能
	 */
	private boolean testOnReturn = GenericObjectPoolConfig.DEFAULT_TEST_ON_RETURN;

	/**
	 * 在检测空闲对象线程检测到对象不需要移除时，是否检测对象的有效性。建议配置为 true，不影响性能，并且保证安全性；
	 */
	private boolean testWhileIdle = GenericObjectPoolConfig.DEFAULT_TEST_WHILE_IDLE;

	/**
	 * 空闲连接检测的周期，如果为负值，表示不运行检测线程（单位 ：毫秒）
	 */
	@Deprecated
	private long timeBetweenEvictionRunsMillis = GenericObjectPoolConfig.DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;

	/**
	 * 空闲连接检测的周期，如果为负值，表示不运行检测线程（单位 ：毫秒）
	 */
	private long timeBetweenEvictionRuns = GenericObjectPoolConfig.DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;

	/**
	 * 当对象池没有空闲对象时，新的获取对象的请求是否阻塞（true 阻塞，maxWaitMillis 才生效； false 连接池没有资源立马抛异常）
	 */
	private boolean blockWhenExhausted = GenericObjectPoolConfig.DEFAULT_BLOCK_WHEN_EXHAUSTED;

	/**
	 * 是否注册 JMX
	 */
	private boolean jmxEnabled = GenericObjectPoolConfig.DEFAULT_JMX_ENABLE;

	/**
	 * JMX 前缀
	 */
	private String jmxNamePrefix = GenericObjectPoolConfig.DEFAULT_JMX_NAME_PREFIX;

	/**
	 * 使用 base + jmxNamePrefix + i 来生成 ObjectName
	 */
	private String jmxNameBase = GenericObjectPoolConfig.DEFAULT_JMX_NAME_BASE;

	/**
	 * 最大连接数
	 */
	private int maxTotal = GenericObjectPoolConfig.DEFAULT_MAX_TOTAL;

	/**
	 * 最小空闲连接数
	 */
	private int minIdle = GenericObjectPoolConfig.DEFAULT_MIN_IDLE;

	/**
	 * 最大空闲连接数
	 */
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

	public boolean isFairness(){
		return getFairness();
	}

	public boolean getFairness(){
		return fairness;
	}

	public void setFairness(boolean fairness){
		this.fairness = fairness;
	}

	@Deprecated
	@DeprecatedConfigurationProperty(reason = "移除时间属性中的 Milli", replacement = "spring.redis.max-wait")
	public long getMaxWaitMillis(){
		return maxWaitMillis;
	}

	@Deprecated
	@DeprecatedConfigurationProperty(reason = "移除时间属性中的 Milli", replacement = "spring.redis.max-wait")
	public void setMaxWaitMillis(long maxWaitMillis){
		this.maxWaitMillis = maxWaitMillis;
		setMaxWait(maxWaitMillis);
	}

	public long getMaxWait(){
		return maxWait;
	}

	public void setMaxWait(long maxWait){
		this.maxWait = maxWait;
	}

	@Deprecated
	@DeprecatedConfigurationProperty(reason = "移除时间属性中的 Milli", replacement =
			"spring.redis" + ".min-evictable-idle" + "-time-millis")
	public long getMinEvictableIdleTimeMillis(){
		return minEvictableIdleTimeMillis;
	}

	@Deprecated
	@DeprecatedConfigurationProperty(reason = "移除时间属性中的 Milli", replacement =
			"spring.redis" + ".min-evictable-idle" + "-time-millis")
	public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis){
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
		setMinEvictableIdleTime(minEvictableIdleTimeMillis);
	}

	public long getMinEvictableIdleTime(){
		return minEvictableIdleTime;
	}

	public void setMinEvictableIdleTime(long minEvictableIdleTime){
		this.minEvictableIdleTime = minEvictableIdleTime;
	}

	@Deprecated
	@DeprecatedConfigurationProperty(reason = "移除时间属性中的 Milli", replacement =
			"spring.redis" + ".soft-min-evictable" + "-idle-time-millis")
	public long getSoftMinEvictableIdleTimeMillis(){
		return softMinEvictableIdleTimeMillis;
	}

	@Deprecated
	@DeprecatedConfigurationProperty(reason = "移除时间属性中的 Milli", replacement =
			"spring.redis" + ".soft-min-evictable" + "-idle-time-millis")
	public void setSoftMinEvictableIdleTimeMillis(long softMinEvictableIdleTimeMillis){
		this.softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
		setSoftMinEvictableIdleTime(softMinEvictableIdleTimeMillis);
	}

	public long getSoftMinEvictableIdleTime(){
		return softMinEvictableIdleTime;
	}

	public void setSoftMinEvictableIdleTime(long softMinEvictableIdleTime){
		this.softMinEvictableIdleTime = softMinEvictableIdleTime;
	}

	public String getEvictionPolicyClassName(){
		return evictionPolicyClassName;
	}

	public void setEvictionPolicyClassName(String evictionPolicyClassName){
		this.evictionPolicyClassName = evictionPolicyClassName;
	}

	@Deprecated
	@DeprecatedConfigurationProperty(reason = "移除时间属性中的 Milli", replacement = "spring.redis" + ".evictor-shutdown" +
			"-timeout-millis")
	public long getEvictorShutdownTimeoutMillis(){
		return evictorShutdownTimeoutMillis;
	}

	@Deprecated
	@DeprecatedConfigurationProperty(reason = "移除时间属性中的 Milli", replacement = "spring.redis" + ".evictor-shutdown" +
			"-timeout-millis")
	public void setEvictorShutdownTimeoutMillis(long evictorShutdownTimeoutMillis){
		this.evictorShutdownTimeoutMillis = evictorShutdownTimeoutMillis;
		setEvictorShutdownTimeout(evictorShutdownTimeoutMillis);
	}

	public long getEvictorShutdownTimeout(){
		return evictorShutdownTimeout;
	}

	public void setEvictorShutdownTimeout(long evictorShutdownTimeout){
		this.evictorShutdownTimeout = evictorShutdownTimeout;
	}

	public int getNumTestsPerEvictionRun(){
		return numTestsPerEvictionRun;
	}

	public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun){
		this.numTestsPerEvictionRun = numTestsPerEvictionRun;
	}

	public boolean isTestOnCreate(){
		return getTestOnCreate();
	}

	public boolean getTestOnCreate(){
		return testOnCreate;
	}

	public void setTestOnCreate(boolean testOnCreate){
		this.testOnCreate = testOnCreate;
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

	@Deprecated
	@DeprecatedConfigurationProperty(reason = "移除时间属性中的 Milli", replacement = "spring.redis" + ".time-between-eviction"
			+ "-runs-millis")
	public long getTimeBetweenEvictionRunsMillis(){
		return timeBetweenEvictionRunsMillis;
	}

	@Deprecated
	@DeprecatedConfigurationProperty(reason = "移除时间属性中的 Milli", replacement = "spring.redis" + ".time-between-eviction"
			+ "-runs-millis")
	public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis){
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
		setTimeBetweenEvictionRuns(timeBetweenEvictionRunsMillis);
	}

	public long getTimeBetweenEvictionRuns(){
		return timeBetweenEvictionRuns;
	}

	public void setTimeBetweenEvictionRuns(long timeBetweenEvictionRuns){
		this.timeBetweenEvictionRuns = timeBetweenEvictionRuns;
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

	public String getJmxNameBase(){
		return jmxNameBase;
	}

	public void setJmxNameBase(String jmxNameBase){
		this.jmxNameBase = jmxNameBase;
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
