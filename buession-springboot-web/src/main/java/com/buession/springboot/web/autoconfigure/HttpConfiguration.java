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
public class HttpConfiguration {

    @Autowired(required = false)
    protected HttpProperties httpProperties;

    @Configuration
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    public static class ServletHttpConfiguration extends HttpConfiguration {

        @Bean(name = "responseHeadersFilter")
        @ConditionalOnMissingBean
        public com.buession.web.servlet.filter.ResponseHeadersFilter responseHeadersFilter(){
            final com.buession.web.servlet.filter.ResponseHeadersFilter responseHeadersFilter = new com.buession.web
                    .servlet.filter.ResponseHeadersFilter();

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
        public com.buession.springboot.web.servlet.filter.PoweredByHeaderFilter poweredByHeaderFilter(){
            return new com.buession.springboot.web.servlet.filter.PoweredByHeaderFilter();
        }

        @Bean(name = "serverInfoFilter")
        @ConditionalOnMissingBean
        @ConditionalOnProperty(prefix = "server", name = "send-server-info", havingValue = "true", matchIfMissing =
                true)
        public com.buession.web.servlet.filter.ServerInfoFilter serverInfoFilter(){
            final com.buession.web.servlet.filter.ServerInfoFilter serverInfoFilter = new com.buession.web.servlet
                    .filter.ServerInfoFilter();

            if(Validate.hasText(httpProperties.getServerInfoName())){
                serverInfoFilter.setHeaderName(httpProperties.getServerInfoName());
            }

            return serverInfoFilter;
        }

        @Bean(name = "responseHeaderProcessor")
        @ConditionalOnMissingBean
        public com.buession.web.servlet.http.response.ResponseHeaderProcessor responseHeaderProcessor(){
            return new com.buession.web.servlet.http.response.ResponseHeaderProcessor();
        }

    }

    @Configuration
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    public static class ReactiveHttpConfiguration extends HttpConfiguration {

        @Bean(name = "responseHeadersFilter")
        @ConditionalOnMissingBean
        public com.buession.web.reactive.filter.ResponseHeadersFilter responseHeadersFilter(){
            final com.buession.web.reactive.filter.ResponseHeadersFilter responseHeadersFilter = new com.buession.web
                    .reactive.filter.ResponseHeadersFilter();

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
        public com.buession.springboot.web.reactive.filter.PoweredByHeaderFilter poweredByHeaderFilter(){
            return new com.buession.springboot.web.reactive.filter.PoweredByHeaderFilter();
        }

        @Bean(name = "serverInfoFilter")
        @ConditionalOnMissingBean
        @ConditionalOnProperty(prefix = "server", name = "send-server-info", havingValue = "true", matchIfMissing =
                true)
        public com.buession.web.reactive.filter.ServerInfoFilter serverInfoFilter(){
            final com.buession.web.reactive.filter.ServerInfoFilter serverInfoFilter = new com.buession.web.reactive
                    .filter.ServerInfoFilter();

            if(Validate.hasText(httpProperties.getServerInfoName())){
                serverInfoFilter.setHeaderName(httpProperties.getServerInfoName());
            }

            return serverInfoFilter;
        }

        @Bean(name = "responseHeaderProcessor")
        @ConditionalOnMissingBean
        public com.buession.web.reactive.http.response.ResponseHeaderProcessor responseHeaderProcessor(){
            return new com.buession.web.reactive.http.response.ResponseHeaderProcessor();
        }
    }

}
