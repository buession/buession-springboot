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
 * | Copyright @ 2013-2025 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.pac4j.config;

import org.pac4j.core.context.HttpConstants;

/**
 * Header 客户端配置基类
 *
 * @author Yong.Teng
 * @since 4.0.0
 */
public abstract class BaseHeaderConfig extends DirectClientConfig {

	/**
	 * 请求头名称
	 */
	private String headerName = HttpConstants.AUTHORIZATION_HEADER;

	/**
	 * 请求头前缀
	 */
	private String prefixHeader = HttpConstants.BASIC_HEADER_PREFIX;

	/**
	 * 构造函数
	 *
	 * @param name
	 * 		Client 名称
	 */
	public BaseHeaderConfig(String name) {
		super(name);
	}

	/**
	 * 返回请求头名称
	 *
	 * @return 请求头名称
	 */
	public String getHeaderName() {
		return headerName;
	}

	/**
	 * 设置请求头名称
	 *
	 * @param headerName
	 * 		请求头名称
	 */
	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	/**
	 * 返回请求头前缀
	 *
	 * @return 请求头前缀
	 */
	public String getPrefixHeader() {
		return prefixHeader;
	}

	/**
	 * 设置请求头前缀
	 *
	 * @param prefixHeader
	 * 		请求头前缀
	 */
	public void setPrefixHeader(String prefixHeader) {
		this.prefixHeader = prefixHeader;
	}

}
