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
 * | Copyright @ 2013-2023 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.pac4j.autoconfigure;

import com.buession.core.validator.Validate;
import com.buession.springboot.pac4j.config.BaseConfig;
import org.pac4j.core.client.BaseClient;
import org.pac4j.core.credentials.Credentials;

/**
 * @author Yong.Teng
 * @since 2.0.0
 */
public abstract class AbstractPac4jClientConfiguration<C extends BaseConfig> {

	protected Pac4jProperties properties;

	protected C config;

	public AbstractPac4jClientConfiguration(Pac4jProperties properties, C config){
		this.properties = properties;
		this.config = config;
	}

	protected void afterClientInitialized(final BaseClient<? extends Credentials> client,
										  final BaseConfig.BaseClientConfig config){
		client.setName(Validate.hasText(config.getName()) ? config.getName() : config.getDefaultName());

		if(config.getCustomProperties() != null){
			client.setCustomProperties(config.getCustomProperties());
		}
	}

}
