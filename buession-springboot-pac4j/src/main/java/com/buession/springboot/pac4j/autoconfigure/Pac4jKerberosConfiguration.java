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
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.kerberos.client.direct.DirectKerberosClient;
import org.pac4j.kerberos.client.indirect.IndirectKerberosClient;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Pac4j Kerberos 自动配置类
 *
 * @author Yong.Teng
 * @since 4.0.0
 */
@AutoConfiguration(before = {Pac4jConfiguration.class})
@EnableConfigurationProperties(Pac4jProperties.class)
@ConditionalOnClass({DirectKerberosClient.class})
public class Pac4jKerberosConfiguration extends AbstractPac4jClientConfiguration<Kerberos> {

	public Pac4jKerberosConfiguration(Pac4jProperties properties) {
		super(properties, properties.getClient().getKerberos());
	}

	/* Direct Client 开始 */

	@Bean(name = "directKerberosClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = Kerberos.PREFIX, name = "direct.enabled")
	public DirectKerberosClient directKerberosClient(
			@Qualifier("directKerberosClientAuthenticator") ObjectProvider<Authenticator> authenticator,
			ObjectProvider<Customizer<DirectKerberosClient>> customizers) {
		final DirectKerberosClient directKerberosClient = new DirectKerberosClient(authenticator.getIfAvailable());
		return directClientInitialized(directKerberosClient, config, config.getDirect(), customizers);
	}

	/* Direct Client 结束 */

	/* Indirect Client 开始 */

	@Bean(name = "indirectKerberosClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = Kerberos.PREFIX, name = "indirect.enabled")
	public IndirectKerberosClient indirectKerberosClient(
			@Qualifier("indirectKerberosClientAuthenticator") ObjectProvider<Authenticator> authenticator,
			ObjectProvider<Customizer<IndirectKerberosClient>> customizers) {
		final IndirectKerberosClient indirectKerberosClient =
				new IndirectKerberosClient(authenticator.getIfAvailable());

		return indirectClientInitialized(indirectKerberosClient, config, config.getIndirect(), customizers);
	}

	/* Indirect Client 结束 */

}
