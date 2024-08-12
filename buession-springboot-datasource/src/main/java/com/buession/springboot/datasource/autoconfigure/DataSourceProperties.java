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

import com.buession.core.utils.Assert;
import com.buession.core.validator.Validate;
import com.buession.lang.Constants;
import com.buession.springboot.datasource.config.TomcatDataSourceConfig;
import com.buession.springboot.datasource.exception.DataSourceBeanCreationException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.util.ClassUtils;

import java.time.Duration;
import java.util.Properties;

/**
 * 数据源配置
 *
 * @author Yong.Teng
 */
@ConfigurationProperties(prefix = DataSourceProperties.PREFIX)
public class DataSourceProperties {

	public final static String PREFIX = "spring.datasource";

	public final static String DEFAULT_NAME = "testdb";

	public final static String DEFAULT_USERNAME = "sa";

	/**
	 * 数据库驱动类名
	 */
	private String driverClassName;

	/**
	 * Fully qualified name of the JDBC driver. Auto-detected based on the URL by default.
	 *
	 * @since 3.0.0
	 */
	private String determineDriverClassName;

	/**
	 * @since 3.0.0
	 */
	private final EmbeddedDatabaseConnection embeddedDatabaseConnection;

	/**
	 * JDBC URL of the database.
	 *
	 * @since 3.0.0
	 */
	private String url;

	/**
	 * JDBC URL of the database.
	 *
	 * @since 3.0.0
	 */
	private String determineUrl;

	/**
	 * Name of the datasource. Default to "testdb" when using an embedded database.
	 *
	 * @since 3.0.0
	 */
	private String name;

	/**
	 * Login username of the database.
	 *
	 * @since 3.0.0
	 */
	private String username;

	/**
	 * Login username of the database.
	 *
	 * @since 3.0.0
	 */
	private String determineUsername;

	/**
	 * Login password of the database.
	 *
	 * @since 3.0.0
	 */
	private String password;

	/**
	 * Login password of the database.
	 *
	 * @since 3.0.0
	 */
	private String determinePassword;

	/**
	 * 登录超时
	 *
	 * @since 3.0.0
	 */
	private Duration loginTimeout;

	/**
	 * 为支持 catalog 概念的数据库设置默认 catalog
	 *
	 * @since 3.0.0
	 */
	private String defaultCatalog;

	/**
	 * 设置的默认模式为支持模式的概念数据库
	 *
	 * @since 3.0.0
	 */
	private String defaultSchema;

	/**
	 * 设置一个SQL语句，在将每个新连接创建后，将其添加到池中之前执行该语句
	 *
	 * @since 3.0.0
	 */
	private String initSQL;

	/**
	 * 连接属性
	 */
	private Properties connectionProperties;

	/**
	 * DBCP2 数据源配置
	 *
	 * @since 1.3.2
	 */
	private Dbcp2DataSourceConfig dbcp2;

	/**
	 * Druid 数据源配置
	 *
	 * @since 1.3.2
	 */
	private DruidDataSourceConfig druid;

	/**
	 * Hikari 数据源配置
	 *
	 * @since 1.3.2
	 */
	private HikariDataSourceConfig hikari;

	/**
	 * Oracle 数据源配置
	 *
	 * @since 3.0.0
	 */
	private OracleDataSourceConfig oracle;

	/**
	 * Tomcat 数据源配置
	 *
	 * @since 1.3.2
	 */
	private TomcatDataSourceConfig tomcat;

	/**
	 * Generic 数据源配置
	 *
	 * @since 1.3.2
	 */
	private GenericDataSourceConfig generic;

	/**
	 * 构造函数
	 */
	public DataSourceProperties() {
		this.embeddedDatabaseConnection = EmbeddedDatabaseConnection.get(DataSourceProperties.class.getClassLoader());
	}

	/**
	 * Determine the driver to use based on this configuration and the environment.
	 *
	 * @return The driver to use
	 *
	 * @since 3.0.0
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
	 * 返回数据库驱动类名
	 *
	 * @return 数据库驱动类名
	 */
	public String getDriverClassName() {
		return driverClassName;
	}

