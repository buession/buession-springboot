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
package com.buession.springboot.cas.autoconfigure;

import com.buession.security.pac4j.Constants;
import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.client.rest.CasRestFormClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yong.Teng
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CasProperties.class)
@ConditionalOnClass({org.pac4j.cas.config.CasConfiguration.class})
@Deprecated
public class CasConfiguration {

	@Autowired
	private CasProperties casProperties;

	private final static Logger logger = LoggerFactory.getLogger(CasConfiguration.class);

	public CasConfiguration(){
		logger.warn("com.buession.springboot.pac4j.autoconfigure.Pac4jConfiguration instead of {}.",
				CasConfiguration.class.getName());
	}

	@Bean(name = "casConfiguration")
	@ConditionalOnMissingBean
	@Deprecated
	public org.pac4j.cas.config.CasConfiguration casConfiguration(){
		org.pac4j.cas.config.CasConfiguration casConfiguration =
				new org.pac4j.cas.config.CasConfiguration(casProperties.getLoginUrl(), casProperties.getPrefixUrl());

		casConfiguration.setProtocol(casProperties.getProtocol());

		return casConfiguration;
	}

	@Bean(name = "casClient")
	@ConditionalOnProperty(prefix = "pac4j.client", name = Constants.CAS, havingValue = "on")
	@DeprecatedConfigurationProperty(reason = "规范名称", replacement = "spring.pac4j.client.cas")
	@ConditionalOnClass(name = "org.pac4j.cas.client.CasClient")
	@ConditionalOnMissingBean
	@Deprecated
	public CasClient casClient(org.pac4j.cas.config.CasConfiguration casConfiguration){
		CasClient casClient = new CasClient();

		casClient.setName(Constants.CAS);
		casClient.setConfiguration(casConfiguration);
		casClient.setCallbackUrl(casProperties.getCallbackUrl());

		return casClient;
	}

	@Bean(name = "casRestFormClient")
	@ConditionalOnProperty(prefix = "pac4j.client", name = Constants.REST, havingValue = "on")
	@DeprecatedConfigurationProperty(reason = "规范名称", replacement = "spring.pac4j.client.rest")
	@ConditionalOnClass(name = "org.pac4j.cas.client.rest.CasRestFormClient")
	@ConditionalOnMissingBean
	@Deprecated
	public CasRestFormClient deprecatedCasRestFormClient(org.pac4j.cas.config.CasConfiguration casConfiguration){
		CasRestFormClient casRestFormClient = new CasRestFormClient();

		casRestFormClient.setName(Constants.REST);
		casRestFormClient.setConfiguration(casConfiguration);

		return casRestFormClient;
	}

}
