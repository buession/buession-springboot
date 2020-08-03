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
 * | Copyright @ 2013-2019 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.web;

import com.buession.core.validator.Validate;
import com.buession.springboot.web.web.ServerProperties;
import org.junit.Test;

/**
 * @author Yong.Teng
 */
public class StringUtils {

	@Test
	public void substr(){
		ServerProperties serverProperties = new ServerProperties();
		String str = "liangvi-web-s-6fb77bc686-qsnj4";

		// httpProperties.setServerInfoPrefix("test-");
		//httpProperties.setServerInfoSuffix("-aaa");

		serverProperties.setStripServerInfoPrefix("liangvi-web-s-");
		serverProperties.setStripServerInfoSuffix("j4");

		System.out.println(buildServerInfo(serverProperties, str));
	}

	private final static String buildServerInfo(final ServerProperties serverProperties, final String serverName){
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
