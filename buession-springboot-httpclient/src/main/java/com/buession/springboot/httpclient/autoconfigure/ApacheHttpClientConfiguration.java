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
import com.buession.httpclient.ApacheHttpClient;
import com.buession.httpclient.conn.Apache5ClientConnectionManager;
import com.buession.httpclient.conn.Apache5NioClientConnectionManager;
import com.buession.httpclient.conn.ApacheClientConnectionManager;
import com.buession.httpclient.conn.ApacheNioClientConnectionManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

/**
 * Apache HttpClient Auto Configuration
 *
 * @author Yong.Teng
 * @since 2.3.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(HttpClientProperties.class)
public class ApacheHttpClientConfiguration extends AbstractHttpClientConfiguration {

	public ApacheHttpClientConfiguration(HttpClientProperties properties) {
		super(properties);
	}

	/**
	 * Apache 5 同步 HttpClient Auto Configuration
	 */
	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(HttpClientProperties.class)
	@ConditionalOnClass(name = {"org.apache.hc.client5.http.classic.HttpClient"})
	@ConditionalOnMissingBean(name = HTTP_CLIENT_BEAN_NAME, value = com.buession.httpclient.HttpClient.class)
	@ConditionalOnProperty(prefix = HttpClientProperties.PREFIX, name = "apache-client.enabled", havingValue = "true", matchIfMissing = true)
	static class Apache5HttpClient extends ApacheHttpClientConfiguration {

		public Apache5HttpClient(HttpClientProperties properties) {
			super(properties);
		}

		@Bean(name = CLIENT_CONNECTION_MANAGER_BEAN_NAME)
		@ConditionalOnMissingBean
		public Apache5ClientConnectionManager apache4ClientConnectionManager() {
			return new Apache5ClientConnectionManager(properties);
		}

		@Bean(name = HTTP_CLIENT_BEAN_NAME)
		public ApacheHttpClient httpClient(ObjectProvider<Apache5ClientConnectionManager> connectionManager) {
			return new ApacheHttpClient(connectionManager.getIfAvailable());
		}

	}

	/**
	 * Apache 同步 HttpClient Auto Configuration
	 */
	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(HttpClientProperties.class)
	@ConditionalOnClass(name = {"org.apache.http.impl.client.CloseableHttpClient"})
	@ConditionalOnMissingBean(name = HTTP_CLIENT_BEAN_NAME, value = com.buession.httpclient.HttpClient.class)
	@ConditionalOnProperty(prefix = HttpClientProperties.PREFIX, name = "apache-client.enabled", havingValue = "true", matchIfMissing = true)
	static class Apache4HttpClient extends ApacheHttpClientConfiguration {

		public Apache4HttpClient(HttpClientProperties properties) {
			super(properties);
		}

		@Bean(name = CLIENT_CONNECTION_MANAGER_BEAN_NAME)
		@ConditionalOnMissingBean
		public ApacheClientConnectionManager apache4ClientConnectionManager() {
			return new ApacheClientConnectionManager(properties);
		}

		@Bean(name = HTTP_CLIENT_BEAN_NAME)
		public ApacheHttpClient httpClient(ObjectProvider<ApacheClientConnectionManager> connectionManager) {
			return new ApacheHttpClient(connectionManager.getIfAvailable());
		}

	}

	/**
	 * Apache 5 异步 HttpClient Auto Configuration
	 */
	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(HttpClientProperties.class)
	@ConditionalOnClass(name = {"org.apache.hc.client5.http.async.HttpAsyncClient"})
	@ConditionalOnMissingBean(name = HTTP_CLIENT_BEAN_NAME, value = com.buession.httpclient.HttpAsyncClient.class)
	@ConditionalOnProperty(prefix = HttpClientProperties.PREFIX, name = "apache-client.async.enabled", havingValue =
			"true", matchIfMissing = true)
	static class Apache5AsyncHttpClient extends ApacheHttpClientConfiguration {

		public Apache5AsyncHttpClient(HttpClientProperties properties) {
			super(properties);
		}

		@Bean(name = NIO_CLIENT_CONNECTION_MANAGER_BEAN_NAME)
		public Apache5NioClientConnectionManager clientConnectionManager() {
			final Apache5NioClientConnectionManager clientConnectionManager =
					new Apache5NioClientConnectionManager(properties);

			if(properties.getApacheClient() != null){
				Optional.ofNullable(properties.getApacheClient().getIoReactor())
						.ifPresent(clientConnectionManager::setIoReactorConfig);
				Optional.ofNullable(properties.getApacheClient().getThreadFactory())
						.ifPresent((threadFactory)->clientConnectionManager.setThreadFactory(
								BeanUtils.instantiateClass(threadFactory)));
			}

			return clientConnectionManager;
		}

		@Bean(name = ASYNC_HTTP_CLIENT_BEAN_NAME)
		public ApacheHttpAsyncClient httpClient(ObjectProvider<Apache5NioClientConnectionManager> connectionManager) {
			return new ApacheHttpAsyncClient(connectionManager.getIfAvailable());
		}

	}

	/**
	 * Apache 异步 HttpClient Auto Configuration
	 */
	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(HttpClientProperties.class)
	@ConditionalOnClass(name = {"org.apache.http.nio.client.HttpAsyncClient"})
	@ConditionalOnMissingBean(name = HTTP_CLIENT_BEAN_NAME, value = com.buession.httpclient.HttpAsyncClient.class)
	@ConditionalOnProperty(prefix = HttpClientProperties.PREFIX, name = "apache-client.async.enabled", havingValue =
			"true", matchIfMissing = true)
	static class Apache4AsyncHttpClient extends ApacheHttpClientConfiguration {

		public Apache4AsyncHttpClient(HttpClientProperties properties) {
			super(properties);
		}

		@Bean(name = NIO_CLIENT_CONNECTION_MANAGER_BEAN_NAME)
		public ApacheNioClientConnectionManager clientConnectionManager() {
			final ApacheNioClientConnectionManager clientConnectionManager =
					new ApacheNioClientConnectionManager(properties);

			if(properties.getApacheClient() != null){
				Optional.ofNullable(properties.getApacheClient().getIoReactor())
						.ifPresent(clientConnectionManager::setIoReactorConfig);
				Optional.ofNullable(properties.getApacheClient().getThreadFactory())
						.ifPresent((threadFactory)->clientConnectionManager.setThreadFactory(
								BeanUtils.instantiateClass(threadFactory)));
			}

			return clientConnectionManager;
		}

		@Bean(name = ASYNC_HTTP_CLIENT_BEAN_NAME)
		public ApacheHttpAsyncClient httpClient(ObjectProvider<ApacheNioClientConnectionManager> connectionManager) {
			return new ApacheHttpAsyncClient(connectionManager.getIfAvailable());
		}

	}

}
