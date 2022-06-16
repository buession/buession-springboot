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
package com.buession.springboot.httpclient.autoconfigure;

import com.buession.httpclient.ApacheHttpClient;
import com.buession.httpclient.HttpClient;
import com.buession.httpclient.OkHttpClient;
import com.buession.httpclient.conn.ApacheClientConnectionManager;
import com.buession.httpclient.conn.OkHttpClientConnectionManager;
import okhttp3.ConnectionPool;
import org.apache.http.conn.HttpClientConnectionManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * HttpClient Auto Configuration 抽象类
 *
 * @author Yong.Teng
 * @since 1.2.2
 */

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(HttpClientProperties.class)
public class HttpClientConfiguration {

	protected HttpClientProperties properties;

	public HttpClientConfiguration(HttpClientProperties properties){
		this.properties = properties;
	}

	/**
	 * Apache HttpClient Auto Configuration
	 *
	 * @author Yong.Teng
	 * @since 1.2.2
	 */
	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(HttpClientProperties.class)
	@ConditionalOnClass({HttpClientConnectionManager.class})
	@ConditionalOnMissingBean({HttpClient.class})
	@ConditionalOnProperty(prefix = "spring.httpclient.apache-client", name = "enabled", havingValue = "true", matchIfMissing = true)
	class ApacheHttpClientConfiguration extends HttpClientConfiguration {

		public ApacheHttpClientConfiguration(HttpClientProperties properties){
			super(properties);
		}

		@Bean
		@ConditionalOnMissingBean
		public ApacheClientConnectionManager apacheClientConnectionManager(){
			return new ApacheClientConnectionManager(properties);
		}

		@Bean
		@ConditionalOnMissingBean
		public ApacheHttpClient apacheHttpClient(ApacheClientConnectionManager connectionManager){
			return new ApacheHttpClient(connectionManager);
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(HttpClientProperties.class)
	@ConditionalOnClass({ConnectionPool.class})
	@ConditionalOnMissingBean({HttpClient.class})
	@ConditionalOnProperty(prefix = "spring.httpclient.okhttp", name = "enabled", havingValue = "true", matchIfMissing = true)
	class OkHttpHttpClientConfiguration extends HttpClientConfiguration {

		public OkHttpHttpClientConfiguration(HttpClientProperties properties){
			super(properties);
		}

		@Bean
		@ConditionalOnMissingBean
		public OkHttpClientConnectionManager connectionManager(){
			return new OkHttpClientConnectionManager(properties);
		}

		@Bean
		@ConditionalOnMissingBean
		public OkHttpClient okHttpHttpClient(OkHttpClientConnectionManager connectionManager){
			return new OkHttpClient(connectionManager);
		}

	}

}
