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

import com.buession.httpclient.ApacheHttpAsyncClient;
import com.buession.httpclient.ApacheHttpClient;
import com.buession.httpclient.conn.ApacheClientConnectionManager;
import com.buession.httpclient.conn.ApacheNioClientConnectionManager;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Apache HttpClient Auto Configuration
 *
 * @author Yong.Teng
 * @since 2.3.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(HttpClientProperties.class)
public class ApacheHttpClientConfiguration extends AbstractHttpClientConfiguration {

	public ApacheHttpClientConfiguration(HttpClientProperties properties){
		super(properties);
	}

	/**
	 * Apache 同步 HttpClient Auto Configuration
	 */
	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(HttpClientProperties.class)
	@ConditionalOnClass(org.apache.http.impl.client.CloseableHttpClient.class)
	@ConditionalOnMissingBean(name = HTTP_CLIENT_BEAN_NAME, value = com.buession.httpclient.HttpClient.class)
	@ConditionalOnProperty(prefix = HttpClientProperties.PREFIX, name = "apache-client.enabled", havingValue = "true", matchIfMissing = true)
	static class HttpClient extends ApacheHttpClientConfiguration {

		public HttpClient(HttpClientProperties properties){
			super(properties);
		}

		@Bean(name = CLIENT_CONNECTION_MANAGER_BEAN_NAME)
		public ApacheClientConnectionManager clientConnectionManager(){
			return new ApacheClientConnectionManager(properties);
		}

		@Bean(name = HTTP_CLIENT_BEAN_NAME)
		public ApacheHttpClient httpClient(ApacheClientConnectionManager connectionManager){
			return new ApacheHttpClient(connectionManager);
		}

	}

	/**
	 * Apache 异步 HttpClient Auto Configuration
	 */
	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(HttpClientProperties.class)
	@ConditionalOnClass(org.apache.http.nio.client.HttpAsyncClient.class)
	@ConditionalOnMissingBean(name = HTTP_CLIENT_BEAN_NAME, value = com.buession.httpclient.HttpAsyncClient.class)
	@ConditionalOnProperty(prefix = HttpClientProperties.PREFIX, name = "apache-client.async.enabled", havingValue =
			"true", matchIfMissing = true)
	static class AsyncHttpClient extends ApacheHttpClientConfiguration {

		public AsyncHttpClient(HttpClientProperties properties){
			super(properties);
		}

		@Bean(name = NIO_CLIENT_CONNECTION_MANAGER_BEAN_NAME)
		public ApacheNioClientConnectionManager clientConnectionManager(){
			final ApacheNioClientConnectionManager clientConnectionManager =
					new ApacheNioClientConnectionManager(properties);

			if(properties.getApacheClient() != null){
				if(properties.getApacheClient().getIoReactor() != null){
					clientConnectionManager.setIoReactorConfig(properties.getApacheClient().getIoReactor());
				}

				if(properties.getApacheClient().getThreadFactory() != null){
					clientConnectionManager.setThreadFactory(
							BeanUtils.instantiateClass(properties.getApacheClient().getThreadFactory()));
				}
			}

			return clientConnectionManager;
		}

		@Bean(name = ASYNC_HTTP_CLIENT_BEAN_NAME)
		public ApacheHttpAsyncClient httpClient(ApacheNioClientConnectionManager connectionManager){
			return new ApacheHttpAsyncClient(connectionManager);
		}

	}

}
