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

import com.buession.core.utils.StringUtils;
import com.buession.core.validator.Validate;
import com.buession.redis.RedisTemplate;
import com.buession.redis.client.connection.RedisConnection;
import com.buession.redis.core.Options;
import com.buession.redis.core.RedisURI;
import com.buession.redis.core.ShardedRedisNode;
import com.buession.redis.exception.RedisException;
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

import java.net.URISyntaxException;
import java.util.LinkedHashSet;
import java.util.Set;

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

		template.setOptions(createOptions());
		template.afterPropertiesSet();
		logger.info("RedisTemplate bean initialize success.");

		return template;
	}

	protected Options createOptions(){
		final Options options = new Options();

		options.setEnableTransactionSupport(redisConfigProperties.isEnableTransactionSupport());
		options.setPrefix(options.getPrefix());
		options.setSerializer(redisConfigProperties.getSerializer());

		return options;
	}

	@Configuration
	@ConditionalOnClass(name = {"redis.clients.jedis.Jedis"})
	public static class JedisConfiguration extends RedisConfiguration {

		@Bean
		@ConditionalOnMissingBean
		public RedisConnection jedisConnection() throws Exception{
			JedisRedisConnectionFactoryBean connectionFactory;

			switch(redisConfigProperties.getClusterMode()){
				case SHARDED:
					connectionFactory = createShardedJedisRedisConnectionFactory();
					break;
				default:
					connectionFactory = createJedisRedisConnectionFactory();
					break;
			}

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

			try{
				connectionFactory.afterPropertiesSet();
				logger.info("RedisConnection bean initialize success.");
			}catch(Exception e){
				logger.error("RedisConnection bean initialize failure.", e);
			}

			return connectionFactory.getObject();
		}

		protected JedisRedisConnectionFactoryBean createJedisRedisConnectionFactory(){
			return new JedisRedisConnectionFactoryBean(redisConfigProperties.getHost(),
					redisConfigProperties.getPort(), redisConfigProperties.getPassword(),
					redisConfigProperties.getDatabase(), redisConfigProperties.getClientName(), true,
					jedisPoolConfig(redisConfigProperties));
		}

		protected JedisRedisConnectionFactoryBean createShardedJedisRedisConnectionFactory(){
			try{
				return new JedisRedisConnectionFactoryBean(parseShardedRedisNode(redisConfigProperties.getNodes()));
			}catch(URISyntaxException e){
				throw new RedisException(e.getMessage());
			}catch(Exception e){
				throw e;
			}
		}

		protected final static Set<ShardedRedisNode> parseShardedRedisNode(final String url) throws URISyntaxException{
			if(Validate.hasText(url)){
				return null;
			}else{
				String[] parts = StringUtils.split(url, ';');
				RedisURI redisURI;
				ShardedRedisNode node;
				Set<ShardedRedisNode> nodes = new LinkedHashSet<>(parts.length);

				for(String part : parts){
					redisURI = RedisURI.create(part);
					node = new ShardedRedisNode(redisURI.getHost(), redisURI.getPort(), redisURI.getWeight(),
							redisURI.getPassword());
					node.setName(redisURI.getClientName());

					nodes.add(node);
				}

				return nodes;
			}
		}

		private final static redis.clients.jedis.JedisPoolConfig jedisPoolConfig(RedisConfigProperties redisConfigProperties){
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

			logger.info("JedisPoolConfig bean initialize success.");

			return config;
		}

	}

}
