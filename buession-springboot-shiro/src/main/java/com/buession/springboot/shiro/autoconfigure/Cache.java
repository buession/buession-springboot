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
package com.buession.springboot.shiro.autoconfigure;

import com.buession.security.shiro.cache.CacheManager;
import com.buession.security.shiro.serializer.RedisSerializer;

/**
 * Shiro 缓存配置
 *
 * @author Yong.Teng
 * @since 1.2.2
 */
class Cache {

	/**
	 * 缓存 Key 前缀
	 */
	private String prefix = CacheManager.DEFAULT_KEY_PREFIX;

	/**
	 * 缓存过期时间
	 */
	private int expire = CacheManager.DEFAULT_EXPIRE;

	/**
	 * Principal Id
	 */
	private String principalIdFieldName = CacheManager.DEFAULT_PRINCIPAL_ID_FIELD_NAME;

	public String getPrefix(){
		return prefix;
	}

	public void setPrefix(String prefix){
		this.prefix = prefix;
	}

	public int getExpire(){
		return expire;
	}

	public void setExpire(int expire){
		this.expire = expire;
	}

	public String getPrincipalIdFieldName(){
		return principalIdFieldName;
	}

	public void setPrincipalIdFieldName(String principalIdFieldName){
		this.principalIdFieldName = principalIdFieldName;
	}

}
