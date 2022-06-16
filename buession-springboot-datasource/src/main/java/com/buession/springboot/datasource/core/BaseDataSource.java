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
package com.buession.springboot.datasource.core;

import com.buession.jdbc.datasource.config.Dbcp2PoolConfiguration;
import com.buession.jdbc.datasource.config.DruidPoolConfiguration;
import com.buession.jdbc.datasource.config.GenericPoolConfiguration;
import com.buession.jdbc.datasource.config.HikariPoolConfiguration;
import com.buession.jdbc.datasource.config.PoolConfiguration;
import com.buession.jdbc.datasource.config.TomcatPoolConfiguration;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

/**
 * @author Yong.Teng
 * @since 1.3.2
 */
public class BaseDataSource {

	protected static <T extends javax.sql.DataSource> T createDataSource(final DataSourceProperties properties,
																		 final Class<T> type){
		return properties.initializeDataSourceBuilder().type(type).build();
	}

	public interface IDataSource<P extends PoolConfiguration, T extends javax.sql.DataSource> {

		void setPoolConfiguration(P poolConfiguration);

		void setProperties(DataSourceProperties properties);

		T createDataSource();

	}

	public final static class HikariDataSource extends com.buession.jdbc.datasource.HikariDataSource
			implements IDataSource<HikariPoolConfiguration, com.zaxxer.hikari.HikariDataSource> {

		private DataSourceProperties properties;

		public HikariDataSource(){
			super();
		}

		@Override
		public void setProperties(DataSourceProperties properties){
			this.properties = properties;
		}

		@Override
		public com.zaxxer.hikari.HikariDataSource createDataSource(){
			com.zaxxer.hikari.HikariDataSource dataSource = BaseDataSource.createDataSource(properties,
					com.zaxxer.hikari.HikariDataSource.class);

			applyPoolConfiguration(dataSource, getPoolConfiguration());

			return dataSource;
		}

	}

	public final static class Dbcp2DataSource extends com.buession.jdbc.datasource.Dbcp2DataSource
			implements IDataSource<Dbcp2PoolConfiguration, BasicDataSource> {

		private DataSourceProperties properties;

		public Dbcp2DataSource(){
			super();
		}

		@Override
		public void setProperties(DataSourceProperties properties){
			this.properties = properties;
		}

		@Override
		public BasicDataSource createDataSource(){
			BasicDataSource dataSource = BaseDataSource.createDataSource(properties,
					org.apache.commons.dbcp2.BasicDataSource.class);

			applyPoolConfiguration(dataSource, getPoolConfiguration());

			return dataSource;
		}

	}

	public final static class DruidDataSource extends com.buession.jdbc.datasource.DruidDataSource
			implements IDataSource<DruidPoolConfiguration, com.alibaba.druid.pool.DruidDataSource> {

		private DataSourceProperties properties;

		public DruidDataSource(){
			super();
		}

		@Override
		public void setProperties(DataSourceProperties properties){
			this.properties = properties;
		}

		@Override
		public com.alibaba.druid.pool.DruidDataSource createDataSource(){
			com.alibaba.druid.pool.DruidDataSource dataSource = BaseDataSource.createDataSource(properties,
					com.alibaba.druid.pool.DruidDataSource.class);

			applyPoolConfiguration(dataSource, getPoolConfiguration());

			return dataSource;
		}

	}

	public final static class TomcatDataSource extends com.buession.jdbc.datasource.TomcatDataSource
			implements IDataSource<TomcatPoolConfiguration, org.apache.tomcat.jdbc.pool.DataSource> {

		private DataSourceProperties properties;

		public TomcatDataSource(){
			super();
		}

		@Override
		public void setProperties(DataSourceProperties properties){
			this.properties = properties;
		}

		@Override
		public org.apache.tomcat.jdbc.pool.DataSource createDataSource(){
			org.apache.tomcat.jdbc.pool.DataSource dataSource = BaseDataSource.createDataSource(properties,
					org.apache.tomcat.jdbc.pool.DataSource.class);

			applyPoolConfiguration(dataSource, getPoolConfiguration());

			return dataSource;
		}

	}

	public final static class GenericDataSource extends com.buession.jdbc.datasource.GenericDataSource
			implements IDataSource<GenericPoolConfiguration, javax.sql.DataSource> {

		private DataSourceProperties properties;

		public GenericDataSource(){
			super();
		}

		@Override
		public void setProperties(DataSourceProperties properties){
			this.properties = properties;
		}

		@Override
		public javax.sql.DataSource createDataSource(){
			javax.sql.DataSource dataSource = BaseDataSource.createDataSource(properties, null);

			applyPoolConfiguration(dataSource, getPoolConfiguration());

			return dataSource;
		}

	}

}
