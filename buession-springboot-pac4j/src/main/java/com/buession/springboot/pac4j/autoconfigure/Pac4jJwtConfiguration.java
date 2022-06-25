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

import com.buession.core.utils.StringUtils;
import com.buession.springboot.pac4j.config.Jwt;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWSAlgorithm;
import org.pac4j.core.client.Clients;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.util.generator.ValueGenerator;
import org.pac4j.http.client.direct.CookieClient;
import org.pac4j.http.client.direct.HeaderClient;
import org.pac4j.http.client.direct.ParameterClient;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.pac4j.jwt.profile.JwtGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Pac4j JWT 自动配置类
 *
 * @author Yong.Teng
 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(Pac4jProperties.class)
@ConditionalOnClass({JwtAuthenticator.class, ParameterClient.class})
@ConditionalOnProperty(name = Jwt.PREFIX)
@Import({Pac4jConfiguration.class})
public class Pac4jJwtConfiguration extends AbstractPac4jConfiguration<Jwt> {

	private final static int PAD_SIZE = 32;

	public Pac4jJwtConfiguration(Pac4jProperties properties, ObjectProvider<Clients> clients){
		super(properties, clients.getIfAvailable());
		this.config = properties.getJwt();
	}

	@Bean
	@ConditionalOnMissingBean
	public SecretSignatureConfiguration secretSignatureConfiguration(){
		String jwtSecret = StringUtils.leftPad(config.getEncryptionKey(), PAD_SIZE, config.getEncryptionKey());
		return new SecretSignatureConfiguration(jwtSecret, JWSAlgorithm.parse(config.getSecretSignatureAlgorithm()));
	}

	@Bean
	@ConditionalOnMissingBean
	public SecretEncryptionConfiguration secretEncryptionConfiguration(){
		String jwtEncryptionKey = StringUtils.leftPad(config.getEncryptionKey(), PAD_SIZE,
				config.getEncryptionKey());
		return new SecretEncryptionConfiguration(jwtEncryptionKey,
				JWEAlgorithm.parse(config.getSecretEncryptionAlgorithm()),
				EncryptionMethod.parse(config.getEncryptionMethod()));
	}

	@Bean
	@ConditionalOnMissingBean
	public JwtGenerator<CommonProfile> jwtGenerator(SecretSignatureConfiguration signatureConfiguration,
													SecretEncryptionConfiguration secretEncryptionConfiguration){
		return new JwtGenerator<>(signatureConfiguration, secretEncryptionConfiguration);
	}

	@Bean
	@ConditionalOnMissingBean
	public JwtAuthenticator jwtAuthenticator(SecretSignatureConfiguration signatureConfiguration,
											 SecretEncryptionConfiguration secretEncryptionConfiguration){
		JwtAuthenticator jwtAuthenticator = new JwtAuthenticator(signatureConfiguration, secretEncryptionConfiguration);

		if(config.getIdentifierGenerator() != null){
			ValueGenerator identifierGenerator = BeanUtils.instantiateClass(config.getIdentifierGenerator());
			jwtAuthenticator.setIdentifierGenerator(identifierGenerator);
		}

		return jwtAuthenticator;
	}

	@Bean(name = "jwtParameterClient")
	@ConditionalOnProperty(prefix = Jwt.PREFIX, name = "header.enabled", havingValue = "on")
	@ConditionalOnMissingBean
	public HeaderClient headerClient(JwtAuthenticator jwtAuthenticator){
		final HeaderClient headerClient = new HeaderClient(config.getHeader().getHeaderName(),
				config.getHeader().getPrefixHeader(), jwtAuthenticator);

		afterClientInitialized(headerClient, config.getHeader());

		return headerClient;
	}

	@Bean(name = "jwtCookieClient")
	@ConditionalOnProperty(prefix = Jwt.PREFIX, name = "cookie.enabled", havingValue = "on")
	@ConditionalOnMissingBean
	public CookieClient cookieClient(JwtAuthenticator jwtAuthenticator){
		final CookieClient cookieClient = new CookieClient(config.getCookie().getCookieName(), jwtAuthenticator);

		afterClientInitialized(cookieClient, config.getCookie());

		return cookieClient;
	}

	@Bean(name = "jwtParameterClient")
	@ConditionalOnProperty(prefix = Jwt.PREFIX, name = "parameter.enabled", havingValue = "on")
	@ConditionalOnMissingBean
	public ParameterClient parameterClient(JwtAuthenticator jwtAuthenticator){
		final ParameterClient parameterClient = new ParameterClient(config.getParameter().getParameterName(),
				jwtAuthenticator);

		parameterClient.setSupportGetRequest(config.isSupportGetRequest());
		parameterClient.setSupportPostRequest(config.isSupportPostRequest());
		//parameterClient.setCredentialsExtractor(
		//new HeaderExtractor(config.getParameter().getParameterName(), config.getPrefixHeader()));

		afterClientInitialized(parameterClient, config.getParameter());

		return parameterClient;
	}

}
