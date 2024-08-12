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

import com.alibaba.druid.pool.DruidDataSourceMBean;
import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.core.validator.Validate;
import com.buession.jdbc.datasource.Dbcp2DataSource;
import com.buession.jdbc.datasource.DruidDataSource;
import com.buession.jdbc.datasource.HikariDataSource;
import com.buession.jdbc.datasource.TomcatDataSource;
import com.buession.springboot.datasource.metadata.DataSourcePoolMetadata;
import com.buession.springboot.datasource.metadata.DataSourcePoolMetadataProvider;
import com.zaxxer.hikari.HikariConfigMXBean;
import org.apache.commons.dbcp2.BasicDataSourceMXBean;
import org.apache.tomcat.jdbc.pool.jmx.ConnectionPoolMBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.jdbc.DataSourceUnwrapper;
import org.springframework.boot.jdbc.metadata.CommonsDbcp2DataSourcePoolMetadata;
import org.springframework.boot.jdbc.metadata.DruidDataSourcePoolMetadata;
import org.springframework.boot.jdbc.metadata.HikariDataSourcePoolMetadata;
import org.springframework.boot.jdbc.metadata.TomcatDataSourcePoolMetadata;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * DataSource Pool Metadata Providers {@link DataSourcePoolMetadataProvider} Auto Configuration
 *
 * @author Yong.Teng
 * @since 1.3.2
 */
@Configuration(proxyBeanMethods = false)
public class DataSourcePoolMetadataProvidersConfiguration {

	abstract static class AbstractPoolDataSourceMetadataProviderConfiguration<PMP extends DataSourcePoolMetadataProvider<?>> {

		protected final static PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

