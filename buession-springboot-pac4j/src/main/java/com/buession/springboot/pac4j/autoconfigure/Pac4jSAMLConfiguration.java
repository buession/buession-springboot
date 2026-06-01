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
import com.buession.springboot.pac4j.config.Kerberos;
import com.buession.springboot.pac4j.config.Saml;
import org.pac4j.saml.client.SAML2Client;
import org.pac4j.saml.config.SAML2Configuration;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.time.Duration;

/**
 * Pac4j SAML 自动配置类
 *
 * @author Yong.Teng
 * @since 4.0.0
 */
@AutoConfiguration(before = {Pac4jConfiguration.class})
@EnableConfigurationProperties(Pac4jProperties.class)
@ConditionalOnClass({SAML2Client.class})
public class Pac4jSAMLConfiguration extends AbstractPac4jClientConfiguration<Saml> {

	public Pac4jSAMLConfiguration(Pac4jProperties properties) {
		super(properties, properties.getClient().getSaml());
	}

	@Bean(name = "saml2Client")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = Kerberos.PREFIX, name = "saml.enabled")
	public SAML2Client saml2Client(ObjectProvider<Customizer<SAML2Client>> customizers) {
		final SAML2Configuration saml2Configuration = new SAML2Configuration();

		if(config != null){
			nonNullpropertyMapper.from(config::getProviderName).to(saml2Configuration::setProviderName);
			nonNullpropertyMapper.from(config::getIdentityProviderMetadataConnectTimeout).asInt(Duration::toMillis)
					.to(saml2Configuration::setIdentityProviderMetadataConnectTimeout);
			nonNullpropertyMapper.from(config::getIdentityProviderMetadataReadTimeout).asInt(Duration::toMillis)
					.to(saml2Configuration::setIdentityProviderMetadataReadTimeout);
			nonNullpropertyMapper.from(config::getIdentityProviderMetadata)
					.to(saml2Configuration::setIdentityProviderMetadataResource);
			nonNullpropertyMapper.from(config::getServiceProviderMetadata)
					.to(saml2Configuration::setServiceProviderMetadataResource);
			nonNullpropertyMapper.from(config::getIdentityProviderMetadataResolver).as(BeanUtils::instantiateClass)
					.to(saml2Configuration::setIdentityProviderMetadataResolver);
			nonNullpropertyMapper.from(config::getSupportedProtocols).to(saml2Configuration::setSupportedProtocols);
			nonNullpropertyMapper.from(config::getAttributeAsId).to(saml2Configuration::setAttributeAsId);
			nonNullpropertyMapper.from(config::getNameIdAttribute).to(saml2Configuration::setNameIdAttribute);
			nonNullpropertyMapper.from(config::getMappedAttributes).to(saml2Configuration::setMappedAttributes);
			nonNullpropertyMapper.from(config::getSingleSignOutServiceUrl)
					.to(saml2Configuration::setSingleSignOutServiceUrl);
			nonNullpropertyMapper.from(config::getRequestInitiatorUrl).to(saml2Configuration::setRequestInitiatorUrl);
			nonNullpropertyMapper.from(config::getAssertionConsumerServiceUrl)
					.to(saml2Configuration::setAssertionConsumerServiceUrl);
			nonNullpropertyMapper.from(config::getIdentityProviderEntityId)
					.to(saml2Configuration::setIdentityProviderEntityId);
			nonNullpropertyMapper.from(config::getServiceProviderEntityId)
					.to(saml2Configuration::setServiceProviderEntityId);
			nonNullpropertyMapper.from(config::getMaximumAuthenticationLifetime)
					.to(saml2Configuration::setMaximumAuthenticationLifetime);
			nonNullpropertyMapper.from(config::getAcceptedSkew).to(saml2Configuration::setAcceptedSkew);
			nonNullpropertyMapper.from(config::getForceAuth).to(saml2Configuration::setForceAuth);
			nonNullpropertyMapper.from(config::getPassive).to(saml2Configuration::setPassive);
			nonNullpropertyMapper.from(config::getPartialLogoutTreatedAsSuccess)
					.to(saml2Configuration::setPartialLogoutTreatedAsSuccess);
			nonNullpropertyMapper.from(config::getComparisonType).to(saml2Configuration::setComparisonType);
			nonNullpropertyMapper.from(config::getAuthnRequestBindingType)
					.to(saml2Configuration::setAuthnRequestBindingType);
			nonNullpropertyMapper.from(config::getAuthnRequestSubjectNameId)
					.to(saml2Configuration::setAuthnRequestSubjectNameId);
			nonNullpropertyMapper.from(config::getAuthnRequestSubjectNameIdFormat)
					.to(saml2Configuration::setAuthnRequestSubjectNameIdFormat);
			nonNullpropertyMapper.from(config::getAuthnContextClassRefs)
					.to(saml2Configuration::setAuthnContextClassRefs);
			nonNullpropertyMapper.from(config::getAuthnRequestSigned).to(saml2Configuration::setAuthnRequestSigned);
			nonNullpropertyMapper.from(config::getAuthnRequestBuilder).as(BeanUtils::instantiateClass)
					.to(saml2Configuration::setSamlAuthnRequestBuilder);
			nonNullpropertyMapper.from(config::getResponseBindingType).to(saml2Configuration::setResponseBindingType);
			nonNullpropertyMapper.from(config::getSpLogoutRequestBindingType)
					.to(saml2Configuration::setSpLogoutRequestBindingType);
			nonNullpropertyMapper.from(config::getSpLogoutResponseBindingType)
					.to(saml2Configuration::setSpLogoutResponseBindingType);
			nonNullpropertyMapper.from(config::getSpLogoutRequestSigned)
					.to(saml2Configuration::setSpLogoutRequestSigned);
			nonNullpropertyMapper.from(config::getNameIdPolicyFormat).to(saml2Configuration::setNameIdPolicyFormat);
			nonNullpropertyMapper.from(config::getUseNameQualifier).to(saml2Configuration::setUseNameQualifier);
			nonNullpropertyMapper.from(config::getSignMetadata).to(saml2Configuration::setSignMetadata);
			nonNullpropertyMapper.from(config::getForceServiceProviderMetadataGeneration)
					.to(saml2Configuration::setForceServiceProviderMetadataGeneration);
			nonNullpropertyMapper.from(config::getNameIdPolicyAllowCreate)
					.to(saml2Configuration::setNameIdPolicyAllowCreate);
			nonNullpropertyMapper.from(config::getAttributeConverter).as(BeanUtils::instantiateClass)
					.to(saml2Configuration::setSamlAttributeConverter);
			nonNullpropertyMapper.from(config::getMessageStoreFactory).as(BeanUtils::instantiateClass)
					.to(saml2Configuration::setSamlMessageStoreFactory);
			nonNullpropertyMapper.from(config::getMetadataGenerator).as(BeanUtils::instantiateClass)
					.to(saml2Configuration::setMetadataGenerator);
			nonNullpropertyMapper.from(config::getCredentialProvider).as(BeanUtils::instantiateClass)
					.to(saml2Configuration::setCredentialProvider);
			nonNullpropertyMapper.from(config::getBlackListedSignatureSigningAlgorithms)
					.to(saml2Configuration::setBlackListedSignatureSigningAlgorithms);
			nonNullpropertyMapper.from(config::getSignatureAlgorithms).to(saml2Configuration::setSignatureAlgorithms);
			nonNullpropertyMapper.from(config::getSignatureReferenceDigestMethods)
					.to(saml2Configuration::setSignatureReferenceDigestMethods);
			nonNullpropertyMapper.from(config::getSignatureCanonicalizationAlgorithm)
					.to(saml2Configuration::setSignatureCanonicalizationAlgorithm);
			nonNullpropertyMapper.from(config::getDefaultIdentityProviderMetadataResolver)
					.as(BeanUtils::instantiateClass)
					.to(saml2Configuration::setDefaultIdentityProviderMetadataResolverSupplier);
			nonNullpropertyMapper.from(config::getWantsAssertionsSigned)
					.to(saml2Configuration::setWantsAssertionsSigned);
			nonNullpropertyMapper.from(config::getWantsResponsesSigned).to(saml2Configuration::setWantsResponsesSigned);
			nonNullpropertyMapper.from(config::getAllSignatureValidationDisabled)
					.to(saml2Configuration::setAllSignatureValidationDisabled);
			nonNullpropertyMapper.from(config::getResponseDestinationAttributeMandatory)
					.to(saml2Configuration::setResponseDestinationAttributeMandatory);
			nonNullpropertyMapper.from(config::getAssertionConsumerServiceIndex)
					.to(saml2Configuration::setAssertionConsumerServiceIndex);
			nonNullpropertyMapper.from(config::getAttributeConsumingServiceIndex)
					.to(saml2Configuration::setAttributeConsumingServiceIndex);
			nonNullpropertyMapper.from(config::getPostLogoutUrl).to(saml2Configuration::setPostLogoutURL);
			nonNullpropertyMapper.from(config::getIssuerFormat).to(saml2Configuration::setIssuerFormat);

		}

		return new SAML2Client(saml2Configuration) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				nonNullpropertyMapper.from(config::getSignatureTrustEngineProvider).as(BeanUtils::instantiateClass)
						.to(this::setSignatureTrustEngineProvider);
				nonNullpropertyMapper.from(config::getLogoutValidator).as(BeanUtils::instantiateClass)
						.to(this::setLogoutValidator);
				nonNullpropertyMapper.from(config::getAuthnResponseValidator).as(BeanUtils::instantiateClass)
						.to(this::setAuthnResponseValidator);
				nonNullpropertyMapper.from(config::getSignatureSigningParametersProvider)
						.as(BeanUtils::instantiateClass).to(this::setSignatureSigningParametersProvider);
				nonNullpropertyMapper.from(config::getServiceProviderMetadataResolver).as(BeanUtils::instantiateClass)
						.to(this::setServiceProviderMetadataResolver);
				nonNullpropertyMapper.from(config::getDecrypter).as(BeanUtils::instantiateClass).to(this::setDecrypter);
				nonNullpropertyMapper.from(config::getStateGenerator).as(BeanUtils::instantiateClass)
						.to(this::setStateGenerator);
				nonNullpropertyMapper.from(config::getReplayCache).as(BeanUtils::instantiateClass)
						.to(this::setReplayCache);
				nonNullpropertyMapper.from(config::getSoapPipelineProvider).as(BeanUtils::instantiateClass)
						.to(this::setSoapPipelineProvider);
				nonNullpropertyMapper.from(config::getLogoutRequestMessageSender).as(BeanUtils::instantiateClass)
						.to(this::setLogoutRequestMessageSender);

				super.internalInit(forceReinit);
				afterClientInitialized(this, config, config);
				customizer(this, customizers);
			}

		};
	}

}
