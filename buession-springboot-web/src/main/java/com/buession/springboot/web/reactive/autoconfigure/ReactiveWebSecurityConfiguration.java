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
 * | Copyright @ 2013-2026 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.web.reactive.autoconfigure;

import com.buession.security.web.reactive.config.ReactiveHttpSecurityConfiguration;
import com.buession.security.web.xss.Options;
import com.buession.security.web.xss.reactive.XssFilter;
import com.buession.springboot.web.autoconfigure.AbstractWebSecurityConfiguration;
import com.buession.springboot.web.security.WebSecurityProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @author Yong.Teng
 * @since 2.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(WebSecurityProperties.class)
@ConditionalOnClass({ServerHttpSecurity.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@ConditionalOnBooleanProperty(prefix = WebSecurityProperties.PREFIX, name = "enabled", matchIfMissing = true)
public class ReactiveWebSecurityConfiguration extends AbstractWebSecurityConfiguration {

	public ReactiveWebSecurityConfiguration(WebSecurityProperties properties) {
		super(properties);
	}

	@Bean
	@ConditionalOnBooleanProperty(prefix = WebSecurityProperties.PREFIX, name = "xss.enabled")
	public XssFilter xssFilter() {
		final com.buession.security.web.config.Xss xss = properties.getXss();
		final Options.Builder optionsBuilder = Options.Builder.getInstance();

		if(xss instanceof WebSecurityProperties.Xss xssProperties){
			optionsBuilder.policy(xssProperties.getMode());

			if(xssProperties.getMode() == Options.Policy.ESCAPE){
				optionsBuilder.escape(new Options.Escape());
			}else{
				final Options.Clean clean = new Options.Clean();

				clean.setPolicyConfigLocation(xssProperties.getPolicyConfigLocation());

				optionsBuilder.clean(clean);
			}
		}

		return new XssFilter(optionsBuilder.build());
	}

	@Bean
	@ConditionalOnBean({ServerHttpSecurity.class})
	@Scope("prototype")
	public SecurityWebFilterChain securityWebFilterChain(ObjectProvider<ServerHttpSecurity> httpSecurity,
	                                                     ObjectProvider<ServerHttpSecurityCustomizer> httpSecurityCustomizer) {
		httpSecurityCustomizer.orderedStream()
				.forEach((customizer)->customizer.customize(httpSecurity.getIfAvailable()));
		ReactiveHttpSecurityConfiguration httpSecurityConfiguration =
				new ReactiveHttpSecurityConfiguration(properties, httpSecurity.getIfAvailable());
		return httpSecurityConfiguration.createSecurityWebFilterChain();
	}

	public interface ServerHttpSecurityCustomizer {

		void customize(ServerHttpSecurity httpSecurity);

	}

}
