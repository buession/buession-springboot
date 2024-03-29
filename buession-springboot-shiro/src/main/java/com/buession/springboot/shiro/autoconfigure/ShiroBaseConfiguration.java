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
package com.buession.springboot.shiro.autoconfigure;

import com.buession.redis.RedisTemplate;
import com.buession.security.shiro.DefaultRedisManager;
import com.buession.security.shiro.RedisManager;
import com.buession.security.shiro.cache.RedisCacheManager;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yong.Teng
 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ShiroProperties.class)
public class ShiroBaseConfiguration {

	private final ShiroProperties properties;

	public ShiroBaseConfiguration(ShiroProperties properties){
		this.properties = properties;
	}

	@Bean
	@ConditionalOnBean(RedisTemplate.class)
	@ConditionalOnMissingBean
	public RedisManager redisManager(ObjectProvider<RedisTemplate> redisTemplate){
		return new DefaultRedisManager(redisTemplate.getIfAvailable());
	}

	@Bean
	@ConditionalOnBean(RedisManager.class)
	@ConditionalOnMissingBean
	public CacheManager cacheManager(ObjectProvider<RedisManager> redisManager){
		ShiroProperties.Cache cache = properties.getCache();
		return new RedisCacheManager(redisManager.getIfAvailable(), cache.getPrefix(), cache.getExpire(),
				cache.getPrincipalIdFieldName());
	}

}
