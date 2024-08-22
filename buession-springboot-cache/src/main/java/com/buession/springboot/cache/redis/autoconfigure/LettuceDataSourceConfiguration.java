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

import com.buession.redis.client.connection.datasource.ClusterDataSource;
import com.buession.redis.client.connection.datasource.DataSource;
import com.buession.redis.client.connection.datasource.SentinelDataSource;
import com.buession.redis.client.connection.datasource.StandaloneDataSource;
import com.buession.redis.client.connection.datasource.lettuce.LettuceClusterDataSource;
import com.buession.redis.client.connection.datasource.lettuce.LettuceDataSource;
import com.buession.redis.client.connection.datasource.lettuce.LettuceRedisDataSource;
import com.buession.redis.client.connection.datasource.lettuce.LettuceSentinelDataSource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Redis 数据源 {@link DataSource} 自动配置类
 *
 * @author Yong.Teng
 * @see DataSource
 * @see StandaloneDataSource
 * @see SentinelDataSource
 * @see ClusterDataSource
 * @see LettuceRedisDataSource
 * @see LettuceDataSource
 * @see LettuceSentinelDataSource
 * @see LettuceClusterDataSource
 * @since 3.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(RedisProperties.class)
@ConditionalOnClass(name = "io.lettuce.core.AbstractRedisClient")
@ConditionalOnMissingBean(name = "redisDataSource", value = DataSource.class)
public class LettuceDataSourceConfiguration extends AbstractDataSourceConfiguration {

	public LettuceDataSourceConfiguration(RedisProperties properties) {
		super(properties);
	}

	@Bean(name = "redisDataSource")
	@Override
	public DataSource dataSource() {
		return super.dataSource();
	}

	@Override
	protected StandaloneDataSource createStandaloneDataSource() {
		return createStandaloneDataSource(new LettuceDataSource());
	}

	@Override
	protected SentinelDataSource createSentinelDataSource() {
		return createSentinelDataSource(new LettuceSentinelDataSource());
	}

	@Override
	protected ClusterDataSource createClusterDataSource() {
		return createClusterDataSource(new LettuceClusterDataSource());
	}

}
