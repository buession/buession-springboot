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
 * | Copyright @ 2013-2023 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.shiro.autoconfigure;

import com.buession.security.shiro.Cookie;
import com.buession.security.shiro.cache.AbstractCacheManager;
import com.buession.security.shiro.session.AbstractSessionDAO;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * Shiro 配置
 *
 * @author Yong.Teng
 */
@ConfigurationProperties(prefix = ShiroProperties.PREFIX)
public class ShiroProperties {

	public final static String PREFIX = "spring.shiro";

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
	 * Session 配置
	 */
	@NestedConfigurationProperty
	private Session session = new Session();

	/**
	 * 缓存配置
	 */
	@NestedConfigurationProperty
	private Cache cache = new Cache();

	/**
	 * 记住我配置
	 */
	@NestedConfigurationProperty
	private RememberMe rememberMe = new RememberMe();

	/**
	 * 返回登录地址
	 *
	 * @return 登录地址
	 */
	public String getLoginUrl() {
		return loginUrl;
	}

	/**
	 * 设置登录地址
	 *
	 * @param loginUrl
	 * 		登录地址
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	/**
	 * 返回登录成功跳转地址
	 *
	 * @return 登录成功跳转地址
	 */
	public String getSuccessUrl() {
		return successUrl;
	}

	/**
	 * 设置登录成功跳转地址
	 *
	 * @param successUrl
	 * 		登录成功跳转地址
	 */
	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	/**
	 * 返回授权失败跳转地址
	 *
	 * @return 授权失败跳转地址
	 */
	public String getUnauthorizedUrl() {
		return unauthorizedUrl;
	}

	/**
	 * 设置授权失败跳转地址
	 *
	 * @param unauthorizedUrl
	 * 		授权失败跳转地址
	 */
	public void setUnauthorizedUrl(String unauthorizedUrl) {
		this.unauthorizedUrl = unauthorizedUrl;
	}

	/**
	 * 返回 Session 配置
	 *
	 * @return Session 配置
	 */
	public Session getSession() {
		return session;
	}

	/**
	 * 设置 Session 配置
	 *
	 * @param session
	 * 		Session 配置
	 */
	public void setSession(Session session) {
		this.session = session;
	}

	/**
	 * 返回缓存配置
	 *
	 * @return 缓存配置
	 */
	public Cache getCache() {
		return cache;
	}

	/**
	 * 设置缓存配置
	 *
	 * @param cache
	 * 		缓存配置
	 */
	public void setCache(Cache cache) {
		this.cache = cache;
	}

	/**
	 * 返回记住我配置
	 *
	 * @return 记住我配置
	 */
	public RememberMe getRememberMe() {
		return rememberMe;
	}

	/**
	 * 设置记住我配置
	 *
	 * @param rememberMe
	 * 		记住我配置
	 */
	public void setRememberMe(RememberMe rememberMe) {
		this.rememberMe = rememberMe;
	}

	/**
	 * Shiro Session 配置
	 *
	 * @author Yong.Teng
	 * @since 2.0.0
	 */
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
		private boolean sessionInMemoryEnabled = AbstractSessionDAO.DEFAULT_SESSION_IN_MEMORY_ENABLED;

		/**
		 * Session 在内存中保存超时时间（单位：毫秒）
		 */
		private long sessionInMemoryTimeout = AbstractSessionDAO.DEFAULT_SESSION_IN_MEMORY_TIMEOUT;

		/**
		 * Session 名称前缀
		 */
		private String prefix = AbstractSessionDAO.DEFAULT_SESSION_KEY_PREFIX;

		/**
		 * Session 有效期，当为 -2 时，则为 Session timeout 的值；为 -1 时，表示永不过期
		 */
		private int expire = AbstractSessionDAO.DEFAULT_EXPIRE;

		/**
		 * 如果 Session 过期或者无效后，是否删除
		 */
		private boolean sessionManagerDeleteInvalidSessions = true;

