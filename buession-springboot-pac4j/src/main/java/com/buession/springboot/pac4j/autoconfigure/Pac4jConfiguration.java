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
 * | Copyright @ 2013-2022 Buession.com Inc.														|
 * +------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.pac4j.autoconfigure;

import com.buession.security.pac4j.spring.servlet.Pac4jWebMvcConfigurerAdapter;
import com.buession.springboot.cas.autoconfigure.CasConfiguration;
import com.buession.springboot.cas.autoconfigure.CasProperties;
import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.client.CasProxyReceptor;
import org.pac4j.cas.client.rest.CasRestBasicAuthClient;
import org.pac4j.cas.client.rest.CasRestFormClient;
import org.pac4j.core.client.Client;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.oauth.client.CasOAuthWrapperClient;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

/**
 * @author Yong.Teng
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CasProperties.class)
@ConditionalOnBean({CasConfiguration.class})
@ConditionalOnClass({Config.class})
public class Pac4jConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public Config pac4jConfig(){
		return new Config(new ArrayList<>());
	}

	/**
	 * 初始化 {@link Pac4jWebMvcConfigurerAdapter} Bean
	 *
	 * @return {@link Pac4jWebMvcConfigurerAdapter}
	 *
	 * @since 1.2.2
	 */
	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
	public Pac4jWebMvcConfigurerAdapter pac4jWebMvcConfigurerAdapter(){
		return new Pac4jWebMvcConfigurerAdapter();
	}

	abstract static class AbstractClientConfiguration<C extends Client> {

		protected AbstractClientConfiguration(ObjectProvider<Config> config, ObjectProvider<C> client){
			Clients clients = config.getIfAvailable().getClients();

			if(clients != null){
				if(clients.getClients() == null){
					clients.setClients(client.getIfAvailable());
				}else{
					clients.getClients().add(client.getIfAvailable());
				}
			}
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnBean({CasClient.class})
	class CasClientConfiguration extends AbstractClientConfiguration<CasClient> {

		public CasClientConfiguration(ObjectProvider<Config> config, ObjectProvider<CasClient> casClient){
			super(config, casClient);
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnBean({CasRestFormClient.class})
	class CasRestFormClientConfiguration extends AbstractClientConfiguration<CasRestFormClient> {

		public CasRestFormClientConfiguration(ObjectProvider<Config> config, ObjectProvider<CasRestFormClient> casRestFormClient){
			super(config, casRestFormClient);
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnBean({CasRestBasicAuthClient.class})
	class CasRestBasicAuthClientConfiguration extends AbstractClientConfiguration<CasRestBasicAuthClient> {

		public CasRestBasicAuthClientConfiguration(ObjectProvider<Config> config, ObjectProvider<CasRestBasicAuthClient> casRestBasicAuthClient){
			super(config, casRestBasicAuthClient);
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnBean({CasOAuthWrapperClient.class})
	class CasOAuthWrapperClientConfiguration extends AbstractClientConfiguration<CasOAuthWrapperClient> {

		public CasOAuthWrapperClientConfiguration(ObjectProvider<Config> config, ObjectProvider<CasOAuthWrapperClient> casOAuthWrapperClient){
			super(config, casOAuthWrapperClient);
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnBean({CasProxyReceptor.class})
	class CasProxyReceptorConfiguration extends AbstractClientConfiguration<CasProxyReceptor> {

		public CasProxyReceptorConfiguration(ObjectProvider<Config> config, ObjectProvider<CasProxyReceptor> casProxyReceptor){
			super(config, casProxyReceptor);
		}

	}

}
