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

import com.buession.security.pac4j.subject.Pac4jSubjectFactory;
import com.buession.springboot.shiro.ShiroFilters;
import org.apache.shiro.mgt.SubjectFactory;
import org.pac4j.core.config.Config;
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
@Configuration(proxyBeanMethods = false)
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
		Pac4j pac4j = shiroProperties.getPac4j();

		FilterCreator filterCreator = new FilterCreator(pac4jConfig, pac4j);
		Map<String, Filter> filters = new HashMap<>(3);

		filters.put("securityFilter", filterCreator.createSecurityFilter());
		filters.put("callbackFilter", filterCreator.createCallbackFilter());
		filters.put("logoutFilter", filterCreator.createLogoutFilter());

		return new ShiroFilters(filters);
	}

}
