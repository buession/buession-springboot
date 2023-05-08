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
 * | Copyright @ 2013-2023 Buession.com Inc.														|
 * +------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.pac4j.autoconfigure;

import com.buession.security.pac4j.spring.reactive.Pac4jWebFluxConfigurerAdapter;
import com.buession.security.pac4j.spring.servlet.Pac4jWebMvcConfigurerAdapter;
import org.pac4j.core.client.Client;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.http.adapter.HttpActionAdapter;
import org.pac4j.core.http.ajax.AjaxRequestResolver;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ReactiveAdapterRegistry;

import java.util.List;

/**
 * Pac4j 基础配置自动加载类
 *
 * @author Yong.Teng
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(Pac4jProperties.class)
public class Pac4jConfiguration {

	private final Pac4jProperties properties;

	public Pac4jConfiguration(Pac4jProperties properties){
		this.properties = properties;
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
	public Clients clients(List<Client> clientList){
		final Clients clients = new Clients(clientList);

		if(properties.getAjaxRequestResolverClass() != null){
			AjaxRequestResolver ajaxRequestResolver = BeanUtils.instantiateClass(
					properties.getAjaxRequestResolverClass());

			clients.setAjaxRequestResolver(ajaxRequestResolver);
		}

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
	public Config config(Clients clients){
		final Config config = Config.INSTANCE;

		config.setClients(clients);

		if(properties.getHttpActionAdapterClass() != null){
			HttpActionAdapter<?, ? extends WebContext> httpActionAdapter = BeanUtils.instantiateClass(
					properties.getHttpActionAdapterClass());
			config.setHttpActionAdapter(httpActionAdapter);
		}

		return config;
	}

	/**
	 * @since 2.1.0
	 */
	@Configuration(proxyBeanMethods = false)
	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
	static class ServletPac4jConfigurerAdapterConfiguration {

		@Bean
		@ConditionalOnMissingBean
		public Pac4jWebMvcConfigurerAdapter pac4jWebMvcConfigurerAdapter(){
			return new Pac4jWebMvcConfigurerAdapter();
		}

	}

	/**
	 * @since 2.1.0
	 */
	@Configuration(proxyBeanMethods = false)
	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
	static class WebFluxPac4jConfigurerAdapterConfiguration {

		private final ConfigurableApplicationContext context;

		public WebFluxPac4jConfigurerAdapterConfiguration(ObjectProvider<ConfigurableApplicationContext> context){
			this.context = context.getIfAvailable();
		}

		@Bean
		@ConditionalOnMissingBean
		public Pac4jWebFluxConfigurerAdapter pac4jWebFluxConfigurerAdapter(
				@Qualifier("webFluxAdapterRegistry") ReactiveAdapterRegistry reactiveAdapterRegistry){
			return new Pac4jWebFluxConfigurerAdapter(context.getBeanFactory(), reactiveAdapterRegistry);
		}

	}

}
