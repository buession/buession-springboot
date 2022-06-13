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
 * | Copyright @ 2013-2022 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.cache.redis.autoconfigure;

import com.buession.core.validator.Validate;
import com.buession.redis.client.connection.datasource.DataSource;
import com.buession.redis.client.connection.datasource.jedis.JedisClusterDataSource;
import com.buession.redis.client.connection.datasource.jedis.JedisDataSource;
import com.buession.redis.client.connection.datasource.jedis.JedisSentinelDataSource;
import com.buession.redis.core.RedisNode;
import com.buession.redis.core.RedisURI;
import com.buession.springboot.cache.redis.utils.RedisNodeUtils;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

import java.text.ParseException;
import java.time.Duration;
import java.util.List;

/**
 * Jedis 连接对象 AutoConfiguration 抽象类
 *
 * @author Yong.Teng
 * @since 1.3.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(RedisProperties.class)
public class DataSourceConfiguration {

	protected RedisProperties properties;

	public DataSourceConfiguration(RedisProperties properties){
		this.properties = properties;
	}

	protected static int durationToMillis(final Duration duration){
		return (int) duration.toMillis();
	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(RedisProperties.class)
	@ConditionalOnClass({Jedis.class})
	public static class Redis extends DataSourceConfiguration {

		public Redis(RedisProperties properties){
			super(properties);
		}

		@Bean
		@ConditionalOnMissingBean
		public DataSource dataSource(){
			DataSource dataSource;

			if(properties.getCluster() != null && Validate.isNotEmpty(properties.getCluster().getNodes())){
				dataSource = createJedisClusterDataSource();
			}else if(properties.getSentinel() != null && Validate.isNotEmpty(properties.getSentinel().getNodes())){
				dataSource = createJedisSentinelDataSource();
			}else{
				dataSource = createJedisDataSource();
			}

			dataSource.setConnectTimeout(durationToMillis(properties.getConnectTimeout()));
			dataSource.setSoTimeout(durationToMillis(properties.getSoTimeout()));
			dataSource.setInfiniteSoTimeout(durationToMillis(properties.getInfiniteSoTimeout()));

			dataSource.setPoolConfig(properties.getPool());

			return dataSource;
		}

		private DataSource createJedisDataSource(){
			final JedisDataSource dataSource = new JedisDataSource();

			if(Validate.hasText(properties.getHost())){
				dataSource.setHost(properties.getHost());
				dataSource.setPort(properties.getPort());
				dataSource.setPassword(properties.getPassword());
				dataSource.setDatabase(properties.getDatabase());
				dataSource.setClientName(properties.getClientName());
			}else{
				if(Validate.hasText(properties.getUri())){
					RedisURI redisURI = RedisURI.create(properties.getUri());

					dataSource.setHost(redisURI.getHost());
					dataSource.setPort(redisURI.getPort());
					dataSource.setPassword(redisURI.getPassword());
					dataSource.setDatabase(redisURI.getDatabase());
					dataSource.setClientName(redisURI.getClientName());
				}else{
					throw new BeanInitializationException("Redis host or uri cloud not be null and empty.");
				}
			}

			return dataSource;
		}

		private DataSource createJedisSentinelDataSource(){
			RedisProperties.Sentinel sentinel = properties.getSentinel();

			List<RedisNode> sentinelNodes;
			try{
				sentinelNodes = RedisNodeUtils.parse(sentinel.getNodes(), RedisNode.DEFAULT_SENTINEL_PORT);
			}catch(ParseException e){
				throw new BeanInitializationException(e.getMessage());
			}

			final JedisSentinelDataSource dataSource = new JedisSentinelDataSource();

			dataSource.setMasterName(sentinel.getMasterName());
			dataSource.setSentinelConnectTimeout(durationToMillis(sentinel.getConnectTimeout()));
			dataSource.setSentinelSoTimeout(durationToMillis(sentinel.getSoTimeout()));
			dataSource.setSentinelClientName(sentinel.getClientName());
			dataSource.setSentinels(sentinelNodes);
			dataSource.setClientName(properties.getClientName());

			return dataSource;
		}

		private DataSource createJedisClusterDataSource(){
			RedisProperties.Cluster cluster = properties.getCluster();

			List<RedisNode> nodes;
			try{
				nodes = RedisNodeUtils.parse(cluster.getNodes(), RedisNode.DEFAULT_PORT);
			}catch(ParseException e){
				throw new BeanInitializationException(e.getMessage());
			}

			final JedisClusterDataSource configuration = new JedisClusterDataSource();

			configuration.setNodes(nodes);
			configuration.setUsername(configuration.getUsername());
			configuration.setPassword(configuration.getPassword());
			configuration.setClientName(properties.getClientName());

			if(cluster.getMaxRedirects() != null){
				configuration.setMaxRedirects(cluster.getMaxRedirects());
			}

			if(cluster.getMaxTotalRetriesDuration() != null){
				configuration.setMaxTotalRetriesDuration(durationToMillis(cluster.getMaxTotalRetriesDuration()));
			}

			return configuration;
		}

	}

}
