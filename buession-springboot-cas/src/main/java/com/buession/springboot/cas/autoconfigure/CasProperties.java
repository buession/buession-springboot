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
package com.buession.springboot.cas.autoconfigure;

import org.pac4j.cas.config.CasProtocol;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;

/**
 * CAS 客户端配置
 *
 * @author Yong.Teng
 */
@ConfigurationProperties(prefix = "spring.cas")
public class CasProperties {

	/**
	 * CAS 版本
	 */
	private CasProtocol protocol = CasProtocol.CAS30;

	/**
	 * CAS 登录地址
	 */
	private String loginUrl;

	/**
	 * CAS 客户端 URL 前缀
	 */
	private String prefixUrl;

	/**
	 * CAS 登录成功跳转地址
	 */
	private String callbackUrl;

	public CasProtocol getProtocol(){
		return protocol;
	}

	public void setProtocol(final CasProtocol protocol){
		this.protocol = protocol;
	}

	public String getLoginUrl(){
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl){
		this.loginUrl = loginUrl;
	}

	public String getPrefixUrl(){
		return prefixUrl;
	}

	public void setPrefixUrl(String prefixUrl){
		this.prefixUrl = prefixUrl;
	}

	public String getCallbackUrl(){
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl){
		this.callbackUrl = callbackUrl;
	}

	@ConfigurationProperties(prefix = "cas")
	@Deprecated
	public final static class DeprecatedCasProperties extends CasProperties {

		/**
		 * CAS 版本
		 */
		@Deprecated
		private CasProtocol protocol = CasProtocol.CAS30;

		/**
		 * CAS 登录地址
		 */
		@Deprecated
		private String loginUrl;

		/**
		 * CAS 客户端 URL 前缀
		 */
		@Deprecated
		private String prefixUrl;

		/**
		 * CAS 登录成功跳转地址
		 */
		@Deprecated
		private String callbackUrl;

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.cas.protocol")
		@Override
		public void setProtocol(CasProtocol protocol){
			super.setProtocol(protocol);
			this.protocol = protocol;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.cas.login-url")
		@Override
		public void setLoginUrl(String loginUrl){
			super.setLoginUrl(loginUrl);
			this.loginUrl = loginUrl;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.cas.prefix-url")
		@Override
		public void setPrefixUrl(String prefixUrl){
			super.setPrefixUrl(prefixUrl);
			this.prefixUrl = prefixUrl;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.cas.callback-url")
		@Override
		public void setCallbackUrl(String callbackUrl){
			super.setCallbackUrl(callbackUrl);
			this.callbackUrl = callbackUrl;
		}

	}

}