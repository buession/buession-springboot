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
package com.buession.springboot.cache.redis.core;

import com.buession.aop.ProxyMethodInvoker;
import com.buession.core.utils.Assert;
import com.buession.redis.RedisTemplate;
import com.buession.springboot.cache.aop.AbstractCacheInvocationHandler;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Yong.Teng
 * @since 3.0.0
 */
public class RedisCacheInvocationHandler extends AbstractCacheInvocationHandler {

	private final RedisTemplate redisTemplate;

	private final Map<Method, ProxyMethodInvoker<RedisTemplate>> methodCache = new ConcurrentHashMap<>();

	public RedisCacheInvocationHandler(final RedisTemplate redisTemplate, final Class<?>[] interfaces) {
		Assert.isNull(redisTemplate, "RedisTemplate cloud not be null");
		this.redisTemplate = redisTemplate;
	}

	@Override
	protected Object handleInvocation(final Object proxy, final Method method, final Object[] args) throws Throwable {
		return cachedInvoker(method).invoke(proxy, method, args, redisTemplate);
	}

	private ProxyMethodInvoker<RedisTemplate> cachedInvoker(final Method method) throws Throwable {
		ProxyMethodInvoker<RedisTemplate> methodInvoker = methodCache.get(method);

		if(methodInvoker != null){
			return methodInvoker;
		}

		try{
			methodInvoker = createMethodInvoker(method);
			methodCache.put(method, methodInvoker);

			return methodInvoker;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw Optional.ofNullable(e.getCause()).orElse(e);
		}
	}

}
