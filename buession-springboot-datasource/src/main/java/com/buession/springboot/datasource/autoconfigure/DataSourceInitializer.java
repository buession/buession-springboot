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

import com.buession.core.Customizer;
import com.buession.jdbc.core.Callback;
import com.buession.jdbc.datasource.pool.PoolConfiguration;
import com.buession.springboot.datasource.config.BaseDataSourceConfig;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;

/**
 * 数据源初始化器
 *
 * @param <C>
 * 		数据源配置
 * @param <P>
 *        {@link PoolConfiguration 实现类}
 * @param <ODS>
 *        {@link javax.sql.DataSource 实现类}
 * @param <DS>
 *        {@link com.buession.jdbc.datasource.DataSource 实现类}
 *
 * @author Yong.Teng
 * @since 1.3.2
 */
class DataSourceInitializer<C extends BaseDataSourceConfig, P extends PoolConfiguration, ODS extends javax.sql.DataSource,
		DS extends com.buession.jdbc.datasource.DataSource<ODS, P>> {

	private final Class<DS> type;

	private final DataSourceProperties properties;

	private final C dataSourceConfig;

	private final P poolConfiguration;

	private final Customizer<DS, C> customizer;

	private final Callback<ODS, DataSourceProperties> callback;

	DataSourceInitializer(final Class<DS> type, final DataSourceProperties properties, final C dataSourceConfig,
						  final P poolConfiguration, final Customizer<DS, C> customizer, final Callback<ODS,
			DataSourceProperties> callback) {
		this.type = type;
		this.properties = properties;
		this.dataSourceConfig = dataSourceConfig;
		this.poolConfiguration = poolConfiguration;
		this.customizer = customizer;
		this.callback = callback;
	}

	public ODS createDataSource() {
		try{
			final Constructor<DS> constructor = type.getConstructor(String.class, String.class, String.class,
					String.class);
			final DS instance = BeanUtils.instantiateClass(constructor, properties.determineDriverClassName(),
					properties.determineUrl(), properties.determineUsername(), properties.determinePassword());

			/*                     数据源基本配置开始                     */
			instance.setDefaultCatalog(properties.getDefaultCatalog());
			instance.setDefaultSchema(properties.getDefaultSchema());

			instance.setLoginTimeout(properties.getLoginTimeout());

			instance.setInitSQL(properties.getInitSQL());

			instance.setQueryTimeout(dataSourceConfig.getQueryTimeout());
			instance.setDefaultTransactionIsolation(dataSourceConfig.getDefaultTransactionIsolation());
			instance.setDefaultAutoCommit(dataSourceConfig.getDefaultAutoCommit());

			instance.setDefaultReadOnly(dataSourceConfig.getDefaultReadOnly());

			instance.setAccessToUnderlyingConnectionAllowed(dataSourceConfig.getAccessToUnderlyingConnectionAllowed());

			instance.setConnectionProperties(properties.getConnectionProperties());
			/*                     数据源基本配置结束                     */


			/*                       连接池配置开始                      */
			if(poolConfiguration != null){
				poolConfiguration.setPoolName(dataSourceConfig.getPoolName());

				poolConfiguration.setInitialSize(dataSourceConfig.getInitialSize());
				poolConfiguration.setMinIdle(dataSourceConfig.getMinIdle());
				poolConfiguration.setMaxIdle(dataSourceConfig.getMaxIdle());
				poolConfiguration.setMaxTotal(dataSourceConfig.getMaxTotal());
				poolConfiguration.setMaxWait(dataSourceConfig.getMaxWait());

				poolConfiguration.setTestOnCreate(dataSourceConfig.getTestOnCreate());
				poolConfiguration.setTestOnBorrow(dataSourceConfig.getTestOnBorrow());
				poolConfiguration.setTestOnReturn(dataSourceConfig.getTestOnReturn());
				poolConfiguration.setTestWhileIdle(dataSourceConfig.getTestWhileIdle());

				poolConfiguration.setValidationQuery(dataSourceConfig.getValidationQuery());
				poolConfiguration.setValidationQueryTimeout(dataSourceConfig.getValidationQueryTimeout());

				poolConfiguration.setMinEvictableIdle(dataSourceConfig.getMinEvictableIdle());
				poolConfiguration.setMaxEvictableIdle(dataSourceConfig.getMaxEvictableIdle());

				poolConfiguration.setNumTestsPerEvictionRun(dataSourceConfig.getNumTestsPerEvictionRun());
				poolConfiguration.setTimeBetweenEvictionRuns(dataSourceConfig.getTimeBetweenEvictionRuns());

				poolConfiguration.setRemoveAbandonedTimeout(dataSourceConfig.getRemoveAbandonedTimeout());
				poolConfiguration.setLogAbandoned(dataSourceConfig.getLogAbandoned());

				poolConfiguration.setJmx(dataSourceConfig.getJmx());
			}
			instance.setPoolConfiguration(poolConfiguration);
			/*                       连接池配置结束                      */

			customizer.customize(instance, this.dataSourceConfig);

			final ODS dataSource = instance.createDataSource();

			callback.apply(dataSource, properties);

			return dataSource;
		}catch(NoSuchMethodException e){
			throw new BeanInstantiationException(type, "Can't specify more arguments than constructor parameters");
		}
	}

}
