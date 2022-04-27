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
 * | Copyright @ 2013-2022 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.cache.redis.autoconfigure;

import com.buession.redis.core.Constants;
import com.buession.redis.serializer.Serializer;
import com.buession.springboot.cache.redis.core.PoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.time.Duration;
import java.util.Set;

/**
 * Redis Properties，当配置集群参数、哨兵参数、单机版参数，优先级依次：集群 &gt; 哨兵 &gt; 单机
 *
 * @author Yong.Teng
 */
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {

	/**
	 * Redis URI，优先使用 host 设置的 redis 主机地址进行连接；host 的值为空，再使用该参数配置的 redis 主机地址端口、数据库信息进行连接
	 */
	private String uri;

	/**
	 * Redis 主机地址，配置了此值，将使用该值作为 redis 的的主机地址进行连接
	 */
	private String host;

	/**
	 * Redis 端口
	 */
	private int port;

	/**
	 * Redis 用户名
	 */
	private String username;

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
	 * 连接超时
	 */
	private Duration connectTimeout = Duration.ofMillis(Constants.DEFAULT_CONNECT_TIMEOUT);

	/**
	 * 读取超时
	 */
	private Duration soTimeout = Duration.ofMillis(Constants.DEFAULT_SO_TIMEOUT);

	/**
	 * Infinite 读取超时
	 *
	 * @since 2.0.0
	 */
	private Duration infiniteSoTimeout = Duration.ofMillis(Constants.DEFAULT_INFINITE_SO_TIMEOUT);

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
	 * Sentinel 模式配置
	 */
	private Sentinel sentinel;

	/**
	 * 集群模式配置
	 */
	private Cluster cluster;

	/**
	 * 连接池配置
	 */
	@NestedConfigurationProperty
	private PoolConfig pool = new PoolConfig();

	/**
	 * 返回 Redis URI
	 *
	 * @return 返回 Redis URI
	 */
	public String getUri(){
		return uri;
	}

	/**
	 * 设置 Redis URI，格式：
	 * 1) redis://[[username]@password]:127.0.0.1:6379
	 * 2) redis://[[username]@password]:127.0.0.1:6379[?database=1&clientName=client_name&timeout=30]
	 * 3) redis://[[username]@password]:127.0.0.1:6379[?db=1&clientName=client_name&timeout=30]
	 *
	 * @param uri
	 * 		Redis URI
	 */
	public void setUri(String uri){
		this.uri = uri;
	}

	/**
	 * 返回 Redis 主机地址
	 *
	 * @return Redis 主机地址
	 */
	public String getHost(){
		return host;
	}

	/**
	 * 设置 Redis 主机地址
	 *
	 * @param host
	 * 		Redis 主机地址
	 */
	public void setHost(String host){
		this.host = host;
	}

	/**
	 * 返回 Redis 端口
	 *
	 * @return Redis 端口
	 */
	public int getPort(){
		return port;
	}

	/**
	 * 设置 Redis 端口
	 *
	 * @param port
	 * 		Redis 端口
	 */
	public void setPort(int port){
		this.port = port;
	}

	/**
	 * 返回 Redis 用户名
	 *
	 * @return Redis 用户名
	 */
	public String getUsername(){
		return username;
	}

	/**
	 * 设置 Redis 用户名
	 *
	 * @param username
	 * 		Redis 用户名
	 */
	public void setUsername(String username){
		this.username = username;
	}

	/**
	 * 返回 Redis 密码
	 *
	 * @return Redis 密码
	 */
	public String getPassword(){
		return password;
	}

	/**
	 * 设置 Redis 密码
	 *
	 * @param password
	 * 		Redis 密码
	 */
	public void setPassword(String password){
		this.password = password;
	}

	/**
	 * 返回数据库
	 *
	 * @return 数据库
	 */
	public int getDatabase(){
		return database;
	}

	/**
	 * 设置数据库
	 *
	 * @param database
	 * 		数据库
	 */
	public void setDatabase(int database){
		this.database = database;
	}

	/**
	 * 返回客户端名称
	 *
	 * @return 客户端名称
	 */
	public String getClientName(){
		return clientName;
	}

	/**
	 * 设置客户端名称
	 *
	 * @param clientName
	 * 		客户端名称
	 */
	public void setClientName(String clientName){
		this.clientName = clientName;
	}

	/**
	 * 返回连接超时
	 *
	 * @return 连接超时
	 */
	public Duration getConnectTimeout(){
		return connectTimeout;
	}

	/**
	 * 设置连接超时
	 *
	 * @param connectTimeout
	 * 		连接超时
	 */
	public void setConnectTimeout(Duration connectTimeout){
		this.connectTimeout = connectTimeout;
	}

	/**
	 * 返回读取超时
	 *
	 * @return 读取超时
	 */
	public Duration getSoTimeout(){
		return soTimeout;
	}

	/**
	 * 设置读取超时
	 *
	 * @param soTimeout
	 * 		读取超时
	 */
	public void setSoTimeout(Duration soTimeout){
		this.soTimeout = soTimeout;
	}

	/**
	 * 返回 Infinite 读取超时
	 *
	 * @return Infinite 读取超时
	 *
	 * @since 2.0.0
	 */
	public Duration getInfiniteSoTimeout(){
		return infiniteSoTimeout;
	}

	/**
	 * 设置 Infinite 读取超时
	 *
	 * @param infiniteSoTimeout
	 * 		Infinite 读取超时
	 *
	 * @since 2.0.0
	 */
	public void setInfiniteSoTimeout(Duration infiniteSoTimeout){
		this.infiniteSoTimeout = infiniteSoTimeout;
	}

	/**
	 * 返回 Key 前缀
	 *
	 * @return Key 前缀
	 */
	public String getKeyPrefix(){
		return keyPrefix;
	}

	/**
	 * 设置 Key 前缀
	 *
	 * @param keyPrefix
	 * 		Key 前缀
	 */
	public void setKeyPrefix(String keyPrefix){
		this.keyPrefix = keyPrefix;
	}

	/**
	 * 返回序列化方式
	 *
	 * @return 序列化方式
	 */
	public Serializer getSerializer(){
		return serializer;
	}

	/**
	 * 设置序列化方式
	 *
	 * @param serializer
	 * 		序列化方式
	 */
	public void setSerializer(Serializer serializer){
		this.serializer = serializer;
	}

	/**
	 * 返回是否开启事务
	 *
	 * @return 是否开启事务
	 */
	public boolean isEnableTransactionSupport(){
		return getEnableTransactionSupport();
	}

	/**
	 * 返回是否开启事务
	 *
	 * @return 是否开启事务
	 */
	public boolean getEnableTransactionSupport(){
		return enableTransactionSupport;
	}

	/**
	 * 事务开关
	 *
	 * @param enableTransactionSupport
	 * 		是否开启事务
	 */
	public void setEnableTransactionSupport(boolean enableTransactionSupport){
		this.enableTransactionSupport = enableTransactionSupport;
	}

	/**
	 * 返回哨兵模式配置
	 *
	 * @return 哨兵模式配置
	 */
	public Sentinel getSentinel(){
		return sentinel;
	}

	/**
	 * 设置哨兵模式配置
	 *
	 * @param sentinel
	 * 		哨兵模式配置
	 */
	public void setSentinel(Sentinel sentinel){
		this.sentinel = sentinel;
	}

	/**
	 * 返回集群模式配置
	 *
	 * @return 集群模式配置
	 */
	public Cluster getCluster(){
		return cluster;
	}

	/**
	 * 设置集群模式配置
	 *
	 * @param cluster
	 * 		集群模式配置
	 */
	public void setCluster(Cluster cluster){
		this.cluster = cluster;
	}

	/**
	 * 返回连接池配置
	 *
	 * @return 连接池配置
	 */
	public PoolConfig getPool(){
		return pool;
	}

	/**
	 * 设置连接池配置
	 *
	 * @param pool
	 * 		连接池配置
	 */
	public void setPool(PoolConfig pool){
		this.pool = pool;
	}

	/**
	 * Redis sentinel properties
	 *
	 * @author yong.teng
	 * @since 2.0.0
	 */
	public static class Sentinel {

		/**
		 * Master 名称
		 */
		private String masterName;

		/**
		 * "host:port" 格式的哨兵节点列表
		 */
		private Set<String> nodes;

		/**
		 * 连接超时
		 */
		private Duration connectTimeout = Duration.ofMillis(Constants.DEFAULT_CONNECT_TIMEOUT);

		/**
		 * 读取超时
		 */
		private Duration soTimeout = Duration.ofMillis(Constants.DEFAULT_SO_TIMEOUT);

		/**
		 * Sentinel 客户端名称
		 */
		private String clientName;

		/**
		 * 返回 Master 名称
		 *
		 * @return Master 名称
		 */
		public String getMasterName(){
			return this.masterName;
		}

		/**
		 * 设置 Master 名称
		 *
		 * @param masterName
		 * 		Master 名称
		 */
		public void setMasterName(String masterName){
			this.masterName = masterName;
		}

		/**
		 * 返回 "host:port" 格式的哨兵节点列表
		 *
		 * @return "host:port" 格式的哨兵节点列表
		 */
		public Set<String> getNodes(){
			return this.nodes;
		}

		/**
		 * 设置 "host:port" 格式的哨兵节点列表
		 *
		 * @param nodes
		 * 		"host:port" 格式的哨兵节点列表
		 */
		public void setNodes(Set<String> nodes){
			this.nodes = nodes;
		}

		/**
		 * 返回连接超时
		 *
		 * @return 连接超时
		 */
		public Duration getConnectTimeout(){
			return connectTimeout;
		}

		/**
		 * 设置连接超时
		 *
		 * @param connectTimeout
		 * 		连接超时
		 */
		public void setConnectTimeout(Duration connectTimeout){
			this.connectTimeout = connectTimeout;
		}

		/**
		 * 返回读取超时
		 *
		 * @return 读取超时
		 */
		public Duration getSoTimeout(){
			return soTimeout;
		}

		/**
		 * 设置读取超时
		 *
		 * @param soTimeout
		 * 		读取超时
		 */
		public void setSoTimeout(Duration soTimeout){
			this.soTimeout = soTimeout;
		}

		/**
		 * 返回 Sentinel 客户端名称
		 *
		 * @return Sentinel 客户端名称
		 */
		public String getClientName(){
			return clientName;
		}

		/**
		 * 设置 Sentinel 客户端名称
		 *
		 * @param clientName
		 * 		Sentinel 客户端名称
		 */
		public void setClientName(String clientName){
			this.clientName = clientName;
		}

	}

	/**
	 * Redis Cluster properties
	 *
	 * @author yong.teng
	 * @since 2.0.0
	 */
	public final static class Cluster {

		/**
		 * "host:port" 格式的集群节点列表
		 */
		private Set<String> nodes;

		/**
		 * 最大重试次数
		 */
		private Integer maxRedirects;

		/**
		 * 最大重数时长
		 */
		private Duration maxTotalRetriesDuration;

		/**
		 * 返回 "host:port" 格式的集群节点列表
		 *
		 * @return "host:port" 格式的集群节点列表
		 */
		public Set<String> getNodes(){
			return nodes;
		}

		/**
		 * 设置 "host:port" 格式的集群节点列表
		 *
		 * @param nodes
		 * 		"host:port" 格式的集群节点列表
		 */
		public void setNodes(Set<String> nodes){
			this.nodes = nodes;
		}

		/**
		 * 返回最大重试次数
		 *
		 * @return 最大重试次数
		 */
		public Integer getMaxRedirects(){
			return maxRedirects;
		}

		/**
		 * 设置最大重试次数
		 *
		 * @param maxRedirects
		 * 		最大重试次数
		 */
		public void setMaxRedirects(Integer maxRedirects){
			this.maxRedirects = maxRedirects;
		}

		/**
		 * 返回最大重试持续时长
		 *
		 * @return 最大重试持续时长
		 */
		public Duration getMaxTotalRetriesDuration(){
			return maxTotalRetriesDuration;
		}

		/**
		 * 设置最大重试持续时长
		 *
		 * @param maxTotalRetriesDuration
		 * 		最大重试持续时长
		 */
		public void setMaxTotalRetriesDuration(Duration maxTotalRetriesDuration){
			this.maxTotalRetriesDuration = maxTotalRetriesDuration;
		}

	}

}
