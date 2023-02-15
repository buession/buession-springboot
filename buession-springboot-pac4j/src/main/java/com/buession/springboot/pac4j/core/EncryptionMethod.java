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
 * @author Yong.Teng
 * @since 2.0.1
 */
@Deprecated
public enum EncryptionMethod {
	A128CBC_HS256(com.nimbusds.jose.EncryptionMethod.A128CBC_HS256.getName(),
			com.nimbusds.jose.EncryptionMethod.A128CBC_HS256),

	A192CBC_HS384(com.nimbusds.jose.EncryptionMethod.A192CBC_HS384.getName(),
			com.nimbusds.jose.EncryptionMethod.A192CBC_HS384),

	A256CBC_HS512(com.nimbusds.jose.EncryptionMethod.A256CBC_HS512.getName(),
			com.nimbusds.jose.EncryptionMethod.A256CBC_HS512),

	A128GCM(com.nimbusds.jose.EncryptionMethod.A128GCM.getName(), com.nimbusds.jose.EncryptionMethod.A128GCM),

	A192GCM(com.nimbusds.jose.EncryptionMethod.A192GCM.getName(), com.nimbusds.jose.EncryptionMethod.A192GCM),

	A256GCM(com.nimbusds.jose.EncryptionMethod.A256GCM.getName(), com.nimbusds.jose.EncryptionMethod.A256GCM);

	private final String name;

	private final com.nimbusds.jose.EncryptionMethod source;

	EncryptionMethod(final String name, final com.nimbusds.jose.EncryptionMethod source){
		this.name = name;
		this.source = source;
	}

	public String getName(){
		return name;
	}

	public com.nimbusds.jose.EncryptionMethod getSource(){
		return source;
	}

}
