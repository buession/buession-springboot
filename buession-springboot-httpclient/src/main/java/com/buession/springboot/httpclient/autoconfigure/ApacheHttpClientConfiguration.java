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
package com.buession.springboot.httpclient.autoconfigure;

import com.buession.httpclient.ApacheHttpAsyncClient;
import com.buession.httpclient.apache.ApacheClientConnectionManager;
import com.buession.httpclient.apache.ApacheNioClientConnectionManager;
import com.buession.httpclient.conn.Apache5ClientConnectionManager;
import com.buession.httpclient.conn.Apache5NioClientConnectionManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Apache HttpClient Auto Configuration
 *
 * @author Yong.Teng
 * @since 2.3.0
 */
@AutoConfiguration
@EnableConfigurationProperties(HttpClientProperties.class)
public class ApacheHttpClientConfiguration extends AbstractHttpClientConfiguration {

	public ApacheHttpClientConfiguration(HttpClientProperties properties) {
		super(properties);
	}

	@AutoConfiguration
	@EnableConfigurationProperties(HttpClientProperties.class)
	@ConditionalOnMissingBean(name = HTTP_CLIENT_BEAN_NAME, value = com.buession.httpclient.HttpClient.class)
	@ConditionalOnProperty(prefix = HttpClientProperties.PREFIX, name = "apache-client.enabled", havingValue = "true", matchIfMissing = true)
	static class ApacheHttpClient extends ApacheHttpClientConfiguration {

		public ApacheHttpClient(HttpClientProperties properties) {
			super(properties);
		}

		/**
		 * Apache 5 同步连接管理器
		 *
		 * @return Apache 5 同步连接管理器
		 */
		@Bean(name = CLIENT_CONNECTION_MANAGER_BEAN_NAME)
		@ConditionalOnClass(name = {"org.apache.hc.client5.http.classic.HttpClient"})
		@ConditionalOnMissingBean(name = {CLIENT_CONNECTION_MANAGER_BEAN_NAME})
		public ApacheClientConnectionManager apache5ClientConnectionManager() {
			return new Apache5ClientConnectionManager(properties);
		}

		/**
		 * Apache 4 同步连接管理器
		 *
		 * @return Apache 4 同步连接管理器
		 */
		@Bean(name = CLIENT_CONNECTION_MANAGER_BEAN_NAME)
		@ConditionalOnClass(name = {"org.apache.http.client.HttpClient"})
		@ConditionalOnMissingBean(name = {CLIENT_CONNECTION_MANAGER_BEAN_NAME})
		public ApacheClientConnectionManager apacheClientConnectionManager() {
			return new com.buession.httpclient.conn.ApacheClientConnectionManager(properties);
		}

		@Bean(name = HTTP_CLIENT_BEAN_NAME)
		@ConditionalOnBean(name = {CLIENT_CONNECTION_MANAGER_BEAN_NAME})
		public com.buession.httpclient.ApacheHttpClient httpClient(
				@Qualifier(CLIENT_CONNECTION_MANAGER_BEAN_NAME) ApacheClientConnectionManager clientConnectionManager) {
			return new com.buession.httpclient.ApacheHttpClient(clientConnectionManager);
		}

	}

	@AutoConfiguration
	@EnableConfigurationProperties(HttpClientProperties.class)
	@ConditionalOnMissingBean(name = HTTP_CLIENT_BEAN_NAME, value = com.buession.httpclient.HttpAsyncClient.class)
	@ConditionalOnProperty(prefix = HttpClientProperties.PREFIX, name = "apache-client.async.enabled", havingValue =
			"true", matchIfMissing = true)
	static class AsyncApacheHttpClient extends ApacheHttpClientConfiguration {

		public AsyncApacheHttpClient(HttpClientProperties properties) {
			super(properties);
		}

		/**
		 * Apache 5 异步连接管理器
		 *
		 * @return Apache 5 异步连接管理器
		 */
		@Bean(name = NIO_CLIENT_CONNECTION_MANAGER_BEAN_NAME)
		@ConditionalOnClass(name = {"org.apache.hc.client5.http.async.HttpAsyncClient"})
		@ConditionalOnMissingBean(name = {NIO_CLIENT_CONNECTION_MANAGER_BEAN_NAME})
		public ApacheNioClientConnectionManager apache5NioClientConnectionManager() {
			final Apache5NioClientConnectionManager clientConnectionManager =
					new Apache5NioClientConnectionManager(properties);

			if(properties.getApacheClient() != null){
				propertyMapper.from(properties.getApacheClient()::getIoReactor)
						.to(clientConnectionManager::setIoReactorConfig);
				propertyMapper.from(properties.getApacheClient()::getThreadFactory)
						.as(BeanUtils::instantiateClass)
						.to(clientConnectionManager::setThreadFactory);
			}

			return clientConnectionManager;
		}

		/**
		 * Apache 4 异步连接管理器
		 *
		 * @return Apache 4 异步连接管理器
		 */
		@Bean(name = NIO_CLIENT_CONNECTION_MANAGER_BEAN_NAME)
		@ConditionalOnClass(name = {"org.apache.http.nio.client.HttpAsyncClient"})
		@ConditionalOnMissingBean(name = {NIO_CLIENT_CONNECTION_MANAGER_BEAN_NAME})
		public ApacheNioClientConnectionManager apacheNioClientConnectionManager() {
			final com.buession.httpclient.conn.ApacheNioClientConnectionManager clientConnectionManager =
					new com.buession.httpclient.conn.ApacheNioClientConnectionManager(properties);

			if(properties.getApacheClient() != null){
				propertyMapper.from(properties.getApacheClient()::getIoReactor)
						.to(clientConnectionManager::setIoReactorConfig);
				propertyMapper.from(properties.getApacheClient()::getThreadFactory)
						.as(BeanUtils::instantiateClass)
						.to(clientConnectionManager::setThreadFactory);
			}

			return clientConnectionManager;
		}

		@Bean(name = ASYNC_HTTP_CLIENT_BEAN_NAME)
		@ConditionalOnBean(name = {NIO_CLIENT_CONNECTION_MANAGER_BEAN_NAME})
		public ApacheHttpAsyncClient httpAsyncClient(
				@Qualifier(NIO_CLIENT_CONNECTION_MANAGER_BEAN_NAME) ApacheNioClientConnectionManager clientConnectionManager) {
			return new ApacheHttpAsyncClient(clientConnectionManager);
		}

	}

}
