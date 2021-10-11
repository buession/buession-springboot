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
 * | Copyright @ 2013-2021 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.web.autoconfigure;

import com.buession.core.utils.StringUtils;
import com.buession.core.validator.Validate;
import com.buession.springboot.web.web.ServerProperties;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yong.Teng
 */
public abstract class AbstractServerConfiguration implements ServerConfiguration {

	protected final static String HEADER_VARIABLE_IDENTIFIER = "$";

	protected final static char HEADER_VARIABLE_IDENTIFIER_CHAR = '$';

	@Autowired(required = false)
	protected ServerProperties serverProperties;

	protected static Map<String, String> buildHeaders(final Map<String, String> headers){
		final Map<String, String> result = new HashMap<>(headers.size());

		headers.forEach((key, value)->{
			if(StringUtils.startsWith(value, HEADER_VARIABLE_IDENTIFIER_CHAR)){
				String propertyName = value.substring(1);
				String propertyValue = System.getProperty(propertyName);

				if(Validate.hasText(propertyValue) == false){
					propertyValue = System.getenv(propertyName);
				}

				if(Validate.hasText(propertyValue)){
					result.put(key, propertyValue);
				}
			}else{
				result.put(key, value);
			}
		});

		return result;
	}

	protected static String buildServerInfo(final ServerProperties serverProperties, final String serverName){
		String s = serverName;
		StringBuffer sb = new StringBuffer();

		if(Validate.hasText(serverProperties.getServerInfoPrefix())){
			sb.append(serverProperties.getServerInfoPrefix());
		}

		if(Validate.hasText(serverProperties.getStripServerInfoPrefix()) && s.startsWith(serverProperties.getStripServerInfoPrefix())){
			s = s.substring(serverProperties.getStripServerInfoPrefix().length());
		}

		if(Validate.hasText(serverProperties.getStripServerInfoSuffix()) && s.endsWith(serverProperties.getStripServerInfoSuffix())){
			s = s.substring(0, s.length() - serverProperties.getStripServerInfoSuffix().length());
		}

		sb.append(s);

		if(Validate.hasText(serverProperties.getServerInfoSuffix())){
			sb.append(serverProperties.getServerInfoSuffix());
		}

		return sb.toString();
	}

}
