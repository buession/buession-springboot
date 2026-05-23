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

/**
 * Basic Auth 客户端配置
 *
 * @author Yong.Teng
 * @since 4.0.0
 */
abstract class BaseBasicAuthConfig {

	static abstract class BaseDirectBasicAuthConfig extends DirectClientConfig {

		/**
		 * Realm Name
		 */
		private String realmName = "authentication required";

		/**
		 * 构造函数
		 *
		 * @param name
		 * 		Client 名称
		 */
		public BaseDirectBasicAuthConfig(String name) {
			super(name);
		}

		/**
		 * 返回 Realm Name
		 *
		 * @return Realm Name
		 */
		public String getRealmName() {
			return realmName;
		}

		/**
		 * 设置 Realm Name
		 *
		 * @param realmName
		 * 		Realm Name
		 */
		public void setRealmName(String realmName) {
			this.realmName = realmName;
		}

	}

	static abstract class BaseIndirectBasicAuthConfig extends IndirectClientConfig {

		/**
		 * Realm Name
		 */
		private String realmName = "authentication required";

		/**
		 * 构造函数
		 *
		 * @param name
		 * 		Client 名称
		 */
		public BaseIndirectBasicAuthConfig(String name) {
			super(name);
		}

		/**
		 * 返回 Realm Name
		 *
		 * @return Realm Name
		 */
		public String getRealmName() {
			return realmName;
		}

		/**
		 * 设置 Realm Name
		 *
		 * @param realmName
		 * 		Realm Name
		 */
		public void setRealmName(String realmName) {
			this.realmName = realmName;
		}

	}


}
