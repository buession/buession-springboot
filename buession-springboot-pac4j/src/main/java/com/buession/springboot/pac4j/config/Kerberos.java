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
 * Kerberos 配置
 *
 * @author Yong.Teng
 * @since 4.0.0
 */
public class Kerberos extends BaseClientConfig {

	public final static String PREFIX = PROPERTIES_PREFIX + ".kerberos";

	private Direct direct = new Direct();

	private Indirect indirect = new Indirect();

	public Direct getDirect() {
		return direct;
	}

	public void setDirect(Direct direct) {
		this.direct = direct;
	}

	public Indirect getIndirect() {
		return indirect;
	}

	public void setIndirect(Indirect indirect) {
		this.indirect = indirect;
	}

	/**
	 * 构造函数
	 */
	public Kerberos() {
		super(null);
	}

	public final static class Direct extends DirectClientConfig {

		/**
		 * 构造函数
		 */
		public Direct() {
			super("direct-kerberos");
		}

	}

	public final static class Indirect extends IndirectClientConfig {

		/**
		 * 构造函数
		 */
		public Indirect() {
			super("indirect-kerberos");
		}

	}
}
