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
 * | Copyright @ 2013-2024 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.pac4j.autoconfigure;

import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.core.validator.Validate;
import com.buession.security.pac4j.config.*;
import com.buession.springboot.pac4j.config.Cas;
import org.jasig.cas.client.validation.ProxyList;
import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.client.CasProxyReceptor;
import org.pac4j.cas.client.direct.*;
import org.pac4j.cas.client.rest.*;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.cas.credentials.authenticator.CasAuthenticator;
import org.pac4j.cas.profile.CasProfileDefinition;
import org.pac4j.core.authorization.generator.AuthorizationGenerator;
import org.pac4j.core.client.BaseClient;
import org.pac4j.core.credentials.TokenCredentials;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Pac4j CAS 自动配置类
 *
 * @author Yong.Teng
 * @since 2.0.0
 */
@AutoConfiguration(before = {Pac4jConfiguration.class})
@EnableConfigurationProperties(Pac4jProperties.class)
@ConditionalOnClass({CasConfiguration.class})
public class Pac4jCasConfiguration extends AbstractPac4jClientConfiguration<Cas> {

	public Pac4jCasConfiguration(Pac4jProperties properties) {
		super(properties, properties.getClient().getCas());
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Cas.PREFIX, name = "direct-proxy.enabled", havingValue = "true")
	public CasProxyReceptor casProxyReceptor() {
		final CasProxyReceptor proxyReceptor = new CasProxyReceptor();

		proxyReceptor.setCallbackUrl(config.getCallbackUrl());

		return proxyReceptor;
	}

	@Bean
	public CasProfileDefinition profileDefinition() {
		return BeanUtils.instantiateClass(config.getProfileDefinition());
	}

	@Bean
	@ConditionalOnMissingBean
	public CasConfiguration casConfiguration(ObjectProvider<CasProxyReceptor> casProxyReceptor,
											 ObjectProvider<CasConfigurationCustomizer> casConfigurationCustomizer) {
		final CasConfiguration casConfiguration = new CasConfiguration();
		final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

		Cas config = properties.getClient().getCas();

		propertyMapper.from(config::getProtocol).to(casConfiguration::setProtocol);
		propertyMapper.from(config::getPrefixUrl).to(casConfiguration::setPrefixUrl);
		propertyMapper.from(config::getLoginUrl).to(casConfiguration::setLoginUrl);
		propertyMapper.from(config::getRestUrl).to(casConfiguration::setRestUrl);
		propertyMapper.from(config::getEncoding).to(casConfiguration::setEncoding);
		propertyMapper.from(config::getMethod).to(casConfiguration::setMethod);
		propertyMapper.from(config::getGateway).to(casConfiguration::setGateway);
		propertyMapper.from(config::getRenew).to(casConfiguration::setRenew);
		propertyMapper.from(config::getTimeTolerance).to(casConfiguration::setTimeTolerance);
		propertyMapper.alwaysApplyingWhenHasText().from(config::getPostLogoutUrlParameter)
				.to(casConfiguration::setPostLogoutUrlParameter);
		propertyMapper.from(config::getAcceptAnyProxy).to(casConfiguration::setAcceptAnyProxy);
		propertyMapper.from(config::getAllowedProxyChains).as((proxyChains)->{
			final List<String[]> realProxyChains =
					proxyChains.stream().map((proxyChain)->new String[]{proxyChain}).collect(Collectors.toList());
			return new ProxyList(realProxyChains);
		}).to(casConfiguration::setAllowedProxyChains);
		propertyMapper.from(config.getSsl()::getPrivateKeyPath).to(casConfiguration::setPrivateKeyPath);
		propertyMapper.from(config.getSsl()::getPrivateKeyAlgorithm).to(casConfiguration::setPrivateKeyAlgorithm);
		propertyMapper.from(config::getCustomParameters).to(casConfiguration::setCustomParams);
		//propertyMapper.from(config.getIfAvailable()).to(casConfiguration::setLogoutHandler);
		//propertyMapper.from(config.getIfAvailable()).to(casConfiguration::setUrlResolver);

		casProxyReceptor.ifAvailable(casConfiguration::setProxyReceptor);

		casConfigurationCustomizer.orderedStream().forEach((customizer)->customizer.customize(casConfiguration));

		return casConfiguration;
	}

