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
 * | Copyright @ 2013-2023 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.datasource.autoconfigure;

import com.buession.core.Configurer;
import com.buession.jdbc.config.*;
import com.buession.jdbc.core.Callback;
import com.buession.jdbc.datasource.*;
import com.buession.jdbc.datasource.pool.*;
import com.buession.springboot.datasource.core.DataSourceType;
import oracle.ucp.jdbc.PoolDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * DataSource Auto Configuration
 *
 * @author Yong.Teng
 */
@AutoConfiguration
@ConditionalOnProperty(name = DataSourceProperties.PREFIX)
public class DataSourceConfiguration {

	protected final DataSourceProperties properties;

	public DataSourceConfiguration(DataSourceProperties properties) {
		this.properties = properties;
	}

	protected static <ODS extends javax.sql.DataSource, C extends BaseConfig, P extends PoolConfiguration,
			DS extends com.buession.jdbc.datasource.DataSource<ODS, P>> DataSource createDataSource(
			final Class<DS> type, final DataSourceProperties dataSourceProperties, final C dataSourceConfig,
			final P poolConfiguration, final Configurer<DS, C> customizer) {
		return createDataSource(type, dataSourceProperties, dataSourceConfig, poolConfiguration, customizer,
				(dataSource, properties)->dataSource);
	}

	protected static <ODS extends javax.sql.DataSource, C extends BaseConfig, P extends PoolConfiguration,
			DS extends com.buession.jdbc.datasource.DataSource<ODS, P>> DataSource createDataSource(
			final Class<DS> type, final DataSourceProperties dataSourceProperties, final C dataSourceConfig,
			final P poolConfiguration, final Configurer<DS, C> customizer,
			final Callback<ODS, DataSourceProperties> callback) {
		final DataSourceInitializer<C, P, ODS, DS> dataSourceInitializer = new DataSourceInitializer<>(type,
				dataSourceProperties, dataSourceConfig, poolConfiguration, customizer, callback);
		return dataSourceInitializer.createDataSource();
	}

	/**
	 * DBCP2 DataSource Configuration.
	 *
	 * @since 1.3.2
	 */
	@AutoConfiguration
	@EnableConfigurationProperties(DataSourceProperties.class)
	@ConditionalOnClass(BasicDataSource.class)
	@ConditionalOnMissingBean(DataSource.class)
	@ConditionalOnProperty(prefix = DataSourceProperties.PREFIX, name = "type", havingValue = DataSourceType.DHCP2,
			matchIfMissing = true)
	static class Dbcp2 extends DataSourceConfiguration {

		public Dbcp2(DataSourceProperties properties) {
			super(properties);
		}

		@Bean
		@ConfigurationProperties(prefix = DataSourceProperties.PREFIX + ".dbcp2")
		public DataSource dataSource() {
			return createDataSource(Dbcp2DataSource.class, properties, properties.getDbcp2(),
					new Dbcp2PoolConfiguration(), (dataSource, config)->{
						dataSource.setConnectionFactoryClassName(config.getConnectionFactoryClassName());

						dataSource.setFastFailValidation(config.getFastFailValidation());
						dataSource.setLogExpiredConnections(config.getLogExpiredConnections());

						dataSource.setAutoCommitOnReturn(config.getAutoCommitOnReturn());
						dataSource.setRollbackOnReturn(config.getRollbackOnReturn());

						dataSource.setCacheState(config.getCacheState());
						dataSource.setDefaultAutoCommit(config.getDefaultAutoCommit());

						poolConfig(dataSource.getPoolConfiguration(), config);
					});
		}

		private static void poolConfig(final Dbcp2PoolConfiguration poolConfiguration,
									   final Dbcp2Config dataSourceConfig) {
			poolConfiguration.setMaxConnLifetime(dataSourceConfig.getMaxConnLifetime());

			poolConfiguration.setPoolPreparedStatements(dataSourceConfig.getPoolPreparedStatements());
			poolConfiguration.setMaxOpenPreparedStatements(dataSourceConfig.getMaxOpenPreparedStatements());
			poolConfiguration.setMaxOpenPreparedStatements(dataSourceConfig.getMaxOpenPreparedStatements());
			poolConfiguration.setClearStatementPoolOnReturn(dataSourceConfig.getClearStatementPoolOnReturn());

			poolConfiguration.setRemoveAbandonedOnBorrow(dataSourceConfig.getRemoveAbandonedOnBorrow());
			poolConfiguration.setRemoveAbandonedOnMaintenance(dataSourceConfig.getRemoveAbandonedOnMaintenance());
			poolConfiguration.setAbandonedUsageTracking(dataSourceConfig.getAbandonedUsageTracking());

			poolConfiguration.setSoftMinEvictableIdle(dataSourceConfig.getSoftMinEvictableIdle());

			poolConfiguration.setEvictionPolicyClassName(dataSourceConfig.getEvictionPolicyClassName());

			poolConfiguration.setLifo(dataSourceConfig.getLifo());
		}

	}

