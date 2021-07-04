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

import java.util.Set;

/**
 * Pac4j 配置
 *
 * @author Yong.Teng
 * @since 1.2.2
 */
public class Pac4j {

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
