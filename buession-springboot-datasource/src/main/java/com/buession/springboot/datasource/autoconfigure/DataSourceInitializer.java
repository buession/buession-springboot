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
 * | Copyright @ 2013-2026 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.datasource.autoconfigure;

import com.buession.core.Configurer;
import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.jdbc.config.BaseConfig;
import com.buession.jdbc.core.Callback;
import com.buession.jdbc.datasource.pool.PoolConfiguration;
import com.buession.springboot.datasource.core.DynamicUrlBuilder;
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
class DataSourceInitializer<C extends BaseConfig, P extends PoolConfiguration, ODS extends javax.sql.DataSource,
		DS extends com.buession.jdbc.datasource.DataSource<ODS, P>> {

	private final Class<DS> type;

	private final DataSourceProperties properties;

	private final C dataSourceConfig;

	private final P poolConfiguration;

	private final Configurer<DS, C> configurer;

	private final Callback<ODS, DataSourceProperties> callback;

	DataSourceInitializer(final Class<DS> type, final DataSourceProperties properties, final C dataSourceConfig,
	                      final P poolConfiguration, final Configurer<DS, C> configurer, final Callback<ODS,
					DataSourceProperties> callback) {
		this.type = type;
		this.properties = properties;
		this.dataSourceConfig = dataSourceConfig;
		this.poolConfiguration = poolConfiguration;
		this.configurer = configurer;
		this.callback = callback;
	}

	public ODS createDataSource(final DynamicUrlBuilder dynamicUrlBuilder) {
		try{
			final Constructor<DS> constructor = type.getConstructor(String.class, String.class, String.class,
					String.class);
			final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
			final DS instance = BeanUtils.instantiateClass(constructor, properties.determineDriverClassName(),
					properties.determineUrl(dynamicUrlBuilder), properties.determineUsername(dynamicUrlBuilder),
					properties.determinePassword(dynamicUrlBuilder));

			/*                     数据源基本配置开始                     */
			propertyMapper.from(properties.getConnectionProperties()).to(instance::setConnectionProperties);
			propertyMapper.from(properties.getDefaultCatalog()).to(instance::setDefaultCatalog);
			propertyMapper.from(properties.getDefaultSchema()).to(instance::setDefaultSchema);
			propertyMapper.from(properties.getInitSQL()).to(instance::setInitSQL);
			propertyMapper.from(properties.getLoginTimeout()).to(instance::setLoginTimeout);

			propertyMapper.from(dataSourceConfig.getQueryTimeout()).to(instance::setQueryTimeout);
			propertyMapper.from(dataSourceConfig.getDefaultTransactionIsolation())
					.to(instance::setDefaultTransactionIsolation);
			propertyMapper.from(dataSourceConfig.getDefaultAutoCommit()).to(instance::setDefaultAutoCommit);
			propertyMapper.from(dataSourceConfig.getDefaultReadOnly()).to(instance::setDefaultReadOnly);
			propertyMapper.from(dataSourceConfig.getAccessToUnderlyingConnectionAllowed())
					.to(instance::setAccessToUnderlyingConnectionAllowed);
			/*                     数据源基本配置结束                     */


			/*                       连接池配置开始                      */
			if(poolConfiguration != null){
				propertyMapper.from(dataSourceConfig.getPoolName()).to(poolConfiguration::setPoolName);

				propertyMapper.from(dataSourceConfig.getInitialSize()).to(poolConfiguration::setInitialSize);
				propertyMapper.from(dataSourceConfig.getMinIdle()).to(poolConfiguration::setMinIdle);
				propertyMapper.from(dataSourceConfig.getMaxIdle()).to(poolConfiguration::setMaxIdle);
				propertyMapper.from(dataSourceConfig.getMaxTotal()).to(poolConfiguration::setMaxTotal);
				propertyMapper.from(dataSourceConfig.getMaxWait()).to(poolConfiguration::setMaxWait);

				propertyMapper.from(dataSourceConfig.getTestOnCreate()).to(poolConfiguration::setTestOnCreate);
				propertyMapper.from(dataSourceConfig.getTestOnBorrow()).to(poolConfiguration::setTestOnBorrow);
				propertyMapper.from(dataSourceConfig.getTestOnReturn()).to(poolConfiguration::setTestOnReturn);
				propertyMapper.from(dataSourceConfig.getTestWhileIdle()).to(poolConfiguration::setTestWhileIdle);

				propertyMapper.from(dataSourceConfig.getValidationQuery()).to(poolConfiguration::setValidationQuery);
				propertyMapper.from(dataSourceConfig.getValidationQueryTimeout())
						.to(poolConfiguration::setValidationQueryTimeout);

				propertyMapper.from(dataSourceConfig.getMinEvictableIdle()).to(poolConfiguration::setMinEvictableIdle);
				propertyMapper.from(dataSourceConfig.getMaxEvictableIdle()).to(poolConfiguration::setMaxEvictableIdle);

				propertyMapper.from(dataSourceConfig.getNumTestsPerEvictionRun())
						.to(poolConfiguration::setNumTestsPerEvictionRun);
				propertyMapper.from(dataSourceConfig.getTimeBetweenEvictionRuns())
						.to(poolConfiguration::setTimeBetweenEvictionRuns);

				propertyMapper.from(dataSourceConfig.getRemoveAbandonedTimeout())
						.to(poolConfiguration::setRemoveAbandonedTimeout);
				propertyMapper.from(dataSourceConfig.getLogAbandoned()).to(poolConfiguration::setLogAbandoned);

				propertyMapper.from(dataSourceConfig.getJmx()).to(poolConfiguration::setJmx);
			}
			instance.setPoolConfiguration(poolConfiguration);
			/*                       连接池配置结束                      */

			configurer.configure(instance, this.dataSourceConfig);

			final ODS dataSource = instance.createDataSource();

			callback.apply(dataSource, properties);

			return dataSource;
		}catch(NoSuchMethodException e){
			throw new BeanInstantiationException(type, "Can't specify more arguments than constructor parameters");
		}
	}

}
