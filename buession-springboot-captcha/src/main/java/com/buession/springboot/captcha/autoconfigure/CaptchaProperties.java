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
package com.buession.springboot.captcha.autoconfigure;

import com.buession.springboot.captcha.Geetest;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author Yong.Teng
 */
@ConfigurationProperties(prefix = "spring.captcha")
public class CaptchaProperties {

	/**
	 * 极验行为验证配置
	 */
	@NestedConfigurationProperty
	private Geetest geetest = new Geetest();

	public Geetest getGeetest(){
		return geetest;
	}

	public void setGeetest(Geetest geetest){
		this.geetest = geetest;
	}

	@ConfigurationProperties(prefix = "captcha")
	@Deprecated
	public final static class DeprecatedCaptchaProperties extends CaptchaProperties {

		/**
		 * 极验行为验证配置
		 */
		@Deprecated
		@NestedConfigurationProperty
		private Geetest geetest = new Geetest();

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.captcha.geetest")
		@Override
		public void setGeetest(Geetest geetest){
			super.setGeetest(geetest);
			this.geetest = geetest;
		}

	}

}
