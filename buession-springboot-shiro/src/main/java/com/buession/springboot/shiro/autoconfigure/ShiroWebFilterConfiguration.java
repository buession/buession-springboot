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
package com.buession.springboot.shiro.autoconfigure;

import com.buession.springboot.shiro.ShiroFilters;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.AbstractShiroWebFilterConfiguration;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import java.util.List;

/**
 * @author Yong.Teng
 * @since 2.0.0
 */
@Configuration
@EnableConfigurationProperties(ShiroProperties.class)
@ConditionalOnProperty(prefix = ShiroProperties.PREFIX, name = "web.enabled", matchIfMissing = true)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ShiroWebFilterConfiguration extends AbstractShiroWebFilterConfiguration {

	public final static String REGISTRATION_BEAN_NAME = "filterShiroFilterRegistrationBean";

	public final static String FILTER_NAME = "shiroFilter";

	protected ShiroProperties properties;

	private ShiroFilters shiroFilters;

	public ShiroWebFilterConfiguration(ShiroProperties properties, ObjectProvider<ShiroFilters> shiroFilters){
		this.properties = properties;
		this.shiroFilters = shiroFilters.getIfAvailable();

		if(this.properties.getLoginUrl() != null){
			this.loginUrl = this.properties.getLoginUrl();
		}

		if(this.properties.getSuccessUrl() != null){
			this.successUrl = this.properties.getSuccessUrl();
		}

		if(this.properties.getUnauthorizedUrl() != null){
			this.unauthorizedUrl = this.properties.getUnauthorizedUrl();
		}
	}

	@Override
	protected ShiroFilterFactoryBean shiroFilterFactoryBean(){
		ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();

		filterFactoryBean.setLoginUrl(loginUrl);
		filterFactoryBean.setSuccessUrl(successUrl);
		filterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);

		filterFactoryBean.setSecurityManager(securityManager);
		filterFactoryBean.setGlobalFilters(globalFilters());
		filterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition.getFilterChainMap());
		filterFactoryBean.setFilters(shiroFilters.getFilters());

		return filterFactoryBean;
	}

	@Bean(name = REGISTRATION_BEAN_NAME)
	@ConditionalOnMissingBean(name = REGISTRATION_BEAN_NAME)
	protected FilterRegistrationBean<AbstractShiroFilter> filterShiroFilterRegistrationBean() throws Exception{

		FilterRegistrationBean<AbstractShiroFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD,
				DispatcherType.INCLUDE, DispatcherType.ERROR);
		filterRegistrationBean.setFilter((AbstractShiroFilter) shiroFilterFactoryBean().getObject());
		filterRegistrationBean.setName(FILTER_NAME);
		filterRegistrationBean.setOrder(1);

		return filterRegistrationBean;
	}

	@Bean(name = "globalFilters")
	@ConditionalOnMissingBean
	@Override
	protected List<String> globalFilters(){
		return super.globalFilters();
	}

}
