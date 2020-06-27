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

import com.buession.redis.Constants;
import com.buession.redis.core.ClusterMode;
import com.buession.redis.serializer.Serializer;
import com.buession.springboot.cache.redis.core.PoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author Yong.Teng
 */
@ConfigurationProperties(prefix = "redis")
public class RedisConfigProperties {

	private String host;

	private int port;

	private String password;

	private int database;

	private String clientName;

	private ClusterMode clusterMode;

	private String nodes;

	@Deprecated
	private int timeout;

	private int connectTimeout = Constants.DEFAULT_CONNECT_TIMEOUT;

	private int soTimeout = Constants.DEFAULT_SO_TIMEOUT;

	private boolean useSsl;

	private int weight;

	private String keyPrefix;

	private Serializer serializer;

	private boolean enableTransactionSupport;

	@NestedConfigurationProperty
	private PoolConfig pool = new PoolConfig();

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

	public ClusterMode getClusterMode(){
		return clusterMode;
	}

	public void setClusterMode(ClusterMode clusterMode){
		this.clusterMode = clusterMode;
	}

	public String getNodes(){
		return nodes;
	}

	public void setNodes(String nodes){
		this.nodes = nodes;
	}

	@Deprecated
	public int getTimeout(){
		return timeout;
	}

	@Deprecated
	public void setTimeout(int timeout){
		this.timeout = timeout;
		setConnectTimeout(timeout);
		setSoTimeout(timeout);
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

	public boolean isUseSsl(){
		return useSsl;
	}

	public void setUseSsl(boolean useSsl){
		this.useSsl = useSsl;
	}

	public int getWeight(){
		return weight;
	}

	public void setWeight(int weight){
		this.weight = weight;
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

}
