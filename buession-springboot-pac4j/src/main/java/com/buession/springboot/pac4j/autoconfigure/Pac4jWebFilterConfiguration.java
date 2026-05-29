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
package com.buession.springboot.pac4j.autoconfigure;

import com.buession.core.utils.StringUtils;
import com.buession.core.utils.SystemPropertyUtils;
import com.buession.core.validator.Validate;
import org.pac4j.core.config.Config;
import org.pac4j.core.util.Pac4jConstants;
import org.pac4j.springframework.web.SecurityFilter;
import org.pac4j.springframework.web.SecurityInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @author Yong.Teng
 * @since 2.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(Pac4jProperties.class)
@ConditionalOnBooleanProperty(prefix = Pac4jProperties.PREFIX + ".filter", name = "enabled",
		matchIfMissing = true)
@Import({Pac4jConfiguration.class})
public class Pac4jWebFilterConfiguration {

	private final Pac4jProperties properties;

	public Pac4jWebFilterConfiguration(Pac4jProperties properties) {
		this.properties = properties;

		callbackConfig(properties.getFilter().getCallback());
		logoutConfig(properties.getFilter().getLogout());
	}


	/**
	 * 登录成功回调配置
	 *
	 * @param callbackConfig
	 * 		登录成功回调配置
	 */
	private void callbackConfig(final Pac4jProperties.Filter.Callback callbackConfig) {
		SystemPropertyUtils.setPropertyIfPresent("pac4j.callback.path", callbackConfig.getPath());
		SystemPropertyUtils.setPropertyIfPresent("pac4j.callback.defaultUrl", callbackConfig.getDefaultUrl());
		SystemPropertyUtils.setPropertyIfPresent("pac4j.callback.renewSession", callbackConfig.getRenewSession());
		SystemPropertyUtils.setPropertyIfPresent("pac4j.callback.defaultClient", properties.getDefaultClient());
	}

	/**
	 * 退出登录配置
	 *
	 * @param logoutConfig
	 * 		退出登录配置
	 */
	private void logoutConfig(final Pac4jProperties.Filter.Logout logoutConfig) {
		SystemPropertyUtils.setPropertyIfPresent("pac4j.logout.path", logoutConfig.getPath());
		SystemPropertyUtils.setPropertyIfPresent("pac4j.logout.defaultUrl", logoutConfig.getDefaultUrl());
		SystemPropertyUtils.setPropertyIfPresent("pac4j.logout.logoutUrlPattern", logoutConfig.getLogoutUrlPattern());
		SystemPropertyUtils.setPropertyIfPresent("pac4j.logout.localLogout", logoutConfig.isLocalLogout());
		SystemPropertyUtils.setPropertyIfPresent("pac4j.logout.centralLogout", logoutConfig.isCentralLogout());
		SystemPropertyUtils.setPropertyIfPresent("pac4j.logout.destroySession", logoutConfig.isDestroySession());
	}

	static class Base {

		protected final Pac4jProperties properties;

		protected final Config config;

		public Base(Pac4jProperties properties, Config config) {
			this.properties = properties;
			this.config = config;
		}

	}

	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
	static class Servlet extends Base {

		public Servlet(Pac4jProperties properties, Config config) {
			super(properties, config);
		}

		@Bean
		public SecurityInterceptor securityInterceptor() {
			Pac4jProperties.Filter.Security security = properties.getFilter().getSecurity();
			String clients = properties.getClients() != null ?
					StringUtils.join(properties.getClients(), Pac4jConstants.ELEMENT_SEPARATOR) : null;
			String authorizers = Validate.isNotEmpty(security.getAuthorizers()) ?
					StringUtils.join(security.getAuthorizers(), Pac4jConstants.ELEMENT_SEPARATOR) : null;
			String matchers = Validate.isNotEmpty(security.getMatchers()) ?
					StringUtils.join(security.getMatchers(), Pac4jConstants.ELEMENT_SEPARATOR) : null;
			return new SecurityInterceptor(config, clients, authorizers, matchers);
		}

	}

	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
	static class WebFlux extends Base {

		public WebFlux(Pac4jProperties properties, Config config) {
			super(properties, config);
		}

		@Bean
		public SecurityFilter securityFilter() {
			Pac4jProperties.Filter.Security security = properties.getFilter().getSecurity();
			String clients = properties.getClients() != null ?
					StringUtils.join(properties.getClients(), Pac4jConstants.ELEMENT_SEPARATOR) : null;
			String authorizers = Validate.isNotEmpty(security.getAuthorizers()) ?
					StringUtils.join(security.getAuthorizers(), Pac4jConstants.ELEMENT_SEPARATOR) : null;
			String matchers = Validate.isNotEmpty(security.getMatchers()) ?
					StringUtils.join(security.getMatchers(), Pac4jConstants.ELEMENT_SEPARATOR) : null;
			return new SecurityFilter(config, clients, authorizers, matchers);
		}

	}

}
