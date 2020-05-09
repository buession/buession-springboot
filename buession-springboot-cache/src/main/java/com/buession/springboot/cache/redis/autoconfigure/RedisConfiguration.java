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
 * | Copyright @ 2013-2020 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.cache.redis.autoconfigure;

import com.buession.core.validator.Validate;
import com.buession.redis.RedisTemplate;
import com.buession.redis.client.connection.RedisConnection;
import com.buession.redis.spring.JedisRedisConnectionFactoryBean;
import com.buession.springboot.cache.redis.core.PoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author Yong.Teng
 */
@Configuration
@EnableConfigurationProperties(RedisConfigProperties.class)
@ConditionalOnClass({RedisTemplate.class})
public class RedisConfiguration {

	@Autowired
	protected RedisConfigProperties redisConfigProperties;

	private final static Logger logger = LoggerFactory.getLogger(RedisConfiguration.class);

	@Bean
	@ConditionalOnBean(RedisConnection.class)
	@ConditionalOnMissingBean
	public RedisTemplate redisTemplate(RedisConnection redisConnection){
		RedisTemplate template = new RedisTemplate(redisConnection);

		template.afterPropertiesSet();
		logger.info("RedisTemplate bean init success.");

		return template;
	}

	@Configuration
	@ConditionalOnClass(name = {"redis.clients.jedis.Jedis"})
	public static class JedisConfiguration extends RedisConfiguration {

		@Bean
		@ConditionalOnMissingBean
		public RedisConnection jedisConnection() throws Exception{
			JedisRedisConnectionFactoryBean connectionFactory = new JedisRedisConnectionFactoryBean();

			connectionFactory.setHost(redisConfigProperties.getHost());
			connectionFactory.setPort(redisConfigProperties.getPort());

			if(Validate.hasText(redisConfigProperties.getPassword())){
				connectionFactory.setPassword(redisConfigProperties.getPassword());
			}

			connectionFactory.setDatabase(redisConfigProperties.getDatabase());
			connectionFactory.setClientName(redisConfigProperties.getClientName());

			if(redisConfigProperties.getConnectTimeout() == 0 && redisConfigProperties.getTimeout() > 0){
				connectionFactory.setConnectTimeout(redisConfigProperties.getTimeout());
			}else{
				connectionFactory.setConnectTimeout(redisConfigProperties.getConnectTimeout());
			}

			if(redisConfigProperties.getSoTimeout() == 0 && redisConfigProperties.getTimeout() > 0){
				connectionFactory.setSoTimeout(redisConfigProperties.getTimeout());
			}else{
				connectionFactory.setSoTimeout(redisConfigProperties.getSoTimeout());
			}

			connectionFactory.setUseSsl(redisConfigProperties.isUseSsl());
			connectionFactory.setWeight(redisConfigProperties.getWeight());

			connectionFactory.setPoolConfig(jedisPoolConfig(redisConfigProperties));

			try{
				connectionFactory.afterPropertiesSet();
			}catch(Exception e){
				logger.error("RedisConnection initialize failure: {}", e);
			}

			return connectionFactory.getObject();
		}

		private final static redis.clients.jedis.JedisPoolConfig jedisPoolConfig(RedisConfigProperties
																						 redisConfigProperties){

			PoolConfig poolConfig = redisConfigProperties.getPool();
			JedisPoolConfig config = new JedisPoolConfig();

			config.setLifo(poolConfig.getLifo());
			config.setMaxWaitMillis(poolConfig.getMaxWaitMillis());
			config.setMinEvictableIdleTimeMillis(poolConfig.getMinEvictableIdleTimeMillis());
			config.setSoftMinEvictableIdleTimeMillis(poolConfig.getSoftMinEvictableIdleTimeMillis());
			config.setNumTestsPerEvictionRun(poolConfig.getNumTestsPerEvictionRun());
			config.setEvictionPolicyClassName(poolConfig.getEvictionPolicyClassName());
			config.setTestOnBorrow(poolConfig.getTestOnBorrow());
			config.setTestOnReturn(poolConfig.getTestOnReturn());
			config.setTestWhileIdle(poolConfig.getTestWhileIdle());
			config.setTimeBetweenEvictionRunsMillis(poolConfig.getTimeBetweenEvictionRunsMillis());
			config.setBlockWhenExhausted(poolConfig.getBlockWhenExhausted());
			config.setJmxEnabled(poolConfig.getJmxEnabled());
			config.setJmxNamePrefix(poolConfig.getJmxNamePrefix());
			config.setMaxTotal(poolConfig.getMaxTotal());
			config.setMinIdle(poolConfig.getMinIdle());
			config.setMaxIdle(poolConfig.getMaxIdle());

			logger.info("JedisPoolConfig bean init success.");

			return config;
		}

	}

}
