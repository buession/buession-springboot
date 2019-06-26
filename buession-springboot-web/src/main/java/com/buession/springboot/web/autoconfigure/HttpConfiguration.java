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
package com.buession.springboot.web.autoconfigure;

import com.buession.core.validator.Validate;
import com.buession.springboot.web.filter.PoweredByHeaderFilter;
import com.buession.web.filter.ResponseHeadersFilter;
import com.buession.web.filter.ServerInfoFilter;
import com.buession.web.http.response.ResponseHeaderProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author Yong.Teng
 */
@Configuration
@EnableConfigurationProperties({HttpProperties.class})
@ConditionalOnWebApplication
public class HttpConfiguration {

    @Autowired(required = false)
    private HttpProperties httpProperties;

    @Bean(name = "responseHeadersFilter")
    @ConditionalOnMissingBean
    public ResponseHeadersFilter responseHeadersFilter(){
        final ResponseHeadersFilter responseHeadersFilter = new ResponseHeadersFilter();

        if(httpProperties != null){
            Map<String, String> responseHeaders = httpProperties.getResponse().getHeaders();

            if(responseHeaders != null){
                responseHeadersFilter.setHeaders(responseHeaders);
            }
        }

        return responseHeadersFilter;
    }

    @Bean(name = "poweredByHeaderFilter")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "server", name = "send-poweredby", havingValue = "true", matchIfMissing = true)
    public PoweredByHeaderFilter poweredByHeaderFilter(){
        return new PoweredByHeaderFilter();
    }

    @Bean(name = "serverInfoFilter")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "server", name = "send-server-info", havingValue = "true", matchIfMissing = true)
    public ServerInfoFilter serverInfoFilter(){
        ServerInfoFilter serverInfoFilter = new ServerInfoFilter();

        if(Validate.hasText(httpProperties.getServerInfoName())){
            serverInfoFilter.setHeaderName(httpProperties.getServerInfoName());
        }

        return serverInfoFilter;
    }

    @Bean(name = "responseHeaderProcessor")
    @ConditionalOnMissingBean
    public ResponseHeaderProcessor responseHeaderProcessor(){
        return new ResponseHeaderProcessor();
    }

}
