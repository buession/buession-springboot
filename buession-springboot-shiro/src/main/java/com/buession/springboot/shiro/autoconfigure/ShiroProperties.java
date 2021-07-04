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
 * | Copyright @ 2013-2021 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.shiro.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * Shiro 配置
 *
 * @author Yong.Teng
 */
@ConfigurationProperties(prefix = "spring.shiro")
public class ShiroProperties {

	/**
	 * 登录地址
	 */
	private String loginUrl;

	/**
	 * 登录成功跳转地址
	 */
	private String successUrl;

	/**
	 * 授权失败跳转地址
	 */
	private String unauthorizedUrl;

	/**
	 * Session
	 */
	@NestedConfigurationProperty
	private Session session = new Session();

	/**
	 * 缓存
	 */
	@NestedConfigurationProperty
	private Cache cache = new Cache();

	/**
	 * Remember Me
	 */
	@NestedConfigurationProperty
	private RememberMe rememberMe = new RememberMe();

	/**
	 * Pac4j
	 */
	@NestedConfigurationProperty
	private Pac4j pac4j = new Pac4j();

	public String getLoginUrl(){
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl){
		this.loginUrl = loginUrl;
	}

	public String getSuccessUrl(){
		return successUrl;
	}

	public void setSuccessUrl(String successUrl){
		this.successUrl = successUrl;
	}

	public String getUnauthorizedUrl(){
		return unauthorizedUrl;
	}

	public void setUnauthorizedUrl(String unauthorizedUrl){
		this.unauthorizedUrl = unauthorizedUrl;
	}

	public Session getSession(){
		return session;
	}

	public void setSession(Session session){
		this.session = session;
	}

	public Cache getCache(){
		return cache;
	}

	public void setCache(Cache cache){
		this.cache = cache;
	}

	public RememberMe getRememberMe(){
		return rememberMe;
	}

	public void setRememberMe(RememberMe rememberMe){
		this.rememberMe = rememberMe;
	}

	public Pac4j getPac4j(){
		return pac4j;
	}

	public void setPac4j(Pac4j pac4j){
		this.pac4j = pac4j;
	}

	@ConfigurationProperties(prefix = "shiro")
	@Deprecated
	public final static class DeprecatedShiroProperties extends ShiroProperties {

		/**
		 * 登录地址
		 */
		@Deprecated
		private String loginUrl;

		/**
		 * 登录成功跳转地址
		 */
		@Deprecated
		private String successUrl;

		/**
		 * 授权失败跳转地址
		 */
		@Deprecated
		private String unauthorizedUrl;

		/**
		 * Session
		 */
		@Deprecated
		@NestedConfigurationProperty
		private Session session = new Session();

		/**
		 * 缓存
		 */
		@Deprecated
		@NestedConfigurationProperty
		private Cache cache = new Cache();

		/**
		 * Remember Me
		 */
		@Deprecated
		@NestedConfigurationProperty
		private RememberMe rememberMe = new RememberMe();

		/**
		 * 认证器名称
		 */
		@Deprecated
		private String[] authorizers;

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "更通用的命名", replacement = "spring.shiro.login-url")
		@Override
		public void setLoginUrl(String loginUrl){
			super.setLoginUrl(loginUrl);
			this.loginUrl = loginUrl;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "更通用的命名", replacement = "spring.shiro.success-url")
		@Override
		public void setSuccessUrl(String successUrl){
			super.setSuccessUrl(successUrl);
			this.successUrl = successUrl;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "更通用的命名", replacement = "spring.shiro.unauthorized-url")
		@Override
		public void setUnauthorizedUrl(String unauthorizedUrl){
			super.setUnauthorizedUrl(unauthorizedUrl);
			this.unauthorizedUrl = unauthorizedUrl;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "更通用的命名", replacement = "spring.shiro.session")
		@Override
		public void setSession(Session session){
			super.setSession(session);
			this.session = session;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "更通用的命名", replacement = "spring.shiro.cache")
		@Override
		public void setCache(Cache cache){
			super.setCache(cache);
			this.cache = cache;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "更通用的命名", replacement = "spring.shiro.remember-me")
		@Override
		public void setRememberMe(RememberMe rememberMe){
			super.setRememberMe(rememberMe);
			this.rememberMe = rememberMe;
		}

	}

}
