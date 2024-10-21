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
 * | Copyright @ 2013-2024 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.shiro.autoconfigure;

import com.buession.core.utils.SystemPropertyUtils;
import com.buession.core.validator.Validate;
import com.buession.springboot.shiro.core.ShiroFilter;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.AbstractShiroWebFilterConfiguration;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.DispatcherType;
import java.util.List;

/**
 * @author Yong.Teng
 * @since 2.0.0
 */
@AutoConfiguration(after = {ShiroWebConfiguration.class})
@EnableConfigurationProperties(ShiroProperties.class)
@ConditionalOnProperty(prefix = ShiroProperties.PREFIX, name = "web.enabled", matchIfMissing = true)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ShiroWebFilterConfiguration extends AbstractShiroWebFilterConfiguration {

	public ShiroWebFilterConfiguration(ShiroProperties properties) {
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

	@Bean(name = "filterShiroFilterRegistrationBean")
	@ConditionalOnMissingBean(name = "filterShiroFilterRegistrationBean")
	@ConditionalOnBean({ShiroFilter.class})
	protected FilterRegistrationBean<AbstractShiroFilter> filterShiroFilterRegistrationBean(ShiroFilter shiroFilter)
			throws Exception {
		FilterRegistrationBean<AbstractShiroFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		ShiroFilterFactoryBean shiroFilterFactoryBean = super.shiroFilterFactoryBean();

		if(shiroFilterFactoryBean.getFilters() == null){
			shiroFilterFactoryBean.setFilters(shiroFilter.getFilters());
		}else{
			shiroFilterFactoryBean.getFilters().putAll(shiroFilter.getFilters());
		}
		filterRegistrationBean.setName("shiroFilter");
		filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD,
				DispatcherType.INCLUDE, DispatcherType.ERROR);
		filterRegistrationBean.setFilter(shiroFilterFactoryBean.getObject());
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
