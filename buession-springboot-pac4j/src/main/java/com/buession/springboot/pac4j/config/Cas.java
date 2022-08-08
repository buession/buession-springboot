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
import org.pac4j.cas.profile.CasProfileDefinition;
import org.pac4j.core.authorization.generator.AuthorizationGenerator;
import org.pac4j.core.context.HttpConstants;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.LinkedHashMap;
import java.util.List;
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
	 * CAS 协议
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
	 * Profile 定义，可用于处理 CAS Server 登录返回字段
	 */
	private Class<CasProfileDefinition> profileDefinition;

	/**
	 * 授权生成器
	 */
	private List<Class<AuthorizationGenerator>> authorizationGenerator;

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

	/**
	 * 返回 CAS 协议
	 *
	 * @return CAS 协议
	 */
	public CasProtocol getProtocol(){
		return protocol;
	}

	/**
	 * 设置 CAS 协议
	 *
	 * @param protocol
	 * 		CAS 协议
	 */
	public void setProtocol(final CasProtocol protocol){
		this.protocol = protocol;
	}

	/**
	 * 返回 CAS 登录地址
	 *
	 * @return CAS 登录地址
	 */
	public String getLoginUrl(){
		return loginUrl;
	}

	/**
	 * 设置 CAS 登录地址
	 *
	 * @param loginUrl
	 * 		CAS 登录地址
	 */
	public void setLoginUrl(String loginUrl){
		this.loginUrl = loginUrl;
	}

	/**
	 * 返回 CAS URL 前缀
	 *
	 * @return CAS URL 前缀
	 */
	public String getPrefixUrl(){
		return prefixUrl;
	}

	/**
	 * 设置 CAS URL 前缀
	 *
	 * @param prefixUrl
	 * 		CAS URL 前缀
	 */
	public void setPrefixUrl(String prefixUrl){
		this.prefixUrl = prefixUrl;
	}

	/**
	 * 返回 CAS 登录成功跳转地址
	 *
	 * @return CAS 登录成功跳转地址
	 */
	public String getCallbackUrl(){
		return callbackUrl;
	}

	/**
	 * 设置 CAS 登录成功跳转地址
	 *
	 * @param callbackUrl
	 * 		CAS 登录成功跳转地址
	 */
	public void setCallbackUrl(String callbackUrl){
		this.callbackUrl = callbackUrl;
	}

	/**
	 * 返回编码
	 *
	 * @return 编码
	 */
	public String getEncoding(){
		return encoding;
	}

	/**
	 * 设置编码
	 *
	 * @param encoding
	 * 		编码
	 */
	public void setEncoding(String encoding){
		this.encoding = encoding;
	}

	/**
	 * 返回请求方法
	 *
	 * @return 请求方法
	 */
	public String getMethod(){
		return method;
	}

	/**
	 * 设置请求方法
	 *
	 * @param method
	 * 		请求方法
	 */
	public void setMethod(String method){
		this.method = method;
	}

	/**
	 * 返回 Gateway
	 *
	 * @return Gateway
	 */
	public Boolean getGateway(){
		return gateway;
	}

	/**
	 * 设置 Gateway
	 *
	 * @param gateway
	 * 		Gateway
	 */
	public void setGateway(Boolean gateway){
		this.gateway = gateway;
	}

	/**
	 * 返回是否重新认证
	 *
	 * @return 是否重新认证
	 */
	public Boolean getRenew(){
		return renew;
	}

	/**
	 * 设置是否重新认证
	 *
	 * @param renew
	 * 		是否重新认证
	 */
	public void setRenew(Boolean renew){
		this.renew = renew;
	}

	public Long getTimeTolerance(){
		return timeTolerance;
	}

	public void setTimeTolerance(Long timeTolerance){
		this.timeTolerance = timeTolerance;
	}

	/**
	 * 返回退出登录请求参数
	 *
	 * @return 退出登录请求参数
	 */
	public String getPostLogoutUrlParameter(){
		return postLogoutUrlParameter;
	}

	/**
	 * 设置退出登录请求参数
	 *
	 * @param postLogoutUrlParameter
	 * 		退出登录请求参数
	 */
	public void setPostLogoutUrlParameter(String postLogoutUrlParameter){
		this.postLogoutUrlParameter = postLogoutUrlParameter;
	}

	/**
	 * 返回 Profile 定义，可用于处理 CAS Server 登录返回字段
	 *
	 * @return Profile 定义
	 */
	public Class<CasProfileDefinition> getProfileDefinition(){
		return profileDefinition;
	}

	/**
	 * 设置 Profile 定义，可用于处理 CAS Server 登录返回字段
	 *
	 * @param profileDefinition
	 * 		Profile 定义
	 */
	public void setProfileDefinition(Class<CasProfileDefinition> profileDefinition){
		this.profileDefinition = profileDefinition;
	}

	/**
	 * 返回授权生成器
	 *
	 * @return 授权生成器
	 */
	public List<Class<AuthorizationGenerator>> getAuthorizationGenerator(){
		return authorizationGenerator;
	}

	/**
	 * 设置授权生成器
	 *
	 * @param authorizationGenerator
	 * 		授权生成器
	 */
	public void setAuthorizationGenerator(List<Class<AuthorizationGenerator>> authorizationGenerator){
		this.authorizationGenerator = authorizationGenerator;
	}

	/**
	 * 返回客户自定义参数
	 *
	 * @return 客户自定义参数
	 */
	public Map<String, String> getCustomParameters(){
		return customParameters;
	}

	/**
	 * 设置客户自定义参数
	 *
	 * @param customParameters
	 * 		客户自定义参数
	 */
	public void setCustomParameters(Map<String, String> customParameters){
		this.customParameters = customParameters;
	}

	/**
	 * 返回常规配置
	 *
	 * @return 常规配置
	 */
	public General getGeneral(){
		return general;
	}

	/**
	 * 设置常规配置
	 *
	 * @param general
	 * 		常规配置
	 */
	public void setGeneral(General general){
		this.general = general;
	}

	/**
	 * 返回 Rest 表单配置
	 *
	 * @return Rest 表单配置
	 */
	public RestForm getRestForm(){
		return restForm;
	}

	/**
	 * 设置 Rest 表单配置
	 *
	 * @param restForm
	 * 		Rest 表单配置
	 */
	public void setRestForm(RestForm restForm){
		this.restForm = restForm;
	}

	/**
	 * 返回 Direct Client 配置
	 *
	 * @return Direct Client 配置
	 */
	public Direct getDirect(){
		return direct;
	}

	/**
	 * 设置 Direct Client 配置
	 *
	 * @param direct
	 * 		Direct Client 配置
	 */
	public void setDirect(Direct direct){
		this.direct = direct;
	}

	/**
	 * 返回 Direct Proxy Client 配置
	 *
	 * @return Direct Proxy Client 配置
	 */
	public DirectProxy getDirectProxy(){
		return directProxy;
	}

	/**
	 * 设置 Direct Proxy Client 配置
	 *
	 * @param directProxy
	 * 		Direct Proxy Client 配置
	 */
	public void setDirectProxy(DirectProxy directProxy){
		this.directProxy = directProxy;
	}

	/**
	 * 返回 Rest Basic Auth Client 配置
	 *
	 * @return Rest Basic Auth Client 配置
	 */
	public RestBasicAuth getRestBasicAuth(){
		return restBasicAuth;
	}

	/**
	 * 设置 Rest Basic Auth Client 配置
	 *
	 * @param restBasicAuth
	 * 		Rest Basic Auth Client 配置
	 */
	public void setRestBasicAuth(RestBasicAuth restBasicAuth){
		this.restBasicAuth = restBasicAuth;
	}

	/**
	 * CAS 常规配置
	 */
	public final static class General extends BaseClientConfig {

		/**
		 * 构造函数
		 */
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

		/**
		 * 构造函数
		 */
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

		/**
		 * 构造函数
		 */
		public Direct(){
			super("direct-cas");
		}

	}

	/**
	 * Direct Proxy Client 配置
	 */
	public final static class DirectProxy extends BaseClientConfig {

		/**
		 * 构造函数
		 */
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

		/**
		 * 构造函数
		 */
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
