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

import java.time.Duration;
import java.util.Properties;

/**
 * Oracle 数据源配置
 *
 * @author Yong.Teng
 * @since 3.0.0
 */
public class OracleDataSourceConfig extends BaseDataSourceConfig {

	/**
	 * 网络协议
	 */
	private String networkProtocol;

	/**
	 * 数据库服务器的名称或地址
	 */
	private String serverName;

	/**
	 * 数据库服务器的端口
	 */
	private Integer portNumber;

	/**
	 * 连接到 Oracle 数据库服务的服务名，服务名用于在 Oracle 数据库中标识特定的服务或数据库实例
	 */
	private String serviceName;

	/**
	 * 数据源名称
	 */
	private String dataSourceName;

	/**
	 * 数据源描述
	 */
	private String dataSourceDescription;

	/**
	 * 要连接的 Oracle 数据库的名称
	 */
	private String databaseName;

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 连接到 Oracle Pluggable Database (PDB) 的角色的属性
	 */
	private Properties pdbRoles;

	/**
	 * 连接工厂类名
	 */
	private String connectionFactoryClassName;

	/**
	 * 是否启用快速连接故障切换
	 */
	private Boolean fastConnectionFailoverEnabled;

	/**
	 * Oracle Notification Services (ONS) 的属性
	 */
	private String onsConfiguration;

	/**
	 * 是否启用分片模式
	 */
	private Boolean shardingMode;

	/**
	 * 每个分片（Shard）中允许的最大连接数
	 */
	private Integer maxConnectionsPerShard;

	/**
	 * 每个服务的最大连接数
	 */
	private Integer maxConnectionsPerService;

	/**
	 * 连接池中最小的连接数量，当为 0 时，连接池在空闲时可以完全没有连接
	 */
	private Integer minPoolSize;

	/**
	 * 连接池中可以保留连接的最大数，如设置为负数，则不限制
	 */
	private Integer maxPoolSize;

	/**
	 * 连接在连接池中保持空闲状态的最大时间
	 */
	private Duration maxIdleTime;

	/**
	 * 连接在连接池中的最大生命周期
	 */
	private Duration timeToLiveConnectionTimeout;

	/**
	 * 在一个连接空闲指定时间后，认为该连接仍然有效并且可以直接重用的时间阈值
	 */
	private Duration trustIdleConnection;

	/**
	 * 连接池中的连接在被强制关闭之前可以被重复使用的最长时间
	 */
	private Duration maxConnectionReuseTime;

	/**
	 * 连接池中的连接可以被重复使用的最大次数
	 */
	private Integer maxConnectionReuseCount;

	/**
	 * 定义在连接标签操作中被认为是“高成本”的阈值时间
	 */
	private Duration connectionLabelingHighCost;

	/**
	 * 连接的重用次数在达到这个阈值后会被标记为“高成本重用”
	 */
	private Integer highCostConnectionReuseThreshold;

	/**
	 * 定义连接在达到指定的重用次数后，是否应该被重新配置（repurpose）
	 */
	private Integer connectionRepurposeThreshold;

	/**
	 * 连接池检查连接超时的间隔时间
	 */
	private Duration timeoutCheckInterval;

	/**
	 * 连接池中每个连接所能缓存的最大 SQL 预编译语句（Prepared Statements）的数量
	 */
	private Integer maxStatements;

	/**
	 * 触发连接池收割（Harvesting）操作的阈值，表示连接池中空闲连接的数量达到多少时触发收割操作
	 */
	private Integer connectionHarvestTriggerCount;

	/**
	 * 在一次连接收割操作中，最多可以从连接池中收回的连接数量
	 */
	private Integer connectionHarvestMaxCount;

	/**
	 * 否允许从只读实例获取连接
	 */
	private Boolean readOnlyInstanceAllowed;

	/**
	 * 是否在借用连接的线程中创建连接
	 */
	private Boolean createConnectionInBorrowThread;

	/**
	 * 返回网络协议
	 *
	 * @return 网络协议
	 */
	public String getNetworkProtocol() {
		return networkProtocol;
	}

