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

import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.client.rest.CasRestFormClient;
import org.pac4j.core.profile.creator.AuthenticatorProfileCreator;
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
@EnableConfigurationProperties(CasProperties.class)
@ConditionalOnClass({org.pac4j.cas.config.CasConfiguration.class})
public class CasConfiguration {

    public final static String CAS_CLIENT = "cas";

    public final static String REST_CLIENT = "rest";

    @Autowired
    private CasProperties casProperties;

    @Bean(name = "casConfiguration")
    @ConditionalOnMissingBean
    public org.pac4j.cas.config.CasConfiguration casConfiguration(){
        org.pac4j.cas.config.CasConfiguration casConfiguration = new org.pac4j.cas.config.CasConfiguration
                (casProperties.getLoginUrl(), casProperties.getPrefixUrl());

        casConfiguration.setProtocol(casProperties.getProtocol());

        return casConfiguration;
    }

    @Bean(name = "casClient")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "pac4j.client.cas", havingValue = "on")
    public CasClient casClient(org.pac4j.cas.config.CasConfiguration casConfiguration){
        CasClient casClient = new CasClient();

        casClient.setName(CAS_CLIENT);
        casClient.setConfiguration(casConfiguration);
        casClient.setCallbackUrl(casProperties.getCallbackUrl());
        // casClient.setProfileCreator(new AuthenticatorProfileCreator<>());

        return casClient;
    }

    @Bean(name = "casRestFormClient")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "pac4j.client.rest", havingValue = "on")
    public CasRestFormClient casRestFormClient(org.pac4j.cas.config.CasConfiguration casConfiguration){
        CasRestFormClient casRestFormClient = new CasRestFormClient();

        casRestFormClient.setName(REST_CLIENT);
        casRestFormClient.setConfiguration(casConfiguration);
        //casRestFormClient.setProfileCreator(new AuthenticatorProfileCreator<>());

        return casRestFormClient;
    }

}
