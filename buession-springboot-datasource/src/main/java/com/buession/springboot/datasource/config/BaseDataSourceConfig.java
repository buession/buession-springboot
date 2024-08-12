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
 * | Copyright @ 2013-2024 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.datasource.core;

import com.buession.jdbc.core.Jmx;
import com.buession.jdbc.core.TransactionIsolation;

import java.time.Duration;

/**
 * 数据源配置基类
 *
 * @author Yong.Teng
 * @since 3.0.0
 */
public abstract class BaseDataSourceConfig {

	/***********************************************************/
	/*                     数据源基本配置开始                     */
	/***********************************************************/
	/**
	 * 查询超时时间
	 */
	private Duration queryTimeout;

	/**
	 * 默认事务隔离级别
	 */
	private TransactionIsolation defaultTransactionIsolation = TransactionIsolation.DEFAULT;

	/**
	 * 默认连接是否是只读模式
	 */
	private Boolean defaultReadOnly;

	/**
	 * 默认是否自动提交事务
	 */
	private Boolean defaultAutoCommit;

	/**
	 * PoolGuard 是否可以访问底层连接
	 */
	private Boolean accessToUnderlyingConnectionAllowed;

	/***********************************************************/
	/*                     数据源基本配置结束                     */
	/***********************************************************/


	/***********************************************************/
	/*                       连接池配置开始                      */
	/***********************************************************/
	/**
	 * 连接池的名称
	 */
	private String poolName;

	/**
	 * 初始连接数，池被启动时初始化的创建的连接个数
	 */
	private Integer initialSize;

	/**
	 * 最小空闲连接数，可以在池中保持空闲的最小连接数，低于设置值时，空闲连接将被创建，以努力保持最小空闲连接数 &gt;= minIdle
	 */
	private Integer minIdle;

	/**
	 * 最大空闲连接数，在池中，可以保持空闲状态的最大连接数，超出设置值之外的空闲连接在归还到连接池时将被释放，如设置为负数，则不限制
	 */
	private Integer maxIdle;

	/**
	 * 最大连接数，可以在这个池中同一时刻被分配的有效连接数的最大值，如设置为负数，则不限制
	 */
	private Integer maxTotal;

	/**
	 * 从连接池获取一个连接时，最大的等待时间，设置为-1时，如果没有可用连接，连接池会一直无限期等待，直到获取到连接为止
	 */
	private Duration maxWait;

	/**
	 * 连接创建后，马上验证有效性；
	 * 指明对象在创建后是否需要验证是否有效，如果对象验证失败，则触发对象创建的租借尝试将失败
	 */
	private Boolean testOnCreate;

	/**
	 * 从连接池获取一个连接时，验证有效性；
	 * 指明在从池中租借对象时是否要进行验证有效，如果对象验证失败，则对象将从池子释放，然后我们将尝试租借另一个
	 */
	private Boolean testOnBorrow;

	/**
	 * 连接被归还到连接池时，验证有效性
	 */
	private Boolean testOnReturn;

	/**
	 * 连接空闲时，验证有效性；
	 * 指明对象是否需要通过对象驱逐者进行校验（如果有的话），假如一个对象验证失败，则对象将被从池中释放
	 */
	private Boolean testWhileIdle;

	/**
	 * 设置一个SQL语句, 从连接池获取连接时, 先执行改 sql, 验证连接是否可用
	 */
	private String validationQuery;

	/**
	 * 连接有效SQL的执行查询超时时间
	 */
	private Duration validationQueryTimeout;

	/**
	 * 空闲的连接被释放最低要待时间
	 */
	private Duration minEvictableIdle;

	/**
	 * 空闲的连接被释放最高要待时间
	 */
	private Duration maxEvictableIdle;

	/**
	 * 在每个空闲对象驱逐线程运行过程中中进行检查的对象个数
	 */
	private Integer numTestsPerEvictionRun;

	/**
	 * 空闲对象驱逐线程运行时的休眠时间
	 */
	private Duration timeBetweenEvictionRuns;

	/**
	 * 当连接被认为是废弃并且被移除时是否记录日志
	 */
	private Boolean logAbandoned;

