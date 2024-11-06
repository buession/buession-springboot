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
 * | Copyright @ 2013-2024 Buession.com Inc.														|
 * +------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.web.servlet.autoconfigure;

import com.buession.core.validator.Validate;
import com.buession.springboot.web.autoconfigure.AbstractServerConfiguration;
import com.buession.springboot.web.autoconfigure.ServerProperties;
import com.buession.springboot.web.servlet.filter.ServerInfoFilter;
import com.buession.web.servlet.filter.PoweredByFilter;
import com.buession.web.servlet.filter.PrintUrlFilter;
import com.buession.web.servlet.filter.ResponseHeadersFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author Yong.Teng
 */
@AutoConfiguration
@EnableConfigurationProperties(ServerProperties.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ServletServerConfiguration extends AbstractServerConfiguration {

	public ServletServerConfiguration(ServerProperties serverProperties) {
		super(serverProperties);
	}

	@Bean
	@ConditionalOnMissingBean
	public ResponseHeadersFilter responseHeadersFilter() {
		final ResponseHeadersFilter responseHeadersFilter = new ResponseHeadersFilter();

		if(Validate.isNotEmpty(properties.getResponseHeaders())){
			responseHeadersFilter.setHeaders(buildHeaders(properties.getResponseHeaders()));
		}

		return responseHeadersFilter;
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = ServerProperties.PREFIX, name = "poweredby.enabled", havingValue = "true", matchIfMissing = true)
	public PoweredByFilter poweredByFilter() {
		return new PoweredByFilter();
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = ServerProperties.PREFIX, name = "server-info.enabled", havingValue = "true", matchIfMissing = true)
	public ServerInfoFilter serverInfoFilter() {
		return new ServerInfoFilter(properties.getServerInfoName(), properties.getServerInfoPrefix(),
				properties.getServerInfoSuffix(), properties.getStripServerInfoPrefix(),
				properties.getStripServerInfoSuffix());
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = ServerProperties.PREFIX, name = "print-url.enabled", havingValue = "true")
	public PrintUrlFilter printUrlFilter() {
		return new PrintUrlFilter();
	}

	@AutoConfiguration
	static class AnnotationProcessorConfiguration
			extends com.buession.web.servlet.config.ServletAnnotationProcessorConfiguration {

	}

}
