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
import com.buession.springboot.shiro.ShiroFilters;
import org.apache.shiro.mgt.SubjectFactory;
import org.pac4j.core.config.Config;
import org.pac4j.core.util.Pac4jConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * Auto-configuration pac4j for shiro
 *
 * @author Yong.Teng
 * @since 1.2.0
 */
@Configuration
@EnableConfigurationProperties(ShiroProperties.class)
@AutoConfigureBefore(ShiroWebFilterConfiguration.class)
@ConditionalOnClass(Config.class)
@Import({com.buession.springboot.pac4j.autoconfigure.Pac4jConfiguration.class})
public class Pac4jConfiguration {

	@Autowired
	protected ShiroProperties shiroProperties;

	@Bean
	@ConditionalOnMissingBean
	public SubjectFactory subjectFactory(){
		return new Pac4jSubjectFactory();
	}

	@Bean
	public ShiroFilters pac4jFilters(Config pac4jConfig){
		ShiroProperties.Pac4j pac4j = shiroProperties.getPac4j();

		Map<String, Filter> filters = new HashMap<>(3);

		filters.put("securityFilter", securityFilter(pac4jConfig, pac4j));
		filters.put("callbackFilter", callbackFilter(pac4jConfig, pac4j));
		filters.put("logoutFilter", logoutFilter(pac4jConfig, pac4j));

		return new ShiroFilters(filters);
	}

	protected static SecurityFilter securityFilter(final Config pac4jConfig, final ShiroProperties.Pac4j pac4j){
		final SecurityFilter filter = new SecurityFilter(pac4jConfig);

		if(pac4j.getClients() != null){
			filter.setClients(ArrayUtils.toString(pac4j.getClients(), Pac4jConstants.ELEMENT_SEPARATOR));
		}

		filter.setMultiProfile(pac4j.isMultiProfile());

		if(Validate.isNotEmpty(pac4j.getAuthorizers())){
			filter.setAuthorizers(ArrayUtils.toString(pac4j.getAuthorizers(), Pac4jConstants.ELEMENT_SEPARATOR));
		}

		if(Validate.isNotEmpty(pac4j.getMatchers())){
			filter.setMatchers(ArrayUtils.toString(pac4j.getMatchers(), Pac4jConstants.ELEMENT_SEPARATOR));
		}

		return filter;
	}

	protected static CallbackFilter callbackFilter(final Config pac4jConfig, final ShiroProperties.Pac4j pac4j){
		final CallbackFilter callbackFilter = new CallbackFilter(pac4jConfig);

		callbackFilter.setDefaultUrl(pac4j.getDefaultUrl());
		callbackFilter.setMultiProfile(pac4j.isMultiProfile());
		callbackFilter.setDefaultClient(pac4j.getDefaultClient());
		callbackFilter.setSaveInSession(pac4j.isSaveInSession());

		return callbackFilter;
	}

	protected static LogoutFilter logoutFilter(final Config pac4jConfig, final ShiroProperties.Pac4j pac4j){
		final LogoutFilter logoutFilter = new LogoutFilter(pac4jConfig);

		logoutFilter.setDefaultUrl(pac4j.getLogoutRedirectUrl());
		logoutFilter.setLogoutUrlPattern(pac4j.getLogoutUrlPattern());
		logoutFilter.setLocalLogout(pac4j.isLocalLogout());
		logoutFilter.setCentralLogout(pac4j.isCentralLogout());

		return logoutFilter;
	}

}
