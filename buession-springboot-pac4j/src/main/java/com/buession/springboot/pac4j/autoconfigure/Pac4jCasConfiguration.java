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
import com.buession.springboot.pac4j.config.Cas;
import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.client.CasProxyReceptor;
import org.pac4j.cas.client.direct.DirectCasClient;
import org.pac4j.cas.client.direct.DirectCasProxyClient;
import org.pac4j.cas.client.rest.CasRestBasicAuthClient;
import org.pac4j.cas.client.rest.CasRestFormClient;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.cas.credentials.authenticator.CasAuthenticator;
import org.pac4j.core.authorization.generator.AuthorizationGenerator;
import org.pac4j.core.client.BaseClient;
import org.pac4j.core.credentials.TokenCredentials;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
public class Pac4jCasConfiguration {

	private final Pac4jProperties properties;

	public Pac4jCasConfiguration(Pac4jProperties properties) {
		this.properties = properties;
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Cas.PREFIX, name = "proxy.enabled", havingValue = "true")
	public CasProxyReceptor casProxyReceptor() {
		final CasProxyReceptor proxyReceptor = new CasProxyReceptor();

		proxyReceptor.setCallbackUrl(properties.getClient().getCas().getCallbackUrl());

		return proxyReceptor;
	}

	@Bean
	@ConditionalOnMissingBean
	public CasConfiguration casConfiguration(ObjectProvider<CasProxyReceptor> casProxyReceptor) {
		final CasConfiguration casConfiguration = new CasConfiguration();

		Cas config = properties.getClient().getCas();

		final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

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

		casProxyReceptor.ifUnique(casConfiguration::setProxyReceptor);

		return casConfiguration;
	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(Pac4jProperties.class)
	static class Pac4jCasClientConfiguration extends AbstractPac4jClientConfiguration<Cas> {

		private final CasConfiguration casConfiguration;

		public Pac4jCasClientConfiguration(Pac4jProperties properties,
										   ObjectProvider<CasConfiguration> casConfiguration) {
			super(properties, properties.getClient().getCas());
			this.casConfiguration = casConfiguration.getIfAvailable();
		}

		@Bean(name = "casClient")
		@ConditionalOnMissingBean
		@ConditionalOnProperty(prefix = Cas.PREFIX, name = "general.enabled", havingValue = "true")
		public CasClient casClient() {
			final CasClient casClient = new CasClient(casConfiguration) {

				@Override
				protected void clientInit() {
					super.clientInit();
					doClientInit(this);
				}

			};

			Optional.ofNullable(config.getCallbackUrl()).ifPresent(casClient::setCallbackUrl);

			afterClientInitialized(casClient, config.getGeneral());

			return casClient;
		}

		@Bean(name = "casRestFormClient")
		@ConditionalOnMissingBean
		@ConditionalOnProperty(prefix = Cas.PREFIX, name = "rest-form.enabled", havingValue = "true")
		public CasRestFormClient casRestFormClient() {
			final CasRestFormClient casRestFormClient = new CasRestFormClient();

			casRestFormClient.setConfiguration(casConfiguration);

			final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenHasText();

			propertyMapper.from(config.getRestForm()::getUsernameParameter).to(casRestFormClient::setUsernameParameter);
			propertyMapper.from(config.getRestForm()::getPasswordParameter).to(casRestFormClient::setPasswordParameter);

			afterClientInitialized(casRestFormClient, config.getRestForm());

			return casRestFormClient;
		}

		@Bean(name = "directCasClient")
		@ConditionalOnMissingBean
		@ConditionalOnProperty(prefix = Cas.PREFIX, name = "direct.enabled", havingValue = "true")
		public DirectCasClient directCasClient() {
			final DirectCasClient directCasClient = new DirectCasClient(casConfiguration) {

				@Override
				protected void clientInit() {
					super.clientInit();
					doClientInit(this);
				}

			};

			afterClientInitialized(directCasClient, config.getDirect());

			return directCasClient;
		}

		@Bean(name = "directCasProxyClient")
		@ConditionalOnMissingBean
		@ConditionalOnProperty(prefix = Cas.PREFIX, name = "direct-proxy.enabled", havingValue = "true")
		public DirectCasProxyClient directCasProxyClient() {
			final DirectCasProxyClient directCasProxyClient = new DirectCasProxyClient(casConfiguration,
					config.getPrefixUrl()) {

				@Override
				protected void clientInit() {
					super.clientInit();
					doClientInit(this);
				}

			};

			afterClientInitialized(directCasProxyClient, config.getDirectProxy());

			return directCasProxyClient;
		}

		@Bean(name = "casRestBasicAuthClient")
		@ConditionalOnMissingBean
		@ConditionalOnProperty(prefix = Cas.PREFIX, name = "rest-basic-auth.enabled", havingValue = "true")
		public CasRestBasicAuthClient casRestBasicAuthClient() {
			final CasRestBasicAuthClient casRestBasicAuthClient = new CasRestBasicAuthClient();

			casRestBasicAuthClient.setConfiguration(casConfiguration);

			final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenHasText();

			propertyMapper.from(config.getRestBasicAuth()::getHeaderName).to(casRestBasicAuthClient::setHeaderName);
			propertyMapper.from(config.getRestBasicAuth()::getPrefixHeader).to(casRestBasicAuthClient::setPrefixHeader);

			afterClientInitialized(casRestBasicAuthClient, config.getRestBasicAuth());

			return casRestBasicAuthClient;
		}

		protected void doClientInit(final BaseClient<TokenCredentials> client) {
			Optional.of(config.getProfileDefinition())
					.ifPresent((profileDefinition)->((CasAuthenticator) client.getAuthenticator()).setProfileDefinition(
							BeanUtils.instantiateClass(profileDefinition)));

			if(Validate.isNotEmpty(config.getAuthorizationGenerator())){
				final List<AuthorizationGenerator> authorizationGenerators = config.getAuthorizationGenerator().stream()
						.map(BeanUtils::instantiateClass).collect(Collectors.toList());

				client.setAuthorizationGenerators(authorizationGenerators);
			}
		}

	}

}
