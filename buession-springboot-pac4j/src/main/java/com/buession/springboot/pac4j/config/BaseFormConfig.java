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
 * | Copyright @ 2013-2025 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.pac4j.config;

import org.pac4j.core.util.Pac4jConstants;

/**
 * Form Client 配置基类
 *
 * @author Yong.Teng
 * @since 4.0.0
 */
public abstract class BaseFormConfig {

	public static abstract class BaseDirectFormConfig extends DirectClientConfig {

		/**
		 * 用户名参数名称
		 */
		private String usernameParameter = Pac4jConstants.USERNAME;

		/**
		 * 密码参数名称
		 */
		private String passwordParameter = Pac4jConstants.PASSWORD;

		/**
		 * 构造函数
		 *
		 * @param name
		 * 		Client 名称
		 */
		public BaseDirectFormConfig(String name) {
			super(name);
		}

		/**
		 * 返回用户名参数名称
		 *
		 * @return 用户名参数名称
		 */
		public String getUsernameParameter() {
			return usernameParameter;
		}

		/**
		 * 设置用户名参数名称
		 *
		 * @param usernameParameter
		 * 		用户名参数名称
		 */
		public void setUsernameParameter(String usernameParameter) {
			this.usernameParameter = usernameParameter;
		}

		/**
		 * 返回密码参数名称
		 *
		 * @return 密码参数名称
		 */
		public String getPasswordParameter() {
			return passwordParameter;
		}

		/**
		 * 设置密码参数名称
		 *
		 * @param passwordParameter
		 * 		密码参数名称
		 */
		public void setPasswordParameter(String passwordParameter) {
			this.passwordParameter = passwordParameter;
		}

	}

	public static abstract class BaseIndirectFormConfig extends IndirectClientConfig {

		/**
		 * 用户名参数名称
		 */
		private String usernameParameter = Pac4jConstants.USERNAME;

		/**
		 * 密码参数名称
		 */
		private String passwordParameter = Pac4jConstants.PASSWORD;

		/**
		 * 构造函数
		 *
		 * @param name
		 * 		Client 名称
		 */
		public BaseIndirectFormConfig(String name) {
			super(name);
		}

		/**
		 * 返回用户名参数名称
		 *
		 * @return 用户名参数名称
		 */
		public String getUsernameParameter() {
			return usernameParameter;
		}

		/**
		 * 设置用户名参数名称
		 *
		 * @param usernameParameter
		 * 		用户名参数名称
		 */
		public void setUsernameParameter(String usernameParameter) {
			this.usernameParameter = usernameParameter;
		}

		/**
		 * 返回密码参数名称
		 *
		 * @return 密码参数名称
		 */
		public String getPasswordParameter() {
			return passwordParameter;
		}

		/**
		 * 设置密码参数名称
		 *
		 * @param passwordParameter
		 * 		密码参数名称
		 */
		public void setPasswordParameter(String passwordParameter) {
			this.passwordParameter = passwordParameter;
		}

	}

}
