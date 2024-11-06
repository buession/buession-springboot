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

import org.pac4j.cas.config.CasProtocol;
import org.pac4j.cas.profile.CasProfileDefinition;
import org.pac4j.core.authorization.generator.AuthorizationGenerator;
import org.pac4j.core.context.HttpConstants;
import org.pac4j.core.util.Pac4jConstants;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	 * REST URL
	 *
	 * @since 3.0.0
	 */
	private String restUrl;

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
	 * 认证票据时间戳验证容忍度范围
	 */
	private Long timeTolerance;

	/**
	 * 退出登录请求参数
	 */
	private String postLogoutUrlParameter;

	/**
	 * Profile 定义，可用于处理 CAS Server 登录返回字段
	 */
	private Class<? extends CasProfileDefinition> profileDefinition = org.pac4j.cas.profile.CasProfileDefinition.class;

	/**
	 * 授权生成器
	 */
	private List<Class<? extends AuthorizationGenerator>> authorizationGenerator;

	/**
	 * 是否接受来自任何代理的请求
	 *
	 * @since 3.0.0
	 */
	private Boolean acceptAnyProxy;

	/**
	 * 允许的代理链
	 *
	 * @since 3.0.0
	 */
	private Set<String> allowedProxyChains;

	/**
	 * 客户自定义参数
	 */
	private Map<String, String> customParameters = new LinkedHashMap<>();

	/**
	 * SSL 配置
	 *
	 * @since 3.0.0
	 */
	private Ssl ssl = new Ssl();

	/**
	 * 常规配置
	 */
	@NestedConfigurationProperty
	private General general = new General();

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
	 * Rest 表单配置
	 */
	@NestedConfigurationProperty
	private RestForm restForm = new RestForm();

	/**
	 * 返回 CAS 协议
	 *
	 * @return CAS 协议
	 */
	public CasProtocol getProtocol() {
		return protocol;
	}

	/**
	 * 设置 CAS 协议
	 *
	 * @param protocol
	 * 		CAS 协议
	 */
	public void setProtocol(final CasProtocol protocol) {
		this.protocol = protocol;
	}

	/**
	 * 返回 CAS 登录地址
	 *
	 * @return CAS 登录地址
	 */
	public String getLoginUrl() {
		return loginUrl;
	}

	/**
	 * 设置 CAS 登录地址
	 *
	 * @param loginUrl
	 * 		CAS 登录地址
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	/**
	 * 返回 CAS URL 前缀
	 *
	 * @return CAS URL 前缀
	 */
	public String getPrefixUrl() {
		return prefixUrl;
	}

	/**
	 * 设置 CAS URL 前缀
	 *
	 * @param prefixUrl
	 * 		CAS URL 前缀
	 */
	public void setPrefixUrl(String prefixUrl) {
		this.prefixUrl = prefixUrl;
	}

	/**
	 * 返回 REST URL
	 *
	 * @return REST URL
	 *
	 * @since 3.0.0
	 */
	public String getRestUrl() {
		return restUrl;
	}

	/**
	 * 设置 REST URL
	 *
	 * @param restUrl
	 * 		REST URL
	 *
	 * @since 3.0.0
	 */
	public void setRestUrl(String restUrl) {
		this.restUrl = restUrl;
	}

	/**
	 * 返回 CAS 登录成功跳转地址
	 *
	 * @return CAS 登录成功跳转地址
	 */
	public String getCallbackUrl() {
		return callbackUrl;
	}

	/**
	 * 设置 CAS 登录成功跳转地址
	 *
	 * @param callbackUrl
	 * 		CAS 登录成功跳转地址
	 */
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	/**
	 * 返回编码
	 *
	 * @return 编码
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * 设置编码
	 *
	 * @param encoding
	 * 		编码
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * 返回请求方法
	 *
	 * @return 请求方法
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * 设置请求方法
	 *
	 * @param method
	 * 		请求方法
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * 返回 Gateway
	 *
	 * @return Gateway
	 */
	public Boolean isGateway() {
		return getGateway();
	}

	/**
	 * 返回 Gateway
	 *
	 * @return Gateway
	 */
	public Boolean getGateway() {
		return gateway;
	}

	/**
	 * 设置 Gateway
	 *
	 * @param gateway
	 * 		Gateway
	 */
	public void setGateway(Boolean gateway) {
		this.gateway = gateway;
	}

	/**
	 * 返回是否重新认证
	 *
	 * @return 是否重新认证
	 */
	public Boolean isRenew() {
		return getRenew();
	}

	/**
	 * 返回是否重新认证
	 *
	 * @return 是否重新认证
	 */
	public Boolean getRenew() {
		return renew;
	}

	/**
	 * 设置是否重新认证
	 *
	 * @param renew
	 * 		是否重新认证
	 */
	public void setRenew(Boolean renew) {
		this.renew = renew;
	}

	/**
	 * 返回认证票据时间戳验证容忍度范围
	 *
	 * @return 认证票据时间戳验证容忍度范围
	 */
	public Long getTimeTolerance() {
		return timeTolerance;
	}

	/**
	 * 设置认证票据时间戳验证容忍度范围
	 *
	 * @param timeTolerance
	 * 		认证票据时间戳验证容忍度范围
	 */
	public void setTimeTolerance(Long timeTolerance) {
		this.timeTolerance = timeTolerance;
	}

	/**
	 * 返回退出登录请求参数
	 *
	 * @return 退出登录请求参数
	 */
	public String getPostLogoutUrlParameter() {
		return postLogoutUrlParameter;
	}

	/**
	 * 设置退出登录请求参数
	 *
	 * @param postLogoutUrlParameter
	 * 		退出登录请求参数
	 */
	public void setPostLogoutUrlParameter(String postLogoutUrlParameter) {
		this.postLogoutUrlParameter = postLogoutUrlParameter;
	}

	/**
	 * 返回 Profile 定义，可用于处理 CAS Server 登录返回字段
	 *
	 * @return Profile 定义
	 */
	public Class<? extends CasProfileDefinition> getProfileDefinition() {
		return profileDefinition;
	}

	/**
	 * 设置 Profile 定义，可用于处理 CAS Server 登录返回字段
	 *
	 * @param profileDefinition
	 * 		Profile 定义
	 */
	public void setProfileDefinition(Class<? extends CasProfileDefinition> profileDefinition) {
		this.profileDefinition = profileDefinition;
	}

	/**
	 * 返回授权生成器
	 *
	 * @return 授权生成器
	 */
	public List<Class<? extends AuthorizationGenerator>> getAuthorizationGenerator() {
		return authorizationGenerator;
	}

	/**
	 * 设置授权生成器
	 *
	 * @param authorizationGenerator
	 * 		授权生成器
	 */
	public void setAuthorizationGenerator(List<Class<? extends AuthorizationGenerator>> authorizationGenerator) {
		this.authorizationGenerator = authorizationGenerator;
	}

	/**
	 * 返回是否接受来自任何代理的请求
	 *
	 * @return 是否接受来自任何代理的请求
	 *
	 * @since 3.0.0
	 */
	public Boolean isAcceptAnyProxy() {
		return getAcceptAnyProxy();
	}

	/**
	 * 返回是否接受来自任何代理的请求
	 *
	 * @return 是否接受来自任何代理的请求
	 *
	 * @since 3.0.0
	 */
	public Boolean getAcceptAnyProxy() {
		return acceptAnyProxy;
	}

	/**
	 * 设置是否接受来自任何代理的请求
	 *
	 * @param acceptAnyProxy
	 * 		是否接受来自任何代理的请求
	 *
	 * @since 3.0.0
	 */
	public void setAcceptAnyProxy(Boolean acceptAnyProxy) {
		this.acceptAnyProxy = acceptAnyProxy;
	}

	/**
	 * 返回允许的代理链
	 *
	 * @return 允许的代理链
	 *
	 * @since 3.0.0
	 */
	public Set<String> getAllowedProxyChains() {
		return allowedProxyChains;
	}

	/**
	 * 设置允许的代理链
	 *
	 * @param allowedProxyChains
	 * 		允许的代理链
	 *
	 * @since 3.0.0
	 */
	public void setAllowedProxyChains(Set<String> allowedProxyChains) {
		this.allowedProxyChains = allowedProxyChains;
	}

	/**
	 * 返回客户自定义参数
	 *
	 * @return 客户自定义参数
	 */
	public Map<String, String> getCustomParameters() {
		return customParameters;
	}

	/**
	 * 设置客户自定义参数
	 *
	 * @param customParameters
	 * 		客户自定义参数
	 */
	public void setCustomParameters(Map<String, String> customParameters) {
		this.customParameters = customParameters;
	}

	/**
	 * 返回SSL 配置
	 *
	 * @return SSL 配置
	 */
	public Ssl getSsl() {
		return ssl;
	}

	/**
	 * 设置SSL 配置
	 *
	 * @param ssl
	 * 		SSL 配置
	 */
	public void setSsl(Ssl ssl) {
		this.ssl = ssl;
	}

	/**
	 * 返回常规配置
	 *
	 * @return 常规配置
	 */
	public General getGeneral() {
		return general;
	}

	/**
	 * 设置常规配置
	 *
	 * @param general
	 * 		常规配置
	 */
	public void setGeneral(General general) {
		this.general = general;
	}

	/**
	 * 返回 Direct Client 配置
	 *
	 * @return Direct Client 配置
	 */
	public Direct getDirect() {
		return direct;
	}

	/**
	 * 设置 Direct Client 配置
	 *
	 * @param direct
	 * 		Direct Client 配置
	 */
	public void setDirect(Direct direct) {
		this.direct = direct;
	}

	/**
	 * 返回 Direct Proxy Client 配置
	 *
	 * @return Direct Proxy Client 配置
	 */
	public DirectProxy getDirectProxy() {
		return directProxy;
	}

	/**
	 * 设置 Direct Proxy Client 配置
	 *
	 * @param directProxy
	 * 		Direct Proxy Client 配置
	 */
	public void setDirectProxy(DirectProxy directProxy) {
		this.directProxy = directProxy;
	}

	/**
	 * 返回 Rest Basic Auth Client 配置
	 *
	 * @return Rest Basic Auth Client 配置
	 */
	public RestBasicAuth getRestBasicAuth() {
		return restBasicAuth;
	}

	/**
	 * 设置 Rest Basic Auth Client 配置
	 *
	 * @param restBasicAuth
	 * 		Rest Basic Auth Client 配置
	 */
	public void setRestBasicAuth(RestBasicAuth restBasicAuth) {
		this.restBasicAuth = restBasicAuth;
	}

	/**
	 * 返回 Rest 表单配置
	 *
	 * @return Rest 表单配置
	 */
	public RestForm getRestForm() {
		return restForm;
	}

	/**
	 * 设置 Rest 表单配置
	 *
	 * @param restForm
	 * 		Rest 表单配置
	 */
	public void setRestForm(RestForm restForm) {
		this.restForm = restForm;
	}

	/**
	 * SSL 配置
	 *
	 * @since 3.0.0
	 */
	public final static class Ssl {

		/**
		 * 私钥路径
		 */
		private String privateKeyPath;

		/**
		 * 私钥算法
		 */
		private String privateKeyAlgorithm;

		/**
		 * 返回私钥路径
		 *
		 * @return 私钥路径
		 */
		public String getPrivateKeyPath() {
			return privateKeyPath;
		}

		/**
		 * 设置私钥路径
		 *
		 * @param privateKeyPath
		 * 		私钥路径
		 */
		public void setPrivateKeyPath(String privateKeyPath) {
			this.privateKeyPath = privateKeyPath;
		}

		/**
		 * 返回私钥算法
		 *
		 * @return 私钥算法
		 */
		public String getPrivateKeyAlgorithm() {
			return privateKeyAlgorithm;
		}

		/**
		 * 设置私钥算法
		 *
		 * @param privateKeyAlgorithm
		 * 		私钥算法
		 */
		public void setPrivateKeyAlgorithm(String privateKeyAlgorithm) {
			this.privateKeyAlgorithm = privateKeyAlgorithm;
		}

	}

	/**
	 * CAS 常规配置
	 */
	public final static class General extends BaseClientConfig {

		/**
		 * 构造函数
		 */
		public General() {
			super("cas");
		}

	}

	/**
	 * CAS Rest 表单配置
	 */
	public final static class RestForm extends BaseFormConfig {

		/**
		 * 构造函数
		 */
		public RestForm() {
			super("cas-rest-form");
			setUsernameParameter(Pac4jConstants.USERNAME);
			setPasswordParameter(Pac4jConstants.PASSWORD);
		}

	}

	/**
	 * Direct Client 配置
	 */
	public final static class Direct extends BaseClientConfig {

		/**
		 * 构造函数
		 */
		public Direct() {
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
		public DirectProxy() {
			super("direct-cas-proxy");
		}

	}

	/**
	 * Cas Rest Basic Auth 配置
	 */
	public final static class RestBasicAuth extends BaseHeaderConfig {

		/**
		 * 构造函数
		 */
		public RestBasicAuth() {
			super("cas-rest-basic-auth");
			setHeaderName(HttpConstants.AUTHORIZATION_HEADER);
			setPrefixHeader(HttpConstants.BASIC_HEADER_PREFIX);
		}

	}

}
