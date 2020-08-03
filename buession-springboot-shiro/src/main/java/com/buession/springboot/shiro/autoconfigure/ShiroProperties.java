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
 * | Copyright @ 2013-2020 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.shiro.autoconfigure;

import com.buession.security.shiro.Cookie;
import com.buession.security.shiro.cache.CacheManager;
import com.buession.security.shiro.session.SessionDAO;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Set;

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
	private RememberMeConfig rememberMe = new RememberMeConfig();

	/**
	 * 客户端名称
	 */
	private Set<String> clients;

	/**
	 * 是否允许多个 Profile
	 */
	private boolean multiProfile;

	/**
	 * 认证器名称
	 */
	private String[] authorizers;

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

	public RememberMeConfig getRememberMe(){
		return rememberMe;
	}

	public void setRememberMe(RememberMeConfig rememberMe){
		this.rememberMe = rememberMe;
	}

	public Set<String> getClients(){
		return clients;
	}

	public void setClients(Set<String> clients){
		this.clients = clients;
	}

	public boolean isMultiProfile(){
		return getMultiProfile();
	}

	public boolean getMultiProfile(){
		return multiProfile;
	}

	public void setMultiProfile(boolean multiProfile){
		this.multiProfile = multiProfile;
	}

	public String[] getAuthorizers(){
		return authorizers;
	}

	public void setAuthorizers(String[] authorizers){
		this.authorizers = authorizers;
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
		private RememberMeConfig rememberMe = new RememberMeConfig();

		/**
		 * 客户端名称
		 */
		@Deprecated
		private Set<String> clients;

		/**
		 * 是否允许多个 Profile
		 */
		@Deprecated
		private boolean multiProfile;

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
		public void setRememberMe(RememberMeConfig rememberMe){
			super.setRememberMe(rememberMe);
			this.rememberMe = rememberMe;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "更通用的命名", replacement = "spring.shiro.clients")
		@Override
		public void setClients(Set<String> clients){
			super.setClients(clients);
			this.clients = clients;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "更通用的命名", replacement = "spring.shiro.multi-profile")
		@Override
		public void setMultiProfile(boolean multiProfile){
			super.setMultiProfile(multiProfile);
			this.multiProfile = multiProfile;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "更通用的命名", replacement = "spring.shiro.authorizers")
		@Override
		public void setAuthorizers(String[] authorizers){
			super.setAuthorizers(authorizers);
			this.authorizers = authorizers;
		}

	}

	public final static class Cache {

		/**
		 * 缓存 Key 前缀
		 */
		private String prefix = CacheManager.DEFAULT_KEY_PREFIX;

		/**
		 * 缓存过期时间
		 */
		private int expire = CacheManager.DEFAULT_EXPIRE;

		/**
		 * Principal Id
		 */
		private String principalIdFieldName = CacheManager.DEFAULT_PRINCIPAL_ID_FIELD_NAME;

		public String getPrefix(){
			return prefix;
		}

		public void setPrefix(String prefix){
			this.prefix = prefix;
		}

		public int getExpire(){
			return expire;
		}

		public void setExpire(int expire){
			this.expire = expire;
		}

		public String getPrincipalIdFieldName(){
			return principalIdFieldName;
		}

		public void setPrincipalIdFieldName(String principalIdFieldName){
			this.principalIdFieldName = principalIdFieldName;
		}

	}

	public final static class Session {

		/**
		 * 是否使用原生 Session 管理器
		 */
		private boolean useNativeSessionManager;

		/**
		 * 是否开启 SESSION ID Cookie
		 */
		private boolean sessionIdCookieEnabled = true;

		/**
		 * 是否开启 URL 重写，开启后 URL 中会带 JSESSIONID
		 */
		private boolean sessionIdUrlRewritingEnabled = true;

		/**
		 * 是否开启 Session 在内存中保存
		 */
		private boolean sessionInMemoryEnabled = SessionDAO.DEFAULT_SESSION_IN_MEMORY_ENABLED;

		/**
		 * Session 在内存中保存超时时间（单位：毫秒）
		 */
		private long sessionInMemoryTimeout = SessionDAO.DEFAULT_SESSION_IN_MEMORY_TIMEOUT;

		/**
		 * Session 前缀
		 */
		private String prefix = SessionDAO.DEFAULT_SESSION_KEY_PREFIX;

		/**
		 * Session 有效期，当为 -2 时，则为 Session timeout 的值；为 -1 时，表示永不过期
		 */
		private int expire = SessionDAO.DEFAULT_EXPIRE;

		/**
		 * 如果 Session 过期或者无效后，是否删除
		 */
		private boolean sessionManagerDeleteInvalidSessions = true;

		/**
		 * Session Cookie
		 */
		private Cookie cookie = new Cookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME, SimpleCookie.DEFAULT_MAX_AGE,
				false);

		public boolean isUseNativeSessionManager(){
			return getUseNativeSessionManager();
		}

		public boolean getUseNativeSessionManager(){
			return useNativeSessionManager;
		}

		public void setUseNativeSessionManager(boolean useNativeSessionManager){
			this.useNativeSessionManager = useNativeSessionManager;
		}

		public boolean isSessionIdCookieEnabled(){
			return getSessionIdCookieEnabled();
		}

		public boolean getSessionIdCookieEnabled(){
			return sessionIdCookieEnabled;
		}

		public void setSessionIdCookieEnabled(boolean sessionIdCookieEnabled){
			this.sessionIdCookieEnabled = sessionIdCookieEnabled;
		}

		public boolean isSessionIdUrlRewritingEnabled(){
			return getSessionIdUrlRewritingEnabled();
		}

		public boolean getSessionIdUrlRewritingEnabled(){
			return sessionIdUrlRewritingEnabled;
		}

		public void setSessionIdUrlRewritingEnabled(boolean sessionIdUrlRewritingEnabled){
			this.sessionIdUrlRewritingEnabled = sessionIdUrlRewritingEnabled;
		}

		public boolean isSessionInMemoryEnabled(){
			return getSessionInMemoryEnabled();
		}

		public boolean getSessionInMemoryEnabled(){
			return sessionInMemoryEnabled;
		}

		public void setSessionInMemoryEnabled(boolean sessionInMemoryEnabled){
			this.sessionInMemoryEnabled = sessionInMemoryEnabled;
		}

		public long getSessionInMemoryTimeout(){
			return sessionInMemoryTimeout;
		}

		public void setSessionInMemoryTimeout(long sessionInMemoryTimeout){
			this.sessionInMemoryTimeout = sessionInMemoryTimeout;
		}

		public String getPrefix(){
			return prefix;
		}

		public void setPrefix(String prefix){
			this.prefix = prefix;
		}

		public int getExpire(){
			return expire;
		}

		public void setExpire(int expire){
			this.expire = expire;
		}

		public boolean isSessionManagerDeleteInvalidSessions(){
			return getSessionManagerDeleteInvalidSessions();
		}

		public boolean getSessionManagerDeleteInvalidSessions(){
			return sessionManagerDeleteInvalidSessions;
		}

		public void setSessionManagerDeleteInvalidSessions(boolean sessionManagerDeleteInvalidSessions){
			this.sessionManagerDeleteInvalidSessions = sessionManagerDeleteInvalidSessions;
		}

		public Cookie getCookie(){
			return cookie;
		}

		public void setCookie(Cookie cookie){
			this.cookie = cookie;
		}

	}

	public final static class RememberMeConfig {

		/**
		 * Remember Me Cookie
		 */
		private Cookie cookie = new Cookie(CookieRememberMeManager.DEFAULT_REMEMBER_ME_COOKIE_NAME,
				org.apache.shiro.web.servlet.Cookie.ONE_YEAR, false);

		public Cookie getCookie(){
			return cookie;
		}

		public void setCookie(Cookie cookie){
			this.cookie = cookie;
		}

	}

}