		abstract PMP poolDataSourceMetadataProvider();

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnBean(HikariDataSource.class)
	static class HikariPoolDataSourceMetadataProviderConfiguration extends
			AbstractPoolDataSourceMetadataProviderConfiguration<DataSourcePoolMetadataProvider.HikariDataSourcePoolMetadataProvider> {

		@Bean
		@Override
		public DataSourcePoolMetadataProvider.HikariDataSourcePoolMetadataProvider poolDataSourceMetadataProvider() {
			return (dataSource)->{
				DataSourcePoolMetadata dataSourcePoolMetadata = new DataSourcePoolMetadata();
				com.zaxxer.hikari.HikariDataSource hikariDataSource = DataSourceUnwrapper.unwrap(dataSource.getMaster(),
						HikariConfigMXBean.class, com.zaxxer.hikari.HikariDataSource.class);

				propertyMapper.from(hikariDataSource).as(HikariDataSourcePoolMetadata::new)
						.to(dataSourcePoolMetadata::setMaster);

				if(Validate.isNotEmpty(dataSource.getSlaves())){
					dataSourcePoolMetadata.setSlaves(dataSource.getSlaves().stream()
							.map((datasource)->DataSourceUnwrapper.unwrap(datasource, HikariConfigMXBean.class,
									com.zaxxer.hikari.HikariDataSource.class)).filter(Objects::nonNull)
							.map(HikariDataSourcePoolMetadata::new).collect(Collectors.toList()));
				}

				return dataSourcePoolMetadata;
			};
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnBean(Dbcp2DataSource.class)
	static class Dbcp2PoolDataSourceMetadataProviderConfiguration extends
			AbstractPoolDataSourceMetadataProviderConfiguration<DataSourcePoolMetadataProvider.Dbcp2DataSourcePoolMetadataProvider> {

		@Bean
		@Override
		public DataSourcePoolMetadataProvider.Dbcp2DataSourcePoolMetadataProvider poolDataSourceMetadataProvider() {
			return (dataSource)->{
				DataSourcePoolMetadata dataSourcePoolMetadata = new DataSourcePoolMetadata();
				org.apache.commons.dbcp2.BasicDataSource dbcp2DataSource = DataSourceUnwrapper.unwrap(
						dataSource.getMaster(), BasicDataSourceMXBean.class,
						org.apache.commons.dbcp2.BasicDataSource.class);

				propertyMapper.from(dbcp2DataSource).as(CommonsDbcp2DataSourcePoolMetadata::new)
						.to(dataSourcePoolMetadata::setMaster);

				if(Validate.isNotEmpty(dataSource.getSlaves())){
					dataSourcePoolMetadata.setSlaves(dataSource.getSlaves().stream()
							.map((datasource)->DataSourceUnwrapper.unwrap(datasource, BasicDataSourceMXBean.class,
									org.apache.commons.dbcp2.BasicDataSource.class)).filter(Objects::nonNull)
							.map(CommonsDbcp2DataSourcePoolMetadata::new).collect(Collectors.toList()));
				}

				return dataSourcePoolMetadata;
			};
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnBean(DruidDataSource.class)
	static class DruidPoolDataSourceMetadataProviderConfiguration extends
			AbstractPoolDataSourceMetadataProviderConfiguration<DataSourcePoolMetadataProvider.DruidDataSourcePoolMetadataProvider> {

		@Bean
		@Override
		public DataSourcePoolMetadataProvider.DruidDataSourcePoolMetadataProvider poolDataSourceMetadataProvider() {
			return (dataSource)->{
				DataSourcePoolMetadata dataSourcePoolMetadata = new DataSourcePoolMetadata();
				com.alibaba.druid.pool.DruidDataSource druidDataSource = DataSourceUnwrapper.unwrap(
						dataSource.getMaster(), DruidDataSourceMBean.class,
						com.alibaba.druid.pool.DruidDataSource.class);

				propertyMapper.from(druidDataSource).as(DruidDataSourcePoolMetadata::new)
						.to(dataSourcePoolMetadata::setMaster);

				if(Validate.isNotEmpty(dataSource.getSlaves())){
					dataSourcePoolMetadata.setSlaves(dataSource.getSlaves().stream()
							.map((datasource)->DataSourceUnwrapper.unwrap(datasource, DruidDataSourceMBean.class,
									com.alibaba.druid.pool.DruidDataSource.class)).filter(Objects::nonNull)
							.map(DruidDataSourcePoolMetadata::new).collect(Collectors.toList()));
				}

				return dataSourcePoolMetadata;
			};
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnBean(TomcatDataSource.class)
	static class TomcatDataSourcePoolMetadataProviderConfiguration extends
			AbstractPoolDataSourceMetadataProviderConfiguration<DataSourcePoolMetadataProvider.TomcatDataSourcePoolMetadataProvider> {

		@Bean
		@Override
		public DataSourcePoolMetadataProvider.TomcatDataSourcePoolMetadataProvider poolDataSourceMetadataProvider() {
			return (dataSource)->{
				DataSourcePoolMetadata dataSourcePoolMetadata = new DataSourcePoolMetadata();
				org.apache.tomcat.jdbc.pool.DataSource tomcatDataSource = DataSourceUnwrapper.unwrap(
						dataSource.getMaster(), ConnectionPoolMBean.class,
						org.apache.tomcat.jdbc.pool.DataSource.class);

				propertyMapper.from(tomcatDataSource).as(TomcatDataSourcePoolMetadata::new)
						.to(dataSourcePoolMetadata::setMaster);

				if(Validate.isNotEmpty(dataSource.getSlaves())){
					dataSourcePoolMetadata.setSlaves(dataSource.getSlaves().stream()
							.map((datasource)->DataSourceUnwrapper.unwrap(datasource, ConnectionPoolMBean.class,
									org.apache.tomcat.jdbc.pool.DataSource.class)).filter(Objects::nonNull)
							.map(TomcatDataSourcePoolMetadata::new).collect(Collectors.toList()));
				}

				return dataSourcePoolMetadata;
			};
		}

	}

}
