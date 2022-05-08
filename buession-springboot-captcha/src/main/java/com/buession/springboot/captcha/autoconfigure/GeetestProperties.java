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
 * | Copyright @ 2013-2022 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.captcha.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 极验行为验证配置
 *
 * @author Yong.Teng
 * @since 1.2.0
 */
@ConfigurationProperties(prefix = "spring.captcha.geetest")
public class GeetestProperties {

	/**
	 * 公钥
	 */
	private String geetestId;

	/**
	 * 私钥
	 */
	private String geetestKey;

	/**
	 * 版本
	 */
	private String version = "v4";

	/**
	 * 前端 JavaScript 库地址
	 */
	private String javascript;

	/**
	 * 返回公钥
	 *
	 * @return 公钥
	 */
	public String getGeetestId(){
		return geetestId;
	}

	/**
	 * 设置公钥
	 *
	 * @param geetestId
	 * 		公钥
	 */
	public void setGeetestId(String geetestId){
		this.geetestId = geetestId;
	}

	/**
	 * 返回私钥
	 *
	 * @return 私钥
	 */
	public String getGeetestKey(){
		return geetestKey;
	}

	/**
	 * 设置私钥
	 *
	 * @param geetestKey
	 * 		私钥
	 */
	public void setGeetestKey(String geetestKey){
		this.geetestKey = geetestKey;
	}

	/**
	 * 返回版本
	 *
	 * @return 版本
	 */
	public String getVersion(){
		return version;
	}

	/**
	 * 设置版本
	 *
	 * @param version
	 * 		版本
	 */
	public void setVersion(String version){
		this.version = version;
	}

	/**
	 * 返回前端 JavaScript 库地址
	 *
	 * @return 前端 JavaScript 库地址
	 */
	public String getJavascript(){
		return javascript;
	}

	/**
	 * 设置前端 JavaScript 库地址
	 *
	 * @param javascript
	 * 		前端 JavaScript 库地址
	 */
	public void setJavascript(String javascript){
		this.javascript = javascript;
	}

}
