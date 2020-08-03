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
package com.buession.springboot.shiro.autoconfigure;

import com.buession.core.utils.ArrayUtils;
import com.buession.core.validator.Validate;
import com.buession.security.pac4j.filter.CallbackFilter;
import com.buession.security.pac4j.filter.LogoutFilter;
import com.buession.security.pac4j.filter.SecurityFilter;
import com.buession.security.pac4j.subject.Pac4jSubjectFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.AbstractShiroWebFilterConfiguration;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.pac4j.core.config.Config;
import org.pac4j.core.util.Pac4jConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yong.Teng
 */
@Configuration
@EnableConfigurationProperties(ShiroProperties.class)
@ConditionalOnProperty(name = "shiro.web.enabled", matchIfMissing = true)
@ConditionalOnWebApplication
public class ShiroWebFilterConfiguration extends AbstractShiroWebFilterConfiguration {

	@Autowired
	protected ShiroProperties shiroProperties;

	@PostConstruct
	public void initialize(){
		loginUrl = shiroProperties.getLoginUrl();
		successUrl = shiroProperties.getSuccessUrl();
		unauthorizedUrl = shiroProperties.getUnauthorizedUrl();
	}

	@Bean(name = "shiroFilterRegistrationBean")
	@ConditionalOnClass(name = {"org.pac4j.core.config.Config"})
	@ConditionalOnMissingBean
	public FilterRegistrationBean shiroFilterRegistrationBean(Config pac4jConfig) throws Exception{
		FilterRegistrationBean<AbstractShiroFilter> filterRegistrationBean = new FilterRegistrationBean<>();

		filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD,
				DispatcherType.INCLUDE, DispatcherType.ERROR);
		filterRegistrationBean.setFilter((AbstractShiroFilter) shiroFilterFactoryBean(pac4jConfig).getObject());
		filterRegistrationBean.setOrder(1);

		return filterRegistrationBean;
	}

	@Bean(name = "shiroFilterRegistrationBean")
	@ConditionalOnMissingBean
	public FilterRegistrationBean shiroFilterRegistrationBean() throws Exception{
		FilterRegistrationBean<AbstractShiroFilter> filterRegistrationBean = new FilterRegistrationBean<>();

		filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD,
				DispatcherType.INCLUDE, DispatcherType.ERROR);
		filterRegistrationBean.setFilter((AbstractShiroFilter) shiroFilterFactoryBean().getObject());
		filterRegistrationBean.setOrder(1);

		return filterRegistrationBean;
	}

	protected ShiroFilterFactoryBean shiroFilterFactoryBean(Config pac4jConfig){
		((DefaultSecurityManager) securityManager).setSubjectFactory(new Pac4jSubjectFactory());

		ShiroFilterFactoryBean filterFactoryBean = super.shiroFilterFactoryBean();

		filterFactoryBean.setFilters(shiroFilters(pac4jConfig));

		return filterFactoryBean;
	}

	@Override
	protected ShiroFilterFactoryBean shiroFilterFactoryBean(){
		return super.shiroFilterFactoryBean();
	}

	protected SecurityFilter securityFilter(final Config pac4jConfig){
		SecurityFilter filter = new SecurityFilter(pac4jConfig);

		if(shiroProperties.getClients() != null){
			filter.setClients(ArrayUtils.toString(shiroProperties.getClients(), Pac4jConstants.ELEMENT_SEPARATOR));
		}

		filter.setMultiProfile(shiroProperties.isMultiProfile());

		if(Validate.isEmpty(shiroProperties.getAuthorizers()) == false){
			filter.setAuthorizers(ArrayUtils.toString(shiroProperties.getAuthorizers(),
					Pac4jConstants.ELEMENT_SEPARATOR));
		}

		return filter;
	}

	protected Map<String, Filter> shiroFilters(final Config pac4jConfig){
		Map<String, Filter> filters = new HashMap<>(3);

		filters.put("securityFilter", securityFilter(pac4jConfig));

		CallbackFilter callbackFilter = new CallbackFilter(pac4jConfig);
		filters.put("callbackFilter", callbackFilter);

		LogoutFilter logoutFilter = new LogoutFilter(pac4jConfig);
		logoutFilter.setCentralLogout(true);
		logoutFilter.setDefaultUrl(successUrl);
		filters.put("logoutFilter", logoutFilter);

		return filters;
	}

}
