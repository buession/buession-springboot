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
package com.buession.springboot.shiro.autoconfigure;

import com.buession.redis.RedisTemplate;
import com.buession.security.shiro.cache.DefaultRedisManager;
import com.buession.security.shiro.cache.RedisCacheManager;
import com.buession.security.shiro.cache.RedisManager;
import com.buession.security.shiro.cache.RedisSessionDAO;
import com.buession.springboot.cache.redis.autoconfigure.RedisConfiguration;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Yong.Teng
 */
@Configuration
@EnableConfigurationProperties(ShiroProperties.class)
@Import(RedisConfiguration.class)
@AutoConfigureAfter(RedisConfiguration.class)
@ConditionalOnBean(RedisTemplate.class)
public class CacheConfiguration {

	@Autowired
	private ShiroProperties shiroProperties;

	@Bean
	@ConditionalOnMissingBean
	public RedisManager shiroRedisManager(RedisTemplate redisTemplate){
		return new DefaultRedisManager(redisTemplate);
	}

	@Bean
	@ConditionalOnMissingBean
	public CacheManager cacheManager(RedisManager shiroRedisManager){
		ShiroProperties.Session session = shiroProperties.getSession();
		return new RedisCacheManager(shiroRedisManager, session.getPrefix(), session.getExpire());
	}

	@Bean
	@ConditionalOnMissingBean
	public SessionDAO sessionDAO(RedisManager shiroRedisManager){
		ShiroProperties.Session session = shiroProperties.getSession();
		return new RedisSessionDAO(shiroRedisManager, session.getPrefix(), session.getExpire(), session.getTimeout() *
				1000);
	}

}