	/**
	 * 设置数据库驱动类名
	 *
	 * @param driverClassName
	 * 		数据库驱动类名
	 */
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	/**
	 * Determine the url to use based on this configuration and the environment.
	 *
	 * @return The url to use
	 *
	 * @since 3.0.0
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
			}
		}

		String url = databaseName != null ? embeddedDatabaseConnection.getUrl(databaseName) : null;
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
	 *
	 * @since 3.0.0
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets JDBC URL of the database.
	 *
	 * @param url
	 * 		The JDBC URL of the database.
	 *
	 * @since 3.0.0
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Return name of the datasource.
	 *
	 * @return The name of the datasource.
	 *
	 * @since 3.0.0
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets name of the datasource.
	 *
	 * @param name
	 * 		The name of the datasource.
	 *
	 * @since 3.0.0
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Determine the username to use based on this configuration and the environment.
	 *
	 * @return The username to use
	 *
	 * @since 3.0.0
	 */
	public String determineUsername() {
		if(Validate.hasText(determineUsername)){
			return determineUsername;
		}

		if(Validate.hasText(username)){
			determineUsername = username;
		}else{
			determineUsername = EmbeddedDatabaseConnection.isEmbedded(
					determineDriverClassName(), determineUrl()) ? DEFAULT_USERNAME : null;
		}

		return determineUsername;
	}

	/**
	 * Return login username of the database.
	 *
	 * @return The login username of the database.
	 *
	 * @since 3.0.0
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets login username of the database.
	 *
	 * @param username
	 * 		The login username of the database.
	 *
	 * @since 3.0.0
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Determine the password to use based on this configuration and the environment.
	 *
	 * @return The password to use
	 *
	 * @since 3.0.0
	 */
	public String determinePassword() {
		if(Validate.hasText(determinePassword)){
			return determinePassword;
		}

		if(Validate.hasText(password)){
			determinePassword = password;
		}else{
			determinePassword = EmbeddedDatabaseConnection.isEmbedded(determineDriverClassName(), determineUrl()) ?
					Constants.EMPTY_STRING : null;
		}

		return determinePassword;
	}

	/**
	 * Return login password of the database.
	 *
	 * @return The login password of the database.
	 *
	 * @since 3.0.0
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets login password of the database.
	 *
	 * @param password
	 * 		The login password of the database.
	 *
	 * @since 3.0.0
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 返回登录超时
	 *
	 * @return 登录超时
	 *
	 * @since 3.0.0
	 */
	public Duration getLoginTimeout() {
		return loginTimeout;
	}

	/**
	 * 设置登录超时
	 *
	 * @param loginTimeout
	 * 		登录超时
	 *
	 * @since 3.0.0
	 */
	public void setLoginTimeout(Duration loginTimeout) {
		this.loginTimeout = loginTimeout;
	}

	/**
	 * 返回为支持 catalog 概念的数据库设置默认 catalog
	 *
	 * @return 为支持 catalog 概念的数据库设置默认 catalog
	 *
	 * @since 3.0.0
	 */
	public String getDefaultCatalog() {
		return defaultCatalog;
	}

	/**
	 * 设置为支持 catalog 概念的数据库设置默认 catalog
	 *
	 * @param defaultCatalog
	 * 		为支持 catalog 概念的数据库设置默认 catalog
	 *
	 * @since 3.0.0
	 */
	public void setDefaultCatalog(String defaultCatalog) {
		this.defaultCatalog = defaultCatalog;
	}

	/**
	 * 返回设置的默认模式为支持模式的概念数据库
	 *
	 * @return 设置的默认模式为支持模式的概念数据库
	 *
	 * @since 3.0.0
	 */
	public String getDefaultSchema() {
		return defaultSchema;
	}

	/**
	 * 设置默认模式为支持模式的概念数据库
	 *
	 * @param defaultSchema
	 * 		默认模式为支持模式的概念数据库
	 *
	 * @since 3.0.0
	 */
	public void setDefaultSchema(String defaultSchema) {
		this.defaultSchema = defaultSchema;
	}

