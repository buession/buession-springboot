/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * =================================================================================================
 *
 * This software consists of voluntary contributions made by many individuals on behalf of the
 * Apache Software Foundation. For more information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 * +------------------------------------------------------------------------------------------------+
 * | License: http://www.apache.org/licenses/LICENSE-2.0.txt 										|
 * | Author: Yong.Teng <webmaster@buession.com> 													|
 * | Copyright @ 2013-2020 Buession.com Inc.														|
 * +------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.web.web;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Web Server 配置
 *
 * @author Yong.Teng
 */
@ConfigurationProperties(prefix = "server")
public class ServerProperties {

	/**
	 * 自定义响应头
	 */
	private Map<String, String> responseHeaders = new LinkedHashMap<>(16, 0.8F);

	/**
	 * Server 信息响应头名称
	 */
	private String serverInfoName;

	/**
	 * Server 信息响应头前缀
	 */
	private String serverInfoPrefix;

	/**
	 * Server 信息响应头后缀
	 */
	private String serverInfoSuffix;

	/**
	 * 删除 Server 信息响应头前缀
	 */
	private String stripServerInfoPrefix;

	/**
	 * 删除 Server 信息响应头后缀
	 */
	private String stripServerInfoSuffix;

	public Map<String, String> getResponseHeaders(){
		return responseHeaders;
	}

	public void setResponseHeaders(Map<String, String> responseHeaders){
		this.responseHeaders = responseHeaders;
	}

	public String getServerInfoName(){
		return serverInfoName;
	}

	public void setServerInfoName(String serverInfoName){
		this.serverInfoName = serverInfoName;
	}

	public String getServerInfoPrefix(){
		return serverInfoPrefix;
	}

	public void setServerInfoPrefix(String serverInfoPrefix){
		this.serverInfoPrefix = serverInfoPrefix;
	}

	public String getServerInfoSuffix(){
		return serverInfoSuffix;
	}

	public void setServerInfoSuffix(String serverInfoSuffix){
		this.serverInfoSuffix = serverInfoSuffix;
	}

	public String getStripServerInfoPrefix(){
		return stripServerInfoPrefix;
	}

	public void setStripServerInfoPrefix(String stripServerInfoPrefix){
		this.stripServerInfoPrefix = stripServerInfoPrefix;
	}

	public String getStripServerInfoSuffix(){
		return stripServerInfoSuffix;
	}

	public void setStripServerInfoSuffix(String stripServerInfoSuffix){
		this.stripServerInfoSuffix = stripServerInfoSuffix;
	}

}
