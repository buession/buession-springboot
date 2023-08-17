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
 * | Copyright @ 2013-2023 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.cache.redis.autoconfigure;

import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.core.validator.Validate;
import com.buession.redis.client.connection.datasource.jedis.JedisClusterDataSource;
import com.buession.redis.client.connection.datasource.jedis.JedisDataSource;
import com.buession.redis.client.connection.datasource.jedis.JedisRedisDataSource;
import com.buession.redis.client.connection.datasource.jedis.JedisSentinelDataSource;
import com.buession.redis.core.RedisNode;
import com.buession.redis.core.RedisURI;
import com.buession.springboot.cache.redis.utils.RedisNodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;

import java.text.ParseException;
import java.util.List;

/**
 * Jedis Redis 数据源 {@link JedisDataSource} 初始化器
 *
 * @author Yong.Teng
 * @since 2.0.0
 */
class JedisDataSourceInitializer extends AbstractDataSourceInitializer<JedisRedisDataSource> {

	private final static Logger logger = LoggerFactory.getLogger(JedisDataSourceInitializer.class);

	/**
	 * 构造函数
	 *
	 * @param properties
	 *        {@link RedisProperties}
	 */
	public JedisDataSourceInitializer(final RedisProperties properties){
		super(properties);
	}

	@Override
	public JedisRedisDataSource createInstance(){
		JedisRedisDataSource dataSource;

		if(properties.getCluster() != null && Validate.isNotEmpty(properties.getCluster().getNodes())){
			dataSource = createJedisClusterDataSource();
		}else if(properties.getSentinel() != null && Validate.isNotEmpty(properties.getSentinel().getNodes())){
			dataSource = createJedisSentinelDataSource();
		}else{
			dataSource = createJedisDataSource();
		}

		if(Validate.hasText(properties.getClientName())){
			dataSource.setClientName(properties.getClientName());
		}

		dataSource.setConnectTimeout((int) properties.getConnectTimeout().toMillis());
		dataSource.setSoTimeout((int) properties.getSoTimeout().toMillis());
		dataSource.setInfiniteSoTimeout((int) properties.getInfiniteSoTimeout().toMillis());

		dataSource.setPoolConfig(properties.getPool());

		if(logger.isInfoEnabled()){
			logger.info("Initialized {} {} pool", dataSource.getClass().getName(),
					dataSource.getPoolConfig() == null ? "without" : "with");
		}

		return dataSource;
	}

	private JedisRedisDataSource createJedisDataSource(){
		final JedisDataSource dataSource = new JedisDataSource();

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

	private JedisRedisDataSource createJedisSentinelDataSource(){
		RedisProperties.Sentinel sentinel = properties.getSentinel();

		List<RedisNode> sentinelNodes;
		try{
			sentinelNodes = RedisNodeUtils.parse(sentinel.getNodes(), RedisNode.DEFAULT_SENTINEL_PORT);
		}catch(ParseException e){
			throw new BeanInitializationException(e.getMessage(), e);
		}

		final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
		final JedisSentinelDataSource dataSource = new JedisSentinelDataSource();

		propertyMapper.from(sentinel::getMasterName).to(dataSource::setMasterName);
		propertyMapper.from(sentinel::getConnectTimeout).as((duration)->(int) duration.toMillis())
				.to(dataSource::setSentinelConnectTimeout);
		propertyMapper.from(sentinel::getSoTimeout).as((duration)->(int) duration.toMillis())
				.to(dataSource::setSentinelSoTimeout);
		propertyMapper.from(sentinel::getClientName).to(dataSource::setSentinelClientName);

		dataSource.setSentinels(sentinelNodes);

		return dataSource;
	}

	private JedisRedisDataSource createJedisClusterDataSource(){
		RedisProperties.Cluster cluster = properties.getCluster();

		List<RedisNode> nodes;
		try{
			nodes = RedisNodeUtils.parse(cluster.getNodes(), RedisNode.DEFAULT_PORT);
		}catch(ParseException e){
			throw new BeanInitializationException(e.getMessage(), e);
		}

		final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
		final JedisClusterDataSource dataSource = new JedisClusterDataSource();

		dataSource.setNodes(nodes);
		propertyMapper.from(properties::getUsername).to(dataSource::setUsername);
		propertyMapper.from(properties::getPassword).to(dataSource::setPassword);
		propertyMapper.from(cluster::getMaxRedirects).to(dataSource::setMaxRedirects);
		propertyMapper.from(cluster::getMaxTotalRetriesDuration).as((duration)->(int) duration.toMillis())
				.to(dataSource::setMaxTotalRetriesDuration);

		return dataSource;
	}

}
