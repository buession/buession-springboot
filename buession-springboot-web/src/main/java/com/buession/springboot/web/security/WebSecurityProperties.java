/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * =================================================================================================
 *
 * This software consists of voluntary contributions made by many individuals on behalf of the
 * Apache Software Foundation. For more information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 * +------------------------------------------------------------------------------------------------+
 * | License: http://www.apache.org/licenses/LICENSE-2.0.txt 										|
 * | Author: Yong.Teng <webmaster@buession.com> 													|
 * | Copyright @ 2013-2026 Buession.com Inc.														|
 * +------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.web.security;

import com.buession.security.web.config.Configurer;
import com.buession.security.web.xss.Options;
import com.buession.web.http.XssProtection;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for Spring Security.
 *
 * @author Yong.Teng
 * @since 1.2.0
 */
@ConfigurationProperties(prefix = WebSecurityProperties.PREFIX)
public class WebSecurityProperties extends Configurer {

	public final static String PREFIX = "spring.security";

	/**
	 * 是否禁用默认配置
	 */
	private boolean disableDefaults;

	/**
	 * 返回是否禁用默认配置
	 *
	 * @return 是否禁用默认配置
	 */
	public boolean isDisableDefaults() {
		return disableDefaults;
	}

	/**
	 * 设置是否禁用默认配置
	 *
	 * @param disableDefaults
	 * 		是否禁用默认配置
	 */
	public void setDisableDefaults(boolean disableDefaults) {
		this.disableDefaults = disableDefaults;
	}

	public void setXss(Xss xss) {
		super.setXss(xss);
	}

	public final static class Xss extends com.buession.security.web.config.Xss {

		/**
		 * 策略
		 */
		private Options.Policy mode = Options.Policy.ESCAPE;

		/**
		 * HTML 转义模式选项
		 */
		private Options.Escape escape;

		/**
		 * HTML 清理模式选项
		 */
		private Options.Clean clean;

		/**
		 * 策略配置文件
		 */
		private String policyConfigLocation;

		public Xss() {
			super();
			setPolicy(XssProtection.ENABLED);
		}

		/**
		 * 返回策略模式
		 *
		 * @return 策略模式
		 */
		public XssProtection getProtection() {
			return getPolicy();
		}

		/**
		 * 设置策略模式
		 *
		 * @param protection
		 * 		策略模式
		 */
		public void setProtection(XssProtection protection) {
			setPolicy(protection);
		}

		public Options.Policy getMode() {
			return mode;
		}

		public void setMode(Options.Policy mode) {
			this.mode = mode;
		}

		/**
		 * 返回策略配置文件
		 *
		 * @return 策略配置文件
		 */
		public String getPolicyConfigLocation() {
			return policyConfigLocation;
		}

		/**
		 * 设置策略配置文件
		 *
		 * @param policyConfigLocation
		 * 		策略配置文件
		 */
		public void setPolicyConfigLocation(String policyConfigLocation) {
			this.policyConfigLocation = policyConfigLocation;
		}

		/**
		 * 返回 HTML 转义模式选项
		 *
		 * @return HTML 转义模式选项
		 */
		public Options.Escape getEscape() {
			return escape;
		}

		/**
		 * 设置 HTML 转义模式选项
		 *
		 * @param escape
		 * 		HTML 转义模式选项
		 */
		public void setEscape(Options.Escape escape) {
			this.escape = escape;
		}

		/**
		 * 返回 HTML 清理模式选项
		 *
		 * @return HTML 清理模式选项
		 */
		public Options.Clean getClean() {
			return clean;
		}

		/**
		 * 设置 HTML 清理模式选项
		 *
		 * @param clean
		 * 		HTML 清理模式选项
		 */
		public void setClean(Options.Clean clean) {
			this.clean = clean;
		}

	}

}