	/**
	 * 指定连接在被认为是废弃连接（abandoned connection）之前的超时时间
	 */
	private Duration removeAbandonedTimeout;

	/**
	 * JMX 管理对象配置
	 */
	private Jmx jmx;
	/***********************************************************/
	/*                       连接池配置结束                      */
	/***********************************************************/

	/**
	 * 返回查询超时时间
	 *
	 * @return 查询超时时间
	 */
	public Duration getQueryTimeout() {
		return queryTimeout;
	}

	/**
	 * 设置查询超时时间
	 *
	 * @param queryTimeout
	 * 		查询超时时间
	 */
	public void setQueryTimeout(Duration queryTimeout) {
		this.queryTimeout = queryTimeout;
	}

	/**
	 * 返回默认事务隔离级别
	 *
	 * @return 默认事务隔离级别
	 */
	public TransactionIsolation getDefaultTransactionIsolation() {
		return defaultTransactionIsolation;
	}

	/**
	 * 设置默认事务隔离级别
	 *
	 * @param defaultTransactionIsolation
	 * 		默认事务隔离级别
	 */
	public void setDefaultTransactionIsolation(TransactionIsolation defaultTransactionIsolation) {
		this.defaultTransactionIsolation = defaultTransactionIsolation;
	}

	/**
	 * 返回连接是否是只读模式
	 *
	 * @return 连接是否是只读模式
	 */
	public Boolean getDefaultReadOnly() {
		return defaultReadOnly;
	}

	/**
	 * 设置连接是否是只读模式
	 *
	 * @param defaultReadOnly
	 * 		连接是否是只读模式
	 */
	public void setDefaultReadOnly(Boolean defaultReadOnly) {
		this.defaultReadOnly = defaultReadOnly;
	}

	/**
	 * 返回是否自动提交事务
	 *
	 * @return 是否自动提交事务
	 */
	public Boolean getDefaultAutoCommit() {
		return defaultAutoCommit;
	}

	/**
	 * 设置是否自动提交事务
	 *
	 * @param defaultAutoCommit
	 * 		是否自动提交事务
	 */
	public void setDefaultAutoCommit(Boolean defaultAutoCommit) {
		this.defaultAutoCommit = defaultAutoCommit;
	}

	/**
	 * 返回 PoolGuard 是否可以访问底层连接
	 *
	 * @return PoolGuard 是否可以访问底层连接
	 */
	public Boolean getAccessToUnderlyingConnectionAllowed() {
		return accessToUnderlyingConnectionAllowed;
	}

	/**
	 * 设置 PoolGuard 是否可以访问底层连接
	 *
	 * @param accessToUnderlyingConnectionAllowed
	 * 		PoolGuard 是否可以访问底层连接
	 */
	public void setAccessToUnderlyingConnectionAllowed(Boolean accessToUnderlyingConnectionAllowed) {
		this.accessToUnderlyingConnectionAllowed = accessToUnderlyingConnectionAllowed;
	}

	/**
	 * 返回用户定义连接池的名称
	 *
	 * @return 用户定义连接池的名称
	 */
	public String getPoolName() {
		return poolName;
	}

	/**
	 * 设置连接池的名称
	 *
	 * @param poolName
	 * 		连接池的名称
	 */
	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

	/**
	 * 返回初始连接数
	 *
	 * @return 初始连接数
	 */
	public Integer getInitialSize() {
		return initialSize;
	}

	/**
	 * 设置初始连接数
	 *
	 * @param initialSize
	 * 		初始连接数
	 */
	public void setInitialSize(Integer initialSize) {
		this.initialSize = initialSize;
	}

	/**
	 * 返回最小空闲连接数
	 *
	 * @return 最小空闲连接数
	 */
	public Integer getMinIdle() {
		return minIdle;
	}