	/**
	 * Druid DataSource Configuration.
	 *
	 * @since 1.3.2
	 */
	@AutoConfiguration
	@EnableConfigurationProperties(DataSourceProperties.class)
	@ConditionalOnClass(DruidDataSource.class)
	@ConditionalOnMissingBean(DataSource.class)
	@ConditionalOnProperty(prefix = DataSourceProperties.PREFIX, name = "type", havingValue = DataSourceType.DRUID,
			matchIfMissing = true)
	static class Druid extends DataSourceConfiguration {

		public Druid(DataSourceProperties properties) {
			super(properties);
		}

		@Bean
		@ConfigurationProperties(prefix = DataSourceProperties.PREFIX + ".druid")
		public DataSource dataSource() {
			return createDataSource(DruidDataSource.class, properties, properties.getDruid(),
					new DruidPoolConfiguration(), (dataSource, config)->{
						dataSource.setUserCallbackClassName(config.getUserCallbackClassName());
						dataSource.setPasswordCallbackClassName(config.getPasswordCallbackClassName());

						dataSource.setConnectTimeout(config.getConnectTimeout());
						dataSource.setSocketTimeout(config.getSocketTimeout());

						dataSource.setTimeBetweenConnectError(config.getTimeBetweenConnectError());
						dataSource.setKillWhenSocketReadTimeout(config.getKillWhenSocketReadTimeout());

						dataSource.setPhyTimeout(config.getPhyTimeout());
						dataSource.setPhyMaxUseCount(config.getPhyMaxUseCount());

						dataSource.setAsyncInit(config.getAsyncInit());

						dataSource.setInitVariants(config.getInitVariants());
						dataSource.setInitGlobalVariants(config.getInitGlobalVariants());

						dataSource.setValidConnectionCheckerClassName(config.getValidConnectionCheckerClassName());
						dataSource.setConnectionErrorRetryAttempts(config.getConnectionErrorRetryAttempts());

						dataSource.setInitExceptionThrow(config.getInitExceptionThrow());
						dataSource.setExceptionSorterClassName(config.getExceptionSorterClassName());

						dataSource.setUseOracleImplicitCache(config.getUseOracleImplicitCache());
						dataSource.setAsyncCloseConnectionEnable(config.getAsyncCloseConnectionEnable());

						dataSource.setTransactionQueryTimeout(config.getTransactionQueryTimeout());
						dataSource.setTransactionThreshold(config.getTransactionThreshold());

						dataSource.setFairLock(config.getFairLock());

						dataSource.setFailFast(config.getFailFast());

						dataSource.setCheckExecuteTime(config.getCheckExecuteTime());

						dataSource.setUseGlobalDataSourceStat(config.getUseGlobalDataSourceStat());
						dataSource.setStatLoggerClassName(config.getStatLoggerClassName());

						dataSource.setMaxSqlSize(config.getMaxSqlSize());
						dataSource.setResetStatEnable(config.getResetStatEnable());

						dataSource.setFilters(config.getFilters());
						dataSource.setLoadSpifilterSkip(config.getLoadSpifilterSkip());
						dataSource.setClearFiltersEnable(config.getClearFiltersEnable());

						dataSource.setEnable(config.getEnable());

						poolConfig(dataSource.getPoolConfiguration(), config);
					});
		}

