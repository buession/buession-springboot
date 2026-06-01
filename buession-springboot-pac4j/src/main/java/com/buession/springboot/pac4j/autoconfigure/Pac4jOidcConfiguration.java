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
 * | Copyright @ 2013-2026 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.pac4j.autoconfigure;

import com.buession.core.Customizer;
import com.buession.springboot.pac4j.config.Oidc;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.oauth2.sdk.auth.ClientAuthenticationMethod;
import com.nimbusds.oauth2.sdk.pkce.CodeChallengeMethod;
import org.pac4j.oidc.client.*;
import org.pac4j.oidc.config.AppleOidcConfiguration;
import org.pac4j.oidc.config.AzureAd2OidcConfiguration;
import org.pac4j.oidc.config.KeycloakOidcConfiguration;
import org.pac4j.oidc.config.OidcConfiguration;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.util.stream.Collectors;

/**
 * Pac4j OIDC 自动配置类
 *
 * @author Yong.Teng
 * @since 4.0.0
 */
@AutoConfiguration(before = {Pac4jConfiguration.class})
@EnableConfigurationProperties(Pac4jProperties.class)
@ConditionalOnClass({OidcClient.class})
public class Pac4jOidcConfiguration extends AbstractPac4jClientConfiguration<Oidc> {

	public Pac4jOidcConfiguration(Pac4jProperties properties) {
		super(properties, properties.getClient().getOidc());
	}

