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
package com.buession.springboot.httpclient.httpcomponents.autoconfigure;

import com.buession.springboot.httpclient.autoconfigure.SSLConfiguration;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;

/**
 * @author Yong.Teng
 */
@Configuration
@EnableConfigurationProperties(HttpClientProperties.class)
@Import({SSLConfiguration.class})
@ConditionalOnClass(HttpClient.class)
@Order(2)
public class HttpClientConfiguration {

    @Autowired
    private HttpClientProperties httpClientProperties;

    @Autowired
    private HttpClientConnectionManager httpClientConnectionManager;

    @Autowired
    private SSLConnectionSocketFactory sslConnectionSocketFactory;

    /**
     * 实例化一个连接池管理器，设置最大连接数、并发连接数
     *
     * @return HttpClientConnectionManager
     */
    @Bean(name = "httpClientConnectionManager")
    @ConditionalOnMissingBean
    public HttpClientConnectionManager httpClientConnectionManager(){
        //Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create().register
        // ("http", new PlainConnectionSocketFactory()).register("https", sslConnectionSocketFactory).build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();

        //最大连接数
        connectionManager.setMaxTotal(httpClientProperties.getMaxTotal());
        //并发数
        connectionManager.setDefaultMaxPerRoute(httpClientProperties.getDefaultMaxPerRoute());

        return connectionManager;
    }

    @Bean(name = "httpClient")
    @ConditionalOnMissingBean
    public HttpClient httpClient(){
        return HttpClientBuilder.create().setConnectionManager(httpClientConnectionManager).build();
    }

    @Bean(name = "requestConfig")
    @ConditionalOnMissingBean
    public RequestConfig requestConfig(){
        return RequestConfig.custom().setConnectTimeout(httpClientProperties.getConnectTimeout())
                .setConnectionRequestTimeout(httpClientProperties.getConnectionRequestTimeout()).setSocketTimeout
                        (httpClientProperties.getSocketTimeout()).build();
    }

}
