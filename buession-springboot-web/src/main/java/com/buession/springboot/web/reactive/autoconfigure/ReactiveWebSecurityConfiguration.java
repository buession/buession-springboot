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
 * | Copyright @ 2013-2022 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.web.reactive.autoconfigure;

import com.buession.core.validator.Validate;
import com.buession.security.web.config.Configurer;
import com.buession.security.web.reactive.config.ReactiveWebSecurityConfigurerAdapterConfiguration;
import com.buession.security.web.utils.PolicyUtils;
import com.buession.security.web.xss.reactive.XssFilter;
import com.buession.springboot.web.autoconfigure.AbstractWebSecurityConfiguration;
import com.buession.springboot.web.security.WebSecurityProperties;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.web.server.ServerHttpSecurity;

import java.io.IOException;

/**
 * @author Yong.Teng
 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "spring.security", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnClass({WebSecurityConfigurerAdapter.class})
@ConditionalOnBean({ServerHttpSecurity.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@Order(ReactiveWebSecurityConfiguration.ORDER)
public class ReactiveWebSecurityConfiguration extends AbstractWebSecurityConfiguration {

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(WebSecurityProperties.class)
	@Order(100)
	static class DefaultWebSecurityConfigurerAdapterConfiguration extends
			ReactiveWebSecurityConfigurerAdapterConfiguration {

		public DefaultWebSecurityConfigurerAdapterConfiguration(WebSecurityProperties properties,
																ObjectProvider<ServerHttpSecurity> serverHttpSecurity){
			super(new Configurer(properties.getHttpBasic(), properties.getCsrf(), properties.getFrameOptions(),
					properties.getHsts(), properties.getHpkp(), properties.getContentSecurityPolicy(),
					properties.getReferrerPolicy(), properties.getXss()), serverHttpSecurity.getIfAvailable());
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(WebSecurityProperties.class)
	@ConditionalOnProperty(prefix = "spring.security.xss", name = "enabled", havingValue = "true")
	@ConditionalOnClass({Policy.class})
	static class XssConfiguration {

		protected WebSecurityProperties properties;

		public XssConfiguration(WebSecurityProperties properties){
			this.properties = properties;
		}

		@Bean
		public XssFilter xssFilter() throws PolicyException, IOException{
			XssFilter xssFilter = new XssFilter();
			String policyConfigLocation = properties.getXss().getPolicyConfigLocation();

			if(Validate.hasText(policyConfigLocation)){
				xssFilter.setPolicy(PolicyUtils.createFromConfigFile(policyConfigLocation));
			}

			return xssFilter;
		}

	}

}
