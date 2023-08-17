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

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

/**
 * @author Yong.Teng
 * @since 2.0.0
 */
class DataSources {

	protected static <T extends javax.sql.DataSource> T createDataSource(final DataSourceProperties properties,
																		 final Class<T> type) {
		return properties.initializeDataSourceBuilder().type(type).build();
	}

	public final static class HikariDataSource extends com.buession.jdbc.datasource.HikariDataSource {

		private final DataSourceProperties properties;

		public HikariDataSource(final DataSourceProperties properties) {
			super(properties.determineDriverClassName(), properties.determineUrl(), properties.determineUsername(),
					properties.determinePassword());
			this.properties = properties;
		}

		@Override
		public com.zaxxer.hikari.HikariDataSource createDataSource() {
			final com.zaxxer.hikari.HikariDataSource dataSource = DataSources.createDataSource(properties,
					com.zaxxer.hikari.HikariDataSource.class);

			initialize(dataSource);

			return dataSource;
		}

	}

	public final static class Dbcp2DataSource extends com.buession.jdbc.datasource.Dbcp2DataSource {

		private final DataSourceProperties properties;

		public Dbcp2DataSource(final DataSourceProperties properties) {
			super(properties.determineDriverClassName(), properties.determineUrl(), properties.determineUsername(),
					properties.determinePassword());
			this.properties = properties;
		}

		@Override
		public BasicDataSource createDataSource() {
			final BasicDataSource dataSource = DataSources.createDataSource(properties,
					org.apache.commons.dbcp2.BasicDataSource.class);

			initialize(dataSource);

			return dataSource;
		}

	}

	public final static class DruidDataSource extends com.buession.jdbc.datasource.DruidDataSource {

		private final DataSourceProperties properties;

		public DruidDataSource(final DataSourceProperties properties) {
			super(properties.determineDriverClassName(), properties.determineUrl(), properties.determineUsername(),
					properties.determinePassword());
			this.properties = properties;
		}

		@Override
		public com.alibaba.druid.pool.DruidDataSource createDataSource() {
			final com.alibaba.druid.pool.DruidDataSource dataSource = DataSources.createDataSource(properties,
					com.alibaba.druid.pool.DruidDataSource.class);

			initialize(dataSource);

			return dataSource;
		}

	}

	public final static class TomcatDataSource extends com.buession.jdbc.datasource.TomcatDataSource {

		private final DataSourceProperties properties;

		public TomcatDataSource(final DataSourceProperties properties) {
			super(properties.determineDriverClassName(), properties.determineUrl(), properties.determineUsername(),
					properties.determinePassword());
			this.properties = properties;
		}

		@Override
		public org.apache.tomcat.jdbc.pool.DataSource createDataSource() {
			final org.apache.tomcat.jdbc.pool.DataSource dataSource = DataSources.createDataSource(properties,
					org.apache.tomcat.jdbc.pool.DataSource.class);

			initialize(dataSource);

			return dataSource;
		}

	}

	public final static class GenericDataSource extends com.buession.jdbc.datasource.GenericDataSource {

		private final DataSourceProperties properties;

		public GenericDataSource(final DataSourceProperties properties) {
			super(properties.determineDriverClassName(), properties.determineUrl(), properties.determineUsername(),
					properties.determinePassword());
			this.properties = properties;
		}

		@Override
		public javax.sql.DataSource createDataSource() {
			final javax.sql.DataSource dataSource = DataSources.createDataSource(properties, null);

			initialize(dataSource);

			return dataSource;
		}

	}

}
