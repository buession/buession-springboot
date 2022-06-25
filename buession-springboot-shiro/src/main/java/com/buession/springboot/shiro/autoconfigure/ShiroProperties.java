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
package com.buession.springboot.shiro.autoconfigure;

import com.buession.security.shiro.Cookie;
import com.buession.security.shiro.cache.AbstractCacheManager;
import com.buession.security.shiro.session.AbstractSessionDAO;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.boot.context.properties.ConfigurationProperties;
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

	/**
	 * Shiro Session 配置
	 *
	 * @author Yong.Teng
	 * @since 2.0.0
	 */
	class Session {

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
		 * Session 前缀
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

	/**
	 * Shiro 缓存配置
	 *
	 * @author Yong.Teng
	 * @since 2.0.0
	 */
	class Cache {

		/**
		 * 缓存 Key 前缀
		 */
		private String prefix = AbstractCacheManager.DEFAULT_KEY_PREFIX;

		/**
		 * 缓存过期时间
		 */
		private int expire = AbstractCacheManager.DEFAULT_EXPIRE;

		/**
		 * Principal Id
		 */
		private String principalIdFieldName = AbstractCacheManager.DEFAULT_PRINCIPAL_ID_FIELD_NAME;

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

	/**
	 * Shiro RememberMe 配置
	 *
	 * @author Yong.Teng
	 * @since 2.0.0
	 */
	class RememberMe {

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

	/**
	 * Pac4j 配置
	 *
	 * @author Yong.Teng
	 * @since 2.0.0
	 */
	class Pac4j {

		/**
		 * 客户端名称
		 */
		private Set<String> clients;

		/**
		 * 默认客户端名称
		 */
		private String defaultClient;

		/**
		 * 是否允许多个 Profile
		 */
		private boolean multiProfile;

		/**
		 * 认证器名称
		 */
		private String[] authorizers;

		private String[] matchers;

		private boolean saveInSession = true;

		/**
		 * 登录成功默认跳转地址
		 */
		private String defaultUrl;

		/**
		 * 登出成功默认跳转地址
		 */
		private String logoutRedirectUrl;

		private String logoutUrlPattern;

		/**
		 * 本地是否退出登录
		 */
		private boolean localLogout = true;

		/**
		 * 认证中心是否退出登录
		 */
		private boolean centralLogout = true;

		public Set<String> getClients(){
			return clients;
		}

		public void setClients(Set<String> clients){
			this.clients = clients;
		}

		public String getDefaultClient(){
			return defaultClient;
		}

		public void setDefaultClient(String defaultClient){
			this.defaultClient = defaultClient;
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

		public String[] getMatchers(){
			return matchers;
		}

		public void setMatchers(String[] matchers){
			this.matchers = matchers;
		}

		public boolean isSaveInSession(){
			return getSaveInSession();
		}

		public boolean getSaveInSession(){
			return saveInSession;
		}

		public void setSaveInSession(boolean saveInSession){
			this.saveInSession = saveInSession;
		}

		public String getDefaultUrl(){
			return defaultUrl;
		}

		public void setDefaultUrl(String defaultUrl){
			this.defaultUrl = defaultUrl;
		}

		public String getLogoutRedirectUrl(){
			return logoutRedirectUrl;
		}

		public void setLogoutRedirectUrl(String logoutRedirectUrl){
			this.logoutRedirectUrl = logoutRedirectUrl;
		}

		public String getLogoutUrlPattern(){
			return logoutUrlPattern;
		}

		public void setLogoutUrlPattern(String logoutUrlPattern){
			this.logoutUrlPattern = logoutUrlPattern;
		}

		public boolean isLocalLogout(){
			return getLocalLogout();
		}

		public boolean getLocalLogout(){
			return localLogout;
		}

		public void setLocalLogout(boolean localLogout){
			this.localLogout = localLogout;
		}

		public boolean isCentralLogout(){
			return getCentralLogout();
		}

		public boolean getCentralLogout(){
			return centralLogout;
		}

		public void setCentralLogout(boolean centralLogout){
			this.centralLogout = centralLogout;
		}

	}

}
