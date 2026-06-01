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
import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.springboot.pac4j.config.BaseClientConfig;
import com.buession.springboot.pac4j.config.DirectClientConfig;
import com.buession.springboot.pac4j.config.IndirectClientConfig;
import org.pac4j.core.client.BaseClient;
import org.pac4j.core.client.DirectClient;
import org.pac4j.core.client.IndirectClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;

/**
 * @author Yong.Teng
 * @since 2.0.0
 */
public abstract class AbstractPac4jClientConfiguration<C extends BaseClientConfig> {

	protected final static PropertyMapper nonNullpropertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

	protected final static PropertyMapper hasTextpropertyMapper = PropertyMapper.get().alwaysApplyingWhenHasText();

	/**
	 * Pac4j 配置
	 */
	protected final Pac4jProperties properties;

	/**
	 * Client 配置信息
	 */
	protected final C config;

	public AbstractPac4jClientConfiguration(Pac4jProperties properties, C config) {
		this.properties = properties;
		this.config = config;
	}

	protected <CF extends BaseClientConfig, BCF extends BaseClientConfig, CLIENT extends BaseClient> void afterClientInitialized(
			final CLIENT client, final CF config, final BCF clientConfig) {
		client.setMultiProfile(clientConfig.isMultiProfile());
		client.setSaveProfileInSession(clientConfig.getSaveProfileInSession());
		hasTextpropertyMapper.from(clientConfig::getName).to(client::setName);
		nonNullpropertyMapper.from(config::getCustomProperties).to(client::setCustomProperties);
	}

	protected <CF extends BaseClientConfig, ICF extends IndirectClientConfig, CLIENT extends IndirectClient> void afterIndirectClientInitialized(
			final CLIENT client, final CF config, final ICF clientConfig) {
		afterClientInitialized(client, config, clientConfig);
		hasTextpropertyMapper.from(clientConfig::getCallbackUrl).to(client::setCallbackUrl);
		nonNullpropertyMapper.from(clientConfig::getCheckAuthenticationAttempt)
				.to(client::setCheckAuthenticationAttempt);
		nonNullpropertyMapper.from(clientConfig::getAjaxRequestResolver).as(BeanUtils::instantiateClass)
				.to(client::setAjaxRequestResolver);
	}

	protected <CF extends BaseClientConfig, DCF extends DirectClientConfig, CLIENT extends DirectClient> void afterDirectClientInitialized(
			final CLIENT client, final CF config, final DCF clientConfig) {
		afterClientInitialized(client, config, clientConfig);
	}

	protected <CLIENT extends BaseClient> void customizer(final CLIENT client,
	                                                      final ObjectProvider<Customizer<CLIENT>> customizers) {
		customizers.orderedStream().forEach((customizer)->customizer.customize(client));
	}

}
