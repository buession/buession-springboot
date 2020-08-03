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
 * | Copyright @ 2013-2020 Buession.com Inc.														|
 * +------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.oss.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;

/**
 * OSS 配置
 *
 * @author Yong.Teng
 */
@ConfigurationProperties(prefix = "spring.oss")
public class OssProperties {

	/**
	 * Endpoint 地址
	 */
	private String endpoint;

	/**
	 * Key
	 */
	private String key;

	/**
	 * Secret
	 */
	private String secret;

	private String baseUrl;

	public String getEndpoint(){
		return endpoint;
	}

	public void setEndpoint(String endpoint){
		this.endpoint = endpoint;
	}

	public String getKey(){
		return key;
	}

	public void setKey(String key){
		this.key = key;
	}

	public String getSecret(){
		return secret;
	}

	public void setSecret(String secret){
		this.secret = secret;
	}

	public String getBaseUrl(){
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl){
		this.baseUrl = baseUrl;
	}

	@ConfigurationProperties(prefix = "oss")
	@Deprecated
	public final static class DeprecatedOssProperties extends OssProperties {

		/**
		 * Endpoint 地址
		 */
		private String endpoint;

		/**
		 * Key
		 */
		@Deprecated
		private String accessKeyId;

		/**
		 * Secret
		 */
		@Deprecated
		private String secretAccessKey;

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "更通用的命名", replacement = "spring.oss.endpoint")
		@Override
		public void setEndpoint(String endpoint){
			super.setEndpoint(endpoint);
			this.endpoint = endpoint;
		}

		@Deprecated
		public String getAccessKeyId(){
			return accessKeyId;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "更通用的命名", replacement = "spring.oss.key")
		public void setAccessKeyId(String accessKeyId){
			this.accessKeyId = accessKeyId;
			setKey(accessKeyId);
		}

		@Deprecated
		public String getSecretAccessKey(){
			return secretAccessKey;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "更通用的命名", replacement = "spring.oss.secret")
		public void setSecretAccessKey(String secretAccessKey){
			this.secretAccessKey = secretAccessKey;
			setSecret(secretAccessKey);
		}

	}

}