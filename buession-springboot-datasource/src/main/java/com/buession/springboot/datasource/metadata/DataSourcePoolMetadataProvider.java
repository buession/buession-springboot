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
package com.buession.springboot.datasource.metadata;

import com.buession.jdbc.datasource.DataSource;
import com.buession.jdbc.datasource.Dbcp2DataSource;
import com.buession.jdbc.datasource.DruidDataSource;
import com.buession.jdbc.datasource.HikariDataSource;
import com.buession.jdbc.datasource.TomcatDataSource;

/**
 * Provide a {@link DataSourcePoolMetadata} based on a {@link DataSource}.
 *
 * @author Yong.Teng
 * @since 1.3.2
 */
@FunctionalInterface
public interface DataSourcePoolMetadataProvider<S extends javax.sql.DataSource> {

	/**
	 * Return the {@link DataSourcePoolMetadata} instance able to manage the specified
	 * {@link DataSource} or {@code null} if the given data source could not be handled.
	 *
	 * @param dataSource
	 * 		the data source
	 *
	 * @return the data source pool metadata
	 */
	DataSourcePoolMetadata getDataSourcePoolMetadata(com.buession.springboot.datasource.core.DataSource dataSource);

	/**
	 * Provide a {@link DataSourcePoolMetadata} based on a {@link HikariDataSource}.
	 *
	 * @author Yong.Teng
	 * @since 1.3.2
	 */
	@FunctionalInterface
	interface HikariDataSourcePoolMetadataProvider extends DataSourcePoolMetadataProvider<com.zaxxer.hikari.HikariDataSource> {

	}

	/**
	 * Provide a {@link DataSourcePoolMetadata} based on a {@link Dbcp2DataSource}.
	 *
	 * @author Yong.Teng
	 * @since 1.3.2
	 */
	@FunctionalInterface
	interface Dbcp2DataSourcePoolMetadataProvider extends DataSourcePoolMetadataProvider<org.apache.commons.dbcp2.BasicDataSource> {

	}

	/**
	 * Provide a {@link DataSourcePoolMetadata} based on a {@link DruidDataSource}.
	 *
	 * @author Yong.Teng
	 * @since 1.3.2
	 */
	@FunctionalInterface
	interface DruidDataSourcePoolMetadataProvider extends DataSourcePoolMetadataProvider<com.alibaba.druid.pool.DruidDataSource> {

	}

	/**
	 * Provide a {@link DataSourcePoolMetadata} based on a {@link TomcatDataSource}.
	 *
	 * @author Yong.Teng
	 * @since 1.3.2
	 */
	@FunctionalInterface
	interface TomcatDataSourcePoolMetadataProvider extends DataSourcePoolMetadataProvider<org.apache.tomcat.jdbc.pool.DataSource> {

	}

}
