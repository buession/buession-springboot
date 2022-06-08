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
package com.buession.springboot.web.reactive.filter;

import com.buession.core.validator.Validate;

/**
 * Server 信息 Filter
 *
 * @author Yong.Teng
 * @see com.buession.web.reactive.filter.ServerInfoFilter
 * @since 2.0.0
 */
public class ServerInfoFilter extends com.buession.web.reactive.filter.ServerInfoFilter {

	/**
	 * 前缀
	 */
	private final String prefix;

	/**
	 * 后缀
	 */
	private final String suffix;

	/**
	 * 删除前缀
	 */
	private final String stripPrefix;

	/**
	 * 删除后缀
	 */
	private final String stripSuffix;

	/**
	 * 构造函数
	 *
	 * @param headerName
	 * 		响应头名称
	 * @param prefix
	 * 		前缀
	 * @param suffix
	 * 		后缀
	 * @param stripPrefix
	 * 		删除前缀
	 * @param stripSuffix
	 * 		删除后缀
	 */
	public ServerInfoFilter(final String headerName, final String prefix, final String suffix, final String stripPrefix,
							final String stripSuffix){
		super();

		if(Validate.hasText(headerName)){
			setHeaderName(headerName);
		}

		this.prefix = prefix;
		this.suffix = suffix;
		this.stripPrefix = stripPrefix;
		this.stripSuffix = stripSuffix;
	}

	@Override
	protected String format(String serverName){
		String s = serverName;
		final StringBuilder sb = new StringBuilder(serverName.length());

		if(Validate.hasText(prefix)){
			sb.append(prefix);
		}

		if(Validate.hasText(stripPrefix) && s.startsWith(stripPrefix)){
			s = s.substring(stripPrefix.length());
		}

		if(Validate.hasText(stripSuffix) && s.endsWith(stripSuffix)){
			s = s.substring(0, s.length() - stripSuffix.length());
		}

		sb.append(s);

		if(Validate.hasText(suffix)){
			sb.append(suffix);
		}

		return sb.toString();
	}

}
