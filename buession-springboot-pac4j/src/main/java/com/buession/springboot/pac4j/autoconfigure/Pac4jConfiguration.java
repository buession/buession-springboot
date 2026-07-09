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
 * | Copyright @ 2013-2026 Buession.com Inc.														|
 * +------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.pac4j.autoconfigure;

import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.security.pac4j.spring.reactive.Pac4jWebFluxConfigurerAdapter;
import com.buession.security.pac4j.spring.servlet.Pac4jServletConfigurerAdapter;
import org.pac4j.core.client.Client;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.util.HttpActionHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ReactiveAdapterRegistry;

import java.util.List;

/**
 * Pac4j 基础配置自动加载类
 *
 * @author Yong.Teng
 */
@AutoConfiguration
@EnableConfigurationProperties(Pac4jProperties.class)
public class Pac4jConfiguration {

	protected final static PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

	private final Pac4jProperties properties;

	public Pac4jConfiguration(Pac4jProperties properties) {
		this.properties = properties;
		HttpActionHelper.setAlwaysUse401ForUnauthenticated(properties.isAlwaysUse401ForUnauthenticated());
	}

	/**
	 * 初始化 Pac4j {@link Clients} Bean
	 *
	 * @param clientList
	 * 		客户端 {@link Client} 列表
	 *
	 * @return Pac4j {@link Clients} Bean
	 *
	 * @since 2.0.0
	 */
	@Bean
	@ConditionalOnMissingBean
	public Clients clients(List<Client> clientList) {
		final Clients clients = new Clients(clientList);

		propertyMapper.from(properties::getAjaxRequestResolverClass).as(BeanUtils::instantiateClass)
				.to(clients::setAjaxRequestResolver);

		return clients;
	}

	/**
	 * 初始化 Pac4j {@link Config} Bean
	 *
	 * @param clients
	 *        {@link Clients} 实例
	 *
	 * @return Pac4j {@link Config} Bean
	 */
	@Bean
	@ConditionalOnMissingBean
	public Config config(Clients clients) {
		final Config config = new Config(clients);

		propertyMapper.from(properties::getHttpActionAdapterClass).as(BeanUtils::instantiateClass)
				.to(config::setHttpActionAdapter);

		return config;
	}

	@AutoConfiguration
	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
	static class Servlet extends Pac4jServletConfigurerAdapter {

	}

	@AutoConfiguration
	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
	static class WebFlux extends Pac4jWebFluxConfigurerAdapter {

		public WebFlux(ConfigurableBeanFactory beanFactory) {
			super(beanFactory, new ReactiveAdapterRegistry());
		}

	}

}
