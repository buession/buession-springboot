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
import com.buession.springboot.pac4j.config.Cas;
import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.client.CasProxyReceptor;
import org.pac4j.cas.client.direct.DirectCasClient;
import org.pac4j.cas.client.direct.DirectCasProxyClient;
import org.pac4j.cas.client.rest.CasRestBasicAuthClient;
import org.pac4j.cas.client.rest.CasRestFormClient;
import org.pac4j.cas.config.CasConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Pac4j CAS 自动配置类
 *
 * @author Yong.Teng
 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(Pac4jProperties.class)
@ConditionalOnClass({CasConfiguration.class})
@ConditionalOnProperty(prefix = Cas.PREFIX, name = "enabled", havingValue = "true")
@AutoConfigureBefore({Pac4jConfiguration.class})
public class Pac4jCasConfiguration extends AbstractPac4jConfiguration<Cas> {

	public Pac4jCasConfiguration(Pac4jProperties properties){
		super(properties, properties.getClient().getCas());
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Cas.PREFIX, name = "proxy.enabled", havingValue = "true")
	public CasProxyReceptor proxyReceptor(){
		CasProxyReceptor proxyReceptor = new CasProxyReceptor();
		proxyReceptor.setCallbackUrl(config.getCallbackUrl());
		return proxyReceptor;
	}

	@Bean
	@ConditionalOnBean({CasProxyReceptor.class})
	@ConditionalOnMissingBean
	public CasConfiguration casConfiguration(ObjectProvider<CasProxyReceptor> proxyReceptorProvider){
		CasConfiguration casConfiguration = createCasConfiguration();

		CasProxyReceptor casProxyReceptor = proxyReceptorProvider.getIfAvailable();

		if(casProxyReceptor != null){
			casConfiguration.setProxyReceptor(casProxyReceptor);
		}

		return casConfiguration;
	}

	@Bean
	@ConditionalOnMissingBean
	public CasConfiguration casConfiguration(){
		return createCasConfiguration();
	}

	@Bean(name = "casClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Cas.PREFIX, name = "general.enabled", havingValue = "true")
	public CasClient casClient(ObjectProvider<CasConfiguration> casConfiguration){
		final CasClient casClient = new CasClient(casConfiguration.getIfAvailable());

		if(config.getCallbackUrl() != null){
			casClient.setCallbackUrl(config.getCallbackUrl());
		}

		afterClientInitialized(casClient, config.getGeneral());

		return casClient;
	}

	@Bean(name = "casRestFormClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Cas.PREFIX, name = "rest-form.enabled", havingValue = "true")
	public CasRestFormClient casRestFormClient(ObjectProvider<CasConfiguration> casConfiguration){
		final CasRestFormClient casRestFormClient = new CasRestFormClient();

		casRestFormClient.setConfiguration(casConfiguration.getIfAvailable());

		if(Validate.hasText(config.getRestForm().getUsernameParameter())){
			casRestFormClient.setUsernameParameter(config.getRestForm().getUsernameParameter());
		}

		if(Validate.hasText(config.getRestForm().getPasswordParameter())){
			casRestFormClient.setPasswordParameter(config.getRestForm().getPasswordParameter());
		}

		afterClientInitialized(casRestFormClient, config.getRestForm());

		return casRestFormClient;
	}

	@Bean(name = "directCasClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Cas.PREFIX, name = "direct.enabled", havingValue = "true")
	public DirectCasClient directCasClient(ObjectProvider<CasConfiguration> casConfiguration){
		final DirectCasClient directCasClient = new DirectCasClient(casConfiguration.getIfAvailable());

		afterClientInitialized(directCasClient, config.getDirect());

		return directCasClient;
	}

	@Bean(name = "directCasProxyClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Cas.PREFIX, name = "direct-proxy.enabled", havingValue = "true")
	public DirectCasProxyClient directCasProxyClient(ObjectProvider<CasConfiguration> configuration){
		final DirectCasProxyClient directCasProxyClient = new DirectCasProxyClient(configuration.getIfAvailable(),
				config.getPrefixUrl());

		afterClientInitialized(directCasProxyClient, config.getDirectProxy());

		return directCasProxyClient;
	}

	@Bean(name = "casRestBasicAuthClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Cas.PREFIX, name = "rest-basic-auth.enabled", havingValue = "true")
	public CasRestBasicAuthClient casRestBasicAuthClient(CasConfiguration configuration){
		final CasRestBasicAuthClient casRestBasicAuthClient = new CasRestBasicAuthClient();

		casRestBasicAuthClient.setConfiguration(configuration);

		if(Validate.hasText(config.getRestBasicAuth().getHeaderName())){
			casRestBasicAuthClient.setHeaderName(config.getRestBasicAuth().getHeaderName());
		}

		if(Validate.hasText(config.getRestBasicAuth().getPrefixHeader())){
			casRestBasicAuthClient.setPrefixHeader(config.getRestBasicAuth().getPrefixHeader());
		}

		afterClientInitialized(casRestBasicAuthClient, config.getRestBasicAuth());

		return casRestBasicAuthClient;
	}

	private CasConfiguration createCasConfiguration(){
		CasConfiguration casConfiguration = new CasConfiguration();

		PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

		propertyMapper.from(config.getProtocol()).to(casConfiguration::setProtocol);
		propertyMapper.from(config.getPrefixUrl()).to(casConfiguration::setPrefixUrl);
		propertyMapper.from(config.getLoginUrl()).to(casConfiguration::setLoginUrl);
		propertyMapper.from(config.getEncoding()).to(casConfiguration::setEncoding);
		propertyMapper.from(config.getMethod()).to(casConfiguration::setMethod);
		propertyMapper.from(config.getGateway()).to(casConfiguration::setGateway);
		propertyMapper.from(config.getRenew()).to(casConfiguration::setRenew);
		propertyMapper.from(config.getTimeTolerance()).to(casConfiguration::setTimeTolerance);
		propertyMapper.from(config.getPostLogoutUrlParameter())
				.to(casConfiguration::setPostLogoutUrlParameter);
		propertyMapper.from(config.getCustomParameters()).to(casConfiguration::setCustomParams);

		//propertyMapper.from(config.getIfAvailable()).to(casConfiguration::setLogoutHandler);
		//propertyMapper.from(config.getIfAvailable()).to(casConfiguration::setUrlResolver);

		return casConfiguration;
	}

}
