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
import org.pac4j.kerberos.client.direct.DirectKerberosClient;
import org.pac4j.kerberos.client.indirect.IndirectKerberosClient;
import org.springframework.beans.factory.ObjectProvider;
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
	public DirectKerberosClient directKerberosClient(ObjectProvider<Customizer<DirectKerberosClient>> customizers) {
		return new DirectKerberosClient() {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
				afterDirectClientInitialized(this, config, config.getDirect());
			}

		};
	}

	/* Direct Client 结束 */

	/* Indirect Client 开始 */

	@Bean(name = "indirectKerberosClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = Kerberos.PREFIX, name = "indirect.enabled")
	public IndirectKerberosClient indirectKerberosClient(
			ObjectProvider<Customizer<IndirectKerberosClient>> customizers) {
		return new IndirectKerberosClient() {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
				afterIndirectClientInitialized(this, config, config.getIndirect());
			}

		};
	}

	/* Indirect Client 结束 */

}
