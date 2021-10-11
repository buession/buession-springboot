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

import com.buession.redis.RedisTemplate;
import com.buession.redis.client.connection.RedisConnection;
import com.buession.redis.core.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Yong.Teng
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(RedisProperties.class)
@ConditionalOnClass({RedisTemplate.class})
@Import({JedisConnectionConfiguration.class})
public class RedisConfiguration {

	@Autowired
	protected RedisProperties redisProperties;

	private final static Logger logger = LoggerFactory.getLogger(RedisConfiguration.class);

	@Bean
	@ConditionalOnBean(RedisConnection.class)
	@ConditionalOnMissingBean
	public RedisTemplate redisTemplate(RedisConnection redisConnection){
		RedisTemplate template = new RedisTemplate(redisConnection);

		template.setOptions(createOptions());
		template.afterPropertiesSet();

		if(logger.isTraceEnabled()){
			logger.trace("RedisTemplate bean initialize success.");
		}

		return template;
	}

	protected Options createOptions(){
		final Options options = new Options();

		options.setPrefix(redisProperties.getKeyPrefix());
		options.setSerializer(redisProperties.getSerializer());
		options.setEnableTransactionSupport(redisProperties.isEnableTransactionSupport());

		return options;
	}

}
