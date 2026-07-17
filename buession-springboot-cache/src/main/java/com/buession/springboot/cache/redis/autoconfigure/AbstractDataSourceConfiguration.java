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
package com.buession.springboot.cache.redis.autoconfigure;

import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.core.validator.Validate;
import com.buession.redis.client.connection.RedisNode;
import com.buession.redis.client.connection.RedisSentinelNode;
import com.buession.redis.client.connection.datasource.ClusterDataSource;
import com.buession.redis.client.connection.datasource.DataSource;
import com.buession.redis.client.connection.datasource.SentinelDataSource;
import com.buession.redis.client.connection.datasource.StandaloneDataSource;
import com.buession.springboot.cache.redis.utils.RedisNodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

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

	@Bean(name = "redisDataSource")
	public DataSource dataSource(ObjectProvider<Consumer<DataSource>> customizers) {
		final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
		DataSource dataSource;

		if(properties.getCluster() != null && Validate.isNotEmpty(properties.getCluster().getNodes())){
			dataSource = applyClusterDataSource(createClusterDataSource(), propertyMapper);
		}else if(properties.getSentinel() != null && Validate.isNotEmpty(properties.getSentinel().getNodes())){
			dataSource = applySentinelDataSource(createSentinelDataSource(), propertyMapper);
		}else{
			dataSource = applyStandaloneDataSource(createStandaloneDataSource(), propertyMapper);
		}

		// 处理全局通用属性赋值
		propertyMapper.from(properties.getClientName()).to(dataSource::setClientName);
		propertyMapper.from(properties.getConnectTimeout()).as((v)->(int) v.toMillis())
				.to(dataSource::setConnectTimeout);
		propertyMapper.from(properties.getAutoReconnect()).to(dataSource::setAutoReconnect);
		propertyMapper.from(properties.getReconnectDelay()).as((v)->(int) v.toMillis())
				.to(dataSource::setReconnectDelay);
		propertyMapper.from(properties.getSoTimeout()).as((v)->(int) v.toMillis()).to(dataSource::setSoTimeout);
		propertyMapper.from(properties.getInfiniteSoTimeout()).as((v)->(int) v.toMillis())
				.to(dataSource::setInfiniteSoTimeout);
		propertyMapper.from(properties.getPool()).to(dataSource::setPoolConfig);
		propertyMapper.from(properties.getSslOptions()).to(dataSource::setSslOptions);

		customizers.stream().forEach((customizer)->customizer.accept(dataSource));

		if(logger.isInfoEnabled()){
			logger.info("Initialized {} {} pool", dataSource.getClass().getName(),
					dataSource.getPoolConfig() == null ? "without" : "with");
		}

		return dataSource;
	}

	protected abstract StandaloneDataSource createStandaloneDataSource();

	protected abstract SentinelDataSource createSentinelDataSource();

	protected abstract ClusterDataSource createClusterDataSource();

	protected StandaloneDataSource applyStandaloneDataSource(final StandaloneDataSource dataSource,
	                                                         final PropertyMapper propertyMapper) {
		dataSource.setHost(properties.getHost());
		dataSource.setPort(properties.getPort());
		dataSource.setUsername(properties.getUsername());
		dataSource.setPassword(properties.getPassword());
		dataSource.setDatabase(properties.getDatabase());

		return dataSource;
	}

	protected SentinelDataSource applySentinelDataSource(final SentinelDataSource dataSource,
	                                                     final PropertyMapper propertyMapper) {
		RedisProperties.Sentinel sentinel = properties.getSentinel();

		Set<RedisSentinelNode> sentinelNodes = new HashSet<>(sentinel.getNodes().size());
		try{
			for(String node : sentinel.getNodes()){
				RedisNode redisNode = RedisNodeUtils.parse(node, RedisSentinelNode.DEFAULT_SENTINEL_PORT);
				sentinelNodes.add(new RedisSentinelNode(redisNode.getHost(), redisNode.getPort()));
			}
		}catch(ParseException e){
			throw new BeanInitializationException(e.getMessage(), e);
		}

		dataSource.setSentinels(sentinelNodes);
		dataSource.setMasterName(sentinel.getMasterName());

		propertyMapper.from(properties.getUsername()).to(dataSource::setUsername);
		propertyMapper.from(properties.getUsername()).to(dataSource::setPassword);

		/*
		  哨兵配置开始
		 */
		propertyMapper.from(sentinel.getUsername()).to(dataSource::setSentinelUsername);
		propertyMapper.from(sentinel.getPassword()).to(dataSource::setSentinelPassword);
		propertyMapper.from(sentinel.getConnectTimeout()).as((duration)->(int) duration.toMillis())
				.to(dataSource::setSentinelConnectTimeout);
		propertyMapper.from(sentinel.getSoTimeout()).as((duration)->(int) duration.toMillis())
				.to(dataSource::setSentinelSoTimeout);
		propertyMapper.from(sentinel.getClientName()).to(dataSource::setSentinelClientName);
		/*
		  哨兵配置结束
		 */

		return dataSource;
	}

	protected ClusterDataSource applyClusterDataSource(final ClusterDataSource dataSource,
	                                                   final PropertyMapper propertyMapper) {
		RedisProperties.Cluster cluster = properties.getCluster();

		Set<RedisNode> nodes;
		try{
			nodes = RedisNodeUtils.parse(cluster.getNodes(), RedisNode.DEFAULT_PORT);
		}catch(ParseException e){
			throw new BeanInitializationException(e.getMessage(), e);
		}

		propertyMapper.from(properties.getUsername()).to(dataSource::setUsername);
		propertyMapper.from(properties.getPassword()).to(dataSource::setPassword);
		propertyMapper.from(cluster.getMaxRedirects()).to(dataSource::setMaxRedirects);
		propertyMapper.from(cluster.getTopologyRefreshPeriod()).as((duration)->(int) duration.toMillis())
				.to(dataSource::setTopologyRefreshPeriod);

		dataSource.setNodes(nodes);

		return dataSource;
	}

}
