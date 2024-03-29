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

import com.buession.redis.client.connection.datasource.ClusterDataSource;
import com.buession.redis.client.connection.datasource.DataSource;
import com.buession.redis.client.connection.datasource.StandaloneDataSource;
import com.buession.redis.client.connection.datasource.SentinelDataSource;
import com.buession.redis.client.connection.datasource.jedis.JedisClusterDataSource;
import com.buession.redis.client.connection.datasource.jedis.JedisDataSource;
import com.buession.redis.client.connection.datasource.jedis.JedisRedisDataSource;
import com.buession.redis.client.connection.datasource.jedis.JedisSentinelDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redis 数据源 {@link DataSource} 自动配置类
 *
 * @author Yong.Teng
 * @see DataSource
 * @see StandaloneDataSource
 * @see SentinelDataSource
 * @see ClusterDataSource
 * @see JedisRedisDataSource
 * @see JedisDataSource
 * @see JedisSentinelDataSource
 * @see JedisClusterDataSource
 * @since 1.3.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisDataSourceConfiguration {

	protected RedisProperties properties;

	public RedisDataSourceConfiguration(RedisProperties properties) {
		this.properties = properties;
	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(RedisProperties.class)
	@ConditionalOnClass({redis.clients.jedis.Jedis.class})
	@ConditionalOnMissingBean(name = "redisDataSource", value = DataSource.class)
	static class Jedis extends RedisDataSourceConfiguration {

		public Jedis(RedisProperties properties) {
			super(properties);
		}

		@Bean(name = "redisDataSource")
		public JedisDataSourceFactoryBean dataSource() {
			return new JedisDataSourceFactoryBean(properties);
		}

	}

}
