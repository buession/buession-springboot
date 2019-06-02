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

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Yong.Teng
 */
@ConfigurationProperties(prefix = "httpclient")
public class HttpClientProperties {

    private int maxTotal = 5000;

    private int defaultMaxPerRoute = 500;

    private int connectTimeout = 3000;

    private int connectionRequestTimeout = 5000;

    private int socketTimeout = 5000;

    public int getMaxTotal(){
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal){
        this.maxTotal = maxTotal;
    }

    public int getDefaultMaxPerRoute(){
        return defaultMaxPerRoute;
    }

    public void setDefaultMaxPerRoute(int defaultMaxPerRoute){
        this.defaultMaxPerRoute = defaultMaxPerRoute;
    }

    public int getConnectTimeout(){
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout){
        this.connectTimeout = connectTimeout;
    }

    public int getConnectionRequestTimeout(){
        return connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(int connectionRequestTimeout){
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public int getSocketTimeout(){
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout){
        this.socketTimeout = socketTimeout;
    }

}
