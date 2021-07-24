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

import com.buession.redis.core.Constants;
import com.buession.redis.serializer.Serializer;
import com.buession.springboot.cache.redis.core.PoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author Yong.Teng
 */
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {

	/**
	 * Redis URI
	 */
	private String uri;

	/**
	 * Redis 主机地址
	 */
	private String host;

	/**
	 * Redis 端口
	 */
	private int port;

	/**
	 * Redis 密码
	 */
	private String password;

	/**
	 * 数据库
	 */
	private int database;

	/**
	 * 客户端名称
	 */
	private String clientName;

	/**
	 * Redis 端口
	 */
	private String nodes;

	/**
	 * 连接超时
	 */
	private int connectTimeout = Constants.DEFAULT_CONNECT_TIMEOUT;

	/**
	 * 读取超时
	 */
	private int soTimeout = Constants.DEFAULT_SO_TIMEOUT;

	/**
	 * Key 前缀
	 */
	private String keyPrefix;

	/**
	 * 序列化方式
	 */
	private Serializer serializer;

	/**
	 * 是否开启事务
	 */
	private boolean enableTransactionSupport;

	/**
	 * 连接池
	 */
	@NestedConfigurationProperty
	private PoolConfig pool = new PoolConfig();

	public String getUri(){
		return uri;
	}

	public void setUri(String uri){
		this.uri = uri;
	}

	public String getHost(){
		return host;
	}

	public void setHost(String host){
		this.host = host;
	}

	public int getPort(){
		return port;
	}

	public void setPort(int port){
		this.port = port;
	}

	public String getPassword(){
		return password;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public int getDatabase(){
		return database;
	}

	public void setDatabase(int database){
		this.database = database;
	}

	public String getClientName(){
		return clientName;
	}

	public void setClientName(String clientName){
		this.clientName = clientName;
	}

	public String getNodes(){
		return nodes;
	}

	public void setNodes(String nodes){
		this.nodes = nodes;
	}

	public int getConnectTimeout(){
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout){
		this.connectTimeout = connectTimeout;
	}

	public int getSoTimeout(){
		return soTimeout;
	}

	public void setSoTimeout(int soTimeout){
		this.soTimeout = soTimeout;
	}

	public String getKeyPrefix(){
		return keyPrefix;
	}

	public void setKeyPrefix(String keyPrefix){
		this.keyPrefix = keyPrefix;
	}

	public Serializer getSerializer(){
		return serializer;
	}

	public void setSerializer(Serializer serializer){
		this.serializer = serializer;
	}

	public boolean isEnableTransactionSupport(){
		return getEnableTransactionSupport();
	}

	public boolean getEnableTransactionSupport(){
		return enableTransactionSupport;
	}

	public void setEnableTransactionSupport(boolean enableTransactionSupport){
		this.enableTransactionSupport = enableTransactionSupport;
	}

	public PoolConfig getPool(){
		return pool;
	}

	public void setPool(PoolConfig pool){
		this.pool = pool;
	}

	@ConfigurationProperties(prefix = "redis")
	@Deprecated
	public final static class DeprecatedRedisProperties extends RedisProperties {

		/**
		 * Redis URI
		 */
		@Deprecated
		private String uri;

		/**
		 * Redis 主机地址
		 */
		@Deprecated
		private String host;

		/**
		 * Redis 端口
		 */
		@Deprecated
		private int port;

		/**
		 * Redis 密码
		 */
		@Deprecated
		private String password;

		/**
		 * 数据库
		 */
		@Deprecated
		private int database;

		/**
		 * 客户端名称
		 */
		@Deprecated
		private String clientName;

		/**
		 * Redis 端口
		 */
		@Deprecated
		private String nodes;

		/**
		 * 超时时间，同时设置连接超时和读取超时
		 */
		@Deprecated
		private int timeout;

		/**
		 * 连接超时
		 */
		@Deprecated
		private int connectTimeout = Constants.DEFAULT_CONNECT_TIMEOUT;

		/**
		 * 读取超时
		 */
		@Deprecated
		private int soTimeout = Constants.DEFAULT_SO_TIMEOUT;

		/**
		 * Key 前缀
		 */
		@Deprecated
		private String keyPrefix;

		/**
		 * 序列化方式
		 */
		@Deprecated
		private Serializer serializer;

		/**
		 * 是否开启事务
		 */
		@Deprecated
		private boolean enableTransactionSupport;

		/**
		 * 连接池
		 */
		@NestedConfigurationProperty
		@Deprecated
		private PoolConfig pool = new PoolConfig();

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.redis.uri")
		@Override
		public void setUri(String uri){
			super.setUri(uri);
			this.uri = uri;
		}

		@Override
		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.redis.host")
		public void setHost(String host){
			super.setHost(host);
			this.host = host;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.redis.port")
		@Override
		public void setPort(int port){
			super.setPort(port);
			this.port = port;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.redis.password")
		@Override
		public void setPassword(String password){
			super.setPassword(password);
			this.password = password;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.redis.password")
		@Override
		public void setDatabase(int database){
			super.setDatabase(database);
			this.database = database;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.redis.client-name")
		@Override
		public void setClientName(String clientName){
			super.setClientName(clientName);
			this.clientName = clientName;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.redis.nodes")
		@Override
		public void setNodes(String nodes){
			super.setNodes(nodes);
			this.nodes = nodes;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "分别设置连接超时和读取超时", replacement =
				"spring.redis.connect-timeout and " + "spring.redis.so-timeout")
		public int getTimeout(){
			return timeout;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "分别设置连接超时和读取超时", replacement =
				"spring.redis.connect-timeout and " + "spring.redis.so-timeout")
		public void setTimeout(int timeout){
			this.timeout = timeout;
			setConnectTimeout(timeout);
			setSoTimeout(timeout);
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.redis.connect-timeout")
		@Override
		public void setConnectTimeout(int connectTimeout){
			super.setConnectTimeout(connectTimeout);
			this.connectTimeout = connectTimeout;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.redis.so-timeout")
		@Override
		public void setSoTimeout(int soTimeout){
			super.setSoTimeout(soTimeout);
			this.soTimeout = soTimeout;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.redis.key-prefix")
		@Override
		public void setKeyPrefix(String keyPrefix){
			super.setKeyPrefix(keyPrefix);
			this.keyPrefix = keyPrefix;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.redis.serializer")
		@Override
		public void setSerializer(Serializer serializer){
			super.setSerializer(serializer);
			this.serializer = serializer;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.redis.enable-transaction-support")
		@Override
		public void setEnableTransactionSupport(boolean enableTransactionSupport){
			super.setEnableTransactionSupport(enableTransactionSupport);
			this.enableTransactionSupport = enableTransactionSupport;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.redis.pool")
		@Override
		public void setPool(PoolConfig pool){
			super.setPool(pool);
			this.pool = pool;
		}

	}

}
