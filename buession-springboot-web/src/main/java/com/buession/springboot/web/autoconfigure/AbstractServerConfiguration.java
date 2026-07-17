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
package com.buession.springboot.web.autoconfigure;

import com.buession.core.utils.StringUtils;
import com.buession.core.validator.Validate;
import org.springframework.context.ApplicationContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Server Auto Configuration 基类
 *
 * @author Yong.Teng
 */
public abstract class AbstractServerConfiguration {

	protected final static char HEADER_VARIABLE_IDENTIFIER = '$';

	/**
	 * {@link ServerProperties}
	 */
	protected ServerProperties properties;

	/**
	 * 构造函数
	 *
	 * @param properties
	 *        {@link ServerProperties}
	 */
	public AbstractServerConfiguration(ServerProperties properties) {
		this.properties = properties;
	}

	protected static Map<String, String> buildHeaders(final ApplicationContext context,
	                                                  final Map<String, String> headers) {
		if(headers == null){
			return Collections.emptyMap();
		}else{
			final Map<String, String> result = new HashMap<>(headers.size());

			headers.forEach((name, value)->{
				if(value != null){
					if(value.length() > 1 && StringUtils.startsWith(value, HEADER_VARIABLE_IDENTIFIER)){
						String propertyName = value.substring(1);
						String propertyValue = context.getEnvironment().getProperty(propertyName);

						if(Validate.hasText(propertyValue)){
							result.put(name, propertyValue);
						}
					}else{
						result.put(name, value);
					}
				}
			});

			return result;
		}
	}

}
