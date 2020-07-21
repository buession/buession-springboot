/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * =================================================================================================
 *
 * This software consists of voluntary contributions made by many individuals on behalf of the
 * Apache Software Foundation. For more information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 * +------------------------------------------------------------------------------------------------+
 * | License: http://www.apache.org/licenses/LICENSE-2.0.txt 										|
 * | Author: Yong.Teng <webmaster@buession.com> 													|
 * | Copyright @ 2013-2020 Buession.com Inc.														|
 * +------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.web.servlet.autoconfigure;

import com.buession.core.validator.Validate;
import com.buession.springboot.web.autoconfigure.AbstractHttpConfiguration;
import com.buession.springboot.web.web.HttpProperties;
import com.buession.web.servlet.aop.advice.aopalliance.ServletHttpAttributeSourcePointcutAdvisor;
import com.buession.web.servlet.filter.PoweredByHeaderFilter;
import com.buession.web.servlet.filter.PrintUrlFilter;
import com.buession.web.servlet.filter.ResponseHeadersFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yong.Teng
 */
@Configuration
@EnableConfigurationProperties({HttpProperties.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ServletHttpConfiguration extends AbstractHttpConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public ResponseHeadersFilter responseHeadersFilter(){
		final ResponseHeadersFilter responseHeadersFilter = new ResponseHeadersFilter();

		if(Validate.isNotEmpty(httpProperties.getResponseHeaders())){
			responseHeadersFilter.setHeaders(buildHeaders(httpProperties.getResponseHeaders()));
		}

		return responseHeadersFilter;
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = "server.poweredby", name = "enable", havingValue = "true", matchIfMissing = true)
	public PoweredByHeaderFilter poweredByHeaderFilter(){
		return new PoweredByHeaderFilter();
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = "server.server-info", name = "enable", havingValue = "true", matchIfMissing = true)
	public com.buession.web.servlet.filter.ServerInfoFilter serverInfoFilter(){
		final ServerInfoFilter serverInfoFilter = new ServerInfoFilter();

		if(Validate.hasText(httpProperties.getServerInfoName())){
			serverInfoFilter.setHeaderName(httpProperties.getServerInfoName());
		}

		return serverInfoFilter;
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = "server.print-url", name = "enable", havingValue = "true")
	public PrintUrlFilter printUrlFilter(){
		return new PrintUrlFilter();
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
