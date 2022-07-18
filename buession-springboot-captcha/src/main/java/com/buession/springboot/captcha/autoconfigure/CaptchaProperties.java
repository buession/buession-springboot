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

import com.buession.security.captcha.aliyun.AliyunParameter;
import com.buession.security.captcha.geetest.api.v3.GeetestV3Parameter;
import com.buession.security.captcha.geetest.api.v4.GeetestV4Parameter;
import com.buession.security.captcha.tencent.TencentParameter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * 极验行为验证配置
 *
 * @author Yong.Teng
 * @since 1.2.0
 */
@ConfigurationProperties(prefix = CaptchaProperties.PREFIX)
public class CaptchaProperties {

	public final static String PREFIX = "spring.captcha";

	/**
	 * 是否启用验证码
	 */
	@Deprecated
	private boolean enabled;

	/**
	 * 前端 JavaScript 库地址
	 */
	private String[] javascript;

	/**
	 * 阿里云行为验证码配置
	 */
	private Aliyun aliyun;

	/**
	 * 极验行为验证码配置
	 */
	private Geetest geetest;

	/**
	 * 腾讯行为验证码配置
	 */
	private Tencent tencent;

	/**
	 * 返回是否启用验证码
	 *
	 * @return 是否启用验证码
	 */
	@Deprecated
	@DeprecatedConfigurationProperty
	public boolean isEnabled(){
		return enabled;
	}

	/**
	 * 设置是否启用验证码
	 *
	 * @param enabled
	 * 		是否启用验证码
	 */
	@Deprecated
	@DeprecatedConfigurationProperty
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}

	/**
	 * 返回前端 JavaScript 库地址
	 *
	 * @return 前端 JavaScript 库地址
	 */
	public String[] getJavascript(){
		return javascript;
	}

	/**
	 * 设置前端 JavaScript 库地址
	 *
	 * @param javascript
	 * 		前端 JavaScript 库地址
	 */
	public void setJavascript(String[] javascript){
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
		 * AccessKey ID
		 */
		private String accessKeyId;

		/**
		 * AccessKey Secret
		 */
		private String accessKeySecret;

		/**
		 * 服务使用的 App Key
		 */
		private String appKey;

		/**
		 * 区域 ID
		 */
		private String regionId;

		/**
		 * 提交参数配置
		 */
		@NestedConfigurationProperty
		private AliyunParameter parameter = new AliyunParameter();

		/**
		 * 返回 AccessKey ID
		 *
		 * @return AccessKey ID
		 */
		public String getAccessKeyId(){
			return accessKeyId;
		}

		/**
		 * 设置 AccessKey ID
		 *
		 * @param accessKeyId
		 * 		AccessKey ID
		 */
		public void setAccessKeyId(String accessKeyId){
			this.accessKeyId = accessKeyId;
		}

		/**
		 * 返回 AccessKey Secret
		 *
		 * @return AccessKey Secret
		 */
		public String getAccessKeySecret(){
			return accessKeySecret;
		}

		/**
		 * 设置 AccessKey Secret
		 *
		 * @param accessKeySecret
		 * 		AccessKey Secret
		 */
		public void setAccessKeySecret(String accessKeySecret){
			this.accessKeySecret = accessKeySecret;
		}

		/**
		 * 返回服务使用的 App Key
		 *
		 * @return 服务使用的 App Key
		 */
		public String getAppKey(){
			return appKey;
		}

		/**
		 * 设置服务使用的 App Key
		 *
		 * @param appKey
		 * 		服务使用的 App Key
		 */
		public void setAppKey(String appKey){
			this.appKey = appKey;
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

		/**
		 * 返回提交参数配置
		 *
		 * @return 提交参数配置
		 */
		public AliyunParameter getParameter(){
			return parameter;
		}

		/**
		 * 设置提交参数配置
		 *
		 * @param parameter
		 * 		提交参数配置
		 */
		public void setParameter(AliyunParameter parameter){
			this.parameter = parameter;
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
		 * V3 版本配置
		 */
		@NestedConfigurationProperty
		private V3 v3 = new V3();

		/**
		 * V4 版本配置
		 */
		@NestedConfigurationProperty
		private V4 v4 = new V4();

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

		/**
		 * 返回 V3 版本配置
		 *
		 * @return V3 版本配置
		 */
		public V3 getV3(){
			return v3;
		}

		/**
		 * 设置 V3 版本配置
		 *
		 * @param v3
		 * 		V3 版本配置
		 */
		public void setV3(V3 v3){
			this.v3 = v3;
		}

		/**
		 * 返回 V4 版本配置
		 *
		 * @return V4 版本配置
		 */
		public V4 getV4(){
			return v4;
		}

		/**
		 * 设置 V4 版本配置
		 *
		 * @param v4
		 * 		V4 版本配置
		 */
		public void setV4(V4 v4){
			this.v4 = v4;
		}

		public final static class V3 {

			/**
			 * 提交参数配置
			 */
			private GeetestV3Parameter parameter = new GeetestV3Parameter();

			/**
			 * 返回提交参数配置
			 *
			 * @return 提交参数配置
			 */
			public GeetestV3Parameter getParameter(){
				return parameter;
			}

			/**
			 * 设置提交参数配置
			 *
			 * @param parameter
			 * 		提交参数配置
			 */
			public void setParameter(GeetestV3Parameter parameter){
				this.parameter = parameter;
			}

		}

		public final static class V4 {

			/**
			 * 提交参数配置
			 */
			private GeetestV4Parameter parameter = new GeetestV4Parameter();

			/**
			 * 返回提交参数配置
			 *
			 * @return 提交参数配置
			 */
			public GeetestV4Parameter getParameter(){
				return parameter;
			}

			/**
			 * 设置提交参数配置
			 *
			 * @param parameter
			 * 		提交参数配置
			 */
			public void setParameter(GeetestV4Parameter parameter){
				this.parameter = parameter;
			}

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
		 * 提交参数配置
		 */
		private TencentParameter parameter = new TencentParameter();

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
		 * 返回提交参数配置
		 *
		 * @return 提交参数配置
		 */
		public TencentParameter getParameter(){
			return parameter;
		}

		/**
		 * 设置提交参数配置
		 *
		 * @param parameter
		 * 		提交参数配置
		 */
		public void setParameter(TencentParameter parameter){
			this.parameter = parameter;
		}

	}

}
