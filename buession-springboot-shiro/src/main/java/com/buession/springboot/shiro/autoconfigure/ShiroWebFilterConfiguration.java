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
import com.buession.security.pac4j.filter.CallbackFilter;
import com.buession.security.pac4j.filter.LogoutFilter;
import com.buession.security.pac4j.filter.SecurityFilter;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.spring.web.config.AbstractShiroWebFilterConfiguration;
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
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ShiroProperties.class)
@ConditionalOnProperty(prefix = "shiro.web", name = "enabled", matchIfMissing = true)
@Import({Pac4jConfiguration.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
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
	@ConditionalOnMissingBean
	public SecurityFilter securityFilter(Config config){
		final SecurityFilter filter = new SecurityFilter(config);
		ShiroProperties.Pac4j pac4j = properties.getPac4j();

		if(pac4j.getClients() != null){
			filter.setClients(Arrays.toString(pac4j.getClients(), Pac4jConstants.ELEMENT_SEPARATOR));
		}

		filter.setMultiProfile(pac4j.isMultiProfile());

		if(Validate.isNotEmpty(pac4j.getAuthorizers())){
			filter.setAuthorizers(Arrays.toString(pac4j.getAuthorizers(), Pac4jConstants.ELEMENT_SEPARATOR));
		}

		if(Validate.isNotEmpty(pac4j.getMatchers())){
			filter.setMatchers(Arrays.toString(pac4j.getMatchers(), Pac4jConstants.ELEMENT_SEPARATOR));
		}

		return filter;
	}

	@Bean(name = "callbackFilter")
	@ConditionalOnMissingBean
	public CallbackFilter callbackFilter(Config config){
		final CallbackFilter callbackFilter = new CallbackFilter(config);
		ShiroProperties.Pac4j pac4j = properties.getPac4j();

		callbackFilter.setDefaultUrl(pac4j.getDefaultUrl());
		callbackFilter.setMultiProfile(pac4j.isMultiProfile());
		callbackFilter.setDefaultClient(pac4j.getDefaultClient());
		callbackFilter.setSaveInSession(pac4j.isSaveInSession());

		return callbackFilter;
	}

	@Bean(name = "logoutFilter")
	@ConditionalOnMissingBean
	public LogoutFilter logoutFilter(Config config){
		final LogoutFilter logoutFilter = new LogoutFilter(config);
		ShiroProperties.Pac4j pac4j = properties.getPac4j();

		logoutFilter.setDefaultUrl(pac4j.getLogoutRedirectUrl());
		logoutFilter.setLogoutUrlPattern(pac4j.getLogoutUrlPattern());
		logoutFilter.setLocalLogout(pac4j.isLocalLogout());
		logoutFilter.setCentralLogout(pac4j.isCentralLogout());

		return logoutFilter;
	}

	/*
	@Bean(name = "shiroFilterRegistrationBean")
	@ConditionalOnBean(ShiroFilters.class)
	@ConditionalOnMissingBean
	public FilterRegistrationBean shiroFilterRegistrationBean(ShiroFilters shiroFilters) throws Exception{
		FilterRegistrationBean<AbstractShiroFilter> filterRegistrationBean = createShiroFilterRegistrationBean();

		ShiroFilterFactoryBean shiroFilterFactoryBean = super.shiroFilterFactoryBean();
		shiroFilterFactoryBean.setFilters(shiroFilters.getFilters());

		filterRegistrationBean.setFilter((AbstractShiroFilter) shiroFilterFactoryBean.getObject());

		return filterRegistrationBean;
	}

	@Bean(name = "shiroFilterRegistrationBean")
	@ConditionalOnMissingBean
	public FilterRegistrationBean shiroFilterRegistrationBean() throws Exception{
		FilterRegistrationBean<AbstractShiroFilter> filterRegistrationBean = createShiroFilterRegistrationBean();

		filterRegistrationBean.setFilter((AbstractShiroFilter) shiroFilterFactoryBean().getObject());

		return filterRegistrationBean;
	}

	protected FilterRegistrationBean<AbstractShiroFilter> createShiroFilterRegistrationBean(){
		FilterRegistrationBean<AbstractShiroFilter> filterRegistrationBean = new FilterRegistrationBean<>();

		filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ERROR);
		filterRegistrationBean.setOrder(1);

		return filterRegistrationBean;
	}
	 */

}