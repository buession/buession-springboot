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
package com.buession.springboot.httpclient.autoconfigure;

import com.buession.httpclient.OkHttpHttpAsyncClient;
import com.buession.httpclient.OkHttpHttpClient;
import com.buession.httpclient.conn.OkHttpClientConnectionManager;
import com.buession.httpclient.conn.OkHttpNioClientConnectionManager;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OkHttp HttpClient Auto Configuration
 *
 * @author Yong.Teng
 * @since 2.3.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(HttpClientProperties.class)
public class OkHttpHttpClientConfiguration extends AbstractHttpClientConfiguration {

	public OkHttpHttpClientConfiguration(HttpClientProperties properties){
		super(properties);
	}

	/**
	 * OkHttp 同步 HttpClient Auto Configuration
	 */
	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(HttpClientProperties.class)
	@ConditionalOnClass(okhttp3.OkHttpClient.class)
	@ConditionalOnMissingBean(name = HTTP_CLIENT_BEAN_NAME, value = com.buession.httpclient.HttpClient.class)
	@ConditionalOnProperty(prefix = HttpClientProperties.PREFIX, name = "okhttp.enabled", havingValue = "true", matchIfMissing = true)
	static class HttpClient extends OkHttpHttpClientConfiguration {

		public HttpClient(HttpClientProperties properties){
			super(properties);
		}

		@Bean(name = CLIENT_CONNECTION_MANAGER_BEAN_NAME)
		public OkHttpClientConnectionManager clientConnectionManager(){
			return new OkHttpClientConnectionManager(properties);
		}

		@Bean(name = HTTP_CLIENT_BEAN_NAME)
		public OkHttpHttpClient httpClient(ObjectProvider<OkHttpClientConnectionManager> connectionManager){
			return new OkHttpHttpClient(connectionManager.getIfAvailable());
		}

	}

	/**
	 * OkHttp 异步 HttpClient Auto Configuration
	 */
	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(HttpClientProperties.class)
	@ConditionalOnClass(okhttp3.OkHttpClient.class)
	@ConditionalOnMissingBean(name = HTTP_CLIENT_BEAN_NAME, value = com.buession.httpclient.HttpAsyncClient.class)
	@ConditionalOnProperty(prefix = HttpClientProperties.PREFIX, name = "okhttp.async.enabled", havingValue = "true", matchIfMissing = true)
	static class AsyncHttpClient extends OkHttpHttpClientConfiguration {

		public AsyncHttpClient(HttpClientProperties properties){
			super(properties);
		}

		@Bean(name = NIO_CLIENT_CONNECTION_MANAGER_BEAN_NAME)
		public OkHttpNioClientConnectionManager clientConnectionManager(){
			return new OkHttpNioClientConnectionManager(properties);
		}

		@Bean(name = ASYNC_HTTP_CLIENT_BEAN_NAME)
		public OkHttpHttpAsyncClient httpClient(ObjectProvider<OkHttpNioClientConnectionManager> connectionManager){
			return new OkHttpHttpAsyncClient(connectionManager.getIfAvailable());
		}

	}

}
