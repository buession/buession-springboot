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
package com.buession.springboot.pac4j.config;

import com.buession.springboot.pac4j.core.EncryptionMethod;
import com.buession.springboot.pac4j.core.JWEAlgorithm;
import com.buession.springboot.pac4j.core.JWSAlgorithm;
import org.pac4j.core.util.generator.ValueGenerator;

/**
 * JWT 配置
 *
 * @author Yong.Teng
 * @since 2.0.0
 */
public class Jwt extends BaseConfig {

	public final static String PREFIX = PROPERTIES_PREFIX + ".jwt";

	/**
	 * Secret 签名算法
	 */
	private JWSAlgorithm secretSignatureAlgorithm = JWSAlgorithm.HS256;

	/**
	 * Secret 加密算法
	 */
	private JWEAlgorithm secretEncryptionAlgorithm = JWEAlgorithm.DIR;

	/**
	 * 加密方法
	 */
	private EncryptionMethod encryptionMethod = EncryptionMethod.A128CBC_HS256;

	/**
	 * 加密 Key
	 */
	private String encryptionKey;

	/**
	 * 标识符生成器
	 */
	private Class<ValueGenerator> identifierGenerator;

	/**
	 * 是否支持 GET 请求
	 */
	private boolean supportGetRequest = false;

	/**
	 * 是否支持 POST 请求
	 */
	private boolean supportPostRequest = true;

	/**
	 * Jwt 请求头模式配置
	 */
	private Header header = new Header();

	/**
	 * Jwt Cookie 模式配置
	 */
	private Cookie cookie = new Cookie();

	/**
	 * Jwt 参数模式配置
	 */
	private Parameter parameter = new Parameter();

	/**
	 * 返回 Secret 签名算法
	 *
	 * @return Secret 签名算法
	 */
	public JWSAlgorithm getSecretSignatureAlgorithm(){
		return secretSignatureAlgorithm;
	}

	/**
	 * 设置 Secret 签名算法
	 *
	 * @param secretSignatureAlgorithm
	 * 		Secret 签名算法
	 */
	public void setSecretSignatureAlgorithm(JWSAlgorithm secretSignatureAlgorithm){
		this.secretSignatureAlgorithm = secretSignatureAlgorithm;
	}

	/**
	 * 返回 Secret 加密算法
	 *
	 * @return Secret 加密算法
	 */
	public JWEAlgorithm getSecretEncryptionAlgorithm(){
		return secretEncryptionAlgorithm;
	}

	/**
	 * 设置 Secret 加密算法
	 *
	 * @param secretEncryptionAlgorithm
	 * 		Secret 加密算法
	 */
	public void setSecretEncryptionAlgorithm(JWEAlgorithm secretEncryptionAlgorithm){
		this.secretEncryptionAlgorithm = secretEncryptionAlgorithm;
	}

	/**
	 * 返回加密方法
	 *
	 * @return 加密方法
	 */
	public EncryptionMethod getEncryptionMethod(){
		return encryptionMethod;
	}

	/**
	 * 设置加密方法
	 *
	 * @param encryptionMethod
	 * 		加密方法
	 */
	public void setEncryptionMethod(EncryptionMethod encryptionMethod){
		this.encryptionMethod = encryptionMethod;
	}

	/**
	 * 返回加密 Key
	 *
	 * @return 加密 Key
	 */
	public String getEncryptionKey(){
		return encryptionKey;
	}

	/**
	 * 设置加密 Key
	 *
	 * @param encryptionKey
	 * 		加密 Key
	 */
	public void setEncryptionKey(String encryptionKey){
		this.encryptionKey = encryptionKey;
	}

	/**
	 * 返回标识符生成器
	 *
	 * @return 标识符生成器
	 */
	public Class<ValueGenerator> getIdentifierGenerator(){
		return identifierGenerator;
	}

	/**
	 * 设置标识符生成器
	 *
	 * @param identifierGenerator
	 * 		标识符生成器
	 */
	public void setIdentifierGenerator(Class<ValueGenerator> identifierGenerator){
		this.identifierGenerator = identifierGenerator;
	}

	/**
	 * 返回是否支持 GET 请求
	 *
	 * @return 是否支持 GET 请求
	 */
	public boolean isSupportGetRequest(){
		return getSupportGetRequest();
	}

	/**
	 * 返回是否支持 GET 请求
	 *
	 * @return 是否支持 GET 请求
	 */
	public boolean getSupportGetRequest(){
		return supportGetRequest;
	}

