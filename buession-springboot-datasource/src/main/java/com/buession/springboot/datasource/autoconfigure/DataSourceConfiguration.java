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
package com.buession.springboot.datasource.autoconfigure;

import com.buession.core.validator.Validate;
import com.buession.jdbc.datasource.config.PoolConfiguration;
import com.buession.springboot.datasource.core.BaseDataSource;
import com.buession.springboot.datasource.core.DataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DataSource Auto Configuration
 *
 * @author Yong.Teng
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "spring.datasource")
public class DataSourceConfiguration {

	protected final DataSourceProperties properties;

	public DataSourceConfiguration(DataSourceProperties properties){
		this.properties = properties;
	}

	protected static <S extends javax.sql.DataSource, P extends PoolConfiguration, T extends BaseDataSource.IDataSource<P, S>> DataSource createDataSource(final Class<T> type, final P poolConfiguration, final DataSourceProperties dataSourceProperties){
		return createDataSource(type, poolConfiguration, dataSourceProperties, (dataSource, properties)->dataSource);
	}

	protected static <S extends javax.sql.DataSource, P extends PoolConfiguration, T extends BaseDataSource.IDataSource<P, S>> DataSource createDataSource(final Class<T> type, final P poolConfiguration, final DataSourceProperties dataSourceProperties, final Callback<S> callback){
		final DataSourceInitializer<S, P, T> dataSourceInitializer = new DataSourceInitializer<>(type, poolConfiguration, dataSourceProperties);
		return dataSourceInitializer.initialize(callback);
	}

	/**
	 * Hikari DataSource Configuration.
	 *
	 * @since 1.3.2
	 */
	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(DataSourceProperties.class)
	@ConditionalOnClass(com.zaxxer.hikari.HikariDataSource.class)
	@ConditionalOnMissingBean(DataSource.class)
	@ConditionalOnProperty(name = "spring.datasource.type", havingValue = "com.zaxxer.hikari.HikariDataSource", matchIfMissing = true)
	public static class Hikari extends DataSourceConfiguration {

		public Hikari(DataSourceProperties properties){
			super(properties);
		}

		@Bean
		@ConfigurationProperties(prefix = "spring.datasource.hikari")
		public DataSource dataSource(){
			return createDataSource(BaseDataSource.HikariDataSource.class, properties.getHikari(), properties, (dataSource, properties)->{
				if(Validate.hasText(properties.getName())){
					dataSource.setPoolName(properties.getName());
				}

				return dataSource;
			});
		}

	}

	/**
	 * DBCP2 DataSource Configuration.
	 *
	 * @since 1.3.2
	 */
	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(DataSourceProperties.class)
	@ConditionalOnClass(org.apache.commons.dbcp2.BasicDataSource.class)
	@ConditionalOnMissingBean(DataSource.class)
	@ConditionalOnProperty(name = "spring.datasource.type", havingValue = "org.apache.commons.dbcp2.BasicDataSource", matchIfMissing = true)
	public static class Dbcp2 extends DataSourceConfiguration {

		public Dbcp2(DataSourceProperties properties){
			super(properties);
		}

		@Bean
		@ConfigurationProperties(prefix = "spring.datasource.dbcp2")
		public DataSource dataSource(){
			return createDataSource(BaseDataSource.Dbcp2DataSource.class, properties.getDbcp2(), properties);
		}

	}

	/**
	 * Druid DataSource Configuration.
	 *
	 * @since 1.3.2
	 */
	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(DataSourceProperties.class)
	@ConditionalOnClass(com.alibaba.druid.pool.DruidDataSource.class)
	@ConditionalOnMissingBean(DataSource.class)
	@ConditionalOnProperty(name = "spring.datasource.type", havingValue = "com.alibaba.druid.pool.DruidDataSource", matchIfMissing = true)
	public static class Druid extends DataSourceConfiguration {

		public Druid(DataSourceProperties properties){
			super(properties);
		}

		@Bean
		@ConfigurationProperties(prefix = "spring.datasource.druid")
		public DataSource dataSource(){
			return createDataSource(BaseDataSource.DruidDataSource.class, properties.getDruid(), properties);
		}

	}

	/**
	 * Tomcat DataSource Configuration.
	 *
	 * @since 1.3.2
	 */
	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(DataSourceProperties.class)
	@ConditionalOnClass(org.apache.tomcat.jdbc.pool.DataSource.class)
	@ConditionalOnMissingBean(DataSource.class)
	@ConditionalOnProperty(name = "spring.datasource.type", havingValue = "org.apache.tomcat.jdbc.pool.DataSource", matchIfMissing = true)
	public static class Tomcat extends DataSourceConfiguration {

		public Tomcat(DataSourceProperties properties){
			super(properties);
		}

		@Bean
		@ConfigurationProperties(prefix = "spring.datasource.tomcat")
		public DataSource dataSource(){
			return createDataSource(BaseDataSource.TomcatDataSource.class, properties.getTomcat(), properties, (dataSource, properties)->{
				DatabaseDriver databaseDriver = DatabaseDriver.fromJdbcUrl(properties.determineUrl());
				String validationQuery = databaseDriver.getValidationQuery();

				if(validationQuery != null){
					dataSource.setTestOnBorrow(true);
					dataSource.setValidationQuery(validationQuery);
				}

				return dataSource;
			});
		}

	}

	/**
	 * Generic DataSource Configuration.
	 *
	 * @since 1.3.2
	 */
	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(DataSourceProperties.class)
	@ConditionalOnMissingBean(DataSource.class)
	@ConditionalOnProperty(name = "spring.datasource.type")
	public static class Generic extends DataSourceConfiguration {

		public Generic(DataSourceProperties properties){
			super(properties);
		}

		@Bean
		public DataSource dataSource(){
			return createDataSource(BaseDataSource.GenericDataSource.class, properties.getGeneric(), properties);
		}

	}

}
