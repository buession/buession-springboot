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
 * | Copyright @ 2013-2024 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.pac4j.config;

import org.pac4j.core.context.HttpConstants;
import org.pac4j.core.http.ajax.AjaxRequestResolver;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Yong.Teng
 * @since 2.0.0
 */
public abstract class BaseConfig {

	protected final static String PROPERTIES_PREFIX = "spring.pac4j.client";

	/**
	 * Ajax 请求解析器
	 *
	 * @since 3.0.0
	 */
	private Class<? extends AjaxRequestResolver> ajaxRequestResolver;

	/**
	 * 返回 Ajax 请求解析器
	 *
	 * @return Ajax 请求解析器
	 *
	 * @since 3.0.0
	 */
	public Class<? extends AjaxRequestResolver> getAjaxRequestResolver() {
		return ajaxRequestResolver;
	}

	/**
	 * 设置 Ajax 请求解析器
	 *
	 * @param ajaxRequestResolver
	 * 		Ajax 请求解析器
	 *
	 * @since 3.0.0
	 */
	public void setAjaxRequestResolver(Class<? extends AjaxRequestResolver> ajaxRequestResolver) {
		this.ajaxRequestResolver = ajaxRequestResolver;
	}

	/**
	 * 客户自定义属性
	 */
	private Map<String, Object> customProperties = new LinkedHashMap<>();

	/**
	 * 返回客户自定义属性
	 *
	 * @return 客户自定义属性
	 */
	public Map<String, Object> getCustomProperties() {
		return customProperties;
	}

	/**
	 * 设置客户自定义属性
	 *
	 * @param customProperties
	 * 		客户自定义属性
	 */
	public void setCustomProperties(Map<String, Object> customProperties) {
		this.customProperties = customProperties;
	}

	public abstract static class BaseClientConfig extends BaseConfig {

		/**
		 * Client 名称
		 */
		private String name;

		/**
		 * 默认 Client 名称
		 */
		private final String defaultName;

		/**
		 * 构造函数
		 *
		 * @param name
		 * 		Client 名称
		 */
		public BaseClientConfig(String name) {
			this.name = name;
			this.defaultName = name;
		}

		/**
		 * 返回 Client 名称
		 *
		 * @return Client 名称
		 */
		public String getName() {
			return name;
		}

		/**
		 * 设置 Client 名称
		 *
		 * @param name
		 * 		Client 名称
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * 返回默认 Client 名称
		 *
		 * @return 默认 Client 名称
		 */
		public String getDefaultName() {
			return defaultName;
		}

	}

	/**
	 * Form Client 配置基类
	 *
	 * @since 3.0.0
	 */
	public abstract static class BaseFormConfig extends BaseClientConfig {

		/**
		 * 构造函数
		 *
		 * @param name
		 * 		Client 名称
		 */
		public BaseFormConfig(String name) {
			super(name);
		}

		/**
		 * 用户名参数名称
		 */
		private String usernameParameter;

		/**
		 * 密码参数名称
		 */
		private String passwordParameter;

		/**
		 * 返回用户名参数名称
		 *
		 * @return 用户名参数名称
		 */
		public String getUsernameParameter() {
			return usernameParameter;
		}

		/**
		 * 设置用户名参数名称
		 *
		 * @param usernameParameter
		 * 		用户名参数名称
		 */
		public void setUsernameParameter(String usernameParameter) {
			this.usernameParameter = usernameParameter;
		}

		/**
		 * 返回密码参数名称
		 *
		 * @return 密码参数名称
		 */
		public String getPasswordParameter() {
			return passwordParameter;
		}

		/**
		 * 设置密码参数名称
		 *
		 * @param passwordParameter
		 * 		密码参数名称
		 */
		public void setPasswordParameter(String passwordParameter) {
			this.passwordParameter = passwordParameter;
		}

	}

	/**
	 * Header 客户端配置基类
	 *
	 * @since 3.0.0
	 */
	public abstract static class BaseHeaderConfig extends BaseClientConfig {

		/**
		 * 请求头名称
		 */
		private String headerName;

		/**
		 * 请求头前缀
		 */
		private String prefixHeader;

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

	/**
	 * Parameter 客户端配置基类
	 *
	 * @since 3.0.0
	 */
	public abstract static class BaseParameterConfig extends BaseClientConfig {

		/**
		 * 参数名称
		 */
		private String parameterName;

		/**
		 * 是否支持 GET 请求
		 */
		private Boolean supportGetRequest;

		/**
		 * 是否支持 POST 请求
		 */
		private Boolean supportPostRequest;

		/**
		 * 构造函数
		 *
		 * @param name
		 * 		Client 名称
		 */
		public BaseParameterConfig(String name) {
			super(name);
		}

		/**
		 * 返回参数名称
		 *
		 * @return 参数名称
		 */
		public String getParameterName() {
			return parameterName;
		}

		/**
		 * 设置参数名称
		 *
		 * @param parameterName
		 * 		参数名称
		 */
		public void setParameterName(String parameterName) {
			this.parameterName = parameterName;
		}

		/**
		 * 返回是否支持 GET 请求
		 *
		 * @return 是否支持 GET 请求
		 */
		public Boolean isSupportGetRequest() {
			return getSupportGetRequest();
		}

		/**
		 * 返回是否支持 GET 请求
		 *
		 * @return 是否支持 GET 请求
		 */
		public Boolean getSupportGetRequest() {
			return supportGetRequest;
		}

		/**
		 * 设置是否支持 GET 请求
		 *
		 * @param supportGetRequest
		 * 		是否支持 GET 请求
		 */
		public void setSupportGetRequest(Boolean supportGetRequest) {
			this.supportGetRequest = supportGetRequest;
		}

		/**
		 * 返回是否支持 POST 请求
		 *
		 * @return 是否支持 POST 请求
		 */
		public Boolean isSupportPostRequest() {
			return getSupportPostRequest();
		}

		/**
		 * 返回是否支持 POST 请求
		 *
		 * @return 是否支持 POST 请求
		 */
		public Boolean getSupportPostRequest() {
			return supportPostRequest;
		}

		/**
		 * 设置是否支持 POST 请求
		 *
		 * @param supportPostRequest
		 * 		是否支持 POST 请求
		 */
		public void setSupportPostRequest(Boolean supportPostRequest) {
			this.supportPostRequest = supportPostRequest;
		}

	}

	/**
	 * Basic Auth 客户端配置
	 *
	 * @since 3.0.0
	 */
	public abstract static class BaseBasicAuthConfig extends BaseClientConfig {

		/**
		 * Realm Name
		 */
		private String realmName = "authentication required";

		/**
		 * 构造函数
		 *
		 * @param name
		 * 		Client 名称
		 */
		public BaseBasicAuthConfig(String name) {
			super(name);
		}

		/**
		 * 返回 Realm Name
		 *
		 * @return Realm Name
		 */
		public String getRealmName() {
			return realmName;
		}

		/**
		 * 设置 Realm Name
		 *
		 * @param realmName
		 * 		Realm Name
		 */
		public void setRealmName(String realmName) {
			this.realmName = realmName;
		}

	}

}
