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
 * | Copyright @ 2013-2023 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.shiro.autoconfigure;

import com.buession.core.utils.SystemPropertyUtils;
import com.buession.core.validator.Validate;
import com.buession.springboot.pac4j.filter.Pac4jFilter;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.AbstractShiroWebFilterConfiguration;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
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
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ShiroProperties.class)
@ConditionalOnProperty(prefix = ShiroProperties.PREFIX, name = "web.enabled", matchIfMissing = true)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@AutoConfigureAfter({ShiroWebConfiguration.class})
public class ShiroWebFilterConfiguration extends AbstractShiroWebFilterConfiguration {

	private final Pac4jFilter pac4jFilter;

	public ShiroWebFilterConfiguration(ShiroProperties properties, ObjectProvider<Pac4jFilter> pac4jFilter) {
		this.pac4jFilter = pac4jFilter.getIfAvailable();

		if(Validate.hasText(properties.getLoginUrl())){
			SystemPropertyUtils.setProperty("shiro.loginUrl", properties.getLoginUrl());
		}

		if(Validate.hasText(properties.getSuccessUrl())){
			SystemPropertyUtils.setProperty("shiro.successUrl", properties.getSuccessUrl());
		}

		if(Validate.hasText(properties.getUnauthorizedUrl())){
			SystemPropertyUtils.setProperty("shiro.unauthorizedUrl", properties.getUnauthorizedUrl());
		}
	}

	@Override
	protected ShiroFilterFactoryBean shiroFilterFactoryBean() {
		ShiroFilterFactoryBean filterFactoryBean = super.shiroFilterFactoryBean();

		filterFactoryBean.setFilters(pac4jFilter.getFilters());

		return filterFactoryBean;
	}

	@Bean(name = "filterShiroFilterRegistrationBean")
	@ConditionalOnMissingBean(name = "filterShiroFilterRegistrationBean")
	protected FilterRegistrationBean<AbstractShiroFilter> filterShiroFilterRegistrationBean() throws Exception {
		FilterRegistrationBean<AbstractShiroFilter> filterRegistrationBean = new FilterRegistrationBean<>();

		filterRegistrationBean.setName("shiroFilter");
		filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD,
				DispatcherType.INCLUDE, DispatcherType.ERROR);
		filterRegistrationBean.setFilter(shiroFilterFactoryBean().getObject());
		filterRegistrationBean.setOrder(1);

		return filterRegistrationBean;
	}

	@Bean(name = "globalFilters")
	@ConditionalOnMissingBean
	@Override
	protected List<String> globalFilters() {
		return super.globalFilters();
	}

}
