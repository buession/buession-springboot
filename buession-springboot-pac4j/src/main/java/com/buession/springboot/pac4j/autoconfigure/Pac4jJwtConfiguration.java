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

import com.buession.core.Customizer;
import com.buession.core.utils.StringUtils;
import com.buession.springboot.pac4j.config.Jwt;
import org.pac4j.core.client.DirectClient;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.http.client.direct.HeaderClient;
import org.pac4j.http.client.direct.ParameterClient;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.pac4j.jwt.profile.JwtGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Pac4j JWT 自动配置类
 *
 * @author Yong.Teng
 * @since 2.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(Pac4jProperties.class)
@ConditionalOnProperty(prefix = Jwt.PREFIX, name = "enabled", havingValue = "true")
@ConditionalOnClass({JwtAuthenticator.class, HeaderClient.class})
@AutoConfigureBefore({Pac4jConfiguration.class})
public class Pac4jJwtConfiguration extends AbstractPac4jClientConfiguration<Jwt> {

	private final static int PAD_SIZE = 32;

	private final String secret;

	public Pac4jJwtConfiguration(Pac4jProperties properties) {
		super(properties, properties.getClient().getJwt());
		this.secret = StringUtils.leftPad(config.getEncryptionKey(), PAD_SIZE, config.getEncryptionKey());
	}

	@Bean
	@ConditionalOnMissingBean
	public SecretSignatureConfiguration secretSignatureConfiguration() {
		return new SecretSignatureConfiguration(secret, config.getSecretSignatureAlgorithm());
	}

	@Bean
	@ConditionalOnMissingBean
	public SecretEncryptionConfiguration secretEncryptionConfiguration() {
		return new SecretEncryptionConfiguration(secret, config.getSecretEncryptionAlgorithm(),
				config.getEncryptionMethod());
	}

	@Bean
	@ConditionalOnMissingBean
	public JwtGenerator jwtGenerator(SecretSignatureConfiguration signatureConfiguration,
	                                 SecretEncryptionConfiguration secretEncryptionConfiguration) {
		return new JwtGenerator(signatureConfiguration, secretEncryptionConfiguration);
	}

	@Bean
	@ConditionalOnMissingBean
	public JwtAuthenticator jwtAuthenticator(SecretSignatureConfiguration signatureConfiguration,
	                                         SecretEncryptionConfiguration secretEncryptionConfiguration) {
		JwtAuthenticator jwtAuthenticator = new JwtAuthenticator(signatureConfiguration, secretEncryptionConfiguration);

		nonNullpropertyMapper.from(config::getIdentifierGenerator).as(BeanUtils::instantiateClass)
				.to(jwtAuthenticator::setIdentifierGenerator);

		return jwtAuthenticator;
	}

	@Bean(name = "jwtHeaderClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Jwt.PREFIX, name = "header.enabled", havingValue = "true")
	public HeaderClient jwtHeaderClient(ObjectProvider<Authenticator> authenticator,
	                                    ObjectProvider<Customizer<HeaderClient>> customizers) {
		final Jwt.Header header = config.getHeader();
		final HeaderClient headerClient = new HeaderClient() {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
			}

		};

		clientApplyCommonProperties(headerClient, authenticator);
		hasTextpropertyMapper.from(header::getHeaderName).to(headerClient::setHeaderName);
		hasTextpropertyMapper.from(header::getPrefixHeader).to(headerClient::setPrefixHeader);

		return headerClient;
	}

	@Bean(name = "jwtParameterClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Jwt.PREFIX, name = "parameter.enabled", havingValue = "true")
	public ParameterClient jwtParameterClient(ObjectProvider<Authenticator> authenticator,
	                                          ObjectProvider<Customizer<ParameterClient>> customizers) {
		final Jwt.Parameter parameter = config.getParameter();
		final ParameterClient parameterClient = new ParameterClient() {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
			}

		};

		clientApplyCommonProperties(parameterClient, authenticator);
		hasTextpropertyMapper.from(parameter::getParameterName).to(parameterClient::setParameterName);
		hasTextpropertyMapper.from(parameter::getSupportGetRequest).to(parameterClient::setSupportGetRequest);
		hasTextpropertyMapper.from(parameter::getSupportPostRequest).to(parameterClient::setSupportPostRequest);

		return parameterClient;
	}

	private void clientApplyCommonProperties(final DirectClient client,
	                                         final ObjectProvider<Authenticator> authenticator) {
		hasTextpropertyMapper.from(config::getName).to(client::setName);
		nonNullpropertyMapper.from(config::getCustomProperties).to(client::setCustomProperties);
		authenticator.ifAvailable(client::setAuthenticator);
	}

}
