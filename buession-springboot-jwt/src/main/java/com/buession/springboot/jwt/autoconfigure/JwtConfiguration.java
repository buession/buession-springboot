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
 * | Copyright @ 2013-2020 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.jwt.autoconfigure;

import com.buession.core.utils.StringUtils;
import com.buession.lang.Constants;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWSAlgorithm;
import org.pac4j.core.credentials.extractor.HeaderExtractor;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.http.client.direct.ParameterClient;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.config.signature.SignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.pac4j.jwt.profile.JwtGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author Yong.Teng
 */
@Configuration
@EnableConfigurationProperties(JwtProperties.class)
@ConditionalOnClass({JwtAuthenticator.class, ParameterClient.class})
public class JwtConfiguration {

	private SignatureConfiguration signatureConfiguration;

	private SecretEncryptionConfiguration secretEncryptionConfiguration;

	@Autowired
	private JwtProperties jwtProperties;

	private final static Logger logger = LoggerFactory.getLogger(JwtConfiguration.class);

	@PostConstruct
	private void initialize(){
		int padSize = 32;

		String jwtSecret = StringUtils.leftPad(jwtProperties.getEncryptionKey(), padSize,
				jwtProperties.getEncryptionKey());
		String jwtEncryptionKey = StringUtils.leftPad(jwtProperties.getEncryptionKey(), padSize,
				jwtProperties.getEncryptionKey());

		signatureConfiguration = new SecretSignatureConfiguration(jwtSecret, JWSAlgorithm.HS256);
		secretEncryptionConfiguration = new SecretEncryptionConfiguration(jwtEncryptionKey, JWEAlgorithm.DIR,
				EncryptionMethod.A128CBC_HS256);
	}

	@Bean
	@ConditionalOnMissingBean
	public JwtGenerator<CommonProfile> jwtGenerator(){
		return new JwtGenerator<>(signatureConfiguration, secretEncryptionConfiguration);
	}

	@Bean
	@ConditionalOnProperty(prefix = "pac4j.client", name = "jwt", havingValue = "on")
	@ConditionalOnMissingBean
	public ParameterClient jwtClient(){
		ParameterClient parameterClient = new ParameterClient();
		HeaderExtractor headerExtractor = new HeaderExtractor(jwtProperties.getParameterName(),
				jwtProperties.getPrefixHeader() == null ? Constants.EMPTY_STRING : jwtProperties.getPrefixHeader());
		JwtAuthenticator jwtAuthenticator = new JwtAuthenticator(signatureConfiguration,
				secretEncryptionConfiguration);

		parameterClient.setName("jwt");
		parameterClient.setParameterName(jwtProperties.getParameterName());
		parameterClient.setSupportGetRequest(true);
		parameterClient.setAuthenticator(jwtAuthenticator);
		parameterClient.setCredentialsExtractor(headerExtractor);
		//parameterClient.setProfileCreator(new AuthenticatorProfileCreator<>());

		logger.debug("initialize {} name => jwt, width encryptionKey => {}, parameterName => {}, prefixHeader => {}, " + "headerName => {}", ParameterClient.class.getName(), jwtProperties.getEncryptionKey(), jwtProperties.getParameterName(), jwtProperties.getPrefixHeader());

		return parameterClient;
	}

}
