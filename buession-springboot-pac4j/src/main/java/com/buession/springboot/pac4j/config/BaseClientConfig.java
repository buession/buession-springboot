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
 * | Copyright @ 2013-2026 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.pac4j.config;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Client 公共配置
 *
 * @author Yong.Teng
 * @since 4.0.0
 */
public abstract class BaseClientConfig {

	protected final static String PROPERTIES_PREFIX = "spring.pac4j.client";

	/**
	 * Client 名称
	 */
	private String name;

	/**
	 * 是否多 Profile
	 */
	private boolean multiProfile = false;

	/**
	 * 是否将 Profile 保存到 Session 中
	 */
	protected Boolean saveProfileInSession;

	/**
	 * 客户自定义属性
	 */
	private Map<String, Object> customProperties = new LinkedHashMap<>();

	/**
	 * 构造函数
	 *
	 * @param name
	 * 		Client 名称
	 */
	public BaseClientConfig(String name) {
		this.name = name;
	}

	/**
	 * 返回 Client 名称
	 *
	 * @return Client 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置 Client 名称
	 *
	 * @param name
	 * 		Client 名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回是否多 Profile
	 *
	 * @return 是否多 Profile
	 */
	public boolean isMultiProfile() {
		return multiProfile;
	}

	/**
	 * 设置是否多 Profile
	 *
	 * @param multiProfile
	 * 		是否多 Profile
	 */
	public void setMultiProfile(boolean multiProfile) {
		this.multiProfile = multiProfile;
	}

	/**
	 * 返回是否将 Profile 保存到 Session 中
	 *
	 * @return 是否将 Profile 保存到 Session 中
	 */
	public Boolean isSaveProfileInSession() {
		return getSaveProfileInSession();
	}

	/**
	 * 返回是否将 Profile 保存到 Session 中
	 *
	 * @return 是否将 Profile 保存到 Session 中
	 */
	public Boolean getSaveProfileInSession() {
		return saveProfileInSession;
	}

	/**
	 * 设置是否将 Profile 保存到 Session 中
	 *
	 * @param saveProfileInSession
	 * 		是否将 Profile 保存到 Session 中
	 */
	public void setSaveProfileInSession(Boolean saveProfileInSession) {
		this.saveProfileInSession = saveProfileInSession;
	}

	/**
	 * 返回客户自定义属性
	 *
	 * @return 客户自定义属性
	 */
	public Map<String, Object> getCustomProperties() {
		return customProperties;
	}

	/**
	 * 设置客户自定义属性
	 *
	 * @param customProperties
	 * 		客户自定义属性
	 */
	public void setCustomProperties(Map<String, Object> customProperties) {
		this.customProperties = customProperties;
	}

}