	/**
	 * 设置是否支持 GET 请求
	 *
	 * @param supportGetRequest
	 * 		是否支持 GET 请求
	 */
	public void setSupportGetRequest(boolean supportGetRequest){
		this.supportGetRequest = supportGetRequest;
	}

	/**
	 * 返回是否支持 POST 请求
	 *
	 * @return 是否支持 POST 请求
	 */
	public boolean isSupportPostRequest(){
		return getSupportPostRequest();
	}

	/**
	 * 返回是否支持 POST 请求
	 *
	 * @return 是否支持 POST 请求
	 */
	public boolean getSupportPostRequest(){
		return supportPostRequest;
	}

	/**
	 * 设置是否支持 POST 请求
	 *
	 * @param supportPostRequest
	 * 		是否支持 POST 请求
	 */
	public void setSupportPostRequest(boolean supportPostRequest){
		this.supportPostRequest = supportPostRequest;
	}

	/**
	 * 返回 Jwt 请求头模式配置
	 *
	 * @return Jwt 请求头模式配置
	 */
	public Header getHeader(){
		return header;
	}

	/**
	 * 设置 Jwt 请求头模式配置
	 *
	 * @param header
	 * 		Jwt 请求头模式配置
	 */
	public void setHeader(Header header){
		this.header = header;
	}

	/**
	 * 返回 Jwt Cookie 模式配置
	 *
	 * @return Jwt Cookie 模式配置
	 */
	public Cookie getCookie(){
		return cookie;
	}

	/**
	 * 设置 Jwt Cookie 模式配置
	 *
	 * @param cookie
	 * 		Jwt Cookie 模式配置
	 */
	public void setCookie(Cookie cookie){
		this.cookie = cookie;
	}

	/**
	 * 返回 Jwt 参数模式配置
	 *
	 * @return Jwt 参数模式配置
	 */
	public Parameter getParameter(){
		return parameter;
	}

	/**
	 * 设置 Jwt 参数模式配置
	 *
	 * @param parameter
	 * 		Jwt 参数模式配置
	 */
	public void setParameter(Parameter parameter){
		this.parameter = parameter;
	}

	/**
	 * Jwt 请求头模式配置
	 */
	public final static class Header extends BaseClientConfig {

		/**
		 * 请求头名称
		 */
		private String headerName = com.buession.lang.Constants.EMPTY_STRING;

		/**
		 * 请求头前缀
		 */
		private String prefixHeader = com.buession.lang.Constants.EMPTY_STRING;

		/**
		 * 构造函数
		 */
		public Header(){
			super("jwt-header");
		}

		/**
		 * 返回请求头名称
		 *
		 * @return 请求头名称
		 */
		public String getHeaderName(){
			return headerName;
		}

		/**
		 * 设置请求头名称
		 *
		 * @param headerName
		 * 		请求头名称
		 */
		public void setHeaderName(String headerName){
			this.headerName = headerName;
		}

		/**
		 * 返回请求头前缀
		 *
		 * @return 请求头前缀
		 */
		public String getPrefixHeader(){
			return prefixHeader;
		}

		/**
		 * 设置请求头前缀
		 *
		 * @param prefixHeader
		 * 		请求头前缀
		 */
		public void setPrefixHeader(String prefixHeader){
			this.prefixHeader = prefixHeader;
		}

	}

	/**
	 * Jwt Cookie 模式配置
	 */
	public final static class Cookie extends BaseClientConfig {

		/**
		 * Cookie 名称
		 */
		private String cookieName;

		/**
		 * 构造函数
		 */
		public Cookie(){
			super("jwt-cookie");
		}

		/**
		 * 返回 Cookie 名称
		 *
		 * @return Cookie 名称
		 */
		public String getCookieName(){
			return cookieName;
		}

		/**
		 * 设置 Cookie 名称
		 *
		 * @param cookieName
		 * 		Cookie 名称
		 */
		public void setCookieName(String cookieName){
			this.cookieName = cookieName;
		}

	}

	/**
	 * Jwt 参数模式配置
	 */
	public final static class Parameter extends BaseClientConfig {

		/**
		 * 参数名称
		 */
		private String parameterName = com.buession.lang.Constants.EMPTY_STRING;

		/**
		 * 构造函数
		 */
		public Parameter(){
			super("jwt-parameter");
		}

		/**
		 * 返回参数名称
		 *
		 * @return 参数名称
		 */
		public String getParameterName(){
			return parameterName;
		}

		/**
		 * 设置参数名称
		 *
		 * @param parameterName
		 * 		参数名称
		 */
		public void setParameterName(String parameterName){
			this.parameterName = parameterName;
		}

	}

}
