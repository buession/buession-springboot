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
import com.buession.core.converter.mapper.PropertyMapper;
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

	protected final static PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

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
						propertyMapper.from(config::getConnectionFactoryClassName).to(dataSource::setConnectionFactoryClassName);

						propertyMapper.from(config::getFastFailValidation).to(dataSource::setFastFailValidation);
						propertyMapper.from(config::getLogExpiredConnections).to(dataSource::setLogExpiredConnections);

						propertyMapper.from(config::getAutoCommitOnReturn).to(dataSource::setAutoCommitOnReturn);
						propertyMapper.from(config::getRollbackOnReturn).to(dataSource::setRollbackOnReturn);

						propertyMapper.from(config::getCacheState).to(dataSource::setCacheState);
						propertyMapper.from(config::getDefaultAutoCommit).to(dataSource::setDefaultAutoCommit);

						poolConfig(dataSource.getPoolConfiguration(), config);
					});
		}

		private static void poolConfig(final Dbcp2PoolConfiguration poolConfiguration,
		                               final Dbcp2Config dataSourceConfig) {
			propertyMapper.from(dataSourceConfig::getMaxConnLifetime).to(poolConfiguration::setMaxConnLifetime);

			propertyMapper.from(dataSourceConfig::getPoolPreparedStatements).to(poolConfiguration::setPoolPreparedStatements);
			propertyMapper.from(dataSourceConfig::getMaxOpenPreparedStatements).to(poolConfiguration::setMaxOpenPreparedStatements);
			propertyMapper.from(dataSourceConfig::getClearStatementPoolOnReturn).to(poolConfiguration::setClearStatementPoolOnReturn);

			propertyMapper.from(dataSourceConfig::getRemoveAbandonedOnBorrow).to(poolConfiguration::setRemoveAbandonedOnBorrow);
			propertyMapper.from(dataSourceConfig::getRemoveAbandonedOnMaintenance).to(poolConfiguration::setRemoveAbandonedOnMaintenance);
			propertyMapper.from(dataSourceConfig::getAbandonedUsageTracking).to(poolConfiguration::setAbandonedUsageTracking);

			propertyMapper.from(dataSourceConfig::getSoftMinEvictableIdle).to(poolConfiguration::setSoftMinEvictableIdle);

			propertyMapper.from(dataSourceConfig::getEvictionPolicyClassName).to(poolConfiguration::setEvictionPolicyClassName);

			propertyMapper.from(dataSourceConfig::getLifo).to(poolConfiguration::setLifo);
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
						propertyMapper.from(config::getUserCallbackClassName).to(dataSource::setUserCallbackClassName);
						propertyMapper.from(config::getPasswordCallbackClassName).to(dataSource::setPasswordCallbackClassName);

						propertyMapper.from(config::getConnectTimeout).to(dataSource::setConnectTimeout);
						propertyMapper.from(config::getSocketTimeout).to(dataSource::setSocketTimeout);

						propertyMapper.from(config::getTimeBetweenConnectError).to(dataSource::setTimeBetweenConnectError);
						propertyMapper.from(config::getKillWhenSocketReadTimeout).to(dataSource::setKillWhenSocketReadTimeout);

						propertyMapper.from(config::getPhyTimeout).to(dataSource::setPhyTimeout);
						propertyMapper.from(config::getPhyMaxUseCount).to(dataSource::setPhyMaxUseCount);

						propertyMapper.from(config::getAsyncInit).to(dataSource::setAsyncInit);

						propertyMapper.from(config::getInitVariants).to(dataSource::setInitVariants);
						propertyMapper.from(config::getInitGlobalVariants).to(dataSource::setInitGlobalVariants);

						propertyMapper.from(config::getValidConnectionCheckerClassName).to(dataSource::setValidConnectionCheckerClassName);
						propertyMapper.from(config::getConnectionErrorRetryAttempts).to(dataSource::setConnectionErrorRetryAttempts);

						propertyMapper.from(config::getInitExceptionThrow).to(dataSource::setInitExceptionThrow);
						propertyMapper.from(config::getExceptionSorterClassName).to(dataSource::setExceptionSorterClassName);

						propertyMapper.from(config::getUseOracleImplicitCache).to(dataSource::setUseOracleImplicitCache);
						propertyMapper.from(config::getAsyncCloseConnectionEnable).to(dataSource::setAsyncCloseConnectionEnable);

						propertyMapper.from(config::getTransactionQueryTimeout).to(dataSource::setTransactionQueryTimeout);
						propertyMapper.from(config::getTransactionThreshold).to(dataSource::setTransactionThreshold);

						propertyMapper.from(config::getFairLock).to(dataSource::setFairLock);

						propertyMapper.from(config::getFailFast).to(dataSource::setFailFast);

						propertyMapper.from(config::getCheckExecuteTime).to(dataSource::setCheckExecuteTime);

						propertyMapper.from(config::getUseGlobalDataSourceStat).to(dataSource::setUseGlobalDataSourceStat);
						propertyMapper.from(config::getStatLoggerClassName).to(dataSource::setStatLoggerClassName);

						propertyMapper.from(config::getMaxSqlSize).to(dataSource::setMaxSqlSize);
						propertyMapper.from(config::getResetStatEnable).to(dataSource::setResetStatEnable);

						propertyMapper.from(config::getFilters).to(dataSource::setFilters);
						propertyMapper.from(config::getLoadSpifilterSkip).to(dataSource::setLoadSpifilterSkip);
						propertyMapper.from(config::getClearFiltersEnable).to(dataSource::setClearFiltersEnable);

						propertyMapper.from(config::getEnable).to(dataSource::setEnable);

						poolConfig(dataSource.getPoolConfiguration(), config);
					});
		}

		private static void poolConfig(final DruidPoolConfiguration poolConfiguration,
		                               final DruidConfig dataSourceConfig) {
			propertyMapper.from(dataSourceConfig::getMaxActive).to(poolConfiguration::setMaxActive);

			propertyMapper.from(dataSourceConfig::getKeepAlive).to(poolConfiguration::setKeepAlive);
			propertyMapper.from(dataSourceConfig::getKeepAliveBetweenTime).to(poolConfiguration::setKeepAliveBetweenTime);

			propertyMapper.from(dataSourceConfig::getUsePingMethod).to(poolConfiguration::setUsePingMethod);
			propertyMapper.from(dataSourceConfig::getKeepConnectionUnderlyingTransactionIsolation)
					.to(poolConfiguration::setKeepConnectionUnderlyingTransactionIsolation);

			propertyMapper.from(dataSourceConfig::getMaxCreateTaskCount).to(poolConfiguration::setMaxCreateTaskCount);
			propertyMapper.from(dataSourceConfig::getMaxWaitThreadCount).to(poolConfiguration::setMaxWaitThreadCount);

			propertyMapper.from(dataSourceConfig::getOnFatalErrorMaxActive).to(poolConfiguration::setOnFatalErrorMaxActive);
			propertyMapper.from(dataSourceConfig::getBreakAfterAcquireFailure).to(poolConfiguration::setBreakAfterAcquireFailure);

			propertyMapper.from(dataSourceConfig::getNotFullTimeoutRetryCount).to(poolConfiguration::setNotFullTimeoutRetryCount);

			propertyMapper.from(dataSourceConfig::getUseLocalSessionState).to(poolConfiguration::setUseLocalSessionState);
			propertyMapper.from(dataSourceConfig::getPoolPreparedStatements).to(poolConfiguration::setPoolPreparedStatements);
			propertyMapper.from(dataSourceConfig::getSharePreparedStatements).to(poolConfiguration::setSharePreparedStatements);
			propertyMapper.from(dataSourceConfig::getMaxPoolPreparedStatementPerConnectionSize)
					.to(poolConfiguration::setMaxPoolPreparedStatementPerConnectionSize);
			propertyMapper.from(dataSourceConfig::getMaxOpenPreparedStatements).to(poolConfiguration::setMaxOpenPreparedStatements);

			propertyMapper.from(dataSourceConfig::getRemoveAbandoned).to(poolConfiguration::setRemoveAbandoned);

			propertyMapper.from(dataSourceConfig::getTimeBetweenLogStats).to(poolConfiguration::setTimeBetweenLogStats);

			propertyMapper.from(dataSourceConfig::getDupCloseLogEnable).to(poolConfiguration::setDupCloseLogEnable);
			propertyMapper.from(dataSourceConfig::getLogDifferentThread).to(poolConfiguration::setLogDifferentThread);
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
						propertyMapper.from(config::getJndiName).to(dataSource::setJndiName);

						propertyMapper.from(config::getConnectionTimeout).to(dataSource::setConnectionTimeout);

						propertyMapper.from(config::getIsolateInternalQueries).to(dataSource::setIsolateInternalQueries);

						poolConfig(dataSource.getPoolConfiguration(), config);
					});
		}

		private static void poolConfig(final HikariPoolConfiguration poolConfiguration,
		                               final HikariConfig dataSourceConfig) {
			propertyMapper.from(dataSourceConfig::getInitializationFailTimeout).to(poolConfiguration::setInitializationFailTimeout);

			propertyMapper.from(dataSourceConfig::getMaxPoolSize).to(poolConfiguration::setMaxPoolSize);

			propertyMapper.from(dataSourceConfig::getConnectionTestQuery).to(poolConfiguration::setConnectionTestQuery);
			propertyMapper.from(dataSourceConfig::getValidationTimeout).to(poolConfiguration::setValidationTimeout);

			propertyMapper.from(dataSourceConfig::getIdleTimeout).to(poolConfiguration::setIdleTimeout);
			propertyMapper.from(dataSourceConfig::getMaxLifetime).to(poolConfiguration::setMaxLifetime);
			propertyMapper.from(dataSourceConfig::getKeepaliveTime).to(poolConfiguration::setKeepaliveTime);

			propertyMapper.from(dataSourceConfig::getLeakDetectionThreshold).to(poolConfiguration::setLeakDetectionThreshold);
			propertyMapper.from(dataSourceConfig::getAllowPoolSuspension).to(poolConfiguration::setAllowPoolSuspension);

			propertyMapper.from(dataSourceConfig::getMetricsTrackerFactoryClassName).to(poolConfiguration::setMetricsTrackerFactoryClassName);
			propertyMapper.from(dataSourceConfig::getMetricRegistryClassName).to(poolConfiguration::setMetricRegistryClassName);

			propertyMapper.from(dataSourceConfig::getHealthCheckRegistryClassName).to(poolConfiguration::setHealthCheckRegistryClassName);
			propertyMapper.from(dataSourceConfig::getHealthCheckProperties).to(poolConfiguration::setHealthCheckProperties);
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
						propertyMapper.from(config::getNetworkProtocol).to(dataSource::setNetworkProtocol);
						propertyMapper.from(config::getServerName).to(dataSource::setServerName);
						propertyMapper.from(config::getPortNumber).to(dataSource::setPortNumber);
						propertyMapper.from(config::getServiceName).to(dataSource::setServiceName);
						propertyMapper.from(config::getDataSourceName).to(dataSource::setDataSourceName);
						propertyMapper.from(config::getDataSourceDescription).to(dataSource::setDataSourceDescription);
						propertyMapper.from(config::getDatabaseName).to(dataSource::setDatabaseName);

						propertyMapper.from(config::getRoleName).to(dataSource::setRoleName);
						propertyMapper.from(config::getPdbRoles).to(dataSource::setPdbRoles);

						propertyMapper.from(config::getConnectionFactoryClassName).to(dataSource::setConnectionFactoryClassName);
						propertyMapper.from(config::getFastConnectionFailoverEnabled).to(dataSource::setFastConnectionFailoverEnabled);

						propertyMapper.from(config::getOnsConfiguration).to(dataSource::setOnsConfiguration);

						propertyMapper.from(config::getShardingMode).to(dataSource::setShardingMode);
						propertyMapper.from(config::getMaxConnectionsPerShard).to(dataSource::setMaxConnectionsPerShard);

						propertyMapper.from(config::getMaxConnectionsPerService).to(dataSource::setMaxConnectionsPerService);

						poolConfig(dataSource.getPoolConfiguration(), config);
					});
		}

		private static void poolConfig(final OraclePoolConfiguration poolConfiguration,
		                               final OracleConfig dataSourceConfig) {
			propertyMapper.from(dataSourceConfig::getMinPoolSize).to(poolConfiguration::setMinPoolSize);
			propertyMapper.from(dataSourceConfig::getMaxPoolSize).to(poolConfiguration::setMaxPoolSize);

			propertyMapper.from(dataSourceConfig::getMaxIdleTime).to(poolConfiguration::setMaxIdleTime);
			propertyMapper.from(dataSourceConfig::getTimeToLiveConnectionTimeout).to(poolConfiguration::setTimeToLiveConnectionTimeout);

			propertyMapper.from(dataSourceConfig::getTrustIdleConnection).to(poolConfiguration::setTrustIdleConnection);
			propertyMapper.from(dataSourceConfig::getMaxConnectionReuseTime).to(poolConfiguration::setMaxConnectionReuseTime);
			propertyMapper.from(dataSourceConfig::getMaxConnectionReuseCount).to(poolConfiguration::setMaxConnectionReuseCount);

			propertyMapper.from(dataSourceConfig::getConnectionLabelingHighCost).to(poolConfiguration::setConnectionLabelingHighCost);
			propertyMapper.from(dataSourceConfig::getHighCostConnectionReuseThreshold).to(poolConfiguration::setHighCostConnectionReuseThreshold);
			propertyMapper.from(dataSourceConfig::getConnectionRepurposeThreshold).to(poolConfiguration::setConnectionRepurposeThreshold);

			propertyMapper.from(dataSourceConfig::getTimeoutCheckInterval).to(poolConfiguration::setTimeoutCheckInterval);

			propertyMapper.from(dataSourceConfig::getMaxStatements).to(poolConfiguration::setMaxStatements);

			propertyMapper.from(dataSourceConfig::getConnectionHarvestTriggerCount).to(poolConfiguration::setConnectionHarvestTriggerCount);
			propertyMapper.from(dataSourceConfig::getConnectionHarvestMaxCount).to(poolConfiguration::setConnectionHarvestMaxCount);

			propertyMapper.from(dataSourceConfig::getReadOnlyInstanceAllowed).to(poolConfiguration::setReadOnlyInstanceAllowed);
			propertyMapper.from(dataSourceConfig::getCreateConnectionInBorrowThread).to(poolConfiguration::setCreateConnectionInBorrowThread);
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
						propertyMapper.from(config::getJndiName).to(dataSource::setJndiName);

						propertyMapper.from(config::getAlternateUsernameAllowed).to(dataSource::setAlternateUsernameAllowed);

						propertyMapper.from(config::getCommitOnReturn).to(dataSource::setCommitOnReturn);
						propertyMapper.from(config::getRollbackOnReturn).to(dataSource::setRollbackOnReturn);

						propertyMapper.from(config::getValidatorClassName).to(dataSource::setValidatorClassName);
						propertyMapper.from(config::getValidationInterval).to(dataSource::setValidationInterval);

						propertyMapper.from(config::getJdbcInterceptors).to(dataSource::setJdbcInterceptors);

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
			propertyMapper.from(dataSourceConfig::getMaxActive).to(poolConfiguration::setMaxActive);
			propertyMapper.from(dataSourceConfig::getMaxAge).to(poolConfiguration::setMaxAge);

			propertyMapper.from(dataSourceConfig::getTestOnConnect).to(poolConfiguration::setTestOnConnect);

			propertyMapper.from(dataSourceConfig::getUseDisposableConnectionFacade).to(poolConfiguration::setUseDisposableConnectionFacade);
			propertyMapper.from(dataSourceConfig::getIgnoreExceptionOnPreLoad).to(poolConfiguration::setIgnoreExceptionOnPreLoad);

			propertyMapper.from(dataSourceConfig::getFairQueue).to(poolConfiguration::setFairQueue);
			propertyMapper.from(dataSourceConfig::getUseStatementFacade).to(poolConfiguration::setUseStatementFacade);

			propertyMapper.from(dataSourceConfig::getRemoveAbandoned).to(poolConfiguration::setRemoveAbandoned);
			propertyMapper.from(dataSourceConfig::getSuspectTimeout).to(poolConfiguration::setSuspectTimeout);
			propertyMapper.from(dataSourceConfig::getAbandonWhenPercentageFull).to(poolConfiguration::setAbandonWhenPercentageFull);

			propertyMapper.from(dataSourceConfig::getPropagateInterruptState).to(poolConfiguration::setPropagateInterruptState);
			propertyMapper.from(dataSourceConfig::getLogValidationErrors).to(poolConfiguration::setLogValidationErrors);

			propertyMapper.from(dataSourceConfig::getUseLock).to(poolConfiguration::setUseLock);
			propertyMapper.from(dataSourceConfig::getUseEquals).to(poolConfiguration::setUseEquals);
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