	/**
	 * 设置网络协议
	 *
	 * @param networkProtocol
	 * 		网络协议
	 */
	public void setNetworkProtocol(String networkProtocol) {
		this.networkProtocol = networkProtocol;
	}

	/**
	 * 返回数据库服务器的名称或地址
	 *
	 * @return 数据库服务器的名称或地址
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * 设置数据库服务器的名称或地址
	 *
	 * @param serverName
	 * 		数据库服务器的名称或地址
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	/**
	 * 返回数据库服务器的端口
	 *
	 * @return 数据库服务器的端口
	 */
	public Integer getPortNumber() {
		return portNumber;
	}

	/**
	 * 设置数据库服务器的端口
	 *
	 * @param portNumber
	 * 		数据库服务器的端口
	 */
	public void setPortNumber(Integer portNumber) {
		this.portNumber = portNumber;
	}

	/**
	 * 返回连接到 Oracle 数据库服务的服务名，服务名用于在 Oracle 数据库中标识特定的服务或数据库实例
	 *
	 * @return 连接到 Oracle 数据库服务的服务名
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * 设置连接到 Oracle 数据库服务的服务名，服务名用于在 Oracle 数据库中标识特定的服务或数据库实例
	 *
	 * @param serviceName
	 * 		连接到 Oracle 数据库服务的服务名
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * 返回数据源名称
	 *
	 * @return 数据源名称
	 */
	public String getDataSourceName() {
		return dataSourceName;
	}

	/**
	 * 设置数据源名称
	 *
	 * @param dataSourceName
	 * 		数据源名称
	 */
	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	/**
	 * 返回数据源描述
	 *
	 * @return 数据源描述
	 */
	public String getDataSourceDescription() {
		return dataSourceDescription;
	}

	/**
	 * 设置数据源描述
	 *
	 * @param dataSourceDescription
	 * 		数据源描述
	 */
	public void setDataSourceDescription(String dataSourceDescription) {
		this.dataSourceDescription = dataSourceDescription;
	}

	/**
	 * 返回要连接的 Oracle 数据库的名称
	 *
	 * @return 要连接的 Oracle 数据库的名称
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * 设置
	 *
	 * @param databaseName
	 * 		要连接的 Oracle 数据库的名称
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	/**
	 * 返回角色名称
	 *
	 * @return 角色名称
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * 设置角色名称
	 *
	 * @param roleName
	 * 		角色名称
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * 返回连接到 Oracle Pluggable Database (PDB) 的角色的属性
	 *
	 * @return 连接到 Oracle Pluggable Database (PDB) 的角色的属性
	 */
	public Properties getPdbRoles() {
		return pdbRoles;
	}

	/**
	 * 设置连接到 Oracle Pluggable Database (PDB) 的角色的属性
	 *
	 * @param pdbRoles
	 * 		连接到 Oracle Pluggable Database (PDB) 的角色的属性
	 */
	public void setPdbRoles(Properties pdbRoles) {
		this.pdbRoles = pdbRoles;
	}

	/**
	 * 返回连接工厂类名
	 *
	 * @return 连接工厂类名
	 */
	public String getConnectionFactoryClassName() {
		return connectionFactoryClassName;
	}

	/**
	 * 设置连接工厂类名
	 *
	 * @param connectionFactoryClassName
	 * 		连接工厂类名
	 */
	public void setConnectionFactoryClassName(String connectionFactoryClassName) {
		this.connectionFactoryClassName = connectionFactoryClassName;
	}

	/**
	 * 返回是否启用快速连接故障切换
	 *
	 * @return 是否启用快速连接故障切换
	 */
	public Boolean getFastConnectionFailoverEnabled() {
		return fastConnectionFailoverEnabled;
	}

	/**
	 * 设置是否启用快速连接故障切换
	 *
	 * @param fastConnectionFailoverEnabled
	 * 		是否启用快速连接故障切换
	 */
	public void setFastConnectionFailoverEnabled(Boolean fastConnectionFailoverEnabled) {
		this.fastConnectionFailoverEnabled = fastConnectionFailoverEnabled;
	}

	/**
	 * 返回 Oracle Notification Services (ONS) 的属性
	 *
	 * @return Oracle Notification Services (ONS) 的属性
	 */
	public String getOnsConfiguration() {
		return onsConfiguration;
	}