		private static void poolConfig(final DruidPoolConfiguration poolConfiguration,
									   final DruidConfig dataSourceConfig) {
			poolConfiguration.setMaxActive(dataSourceConfig.getMaxActive());

			poolConfiguration.setKeepAlive(dataSourceConfig.getKeepAlive());
			poolConfiguration.setKeepAliveBetweenTime(dataSourceConfig.getKeepAliveBetweenTime());

			poolConfiguration.setUsePingMethod(dataSourceConfig.getUsePingMethod());
			poolConfiguration.setKeepConnectionUnderlyingTransactionIsolation(
					dataSourceConfig.getKeepConnectionUnderlyingTransactionIsolation());

			poolConfiguration.setMaxCreateTaskCount(dataSourceConfig.getMaxCreateTaskCount());
			poolConfiguration.setMaxWaitThreadCount(dataSourceConfig.getMaxWaitThreadCount());

			poolConfiguration.setOnFatalErrorMaxActive(dataSourceConfig.getOnFatalErrorMaxActive());
			poolConfiguration.setBreakAfterAcquireFailure(dataSourceConfig.getBreakAfterAcquireFailure());

			poolConfiguration.setNotFullTimeoutRetryCount(dataSourceConfig.getNotFullTimeoutRetryCount());

			poolConfiguration.setUseLocalSessionState(dataSourceConfig.getUseLocalSessionState());
			poolConfiguration.setPoolPreparedStatements(dataSourceConfig.getPoolPreparedStatements());
			poolConfiguration.setSharePreparedStatements(dataSourceConfig.getSharePreparedStatements());
			poolConfiguration.setMaxPoolPreparedStatementPerConnectionSize(
					dataSourceConfig.getMaxPoolPreparedStatementPerConnectionSize());
			poolConfiguration.setMaxOpenPreparedStatements(dataSourceConfig.getMaxOpenPreparedStatements());

			poolConfiguration.setRemoveAbandoned(dataSourceConfig.getRemoveAbandoned());

			poolConfiguration.setTimeBetweenLogStats(dataSourceConfig.getTimeBetweenLogStats());

			poolConfiguration.setDupCloseLogEnable(dataSourceConfig.getDupCloseLogEnable());
			poolConfiguration.setLogDifferentThread(dataSourceConfig.getLogDifferentThread());
		}

	}

	/**
	 * Hikari DataSource Configuration.
	 *
	 * @since 1.3.2
	 */
	@AutoConfiguration
	@EnableConfigurationProperties(DataSourceProperties.class)
	@ConditionalOnClass(HikariDataSource.class)
	@ConditionalOnMissingBean(DataSource.class)
	@ConditionalOnProperty(prefix = DataSourceProperties.PREFIX, name = "type", havingValue = DataSourceType.HIKARI,
			matchIfMissing = true)
	static class Hikari extends DataSourceConfiguration {

		public Hikari(DataSourceProperties properties) {
			super(properties);
		}

		@Bean
		@ConfigurationProperties(prefix = DataSourceProperties.PREFIX + ".hikari")
		public DataSource dataSource() {
			return createDataSource(HikariDataSource.class, properties, properties.getHikari(),
					new HikariPoolConfiguration(), (dataSource, config)->{
						dataSource.setJndiName(config.getJndiName());

						dataSource.setConnectionTimeout(config.getConnectionTimeout());

						dataSource.setIsolateInternalQueries(config.getIsolateInternalQueries());

						poolConfig(dataSource.getPoolConfiguration(), config);
					});
		}

		private static void poolConfig(final HikariPoolConfiguration poolConfiguration,
									   final HikariConfig dataSourceConfig) {
			poolConfiguration.setInitializationFailTimeout(dataSourceConfig.getInitializationFailTimeout());

			poolConfiguration.setMaxPoolSize(dataSourceConfig.getMaxPoolSize());

			poolConfiguration.setConnectionTestQuery(dataSourceConfig.getConnectionTestQuery());
			poolConfiguration.setValidationTimeout(dataSourceConfig.getValidationTimeout());

			poolConfiguration.setIdleTimeout(dataSourceConfig.getIdleTimeout());
			poolConfiguration.setMaxLifetime(dataSourceConfig.getMaxLifetime());
			poolConfiguration.setKeepaliveTime(dataSourceConfig.getKeepaliveTime());

			poolConfiguration.setLeakDetectionThreshold(dataSourceConfig.getLeakDetectionThreshold());
			poolConfiguration.setAllowPoolSuspension(dataSourceConfig.getAllowPoolSuspension());

			poolConfiguration.setMetricsTrackerFactoryClassName(dataSourceConfig.getMetricsTrackerFactoryClassName());
			poolConfiguration.setMetricRegistryClassName(dataSourceConfig.getMetricRegistryClassName());

			poolConfiguration.setHealthCheckRegistryClassName(dataSourceConfig.getHealthCheckRegistryClassName());
			poolConfiguration.setHealthCheckProperties(dataSourceConfig.getHealthCheckProperties());
		}

	}

