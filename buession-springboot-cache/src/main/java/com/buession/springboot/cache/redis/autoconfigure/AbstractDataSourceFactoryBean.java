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
package com.buession.springboot.cache.redis.autoconfigure;

import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.redis.client.connection.datasource.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Redis 数据源 {@link DataSource} 工厂 Bean 抽象类
 *
 * @param <DS>
 * 		数据源类型
 *
 * @author Yong.Teng
 * @since 2.3.1
 */
public abstract class AbstractDataSourceFactoryBean<DS extends DataSource> implements DataSourceFactoryBean<DS> {

	protected final RedisProperties properties;

	protected DS dataSource;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 构造函数
	 *
	 * @param properties
	 *        {@link RedisProperties}
	 */
	public AbstractDataSourceFactoryBean(final RedisProperties properties) {
		this.properties = properties;
	}

	@Override
	public DS getObject() throws Exception {
		return dataSource;
	}

	@Override
	public Class<? extends DataSource> getObjectType() {
		return dataSource.getClass();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(dataSource == null){
			final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
			dataSource = createDataSource();

			propertyMapper.alwaysApplyingWhenHasText().from(properties.getClientName()).to(dataSource::setClientName);
			propertyMapper.from(properties.getConnectTimeout()).as((v)->(int) v.toMillis())
					.to(dataSource::setConnectTimeout);
			propertyMapper.from(properties.getSoTimeout()).as((v)->(int) v.toMillis()).to(dataSource::setSoTimeout);
			propertyMapper.from(properties.getInfiniteSoTimeout()).as((v)->(int) v.toMillis())
					.to(dataSource::setInfiniteSoTimeout);
			propertyMapper.from(properties.getPool()).to(dataSource::setPoolConfig);

			if(logger.isInfoEnabled()){
				logger.info("Initialized {} {} pool", dataSource.getClass().getName(),
						dataSource.getPoolConfig() == null ? "without" : "with");
			}
		}
	}

	protected abstract DS createDataSource();

}
