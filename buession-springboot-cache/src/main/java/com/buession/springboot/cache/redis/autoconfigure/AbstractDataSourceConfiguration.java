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
import com.buession.core.validator.Validate;
import com.buession.redis.client.connection.datasource.ClusterDataSource;
import com.buession.redis.client.connection.datasource.DataSource;
import com.buession.redis.client.connection.datasource.SentinelDataSource;
import com.buession.redis.client.connection.datasource.StandaloneDataSource;
import com.buession.redis.core.RedisNode;
import com.buession.redis.core.RedisURI;
import com.buession.springboot.cache.redis.utils.RedisNodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.Bean;

import java.text.ParseException;
import java.util.List;

/**
 * @author Yong.Teng
 * @since 3.0.0
 */
public abstract class AbstractDataSourceConfiguration {

	protected RedisProperties properties;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public AbstractDataSourceConfiguration(RedisProperties properties) {
		this.properties = properties;
	}
	
	public DataSource dataSource() {
		DataSource dataSource;

		if(properties.getCluster() != null && Validate.isNotEmpty(properties.getCluster().getNodes())){
			dataSource = createClusterDataSource();
		}else if(properties.getSentinel() != null && Validate.isNotEmpty(properties.getSentinel().getNodes())){
			dataSource = createSentinelDataSource();
		}else{
			dataSource = createStandaloneDataSource();
		}

		afterInitialized(dataSource);

		return dataSource;
	}

	protected void afterInitialized(final DataSource dataSource) {
		if(dataSource == null){
			final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

			// 处理全局通用属性赋值
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

	protected StandaloneDataSource createStandaloneDataSource(final StandaloneDataSource dataSource) {
		if(Validate.hasText(properties.getHost())){
			dataSource.setHost(properties.getHost());
			dataSource.setPort(properties.getPort());
			dataSource.setUsername(properties.getUsername());
			dataSource.setPassword(properties.getPassword());
			dataSource.setDatabase(properties.getDatabase());
		}else{
			if(Validate.hasText(properties.getUri())){
				RedisURI redisURI = RedisURI.create(properties.getUri());

				dataSource.setHost(redisURI.getHost());
				dataSource.setPort(redisURI.getPort());
				dataSource.setUsername(redisURI.getUsername());
				dataSource.setPassword(redisURI.getPassword());
				dataSource.setDatabase(redisURI.getDatabase());
				dataSource.setClientName(redisURI.getClientName());
			}else{
				throw new BeanInitializationException("Redis host or uri cloud not be null and empty.");
			}
		}

		return dataSource;
	}

	protected SentinelDataSource createSentinelDataSource(final SentinelDataSource dataSource) {
		final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
		RedisProperties.Sentinel sentinel = properties.getSentinel();

		List<RedisNode> sentinelNodes;
		try{
			sentinelNodes = RedisNodeUtils.parse(sentinel.getNodes(), RedisNode.DEFAULT_SENTINEL_PORT);
		}catch(ParseException e){
			throw new BeanInitializationException(e.getMessage(), e);
		}

		propertyMapper.from(properties::getUsername).to(dataSource::setUsername);
		propertyMapper.from(properties::getPassword).to(dataSource::setPassword);
		propertyMapper.from(sentinel::getConnectTimeout).as((duration)->(int) duration.toMillis())
				.to(dataSource::setSentinelConnectTimeout);
		propertyMapper.from(sentinel::getSoTimeout).as((duration)->(int) duration.toMillis())
				.to(dataSource::setSentinelSoTimeout);
		propertyMapper.from(sentinel::getClientName).to(dataSource::setSentinelClientName);

		dataSource.setSentinels(sentinelNodes);

		return dataSource;
	}

	protected ClusterDataSource createClusterDataSource(final ClusterDataSource dataSource) {
		final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
		RedisProperties.Cluster cluster = properties.getCluster();

		List<RedisNode> nodes;
		try{
			nodes = RedisNodeUtils.parse(cluster.getNodes(), RedisNode.DEFAULT_PORT);
		}catch(ParseException e){
			throw new BeanInitializationException(e.getMessage(), e);
		}

		propertyMapper.from(properties::getUsername).to(dataSource::setUsername);
		propertyMapper.from(properties::getPassword).to(dataSource::setPassword);
		propertyMapper.from(cluster::getMaxRedirects).to(dataSource::setMaxRedirects);
		propertyMapper.from(cluster::getMaxTotalRetriesDuration).as((duration)->(int) duration.toMillis())
				.to(dataSource::setMaxTotalRetriesDuration);

		dataSource.setNodes(nodes);

		return dataSource;
	}

	protected abstract StandaloneDataSource createStandaloneDataSource();

	protected abstract SentinelDataSource createSentinelDataSource();

	protected abstract ClusterDataSource createClusterDataSource();

}
