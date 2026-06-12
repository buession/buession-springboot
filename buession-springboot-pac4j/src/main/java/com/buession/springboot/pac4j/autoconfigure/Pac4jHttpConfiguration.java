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
import com.buession.springboot.pac4j.config.Http;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.http.client.direct.*;
import org.pac4j.http.client.indirect.*;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Pac4j HTTP 自动配置类
 *
 * @author Yong.Teng
 * @since 2.0.0
 */
@AutoConfiguration(before = {Pac4jConfiguration.class})
@EnableConfigurationProperties(Pac4jProperties.class)
@ConditionalOnClass({FormClient.class})
public class Pac4jHttpConfiguration extends AbstractPac4jClientConfiguration<Http> {

	public Pac4jHttpConfiguration(Pac4jProperties properties) {
		super(properties, properties.getClient().getHttp());
	}

	/* Direct Client 开始 */

	@Bean(name = "cookieClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = Http.PREFIX, name = "cookie.enabled")
	public CookieClient cookieClient(
			@Qualifier("cookieClientAuthenticator") ObjectProvider<Authenticator> authenticator,
			ObjectProvider<Customizer<CookieClient>> customizers) {
		return new CookieClient(config.getCookie().getCookieName(), authenticator.getIfAvailable()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				afterDirectClientInitialized(this, config, config.getCookie());
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "directBasicAuthClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = Http.PREFIX, name = "direct-basic-auth.enabled")
	public DirectBasicAuthClient directBasicAuthClient(
			@Qualifier("directBasicAuthClientAuthenticator") ObjectProvider<Authenticator> authenticator,
			ObjectProvider<Customizer<DirectBasicAuthClient>> customizers) {
		return new DirectBasicAuthClient(authenticator.getIfAvailable()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				final Http.DirectBasicAuth directBasicAuth = config.getDirectBasicAuth();

				hasTextpropertyMapper.from(directBasicAuth::getRealmName).to(this::setRealmName);

				super.internalInit(forceReinit);
				afterDirectClientInitialized(this, config, directBasicAuth);
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "directBearerAuthClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = Http.PREFIX, name = "direct-bearer-auth.enabled")
	public DirectBearerAuthClient directBearerAuthClient(
			@Qualifier("directBearerAuthClientAuthenticator") ObjectProvider<Authenticator> authenticator,
			ObjectProvider<Customizer<DirectBearerAuthClient>> customizers) {
		return new DirectBearerAuthClient(authenticator.getIfAvailable()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				final Http.DirectBearerAuth directBearerAuth = config.getDirectBearerAuth();

				hasTextpropertyMapper.from(directBearerAuth::getRealmName).to(this::setRealmName);

				super.internalInit(forceReinit);
				afterDirectClientInitialized(this, config, directBearerAuth);
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "directDigestAuthClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = Http.PREFIX, name = "direct-digest-auth.enabled")
	public DirectDigestAuthClient directDigestAuthClient(
			@Qualifier("directDigestAuthClientAuthenticator") ObjectProvider<Authenticator> authenticator,
			ObjectProvider<Customizer<DirectDigestAuthClient>> customizers) {
		return new DirectDigestAuthClient(authenticator.getIfAvailable()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				final Http.DirectDigestAuth directDigestAuth = config.getDirectDigestAuth();

				hasTextpropertyMapper.from(directDigestAuth::getRealm).to(this::setRealm);

				super.internalInit(forceReinit);
				afterDirectClientInitialized(this, config, directDigestAuth);
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "directFormClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = Http.PREFIX, name = "direct-form.enabled")
	public DirectFormClient directFormClient(
			@Qualifier("directFormClientAuthenticator") ObjectProvider<Authenticator> authenticator,
			ObjectProvider<Customizer<DirectFormClient>> customizers) {
		return new DirectFormClient(authenticator.getIfAvailable()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				final Http.DirectForm form = config.getDirectForm();

				hasTextpropertyMapper.from(form::getUsernameParameter).to(this::setUsernameParameter);
				hasTextpropertyMapper.from(form::getPasswordParameter).to(this::setPasswordParameter);

				super.internalInit(forceReinit);
				afterDirectClientInitialized(this, config, form);
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "headerClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = Http.PREFIX, name = "header.enabled")
	public HeaderClient headerClient(
			@Qualifier("headerClientAuthenticator") ObjectProvider<Authenticator> authenticator,
			ObjectProvider<Customizer<HeaderClient>> customizers) {
		return new HeaderClient(config.getHeader().getHeaderName(), config.getHeader().getPrefixHeader(),
				authenticator.getIfAvailable()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				afterDirectClientInitialized(this, config, config.getHeader());
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "ipClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = Http.PREFIX, name = "ip.enabled")
	public IpClient ipClient(@Qualifier("ipClientAuthenticator") ObjectProvider<Authenticator> authenticator,
	                         ObjectProvider<Customizer<IpClient>> customizers) {
		return new IpClient(authenticator.getIfAvailable()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				afterDirectClientInitialized(this, config, config.getIp());
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "parameterClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = Http.PREFIX, name = "parameter.enabled")
	public ParameterClient parameterClient(
			@Qualifier("parameterClientAuthenticator") ObjectProvider<Authenticator> authenticator,
			ObjectProvider<Customizer<ParameterClient>> customizers) {
		return new ParameterClient(config.getParameter().getParameterName(), authenticator.getIfAvailable()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				final Http.Parameter parameter = config.getParameter();

				hasTextpropertyMapper.from(parameter::getSupportGetRequest).to(this::setSupportGetRequest);
				hasTextpropertyMapper.from(parameter::getSupportPostRequest).to(this::setSupportPostRequest);

				super.internalInit(forceReinit);
				afterDirectClientInitialized(this, config, parameter);
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "x509Client")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = Http.PREFIX, name = "x509.enabled")
	public X509Client x509Client(
			@Qualifier("x509ClientAuthenticator") ObjectProvider<Customizer<X509Client>> customizers) {
		return new X509Client() {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				afterDirectClientInitialized(this, config, config.getX509());
				customizer(this, customizers);
			}

		};
	}

	/* Direct Client 结束 */

	/* Indirect Client 开始 */

	@Bean(name = "formClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = Http.PREFIX, name = "form.enabled")
	public FormClient formClient(@Qualifier("formClientAuthenticator") ObjectProvider<Authenticator> authenticator,
	                             ObjectProvider<Customizer<FormClient>> customizers) {
		return new FormClient(config.getForm().getLoginUrl(), authenticator.getIfAvailable()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				final Http.Form form = config.getForm();

				hasTextpropertyMapper.from(form::getUsernameParameter).to(this::setUsernameParameter);
				hasTextpropertyMapper.from(form::getPasswordParameter).to(this::setPasswordParameter);

				super.internalInit(forceReinit);
				afterIndirectClientInitialized(this, config, form);
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "indirectBasicAuthClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = Http.PREFIX, name = "indirect-basic-auth.enabled")
	public IndirectBasicAuthClient indirectBasicAuthClient(
			@Qualifier("indirectBasicAuthClientAuthenticator") ObjectProvider<Authenticator> authenticator,
			ObjectProvider<Customizer<IndirectBasicAuthClient>> customizers) {
		return new IndirectBasicAuthClient(config.getIndirectBasicAuth().getRealmName(),
				authenticator.getIfAvailable()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				afterIndirectClientInitialized(this, config, config.getIndirectBasicAuth());
				customizer(this, customizers);
			}

		};
	}

	/* Indirect Client 结束 */

}
