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
package com.buession.springboot.datasource.autoconfigure;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceMBean;
import com.zaxxer.hikari.HikariConfigMXBean;
import com.zaxxer.hikari.HikariDataSource;
import oracle.ucp.jdbc.PoolDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceMXBean;
import org.apache.tomcat.jdbc.pool.jmx.ConnectionPoolMBean;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.jdbc.DataSourceUnwrapper;
import org.springframework.boot.jdbc.metadata.*;
import org.springframework.context.annotation.Bean;

/**
 * DataSource Pool Metadata Providers {@link DataSourcePoolMetadataProvider} Auto Configuration
 *
 * @author Yong.Teng
 * @since 1.3.2
 */
@AutoConfiguration
public class DataSourcePoolMetadataProvidersConfiguration
		extends org.springframework.boot.autoconfigure.jdbc.metadata.DataSourcePoolMetadataProvidersConfiguration {

	@AutoConfiguration
	@ConditionalOnBean(BasicDataSource.class)
	static class Dbcp2PoolDataSourceMetadataProviderConfiguration {

		@Bean
		public DataSourcePoolMetadataProvider poolDataSourceMetadataProvider() {
			return (dataSource)->{
				BasicDataSource dbcpDataSource = DataSourceUnwrapper.unwrap(dataSource, BasicDataSourceMXBean.class,
						BasicDataSource.class);
				if(dbcpDataSource != null){
					return new CommonsDbcp2DataSourcePoolMetadata(dbcpDataSource);
				}

				return null;
			};
		}

	}

	@AutoConfiguration
	@ConditionalOnBean(DruidDataSource.class)
	static class DruidPoolDataSourceMetadataProviderConfiguration {

		@Bean
		public DataSourcePoolMetadataProvider poolDataSourceMetadataProvider() {
			return (dataSource)->{
				DruidDataSource dbcpDataSource = DataSourceUnwrapper.unwrap(dataSource, DruidDataSourceMBean.class,
						DruidDataSource.class);
				if(dbcpDataSource != null){
					return new DruidDataSourcePoolMetadata(dbcpDataSource);
				}

				return null;
			};
		}

	}

	@AutoConfiguration
	@ConditionalOnBean(HikariDataSource.class)
	static class HikariPoolDataSourceMetadataProviderConfiguration {

		@Bean
		public DataSourcePoolMetadataProvider poolDataSourceMetadataProvider() {
			return (dataSource)->{
				HikariDataSource hikariDataSource = DataSourceUnwrapper.unwrap(dataSource,
						HikariConfigMXBean.class, com.zaxxer.hikari.HikariDataSource.class);
				if(hikariDataSource != null){
					return new HikariDataSourcePoolMetadata(hikariDataSource);
				}

				return null;
			};
		}

	}

	/**
	 * @since 3.0.0
	 */
	@AutoConfiguration
	@ConditionalOnBean(PoolDataSource.class)
	static class OraclePoolDataSourceMetadataProviderConfiguration {

		@Bean
		public DataSourcePoolMetadataProvider poolDataSourceMetadataProvider() {
			return (dataSource)->{
				PoolDataSource ucpDataSource = DataSourceUnwrapper.unwrap(dataSource, PoolDataSource.class);
				if(ucpDataSource != null){
					return new OracleUcpDataSourcePoolMetadata(ucpDataSource);
				}

				return null;
			};
		}

	}

	@AutoConfiguration
	@ConditionalOnBean(org.apache.tomcat.jdbc.pool.DataSource.class)
	static class TomcatDataSourcePoolMetadataProviderConfiguration {

		@Bean
		public DataSourcePoolMetadataProvider poolDataSourceMetadataProvider() {
			return (dataSource)->{
				org.apache.tomcat.jdbc.pool.DataSource tomcatDataSource = DataSourceUnwrapper.unwrap(dataSource,
						ConnectionPoolMBean.class, org.apache.tomcat.jdbc.pool.DataSource.class);
				if(tomcatDataSource != null){
					return new TomcatDataSourcePoolMetadata(tomcatDataSource);
				}

				return null;
			};
		}

	}

}
