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
	private String secretSignatureAlgorithm = "HS256";

	/**
	 * Secret 加密算法
	 */
	private String secretEncryptionAlgorithm = "DIR";

	/**
	 * 加密方法
	 */
	private String encryptionMethod = "A128CBC_HS256";

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

	public String getEncryptionKey(){
		return encryptionKey;
	}

	public void setEncryptionKey(String encryptionKey){
		this.encryptionKey = encryptionKey;
	}

	public String getSecretSignatureAlgorithm(){
		return secretSignatureAlgorithm;
	}

	public void setSecretSignatureAlgorithm(String secretSignatureAlgorithm){
		this.secretSignatureAlgorithm = secretSignatureAlgorithm;
	}

	public String getSecretEncryptionAlgorithm(){
		return secretEncryptionAlgorithm;
	}

	public void setSecretEncryptionAlgorithm(String secretEncryptionAlgorithm){
		this.secretEncryptionAlgorithm = secretEncryptionAlgorithm;
	}

	public String getEncryptionMethod(){
		return encryptionMethod;
	}

	public void setEncryptionMethod(String encryptionMethod){
		this.encryptionMethod = encryptionMethod;
	}

	public Class<ValueGenerator> getIdentifierGenerator(){
		return identifierGenerator;
	}

	public void setIdentifierGenerator(Class<ValueGenerator> identifierGenerator){
		this.identifierGenerator = identifierGenerator;
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

	public Header getHeader(){
		return header;
	}

	public void setHeader(Header header){
		this.header = header;
	}

	public Cookie getCookie(){
		return cookie;
	}

	public void setCookie(Cookie cookie){
		this.cookie = cookie;
	}

	public Parameter getParameter(){
		return parameter;
	}

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
		 * 参数名
		 */
		private String parameterName = com.buession.lang.Constants.EMPTY_STRING;

		public Parameter(){
			super("jwt-parameter");
		}

		/**
		 * 返回参数名
		 *
		 * @return 参数名
		 */
		public String getParameterName(){
			return parameterName;
		}

		/**
		 * 设置参数名
		 *
		 * @param parameterName
		 * 		参数名
		 */
		public void setParameterName(String parameterName){
			this.parameterName = parameterName;
		}

	}

}
