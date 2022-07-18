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
 * JWE 算法
 *
 * @author Yong.Teng
 * @since 2.0.1
 */
public enum JWEAlgorithm {
	RSA1_5(com.nimbusds.jose.JWEAlgorithm.RSA1_5.getName(), com.nimbusds.jose.JWEAlgorithm.RSA1_5),

	RSA_OAEP(com.nimbusds.jose.JWEAlgorithm.RSA_OAEP.getName(), com.nimbusds.jose.JWEAlgorithm.RSA_OAEP),

	RSA_OAEP_256(com.nimbusds.jose.JWEAlgorithm.RSA_OAEP_256.getName(), com.nimbusds.jose.JWEAlgorithm.RSA_OAEP_256),

	A128KW(com.nimbusds.jose.JWEAlgorithm.A128KW.getName(), com.nimbusds.jose.JWEAlgorithm.A128KW),

	A192KW(com.nimbusds.jose.JWEAlgorithm.A192KW.getName(), com.nimbusds.jose.JWEAlgorithm.A192KW),

	A256KW(com.nimbusds.jose.JWEAlgorithm.A256KW.getName(), com.nimbusds.jose.JWEAlgorithm.A256KW),

	DIR(com.nimbusds.jose.JWEAlgorithm.DIR.getName(), com.nimbusds.jose.JWEAlgorithm.DIR),

	ECDH_ES(com.nimbusds.jose.JWEAlgorithm.ECDH_ES.getName(), com.nimbusds.jose.JWEAlgorithm.ECDH_ES),

	ECDH_ES_A128KW(com.nimbusds.jose.JWEAlgorithm.ECDH_ES_A128KW.getName(),
			com.nimbusds.jose.JWEAlgorithm.ECDH_ES_A128KW),

	ECDH_ES_A192KW(com.nimbusds.jose.JWEAlgorithm.ECDH_ES_A192KW.getName(),
			com.nimbusds.jose.JWEAlgorithm.ECDH_ES_A192KW),

	ECDH_ES_A256KW(com.nimbusds.jose.JWEAlgorithm.ECDH_ES_A256KW.getName(),
			com.nimbusds.jose.JWEAlgorithm.ECDH_ES_A256KW),

	A128GCMKW(com.nimbusds.jose.JWEAlgorithm.A128GCMKW.getName(), com.nimbusds.jose.JWEAlgorithm.A128GCMKW),

	A192GCMKW(com.nimbusds.jose.JWEAlgorithm.A192GCMKW.getName(), com.nimbusds.jose.JWEAlgorithm.A192GCMKW),

	A256GCMKW(com.nimbusds.jose.JWEAlgorithm.A256GCMKW.getName(), com.nimbusds.jose.JWEAlgorithm.A256GCMKW),

	PBES2_HS256_A128KW(com.nimbusds.jose.JWEAlgorithm.PBES2_HS256_A128KW.getName(),
			com.nimbusds.jose.JWEAlgorithm.PBES2_HS256_A128KW),

	PBES2_HS384_A192KW(com.nimbusds.jose.JWEAlgorithm.PBES2_HS384_A192KW.getName(),
			com.nimbusds.jose.JWEAlgorithm.PBES2_HS384_A192KW),

	PBES2_HS512_A256KW(com.nimbusds.jose.JWEAlgorithm.PBES2_HS512_A256KW.getName(),
			com.nimbusds.jose.JWEAlgorithm.PBES2_HS512_A256KW);

	private final String name;

	private final com.nimbusds.jose.JWEAlgorithm source;

	JWEAlgorithm(final String name, final com.nimbusds.jose.JWEAlgorithm source){
		this.name = name;
		this.source = source;
	}

	public String getName(){
		return name;
	}

	public com.nimbusds.jose.JWEAlgorithm getSource(){
		return source;
	}

}
