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
package com.buession.springboot.shiro.autoconfigure;

import com.buession.core.collect.Arrays;
import com.buession.core.utils.SystemPropertyUtils;
import com.buession.core.validator.Validate;
import io.buji.pac4j.filter.CallbackFilter;
import io.buji.pac4j.filter.LogoutFilter;
import io.buji.pac4j.filter.SecurityFilter;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.spring.web.config.AbstractShiroWebFilterConfiguration;
import org.pac4j.core.config.Config;
import org.pac4j.core.util.Pac4jConstants;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yong.Teng
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ShiroProperties.class)
@ConditionalOnProperty(prefix = "shiro.web", name = "enabled", matchIfMissing = true)
@AutoConfigureBefore({org.apache.shiro.spring.web.config.ShiroWebFilterConfiguration.class,
		org.apache.shiro.spring.config.web.autoconfigure.ShiroWebFilterConfiguration.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@Import({com.buession.springboot.pac4j.autoconfigure.Pac4jConfiguration.class})
public class ShiroWebFilterConfiguration extends AbstractShiroWebFilterConfiguration {

	protected ShiroProperties properties;

	protected SubjectFactory subjectFactory;

	public ShiroWebFilterConfiguration(ShiroProperties properties, ObjectProvider<SubjectFactory> subjectFactory,
									   ObjectProvider<SecurityManager> securityManager){
		this.properties = properties;
		this.subjectFactory = subjectFactory.getIfAvailable();
		this.securityManager = securityManager.getIfAvailable();

		SystemPropertyUtils.setPropertyIfPresent("shiro.loginUrl", properties.getLoginUrl());
		SystemPropertyUtils.setPropertyIfPresent("shiro.successUrl", properties.getSuccessUrl());
		SystemPropertyUtils.setPropertyIfPresent("shiro.unauthorizedUrl", properties.getUnauthorizedUrl());

		((DefaultSecurityManager) this.securityManager).setSubjectFactory(this.subjectFactory);
	}

	@Bean(name = "securityFilter")
	@ConditionalOnClass(Config.class)
	@ConditionalOnMissingBean
	public SecurityFilter securityFilter(Config config){
		final SecurityFilter securityFilter = new SecurityFilter();

		ShiroProperties.Pac4j pac4j = properties.getPac4j();

		securityFilter.setConfig(config);

		if(pac4j.getClients() != null){
			securityFilter.setClients(Arrays.toString(pac4j.getClients(), Pac4jConstants.ELEMENT_SEPARATOR));
		}

		securityFilter.setMultiProfile(pac4j.isMultiProfile());

		if(Validate.isNotEmpty(pac4j.getAuthorizers())){
			securityFilter.setAuthorizers(Arrays.toString(pac4j.getAuthorizers(), Pac4jConstants.ELEMENT_SEPARATOR));
		}

		if(Validate.isNotEmpty(pac4j.getMatchers())){
			securityFilter.setMatchers(Arrays.toString(pac4j.getMatchers(), Pac4jConstants.ELEMENT_SEPARATOR));
		}

		return securityFilter;
	}

	@Bean(name = "callbackFilter")
	@ConditionalOnClass(Config.class)
	@ConditionalOnMissingBean
	public CallbackFilter callbackFilter(Config config){
		final CallbackFilter callbackFilter = new CallbackFilter();

		ShiroProperties.Pac4j pac4j = properties.getPac4j();

		callbackFilter.setConfig(config);
		callbackFilter.setDefaultUrl(pac4j.getDefaultUrl());
		callbackFilter.setMultiProfile(pac4j.isMultiProfile());
		callbackFilter.setDefaultClient(pac4j.getDefaultClient());
		callbackFilter.setSaveInSession(pac4j.isSaveInSession());

		return callbackFilter;
	}

	@Bean(name = "logoutFilter")
	@ConditionalOnClass(Config.class)
	@ConditionalOnMissingBean
	public LogoutFilter logoutFilter(Config config){
		final LogoutFilter logoutFilter = new LogoutFilter();

		ShiroProperties.Pac4j pac4j = properties.getPac4j();

		logoutFilter.setConfig(config);
		logoutFilter.setDefaultUrl(pac4j.getLogoutRedirectUrl());
		logoutFilter.setLogoutUrlPattern(pac4j.getLogoutUrlPattern());
		logoutFilter.setLocalLogout(pac4j.isLocalLogout());
		logoutFilter.setCentralLogout(pac4j.isCentralLogout());

		return logoutFilter;
	}

	@Bean
	public Map<String, Filter> filterMap(){
		Map<String, Filter> filters = new HashMap<>(3);

		/*
		SecurityFilter realSecurityFilter = securityFilter.getIfAvailable();
		if(realSecurityFilter != null){
			filters.put("securityFilter", realSecurityFilter);
		}

		CallbackFilter realCallbackFilter = callbackFilter.getIfAvailable();
		if(realCallbackFilter != null){
			filters.put("callbackFilter", realCallbackFilter);
		}

		LogoutFilter realLogoutFilter = logoutFilter.getIfAvailable();
		if(realLogoutFilter != null){
			filters.put("logoutFilter", realLogoutFilter);
		}

		 */

		return filters;
	}

}