	@Bean(name = "appleOidcClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = Oidc.PREFIX, name = "apple.enabled")
	public AppleClient appleOidcClient(ObjectProvider<Customizer<AppleClient>> customizers) {
		final AppleOidcConfiguration appleOidcConfiguration = new AppleOidcConfiguration();

		if(config.getApple() != null){
			Oidc.Apple apple = config.getApple();

			applyCommonConfiguration(apple, appleOidcConfiguration);
			nonNullpropertyMapper.from(apple::getDiscoveryURI).to(appleOidcConfiguration::setDiscoveryURI);
			nonNullpropertyMapper.from(apple::getPrivateKeyID).to(appleOidcConfiguration::setPrivateKeyID);
			nonNullpropertyMapper.from(apple::getTeamId).to(appleOidcConfiguration::setTeamID);
			nonNullpropertyMapper.from(apple::getStore).to(appleOidcConfiguration::setStore);
			nonNullpropertyMapper.from(apple::getTimeout).to(appleOidcConfiguration::setTimeout);
		}

		return new AppleClient(appleOidcConfiguration) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				afterIndirectClientInitialized(this, config, config.getApple());
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "azureAd2OidcClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = Oidc.PREFIX, name = "azuread2.enabled")
	public AzureAd2Client azureAd2OidcClient(ObjectProvider<Customizer<AzureAd2Client>> customizers) {
		final AzureAd2OidcConfiguration azureAd2OidcConfiguration = new AzureAd2OidcConfiguration();

		if(config.getAzureAd2() != null){
			Oidc.AzureAd2 keycloak = config.getAzureAd2();

			applyCommonConfiguration(keycloak, azureAd2OidcConfiguration);
			nonNullpropertyMapper.from(keycloak::getTenant).to(azureAd2OidcConfiguration::setTenant);
		}

		return new AzureAd2Client(azureAd2OidcConfiguration) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				afterIndirectClientInitialized(this, config, config.getAzureAd2());
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "googleOidcClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = Oidc.PREFIX, name = "google.enabled")
	public GoogleOidcClient googleOidcClient(ObjectProvider<Customizer<GoogleOidcClient>> customizers) {
		final OidcConfiguration oidcConfiguration = new OidcConfiguration();

		if(config.getGoogle() != null){
			applyCommonConfiguration(config.getGoogle(), oidcConfiguration);
		}

		return new GoogleOidcClient(oidcConfiguration) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				afterIndirectClientInitialized(this, config, config.getGoogle());
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "keycloakOidcClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = Oidc.PREFIX, name = "keycloak.enabled")
	public KeycloakOidcClient keycloakOidcClient(ObjectProvider<Customizer<KeycloakOidcClient>> customizers) {
		final KeycloakOidcConfiguration keycloakOidcConfiguration = new KeycloakOidcConfiguration();

		if(config.getKeycloak() != null){
			Oidc.Keycloak keycloak = config.getKeycloak();

			applyCommonConfiguration(keycloak, keycloakOidcConfiguration);
			nonNullpropertyMapper.from(keycloak::getRealm).to(keycloakOidcConfiguration::setRealm);
			nonNullpropertyMapper.from(keycloak::getBaseUri).to(keycloakOidcConfiguration::setBaseUri);
		}

		return new KeycloakOidcClient(keycloakOidcConfiguration) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				afterIndirectClientInitialized(this, config, config.getKeycloak());
				customizer(this, customizers);
			}

		};
	}

	private void applyCommonConfiguration(final Oidc.BaseOidcClientConfig config,
	                                      final OidcConfiguration oidcConfiguration) {
		nonNullpropertyMapper.from(config::getFederation).to(oidcConfiguration::setFederation);
		nonNullpropertyMapper.from(config::getRpJwks).to(oidcConfiguration::setRpJwks);
		nonNullpropertyMapper.from(config::getSecret).to(oidcConfiguration::setSecret);
		nonNullpropertyMapper.from(config::getLogoutUrl).to(oidcConfiguration::setLogoutUrl);
		nonNullpropertyMapper.from(config::getLoginHint).to(oidcConfiguration::setLoginHint);
		nonNullpropertyMapper.from(config::getConnectTimeout).asInt(Duration::toMillis)
				.to(oidcConfiguration::setConnectTimeout);
		nonNullpropertyMapper.from(config::getReadTimeout).asInt(Duration::toMillis)
				.to(oidcConfiguration::setReadTimeout);
		nonNullpropertyMapper.from(config::getClientAuthenticationMethod)
				.as((v)->ClientAuthenticationMethod.parse(v.getValue()))
				.to(oidcConfiguration::setClientAuthenticationMethod);
		nonNullpropertyMapper.from(config::getSupportedClientAuthenticationMethods)
				.as((v)->v.stream().map((s)->ClientAuthenticationMethod.parse(s.getValue())).collect(
						Collectors.toSet())).to(oidcConfiguration::setSupportedClientAuthenticationMethods);
		nonNullpropertyMapper.from(config::getClientSecretJwtClientAuthnMethodConfig)
				.to(oidcConfiguration::setClientSecretJwtClientAuthnMethodConfig);
		nonNullpropertyMapper.from(config::getPkceMethod).as((v)->CodeChallengeMethod.parse(v.getValue()))
				.to(oidcConfiguration::setPkceMethod);
		nonNullpropertyMapper.from(config::getRequestObjectSigningAlgorithm)
				.as((v)->JWSAlgorithm.parse(v.getValue()))
				.to(oidcConfiguration::setRequestObjectSigningAlgorithm);
		nonNullpropertyMapper.from(config::getResponseMode).to(oidcConfiguration::setResponseMode);
		nonNullpropertyMapper.from(config::getScope).to(oidcConfiguration::setScope);
		nonNullpropertyMapper.from(config::getStateGenerator).as(BeanUtils::instantiateClass)
				.to(oidcConfiguration::setStateGenerator);
		nonNullpropertyMapper.from(config::getCodeVerifierGenerator).as(BeanUtils::instantiateClass)
				.to(oidcConfiguration::setCodeVerifierGenerator);
		nonNullpropertyMapper.from(config::getValueRetriever).as(BeanUtils::instantiateClass)
				.to(oidcConfiguration::setValueRetriever);
		nonNullpropertyMapper.from(config::getOpMetadataResolver).as(BeanUtils::instantiateClass)
				.to(oidcConfiguration::setOpMetadataResolver);
		nonNullpropertyMapper.from(config::getWithState).to(oidcConfiguration::setWithState);
		nonNullpropertyMapper.from(config::getExpireSessionWithToken).to(oidcConfiguration::setExpireSessionWithToken);
		nonNullpropertyMapper.from(config::getTokenExpirationAdvance).asInt(Duration::toMillis)
				.to(oidcConfiguration::setTokenExpirationAdvance);
		nonNullpropertyMapper.from(config::getAllowUnsignedIdTokens).to(oidcConfiguration::setAllowUnsignedIdTokens);
		nonNullpropertyMapper.from(config::getIncludeAccessTokenClaimsInProfile)
				.to(oidcConfiguration::setIncludeAccessTokenClaimsInProfile);
		nonNullpropertyMapper.from(config::getUseNonce).to(oidcConfiguration::setUseNonce);
		nonNullpropertyMapper.from(config::getUseNonceOnRefresh).to(oidcConfiguration::setUseNonceOnRefresh);
		nonNullpropertyMapper.from(config::getCallUserInfoEndpoint).to(oidcConfiguration::setCallUserInfoEndpoint);
		nonNullpropertyMapper.from(config::getDisablePkce).to(oidcConfiguration::setDisablePkce);
		nonNullpropertyMapper.from(config::getLogoutValidation).to(oidcConfiguration::setLogoutValidation);
		nonNullpropertyMapper.from(config::getPushedAuthorizationRequest)
				.to(oidcConfiguration::setPushedAuthorizationRequest);
		nonNullpropertyMapper.from(config::getMaxAge).to(oidcConfiguration::setMaxAge);
		nonNullpropertyMapper.from(config::getMaxClockSkew).to(oidcConfiguration::setMaxClockSkew);
	}

}
