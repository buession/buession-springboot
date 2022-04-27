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
package com.buession.springboot.jwt.autoconfigure;

import com.buession.lang.Constants;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JWT 配置
 *
 * @author Yong.Teng
 */
@ConfigurationProperties(prefix = "spring.jwt")
public class JwtProperties {

	/**
	 * 参数名
	 */
	private String parameterName = Constants.EMPTY_STRING;

	/**
	 * HTTP 头前缀
	 */
	private String prefixHeader = Constants.EMPTY_STRING;

	/**
	 * 加密 Key
	 */
	private String encryptionKey;

	/**
	 * 是否支持 GET 请求
	 */
	private boolean supportGetRequest = false;

	/**
	 * 是否支持 POST 请求
	 */
	private boolean supportPostRequest = true;

	public String getParameterName(){
		return parameterName;
	}

	public void setParameterName(String parameterName){
		this.parameterName = parameterName;
	}

	public String getPrefixHeader(){
		return prefixHeader;
	}

	public void setPrefixHeader(String prefixHeader){
		this.prefixHeader = prefixHeader;
	}

	public String getEncryptionKey(){
		return encryptionKey;
	}

	public void setEncryptionKey(String encryptionKey){
		this.encryptionKey = encryptionKey;
	}

	public boolean isSupportGetRequest(){
		return getSupportGetRequest();
	}

	public boolean getSupportGetRequest(){
		return supportGetRequest;
	}

	public void setSupportGetRequest(boolean supportGetRequest){
		this.supportGetRequest = supportGetRequest;
	}

	public boolean isSupportPostRequest(){
		return getSupportPostRequest();
	}

	public boolean getSupportPostRequest(){
		return supportPostRequest;
	}

	public void setSupportPostRequest(boolean supportPostRequest){
		this.supportPostRequest = supportPostRequest;
	}

}
