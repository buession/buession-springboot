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

import com.buession.springboot.pac4j.config.BaseConfig;
import com.buession.springboot.pac4j.config.Http;
import org.pac4j.core.client.DirectClient;
import org.pac4j.core.client.IndirectClient;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.credentials.TokenCredentials;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.http.client.direct.*;
import org.pac4j.http.client.indirect.*;
import org.pac4j.http.credentials.DigestCredentials;
import org.pac4j.http.credentials.authenticator.test.SimpleTestUsernamePasswordAuthenticator;
import org.springframework.beans.BeanUtils;
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
	public CookieClient cookieClient(ObjectProvider<Authenticator<TokenCredentials>> authenticator) {
		final Http.Cookie cookie = config.getCookie();
		final CookieClient cookieClient = new CookieClient();

		hasTextpropertyMapper.from(cookie::getCookieName).to(cookie::setCookieName);
		authenticator.ifAvailable(cookieClient::setAuthenticator);

		initHttpDirectClient(cookieClient, cookie);

		return cookieClient;
	}

	@Bean(name = "directBasicAuthClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Http.PREFIX, name = "direct-basic-auth.enabled", havingValue = "true")
	public DirectBasicAuthClient directBasicAuthClient(
			ObjectProvider<Authenticator<UsernamePasswordCredentials>> authenticator) {
		final Http.DirectBasicAuth directBasicAuth = config.getDirectBasicAuth();
		final DirectBasicAuthClient directBasicAuthClient = new DirectBasicAuthClient();

		hasTextpropertyMapper.from(directBasicAuth::getRealmName).to(directBasicAuthClient::setRealmName);
		authenticator.ifAvailable(directBasicAuthClient::setAuthenticator);

		initHttpDirectClient(directBasicAuthClient, directBasicAuth);

		return directBasicAuthClient;
	}

	@Bean(name = "directBearerAuthClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Http.PREFIX, name = "direct-bearer-auth.enabled", havingValue = "true")
	public DirectBearerAuthClient directBearerAuthClient(
			ObjectProvider<Authenticator<TokenCredentials>> authenticator) {
		final Http.DirectBearerAuth directBearerAuth = config.getDirectBearerAuth();
		final DirectBearerAuthClient directBearerAuthClient = new DirectBearerAuthClient();

		hasTextpropertyMapper.from(directBearerAuth::getRealmName).to(directBearerAuthClient::setRealmName);
		authenticator.ifAvailable(directBearerAuthClient::setAuthenticator);

		initHttpDirectClient(directBearerAuthClient, directBearerAuth);

		return directBearerAuthClient;
	}

	@Bean(name = "directDigestAuthClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Http.PREFIX, name = "direct-digest-auth.enabled", havingValue = "true")
	public DirectDigestAuthClient directDigestAuthClient(
			ObjectProvider<Authenticator<DigestCredentials>> authenticator) {
		final Http.DirectDigestAuth directDigestAuth = config.getDirectDigestAuth();
		final DirectDigestAuthClient directDigestAuthClient = new DirectDigestAuthClient();

		hasTextpropertyMapper.from(directDigestAuth::getRealm).to(directDigestAuthClient::setRealm);
		authenticator.ifAvailable(directDigestAuthClient::setAuthenticator);

		initHttpDirectClient(directDigestAuthClient, directDigestAuth);

		return directDigestAuthClient;
	}

	@Bean(name = "directFormClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Http.PREFIX, name = "direct-form.enabled", havingValue = "true")
	public DirectFormClient directFormClient(ObjectProvider<Authenticator<UsernamePasswordCredentials>> authenticator) {
		final Http.DirectForm form = config.getDirectForm();
		final DirectFormClient directFormClient = new DirectFormClient();

		hasTextpropertyMapper.from(form::getUsernameParameter).to(directFormClient::setUsernameParameter);
		hasTextpropertyMapper.from(form::getPasswordParameter).to(directFormClient::setPasswordParameter);
		authenticator.ifAvailable(directFormClient::setAuthenticator);

		initHttpDirectClient(directFormClient, form);

		return directFormClient;
	}

	@Bean(name = "headerClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Http.PREFIX, name = "header.enabled", havingValue = "true")
	public HeaderClient headerClient(ObjectProvider<Authenticator<TokenCredentials>> authenticator) {
		final Http.Header header = config.getHeader();
		final HeaderClient headerClient = new HeaderClient();

		hasTextpropertyMapper.from(header::getHeaderName).to(headerClient::setHeaderName);
		hasTextpropertyMapper.from(header::getPrefixHeader).to(headerClient::setPrefixHeader);
		authenticator.ifAvailable(headerClient::setAuthenticator);

		initHttpDirectClient(headerClient, header);

		return headerClient;
	}

	@Bean(name = "ipClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Http.PREFIX, name = "ip.enabled", havingValue = "true")
	public IpClient ipClient(ObjectProvider<Authenticator<TokenCredentials>> authenticator) {
		final Http.Ip ip = config.getIp();
		final IpClient ipClient = new IpClient();

		authenticator.ifAvailable(ipClient::setAuthenticator);

		initHttpDirectClient(ipClient, ip);

		return ipClient;
	}

	@Bean(name = "parameterClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Http.PREFIX, name = "parameter.enabled", havingValue = "true")
	public ParameterClient parameterClient(ObjectProvider<Authenticator<TokenCredentials>> authenticator) {
		final Http.Parameter parameter = config.getParameter();
		final ParameterClient parameterClient = new ParameterClient();

		hasTextpropertyMapper.from(parameter::getParameterName).to(parameterClient::setParameterName);
		hasTextpropertyMapper.from(parameter::getSupportGetRequest).to(parameterClient::setSupportGetRequest);
		hasTextpropertyMapper.from(parameter::getSupportPostRequest).to(parameterClient::setSupportPostRequest);
		authenticator.ifAvailable(parameterClient::setAuthenticator);

		initHttpDirectClient(parameterClient, parameter);

		return parameterClient;
	}

	@Bean(name = "x509Client")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Http.PREFIX, name = "x509.enabled", havingValue = "true")
	public X509Client x509Client() {
		final Http.X509 x509 = config.getX509();
		final X509Client x509Client = new X509Client();

		initHttpDirectClient(x509Client, x509);

		return x509Client;
	}

	/* Direct Client 结束 */

	/* Indirect Client 开始 */

	@Bean(name = "formClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Http.PREFIX, name = "form.enabled", havingValue = "true")
	public FormClient formClient() {
		final SimpleTestUsernamePasswordAuthenticator usernamePasswordAuthenticator = new SimpleTestUsernamePasswordAuthenticator();
		final Http.Form form = config.getForm();
		final FormClient formClient = new FormClient(form.getLoginUrl(), usernamePasswordAuthenticator);

		hasTextpropertyMapper.from(form::getUsernameParameter).to(formClient::setUsernameParameter);
		hasTextpropertyMapper.from(form::getPasswordParameter).to(formClient::setPasswordParameter);
		propertyMapper.from(config::getAjaxRequestResolver).as(BeanUtils::instantiateClass)
				.to(formClient::setAjaxRequestResolver);

		initHttpIndirectClient(formClient, form);

		return formClient;
	}

	@Bean(name = "indirectBasicAuthClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = Http.PREFIX, name = "indirect-basic-auth.enabled", havingValue = "true")
	public IndirectBasicAuthClient indirectBasicAuthClient() {
		final SimpleTestUsernamePasswordAuthenticator usernamePasswordAuthenticator = new SimpleTestUsernamePasswordAuthenticator();
		final Http.IndirectBasicAuth indirectBasicAuth = config.getIndirectBasicAuth();
		final IndirectBasicAuthClient indirectBasicAuthClient = new IndirectBasicAuthClient(
				indirectBasicAuth.getRealmName(), usernamePasswordAuthenticator);

		propertyMapper.from(config::getAjaxRequestResolver).as(BeanUtils::instantiateClass)
				.to(indirectBasicAuthClient::setAjaxRequestResolver);

		initHttpIndirectClient(indirectBasicAuthClient, indirectBasicAuth);

		return indirectBasicAuthClient;
	}

	/* Indirect Client 结束 */

	protected void initHttpDirectClient(final DirectClient<? extends Credentials> client,
										final BaseConfig.BaseClientConfig clientConfig) {
		afterClientInitialized(client, config, clientConfig);
	}

	protected void initHttpIndirectClient(final IndirectClient<? extends Credentials> client,
										  final BaseConfig.BaseClientConfig clientConfig) {
		propertyMapper.from(config::getCallbackUrl).to(client::setCallbackUrl);
		afterClientInitialized(client, config, clientConfig);
	}

}