	/**
	 * 返回在将每个新连接创建后，将其添加到池中之前执行的SQL语句
	 *
	 * @return 每个新连接创建后，将其添加到池中之前执行的SQL语句
	 *
	 * @since 3.0.0
	 */
	public String getInitSQL() {
		return initSQL;
	}

	/**
	 * 设置每个新连接创建后，将其添加到池中之前执行的SQL语句
	 *
	 * @param initSQL
	 * 		每个新连接创建后，将其添加到池中之前执行的SQL语句
	 *
	 * @since 3.0.0
	 */
	public void setInitSQL(String initSQL) {
		this.initSQL = initSQL;
	}

	/**
	 * 返回连接属性
	 *
	 * @return 连接属性
	 */
	public Properties getConnectionProperties() {
		return connectionProperties;
	}

	/**
	 * 设置连接属性
	 *
	 * @param connectionProperties
	 * 		连接属性
	 */
	public void setConnectionProperties(Properties connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

	/**
	 * 返回 Dbcp2 数据源配置
	 *
	 * @return Dbcp2 数据源配置
	 *
	 * @since 1.3.2
	 */
	public Dbcp2DataSourceConfig getDbcp2() {
		return dbcp2;
	}

	/**
	 * 设置 Dbcp2 数据源配置
	 *
	 * @param dbcp2
	 * 		Dbcp2 数据源配置
	 *
	 * @since 1.3.2
	 */
	public void setDbcp2(Dbcp2DataSourceConfig dbcp2) {
		this.dbcp2 = dbcp2;
	}

	/**
	 * 返回 Druid 数据源配置
	 *
	 * @return Druid 数据源配置
	 */
	public DruidDataSourceConfig getDruid() {
		return druid;
	}

	/**
	 * 设置 Druid 数据源配置
	 *
	 * @param druid
	 * 		Druid 数据源配置
	 */
	public void setDruid(DruidDataSourceConfig druid) {
		this.druid = druid;
	}

	/**
	 * 返回 Hikari 数据源配置
	 *
	 * @return Hikari 数据源配置
	 *
	 * @since 1.3.2
	 */
	public HikariDataSourceConfig getHikari() {
		return hikari;
	}

	/**
	 * 设置 Hikari 数据源配置
	 *
	 * @param hikari
	 * 		Hikari 数据源配置
	 *
	 * @since 1.3.2
	 */
	public void setHikari(HikariDataSourceConfig hikari) {
		this.hikari = hikari;
	}

	/**
	 * 返回 Oracle 数据源配置
	 *
	 * @return Oracle 数据源配置
	 *
	 * @since 3.0.0
	 */
	public OracleDataSourceConfig getOracle() {
		return oracle;
	}

	/**
	 * 设置 Oracle 数据源配置
	 *
	 * @param oracle
	 * 		Oracle 数据源配置
	 *
	 * @since 3.0.0
	 */
	public void setOracle(OracleDataSourceConfig oracle) {
		this.oracle = oracle;
	}

	/**
	 * 返回 Tomcat 数据源配置
	 *
	 * @return Tomcat 数据源配置
	 */
	public TomcatDataSourceConfig getTomcat() {
		return tomcat;
	}

	/**
	 * 设置 Tomcat 数据源配置
	 *
	 * @param tomcat
	 * 		Tomcat 数据源配置
	 */
	public void setTomcat(TomcatDataSourceConfig tomcat) {
		this.tomcat = tomcat;
	}

	/**
	 * 返回 Generic 数据源配置
	 *
	 * @return Generic 数据源配置
	 */
	public GenericDataSourceConfig getGeneric() {
		return generic;
	}

	/**
	 * 设置 Generic 数据源配置
	 *
	 * @param generic
	 * 		Generic 数据源配置
	 */
	public void setGeneric(GenericDataSourceConfig generic) {
		this.generic = generic;
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
