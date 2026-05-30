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
 * | Copyright @ 2013-2026 Buession.com Inc.														|
 * +------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.web.servlet.autoconfigure;

import com.buession.security.web.servlet.config.ServletHttpSecurityConfiguration;
import com.buession.security.web.xss.servlet.WebMvcXssConfigurer;
import com.buession.security.web.xss.servlet.XssFilter;
import com.buession.springboot.web.autoconfigure.AbstractWebSecurityConfiguration;
import com.buession.springboot.web.security.WebSecurityProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security Configuration
 *
 * @author Yong.Teng
 * @since 2.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(WebSecurityProperties.class)
@ConditionalOnClass({HttpSecurity.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnBooleanProperty(prefix = WebSecurityProperties.PREFIX, name = "enabled", matchIfMissing = true)
public class ServletWebSecurityConfiguration extends AbstractWebSecurityConfiguration {

	public ServletWebSecurityConfiguration(WebSecurityProperties properties) {
		super(properties);
	}

	@Bean
	@ConditionalOnBooleanProperty(prefix = WebSecurityProperties.PREFIX, name = "xss.enabled")
	public XssFilter xssFilter() {
		return new XssFilter(xssOptions());
	}

	@Bean
	@ConditionalOnBooleanProperty(prefix = WebSecurityProperties.PREFIX, name = "xss.enabled")
	public WebMvcXssConfigurer webMvcXssConfigurer() {
		return new WebMvcXssConfigurer();
	}

	@Bean
	public SecurityFilterChain createSecurityFilterChain(ApplicationContext context) throws Exception {
		HttpSecurity httpSecurity = context.getBean(HttpSecurity.class);
		ServletHttpSecurityConfiguration servletHttpSecurityConfiguration = new ServletHttpSecurityConfiguration(
				properties, httpSecurity);

		return servletHttpSecurityConfiguration.createSecurityFilterChain();
	}

}
