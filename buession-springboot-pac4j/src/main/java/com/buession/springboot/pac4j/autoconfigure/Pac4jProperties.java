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
package com.buession.springboot.pac4j.autoconfigure;

import com.buession.security.pac4j.http.JsonAjaxRequestResolver;
import com.buession.springboot.pac4j.config.BaseConfig;
import com.buession.springboot.pac4j.config.Cas;
import com.buession.springboot.pac4j.config.Http;
import com.buession.springboot.pac4j.config.Jwt;
import com.buession.springboot.pac4j.config.OAuth;
import org.pac4j.core.http.ajax.AjaxRequestResolver;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

/**
 * @author Yong.Teng
 * @since 2.0.0
 */
@ConfigurationProperties(prefix = Pac4jProperties.PREFIX)
public class Pac4jProperties {

	public final static String PREFIX = "spring.pac4j";

	/**
	 * 启用认证的客户端类型名称，需要和各配置 {@link BaseConfig.BaseClientConfig} 类型中的名称保持一致
	 */
	private Set<String> clients;

	/**
	 * Compute if a HTTP request is an AJAX one and the appropriate response.
	 */
	private Class<? extends AjaxRequestResolver> ajaxRequestResolverClass = JsonAjaxRequestResolver.class;

	/**
	 * CAS 配置
	 */
	private Cas cas;

	/**
	 * HTTP 配置
	 */
	private Http http;

	/**
	 * JWT 配置
	 */
	private Jwt jwt;

	/**
	 * OAuth 配置
	 */
	private OAuth oAuth;

	/**
	 * 返回启用认证的客户端类型名称，需要和各配置 {@link BaseConfig.BaseClientConfig} 类型中的名称保持一致
	 *
	 * @return 启用认证的客户端类型名称
	 */
	public Set<String> getClients(){
		return clients;
	}

	/**
	 * 设置启用认证的客户端类型名称，需要和各配置 {@link BaseConfig.BaseClientConfig} 类型中的名称保持一致
	 *
	 * @param clients
	 * 		启用认证的客户端类型名称
	 */
	public void setClients(Set<String> clients){
		this.clients = clients;
	}

	/**
	 * Return Compute if a HTTP request is an AJAX one and the appropriate response.
	 *
	 * @return Compute if a HTTP request is an AJAX one and the appropriate response.
	 */
	public Class<? extends AjaxRequestResolver> getAjaxRequestResolverClass(){
		return ajaxRequestResolverClass;
	}

	/**
	 * Set Compute if a HTTP request is an AJAX one and the appropriate response.
	 *
	 * @param ajaxRequestResolverClass
	 * 		Compute if a HTTP request is an AJAX one and the appropriate response
	 */
	public void setAjaxRequestResolverClass(
			Class<? extends AjaxRequestResolver> ajaxRequestResolverClass){
		this.ajaxRequestResolverClass = ajaxRequestResolverClass;
	}

	/**
	 * 返回 CAS 配置
	 *
	 * @return CAS 配置
	 */
	public Cas getCas(){
		return cas;
	}

	/**
	 * 设置 CAS 配置
	 *
	 * @param cas
	 * 		CAS 配置
	 */
	public void setCas(Cas cas){
		this.cas = cas;
	}

	/**
	 * 返回 HTTP 配置
	 *
	 * @return HTTP 配置
	 */
	public Http getHttp(){
		return http;
	}

	/**
	 * 设置 HTTP 配置
	 *
	 * @param http
	 * 		HTTP 配置
	 */
	public void setHttp(Http http){
		this.http = http;
	}

	/**
	 * 返回 JWT 配置
	 *
	 * @return JWT 配置
	 */
	public Jwt getJwt(){
		return jwt;
	}

	/**
	 * 设置 JWT 配置
	 *
	 * @param jwt
	 * 		JWT 配置
	 */
	public void setJwt(Jwt jwt){
		this.jwt = jwt;
	}

	/**
	 * 返回 OAuth 配置
	 *
	 * @return OAuth 配置
	 */
	public OAuth getOAuth(){
		return oAuth;
	}

	/**
	 * 设置 OAuth 配置
	 *
	 * @param oAuth
	 * 		OAuth 配置
	 */
	public void setOAuth(OAuth oAuth){
		this.oAuth = oAuth;
	}

}
