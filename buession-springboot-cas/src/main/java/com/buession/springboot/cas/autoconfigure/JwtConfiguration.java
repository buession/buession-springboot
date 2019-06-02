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
 * | Copyright @ 2013-2019 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.cas.autoconfigure;

import com.buession.core.utils.StringUtils;
import com.buession.springboot.cas.Constant;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWSAlgorithm;
import org.pac4j.core.credentials.extractor.HeaderExtractor;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.creator.AuthenticatorProfileCreator;
import org.pac4j.http.client.direct.ParameterClient;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
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

/**
 * @author Yong.Teng
 */
@Configuration
@EnableConfigurationProperties(JwtProperties.class)
@ConditionalOnClass(JwtAuthenticator.class)
public class JwtConfiguration {

    private final static int PAD_SIZE = 32;

    @Autowired
    private JwtProperties jwtProperties;

    private final static Logger logger = LoggerFactory.getLogger(JwtConfiguration.class);

    @Bean(name = "jwtGenerator")
    @ConditionalOnMissingBean
    public JwtGenerator<CommonProfile> jwtGenerator(){
        return new JwtGenerator<>(new SecretSignatureConfiguration(getJwtSecret(), JWSAlgorithm.HS256), new
                SecretEncryptionConfiguration(getJwtEncryptionKey(), JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256));
    }

    @Bean(name = "jwtClient")
    @ConditionalOnProperty(name = "pac4j.client.jwt", havingValue = "on")
    @ConditionalOnMissingBean
    public ParameterClient jwtClient(){
        ParameterClient parameterClient = new ParameterClient(jwtProperties.getParameterName(), jwtAuthenticator());

        parameterClient.setSupportGetRequest(true);
        parameterClient.setCredentialsExtractor(new HeaderExtractor(jwtProperties.getParameterName(), jwtProperties
                .getPrefixHeader() == null ? "" : jwtProperties.getPrefixHeader()));
        parameterClient.setProfileCreator(new AuthenticatorProfileCreator<>());
        parameterClient.setName(Constant.JWT_CLIENT);

        logger.debug("initialize {} name => {}, width encryptionKey => {}, parameterName => {}, prefixHeader => {}, "
                + "headerName => {}", ParameterClient.class.getName(), Constant.JWT_CLIENT, jwtProperties
                .getEncryptionKey(), jwtProperties.getParameterName(), jwtProperties.getPrefixHeader());

        return parameterClient;
    }

    private String getJwtSecret(){
        return StringUtils.leftPad(jwtProperties.getEncryptionKey(), PAD_SIZE, jwtProperties.getEncryptionKey());
    }

    private String getJwtEncryptionKey(){
        return StringUtils.leftPad(jwtProperties.getEncryptionKey(), PAD_SIZE, jwtProperties.getEncryptionKey());
    }

    public JwtAuthenticator jwtAuthenticator(){
        return new JwtAuthenticator(new SecretSignatureConfiguration(getJwtSecret(), JWSAlgorithm.HS256), new
                SecretEncryptionConfiguration(getJwtEncryptionKey(), JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256));
    }

}
