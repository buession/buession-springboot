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
		final Http.Cookie cookie = config.getCookie();
		final CookieClient cookieClient = new CookieClient(cookie.getCookieName(), authenticator.getIfAvailable());

		afterDirectClientInitialized(cookieClient, config, cookie, customizers);

		return cookieClient;
	}

	@Bean(name = "directBasicAuthClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = Http.PREFIX, name = "direct-basic-auth.enabled")
	public DirectBasicAuthClient directBasicAuthClient(
			@Qualifier("directBasicAuthClientAuthenticator") ObjectProvider<Authenticator> authenticator,
			ObjectProvider<Customizer<DirectBasicAuthClient>> customizers) {
		final Http.DirectBasicAuth directBasicAuth = config.getDirectBasicAuth();
		final DirectBasicAuthClient directBasicAuthClient = new DirectBasicAuthClient(authenticator.getIfAvailable());

		hasTextpropertyMapper.from(directBasicAuth::getRealmName).to(directBasicAuthClient::setRealmName);
		afterDirectClientInitialized(directBasicAuthClient, config, directBasicAuth, customizers);

		return directBasicAuthClient;
	}

	@Bean(name = "directBearerAuthClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = Http.PREFIX, name = "direct-bearer-auth.enabled")
	public DirectBearerAuthClient directBearerAuthClient(
			@Qualifier("directBearerAuthClientAuthenticator") ObjectProvider<Authenticator> authenticator,
			ObjectProvider<Customizer<DirectBearerAuthClient>> customizers) {
		final Http.DirectBearerAuth directBearerAuth = config.getDirectBearerAuth();
		final DirectBearerAuthClient directBearerAuthClient =
				new DirectBearerAuthClient(authenticator.getIfAvailable());

		hasTextpropertyMapper.from(directBearerAuth::getRealmName).to(directBearerAuthClient::setRealmName);
		afterDirectClientInitialized(directBearerAuthClient, config, directBearerAuth, customizers);

		return directBearerAuthClient;
	}

	@Bean(name = "directDigestAuthClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = Http.PREFIX, name = "direct-digest-auth.enabled")
	public DirectDigestAuthClient directDigestAuthClient(
			@Qualifier("directDigestAuthClientAuthenticator") ObjectProvider<Authenticator> authenticator,
			ObjectProvider<Customizer<DirectDigestAuthClient>> customizers) {
		final Http.DirectDigestAuth directDigestAuth = config.getDirectDigestAuth();
		final DirectDigestAuthClient directDigestAuthClient =
				new DirectDigestAuthClient(authenticator.getIfAvailable());

		hasTextpropertyMapper.from(directDigestAuth::getRealm).to(directDigestAuthClient::setRealm);
		afterDirectClientInitialized(directDigestAuthClient, config, directDigestAuth, customizers);

		return directDigestAuthClient;
	}

	@Bean(name = "directFormClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = Http.PREFIX, name = "direct-form.enabled")
	public DirectFormClient directFormClient(
			@Qualifier("directFormClientAuthenticator") ObjectProvider<Authenticator> authenticator,
			ObjectProvider<Customizer<DirectFormClient>> customizers) {
		final Http.DirectForm directForm = config.getDirectForm();
		final DirectFormClient directFormClient = new DirectFormClient(authenticator.getIfAvailable());

		hasTextpropertyMapper.from(directForm::getUsernameParameter).to(directFormClient::setUsernameParameter);
		hasTextpropertyMapper.from(directForm::getPasswordParameter).to(directFormClient::setPasswordParameter);
		afterDirectClientInitialized(directFormClient, config, directForm, customizers);

		return directFormClient;
	}

	@Bean(name = "headerClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = Http.PREFIX, name = "header.enabled")
	public HeaderClient headerClient(
			@Qualifier("headerClientAuthenticator") ObjectProvider<Authenticator> authenticator,
			ObjectProvider<Customizer<HeaderClient>> customizers) {
		final Http.Header header = config.getHeader();
		final HeaderClient headerClient = new HeaderClient(header.getHeaderName(), header.getPrefixHeader(),
				authenticator.getIfAvailable());

		afterDirectClientInitialized(headerClient, config, header, customizers);

		return headerClient;
	}

	@Bean(name = "ipClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = Http.PREFIX, name = "ip.enabled")
	public IpClient ipClient(@Qualifier("ipClientAuthenticator") ObjectProvider<Authenticator> authenticator,
	                         ObjectProvider<Customizer<IpClient>> customizers) {
		final IpClient ipClient = new IpClient(authenticator.getIfAvailable());

		afterDirectClientInitialized(ipClient, config, config.getIp(), customizers);

		return ipClient;
	}

	@Bean(name = "parameterClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = Http.PREFIX, name = "parameter.enabled")
	public ParameterClient parameterClient(
			@Qualifier("parameterClientAuthenticator") ObjectProvider<Authenticator> authenticator,
			ObjectProvider<Customizer<ParameterClient>> customizers) {
		final Http.Parameter parameter = config.getParameter();
		final ParameterClient parameterClient = new ParameterClient(parameter.getParameterName(),
				authenticator.getIfAvailable());

		hasTextpropertyMapper.from(parameter::getSupportGetRequest).to(parameterClient::setSupportGetRequest);
		hasTextpropertyMapper.from(parameter::getSupportPostRequest).to(parameterClient::setSupportPostRequest);
		afterDirectClientInitialized(parameterClient, config, parameter, customizers);

		return parameterClient;
	}

	@Bean(name = "x509Client")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = Http.PREFIX, name = "x509.enabled")
	public X509Client x509Client(
			@Qualifier("x509ClientAuthenticator") ObjectProvider<Customizer<X509Client>> customizers) {
		final X509Client x509Client = new X509Client();

		afterDirectClientInitialized(x509Client, config, config.getX509(), customizers);

		return x509Client;
	}

	/* Direct Client 结束 */

	/* Indirect Client 开始 */

	@Bean(name = "formClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = Http.PREFIX, name = "form.enabled")
	public FormClient formClient(@Qualifier("formClientAuthenticator") ObjectProvider<Authenticator> authenticator,
	                             ObjectProvider<Customizer<FormClient>> customizers) {
		final Http.Form form = config.getForm();
		final FormClient formClient = new FormClient(form.getLoginUrl(), authenticator.getIfAvailable());

		hasTextpropertyMapper.from(form::getUsernameParameter).to(formClient::setUsernameParameter);
		hasTextpropertyMapper.from(form::getPasswordParameter).to(formClient::setPasswordParameter);
		afterIndirectClientInitialized(formClient, config, form, customizers);

		return formClient;
	}

	@Bean(name = "indirectBasicAuthClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = Http.PREFIX, name = "indirect-basic-auth.enabled")
	public IndirectBasicAuthClient indirectBasicAuthClient(
			@Qualifier("indirectBasicAuthClientAuthenticator") ObjectProvider<Authenticator> authenticator,
			ObjectProvider<Customizer<IndirectBasicAuthClient>> customizers) {
		final IndirectBasicAuthClient indirectBasicAuthClient =
				new IndirectBasicAuthClient(config.getIndirectBasicAuth().getRealmName(),
						authenticator.getIfAvailable());

		afterIndirectClientInitialized(indirectBasicAuthClient, config, config.getIndirectBasicAuth(), customizers);

		return indirectBasicAuthClient;
	}

	/* Indirect Client 结束 */

}