	/**
	 * 设置最小空闲连接数
	 *
	 * @param minIdle
	 * 		最小空闲连接数
	 */
	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}

	/**
	 * 返回最大空闲连接数
	 *
	 * @return 最大空闲连接数
	 */
	public Integer getMaxIdle() {
		return maxIdle;
	}

	/**
	 * 设置最大空闲连接数，如设置为负数，则不限制
	 *
	 * @param maxIdle
	 * 		最大空闲连接数
	 */
	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}

	/**
	 * 返回最大连接数
	 *
	 * @return 最大连接数
	 */
	public Integer getMaxTotal() {
		return maxTotal;
	}

	/**
	 * 设置最大连接数，如设置为负数，则不限制
	 *
	 * @param maxTotal
	 * 		最大连接数
	 */
	public void setMaxTotal(Integer maxTotal) {
		this.maxTotal = maxTotal;
	}

	/**
	 * 返回从连接池获取一个连接时，最大的等待时间
	 *
	 * @return 从连接池获取一个连接时，最大的等待时间
	 */
	public Duration getMaxWait() {
		return maxWait;
	}

	/**
	 * 设置从连接池获取一个连接时，最大的等待时间
	 *
	 * @param maxWait
	 * 		从连接池获取一个连接时，最大的等待时间
	 */
	public void setMaxWait(Duration maxWait) {
		this.maxWait = maxWait;
	}

	/**
	 * 返回连接创建后，是否马上验证有效性
	 *
	 * @return 连接创建后，是否马上验证有效性
	 */
	public Boolean getTestOnCreate() {
		return testOnCreate;
	}

	/**
	 * 设置连接创建后，是否马上验证有效性
	 *
	 * @param testOnCreate
	 * 		连接创建后，是否马上验证有效性
	 */
	public void setTestOnCreate(Boolean testOnCreate) {
		this.testOnCreate = testOnCreate;
	}

	/**
	 * 返回从连接池获取一个连接时，是否验证有效性
	 *
	 * @return 从连接池获取一个连接时，验证有效性
	 */
	public Boolean getTestOnBorrow() {
		return testOnBorrow;
	}

	/**
	 * 设置从连接池获取一个连接时，是否验证有效性
	 *
	 * @param testOnBorrow
	 * 		从连接池获取一个连接时，是否验证有效性
	 */
	public void setTestOnBorrow(Boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	/**
	 * 返回连接被归还到连接池时，是否验证有效性
	 *
	 * @return 连接被归还到连接池时，是否验证有效性
	 */
	public Boolean getTestOnReturn() {
		return testOnReturn;
	}

	/**
	 * 设置连接被归还到连接池时，是否验证有效性
	 *
	 * @param testOnReturn
	 * 		连接被归还到连接池时，是否验证有效性
	 */
	public void setTestOnReturn(Boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	/**
	 * 返回连接空闲时，是否验证有效性
	 *
	 * @return 连接空闲时，是否验证有效性
	 */
	public Boolean getTestWhileIdle() {
		return testWhileIdle;
	}

	/**
	 * 设置连接空闲时，是否验证有效性
	 *
	 * @param testWhileIdle
	 * 		连接空闲时，是否验证有效性
	 */
	public void setTestWhileIdle(Boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	/**
	 * 返回从连接池获取连接时, 验证连接是否可用的SQL语句
	 *
	 * @return 从连接池获取连接时, 验证连接是否可用的SQL语句
	 */
	public String getValidationQuery() {
		return validationQuery;
	}

	/**
	 * 设置从连接池获取连接时, 验证连接是否可用的SQL语句
	 *
	 * @param validationQuery
	 * 		从连接池获取连接时, 验证连接是否可用的SQL语句
	 */
	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

	/**
	 * 返回连接有效SQL的执行查询超时时间
	 *
	 * @return 连接有效SQL的执行查询超时时间
	 */
	public Duration getValidationQueryTimeout() {
		return validationQueryTimeout;
	}

	/**
	 * 设置连接有效SQL的执行查询超时时间
	 *
	 * @param validationQueryTimeout
	 * 		连接有效SQL的执行查询超时时间
	 */
	public void setValidationQueryTimeout(Duration validationQueryTimeout) {
		this.validationQueryTimeout = validationQueryTimeout;
	}

	/**
	 * 返回空闲的连接被释放最低要待时间
	 *
	 * @return 空闲的连接被释放最低要待时间
	 */
	public Duration getMinEvictableIdle() {
		return minEvictableIdle;
	}

	/**
	 * 设置空闲的连接被释放最低要待时间
	 *
	 * @param minEvictableIdle
	 * 		空闲的连接被释放最低要待时间
	 */
	public void setMinEvictableIdle(Duration minEvictableIdle) {
		this.minEvictableIdle = minEvictableIdle;
	}

	/**
	 * 返回空闲的连接被释放最高要待时间
	 *
	 * @return 空闲的连接被释放最高要待时间
	 */
	public Duration getMaxEvictableIdle() {
		return maxEvictableIdle;
	}

	/**
	 * 设置空闲的连接被释放最高要待时间
	 *
	 * @param maxEvictableIdle
	 * 		空闲的连接被释放最高要待时间
	 */
	public void setMaxEvictableIdle(Duration maxEvictableIdle) {
		this.maxEvictableIdle = maxEvictableIdle;
	}

	/**
	 * 返回在每个空闲对象驱逐线程运行过程中中进行检查的对象个数
	 *
	 * @return 在每个空闲对象驱逐线程运行过程中中进行检查的对象个数
	 */
	public Integer getNumTestsPerEvictionRun() {
		return numTestsPerEvictionRun;
	}

	/**
	 * 设置在每个空闲对象驱逐线程运行过程中中进行检查的对象个数
	 *
	 * @param numTestsPerEvictionRun
	 * 		在每个空闲对象驱逐线程运行过程中中进行检查的对象个数
	 */
	public void setNumTestsPerEvictionRun(Integer numTestsPerEvictionRun) {
		this.numTestsPerEvictionRun = numTestsPerEvictionRun;
	}

	/**
	 * 返回空闲对象驱逐线程运行时的休眠时间
	 *
	 * @return 空闲对象驱逐线程运行时的休眠时间
	 */
	public Duration getTimeBetweenEvictionRuns() {
		return timeBetweenEvictionRuns;
	}

	/**
	 * 设置空闲对象驱逐线程运行时的休眠时间
	 *
	 * @param timeBetweenEvictionRuns
	 * 		空闲对象驱逐线程运行时的休眠时间
	 */
	public void setTimeBetweenEvictionRuns(Duration timeBetweenEvictionRuns) {
		this.timeBetweenEvictionRuns = timeBetweenEvictionRuns;
	}

	/**
	 * 返回当连接被认为是废弃并且被移除时是否记录日志
	 *
	 * @return 当连接被认为是废弃并且被移除时是否记录日志
	 */
	public Boolean getLogAbandoned() {
		return logAbandoned;
	}

	/**
	 * 设置当连接被认为是废弃并且被移除时是否记录日志
	 *
	 * @param logAbandoned
	 * 		当连接被认为是废弃并且被移除时是否记录日志
	 */
	public void setLogAbandoned(Boolean logAbandoned) {
		this.logAbandoned = logAbandoned;
	}

	/**
	 * 返回指定连接在被认为是废弃连接（abandoned connection）之前的超时时间
	 *
	 * @return 指定连接在被认为是废弃连接（abandoned connection）之前的超时时间
	 */
	public Duration getRemoveAbandonedTimeout() {
		return removeAbandonedTimeout;
	}

	/**
	 * 设置指定连接在被认为是废弃连接（abandoned connection）之前的超时时间
	 *
	 * @param removeAbandonedTimeout
	 * 		指定连接在被认为是废弃连接（abandoned connection）之前的超时时间
	 */
	public void setRemoveAbandonedTimeout(Duration removeAbandonedTimeout) {
		this.removeAbandonedTimeout = removeAbandonedTimeout;
	}

	/**
	 * 返回 JMX 管理对象配置
	 *
	 * @return JMX 管理对象配置
	 */
	public Jmx getJmx() {
		return jmx;
	}

	/**
	 * 设置 JMX 管理对象配置
	 *
	 * @param jmx
	 * 		JMX 管理对象配置
	 */
	public void setJmx(Jmx jmx) {
		this.jmx = jmx;
	}

}