	/**
	 * 设置 Oracle Notification Services (ONS) 的属性
	 *
	 * @param onsConfiguration
	 * 		Oracle Notification Services (ONS) 的属性
	 */
	public void setOnsConfiguration(String onsConfiguration) {
		this.onsConfiguration = onsConfiguration;
	}

	/**
	 * 返回是否启用分片模式
	 *
	 * @return 是否启用分片模式
	 */
	public Boolean getShardingMode() {
		return shardingMode;
	}

	/**
	 * 设置是否启用分片模式
	 *
	 * @param shardingMode
	 * 		是否启用分片模式
	 */
	public void setShardingMode(Boolean shardingMode) {
		this.shardingMode = shardingMode;
	}

	/**
	 * 返回每个分片（Shard）中允许的最大连接数
	 *
	 * @return 每个分片（Shard）中允许的最大连接数
	 */
	public Integer getMaxConnectionsPerShard() {
		return maxConnectionsPerShard;
	}

	/**
	 * 设置每个分片（Shard）中允许的最大连接数
	 *
	 * @param maxConnectionsPerShard
	 * 		每个分片（Shard）中允许的最大连接数
	 */
	public void setMaxConnectionsPerShard(Integer maxConnectionsPerShard) {
		this.maxConnectionsPerShard = maxConnectionsPerShard;
	}

	/**
	 * 返回每个服务的最大连接数
	 *
	 * @return 每个服务的最大连接数
	 */
	public Integer getMaxConnectionsPerService() {
		return maxConnectionsPerService;
	}

	/**
	 * 设置每个服务的最大连接数
	 *
	 * @param maxConnectionsPerService
	 * 		每个服务的最大连接数
	 */
	public void setMaxConnectionsPerService(Integer maxConnectionsPerService) {
		this.maxConnectionsPerService = maxConnectionsPerService;
	}

	/**
	 * 返回连接池中最小的连接数量
	 *
	 * @return 连接池中最小的连接数量
	 */
	public Integer getMinPoolSize() {
		return minPoolSize;
	}

