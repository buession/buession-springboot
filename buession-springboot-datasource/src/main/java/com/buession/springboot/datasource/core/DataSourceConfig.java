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
package com.buession.springboot.datasource.core;

import com.buession.core.utils.Assert;
import com.buession.core.validator.Validate;
import com.buession.lang.Constants;
import com.buession.springboot.datasource.exception.DataSourceBeanCreationException;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.util.ClassUtils;

import java.util.UUID;

/**
 * 数据源配置
 *
 * @author Yong.Teng
 * @since 3.0.0
 */
public class DataSourceConfig {

	public final static String DEFAULT_NAME = "testdb";

	public final static String DEFAULT_USERNAME = "sa";

	/**
	 * Fully qualified name of the JDBC driver. Auto-detected based on the URL by default.
	 */
	private String driverClassName;

	/**
	 * Fully qualified name of the JDBC driver. Auto-detected based on the URL by default.
	 */
	private String determineDriverClassName;

	private EmbeddedDatabaseConnection embeddedDatabaseConnection = EmbeddedDatabaseConnection.NONE;

	/**
	 * JDBC URL of the database.
	 */
	private String url;

	/**
	 * JDBC URL of the database.
	 */
	private String determineUrl;

	/**
	 * Name of the datasource. Default to "testdb" when using an embedded database.
	 */
	private String name;

	/**
	 * Login username of the database.
	 */
	private String username;

	/**
	 * Login username of the database.
	 */
	private String determineUsername;

	/**
	 * Login password of the database.
	 */
	private String password;

	/**
	 * Login password of the database.
	 */
	private String determinePassword;

	public DataSourceConfig() {
		this.embeddedDatabaseConnection = EmbeddedDatabaseConnection.get(DataSourceConfig.class.getClassLoader());
	}

	/**
	 * Determine the driver to use based on this configuration and the environment.
	 *
	 * @return The driver to use
	 */
	public String determineDriverClassName() {
		if(Validate.hasText(determineDriverClassName)){
			return determineDriverClassName;
		}

		if(Validate.hasText(driverClassName)){
			Assert.isFalse(driverClassIsLoadable(),
					()->new IllegalStateException("Cannot load driver class: " + driverClassName));
			determineDriverClassName = driverClassName;
			return determineDriverClassName;
		}

		if(Validate.hasText(url)){
			determineDriverClassName = DatabaseDriver.fromJdbcUrl(url).getDriverClassName();
		}

		if(Validate.isBlank(determineDriverClassName)){
			determineDriverClassName = embeddedDatabaseConnection.getDriverClassName();
		}

		if(Validate.isBlank(determineDriverClassName)){
			throw new DataSourceBeanCreationException(this, embeddedDatabaseConnection,
					"Failed to determine a suitable driver class");
		}

		return determineDriverClassName;
	}

	/**
	 * Return fully qualified name of the JDBC driver.
	 *
	 * @return The fully qualified name of the JDBC driver.
	 */
	public String getDriverClassName() {
		return driverClassName;
	}

	/**
	 * Sets fully qualified name of the JDBC driver.
	 *
	 * @param driverClassName
	 * 		The fully qualified name of the JDBC driver.
	 */
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	/**
	 * Determine the url to use based on this configuration and the environment.
	 *
	 * @return The url to use
	 */
	public String determineUrl() {
		if(Validate.hasText(determineUrl)){
			return determineUrl;
		}

		if(Validate.hasText(url)){
			determineUrl = url;
			return determineUrl;
		}

		String databaseName = getName();

		if(Validate.isBlank(databaseName)){
			if(embeddedDatabaseConnection != EmbeddedDatabaseConnection.NONE){
				databaseName = DEFAULT_NAME;
			}else{
				databaseName = UUID.randomUUID().toString();
			}
		}

		String url = (databaseName != null) ? embeddedDatabaseConnection.getUrl(databaseName) : null;
		if(Validate.isBlank(url)){
			throw new DataSourceBeanCreationException(this, embeddedDatabaseConnection, "Failed to determine suitable" +
					" jdbc url");
		}

		return url;
	}

	/**
	 * Return JDBC URL of the database.
	 *
	 * @return The JDBC URL of the database.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets JDBC URL of the database.
	 *
	 * @param url
	 * 		The JDBC URL of the database.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Return name of the datasource.
	 *
	 * @return The name of the datasource.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets name of the datasource.
	 *
	 * @param name
	 * 		The name of the datasource.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Determine the username to use based on this configuration and the environment.
	 *
	 * @return The username to use
	 */
	public String determineUsername() {
		if(Validate.hasText(determineUsername)){
			return determineUsername;
		}

		if(Validate.hasText(username)){
			determineUsername = username;
		}else{
			determineUsername = EmbeddedDatabaseConnection.isEmbedded(
					determineDriverClassName()) ? DEFAULT_USERNAME : null;
		}

		return determineUsername;
	}

	/**
	 * Return login username of the database.
	 *
	 * @return The login username of the database.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets login username of the database.
	 *
	 * @param username
	 * 		The login username of the database.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Determine the password to use based on this configuration and the environment.
	 *
	 * @return The password to use
	 */
	public String determinePassword() {
		if(Validate.hasText(determinePassword)){
			return determinePassword;
		}

		if(Validate.hasText(password)){
			determinePassword = password;
		}else{
			determinePassword = EmbeddedDatabaseConnection.isEmbedded(determineDriverClassName()) ?
					Constants.EMPTY_STRING : null;
		}

		return determinePassword;
	}

	/**
	 * Return login password of the database.
	 *
	 * @return The login password of the database.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets login password of the database.
	 *
	 * @param password
	 * 		The login password of the database.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	private boolean driverClassIsLoadable() {
		try{
			ClassUtils.forName(driverClassName, null);
			return true;
		}catch(UnsupportedClassVersionError e){
			// Driver library has been compiled with a later JDK, propagate error
			throw e;
		}catch(Throwable e){
			return false;
		}
	}

}
