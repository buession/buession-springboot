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

import com.buession.security.shiro.Cookie;
import com.buession.security.shiro.session.SessionDAO;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;

/**
 * Shiro Session 配置
 *
 * @author Yong.Teng
 * @since 1.2.2
 */
public class Session {

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
	private Cookie cookie = new Cookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME, SimpleCookie.DEFAULT_MAX_AGE, false);

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