	/**
	 * 设置连接池中最小的连接数量
	 *
	 * @param minPoolSize
	 * 		连接池中最小的连接数量
	 */
	public void setMinPoolSize(Integer minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	/**
	 * 返回连接池中可以保留连接的最大数
	 *
	 * @return 连接池中可以保留连接的最大数
	 */
	public Integer getMaxPoolSize() {
		return maxPoolSize;
	}

	/**
	 * 设置连接池中可以保留连接的最大数
	 *
	 * @param maxPoolSize
	 * 		连接池中可以保留连接的最大数
	 */
	public void setMaxPoolSize(Integer maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
		super.setMaxTotal(maxPoolSize);
	}

	@Override
	public void setMaxTotal(Integer maxTotal) {
		setMaxPoolSize(maxTotal);
	}

	/**
	 * 返回连接在连接池中保持空闲状态的最大时间
	 *
	 * @return 连接在连接池中保持空闲状态的最大时间
	 */
	public Duration getMaxIdleTime() {
		return maxIdleTime;
	}

	/**
	 * 设置连接在连接池中保持空闲状态的最大时间
	 *
	 * @param maxIdleTime
	 * 		连接在连接池中保持空闲状态的最大时间
	 */
	public void setMaxIdleTime(Duration maxIdleTime) {
		this.maxIdleTime = maxIdleTime;
	}

	/**
	 * 返回连接在连接池中的最大生命周期
	 *
	 * @return 连接在连接池中的最大生命周期
	 */
	public Duration getTimeToLiveConnectionTimeout() {
		return timeToLiveConnectionTimeout;
	}

	/**
	 * 设置连接在连接池中的最大生命周期
	 *
	 * @param timeToLiveConnectionTimeout
	 * 		连接在连接池中的最大生命周期
	 */
	public void setTimeToLiveConnectionTimeout(Duration timeToLiveConnectionTimeout) {
		this.timeToLiveConnectionTimeout = timeToLiveConnectionTimeout;
	}

	/**
	 * 返回在一个连接空闲指定时间后，认为该连接仍然有效并且可以直接重用的时间阈值
	 *
	 * @return 在一个连接空闲指定时间后，认为该连接仍然有效并且可以直接重用的时间阈值
	 */
	public Duration getTrustIdleConnection() {
		return trustIdleConnection;
	}

	/**
	 * 设置在一个连接空闲指定时间后，认为该连接仍然有效并且可以直接重用的时间阈值
	 *
	 * @param trustIdleConnection
	 * 		在一个连接空闲指定时间后，认为该连接仍然有效并且可以直接重用的时间阈值
	 */
	public void setTrustIdleConnection(Duration trustIdleConnection) {
		this.trustIdleConnection = trustIdleConnection;
	}

	/**
	 * 返回连接池中的连接在被强制关闭之前可以被重复使用的最长时间
	 *
	 * @return 连接池中的连接在被强制关闭之前可以被重复使用的最长时间
	 */
	public Duration getMaxConnectionReuseTime() {
		return maxConnectionReuseTime;
	}

	/**
	 * 设置连接池中的连接在被强制关闭之前可以被重复使用的最长时间
	 *
	 * @param maxConnectionReuseTime
	 * 		连接池中的连接在被强制关闭之前可以被重复使用的最长时间
	 */
	public void setMaxConnectionReuseTime(Duration maxConnectionReuseTime) {
		this.maxConnectionReuseTime = maxConnectionReuseTime;
	}

	/**
	 * 返回连接池中的连接可以被重复使用的最大次数
	 *
	 * @return 连接池中的连接可以被重复使用的最大次数
	 */
	public Integer getMaxConnectionReuseCount() {
		return maxConnectionReuseCount;
	}

	/**
	 * 设置连接池中的连接可以被重复使用的最大次数
	 *
	 * @param maxConnectionReuseCount
	 * 		连接池中的连接可以被重复使用的最大次数
	 */
	public void setMaxConnectionReuseCount(Integer maxConnectionReuseCount) {
		this.maxConnectionReuseCount = maxConnectionReuseCount;
	}

	/**
	 * 返回定义在连接标签操作中被认为是“高成本”的阈值时间
	 *
	 * @return 定义在连接标签操作中被认为是“高成本”的阈值时间
	 */
	public Duration getConnectionLabelingHighCost() {
		return connectionLabelingHighCost;
	}

	/**
	 * 设置定义在连接标签操作中被认为是“高成本”的阈值时间
	 *
	 * @param connectionLabelingHighCost
	 * 		定义在连接标签操作中被认为是“高成本”的阈值时间
	 */
	public void setConnectionLabelingHighCost(Duration connectionLabelingHighCost) {
		this.connectionLabelingHighCost = connectionLabelingHighCost;
	}

	/**
	 * 返回连接的重用次数在达到这个阈值后会被标记为“高成本重用”
	 *
	 * @return 连接的重用次数在达到这个阈值后会被标记为“高成本重用”
	 */
	public Integer getHighCostConnectionReuseThreshold() {
		return highCostConnectionReuseThreshold;
	}

	/**
	 * 设置连接的重用次数在达到这个阈值后会被标记为“高成本重用”
	 *
	 * @param highCostConnectionReuseThreshold
	 * 		连接的重用次数在达到这个阈值后会被标记为“高成本重用”
	 */
	public void setHighCostConnectionReuseThreshold(Integer highCostConnectionReuseThreshold) {
		this.highCostConnectionReuseThreshold = highCostConnectionReuseThreshold;
	}

	/**
	 * 返回定义连接在达到指定的重用次数后，是否应该被重新配置（repurpose）
	 *
	 * @return 定义连接在达到指定的重用次数后，是否应该被重新配置（repurpose）
	 */
	public Integer getConnectionRepurposeThreshold() {
		return connectionRepurposeThreshold;
	}

	/**
	 * 设置定义连接在达到指定的重用次数后，是否应该被重新配置（repurpose）
	 *
	 * @param connectionRepurposeThreshold
	 * 		定义连接在达到指定的重用次数后，是否应该被重新配置（repurpose）
	 */
	public void setConnectionRepurposeThreshold(Integer connectionRepurposeThreshold) {
		this.connectionRepurposeThreshold = connectionRepurposeThreshold;
	}

	/**
	 * 返回连接池检查连接超时的间隔时间
	 *
	 * @return 连接池检查连接超时的间隔时间
	 */
	public Duration getTimeoutCheckInterval() {
		return timeoutCheckInterval;
	}

	/**
	 * 设置连接池检查连接超时的间隔时间
	 *
	 * @param timeoutCheckInterval
	 * 		连接池检查连接超时的间隔时间
	 */
	public void setTimeoutCheckInterval(Duration timeoutCheckInterval) {
		this.timeoutCheckInterval = timeoutCheckInterval;
	}

	/**
	 * 返回连接池中每个连接所能缓存的最大 SQL 预编译语句（Prepared Statements）的数量
	 *
	 * @return 连接池中每个连接所能缓存的最大 SQL 预编译语句（Prepared Statements）的数量
	 */
	public Integer getMaxStatements() {
		return maxStatements;
	}

	/**
	 * 设置连接池中每个连接所能缓存的最大 SQL 预编译语句（Prepared Statements）的数量
	 *
	 * @param maxStatements
	 * 		连接池中每个连接所能缓存的最大 SQL 预编译语句（Prepared Statements）的数量
	 */
	public void setMaxStatements(Integer maxStatements) {
		this.maxStatements = maxStatements;
	}

	/**
	 * 返回触发连接池收割（Harvesting）操作的阈值，表示连接池中空闲连接的数量达到多少时触发收割操作
	 *
	 * @return 触发连接池收割（Harvesting）操作的阈值，表示连接池中空闲连接的数量达到多少时触发收割操作
	 */
	public Integer getConnectionHarvestTriggerCount() {
		return connectionHarvestTriggerCount;
	}

	/**
	 * 设置触发连接池收割（Harvesting）操作的阈值，表示连接池中空闲连接的数量达到多少时触发收割操作
	 *
	 * @param connectionHarvestTriggerCount
	 * 		触发连接池收割（Harvesting）操作的阈值，表示连接池中空闲连接的数量达到多少时触发收割操作
	 */
	public void setConnectionHarvestTriggerCount(Integer connectionHarvestTriggerCount) {
		this.connectionHarvestTriggerCount = connectionHarvestTriggerCount;
	}

	/**
	 * 返回在一次连接收割操作中，最多可以从连接池中收回的连接数量
	 *
	 * @return 在一次连接收割操作中，最多可以从连接池中收回的连接数量
	 */
	public Integer getConnectionHarvestMaxCount() {
		return connectionHarvestMaxCount;
	}

	/**
	 * 设置在一次连接收割操作中，最多可以从连接池中收回的连接数量
	 *
	 * @param connectionHarvestMaxCount
	 * 		在一次连接收割操作中，最多可以从连接池中收回的连接数量
	 */
	public void setConnectionHarvestMaxCount(Integer connectionHarvestMaxCount) {
		this.connectionHarvestMaxCount = connectionHarvestMaxCount;
	}

	/**
	 * 返回否允许从只读实例获取连接
	 *
	 * @return 否允许从只读实例获取连接
	 */
	public Boolean getReadOnlyInstanceAllowed() {
		return readOnlyInstanceAllowed;
	}

	/**
	 * 设置否允许从只读实例获取连接
	 *
	 * @param readOnlyInstanceAllowed
	 * 		否允许从只读实例获取连接
	 */
	public void setReadOnlyInstanceAllowed(Boolean readOnlyInstanceAllowed) {
		this.readOnlyInstanceAllowed = readOnlyInstanceAllowed;
	}

	/**
	 * 返回是否在借用连接的线程中创建连接
	 *
	 * @return 是否在借用连接的线程中创建连接
	 */
	public Boolean getCreateConnectionInBorrowThread() {
		return createConnectionInBorrowThread;
	}

	/**
	 * 设置是否在借用连接的线程中创建连接
	 *
	 * @param createConnectionInBorrowThread
	 * 		是否在借用连接的线程中创建连接
	 */
	public void setCreateConnectionInBorrowThread(Boolean createConnectionInBorrowThread) {
		this.createConnectionInBorrowThread = createConnectionInBorrowThread;
	}

}
