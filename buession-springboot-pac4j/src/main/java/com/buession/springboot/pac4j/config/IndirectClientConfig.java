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

import org.pac4j.core.http.ajax.AjaxRequestResolver;

/**
 * Indirect client 公共配置
 *
 * @author Yong.Teng
 * @since 4.0.0
 */
public abstract class IndirectClientConfig extends BaseClientConfig {

	/**
	 * 登录成功跳转地址
	 */
	private String callbackUrl;

	private Boolean checkAuthenticationAttempt;

	/**
	 * Ajax 请求解析器
	 */
	private Class<? extends AjaxRequestResolver> ajaxRequestResolver;

	/**
	 * 构造函数
	 *
	 * @param name
	 * 		Client 名称
	 */
	public IndirectClientConfig(String name) {
		super(name);
	}

	/**
	 * 返回登录成功跳转地址
	 *
	 * @return 登录成功跳转地址
	 */
	public String getCallbackUrl() {
		return callbackUrl;
	}

	/**
	 * 设置登录成功跳转地址
	 *
	 * @param callbackUrl
	 * 		登录成功跳转地址
	 */
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public Boolean getCheckAuthenticationAttempt() {
		return checkAuthenticationAttempt;
	}

	public void setCheckAuthenticationAttempt(Boolean checkAuthenticationAttempt) {
		this.checkAuthenticationAttempt = checkAuthenticationAttempt;
	}

	/**
	 * 返回 Ajax 请求解析器
	 *
	 * @return Ajax 请求解析器
	 */
	public Class<? extends AjaxRequestResolver> getAjaxRequestResolver() {
		return ajaxRequestResolver;
	}

	/**
	 * 设置 Ajax 请求解析器
	 *
	 * @param ajaxRequestResolver
	 * 		Ajax 请求解析器
	 */
	public void setAjaxRequestResolver(Class<? extends AjaxRequestResolver> ajaxRequestResolver) {
		this.ajaxRequestResolver = ajaxRequestResolver;
	}

}
