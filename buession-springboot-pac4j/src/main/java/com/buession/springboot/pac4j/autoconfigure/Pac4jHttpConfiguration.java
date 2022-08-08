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
package com.buession.springboot.pac4j.autoconfigure;

import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.springboot.pac4j.config.BaseConfig;
import com.buession.springboot.pac4j.config.Http;
import org.pac4j.core.client.IndirectClient;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.http.client.direct.DirectBasicAuthClient;
import org.pac4j.http.client.indirect.FormClient;
import org.pac4j.http.client.indirect.IndirectBasicAuthClient;
import org.pac4j.http.credentials.authenticator.test.SimpleTestUsernamePasswordAuthenticator;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Pac4j HTTP 自动配置类
 *
 * @author Yong.Teng
 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(Pac4jProperties.class)
@ConditionalOnClass({FormClient.class})
@ConditionalOnProperty(prefix = Http.PREFIX, name = "enabled", havingValue = "true")
@AutoConfigureBefore({Pac4jConfiguration.class})
public class Pac4jHttpConfiguration {

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(Pac4jProperties.class)
	static class Pac4JHttpClientConfiguration extends AbstractPac4jClientConfiguration<Http> {

		public Pac4JHttpClientConfiguration(Pac4jProperties properties){
			super(properties, properties.getClient().getHttp());
		}

		@Bean(name = "formClient")
		@ConditionalOnMissingBean
		@ConditionalOnProperty(prefix = Http.PREFIX, name = "form.enabled", havingValue = "true")
		public FormClient formClient(){
			final SimpleTestUsernamePasswordAuthenticator usernamePasswordAuthenticator = new SimpleTestUsernamePasswordAuthenticator();
			final FormClient formClient = new FormClient(config.getForm().getLoginUrl(), usernamePasswordAuthenticator);
			final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenHasText();

			propertyMapper.from(config.getForm()::getUsernameParameter).to(formClient::setUsernameParameter);
			propertyMapper.from(config.getForm()::getPasswordParameter).to(formClient::setPasswordParameter);

			initHttpIndirectClient(formClient, config.getForm(), propertyMapper);

			return formClient;
		}

		@Bean(name = "indirectBasicAuthClient")
		@ConditionalOnMissingBean
		@ConditionalOnProperty(prefix = Http.PREFIX, name = "indirect-basic-auth.enabled", havingValue = "true")
		public IndirectBasicAuthClient indirectBasicAuthClient(){
			final SimpleTestUsernamePasswordAuthenticator usernamePasswordAuthenticator = new SimpleTestUsernamePasswordAuthenticator();
			final IndirectBasicAuthClient indirectBasicAuthClient = new IndirectBasicAuthClient(
					usernamePasswordAuthenticator);
			final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenHasText();

			propertyMapper.from(config.getIndirectBasicAuth()::getRealmName).to(indirectBasicAuthClient::setRealmName);

			initHttpIndirectClient(indirectBasicAuthClient, config.getIndirectBasicAuth(), propertyMapper);

			return indirectBasicAuthClient;
		}

		@Bean(name = "directBasicAuthClient")
		@ConditionalOnMissingBean
		@ConditionalOnProperty(prefix = Http.PREFIX, name = "direct-basic-auth.enabled", havingValue = "true")
		public DirectBasicAuthClient directBasicAuthClient(){
			final SimpleTestUsernamePasswordAuthenticator usernamePasswordAuthenticator = new SimpleTestUsernamePasswordAuthenticator();
			final DirectBasicAuthClient directBasicAuthClient = new DirectBasicAuthClient(
					usernamePasswordAuthenticator);
			final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenHasText();

			propertyMapper.from(config.getDirectBasicAuth()::getRealmName).to(directBasicAuthClient::setRealmName);

			afterClientInitialized(directBasicAuthClient, config.getDirectBasicAuth());

			return directBasicAuthClient;
		}

		protected void initHttpIndirectClient(final IndirectClient<? extends Credentials> client,
											  final BaseConfig.BaseClientConfig config,
											  final PropertyMapper propertyMapper){
			propertyMapper.from(this.config::getCallbackUrl).to(client::setCallbackUrl);
			afterClientInitialized(client, config);
		}

	}

}
