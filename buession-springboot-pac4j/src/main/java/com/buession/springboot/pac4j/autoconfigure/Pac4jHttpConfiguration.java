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
import org.pac4j.http.credentials.authenticator.test.SimpleTestUsernamePasswordAuthenticator;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
	@ConditionalOnProperty(prefix = Http.PREFIX, name = "cookie.enabled", havingValue = "true")
	public CookieClient cookieClient(ObjectProvider<Authenticator> authenticator,
	                                 ObjectProvider<Customizer<CookieClient>> customizers) {
		return new CookieClient() {

			@Override
			protected void internalInit(final boolean forceReinit) {
				final Http.Cookie cookie = config.getCookie();

				super.internalInit(forceReinit);
				customizer(this, customizers);

				hasTextpropertyMapper.from(cookie::getCookieName).to(cookie::setCookieName);
				authenticator.ifAvailable(this::setAuthenticator);

				afterDirectClientInitialized(this, config, cookie);
			}

		};
	}

	@Bean(name = "directBasicAuthClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Http.PREFIX, name = "direct-basic-auth.enabled", havingValue = "true")
	public DirectBasicAuthClient directBasicAuthClient(ObjectProvider<Authenticator> authenticator,
	                                                   ObjectProvider<Customizer<DirectBasicAuthClient>> customizers) {
		return new DirectBasicAuthClient() {

			@Override
			protected void internalInit(final boolean forceReinit) {
				final Http.DirectBasicAuth directBasicAuth = config.getDirectBasicAuth();

				super.internalInit(forceReinit);
				customizer(this, customizers);

				hasTextpropertyMapper.from(directBasicAuth::getRealmName).to(this::setRealmName);
				authenticator.ifAvailable(this::setAuthenticator);

				afterDirectClientInitialized(this, config, directBasicAuth);
			}

		};
	}

	@Bean(name = "directBearerAuthClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Http.PREFIX, name = "direct-bearer-auth.enabled", havingValue = "true")
	public DirectBearerAuthClient directBearerAuthClient(ObjectProvider<Authenticator> authenticator,
	                                                     ObjectProvider<Customizer<DirectBearerAuthClient>> customizers) {
		return new DirectBearerAuthClient() {

			@Override
			protected void internalInit(final boolean forceReinit) {
				final Http.DirectBearerAuth directBearerAuth = config.getDirectBearerAuth();

				super.internalInit(forceReinit);
				customizer(this, customizers);

				hasTextpropertyMapper.from(directBearerAuth::getRealmName).to(this::setRealmName);
				authenticator.ifAvailable(this::setAuthenticator);

				afterDirectClientInitialized(this, config, directBearerAuth);
			}

		};
	}

	@Bean(name = "directDigestAuthClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Http.PREFIX, name = "direct-digest-auth.enabled", havingValue = "true")
	public DirectDigestAuthClient directDigestAuthClient(ObjectProvider<Authenticator> authenticator,
	                                                     ObjectProvider<Customizer<DirectDigestAuthClient>> customizers) {
		return new DirectDigestAuthClient() {

			@Override
			protected void internalInit(final boolean forceReinit) {
				final Http.DirectDigestAuth directDigestAuth = config.getDirectDigestAuth();

				super.internalInit(forceReinit);
				customizer(this, customizers);

				hasTextpropertyMapper.from(directDigestAuth::getRealm).to(this::setRealm);
				authenticator.ifAvailable(this::setAuthenticator);

				afterDirectClientInitialized(this, config, directDigestAuth);
			}

		};
	}

	@Bean(name = "directFormClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Http.PREFIX, name = "direct-form.enabled", havingValue = "true")
	public DirectFormClient directFormClient(ObjectProvider<Authenticator> authenticator,
	                                         ObjectProvider<Customizer<DirectFormClient>> customizers) {
		return new DirectFormClient() {

			@Override
			protected void internalInit(final boolean forceReinit) {
				final Http.DirectForm form = config.getDirectForm();

				super.internalInit(forceReinit);
				customizer(this, customizers);

				hasTextpropertyMapper.from(form::getUsernameParameter).to(this::setUsernameParameter);
				hasTextpropertyMapper.from(form::getPasswordParameter).to(this::setPasswordParameter);
				authenticator.ifAvailable(this::setAuthenticator);

				afterDirectClientInitialized(this, config, form);
			}

		};
	}

	@Bean(name = "headerClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Http.PREFIX, name = "header.enabled", havingValue = "true")
	public HeaderClient headerClient(ObjectProvider<Authenticator> authenticator,
	                                 ObjectProvider<Customizer<HeaderClient>> customizers) {
		return new HeaderClient() {

			@Override
			protected void internalInit(final boolean forceReinit) {
				final Http.Header header = config.getHeader();

				super.internalInit(forceReinit);
				customizer(this, customizers);

				hasTextpropertyMapper.from(header::getHeaderName).to(this::setHeaderName);
				hasTextpropertyMapper.from(header::getPrefixHeader).to(this::setPrefixHeader);
				authenticator.ifAvailable(this::setAuthenticator);

				afterDirectClientInitialized(this, config, header);
			}

		};
	}

	@Bean(name = "ipClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Http.PREFIX, name = "ip.enabled", havingValue = "true")
	public IpClient ipClient(ObjectProvider<Authenticator> authenticator,
	                         ObjectProvider<Customizer<IpClient>> customizers) {
		return new IpClient() {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
				authenticator.ifAvailable(this::setAuthenticator);
				afterDirectClientInitialized(this, config, config.getIp());
			}

		};
	}

	@Bean(name = "parameterClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Http.PREFIX, name = "parameter.enabled", havingValue = "true")
	public ParameterClient parameterClient(ObjectProvider<Authenticator> authenticator,
	                                       ObjectProvider<Customizer<ParameterClient>> customizers) {
		return new ParameterClient() {

			@Override
			protected void internalInit(final boolean forceReinit) {
				final Http.Parameter parameter = config.getParameter();

				super.internalInit(forceReinit);
				customizer(this, customizers);

				hasTextpropertyMapper.from(parameter::getParameterName).to(this::setParameterName);
				hasTextpropertyMapper.from(parameter::getSupportGetRequest).to(this::setSupportGetRequest);
				hasTextpropertyMapper.from(parameter::getSupportPostRequest).to(this::setSupportPostRequest);
				authenticator.ifAvailable(this::setAuthenticator);

				afterDirectClientInitialized(this, config, parameter);
			}

		};
	}

	@Bean(name = "x509Client")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Http.PREFIX, name = "x509.enabled", havingValue = "true")
	public X509Client x509Client(ObjectProvider<Customizer<X509Client>> customizers) {
		return new X509Client() {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
				afterDirectClientInitialized(this, config, config.getX509());
			}

		};
	}

	/* Direct Client 结束 */

	/* Indirect Client 开始 */

	@Bean(name = "formClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Http.PREFIX, name = "form.enabled", havingValue = "true")
	public FormClient formClient(ObjectProvider<Customizer<FormClient>> customizers) {
		return new FormClient(config.getForm().getLoginUrl(), new SimpleTestUsernamePasswordAuthenticator()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				final Http.Form form = config.getForm();

				super.internalInit(forceReinit);
				customizer(this, customizers);

				hasTextpropertyMapper.from(form::getUsernameParameter).to(this::setUsernameParameter);
				hasTextpropertyMapper.from(form::getPasswordParameter).to(this::setPasswordParameter);

				afterIndirectClientInitialized(this, config, form);
			}

		};
	}

	@Bean(name = "indirectBasicAuthClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Http.PREFIX, name = "indirect-basic-auth.enabled", havingValue = "true")
	public IndirectBasicAuthClient indirectBasicAuthClient(
			ObjectProvider<Customizer<IndirectBasicAuthClient>> customizers) {
		return new IndirectBasicAuthClient(config.getIndirectBasicAuth().getRealmName(),
				new SimpleTestUsernamePasswordAuthenticator()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
				afterIndirectClientInitialized(this, config, config.getIndirectBasicAuth());
			}

		};
	}

	/* Indirect Client 结束 */

}
