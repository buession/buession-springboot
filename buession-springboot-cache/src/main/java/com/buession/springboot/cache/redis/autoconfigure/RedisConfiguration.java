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
import com.buession.redis.spring.jedis.ShardedRedisConfiguration;
import com.buession.springboot.cache.redis.core.PoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
		@ConditionalOnProperty(prefix = "redis", name = "mode", havingValue = "sharded")
		public RedisConnection shardedJedisConnection() throws Exception{
			JedisRedisConnectionFactoryBean connectionFactory = createShardedJedisRedisConnectionFactory();
			connectionFactory.afterPropertiesSet();
			return connectionFactory.getObject();
		}

		@Bean
		@ConditionalOnMissingBean
		public RedisConnection jedisConnection() throws Exception{
			JedisRedisConnectionFactoryBean connectionFactory = createJedisRedisConnectionFactory();
			connectionFactory.afterPropertiesSet();
			return connectionFactory.getObject();
		}

		protected void setTimeout(final com.buession.redis.spring.RedisConfiguration configuration){
			configuration.setConnectTimeout(redisConfigProperties.getConnectTimeout());
			configuration.setSoTimeout(redisConfigProperties.getSoTimeout());
		}

		protected JedisRedisConnectionFactoryBean createJedisRedisConnectionFactory(){
			com.buession.redis.spring.jedis.JedisConfiguration configuration =
					new com.buession.redis.spring.jedis.JedisConfiguration();

			if(Validate.hasText(redisConfigProperties.getHost())){
				configuration.setHost(redisConfigProperties.getHost());
				configuration.setPort(redisConfigProperties.getPort());
				configuration.setPassword(redisConfigProperties.getPassword());
				configuration.setDatabase(redisConfigProperties.getDatabase());
				configuration.setClientName(redisConfigProperties.getClientName());
			}else{
				if(Validate.hasText(redisConfigProperties.getUri())){
					try{
						RedisURI redisURI = RedisURI.create(redisConfigProperties.getUri());

						configuration.setHost(redisURI.getHost());
						configuration.setPort(redisURI.getPort());
						configuration.setPassword(redisURI.getPassword());
						configuration.setDatabase(redisURI.getDatabase());
						configuration.setClientName(redisURI.getClientName());
					}catch(Exception e){
						throw e;
					}
				}

				return null;
			}

			setTimeout(configuration);

			return new JedisRedisConnectionFactoryBean(configuration, jedisPoolConfig(redisConfigProperties));
		}

		protected JedisRedisConnectionFactoryBean createShardedJedisRedisConnectionFactory(){
			ShardedRedisConfiguration configuration = new ShardedRedisConfiguration();

			setTimeout(configuration);

			try{
				configuration.setNodes(parseShardedRedisNode(redisConfigProperties.getNodes()));

				return new JedisRedisConnectionFactoryBean(configuration, jedisPoolConfig(redisConfigProperties));
			}catch(URISyntaxException e){
				throw new RedisException(e.getMessage());
			}catch(Exception e){
				throw e;
			}
		}

		protected final static Set<ShardedRedisNode> parseShardedRedisNode(final String url) throws URISyntaxException{
			if(Validate.hasText(url)){
				return null;
			}

			String[] parts = StringUtils.splitByWholeSeparatorPreserveAllTokens(url, ";");
			Set<ShardedRedisNode> nodes = new LinkedHashSet<>(parts.length);
			RedisURI redisURI;

			for(String part : parts){
				redisURI = RedisURI.create(part);
				nodes.add(new ShardedRedisNode(redisURI.getHost(), redisURI.getPort(), redisURI.getPassword(),
						redisURI.getDatabase(), redisURI.getClientName(), redisURI.getWeight()));
			}

			return nodes;
		}

		private final static redis.clients.jedis.JedisPoolConfig jedisPoolConfig(RedisConfigProperties redisConfigProperties){
			PoolConfig poolConfig = redisConfigProperties.getPool();

			if(poolConfig == null){
				return null;
			}

			JedisPoolConfig config = new JedisPoolConfig();

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

			logger.info("JedisPoolConfig bean initialize success.");

			return config;
		}

	}

}
