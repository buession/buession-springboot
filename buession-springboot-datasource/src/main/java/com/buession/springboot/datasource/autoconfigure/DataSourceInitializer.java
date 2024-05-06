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

import com.buession.core.builder.ListBuilder;
import com.buession.core.validator.Validate;
import com.buession.jdbc.datasource.config.PoolConfiguration;
import com.buession.springboot.datasource.core.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.util.stream.Collectors;

/**
 * 数据源初始化器
 *
 * @param <T>
 *        {@link javax.sql.DataSource 实现类}
 * @param <P>
 *        {@link PoolConfiguration 实现类}
 * @param <D>
 *        {@link com.buession.jdbc.datasource.DataSource 实现类}
 *
 * @author Yong.Teng
 * @since 1.3.2
 */
class DataSourceInitializer<T extends javax.sql.DataSource, P extends PoolConfiguration, D extends com.buession.jdbc.datasource.DataSource<T, P>> {

	private final Class<D> type;

	private final P poolConfiguration;

	private final DataSourceProperties properties;

	private final static Logger logger = LoggerFactory.getLogger(DataSourceInitializer.class);

	DataSourceInitializer(final Class<D> type, final P poolConfiguration, final DataSourceProperties properties) {
		this.type = type;
		this.poolConfiguration = poolConfiguration;
		this.properties = properties;
	}

	public DataSource createDataSource() {
		final DataSource dataSource = new DataSource();

		dataSource.setMaster(createDataSource(properties.getMaster()));

		if(logger.isInfoEnabled()){
			logger.info("Create master datasource: by driver {}, type {}", properties.getDriverClassName(),
					type.getName());
		}

		if(Validate.isEmpty(properties.getSlaves())){
			dataSource.setSlaves(ListBuilder.of(createDataSource(properties.getMaster())));
		}else{
			dataSource.setSlaves(properties.getSlaves().parallelStream().map(this::createDataSource)
					.collect(Collectors.toList()));
		}

		if(logger.isInfoEnabled()){
			logger.info("Create {} size slave datasource: by driver {}, type {}", dataSource.getSlaves().size(),
					properties.getDriverClassName(), type.getName());
		}

		return dataSource;
	}

	private T createDataSource(final org.springframework.boot.autoconfigure.jdbc.DataSourceProperties properties) {
		try{
			final Constructor<D> constructor = type.getConstructor(
					org.springframework.boot.autoconfigure.jdbc.DataSourceProperties.class);
			final D instance = BeanUtils.instantiateClass(constructor, properties);

			instance.setPoolConfiguration(poolConfiguration);

			return instance.createDataSource();
		}catch(NoSuchMethodException e){
			throw new BeanInstantiationException(type, "Can't specify more arguments than constructor parameters");
		}
	}

}
