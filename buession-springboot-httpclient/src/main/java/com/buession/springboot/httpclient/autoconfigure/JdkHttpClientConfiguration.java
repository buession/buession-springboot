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
 * | Copyright @ 2013-2026 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.httpclient.autoconfigure;

import com.buession.httpclient.JdkHttpAsyncClient;
import com.buession.httpclient.JdkHttpClient;
import com.buession.httpclient.conn.JdkHttpClientConnectionManager;
import com.buession.httpclient.conn.JdkHttpNioClientConnectionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * JDK HttpClient Auto Configuration
 *
 * @author Yong.Teng
 * @since 4.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(HttpClientProperties.class)
public class JdkHttpClientConfiguration extends AbstractHttpClientConfiguration {

	public JdkHttpClientConfiguration(HttpClientProperties properties) {
		super(properties);
	}

	/**
	 * JDK 同步 HttpClient Auto Configuration
	 */
	@AutoConfiguration
	@EnableConfigurationProperties(HttpClientProperties.class)
	@ConditionalOnClass(java.net.http.HttpClient.class)
	@ConditionalOnMissingBean(name = HTTP_CLIENT_BEAN_NAME, value = com.buession.httpclient.HttpClient.class)
	@ConditionalOnBooleanProperty(prefix = HttpClientProperties.PREFIX, name = "jdk.enabled", matchIfMissing = true)
	static class HttpClient extends JdkHttpClientConfiguration {

		public HttpClient(HttpClientProperties properties) {
			super(properties);
		}

		@Bean(name = CLIENT_CONNECTION_MANAGER_BEAN_NAME)
		@ConditionalOnMissingBean(name = {CLIENT_CONNECTION_MANAGER_BEAN_NAME})
		public JdkHttpClientConnectionManager jdkHttpClientConnectionManager() {
			return new JdkHttpClientConnectionManager(properties);
		}

		@Bean(name = HTTP_CLIENT_BEAN_NAME)
		@ConditionalOnBean(name = {CLIENT_CONNECTION_MANAGER_BEAN_NAME})
		public JdkHttpClient httpClient(
				@Qualifier(CLIENT_CONNECTION_MANAGER_BEAN_NAME) JdkHttpClientConnectionManager clientConnectionManager) {
			return new JdkHttpClient(clientConnectionManager);
		}

	}

	/**
	 * JDK 异步 HttpClient Auto Configuration
	 */
	@AutoConfiguration
	@EnableConfigurationProperties(HttpClientProperties.class)
	@ConditionalOnClass(java.net.http.HttpClient.class)
	@ConditionalOnMissingBean(name = HTTP_CLIENT_BEAN_NAME, value = com.buession.httpclient.HttpAsyncClient.class)
	@ConditionalOnBooleanProperty(prefix = HttpClientProperties.PREFIX, name = "jdk.async.enabled", matchIfMissing =
			true)
	static class AsyncHttpClient extends JdkHttpClientConfiguration {

		public AsyncHttpClient(HttpClientProperties properties) {
			super(properties);
		}

		@Bean(name = NIO_CLIENT_CONNECTION_MANAGER_BEAN_NAME)
		@ConditionalOnMissingBean(name = {NIO_CLIENT_CONNECTION_MANAGER_BEAN_NAME})
		public JdkHttpNioClientConnectionManager jdkHttpNioClientConnectionManager() {
			return new JdkHttpNioClientConnectionManager(properties);
		}

		@Bean(name = ASYNC_HTTP_CLIENT_BEAN_NAME)
		@ConditionalOnBean(name = {NIO_CLIENT_CONNECTION_MANAGER_BEAN_NAME})
		public JdkHttpAsyncClient httpClient(
				@Qualifier(NIO_CLIENT_CONNECTION_MANAGER_BEAN_NAME) JdkHttpNioClientConnectionManager clientConnectionManager) {
			return new JdkHttpAsyncClient(clientConnectionManager);
		}

	}

}
