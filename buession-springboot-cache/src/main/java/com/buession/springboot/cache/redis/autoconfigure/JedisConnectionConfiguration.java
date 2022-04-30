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

import com.buession.core.utils.StringUtils;
import com.buession.core.validator.Validate;
import com.buession.redis.client.connection.RedisConnection;
import com.buession.redis.core.RedisNode;
import com.buession.redis.core.RedisURI;
import com.buession.redis.spring.JedisConnectionFactoryBean;
import com.buession.redis.spring.RedisConfiguration;
import com.buession.redis.spring.jedis.JedisClusterConfiguration;
import com.buession.redis.spring.jedis.JedisConfiguration;
import com.buession.redis.spring.jedis.JedisRedisConfiguration;
import com.buession.redis.spring.jedis.JedisSentinelConfiguration;
import com.buession.springboot.cache.redis.core.PoolConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.ConnectionPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Jedis 连接对象 AutoConfiguration 抽象类
 *
 * @author Yong.Teng
 * @since 1.3.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(RedisProperties.class)
@ConditionalOnClass({Jedis.class, JedisCluster.class})
public class JedisConnectionConfiguration extends AbstractConnectionConfiguration {

	private final static Logger logger = LoggerFactory.getLogger(JedisConnectionConfiguration.class);

	public JedisConnectionConfiguration(RedisProperties properties){
		super(properties);
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = "spring.redis", name = "cluster")
	public ConnectionPoolConfig jedisConnectionPoolConfig(){
		ConnectionPoolConfig config = new ConnectionPoolConfig();

		initPoolConfig(config, properties.getPool());

		if(logger.isTraceEnabled()){
			logger.trace("ConnectionPoolConfig bean initialize success.");
		}

		return config;
	}

	@Bean
	@ConditionalOnMissingBean(value = {ConnectionPoolConfig.class, JedisPoolConfig.class})
	public JedisPoolConfig jedisPoolConfig(){
		JedisPoolConfig config = new JedisPoolConfig();

		initPoolConfig(config, properties.getPool());

		if(logger.isTraceEnabled()){
			logger.trace("JedisPoolConfig bean initialize success.");
		}

		return config;
	}

	@Bean
	@ConditionalOnBean(value = {ConnectionPoolConfig.class})
	@ConditionalOnMissingBean
	public JedisConnectionFactoryBean jedisConnectionFactoryBean(ConnectionPoolConfig poolConfig){
		JedisRedisConfiguration configuration = createJedisClusterConfiguration(poolConfig);

		applyConfigurationTimeout(configuration);

		return new JedisConnectionFactoryBean(configuration);
	}

	@Bean
	@ConditionalOnBean(value = {JedisPoolConfig.class})
	@ConditionalOnMissingBean
	public JedisConnectionFactoryBean jedisConnectionFactoryBean(JedisPoolConfig poolConfig){
		JedisRedisConfiguration configuration;

		if(properties.getSentinel() != null && Validate.isNotEmpty(properties.getSentinel().getNodes())){
			configuration = createJedisSentinelConfiguration(poolConfig);
		}else{
			configuration = createJedisConfiguration(poolConfig);
		}

		applyConfigurationTimeout(configuration);

		return new JedisConnectionFactoryBean(configuration);
	}

	@Bean
	@ConditionalOnMissingBean
	public RedisConnection jedisConnection(JedisConnectionFactoryBean connectionFactory) throws Exception{
		connectionFactory.afterPropertiesSet();
		return connectionFactory.getObject();
	}

	private JedisRedisConfiguration createJedisConfiguration(final JedisPoolConfig poolConfig){
		final JedisConfiguration configuration = new JedisConfiguration();

		configuration.setPoolConfig(poolConfig);

		if(Validate.hasText(properties.getHost())){
			configuration.setHost(properties.getHost());
			configuration.setPort(properties.getPort());
			configuration.setPassword(properties.getPassword());
			configuration.setDatabase(properties.getDatabase());
			configuration.setClientName(properties.getClientName());
		}else{
			if(Validate.hasText(properties.getUri())){
				RedisURI redisURI = RedisURI.create(properties.getUri());

				configuration.setHost(redisURI.getHost());
				configuration.setPort(redisURI.getPort());
				configuration.setPassword(redisURI.getPassword());
				configuration.setDatabase(redisURI.getDatabase());
				configuration.setClientName(redisURI.getClientName());
			}else{
				throw new BeanInitializationException("Redis host or uri cloud not be null and empty.");
			}
		}

		return configuration;
	}

