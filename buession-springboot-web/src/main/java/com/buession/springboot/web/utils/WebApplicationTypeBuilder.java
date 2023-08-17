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
package com.buession.springboot.web.utils;

import org.springframework.boot.WebApplicationType;
import org.springframework.util.ClassUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yong.Teng
 * @since 2.3.0
 */
public class WebApplicationTypeBuilder {

	private final static Map<String, WebApplicationType> TYPE_MAP = new HashMap<>(2);

	static{
		TYPE_MAP.put("org.springframework.web.reactive.config.WebFluxConfigurationSupport",
				WebApplicationType.REACTIVE);
		TYPE_MAP.put("javax.servlet.Servlet", WebApplicationType.SERVLET);
	}

	public static WebApplicationType findType(){
		return findType(null);
	}

	public static WebApplicationType findType(final ClassLoader classLoader){
		for(Map.Entry<String, WebApplicationType> e : TYPE_MAP.entrySet()){
			try{
				ClassUtils.forName(e.getKey(), classLoader);
				return e.getValue();
			}catch(Exception ex){
				// Swallow and continue
			}
		}

		return WebApplicationType.NONE;
	}

}