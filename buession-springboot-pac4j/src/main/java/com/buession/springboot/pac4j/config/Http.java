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

import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * HTTP 配置
 *
 * @author Yong.Teng
 * @since 2.0.0
 */
public class Http extends BaseConfig {

	public final static String PREFIX = PROPERTIES_PREFIX + ".http";

	/**
	 * 登录成功跳转地址
	 */
	private String callbackUrl;

	/**
	 * 表单客户端配置
	 */
	@NestedConfigurationProperty
	private Form form = new Form();

	/**
	 * Indirect Basic Auth 客户端配置
	 */
	@NestedConfigurationProperty
	private IndirectBasicAuth indirectBasicAuth = new IndirectBasicAuth();

	/**
	 * Direct Basic Auth 客户端配置
	 */
	@NestedConfigurationProperty
	private DirectBasicAuth directBasicAuth = new DirectBasicAuth();

	/**
	 * 返回登录成功跳转地址
	 *
	 * @return 登录成功跳转地址
	 */
	public String getCallbackUrl(){
		return callbackUrl;
	}

	/**
	 * 设置登录成功跳转地址
	 *
	 * @param callbackUrl
	 * 		登录成功跳转地址
	 */
	public void setCallbackUrl(String callbackUrl){
		this.callbackUrl = callbackUrl;
	}

	/**
	 * 返回表单客户端配置
	 *
	 * @return 表单客户端配置
	 */
	public Form getForm(){
		return form;
	}

	/**
	 * 设置表单客户端配置
	 *
	 * @param form
	 * 		表单客户端配置
	 */
	public void setForm(Form form){
		this.form = form;
	}

	/**
	 * 返回 Indirect Basic Auth 客户端配置
	 *
	 * @return Indirect Basic Auth 客户端配置
	 */
	public IndirectBasicAuth getIndirectBasicAuth(){
		return indirectBasicAuth;
	}

	/**
	 * 设置 Indirect Basic Auth 客户端配置
	 *
	 * @param indirectBasicAuth
	 * 		Indirect Basic Auth 客户端配置
	 */
	public void setIndirectBasicAuth(IndirectBasicAuth indirectBasicAuth){
		this.indirectBasicAuth = indirectBasicAuth;
	}

	/**
	 * 返回 Direct Basic Auth 客户端配置
	 *
	 * @return Direct Basic Auth 客户端配置
	 */
	public DirectBasicAuth getDirectBasicAuth(){
		return directBasicAuth;
	}

	/**
	 * 设置 Direct Basic Auth 客户端配置
	 *
	 * @param directBasicAuth
	 * 		Direct Basic Auth 客户端配置
	 */
	public void setDirectBasicAuth(DirectBasicAuth directBasicAuth){
		this.directBasicAuth = directBasicAuth;
	}

	/**
	 * 表单客户端配置
	 */
	public final static class Form extends BaseClientConfig {

		/**
		 * 用户名参数名称
		 */
		private String usernameParameter = "username";

		/**
		 * 密码参数名称
		 */
		private String passwordParameter = "password";

		/**
		 * 登录地址
		 */
		private String loginUrl;

		public Form(){
			super("form");
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

		/**
		 * 返回登录地址
		 *
		 * @return 登录地址
		 */
		public String getLoginUrl(){
			return loginUrl;
		}

		/**
		 * 设置登录地址
		 *
		 * @param loginUrl
		 * 		登录地址
		 */
		public void setLoginUrl(String loginUrl){
			this.loginUrl = loginUrl;
		}

	}

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
		public BaseBasicAuthConfig(String name){
			super(name);
		}

		/**
		 * 返回 Realm Name
		 *
		 * @return Realm Name
		 */
		public String getRealmName(){
			return realmName;
		}

		/**
		 * 设置 Realm Name
		 *
		 * @param realmName
		 * 		Realm Name
		 */
		public void setRealmName(String realmName){
			this.realmName = realmName;
		}

	}

	/**
	 * Indirect Basic Auth 客户端配置
	 */
	public final static class IndirectBasicAuth extends BaseBasicAuthConfig {

		public IndirectBasicAuth(){
			super("indirect-basic-auth");
		}

	}

	/**
	 * Direct Basic Auth 客户端配置
	 */
	public final static class DirectBasicAuth extends BaseBasicAuthConfig {

		public DirectBasicAuth(){
			super("direct-basic-auth");
		}

	}

}