	/**
	 * Oracle ucp DataSource Configuration.
	 *
	 * @since 3.0.0
	 */
	@AutoConfiguration
	@EnableConfigurationProperties(DataSourceProperties.class)
	@ConditionalOnClass(PoolDataSource.class)
	@ConditionalOnMissingBean(DataSource.class)
	@ConditionalOnProperty(prefix = DataSourceProperties.PREFIX, name = "type", havingValue = DataSourceType.ORACLE,
			matchIfMissing = true)
	static class Oracle extends DataSourceConfiguration {

		public Oracle(DataSourceProperties properties) {
			super(properties);
		}

		@Bean
		@ConfigurationProperties(prefix = DataSourceProperties.PREFIX + ".oracle")
		public DataSource dataSource() {
			return createDataSource(OracleDataSource.class, properties, properties.getOracle(),
					new OraclePoolConfiguration(), (dataSource, config)->{
						dataSource.setNetworkProtocol(config.getNetworkProtocol());
						dataSource.setServerName(config.getServerName());
						dataSource.setPortNumber(config.getPortNumber());
						dataSource.setServiceName(config.getServiceName());
						dataSource.setDataSourceName(config.getDataSourceName());
						dataSource.setDataSourceDescription(config.getDataSourceDescription());
						dataSource.setDatabaseName(config.getDatabaseName());

						dataSource.setRoleName(config.getRoleName());
						dataSource.setPdbRoles(config.getPdbRoles());

						dataSource.setConnectionFactoryClassName(config.getConnectionFactoryClassName());
						dataSource.setFastConnectionFailoverEnabled(config.getFastConnectionFailoverEnabled());

						dataSource.setOnsConfiguration(config.getOnsConfiguration());

						dataSource.setShardingMode(config.getShardingMode());
						dataSource.setMaxConnectionsPerShard(config.getMaxConnectionsPerShard());

						dataSource.setMaxConnectionsPerService(config.getMaxConnectionsPerService());

						poolConfig(dataSource.getPoolConfiguration(), config);
					});
		}

		private static void poolConfig(final OraclePoolConfiguration poolConfiguration,
									   final OracleConfig dataSourceConfig) {
			poolConfiguration.setMinPoolSize(dataSourceConfig.getMinPoolSize());
			poolConfiguration.setMaxPoolSize(dataSourceConfig.getMaxPoolSize());

			poolConfiguration.setMaxIdleTime(dataSourceConfig.getMaxIdleTime());
			poolConfiguration.setTimeToLiveConnectionTimeout(dataSourceConfig.getTimeToLiveConnectionTimeout());

			poolConfiguration.setTrustIdleConnection(dataSourceConfig.getTrustIdleConnection());
			poolConfiguration.setMaxConnectionReuseTime(dataSourceConfig.getMaxConnectionReuseTime());
			poolConfiguration.setMaxConnectionReuseCount(dataSourceConfig.getMaxConnectionReuseCount());

			poolConfiguration.setConnectionLabelingHighCost(dataSourceConfig.getConnectionLabelingHighCost());
			poolConfiguration.setHighCostConnectionReuseThreshold(
					dataSourceConfig.getHighCostConnectionReuseThreshold());
			poolConfiguration.setConnectionRepurposeThreshold(dataSourceConfig.getConnectionRepurposeThreshold());

			poolConfiguration.setTimeoutCheckInterval(dataSourceConfig.getTimeoutCheckInterval());

			poolConfiguration.setMaxStatements(dataSourceConfig.getMaxStatements());

			poolConfiguration.setConnectionHarvestTriggerCount(dataSourceConfig.getConnectionHarvestTriggerCount());
			poolConfiguration.setConnectionHarvestMaxCount(dataSourceConfig.getConnectionHarvestMaxCount());

			poolConfiguration.setReadOnlyInstanceAllowed(dataSourceConfig.getReadOnlyInstanceAllowed());
			poolConfiguration.setCreateConnectionInBorrowThread(dataSourceConfig.getCreateConnectionInBorrowThread());
		}

	}

