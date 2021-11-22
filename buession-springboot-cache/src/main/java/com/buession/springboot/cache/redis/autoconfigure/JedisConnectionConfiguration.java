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
 * | Copyright @ 2013-2021 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.cache.redis.autoconfigure;

import com.buession.core.utils.StringUtils;
import com.buession.core.validator.Validate;
import com.buession.redis.client.connection.RedisConnection;
import com.buession.redis.client.connection.jedis.JedisConnection;
import com.buession.redis.core.RedisURI;
import com.buession.redis.core.ShardedRedisNode;
import com.buession.redis.exception.RedisException;
import com.buession.redis.spring.JedisRedisConnectionFactoryBean;
import com.buession.redis.spring.jedis.ShardedRedisConfiguration;
import com.buession.springboot.cache.redis.core.PoolConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ShardedJedisPoolConfig;
import redis.clients.jedis.commands.JedisCommands;

import java.net.URISyntaxException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Jedis 连接对象 AutoConfiguration 抽象类
 *
 * @author Yong.Teng
 * @since 1.3.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(RedisProperties.class)
@ConditionalOnClass({JedisConnection.class, Jedis.class})
public class JedisConnectionConfiguration extends AbstractConnectionConfiguration {

	private final static Logger logger = LoggerFactory.getLogger(JedisConnectionConfiguration.class);

	public JedisConnectionConfiguration(RedisProperties properties){
		super(properties);
	}

	@Bean
	@ConditionalOnProperty(prefix = "spring.redis", name = "mode", havingValue = "sharded")
	@ConditionalOnMissingBean
	public ShardedJedisPoolConfig poolConfig(){
		PoolConfig poolConfig = properties.getPool();

		ShardedJedisPoolConfig config = new ShardedJedisPoolConfig();
		buildPoolConfig(poolConfig, config);

		if(logger.isTraceEnabled()){
			logger.trace("ShardedJedisPoolConfig bean initialize success.");
		}

		return config;
	}

	@Bean
	@ConditionalOnMissingBean
	public JedisPoolConfig jedisPoolConfig(){
		PoolConfig poolConfig = properties.getPool();

		JedisPoolConfig config = new JedisPoolConfig();
		buildPoolConfig(poolConfig, config);

		if(logger.isTraceEnabled()){
			logger.trace("JedisPoolConfig bean initialize success.");
		}

		return config;
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = "spring.redis", name = "mode", havingValue = "sharded")
	public JedisRedisConnectionFactoryBean shardedJedisRedisConnectionFactory(ShardedJedisPoolConfig poolConfig){
		return createShardedJedisRedisConnectionFactory(poolConfig);
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = "redis", name = "mode", havingValue = "sharded")
	@DeprecatedConfigurationProperty(reason = "规范名称", replacement = "spring.redis.mode")
	@Deprecated
	public JedisRedisConnectionFactoryBean deprecatedShardedJedisRedisConnectionFactory(ShardedJedisPoolConfig poolConfig){
		return createShardedJedisRedisConnectionFactory(poolConfig);
	}

	@Bean
	@ConditionalOnMissingBean
	public JedisRedisConnectionFactoryBean jedisRedisConnectionFactoryBean(JedisPoolConfig poolConfig){
		com.buession.redis.spring.jedis.JedisConfiguration configuration = new com.buession.redis.spring.jedis.JedisConfiguration();

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

		configuration.setPoolConfig(poolConfig);
		setTimeout(configuration);

		return new JedisRedisConnectionFactoryBean(configuration);
	}

	@Bean
	@ConditionalOnMissingBean
	public RedisConnection jedisConnection(JedisRedisConnectionFactoryBean connectionFactory) throws Exception{
		connectionFactory.afterPropertiesSet();
		return connectionFactory.getObject();
	}

	protected static <T extends JedisCommands> void buildPoolConfig(PoolConfig poolConfig, GenericObjectPoolConfig<T> config){
		config.setLifo(poolConfig.getLifo());
		config.setFairness(poolConfig.getFairness());
		config.setMaxWaitMillis(poolConfig.getMaxWait());
		config.setMinEvictableIdleTimeMillis(poolConfig.getMinEvictableIdleTime());
		config.setSoftMinEvictableIdleTimeMillis(poolConfig.getSoftMinEvictableIdleTime());
		config.setEvictionPolicyClassName(poolConfig.getEvictionPolicyClassName());
		config.setEvictorShutdownTimeoutMillis(poolConfig.getEvictorShutdownTimeout());
		config.setNumTestsPerEvictionRun(poolConfig.getNumTestsPerEvictionRun());
		config.setTestOnCreate(poolConfig.getTestOnCreate());
		config.setTestOnBorrow(poolConfig.getTestOnBorrow());
		config.setTestOnReturn(poolConfig.getTestOnReturn());
		config.setTestWhileIdle(poolConfig.getTestWhileIdle());
		config.setTimeBetweenEvictionRunsMillis(poolConfig.getTimeBetweenEvictionRuns());
		config.setBlockWhenExhausted(poolConfig.getBlockWhenExhausted());
		config.setJmxEnabled(poolConfig.getJmxEnabled());
		config.setJmxNamePrefix(poolConfig.getJmxNamePrefix());
		config.setJmxNameBase(poolConfig.getJmxNameBase());
		config.setMaxTotal(poolConfig.getMaxTotal());
		config.setMinIdle(poolConfig.getMinIdle());
		config.setMaxIdle(poolConfig.getMaxIdle());
	}

	protected void setTimeout(final com.buession.redis.spring.RedisConfiguration configuration){
		configuration.setConnectTimeout(properties.getConnectTimeout());
		configuration.setSoTimeout(properties.getSoTimeout());
	}

	protected static Set<ShardedRedisNode> parseShardedRedisNode(final String url) throws URISyntaxException{
		if(Validate.hasText(url)){
			return null;
		}

		String[] parts = StringUtils.splitByWholeSeparatorPreserveAllTokens(url, ";");
		Set<ShardedRedisNode> nodes = new LinkedHashSet<>(parts.length);
		RedisURI redisURI;

		for(String part : parts){
			redisURI = RedisURI.create(part);
			nodes.add(new ShardedRedisNode(redisURI.getHost(), redisURI.getPort(), redisURI.getPassword(), redisURI.getDatabase(), redisURI.getClientName(), redisURI.getWeight()));
		}

		return nodes;
	}

	private JedisRedisConnectionFactoryBean createShardedJedisRedisConnectionFactory(final ShardedJedisPoolConfig poolConfig){
		ShardedRedisConfiguration configuration = new ShardedRedisConfiguration();

		configuration.setPoolConfig(poolConfig);
		setTimeout(configuration);

		try{
			configuration.setNodes(parseShardedRedisNode(properties.getNodes()));

			return new JedisRedisConnectionFactoryBean(configuration);
		}catch(URISyntaxException e){
			throw new RedisException(e.getMessage());
		}catch(Exception e){
			throw e;
		}
	}

}
