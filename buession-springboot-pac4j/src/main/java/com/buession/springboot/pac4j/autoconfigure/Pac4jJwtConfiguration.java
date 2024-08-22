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
 * | Copyright @ 2013-2024 Buession.com Inc.														|
 * +------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.pac4j.autoconfigure;

import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.core.utils.StringUtils;
import com.buession.springboot.pac4j.config.Jwt;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.http.client.direct.ParameterClient;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.pac4j.jwt.profile.JwtGenerator;
import org.springframework.beans.BeanUtils;
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
@ConditionalOnClass({JwtAuthenticator.class, ParameterClient.class})
@ConditionalOnProperty(prefix = Jwt.PREFIX, name = "enabled", havingValue = "true")
@AutoConfigureBefore({Pac4jConfiguration.class})
public class Pac4jJwtConfiguration {

	private final static int PAD_SIZE = 32;

	private final Pac4jProperties properties;

	public Pac4jJwtConfiguration(Pac4jProperties properties) {
		this.properties = properties;
	}

	@Bean
	@ConditionalOnMissingBean
	public SecretSignatureConfiguration secretSignatureConfiguration() {
		Jwt config = properties.getClient().getJwt();
		String jwtSecret = StringUtils.leftPad(config.getEncryptionKey(), PAD_SIZE, config.getEncryptionKey());
		return new SecretSignatureConfiguration(jwtSecret, config.getSecretSignatureAlgorithm());
	}

	@Bean
	@ConditionalOnMissingBean
	public SecretEncryptionConfiguration secretEncryptionConfiguration() {
		Jwt config = properties.getClient().getJwt();
		String jwtEncryptionKey = StringUtils.leftPad(config.getEncryptionKey(), PAD_SIZE, config.getEncryptionKey());
		return new SecretEncryptionConfiguration(jwtEncryptionKey, config.getSecretEncryptionAlgorithm(),
				config.getEncryptionMethod());
	}

	@Bean
	@ConditionalOnMissingBean
	public JwtGenerator<CommonProfile> jwtGenerator(SecretSignatureConfiguration signatureConfiguration,
													SecretEncryptionConfiguration secretEncryptionConfiguration) {
		return new JwtGenerator<>(signatureConfiguration, secretEncryptionConfiguration);
	}

	@Bean
	@ConditionalOnMissingBean
	public JwtAuthenticator jwtAuthenticator(SecretSignatureConfiguration signatureConfiguration,
											 SecretEncryptionConfiguration secretEncryptionConfiguration) {
		Jwt config = properties.getClient().getJwt();
		JwtAuthenticator jwtAuthenticator = new JwtAuthenticator(signatureConfiguration, secretEncryptionConfiguration);

		PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

		propertyMapper.from(config::getIdentifierGenerator).as(BeanUtils::instantiateClass)
				.to(jwtAuthenticator::setIdentifierGenerator);

		return jwtAuthenticator;
	}

}
