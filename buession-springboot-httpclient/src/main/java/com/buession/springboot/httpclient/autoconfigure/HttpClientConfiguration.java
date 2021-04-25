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
import com.buession.httpclient.OkHttpClient;
import com.buession.httpclient.conn.ApacheClientConnectionManager;
import com.buession.httpclient.conn.OkHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yong.Teng
 */
@Configuration
@EnableConfigurationProperties(HttpClientProperties.class)
public class HttpClientConfiguration {

	@Autowired
	private HttpClientProperties httpClientProperties;

	/**
	 * 实例化 ApacheClientConnectionManager 连接池管理器
	 *
	 * @return ApacheClientConnectionManager 连接池管理器
	 */
	@Bean
	@ConditionalOnClass(name = {"org.apache.http.conn.HttpClientConnectionManager"})
	@ConditionalOnProperty(prefix = "spring.httpclient.apache-client", name = "enable", havingValue = "true",
			matchIfMissing = true)
	@ConditionalOnMissingBean
	public ApacheClientConnectionManager apacheClientConnectionManager(){
		return new ApacheClientConnectionManager(httpClientProperties);
	}

	/**
	 * 实例化 ApacheClientConnectionManager 连接池管理器
	 *
	 * @return ApacheClientConnectionManager 连接池管理器
	 */
	@Bean
	@ConditionalOnClass(name = {"org.apache.http.conn.HttpClientConnectionManager"})
	@ConditionalOnProperty(prefix = "httpclient.apacheclient", name = "enable", havingValue = "true", matchIfMissing =
			true)
	@DeprecatedConfigurationProperty(reason = "规范名称", replacement = "spring.httpclient.apache-client.enable")
	@ConditionalOnMissingBean
	@Deprecated
	public ApacheClientConnectionManager deprecatedApacheClientConnectionManager(){
		return new ApacheClientConnectionManager(httpClientProperties);
	}

	@Bean
	@ConditionalOnClass(name = {"org.apache.http.client.HttpClient"})
	@ConditionalOnProperty(prefix = "spring.httpclient.apache-client", name = "enable", havingValue = "true",
			matchIfMissing = true)
	@ConditionalOnMissingBean
	public ApacheHttpClient apacheHttpClient(ApacheClientConnectionManager connectionManager){
		return new ApacheHttpClient(connectionManager);
	}

	@Bean
	@ConditionalOnClass(name = {"org.apache.http.client.HttpClient"})
	@ConditionalOnProperty(prefix = "httpclient.apacheclient", name = "enable", havingValue = "true", matchIfMissing =
			true)
	@DeprecatedConfigurationProperty(reason = "规范名称", replacement = "spring.httpclient.apache-client.enable")
	@ConditionalOnMissingBean
	@Deprecated
	public ApacheHttpClient deprecatedApacheHttpClient(ApacheClientConnectionManager connectionManager){
		return new ApacheHttpClient(connectionManager);
	}

	/**
	 * 实例化 OkHttpClientConnectionManager 连接池管理器
	 *
	 * @return OkHttpClientConnectionManager 连接池管理器
	 */
	@Bean
	@ConditionalOnClass(name = {"okhttp3.ConnectionPool"})
	@ConditionalOnProperty(prefix = "spring.httpclient.okhttp", name = "enable", havingValue = "true", matchIfMissing
			= true)
	@ConditionalOnMissingBean
	public OkHttpClientConnectionManager okHttpClientConnectionManager(){
		return new OkHttpClientConnectionManager(httpClientProperties);
	}

	/**
	 * 实例化 OkHttpClientConnectionManager 连接池管理器
	 *
	 * @return OkHttpClientConnectionManager 连接池管理器
	 */
	@Bean
	@ConditionalOnClass(name = {"okhttp3.ConnectionPool"})
	@ConditionalOnProperty(prefix = "httpclient.okhttp", name = "enable", havingValue = "true", matchIfMissing = true)
	@DeprecatedConfigurationProperty(reason = "规范名称", replacement = "spring.httpclient.okhttp.enable")
	@ConditionalOnMissingBean
	@Deprecated
	public OkHttpClientConnectionManager deprecatedOkHttpClientConnectionManager(){
		return new OkHttpClientConnectionManager(httpClientProperties);
	}

	@Bean
	@ConditionalOnClass(name = {"okhttp3.OkHttpClient"})
	@ConditionalOnProperty(prefix = "spring.httpclient.okhttp", name = "enable", havingValue = "true", matchIfMissing
			= true)
	@ConditionalOnMissingBean
	public OkHttpClient okHttpHttpClient(OkHttpClientConnectionManager connectionManager){
		return new OkHttpClient(connectionManager);
	}

	@Bean
	@ConditionalOnClass(name = {"okhttp3.OkHttpClient"})
	@ConditionalOnProperty(prefix = "httpclient.okhttp", name = "enable", havingValue = "true", matchIfMissing = true)
	@DeprecatedConfigurationProperty(reason = "规范名称", replacement = "spring.httpclient.okhttp.enable")
	@ConditionalOnMissingBean
	@Deprecated
	public OkHttpClient deprecatedOkHttpHttpClient(OkHttpClientConnectionManager connectionManager){
		return new OkHttpClient(connectionManager);
	}

}