	private JedisRedisConfiguration createJedisSentinelConfiguration(final JedisPoolConfig poolConfig){
		RedisProperties.Sentinel sentinel = properties.getSentinel();

		List<RedisNode> sentinelNodes = new ArrayList<>(sentinel.getNodes().size());

		for(String node : sentinel.getNodes()){
			String[] hostAndPort = StringUtils.split(node, ':');

			if(hostAndPort.length == 1){
				sentinelNodes.add(new RedisNode(hostAndPort[0], RedisNode.DEFAULT_SENTINEL_PORT));
			}else if(hostAndPort.length == 2){
				try{
					sentinelNodes.add(new RedisNode(hostAndPort[0], Integer.parseInt(hostAndPort[1])));
				}catch(Exception e){
					throw new BeanInitializationException("Illegal redis host and port: " + node + ".");
				}
			}else{
				throw new BeanInitializationException("Illegal redis host and port: " + node + ".");
			}
		}

		final JedisSentinelConfiguration configuration = new JedisSentinelConfiguration();

		configuration.setMasterName(sentinel.getMasterName());
		configuration.setSentinelConnectTimeout(durationToInteger(sentinel.getConnectTimeout()));
		configuration.setSentinelSoTimeout(durationToInteger(sentinel.getSoTimeout()));
		configuration.setSentinelClientName(sentinel.getClientName());
		configuration.setSentinels(sentinelNodes);
		configuration.setClientName(properties.getClientName());
		configuration.setPoolConfig(poolConfig);

		return configuration;
	}

	private JedisRedisConfiguration createJedisClusterConfiguration(final ConnectionPoolConfig poolConfig){
		RedisProperties.Cluster cluster = properties.getCluster();

		List<RedisNode> nodes = new ArrayList<>(cluster.getNodes().size());

		for(String node : cluster.getNodes()){
			String[] hostAndPort = StringUtils.split(node, ':');

			if(hostAndPort.length == 1){
				nodes.add(new RedisNode(hostAndPort[0], RedisNode.DEFAULT_SENTINEL_PORT));
			}else if(hostAndPort.length == 2){
				try{
					nodes.add(new RedisNode(hostAndPort[0], Integer.parseInt(hostAndPort[1])));
				}catch(Exception e){
					throw new BeanInitializationException("Illegal redis host and port: " + node + ".");
				}
			}else{
				throw new BeanInitializationException("Illegal redis host and port: " + node + ".");
			}
		}

		final JedisClusterConfiguration configuration = new JedisClusterConfiguration();

		configuration.setNodes(nodes);
		configuration.setUsername(configuration.getUsername());
		configuration.setPassword(configuration.getPassword());
		configuration.setClientName(properties.getClientName());
		configuration.setPoolConfig(poolConfig);

		if(cluster.getMaxRedirects() != null){
			configuration.setMaxRedirects(cluster.getMaxRedirects());
		}

		if(cluster.getMaxTotalRetriesDuration() != null){
			configuration.setMaxTotalRetriesDuration(durationToInteger(cluster.getMaxTotalRetriesDuration()));
		}

		return configuration;
	}

	private <T> void initPoolConfig(final GenericObjectPoolConfig<T> poolConfig, final PoolConfig config){
		poolConfig.setLifo(config.getLifo());
		poolConfig.setFairness(config.getFairness());
		poolConfig.setMaxWait(config.getMaxWait());
		poolConfig.setMinEvictableIdleTime(config.getMinEvictableIdleTime());
		poolConfig.setSoftMinEvictableIdleTime(config.getSoftMinEvictableIdleTime());
		poolConfig.setEvictionPolicyClassName(config.getEvictionPolicyClassName());
		poolConfig.setEvictorShutdownTimeout(config.getEvictorShutdownTimeout());
		poolConfig.setNumTestsPerEvictionRun(config.getNumTestsPerEvictionRun());
		poolConfig.setTestOnCreate(config.getTestOnCreate());
		poolConfig.setTestOnBorrow(config.getTestOnBorrow());
		poolConfig.setTestOnReturn(config.getTestOnReturn());
		poolConfig.setTestWhileIdle(config.getTestWhileIdle());
		poolConfig.setTimeBetweenEvictionRuns(config.getTimeBetweenEvictionRuns());
		poolConfig.setBlockWhenExhausted(config.getBlockWhenExhausted());
		poolConfig.setJmxEnabled(config.getJmxEnabled());
		poolConfig.setJmxNamePrefix(config.getJmxNamePrefix());
		poolConfig.setJmxNameBase(config.getJmxNameBase());
		poolConfig.setMaxTotal(config.getMaxTotal());
		poolConfig.setMinIdle(config.getMinIdle());
		poolConfig.setMaxIdle(config.getMaxIdle());
	}

	private void applyConfigurationTimeout(final RedisConfiguration configuration){
		configuration.setConnectTimeout(durationToInteger(properties.getConnectTimeout()));
		configuration.setSoTimeout(durationToInteger(properties.getSoTimeout()));
		configuration.setInfiniteSoTimeout(durationToInteger(properties.getInfiniteSoTimeout()));
	}

}
