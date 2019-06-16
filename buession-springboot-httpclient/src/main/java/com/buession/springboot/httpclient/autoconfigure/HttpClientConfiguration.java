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
 * | Copyright @ 2013-2019 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.httpclient.autoconfigure;

import com.buession.httpclient.ApacheHttpClient;
import com.buession.httpclient.HttpClient;
import com.buession.httpclient.OkHttpClient;
import com.buession.httpclient.conn.ApacheClientConnectionManager;
import com.buession.httpclient.conn.ConnectionManager;
import com.buession.httpclient.conn.OkHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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

    @Autowired
    private ConnectionManager connectionManager;

    /**
     * 实例化 ApacheClientConnectionManager 连接池管理器
     *
     * @return ApacheClientConnectionManager 连接池管理器
     */
    @Bean(name = "httpConnectionManager")
    @ConditionalOnMissingBean
    @ConditionalOnClass({org.apache.http.conn.HttpClientConnectionManager.class})
    public ApacheClientConnectionManager apacheClientConnectionManager(){
        return new ApacheClientConnectionManager(httpClientProperties);
    }

    @Bean(name = "httpClient")
    @ConditionalOnMissingBean
    @ConditionalOnClass({org.apache.http.client.HttpClient.class})
    public ApacheHttpClient apacheHttpClient(){
        return new ApacheHttpClient(connectionManager);
    }

    /**
     * 实例化 OkHttpClientConnectionManager 连接池管理器
     *
     * @return OkHttpClientConnectionManager 连接池管理器
     */
    @Bean(name = "httpConnectionManager")
    @ConditionalOnMissingBean
    @ConditionalOnClass({okhttp3.ConnectionPool.class})
    public OkHttpClientConnectionManager okHttpClientConnectionManager(){
        return new OkHttpClientConnectionManager(httpClientProperties);
    }

    @Bean(name = "httpClient")
    @ConditionalOnMissingBean
    @ConditionalOnClass({okhttp3.OkHttpClient.class})
    public OkHttpClient okHttpHttpClient(){
        return new OkHttpClient(connectionManager);
    }

}