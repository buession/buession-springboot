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

import com.buession.core.validator.Validate;
import com.buession.springboot.pac4j.config.BaseConfig;
import com.buession.springboot.pac4j.config.Http;
import org.pac4j.core.client.Clients;
import org.pac4j.core.client.DirectClient;
import org.pac4j.core.client.IndirectClient;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.http.client.direct.DirectBasicAuthClient;
import org.pac4j.http.client.indirect.FormClient;
import org.pac4j.http.client.indirect.IndirectBasicAuthClient;
import org.pac4j.http.credentials.authenticator.test.SimpleTestUsernamePasswordAuthenticator;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Pac4j HTTP 自动配置类
 *
 * @author Yong.Teng
 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(Pac4jProperties.class)
@ConditionalOnClass({FormClient.class})
@ConditionalOnProperty(name = Http.PREFIX)
@Import({Pac4jConfiguration.class})
public class Pac4jHttpConfiguration extends AbstractPac4jConfiguration<Http> {

	public Pac4jHttpConfiguration(Pac4jProperties properties, ObjectProvider<Clients> clients){
		super(properties, clients.getIfAvailable());
		this.config = properties.getClient().getHttp();
	}

	@Bean(name = "formClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Http.PREFIX, name = "form.enabled", havingValue = "true")
	public FormClient formClient(){
		final SimpleTestUsernamePasswordAuthenticator usernamePasswordAuthenticator = new SimpleTestUsernamePasswordAuthenticator();
		final FormClient formClient = new FormClient(config.getForm().getLoginUrl(), usernamePasswordAuthenticator);

		if(Validate.hasText(config.getForm().getUsernameParameter())){
			formClient.setUsernameParameter(config.getForm().getUsernameParameter());
		}

		if(Validate.hasText(config.getForm().getPasswordParameter())){
			formClient.setPasswordParameter(config.getForm().getPasswordParameter());
		}

		initHttpIndirectClient(formClient, config.getForm());

		return formClient;
	}

	@Bean(name = "indirectBasicAuthClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Http.PREFIX, name = {"indirectBasicAuth.enabled",
			"indirect-basic-auth.enabled"}, havingValue = "true")
	public IndirectBasicAuthClient indirectBasicAuthClient(){
		final SimpleTestUsernamePasswordAuthenticator usernamePasswordAuthenticator = new SimpleTestUsernamePasswordAuthenticator();
		final IndirectBasicAuthClient indirectBasicAuthClient = new IndirectBasicAuthClient(
				usernamePasswordAuthenticator);

		if(Validate.hasText(config.getIndirectBasicAuth().getRealmName())){
			indirectBasicAuthClient.setRealmName(config.getIndirectBasicAuth().getRealmName());
		}

		initHttpIndirectClient(indirectBasicAuthClient, config.getIndirectBasicAuth());

		return indirectBasicAuthClient;
	}

	@Bean(name = "directBasicAuthClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Http.PREFIX, name = {"directBasicAuth.enabled",
			"direct-basic-auth.enabled"}, havingValue = "true")
	public DirectBasicAuthClient directBasicAuthClient(){
		final SimpleTestUsernamePasswordAuthenticator usernamePasswordAuthenticator = new SimpleTestUsernamePasswordAuthenticator();
		final DirectBasicAuthClient directBasicAuthClient = new DirectBasicAuthClient(usernamePasswordAuthenticator);

		if(Validate.hasText(config.getDirectBasicAuth().getRealmName())){
			directBasicAuthClient.setRealmName(config.getDirectBasicAuth().getRealmName());
		}

		initHttpDirectClient(directBasicAuthClient, config.getDirectBasicAuth());

		return directBasicAuthClient;
	}

	protected void initHttpIndirectClient(final IndirectClient<? extends Credentials> client,
										  final BaseConfig.BaseClientConfig config){
		if(Validate.hasText(this.config.getCallbackUrl())){
			client.setCallbackUrl(this.config.getCallbackUrl());
		}

		afterClientInitialized(client, config);
	}

	protected void initHttpDirectClient(final DirectClient<? extends Credentials> client,
										final BaseConfig.BaseClientConfig config){
		afterClientInitialized(client, config);
	}

}
