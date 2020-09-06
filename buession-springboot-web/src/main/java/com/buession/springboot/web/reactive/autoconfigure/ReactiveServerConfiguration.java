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
package com.buession.springboot.web.reactive.autoconfigure;

import com.buession.core.validator.Validate;
import com.buession.springboot.web.autoconfigure.AbstractServerConfiguration;
import com.buession.springboot.web.web.ServerProperties;
import com.buession.web.reactive.aop.advice.aopalliance.ReactiveHttpAttributeSourcePointcutAdvisor;
import com.buession.web.reactive.filter.PoweredByHeaderFilter;
import com.buession.web.reactive.filter.PrintUrlFilter;
import com.buession.web.reactive.filter.ResponseHeadersFilter;
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
@EnableConfigurationProperties({ServerProperties.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class ReactiveServerConfiguration extends AbstractServerConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public ResponseHeadersFilter responseHeadersFilter(){
		final ResponseHeadersFilter responseHeadersFilter = new ResponseHeadersFilter();

		if(Validate.isNotEmpty(serverProperties.getResponseHeaders())){
			responseHeadersFilter.setHeaders(buildHeaders(serverProperties.getResponseHeaders()));
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
	public com.buession.web.reactive.filter.ServerInfoFilter serverInfoFilter(){
		final ServerInfoFilter serverInfoFilter = new ServerInfoFilter();

		if(Validate.hasText(serverProperties.getServerInfoName())){
			serverInfoFilter.setHeaderName(serverProperties.getServerInfoName());
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
	public ReactiveHttpAttributeSourcePointcutAdvisor reactiveHttpAttributeSourcePointcutAdvisor(){
		return new ReactiveHttpAttributeSourcePointcutAdvisor();
	}

	private final class ServerInfoFilter extends com.buession.web.reactive.filter.ServerInfoFilter {

		@Override
		protected String format(String computerName){
			return buildServerInfo(serverProperties, computerName);
		}

	}

}
