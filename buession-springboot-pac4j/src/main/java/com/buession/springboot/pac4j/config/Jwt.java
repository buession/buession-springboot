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
 * | Copyright @ 2013-2024 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.pac4j.config;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWSAlgorithm;
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
	private Boolean supportGetRequest;

	/**
	 * 是否支持 POST 请求
	 */
	private Boolean supportPostRequest;

	/**
	 * 返回 Secret 签名算法
	 *
	 * @return Secret 签名算法
	 */
	public JWSAlgorithm getSecretSignatureAlgorithm() {
		return secretSignatureAlgorithm;
	}

	/**
	 * 设置 Secret 签名算法
	 *
	 * @param secretSignatureAlgorithm
	 * 		Secret 签名算法
	 */
	public void setSecretSignatureAlgorithm(JWSAlgorithm secretSignatureAlgorithm) {
		this.secretSignatureAlgorithm = secretSignatureAlgorithm;
	}

	/**
	 * 返回 Secret 加密算法
	 *
	 * @return Secret 加密算法
	 */
	public JWEAlgorithm getSecretEncryptionAlgorithm() {
		return secretEncryptionAlgorithm;
	}

	/**
	 * 设置 Secret 加密算法
	 *
	 * @param secretEncryptionAlgorithm
	 * 		Secret 加密算法
	 */
	public void setSecretEncryptionAlgorithm(JWEAlgorithm secretEncryptionAlgorithm) {
		this.secretEncryptionAlgorithm = secretEncryptionAlgorithm;
	}

	/**
	 * 返回加密方法
	 *
	 * @return 加密方法
	 */
	public EncryptionMethod getEncryptionMethod() {
		return encryptionMethod;
	}

	/**
	 * 设置加密方法
	 *
	 * @param encryptionMethod
	 * 		加密方法
	 */
	public void setEncryptionMethod(EncryptionMethod encryptionMethod) {
		this.encryptionMethod = encryptionMethod;
	}

	/**
	 * 返回加密 Key
	 *
	 * @return 加密 Key
	 */
	public String getEncryptionKey() {
		return encryptionKey;
	}

	/**
	 * 设置加密 Key
	 *
	 * @param encryptionKey
	 * 		加密 Key
	 */
	public void setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}

	/**
	 * 返回标识符生成器
	 *
	 * @return 标识符生成器
	 */
	public Class<ValueGenerator> getIdentifierGenerator() {
		return identifierGenerator;
	}

	/**
	 * 设置标识符生成器
	 *
	 * @param identifierGenerator
	 * 		标识符生成器
	 */
	public void setIdentifierGenerator(Class<ValueGenerator> identifierGenerator) {
		this.identifierGenerator = identifierGenerator;
	}

	/**
	 * 返回是否支持 GET 请求
	 *
	 * @return 是否支持 GET 请求
	 */
	public Boolean isSupportGetRequest() {
		return getSupportGetRequest();
	}

	/**
	 * 返回是否支持 GET 请求
	 *
	 * @return 是否支持 GET 请求
	 */
	public Boolean getSupportGetRequest() {
		return supportGetRequest;
	}

	/**
	 * 设置是否支持 GET 请求
	 *
	 * @param supportGetRequest
	 * 		是否支持 GET 请求
	 */
	public void setSupportGetRequest(Boolean supportGetRequest) {
		this.supportGetRequest = supportGetRequest;
	}

	/**
	 * 返回是否支持 POST 请求
	 *
	 * @return 是否支持 POST 请求
	 */
	public Boolean isSupportPostRequest() {
		return getSupportPostRequest();
	}

	/**
	 * 返回是否支持 POST 请求
	 *
	 * @return 是否支持 POST 请求
	 */
	public Boolean getSupportPostRequest() {
		return supportPostRequest;
	}

	/**
	 * 设置是否支持 POST 请求
	 *
	 * @param supportPostRequest
	 * 		是否支持 POST 请求
	 */
	public void setSupportPostRequest(Boolean supportPostRequest) {
		this.supportPostRequest = supportPostRequest;
	}

}
