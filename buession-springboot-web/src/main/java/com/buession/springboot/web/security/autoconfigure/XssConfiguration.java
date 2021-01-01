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
package com.buession.springboot.web.security.autoconfigure;

import com.buession.security.web.xss.servlet.XssFilter;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Xss 过滤器自动配置
 *
 * @author Yong.Teng
 * @since 1.2.0
 */
@Configuration
@EnableConfigurationProperties(WebSecurityProperties.class)
@ConditionalOnProperty(prefix = "spring.security.xss", name = "enable", havingValue = "true")
public class XssConfiguration {

	@Autowired
	protected WebSecurityProperties webSecurityProperties;

	@Configuration
	@ConditionalOnClass(XssFilter.class)
	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
	public static class ServletXssConfiguration extends XssConfiguration {

		@Bean
		public XssFilter xssFilter() throws PolicyException{
			XssFilter xssFilter = new XssFilter();

			xssFilter.setPolicy(Policy.getInstance(webSecurityProperties.getXss().getPolicyConfigLocation()));

			return xssFilter;
		}

	}

}