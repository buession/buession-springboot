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
package com.buession.springboot.datasource.config;

import java.sql.PreparedStatement;
import java.time.Duration;
import java.util.Set;

/**
 * DBCP2 数据源配置
 *
 * @author Yong.Teng
 * @since 3.0.0
 */
public class Dbcp2DataSourceConfig extends BaseDataSourceConfig {

	/**
	 * 实现 {@link org.apache.commons.dbcp2.ConnectionFactory} 接口，连接工厂实现类
	 */
	private String connectionFactoryClassName;

	/**
	 * 验证快速失败
	 */
	private Boolean fastFailValidation;

	/**
	 * 一个过期的连接被连接池关闭时，写日志标识；
	 * 如果连接存货时间超过 maxConnLifetimeMillis，连接将被连接池会抽，此时默认输出日志
	 */
	private Boolean logExpiredConnections;

	/**
	 * 连接归还到池时，设置为自动提交
	 */
	private Boolean autoCommitOnReturn;

	/**
	 * 连接归还到池时，是否回滚所有操作
	 */
	private Boolean rollbackOnReturn;

	/**
	 * 缓存状态，如果设置为true，
	 * 池化的连接将在第一次读或写，以及随后的写的时候缓存当前的只读状态和自动提交设置。
	 * 这样就省去了对 getter 的任何进一步的调用时对数据库的额外查询。
	 * 如果直接访问底层连接，只读状态和/或自动提交设置改变缓存值将不会被反映到当前的状态，在这种情况下，应该将该属性设置为false以禁用缓存
	 */
	private Boolean cacheState;

	/**
	 * 用于指定一组 SQL 错误代码，当连接池检测到这些错误代码时，会将连接标记为失效并将其从连接池中移除
	 */
	private Set<String> disconnectionSqlCodes;

	/**
	 * 连接的最大存活时间；
	 * 如果超过这个时间，则连接在下次激活、钝化、校验时都将会失败。如果设置为0或小于0的值，则连接的存活时间是无限的
	 */
	private Duration maxConnLifetime;

	/**
	 * 是否缓存 preparedStatement，也就是PSCache；
	 * PSCache 对支持游标的数据库性能提升巨大，比如说 oracle，在mysql下建议关闭
	 */
	private Boolean poolPreparedStatements;

	/**
	 * 可以在语句池中同时分配的最大语句数
	 */
	private Integer maxOpenPreparedStatements;

	/**
	 * 是否清除与该连接关联的已缓存的 {@link PreparedStatement} 对象；启用后，每次连接返回到池中时，所有缓存的 PreparedStatement 对象都会被清除
	 */
	private Boolean clearStatementPoolOnReturn;

	/**
	 * 从连接池借用连接时是否对废弃连接的检查
	 */
	private Boolean removeAbandonedOnBorrow;

	/**
	 * 后台维护线程会在运行时定期检查连接池中的连接，并移除那些被认为是废弃的连接
	 */
	private Boolean removeAbandonedOnMaintenance;

	/**
	 * 是否在每次连接被借出时记录当前的堆栈跟踪信息
	 */
	private Boolean abandonedUsageTracking;

	/**
	 * 空闲的连接被释放最低要待时间，但有额外条件
	 * 额外的条件是池中至少保留有 minIdle 所指定的个数的连接；
	 * 当 miniEvictableIdleTime 被设置为一个正数，空闲连接驱逐者首先检测 miniEvictableIdleTime ，
	 * 当空闲连接被驱逐者访问时，首先与 miniEvictableIdleTime 所指定的值进行比较（而不考虑当前池中的空闲连接数），
	 * 然后比较 softMinEvictableIdleTime 所指定的连接数，包括 minIdle 条件
	 */
	private Duration softMinEvictableIdle;

	/**
	 * 空闲对象驱逐策略名称
	 */
	private String evictionPolicyClassName;

	/**
	 * 后进先出，设置为true表明连接池（如果池中有可用的空闲连接时）将返回最后一次使用的租借对象（最后进入），
	 * 设置为false则表明池将表现为FIFO队列（先进先出）—将会按照它们被归还的顺序从空闲连接实例池中获取连接
	 */
	private Boolean lifo;

	/**
	 * 返回 {@link org.apache.commons.dbcp2.ConnectionFactory} 接口实现，连接工厂实现
	 *
	 * @return {@link org.apache.commons.dbcp2.ConnectionFactory} 接口实现，连接工厂实现类
	 */
	public String getConnectionFactoryClassName() {
		return connectionFactoryClassName;
	}

	/**
	 * 设置{@link org.apache.commons.dbcp2.ConnectionFactory} 接口实现，连接工厂实现类
	 *
	 * @param connectionFactoryClassName
	 *        {@link org.apache.commons.dbcp2.ConnectionFactory} 接口实现，连接工厂实现类
	 */
	public void setConnectionFactoryClassName(String connectionFactoryClassName) {
		this.connectionFactoryClassName = connectionFactoryClassName;
	}

