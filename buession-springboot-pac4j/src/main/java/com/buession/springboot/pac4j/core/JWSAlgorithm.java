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
package com.buession.springboot.pac4j.core;

/**
 * JWS 算法
 *
 * @author Yong.Teng
 * @since 2.0.1
 */
public enum JWSAlgorithm {
	HS256(com.nimbusds.jose.JWSAlgorithm.HS256.getName(), com.nimbusds.jose.JWSAlgorithm.HS256),

	HS384(com.nimbusds.jose.JWSAlgorithm.HS384.getName(), com.nimbusds.jose.JWSAlgorithm.HS384),

	HS512(com.nimbusds.jose.JWSAlgorithm.HS512.getName(), com.nimbusds.jose.JWSAlgorithm.HS512),

	RS256(com.nimbusds.jose.JWSAlgorithm.RS256.getName(), com.nimbusds.jose.JWSAlgorithm.RS256),

	RS384(com.nimbusds.jose.JWSAlgorithm.RS384.getName(), com.nimbusds.jose.JWSAlgorithm.RS384),

	RS512(com.nimbusds.jose.JWSAlgorithm.RS512.getName(), com.nimbusds.jose.JWSAlgorithm.RS512),

	ES256(com.nimbusds.jose.JWSAlgorithm.ES256.getName(), com.nimbusds.jose.JWSAlgorithm.ES256),

	ES256K(com.nimbusds.jose.JWSAlgorithm.ES256K.getName(), com.nimbusds.jose.JWSAlgorithm.ES256K),

	ES384(com.nimbusds.jose.JWSAlgorithm.ES384.getName(), com.nimbusds.jose.JWSAlgorithm.ES384),

	ES512(com.nimbusds.jose.JWSAlgorithm.ES512.getName(), com.nimbusds.jose.JWSAlgorithm.ES512),

	PS256(com.nimbusds.jose.JWSAlgorithm.PS256.getName(), com.nimbusds.jose.JWSAlgorithm.PS256),

	PS384(com.nimbusds.jose.JWSAlgorithm.PS384.getName(), com.nimbusds.jose.JWSAlgorithm.PS384),

	PS512(com.nimbusds.jose.JWSAlgorithm.PS512.getName(), com.nimbusds.jose.JWSAlgorithm.PS512),

	EDDSA(com.nimbusds.jose.JWSAlgorithm.EdDSA.getName(), com.nimbusds.jose.JWSAlgorithm.EdDSA);

	private final String name;

	private final com.nimbusds.jose.JWSAlgorithm source;

	JWSAlgorithm(final String name, final com.nimbusds.jose.JWSAlgorithm source){
		this.name = name;
		this.source = source;
	}

	public String getName(){
		return name;
	}

	public com.nimbusds.jose.JWSAlgorithm getSource(){
		return source;
	}

}
