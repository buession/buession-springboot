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
import com.buession.springboot.datasource.core.DataSourceType;
import com.zaxxer.hikari.HikariConfigMXBean;
import com.zaxxer.hikari.HikariDataSource;
import oracle.ucp.jdbc.PoolDataSource;
import org.apache.tomcat.jdbc.pool.DataSourceProxy;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.DataSourceUnwrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.jmx.export.MBeanExporter;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Configures DataSource related MBeans.
 *
 * @author Yong.Teng
 * @since 3.0.0
 */
@AutoConfiguration
@ConditionalOnProperty(prefix = "spring.jmx", name = "enabled", havingValue = "true", matchIfMissing = true)
public class DataSourceJmxConfiguration {

	@AutoConfiguration
	@ConditionalOnClass(org.apache.commons.dbcp2.BasicDataSource.class)
	@ConditionalOnProperty(prefix = DataSourceProperties.PREFIX, name = "type", havingValue = DataSourceType.DHCP2,
			matchIfMissing = true)
	static class Dbcp2 {

	}

	@AutoConfiguration
	@ConditionalOnClass(DruidDataSource.class)
	@ConditionalOnProperty(prefix = DataSourceProperties.PREFIX, name = "type", havingValue = DataSourceType.DRUID,
			matchIfMissing = true)
	static class Druid {

	}

	@AutoConfiguration
	@ConditionalOnClass(HikariDataSource.class)
	@ConditionalOnProperty(prefix = DataSourceProperties.PREFIX, name = "type", havingValue = DataSourceType.HIKARI,
			matchIfMissing = true)
	static class Hikari {

		private final DataSource dataSource;

		private final ObjectProvider<MBeanExporter> mBeanExporter;

		Hikari(DataSource dataSource, ObjectProvider<MBeanExporter> mBeanExporter) {
			this.dataSource = dataSource;
			this.mBeanExporter = mBeanExporter;
			validateMBeans();
		}

		private void validateMBeans() {
			HikariDataSource hikariDataSource = DataSourceUnwrapper.unwrap(this.dataSource, HikariConfigMXBean.class,
					HikariDataSource.class);
			if(hikariDataSource != null && hikariDataSource.isRegisterMbeans()){
				this.mBeanExporter.ifUnique((exporter)->exporter.addExcludedBean("dataSource"));
			}
		}

	}

	@AutoConfiguration
	@ConditionalOnClass(PoolDataSource.class)
	@ConditionalOnProperty(prefix = DataSourceProperties.PREFIX, name = "type", havingValue = DataSourceType.ORACLE,
			matchIfMissing = true)
	static class Oracle {

	}

	@AutoConfiguration
	@ConditionalOnClass(org.apache.tomcat.jdbc.pool.DataSource.class)
	@ConditionalOnProperty(prefix = DataSourceProperties.PREFIX, name = "type", havingValue = DataSourceType.TOMCAT,
			matchIfMissing = true)
	static class Tomcat {

		private final static Logger logger = LoggerFactory.getLogger(Tomcat.class);

		@Bean
		@ConditionalOnMissingBean(name = "dataSourceMBean")
		Object dataSourceMBean(DataSource dataSource) {
			DataSourceProxy dataSourceProxy = DataSourceUnwrapper.unwrap(dataSource, PoolConfiguration.class,
					DataSourceProxy.class);
			if(dataSourceProxy != null){
				try{
					return dataSourceProxy.createPool().getJmxPool();
				}catch(SQLException ex){
					logger.warn("Cannot expose DataSource to JMX (could not connect)");
				}
			}
			return null;
		}

	}

}
