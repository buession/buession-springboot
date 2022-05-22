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
@ConfigurationProperties(prefix = "spring.captcha")
public class CaptchaProperties {

	/**
	 * 前端 JavaScript 库地址
	 */
	private String javascript;

	/**
	 * 阿里云行为验证码配置
	 */
	private Aliyun aliyun;

	/**
	 * 极验行为验证码配置
	 */
	private Geetest geetest;

	/**
	 * 网易行为验证码配置
	 */
	private Netease netease;

	/**
	 * 腾讯行为验证码配置
	 */
	private Tencent tencent;

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

	/**
	 * 返回阿里云行为验证码配置
	 *
	 * @return 阿里云行为验证码配置
	 */
	public Aliyun getAliyun(){
		return aliyun;
	}

	/**
	 * 设置阿里云行为验证码配置
	 *
	 * @param aliyun
	 * 		阿里云行为验证码配置
	 */
	public void setAliyun(Aliyun aliyun){
		this.aliyun = aliyun;
	}

	/**
	 * 返回极验行为验证码配置
	 *
	 * @return 极验行为验证码配置
	 */
	public Geetest getGeetest(){
		return geetest;
	}

	/**
	 * 设置极验行为验证码配置
	 *
	 * @param geetest
	 * 		极验行为验证码配置
	 */
	public void setGeetest(Geetest geetest){
		this.geetest = geetest;
	}

	/**
	 * 返回网易行为验证码配置
	 *
	 * @return 网易行为验证码配置
	 */
	public Netease getNetease(){
		return netease;
	}

	/**
	 * 设置网易行为验证码配置
	 *
	 * @param netease
	 * 		网易行为验证码配置
	 */
	public void setNetease(Netease netease){
		this.netease = netease;
	}

	/**
	 * 返回腾讯行为验证码配置
	 *
	 * @return 腾讯行为验证码配置
	 */
	public Tencent getTencent(){
		return tencent;
	}

	/**
	 * 设置腾讯行为验证码配置
	 *
	 * @param tencent
	 * 		腾讯行为验证码配置
	 */
	public void setTencent(Tencent tencent){
		this.tencent = tencent;
	}

	/**
	 * 阿里云行为验证码配置
	 *
	 * @author yong.teng
	 * @since 2.0.0
	 */
	public static class Aliyun {

		/**
		 * 密钥 ID
		 */
		private String accessKeyId;

		/**
		 * 服务使用的 App Key
		 */
		private String accessKeySecret;

		/**
		 * 区域 ID
		 */
		private String regionId;

		/**
		 * 返回密钥 ID
		 *
		 * @return 密钥 ID
		 */
		public String getAccessKeyId(){
			return accessKeyId;
		}

		/**
		 * 设置密钥 ID
		 *
		 * @param accessKeyId
		 * 		密钥 ID
		 */
		public void setAccessKeyId(String accessKeyId){
			this.accessKeyId = accessKeyId;
		}

		/**
		 * 返回服务使用的 App Key
		 *
		 * @return 服务使用的 App Key
		 */
		public String getAccessKeySecret(){
			return accessKeySecret;
		}

		/**
		 * 设置服务使用的 App Key
		 *
		 * @param accessKeySecret
		 * 		服务使用的 App Key
		 */
		public void setAccessKeySecret(String accessKeySecret){
			this.accessKeySecret = accessKeySecret;
		}

		/**
		 * 返回区域 ID
		 *
		 * @return 区域 ID
		 */
		public String getRegionId(){
			return regionId;
		}

		/**
		 * 设置区域 ID
		 *
		 * @param regionId
		 * 		区域 ID
		 */
		public void setRegionId(String regionId){
			this.regionId = regionId;
		}

	}

	/**
	 * 极验行为验证码配置
	 *
	 * @author yong.teng
	 * @since 2.0.0
	 */
	public static class Geetest {

		/**
		 * 应用 ID
		 */
		private String appId;

		/**
		 * 密钥
		 */
		private String secretKey;

		/**
		 * 版本
		 */
		private String version = "v4";

		/**
		 * 返回应用 ID
		 *
		 * @return 应用 ID
		 */
		public String getAppId(){
			return appId;
		}

		/**
		 * 设置应用 ID
		 *
		 * @param appId
		 * 		应用 ID
		 */
		public void setAppId(String appId){
			this.appId = appId;
		}

		/**
		 * 返回密钥
		 *
		 * @return 密钥
		 */
		public String getSecretKey(){
			return secretKey;
		}

		/**
		 * 设置密钥
		 *
		 * @param secretKey
		 * 		密钥
		 */
		public void setSecretKey(String secretKey){
			this.secretKey = secretKey;
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

	}

	/**
	 * 网易行为验证码配置
	 *
	 * @author yong.teng
	 * @since 2.0.0
	 */
	public static class Netease {

		/**
		 * 应用 ID
		 */
		private String appId;

		/**
		 * 密钥
		 */
		private String secretKey;

		/**
		 * 返回应用 ID
		 *
		 * @return 应用 ID
		 */
		public String getAppId(){
			return appId;
		}

		/**
		 * 设置应用 ID
		 *
		 * @param appId
		 * 		应用 ID
		 */
		public void setAppId(String appId){
			this.appId = appId;
		}

		/**
		 * 返回密钥
		 *
		 * @return 密钥
		 */
		public String getSecretKey(){
			return secretKey;
		}

		/**
		 * 设置密钥
		 *
		 * @param secretKey
		 * 		密钥
		 */
		public void setSecretKey(String secretKey){
			this.secretKey = secretKey;
		}

	}

	/**
	 * 腾讯行为验证码配置
	 *
	 * @author yong.teng
	 * @since 2.0.0
	 */
	public static class Tencent {

		/**
		 * 应用 ID
		 */
		private String appId;

		/**
		 * 密钥
		 */
		private String secretKey;

		/**
		 * 返回应用 ID
		 *
		 * @return 应用 ID
		 */
		public String getAppId(){
			return appId;
		}

		/**
		 * 设置应用 ID
		 *
		 * @param appId
		 * 		应用 ID
		 */
		public void setAppId(String appId){
			this.appId = appId;
		}

		/**
		 * 返回密钥
		 *
		 * @return 密钥
		 */
		public String getSecretKey(){
			return secretKey;
		}

		/**
		 * 设置密钥
		 *
		 * @param secretKey
		 * 		密钥
		 */
		public void setSecretKey(String secretKey){
			this.secretKey = secretKey;
		}

	}

}
