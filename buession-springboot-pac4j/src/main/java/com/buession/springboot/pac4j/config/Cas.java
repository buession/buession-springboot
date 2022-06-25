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
package com.buession.springboot.pac4j.config;

import org.pac4j.cas.config.CasProtocol;
import org.pac4j.core.context.HttpConstants;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * CAS 配置
 *
 * @author Yong.Teng
 * @since 2.0.0
 */
public class Cas extends BaseConfig {

	public final static String PREFIX = PROPERTIES_PREFIX + ".cas";

	/**
	 * CAS 版本
	 */
	private CasProtocol protocol = CasProtocol.CAS30;

	/**
	 * CAS 登录地址
	 */
	private String loginUrl;

	/**
	 * CAS URL 前缀
	 */
	private String prefixUrl;

	/**
	 * CAS 登录成功跳转地址
	 */
	private String callbackUrl;

	/**
	 * 编码
	 */
	private String encoding;

	/**
	 * 请求方法
	 */
	private String method;

	/**
	 * Gateway
	 */
	private Boolean gateway;

	/**
	 * 是否重新认证
	 */
	private Boolean renew;

	/**
	 *
	 */
	private Long timeTolerance;

	/**
	 * 退出登录请求参数
	 */
	private String postLogoutUrlParameter;

	/**
	 * 客户自定义参数
	 */
	private Map<String, String> customParameters = new LinkedHashMap<>();

	/**
	 * 常规配置
	 */
	@NestedConfigurationProperty
	private General general = new General();

	/**
	 * Rest 表单配置
	 */
	@NestedConfigurationProperty
	private RestForm restForm = new RestForm();

	/**
	 * Direct Client 配置
	 */
	@NestedConfigurationProperty
	private Direct direct = new Direct();

	/**
	 * Direct Proxy Client 配置
	 */
	@NestedConfigurationProperty
	private DirectProxy directProxy = new DirectProxy();

	/**
	 * Rest Basic Auth Client 配置
	 */
	private RestBasicAuth restBasicAuth = new RestBasicAuth();

	public CasProtocol getProtocol(){
		return protocol;
	}

	public void setProtocol(final CasProtocol protocol){
		this.protocol = protocol;
	}

	public String getLoginUrl(){
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl){
		this.loginUrl = loginUrl;
	}

	public String getPrefixUrl(){
		return prefixUrl;
	}

	public void setPrefixUrl(String prefixUrl){
		this.prefixUrl = prefixUrl;
	}

	public String getCallbackUrl(){
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl){
		this.callbackUrl = callbackUrl;
	}

	public String getEncoding(){
		return encoding;
	}

	public void setEncoding(String encoding){
		this.encoding = encoding;
	}

	public String getMethod(){
		return method;
	}

	public void setMethod(String method){
		this.method = method;
	}

	public Boolean getGateway(){
		return gateway;
	}

	public void setGateway(Boolean gateway){
		this.gateway = gateway;
	}

	public Boolean getRenew(){
		return renew;
	}

	public void setRenew(Boolean renew){
		this.renew = renew;
	}

	public Long getTimeTolerance(){
		return timeTolerance;
	}

	public void setTimeTolerance(Long timeTolerance){
		this.timeTolerance = timeTolerance;
	}

	public String getPostLogoutUrlParameter(){
		return postLogoutUrlParameter;
	}

	public void setPostLogoutUrlParameter(String postLogoutUrlParameter){
		this.postLogoutUrlParameter = postLogoutUrlParameter;
	}

	public Map<String, String> getCustomParameters(){
		return customParameters;
	}

	public void setCustomParameters(Map<String, String> customParameters){
		this.customParameters = customParameters;
	}

	public General getGeneral(){
		return general;
	}

	public void setGeneral(General general){
		this.general = general;
	}

	public RestForm getRestForm(){
		return restForm;
	}

	public void setRestForm(RestForm restForm){
		this.restForm = restForm;
	}

	public Direct getDirect(){
		return direct;
	}

	public void setDirect(Direct direct){
		this.direct = direct;
	}

	public DirectProxy getDirectProxy(){
		return directProxy;
	}

	public void setDirectProxy(DirectProxy directProxy){
		this.directProxy = directProxy;
	}

	public RestBasicAuth getRestBasicAuth(){
		return restBasicAuth;
	}

	public void setRestBasicAuth(RestBasicAuth restBasicAuth){
		this.restBasicAuth = restBasicAuth;
	}

	/**
	 * CAS 常规配置
	 */
	public final static class General extends BaseClientConfig {

		public General(){
			super("cas");
		}
	}

	/**
	 * CAS Rest 表单配置
	 */
	public final static class RestForm extends BaseClientConfig {

		/**
		 * 用户名参数名称
		 */
		private String usernameParameter = "username";

		/**
		 * 密码参数名称
		 */
		private String passwordParameter = "password";

		public RestForm(){
			super("cas-rest-form");
		}

		/**
		 * 返回用户名参数名称
		 *
		 * @return 用户名参数名称
		 */
		public String getUsernameParameter(){
			return usernameParameter;
		}

		/**
		 * 设置用户名参数名称
		 *
		 * @param usernameParameter
		 * 		用户名参数名称
		 */
		public void setUsernameParameter(String usernameParameter){
			this.usernameParameter = usernameParameter;
		}

		/**
		 * 返回密码参数名称
		 *
		 * @return 密码参数名称
		 */
		public String getPasswordParameter(){
			return passwordParameter;
		}

		/**
		 * 设置密码参数名称
		 *
		 * @param passwordParameter
		 * 		密码参数名称
		 */
		public void setPasswordParameter(String passwordParameter){
			this.passwordParameter = passwordParameter;
		}

	}

	/**
	 * Direct Client 配置
	 */
	public final static class Direct extends BaseClientConfig {

		public Direct(){
			super("direct-cas");
		}

	}

	/**
	 * Direct Proxy Client 配置
	 */
	public final static class DirectProxy extends BaseClientConfig {

		public DirectProxy(){
			super("direct-cas-proxy");
		}

	}

	/**
	 * Cas Rest Basic Auth 配置
	 */
	public final static class RestBasicAuth extends BaseClientConfig {

		/**
		 * 请求头名称
		 */
		private String headerName = HttpConstants.AUTHORIZATION_HEADER;

		/**
		 * 请求头前缀
		 */
		private String prefixHeader = HttpConstants.BASIC_HEADER_PREFIX;

		public RestBasicAuth(){
			super("cas-rest-basic-auth");
		}

		/**
		 * 返回请求头名称
		 *
		 * @return 请求头名称
		 */
		public String getHeaderName(){
			return headerName;
		}

		/**
		 * 设置请求头名称
		 *
		 * @param headerName
		 * 		请求头名称
		 */
		public void setHeaderName(String headerName){
			this.headerName = headerName;
		}

		/**
		 * 返回请求头前缀
		 *
		 * @return 请求头前缀
		 */
		public String getPrefixHeader(){
			return prefixHeader;
		}

		/**
		 * 设置请求头前缀
		 *
		 * @param prefixHeader
		 * 		请求头前缀
		 */
		public void setPrefixHeader(String prefixHeader){
			this.prefixHeader = prefixHeader;
		}

	}

}
