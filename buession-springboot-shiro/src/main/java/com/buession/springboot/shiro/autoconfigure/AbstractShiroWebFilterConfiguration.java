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
 * | Copyright @ 2013-2020 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.shiro.autoconfigure;

import com.buession.core.utils.ArrayUtils;
import com.buession.core.validator.Validate;
import com.buession.security.pac4j.filter.SecurityFilter;
import com.buession.security.pac4j.subject.Pac4jSubjectFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.pac4j.core.config.Config;
import org.pac4j.core.context.Pac4jConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.Map;

/**
 * @author Yong.Teng
 */
public abstract class AbstractShiroWebFilterConfiguration extends org.apache.shiro.spring.web.config
		.AbstractShiroWebFilterConfiguration {

	@Autowired
	protected ShiroProperties shiroProperties;

	@Autowired
	protected Config pac4jConfig;

	@Bean
	@Override
	public ShiroFilterFactoryBean shiroFilterFactoryBean(){
		// 把 subject 对象设为 subjectFactory
		// 由于 cas 代理了用户，所以必须通过 ca 进行创建对象
		((DefaultSecurityManager) securityManager).setSubjectFactory(new Pac4jSubjectFactory());

		ShiroFilterFactoryBean filterFactoryBean = super.shiroFilterFactoryBean();

		filterFactoryBean.setLoginUrl(shiroProperties.getLoginUrl());
		filterFactoryBean.setSuccessUrl(shiroProperties.getSuccessUrl());
		filterFactoryBean.setUnauthorizedUrl(shiroProperties.getUnauthorizedUrl());
		filterFactoryBean.setFilters(shiroFilters());

		return filterFactoryBean;
	}

	@Bean(name = "shiroFilterRegistrationBean")
	@ConditionalOnMissingBean
	public FilterRegistrationBean shiroFilterRegistrationBean(ShiroFilterFactoryBean shiroFilterFactoryBean) throws
			Exception{
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();

		filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType
				.INCLUDE, DispatcherType.ERROR);
		filterRegistrationBean.setFilter((AbstractShiroFilter) shiroFilterFactoryBean.getObject());
		filterRegistrationBean.setOrder(1);

		return filterRegistrationBean;
	}

	protected SecurityFilter securityFilter(){
		SecurityFilter filter = new SecurityFilter(pac4jConfig);

		if(shiroProperties.getClients() != null){
			filter.setClients(ArrayUtils.toString(shiroProperties.getClients(), Pac4jConstants.ELEMENT_SEPARATOR));
		}

		filter.setMultiProfile(shiroProperties.isMultiProfile());

		if(Validate.isEmpty(shiroProperties.getAuthorizers()) == false){
			filter.setAuthorizers(ArrayUtils.toString(shiroProperties.getAuthorizers(), Pac4jConstants
					.ELEMENT_SEPARATOR));
		}

		return filter;
	}

	protected abstract Map<String, Filter> shiroFilters();

}