	/**
	 * 返回验证快速失败
	 *
	 * @return 验证快速失败
	 */
	public Boolean getFastFailValidation() {
		return fastFailValidation;
	}

	/**
	 * 设置验证快速失败
	 *
	 * @param fastFailValidation
	 * 		验证快速失败
	 */
	public void setFastFailValidation(Boolean fastFailValidation) {
		this.fastFailValidation = fastFailValidation;
	}

	/**
	 * 返回一个过期的连接被连接池关闭时，写日志标识
	 *
	 * @return 一个过期的连接被连接池关闭时，写日志标识
	 */
	public Boolean getLogExpiredConnections() {
		return logExpiredConnections;
	}

	/**
	 * 设置一个过期的连接被连接池关闭时，写日志标识
	 *
	 * @param logExpiredConnections
	 * 		一个过期的连接被连接池关闭时，写日志标识
	 */
	public void setLogExpiredConnections(Boolean logExpiredConnections) {
		this.logExpiredConnections = logExpiredConnections;
	}

	/**
	 * 返回连接归还到池时，设置为自动提交
	 *
	 * @return 连接归还到池时，设置为自动提交
	 */
	public Boolean getAutoCommitOnReturn() {
		return autoCommitOnReturn;
	}

	/**
	 * 设置连接归还到池时，是否自动提交
	 *
	 * @param autoCommitOnReturn
	 * 		连接归还到池时，是否自动提交
	 */
	public void setAutoCommitOnReturn(Boolean autoCommitOnReturn) {
		this.autoCommitOnReturn = autoCommitOnReturn;
	}

	/**
	 * 返回连接归还到池时，是否回滚所有操作
	 *
	 * @return 连接归还到池时，是否回滚所有操作
	 */
	public Boolean getRollbackOnReturn() {
		return rollbackOnReturn;
	}

	/**
	 * 设置连接归还到池时，是否回滚所有操作
	 *
	 * @param rollbackOnReturn
	 * 		连接归还到池时，是否回滚所有操作
	 */
	public void setRollbackOnReturn(Boolean rollbackOnReturn) {
		this.rollbackOnReturn = rollbackOnReturn;
	}

	/**
	 * 返回缓存状态
	 *
	 * @return 缓存状态
	 */
	public Boolean getCacheState() {
		return cacheState;
	}

	/**
	 * 设置缓存状态
	 *
	 * @param cacheState
	 * 		缓存状态
	 */
	public void setCacheState(Boolean cacheState) {
		this.cacheState = cacheState;
	}

	/**
	 * 返回指定一组 SQL 错误代码，当连接池检测到这些错误代码时，会将连接标记为失效并将其从连接池中移除
	 *
	 * @return 指定的 SQL 错误代码，当连接池检测到这些错误代码时，会将连接标记为失效并将其从连接池中移除
	 */
	public Set<String> getDisconnectionSqlCodes() {
		return disconnectionSqlCodes;
	}

	/**
	 * 设置一组 SQL 错误代码，当连接池检测到这些错误代码时，会将连接标记为失效并将其从连接池中移除
	 *
	 * @param disconnectionSqlCodes
	 * 		一组 SQL 错误代码，当连接池检测到这些错误代码时，会将连接标记为失效并将其从连接池中移除
	 */
	public void setDisconnectionSqlCodes(Set<String> disconnectionSqlCodes) {
		this.disconnectionSqlCodes = disconnectionSqlCodes;
	}

	/**
	 * 返回连接的最大存活时间
	 *
	 * @return 连接的最大存活时间
	 */
	public Duration getMaxConnLifetime() {
		return maxConnLifetime;
	}

	/**
	 * 设置连接的最大存活时间
	 *
	 * @param maxConnLifetime
	 * 		连接的最大存活时间
	 */
	public void setMaxConnLifetime(Duration maxConnLifetime) {
		this.maxConnLifetime = maxConnLifetime;
	}

	/**
	 * 返回是否缓存 preparedStatement，也就是PSCache
	 *
	 * @return 是否缓存 preparedStatement，也就是PSCache
	 */
	public Boolean getPoolPreparedStatements() {
		return poolPreparedStatements;
	}

	/**
	 * 设置是否缓存 preparedStatement，也就是PSCache；
	 * PSCache 对支持游标的数据库性能提升巨大，比如说 oracle，在mysql下建议关闭
	 *
	 * @param poolPreparedStatements
	 * 		是否缓存 preparedStatement
	 */
	public void setPoolPreparedStatements(Boolean poolPreparedStatements) {
		this.poolPreparedStatements = poolPreparedStatements;
	}

	/**
	 * 返回可以在语句池中同时分配的最大语句数
	 *
	 * @return 可以在语句池中同时分配的最大语句数
	 */
	public Integer getMaxOpenPreparedStatements() {
		return maxOpenPreparedStatements;
	}

