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
 * | Copyright @ 2013-2021 Buession.com Inc.														|
 * +------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.shiro.autoconfigure;

import com.buession.springboot.shiro.ShiroFilters;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.AbstractShiroWebFilterConfiguration;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.servlet.DispatcherType;

/**
 * @author Yong.Teng
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ShiroProperties.class)
@ConditionalOnProperty(prefix = "shiro.web", name = "enabled", matchIfMissing = true)
@Import({Pac4jConfiguration.class})
@ConditionalOnWebApplication
public class ShiroWebFilterConfiguration extends AbstractShiroWebFilterConfiguration {

	protected ShiroProperties properties;

	protected SubjectFactory subjectFactory;

	public ShiroWebFilterConfiguration(ShiroProperties properties, ObjectProvider<SubjectFactory> subjectFactory){
		this.properties = properties;
		this.subjectFactory = subjectFactory.getIfAvailable();

		loginUrl = properties.getLoginUrl();
		successUrl = properties.getSuccessUrl();
		unauthorizedUrl = properties.getUnauthorizedUrl();

		((DefaultSecurityManager) securityManager).setSubjectFactory(this.subjectFactory);
	}

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

}