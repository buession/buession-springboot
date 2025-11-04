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
 * | Copyright @ 2013-2025 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.pac4j.autoconfigure;

import com.buession.core.Customizer;
import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.core.validator.Validate;
import com.buession.springboot.pac4j.CasConfigurationCustomizer;
import com.buession.springboot.pac4j.config.Cas;
import org.apereo.cas.client.validation.ProxyList;
import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.client.CasProxyReceptor;
import org.pac4j.cas.client.direct.*;
import org.pac4j.cas.client.rest.*;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.cas.credentials.authenticator.CasAuthenticator;
import org.pac4j.cas.profile.CasProfileDefinition;
import org.pac4j.core.authorization.generator.AuthorizationGenerator;
import org.pac4j.core.client.BaseClient;
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
	public CasClient casClient(CasConfiguration casConfiguration, CasProfileDefinition casProfileDefinition,
							   ObjectProvider<Customizer<CasClient>> customizers) {
		final CasClient casClient = new CasClient(casConfiguration) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				doClientInit(this, casProfileDefinition, customizers);
			}

		};

		afterIndirectClientInitialized(casClient, config, config.getGeneral());

		return casClient;
	}

	/* Direct Client 开始 */

	@Bean(name = "directCasClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Cas.PREFIX, name = "direct.enabled", havingValue = "true")
	public DirectCasClient directCasClient(CasConfiguration casConfiguration,
										   CasProfileDefinition casProfileDefinition,
										   ObjectProvider<Customizer<DirectCasClient>> customizers) {
		final DirectCasClient directCasClient = new DirectCasClient(casConfiguration) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				doClientInit(this, casProfileDefinition, customizers);
			}

		};

		afterDirectClientInitialized(directCasClient, config, config.getDirect());

		return directCasClient;
	}

	@Bean(name = "directCasProxyClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Cas.PREFIX, name = "direct-proxy.enabled", havingValue = "true")
	public DirectCasProxyClient directCasProxyClient(CasConfiguration casConfiguration,
													 CasProfileDefinition casProfileDefinition,
													 ObjectProvider<Customizer<DirectCasProxyClient>> customizers) {
		final DirectCasProxyClient directCasProxyClient = new DirectCasProxyClient(casConfiguration,
				config.getDirectProxy().getServiceUrl()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				doClientInit(this, casProfileDefinition, customizers);
			}

		};

		afterDirectClientInitialized(directCasProxyClient, config, config.getDirectProxy());

		return directCasProxyClient;
	}

	/* Direct Client 结束 */

	/* Rest Client 开始 */

	@Bean(name = "casRestBasicAuthClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Cas.PREFIX, name = "rest-basic-auth.enabled", havingValue = "true")
	public CasRestBasicAuthClient casRestBasicAuthClient(CasConfiguration casConfiguration,
														 ObjectProvider<Customizer<CasRestBasicAuthClient>> customizers) {
		final CasRestBasicAuthClient casRestBasicAuthClient = new CasRestBasicAuthClient(casConfiguration, null, null) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
			}

		};
		final Cas.RestBasicAuth restBasicAuth = config.getRestBasicAuth();

		hasTextpropertyMapper.from(restBasicAuth::getHeaderName).to(casRestBasicAuthClient::setHeaderName);
		hasTextpropertyMapper.from(restBasicAuth::getPrefixHeader).to(casRestBasicAuthClient::setPrefixHeader);

		afterDirectClientInitialized(casRestBasicAuthClient, config, restBasicAuth);

		return casRestBasicAuthClient;
	}

	@Bean(name = "casRestFormClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Cas.PREFIX, name = "rest-form.enabled", havingValue = "true")
	public CasRestFormClient casRestFormClient(CasConfiguration casConfiguration,
											   ObjectProvider<Customizer<CasRestFormClient>> customizers) {
		final CasRestFormClient casRestFormClient = new CasRestFormClient(casConfiguration, null, null) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
			}

		};
		final Cas.RestForm restForm = config.getRestForm();

		hasTextpropertyMapper.from(restForm::getUsernameParameter).to(casRestFormClient::setUsernameParameter);
		hasTextpropertyMapper.from(restForm::getPasswordParameter).to(casRestFormClient::setPasswordParameter);

		afterDirectClientInitialized(casRestFormClient, config, restForm);

		return casRestFormClient;
	}

	/* Rest Client 结束 */

	protected <C extends BaseClient> void doClientInit(final C client,
													   final CasProfileDefinition casProfileDefinition,
													   final ObjectProvider<Customizer<C>> customizers) {
		((CasAuthenticator) client.getAuthenticator()).setProfileDefinition(casProfileDefinition);

		if(Validate.isNotEmpty(config.getAuthorizationGenerator())){
			final List<AuthorizationGenerator> authorizationGenerators = config.getAuthorizationGenerator()
					.stream()
					.map(BeanUtils::instantiateClass).collect(Collectors.toList());

			client.setAuthorizationGenerators(authorizationGenerators);
		}
		customizer(client, customizers);
	}

}
