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
 * | Copyright @ 2013-2019 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.shiro.autoconfigure;

import com.buession.security.shiro.Cookie;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Set;

/**
 * @author Yong.Teng
 */
@ConfigurationProperties(prefix = "shiro")
public class ShiroProperties {

	private String loginUrl;

	private String successUrl;

	private String unauthorizedUrl;

	@NestedConfigurationProperty
	private Session session = new Session();

	@NestedConfigurationProperty
	private RememberMeConfig rememberMe = new RememberMeConfig();

	private Set<String> clients;

	private boolean multiProfile;

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

	public final static class Session {

		private boolean userNativeSessionManager;

		private boolean sessionIdCookieEnabled = true;

		private boolean sessionIdUrlRewritingEnabled = true;

		private String prefix;

		private int expire;

		private int timeout;

		private Cookie cookie = new Cookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME, SimpleCookie.DEFAULT_MAX_AGE,
				false);

		public boolean isUserNativeSessionManager(){
			return getUserNativeSessionManager();
		}

		public boolean getUserNativeSessionManager(){
			return userNativeSessionManager;
		}

		public void setUserNativeSessionManager(boolean userNativeSessionManager){
			this.userNativeSessionManager = userNativeSessionManager;
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

		public int getTimeout(){
			return timeout;
		}

		public void setTimeout(int timeout){
			this.timeout = timeout;
		}

		public Cookie getCookie(){
			return cookie;
		}

		public void setCookie(Cookie cookie){
			this.cookie = cookie;
		}

	}

	public final static class RememberMeConfig {

		private Cookie cookie = new Cookie(CookieRememberMeManager.DEFAULT_REMEMBER_ME_COOKIE_NAME, org.apache.shiro
				.web.servlet.Cookie.ONE_YEAR, false);

		public Cookie getCookie(){
			return cookie;
		}

		public void setCookie(Cookie cookie){
			this.cookie = cookie;
		}
	}

}