		/**
		 * Session Cookie
		 */
		private Cookie cookie = new Cookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME, SimpleCookie.DEFAULT_MAX_AGE,
				false, true);

		/**
		 * 返回是否使用原生 Session 管理器
		 *
		 * @return 是否使用原生 Session 管理器
		 */
		public boolean isUseNativeSessionManager() {
			return getUseNativeSessionManager();
		}

		/**
		 * 返回是否使用原生 Session 管理器
		 *
		 * @return 是否使用原生 Session 管理器
		 */
		public boolean getUseNativeSessionManager() {
			return useNativeSessionManager;
		}

		/**
		 * 设置是否使用原生 Session 管理器
		 *
		 * @param useNativeSessionManager
		 * 		是否使用原生 Session 管理器
		 */
		public void setUseNativeSessionManager(boolean useNativeSessionManager) {
			this.useNativeSessionManager = useNativeSessionManager;
		}

		/**
		 * 返回是否开启 SESSION ID Cookie
		 *
		 * @return 是否开启 SESSION ID Cookie
		 */
		public boolean isSessionIdCookieEnabled() {
			return getSessionIdCookieEnabled();
		}

		/**
		 * 返回是否开启 SESSION ID Cookie
		 *
		 * @return 是否开启 SESSION ID Cookie
		 */
		public boolean getSessionIdCookieEnabled() {
			return sessionIdCookieEnabled;
		}

		/**
		 * 设置是否开启 SESSION ID Cookie
		 *
		 * @param sessionIdCookieEnabled
		 * 		是否开启 SESSION ID Cookie
		 */
		public void setSessionIdCookieEnabled(boolean sessionIdCookieEnabled) {
			this.sessionIdCookieEnabled = sessionIdCookieEnabled;
		}

		/**
		 * 返回是否开启 URL 重写，开启后 URL 中会带 JSESSIONID
		 *
		 * @return 是否开启 URL 重写
		 */
		public boolean isSessionIdUrlRewritingEnabled() {
			return getSessionIdUrlRewritingEnabled();
		}

		/**
		 * 返回是否开启 URL 重写，开启后 URL 中会带 JSESSIONID
		 *
		 * @return 是否开启 URL 重写
		 */
		public boolean getSessionIdUrlRewritingEnabled() {
			return sessionIdUrlRewritingEnabled;
		}

		/**
		 * 设置是否开启 URL 重写，开启后 URL 中会带 JSESSIONID
		 *
		 * @param sessionIdUrlRewritingEnabled
		 * 		是否开启 URL 重写
		 */
		public void setSessionIdUrlRewritingEnabled(boolean sessionIdUrlRewritingEnabled) {
			this.sessionIdUrlRewritingEnabled = sessionIdUrlRewritingEnabled;
		}

		/**
		 * 返回是否开启 Session 在内存中保存
		 *
		 * @return 是否开启 Session 在内存中保存
		 */
		public boolean isSessionInMemoryEnabled() {
			return getSessionInMemoryEnabled();
		}

		/**
		 * 返回是否开启 Session 在内存中保存
		 *
		 * @return 是否开启 Session 在内存中保存
		 */
		public boolean getSessionInMemoryEnabled() {
			return sessionInMemoryEnabled;
		}

		/**
		 * 设置是否开启 Session 在内存中保存
		 *
		 * @param sessionInMemoryEnabled
		 * 		是否开启 Session 在内存中保存
		 */
		public void setSessionInMemoryEnabled(boolean sessionInMemoryEnabled) {
			this.sessionInMemoryEnabled = sessionInMemoryEnabled;
		}

		/**
		 * 返回 Session 在内存中保存超时时间（单位：毫秒）
		 *
		 * @return Session 在内存中保存超时时间
		 */
		public long getSessionInMemoryTimeout() {
			return sessionInMemoryTimeout;
		}

		/**
		 * 设置 Session 在内存中保存超时时间（单位：毫秒）
		 *
		 * @param sessionInMemoryTimeout
		 * 		Session 在内存中保存超时时间
		 */
		public void setSessionInMemoryTimeout(long sessionInMemoryTimeout) {
			this.sessionInMemoryTimeout = sessionInMemoryTimeout;
		}

		/**
		 * 返回 Session 名称前缀
		 *
		 * @return Session 名称前缀
		 */
		public String getPrefix() {
			return prefix;
		}

		/**
		 * 设置 Session 名称前缀
		 *
		 * @param prefix
		 * 		Session 名称前缀
		 */
		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}

		/**
		 * 返回 Session 有效期，当为 -2 时，则为 Session timeout 的值；为 -1 时，表示永不过期
		 *
		 * @return Session 有效期
		 */
		public int getExpire() {
			return expire;
		}

		/**
		 * 设置 Session 有效期，当为 -2 时，则为 Session timeout 的值；为 -1 时，表示永不过期
		 *
		 * @param expire
		 * 		Session 有效期
		 */
		public void setExpire(int expire) {
			this.expire = expire;
		}

		/**
		 * 返回如果 Session 过期或者无效后，是否删除
		 *
		 * @return 如果 Session 过期或者无效后，是否删除
		 */
		public boolean isSessionManagerDeleteInvalidSessions() {
			return getSessionManagerDeleteInvalidSessions();
		}

		/**
		 * 返回如果 Session 过期或者无效后，是否删除
		 *
		 * @return 如果 Session 过期或者无效后，是否删除
		 */
		public boolean getSessionManagerDeleteInvalidSessions() {
			return sessionManagerDeleteInvalidSessions;
		}

		/**
		 * 设置如果 Session 过期或者无效后，是否删除
		 *
		 * @param sessionManagerDeleteInvalidSessions
		 * 		如果 Session 过期或者无效后，是否删除
		 */
		public void setSessionManagerDeleteInvalidSessions(boolean sessionManagerDeleteInvalidSessions) {
			this.sessionManagerDeleteInvalidSessions = sessionManagerDeleteInvalidSessions;
		}

		/**
		 * 返回 Session Cookie
		 *
		 * @return Session Cookie
		 */
		public Cookie getCookie() {
			return cookie;
		}

		/**
		 * 设置 Session Cookie
		 *
		 * @param cookie
		 * 		Session Cookie
		 */
		public void setCookie(Cookie cookie) {
			this.cookie = cookie;
		}

	}

	/**
	 * Shiro 缓存配置
	 *
	 * @author Yong.Teng
	 * @since 2.0.0
	 */
	public final static class Cache {

		/**
		 * 缓存 Key 前缀
		 */
		private String prefix = AbstractCacheManager.DEFAULT_KEY_PREFIX;

		/**
		 * 缓存过期时间（单位：秒）
		 */
		private int expire = AbstractCacheManager.DEFAULT_EXPIRE;

		/**
		 * Principal Id
		 */
		private String principalIdFieldName = AbstractCacheManager.DEFAULT_PRINCIPAL_ID_FIELD_NAME;

		/**
		 * 返回缓存 Key 前缀
		 *
		 * @return 缓存 Key 前缀
		 */
		public String getPrefix() {
			return prefix;
		}

		/**
		 * 设置缓存 Key 前缀
		 *
		 * @param prefix
		 * 		缓存 Key 前缀
		 */
		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}

		/**
		 * 返回缓存过期时间
		 *
		 * @return 缓存过期时间（单位：秒）
		 */
		public int getExpire() {
			return expire;
		}

		/**
		 * 设置缓存过期时间（单位：秒）
		 *
		 * @param expire
		 * 		缓存过期时间
		 */
		public void setExpire(int expire) {
			this.expire = expire;
		}

		/**
		 * 返回 Principal Id
		 *
		 * @return Principal Id
		 */
		public String getPrincipalIdFieldName() {
			return principalIdFieldName;
		}

		/**
		 * 设置 Principal Id
		 *
		 * @param principalIdFieldName
		 * 		Principal Id
		 */
		public void setPrincipalIdFieldName(String principalIdFieldName) {
			this.principalIdFieldName = principalIdFieldName;
		}

	}

	/**
	 * Shiro 记住我配置
	 *
	 * @author Yong.Teng
	 * @since 2.0.0
	 */
	public final static class RememberMe {

		/**
		 * 记住我 Cookie
		 */
		private Cookie cookie = new Cookie(CookieRememberMeManager.DEFAULT_REMEMBER_ME_COOKIE_NAME,
				org.apache.shiro.web.servlet.Cookie.ONE_YEAR, false, true);

		/**
		 * 返回记住我 Cookie
		 *
		 * @return 记住我 Cookie
		 */
		public Cookie getCookie() {
			return cookie;
		}

		/**
		 * 设置记住我 Cookie
		 *
		 * @param cookie
		 * 		记住我 Cookie
		 */
		public void setCookie(Cookie cookie) {
			this.cookie = cookie;
		}

	}

}
