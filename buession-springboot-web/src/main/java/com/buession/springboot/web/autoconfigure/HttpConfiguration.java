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
import com.buession.springboot.web.reactive.filter.ReactivePoweredByHeaderFilter;
import com.buession.springboot.web.servlet.filter.ServletPoweredByHeaderFilter;
import com.buession.web.reactive.aop.advice.aopalliance.ReactiveHttpAttributeSourcePointcutAdvisor;
import com.buession.web.servlet.aop.advice.aopalliance.ServletHttpAttributeSourcePointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yong.Teng
 */
@Configuration
@EnableConfigurationProperties({HttpProperties.class})
public class HttpConfiguration {

    private final static String HEADER_VARIABLE_IDENTIFIER = "$";

    @Autowired(required = false)
    protected HttpProperties httpProperties;

    @Configuration
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    public static class ServletHttpConfiguration extends HttpConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public com.buession.web.servlet.filter.ResponseHeadersFilter responseHeadersFilter(){
            final com.buession.web.servlet.filter.ResponseHeadersFilter responseHeadersFilter = new com.buession.web
                    .servlet.filter.ResponseHeadersFilter();

            if(httpProperties != null && Validate.isEmpty(httpProperties.getResponseHeaders()) == false){
                responseHeadersFilter.setHeaders(buildHeaders(httpProperties.getResponseHeaders()));
            }

            return responseHeadersFilter;
        }

        @Bean
        @ConditionalOnMissingBean
        @ConditionalOnProperty(prefix = "server.poweredby", name = "enable", havingValue = "true", matchIfMissing =
                true)
        public ServletPoweredByHeaderFilter poweredByHeaderFilter(){
            return new ServletPoweredByHeaderFilter();
        }

        @Bean
        @ConditionalOnMissingBean
        @ConditionalOnProperty(prefix = "server.server-info", name = "enable", havingValue = "true", matchIfMissing =
                true)
        public com.buession.web.servlet.filter.ServerInfoFilter serverInfoFilter(){
            final com.buession.web.servlet.filter.ServerInfoFilter serverInfoFilter = new ServerInfoFilter();

            if(Validate.hasText(httpProperties.getServerInfoName())){
                serverInfoFilter.setHeaderName(httpProperties.getServerInfoName());
            }

            return serverInfoFilter;
        }

        @Bean
        @ConditionalOnMissingBean
        public ServletHttpAttributeSourcePointcutAdvisor servletHttpAttributeSourcePointcutAdvisor(){
            return new ServletHttpAttributeSourcePointcutAdvisor();
        }

        private final class ServerInfoFilter extends com.buession.web.servlet.filter.ServerInfoFilter {

            @Override
            protected String format(String computerName){
                return buildServerInfo(httpProperties, computerName);
            }

        }

    }

    @Configuration
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    public static class ReactiveHttpConfiguration extends HttpConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public com.buession.web.reactive.filter.ResponseHeadersFilter responseHeadersFilter(){
            final com.buession.web.reactive.filter.ResponseHeadersFilter responseHeadersFilter = new com.buession.web
                    .reactive.filter.ResponseHeadersFilter();

            if(httpProperties != null && Validate.isEmpty(httpProperties.getResponseHeaders()) == false){
                responseHeadersFilter.setHeaders(buildHeaders(httpProperties.getResponseHeaders()));
            }

            return responseHeadersFilter;
        }

        @Bean
        @ConditionalOnMissingBean
        @ConditionalOnProperty(prefix = "server.poweredby", name = "enable", havingValue = "true", matchIfMissing =
                true)
        public ReactivePoweredByHeaderFilter poweredByHeaderFilter(){
            return new ReactivePoweredByHeaderFilter();
        }

        @Bean
        @ConditionalOnMissingBean
        @ConditionalOnProperty(prefix = "server.server-info", name = "enable", havingValue = "true", matchIfMissing =
                true)
        public com.buession.web.reactive.filter.ServerInfoFilter serverInfoFilter(){
            final com.buession.web.reactive.filter.ServerInfoFilter serverInfoFilter = new ServerInfoFilter();

            if(Validate.hasText(httpProperties.getServerInfoName())){
                serverInfoFilter.setHeaderName(httpProperties.getServerInfoName());
            }

            return serverInfoFilter;
        }

        @Bean
        @ConditionalOnMissingBean
        public ReactiveHttpAttributeSourcePointcutAdvisor reactiveHttpAttributeSourcePointcutAdvisor(){
            return new ReactiveHttpAttributeSourcePointcutAdvisor();
        }

        private final class ServerInfoFilter extends com.buession.web.reactive.filter.ServerInfoFilter {

            @Override
            protected String format(String computerName){
                return buildServerInfo(httpProperties, computerName);
            }

        }

    }

    private final static Map<String, String> buildHeaders(final Map<String, String> headers){
        final Map<String, String> result = new HashMap<>(headers.size());

        headers.forEach((key, value)->{
            if(value.startsWith(HEADER_VARIABLE_IDENTIFIER)){
                String propertyName = value.substring(1);
                String propertyValue = System.getProperty(propertyName);

                if(Validate.hasText(propertyValue) == false){
                    propertyValue = System.getenv(propertyName);
                }

                if(Validate.hasText(propertyValue)){
                    result.put(key, propertyValue);
                }
            }else{
                result.put(key, value);
            }
        });

        return result;
    }

    private final static String buildServerInfo(final HttpProperties httpProperties, final String serverName){
        String s = serverName;
        StringBuffer sb = new StringBuffer();

        if(Validate.hasText(httpProperties.getServerInfoPrefix())){
            sb.append(httpProperties.getServerInfoPrefix());
        }

        if(Validate.hasText(httpProperties.getStripServerInfoPrefix()) && s.startsWith(httpProperties
                .getStripServerInfoPrefix())){
            s = s.substring(httpProperties.getStripServerInfoPrefix().length());
        }

        if(Validate.hasText(httpProperties.getStripServerInfoSuffix()) && s.endsWith(httpProperties
                .getStripServerInfoSuffix())){
            s = s.substring(0, s.length() - httpProperties.getStripServerInfoSuffix().length());
        }

        sb.append(s);

        if(Validate.hasText(httpProperties.getServerInfoSuffix())){
            sb.append(httpProperties.getServerInfoSuffix());
        }

        return sb.toString();
    }

}
