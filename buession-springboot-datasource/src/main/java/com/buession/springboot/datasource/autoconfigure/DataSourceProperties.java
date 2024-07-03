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

import com.buession.jdbc.datasource.config.Dbcp2PoolConfiguration;
import com.buession.jdbc.datasource.config.DruidPoolConfiguration;
import com.buession.jdbc.datasource.config.GenericPoolConfiguration;
import com.buession.jdbc.datasource.config.HikariPoolConfiguration;
import com.buession.jdbc.datasource.config.TomcatPoolConfiguration;
import com.buession.springboot.datasource.core.DataSourceConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据源配置
 *
 * @author Yong.Teng
 */
@ConfigurationProperties(prefix = DataSourceProperties.PREFIX)
public class DataSourceProperties {

	public final static String PREFIX = "spring.datasource";

	/**
	 * Fully qualified name of the connection pool implementation to use. By default, it
	 * is auto-detected from the classpath.
	 */
	@Deprecated
	private Class<? extends DataSource> type;

	/**
	 * 数据库驱动类名
	 */
	private String driverClassName;

	/**
	 * Master 配置
	 * {@link org.springframework.boot.autoconfigure.jdbc.DataSourceProperties}
	 */
	@NestedConfigurationProperty
	private DataSourceConfig master = new DataSourceConfig();

	/**
	 * Slaves 配置
	 * {@link DataSourceConfig}
	 */
	private List<DataSourceConfig> slaves = new ArrayList<>();

	/**
	 * Hikari 数据源配置
	 *
	 * @since 1.3.2
	 */
	private HikariPoolConfiguration hikari;

	/**
	 * Dbcp2 数据源配置
	 *
	 * @since 1.3.2
	 */
	private Dbcp2PoolConfiguration dbcp2;

	/**
	 * Druid 数据源配置
	 *
	 * @since 1.3.2
	 */
	private DruidPoolConfiguration druid;

	/**
	 * Tomcat 数据源配置
	 *
	 * @since 1.3.2
	 */
	private TomcatPoolConfiguration tomcat;

	/**
	 * Generic 数据源配置
	 *
	 * @since 1.3.2
	 */
	private GenericPoolConfiguration generic;

	@Deprecated
	public Class<? extends DataSource> getType() {
		return type;
	}

	@Deprecated
	public void setType(Class<? extends DataSource> type) {
		this.type = type;
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
	 * 返回 Master 配置
	 *
	 * @return Master 配置
	 */
	public DataSourceConfig getMaster() {
		return master;
	}

	/**
	 * 设置 Master 配置
	 *
	 * @param master
	 * 		Master 配置
	 */
	public void setMaster(DataSourceConfig master) {
		this.master = master;
	}

	/**
	 * 返回 Slaves 配置
	 *
	 * @return Slaves 配置
	 */
	public List<DataSourceConfig> getSlaves() {
		return slaves;
	}

	/**
	 * 设置 Slaves 配置
	 *
	 * @param slaves
	 * 		Slaves 配置
	 */
	public void setSlaves(List<DataSourceConfig> slaves) {
		this.slaves = slaves;
	}

	/**
	 * 返回 Hikari 数据源配置
	 *
	 * @return Hikari 数据源配置
	 *
	 * @since 1.3.2
	 */
	public HikariPoolConfiguration getHikari() {
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
	public void setHikari(HikariPoolConfiguration hikari) {
		this.hikari = hikari;
	}

	/**
	 * 返回 Dbcp2 数据源配置
	 *
	 * @return Dbcp2 数据源配置
	 *
	 * @since 1.3.2
	 */
	public Dbcp2PoolConfiguration getDbcp2() {
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
	public void setDbcp2(Dbcp2PoolConfiguration dbcp2) {
		this.dbcp2 = dbcp2;
	}

	/**
	 * 返回 Druid 数据源配置
	 *
	 * @return Druid 数据源配置
	 */
	public DruidPoolConfiguration getDruid() {
		return druid;
	}

	/**
	 * 设置 Druid 数据源配置
	 *
	 * @param druid
	 * 		Druid 数据源配置
	 */
	public void setDruid(DruidPoolConfiguration druid) {
		this.druid = druid;
	}

	/**
	 * 返回 Tomcat 数据源配置
	 *
	 * @return Tomcat 数据源配置
	 */
	public TomcatPoolConfiguration getTomcat() {
		return tomcat;
	}

	/**
	 * 设置 Tomcat 数据源配置
	 *
	 * @param tomcat
	 * 		Tomcat 数据源配置
	 */
	public void setTomcat(TomcatPoolConfiguration tomcat) {
		this.tomcat = tomcat;
	}

	/**
	 * 返回 Generic 数据源配置
	 *
	 * @return Generic 数据源配置
	 */
	public GenericPoolConfiguration getGeneric() {
		return generic;
	}

	/**
	 * 设置 Generic 数据源配置
	 *
	 * @param generic
	 * 		Generic 数据源配置
	 */
	public void setGeneric(GenericPoolConfiguration generic) {
		this.generic = generic;
	}

}
