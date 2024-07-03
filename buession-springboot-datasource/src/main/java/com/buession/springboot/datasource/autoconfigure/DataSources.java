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

import com.buession.springboot.datasource.core.DataSourceConfig;
import org.springframework.boot.jdbc.DatabaseDriver;

/**
 * @author Yong.Teng
 * @since 2.0.0
 */
class DataSources {

	public final static class HikariDataSource extends com.buession.jdbc.datasource.HikariDataSource {

		public HikariDataSource(final DataSourceConfig dataSourceConfig) {
			super(dataSourceConfig.determineDriverClassName(), dataSourceConfig.determineUrl(),
					dataSourceConfig.determineUsername(), dataSourceConfig.determinePassword());
		}

	}

	public final static class Dbcp2DataSource extends com.buession.jdbc.datasource.Dbcp2DataSource {

		public Dbcp2DataSource(final DataSourceConfig dataSourceConfig) {
			super(dataSourceConfig.determineDriverClassName(), dataSourceConfig.determineUrl(),
					dataSourceConfig.determineUsername(), dataSourceConfig.determinePassword());
		}

	}

	public final static class DruidDataSource extends com.buession.jdbc.datasource.DruidDataSource {

		public DruidDataSource(final DataSourceConfig dataSourceConfig) {
			super(dataSourceConfig.determineDriverClassName(), dataSourceConfig.determineUrl(),
					dataSourceConfig.determineUsername(), dataSourceConfig.determinePassword());
		}

	}

	public final static class TomcatDataSource extends com.buession.jdbc.datasource.TomcatDataSource {

		public TomcatDataSource(final DataSourceConfig dataSourceConfig) {
			super(dataSourceConfig.determineDriverClassName(), dataSourceConfig.determineUrl(),
					dataSourceConfig.determineUsername(), dataSourceConfig.determinePassword());
		}

		@Override
		public org.apache.tomcat.jdbc.pool.DataSource createDataSource() {
			final org.apache.tomcat.jdbc.pool.DataSource dataSource = super.createDataSource();

			DatabaseDriver databaseDriver = DatabaseDriver.fromJdbcUrl(getUrl());
			String validationQuery = databaseDriver.getValidationQuery();

			if(validationQuery != null){
				dataSource.setTestOnBorrow(true);
				dataSource.setValidationQuery(validationQuery);
			}

			return dataSource;
		}

	}

	public final static class GenericDataSource extends com.buession.jdbc.datasource.GenericDataSource {

		public GenericDataSource(final DataSourceConfig dataSourceConfig) {
			super(dataSourceConfig.determineDriverClassName(), dataSourceConfig.determineUrl(),
					dataSourceConfig.determineUsername(), dataSourceConfig.determinePassword());
		}

	}

}
