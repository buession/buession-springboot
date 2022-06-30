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
package com.buession.springboot.pac4j.autoconfigure;

import com.buession.core.collect.Arrays;
import com.buession.core.validator.Validate;
import io.buji.pac4j.filter.CallbackFilter;
import io.buji.pac4j.filter.LogoutFilter;
import io.buji.pac4j.filter.SecurityFilter;
import org.pac4j.core.config.Config;
import org.pac4j.core.util.Pac4jConstants;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Yong.Teng
 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(Pac4jProperties.class)
@ConditionalOnProperty(prefix = Pac4jProperties.PREFIX +
		".filter", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@Import({Pac4jConfiguration.class})
public class Pac4jWebFilterConfiguration {

	private Pac4jProperties properties;

	private Config config;

	public Pac4jWebFilterConfiguration(Pac4jProperties properties, ObjectProvider<Config> config){
		this.properties = properties;
		this.config = config.getIfAvailable();
	}

	@Bean(name = "securityFilter")
	@ConditionalOnMissingBean
	public SecurityFilter securityFilter(){
		final SecurityFilter securityFilter = new SecurityFilter();

		securityFilter.setConfig(config);

		if(properties.getClients() != null){
			securityFilter.setClients(Arrays.toString(properties.getClients(), Pac4jConstants.ELEMENT_SEPARATOR));
		}

		securityFilter.setMultiProfile(properties.isMultiProfile());

		Pac4jProperties.Filter.Security securityConfig = properties.getFilter().getSecurity();

		if(Validate.isNotEmpty(securityConfig.getAuthorizers())){
			securityFilter.setAuthorizers(
					Arrays.toString(securityConfig.getAuthorizers(), Pac4jConstants.ELEMENT_SEPARATOR));
		}

		if(Validate.isNotEmpty(securityConfig.getMatchers())){
			securityFilter.setMatchers(Arrays.toString(securityConfig.getMatchers(), Pac4jConstants.ELEMENT_SEPARATOR));
		}

		return securityFilter;
	}

	@Bean(name = "callbackFilter")
	@ConditionalOnMissingBean
	public CallbackFilter callbackFilter(){
		final CallbackFilter callbackFilter = new CallbackFilter();

		Pac4jProperties.Filter.Callback callbackConfig = properties.getFilter().getCallback();

		callbackFilter.setConfig(config);
		callbackFilter.setDefaultUrl(callbackConfig.getDefaultUrl());
		callbackFilter.setMultiProfile(properties.isMultiProfile());
		callbackFilter.setDefaultClient(properties.getDefaultClient());
		callbackFilter.setSaveInSession(properties.isSaveInSession());

		return callbackFilter;
	}

	@Bean(name = "logoutFilter")
	@ConditionalOnMissingBean
	public LogoutFilter logoutFilter(){
		final LogoutFilter logoutFilter = new LogoutFilter();

		Pac4jProperties.Filter.Logout logoutConfig = properties.getFilter().getLogout();

		logoutFilter.setConfig(config);
		logoutFilter.setDefaultUrl(logoutConfig.getDefaultUrl());
		logoutFilter.setLogoutUrlPattern(logoutConfig.getLogoutUrlPattern());
		logoutFilter.setLocalLogout(logoutConfig.isLocalLogout());
		logoutFilter.setCentralLogout(logoutConfig.isCentralLogout());

		return logoutFilter;
	}

}