	/**
	 * 设置可以在语句池中同时分配的最大语句数
	 *
	 * @param maxOpenPreparedStatements
	 * 		可以在语句池中同时分配的最大语句数
	 */
	public void setMaxOpenPreparedStatements(Integer maxOpenPreparedStatements) {
		this.maxOpenPreparedStatements = maxOpenPreparedStatements;
	}

	/**
	 * 返回是否清除与该连接关联的已缓存的 {@link PreparedStatement} 对象
	 *
	 * @return 是否清除与该连接关联的已缓存的 {@link PreparedStatement} 对象
	 */
	public Boolean getClearStatementPoolOnReturn() {
		return clearStatementPoolOnReturn;
	}

	/**
	 * 设置是否清除与该连接关联的已缓存的 {@link PreparedStatement} 对象；启用后，每次连接返回到池中时，所有缓存的 PreparedStatement 对象都会被清除
	 *
	 * @param clearStatementPoolOnReturn
	 * 		是否清除与该连接关联的已缓存的 {@link PreparedStatement} 对象
	 */
	public void setClearStatementPoolOnReturn(Boolean clearStatementPoolOnReturn) {
		this.clearStatementPoolOnReturn = clearStatementPoolOnReturn;
	}

	/**
	 * 返回从连接池借用连接时是否对废弃连接的检查
	 *
	 * @return 从连接池借用连接时是否对废弃连接的检查
	 */
	public Boolean getRemoveAbandonedOnBorrow() {
		return removeAbandonedOnBorrow;
	}

	/**
	 * 设置从连接池借用连接时是否对废弃连接的检查
	 *
	 * @param removeAbandonedOnBorrow
	 * 		从连接池借用连接时是否对废弃连接的检查
	 */
	public void setRemoveAbandonedOnBorrow(Boolean removeAbandonedOnBorrow) {
		this.removeAbandonedOnBorrow = removeAbandonedOnBorrow;
	}

	/**
	 * 返回后台维护线程会在运行时定期检查连接池中的连接，并移除那些被认为是废弃的连接
	 *
	 * @return 后台维护线程会在运行时定期检查连接池中的连接，并移除那些被认为是废弃的连接
	 */
	public Boolean getRemoveAbandonedOnMaintenance() {
		return removeAbandonedOnMaintenance;
	}

	/**
	 * 设置后台维护线程会在运行时定期检查连接池中的连接，并移除那些被认为是废弃的连接
	 *
	 * @param removeAbandonedOnMaintenance
	 * 		后台维护线程会在运行时定期检查连接池中的连接，并移除那些被认为是废弃的连接
	 */
	public void setRemoveAbandonedOnMaintenance(Boolean removeAbandonedOnMaintenance) {
		this.removeAbandonedOnMaintenance = removeAbandonedOnMaintenance;
	}

	/**
	 * 返回是否在每次连接被借出时记录当前的堆栈跟踪信息
	 *
	 * @return 是否在每次连接被借出时记录当前的堆栈跟踪信息
	 */
	public Boolean getAbandonedUsageTracking() {
		return abandonedUsageTracking;
	}

	/**
	 * 设置是否在每次连接被借出时记录当前的堆栈跟踪信息
	 *
	 * @param abandonedUsageTracking
	 * 		是否在每次连接被借出时记录当前的堆栈跟踪信息
	 */
	public void setAbandonedUsageTracking(Boolean abandonedUsageTracking) {
		this.abandonedUsageTracking = abandonedUsageTracking;
	}

	/**
	 * 返回空闲的连接被释放最低要待时间，但有额外条件
	 *
	 * @return 空闲的连接被释放最低要待时间
	 */
	public Duration getSoftMinEvictableIdle() {
		return softMinEvictableIdle;
	}

	/**
	 * 设置空闲的连接被释放最低要待时间
	 *
	 * @param softMinEvictableIdle
	 * 		空闲的连接被释放最低要待时间
	 */
	public void setSoftMinEvictableIdle(Duration softMinEvictableIdle) {
		this.softMinEvictableIdle = softMinEvictableIdle;
	}

	/**
	 * 返回空闲对象驱逐策略名称
	 *
	 * @return 空闲对象驱逐策略名称
	 */
	public String getEvictionPolicyClassName() {
		return evictionPolicyClassName;
	}

	/**
	 * 设置空闲对象驱逐策略名称
	 *
	 * @param evictionPolicyClassName
	 * 		空闲对象驱逐策略名称
	 */
	public void setEvictionPolicyClassName(String evictionPolicyClassName) {
		this.evictionPolicyClassName = evictionPolicyClassName;
	}

	/**
	 * 是否后进先出
	 *
	 * @return 后进先出
	 */
	public Boolean getLifo() {
		return lifo;
	}

	/**
	 * 设置后进先出
	 *
	 * @param lifo
	 * 		后进先出
	 */
	public void setLifo(Boolean lifo) {
		this.lifo = lifo;
	}

}
