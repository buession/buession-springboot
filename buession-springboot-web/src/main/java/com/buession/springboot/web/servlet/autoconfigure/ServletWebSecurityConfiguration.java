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
 * | Copyright @ 2013-2022 Buession.com Inc.														|
 * +------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.web.servlet.autoconfigure;

import com.buession.core.validator.Validate;
import com.buession.security.web.builder.servlet.ServletHttpSecurityBuilder;
import com.buession.security.web.xss.servlet.XssFilter;
import com.buession.springboot.web.security.WebSecurityProperties;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.io.IOException;

/**
 * Spring Security Configuration
 *
 * @author Yong.Teng
 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(WebSecurityProperties.class)
@ConditionalOnProperty(prefix = "spring.security", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private WebSecurityProperties properties;

	public WebSecurityConfiguration(WebSecurityProperties properties){
		super();
		this.properties = properties;
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception{
		ServletHttpSecurityBuilder.getInstance(httpSecurity).httpBasic(properties.getHttpBasic())
				.csrf(properties.getCsrf()).frameOptions(properties.getFrameOptions()).hsts(properties.getHsts())
				.hpkp(properties.getHpkp()).contentSecurityPolicy(properties.getContentSecurityPolicy())
				.referrerPolicy(properties.getReferrerPolicy()).xss(properties.getXss());
	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(WebSecurityProperties.class)
	@ConditionalOnProperty(prefix = "spring.security.xss", name = "enabled", havingValue = "true")
	@ConditionalOnClass({Policy.class})
	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
	public static class XssConfiguration {

		protected WebSecurityProperties properties;

		public XssConfiguration(WebSecurityProperties properties){
			this.properties = properties;
		}

		@Bean
		public XssFilter xssFilter() throws PolicyException, IOException{
			XssFilter xssFilter = new XssFilter();
			String policyConfigLocation = properties.getXss().getPolicyConfigLocation();

			if(Validate.hasText(policyConfigLocation)){
				PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
				Resource[] resources = resourceResolver.getResources(policyConfigLocation);

				xssFilter.setPolicy(Policy.getInstance(resources[0].getInputStream()));
			}

			return xssFilter;
		}

	}

}