	/**
	 * Tomcat DataSource Configuration.
	 *
	 * @since 1.3.2
	 */
	@AutoConfiguration
	@EnableConfigurationProperties(DataSourceProperties.class)
	@ConditionalOnClass(org.apache.tomcat.jdbc.pool.DataSource.class)
	@ConditionalOnMissingBean(DataSource.class)
	@ConditionalOnProperty(prefix = DataSourceProperties.PREFIX, name = "type", havingValue = DataSourceType.TOMCAT,
			matchIfMissing = true)
	static class Tomcat extends DataSourceConfiguration {

		public Tomcat(DataSourceProperties properties) {
			super(properties);
		}

		@Bean
		@ConfigurationProperties(prefix = DataSourceProperties.PREFIX + ".tomcat")
		public DataSource dataSource() {
			return createDataSource(TomcatDataSource.class, properties, properties.getTomcat(),
					new TomcatPoolConfiguration(),
					(dataSource, config)->{
						dataSource.setJndiName(config.getJndiName());

						dataSource.setAlternateUsernameAllowed(config.getAlternateUsernameAllowed());

						dataSource.setCommitOnReturn(config.getCommitOnReturn());
						dataSource.setRollbackOnReturn(config.getRollbackOnReturn());

						dataSource.setValidatorClassName(config.getValidatorClassName());
						dataSource.setValidationInterval(config.getValidationInterval());

						dataSource.setJdbcInterceptors(config.getJdbcInterceptors());

						poolConfig(dataSource.getPoolConfiguration(), config);
					},
					(dataSource, properties)->{
						DatabaseDriver databaseDriver = DatabaseDriver.fromJdbcUrl(dataSource.getUrl());
						String validationQuery = databaseDriver.getValidationQuery();

						if(validationQuery != null){
							dataSource.setTestOnBorrow(true);
							dataSource.setValidationQuery(validationQuery);
						}

						return dataSource;
					});
		}

		private static void poolConfig(final TomcatPoolConfiguration poolConfiguration,
									   final TomcatConfig dataSourceConfig) {
			poolConfiguration.setMaxActive(dataSourceConfig.getMaxActive());
			poolConfiguration.setMaxAge(dataSourceConfig.getMaxAge());

			poolConfiguration.setTestOnConnect(dataSourceConfig.getTestOnConnect());

			poolConfiguration.setUseDisposableConnectionFacade(dataSourceConfig.getUseDisposableConnectionFacade());
			poolConfiguration.setIgnoreExceptionOnPreLoad(dataSourceConfig.getIgnoreExceptionOnPreLoad());

			poolConfiguration.setFairQueue(dataSourceConfig.getFairQueue());
			poolConfiguration.setUseStatementFacade(dataSourceConfig.getUseStatementFacade());

			poolConfiguration.setRemoveAbandoned(dataSourceConfig.getRemoveAbandoned());
			poolConfiguration.setSuspectTimeout(dataSourceConfig.getSuspectTimeout());
			poolConfiguration.setAbandonWhenPercentageFull(dataSourceConfig.getAbandonWhenPercentageFull());

			poolConfiguration.setPropagateInterruptState(dataSourceConfig.getPropagateInterruptState());
			poolConfiguration.setLogValidationErrors(dataSourceConfig.getLogValidationErrors());

			poolConfiguration.setUseLock(dataSourceConfig.getUseLock());
			poolConfiguration.setUseEquals(dataSourceConfig.getUseEquals());
		}

	}

	/**
	 * Generic DataSource Configuration.
	 *
	 * @since 1.3.2
	 */
	@AutoConfiguration
	@EnableConfigurationProperties(DataSourceProperties.class)
	@ConditionalOnMissingBean(DataSource.class)
	@ConditionalOnProperty(prefix = DataSourceProperties.PREFIX, name = "type")
	static class Generic extends DataSourceConfiguration {

		public Generic(DataSourceProperties properties) {
			super(properties);
		}

		@Bean
		public DataSource dataSource() {
			return createDataSource(GenericDataSource.class, properties, properties.getGeneric(), null,
					(dataSource, config)->{

					});
		}

	}

}