	@Bean(name = "casClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Cas.PREFIX, name = "general.enabled", havingValue = "true")
	public CasClient casClient(CasConfiguration casConfiguration, CasProfileDefinition casProfileDefinition) {
		final CasClient casClient = new CasClient() {


			@Override
			protected void clientInit() {
				super.clientInit();
				doClientInit(this, casProfileDefinition);
			}

		};

		casClient.setConfiguration(casConfiguration);
		hasTextpropertyMapper.from(config::getCallbackUrl).to(casClient::setCallbackUrl);
		propertyMapper.from(config::getAjaxRequestResolver).as(BeanUtils::instantiateClass)
				.to(casClient::setAjaxRequestResolver);

		afterClientInitialized(casClient, config, config.getGeneral());

		return casClient;
	}

	/* Direct Client 开始 */

	@Bean(name = "directCasClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Cas.PREFIX, name = "direct.enabled", havingValue = "true")
	public DirectCasClient directCasClient(CasConfiguration casConfiguration,
										   CasProfileDefinition casProfileDefinition) {
		final DirectCasClient directCasClient = new DirectCasClient() {


			@Override
			protected void clientInit() {
				super.clientInit();
				doClientInit(this, casProfileDefinition);
			}

		};

		directCasClient.setConfiguration(casConfiguration);

		afterClientInitialized(directCasClient, config, config.getDirect());

		return directCasClient;
	}

	@Bean(name = "directCasProxyClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Cas.PREFIX, name = "direct-proxy.enabled", havingValue = "true")
	public DirectCasProxyClient directCasProxyClient(CasConfiguration casConfiguration,
													 CasProfileDefinition casProfileDefinition) {
		final DirectCasProxyClient directCasProxyClient = new DirectCasProxyClient() {


			@Override
			protected void clientInit() {
				super.clientInit();
				doClientInit(this, casProfileDefinition);
			}

		};

		directCasProxyClient.setConfiguration(casConfiguration);
		hasTextpropertyMapper.from(config::getCallbackUrl).to(directCasProxyClient::setServiceUrl);

		afterClientInitialized(directCasProxyClient, config, config.getDirectProxy());

		return directCasProxyClient;
	}

	/* Direct Client 结束 */

	/* Rest Client 开始 */

	@Bean(name = "casRestBasicAuthClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Cas.PREFIX, name = "rest-basic-auth.enabled", havingValue = "true")
	public CasRestBasicAuthClient casRestBasicAuthClient(CasConfiguration casConfiguration) {
		final CasRestBasicAuthClient casRestBasicAuthClient = new CasRestBasicAuthClient();
		final Cas.RestBasicAuth restBasicAuth = config.getRestBasicAuth();

		casRestBasicAuthClient.setConfiguration(casConfiguration);
		hasTextpropertyMapper.from(restBasicAuth::getHeaderName).to(casRestBasicAuthClient::setHeaderName);
		hasTextpropertyMapper.from(restBasicAuth::getPrefixHeader).to(casRestBasicAuthClient::setPrefixHeader);

		afterClientInitialized(casRestBasicAuthClient, config, restBasicAuth);

		return casRestBasicAuthClient;
	}

	@Bean(name = "casRestFormClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Cas.PREFIX, name = "rest-form.enabled", havingValue = "true")
	public CasRestFormClient casRestFormClient(CasConfiguration casConfiguration) {
		final CasRestFormClient casRestFormClient = new CasRestFormClient();
		final Cas.RestForm restForm = config.getRestForm();

		casRestFormClient.setConfiguration(casConfiguration);
		hasTextpropertyMapper.from(restForm::getUsernameParameter).to(casRestFormClient::setUsernameParameter);
		hasTextpropertyMapper.from(restForm::getPasswordParameter).to(casRestFormClient::setPasswordParameter);

		afterClientInitialized(casRestFormClient, config, restForm);

		return casRestFormClient;
	}

	/* Rest Client 结束 */

	protected void doClientInit(final BaseClient<TokenCredentials> client,
								final CasProfileDefinition casProfileDefinition) {
		((CasAuthenticator) client.getAuthenticator()).setProfileDefinition(casProfileDefinition);

		if(Validate.isNotEmpty(config.getAuthorizationGenerator())){
			final List<AuthorizationGenerator> authorizationGenerators = config.getAuthorizationGenerator()
					.stream()
					.map(BeanUtils::instantiateClass).collect(Collectors.toList());

			client.setAuthorizationGenerators(authorizationGenerators);
		}
	}

}
