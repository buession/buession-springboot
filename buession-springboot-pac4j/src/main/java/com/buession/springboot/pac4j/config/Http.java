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

import org.pac4j.core.util.Pac4jConstants;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * HTTP 配置
 *
 * @author Yong.Teng
 * @since 2.0.0
 */
public class Http extends BaseConfig {

	public final static String PREFIX = PROPERTIES_PREFIX + ".http";

	/**
	 * 登录成功跳转地址
	 */
	private String callbackUrl;

	/**
	 * Cookie 客户端配置
	 *
	 * @since 3.0.0
	 */
	@NestedConfigurationProperty
	private Cookie cookie = new Cookie();

	/**
	 * Direct Basic Auth 客户端配置
	 */
	@NestedConfigurationProperty
	private DirectBasicAuth directBasicAuth = new DirectBasicAuth();

	/**
	 * Direct Bearer Auth 客户端配置
	 *
	 * @since 3.0.0
	 */
	@NestedConfigurationProperty
	private DirectBearerAuth directBearerAuth = new DirectBearerAuth();

	/**
	 * Direct Digest Auth 客户端配置
	 *
	 * @since 3.0.0
	 */
	@NestedConfigurationProperty
	private DirectDigestAuth directDigestAuth = new DirectDigestAuth();

	/**
	 * Direct Form 客户端配置
	 *
	 * @since 3.0.0
	 */
	@NestedConfigurationProperty
	private DirectForm directForm = new DirectForm();

	/**
	 * Header 客户端配置
	 *
	 * @since 3.0.0
	 */
	@NestedConfigurationProperty
	private Header header = new Header();

	/**
	 * IP 客户端配置
	 *
	 * @since 3.0.0
	 */
	@NestedConfigurationProperty
	private Ip ip = new Ip();

	/**
	 * Parameter 客户端配置
	 *
	 * @since 3.0.0
	 */
	@NestedConfigurationProperty
	private Parameter parameter = new Parameter();

	/**
	 * X509 客户端配置
	 *
	 * @since 3.0.0
	 */
	@NestedConfigurationProperty
	private X509 x509 = new X509();

	/**
	 * 表单客户端配置
	 */
	@NestedConfigurationProperty
	private Form form = new Form();

	/**
	 * Indirect Basic Auth 客户端配置
	 */
	@NestedConfigurationProperty
	private IndirectBasicAuth indirectBasicAuth = new IndirectBasicAuth();

	/**
	 * 返回登录成功跳转地址
	 *
	 * @return 登录成功跳转地址
	 */
	public String getCallbackUrl() {
		return callbackUrl;
	}

	/**
	 * 设置登录成功跳转地址
	 *
	 * @param callbackUrl
	 * 		登录成功跳转地址
	 */
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	/**
	 * 返回 Cookie 客户端配置
	 *
	 * @return Cookie 客户端配置
	 *
	 * @since 3.0.0
	 */
	public Cookie getCookie() {
		return cookie;
	}

	/**
	 * 设置 Cookie 客户端配置
	 *
	 * @param cookie
	 * 		Cookie 客户端配置
	 *
	 * @since 3.0.0
	 */
	public void setCookie(Cookie cookie) {
		this.cookie = cookie;
	}

	/**
	 * 返回 Direct Basic Auth 客户端配置
	 *
	 * @return Direct Basic Auth 客户端配置
	 */
	public DirectBasicAuth getDirectBasicAuth() {
		return directBasicAuth;
	}

	/**
	 * 设置 Direct Basic Auth 客户端配置
	 *
	 * @param directBasicAuth
	 * 		Direct Basic Auth 客户端配置
	 */
	public void setDirectBasicAuth(DirectBasicAuth directBasicAuth) {
		this.directBasicAuth = directBasicAuth;
	}

	/**
	 * 返回 Direct Bearer Auth 客户端配置
	 *
	 * @return Direct Bearer Auth 客户端配置
	 *
	 * @since 3.0.0
	 */
	public DirectBearerAuth getDirectBearerAuth() {
		return directBearerAuth;
	}

	/**
	 * 设置 Direct Bearer Auth 客户端配置
	 *
	 * @param directBearerAuth
	 * 		Direct Bearer Auth 客户端配置
	 *
	 * @since 3.0.0
	 */
	public void setDirectBearerAuth(DirectBearerAuth directBearerAuth) {
		this.directBearerAuth = directBearerAuth;
	}

	/**
	 * 返回 Direct Digest Auth 客户端配置
	 *
	 * @return Direct Digest Auth 客户端配置
	 *
	 * @since 3.0.0
	 */
	public DirectDigestAuth getDirectDigestAuth() {
		return directDigestAuth;
	}

	/**
	 * 设置 Direct Digest Auth 客户端配置
	 *
	 * @param directDigestAuth
	 * 		Direct Digest Auth 客户端配置
	 *
	 * @since 3.0.0
	 */
	public void setDirectDigestAuth(DirectDigestAuth directDigestAuth) {
		this.directDigestAuth = directDigestAuth;
	}

	/**
	 * 返回 Direct Form 客户端配置
	 *
	 * @return Direct Form 客户端配置
	 *
	 * @since 3.0.0
	 */
	public DirectForm getDirectForm() {
		return directForm;
	}

	/**
	 * 设置 Direct Form 客户端配置
	 *
	 * @param directForm
	 * 		Direct Form 客户端配置
	 *
	 * @since 3.0.0
	 */
	public void setDirectForm(DirectForm directForm) {
		this.directForm = directForm;
	}

	/**
	 * 返回表单客户端配置
	 *
	 * @return 表单客户端配置
	 */
	public Form getForm() {
		return form;
	}

	/**
	 * 设置表单客户端配置
	 *
	 * @param form
	 * 		表单客户端配置
	 */
	public void setForm(Form form) {
		this.form = form;
	}

	/**
	 * 返回 Header 客户端配置
	 *
	 * @return Header 客户端配置
	 *
	 * @since 3.0.0
	 */
	public Header getHeader() {
		return header;
	}

	/**
	 * 设置 Header 客户端配置
	 *
	 * @param header
	 * 		Header 客户端配置
	 *
	 * @since 3.0.0
	 */
	public void setHeader(Header header) {
		this.header = header;
	}

	/**
	 * 返回 IP 客户端配置
	 *
	 * @return IP 客户端配置
	 *
	 * @since 3.0.0
	 */
	public Ip getIp() {
		return ip;
	}

	/**
	 * 设置 IP 客户端配置
	 *
	 * @param ip
	 * 		IP 客户端配置
	 *
	 * @since 3.0.0
	 */
	public void setIp(Ip ip) {
		this.ip = ip;
	}

	/**
	 * 返回 Parameter 客户端配置
	 *
	 * @return Parameter 客户端配置
	 *
	 * @since 3.0.0
	 */
	public Parameter getParameter() {
		return parameter;
	}

	/**
	 * 设置 Parameter 客户端配置
	 *
	 * @param parameter
	 * 		Parameter 客户端配置
	 *
	 * @since 3.0.0
	 */
	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}

	/**
	 * 返回 X509 客户端配置
	 *
	 * @return X509 客户端配置
	 *
	 * @since 3.0.0
	 */
	public X509 getX509() {
		return x509;
	}

	/**
	 * 设置 X509 客户端配置
	 *
	 * @param x509
	 * 		X509 客户端配置
	 *
	 * @since 3.0.0
	 */
	public void setX509(X509 x509) {
		this.x509 = x509;
	}

	/**
	 * 返回 Indirect Basic Auth 客户端配置
	 *
	 * @return Indirect Basic Auth 客户端配置
	 */
	public IndirectBasicAuth getIndirectBasicAuth() {
		return indirectBasicAuth;
	}

	/**
	 * 设置 Indirect Basic Auth 客户端配置
	 *
	 * @param indirectBasicAuth
	 * 		Indirect Basic Auth 客户端配置
	 */
	public void setIndirectBasicAuth(IndirectBasicAuth indirectBasicAuth) {
		this.indirectBasicAuth = indirectBasicAuth;
	}

	/**
	 * Form 客户端配置
	 */
	public final static class Form extends BaseFormConfig {

		/**
		 * 登录地址
		 */
		private String loginUrl;

		/**
		 * 构造函数
		 */
		public Form() {
			super("form");
			setUsernameParameter(Pac4jConstants.USERNAME);
			setPasswordParameter(Pac4jConstants.PASSWORD);
		}

		/**
		 * 返回登录地址
		 *
		 * @return 登录地址
		 */
		public String getLoginUrl() {
			return loginUrl;
		}

		/**
		 * 设置登录地址
		 *
		 * @param loginUrl
		 * 		登录地址
		 */
		public void setLoginUrl(String loginUrl) {
			this.loginUrl = loginUrl;
		}

	}

	/**
	 * Direct Form 客户端配置
	 *
	 * @since 3.0.0
	 */
	public final static class DirectForm extends BaseFormConfig {

		/**
		 * 构造函数
		 */
		public DirectForm() {
			super("direct-form");
			setUsernameParameter(Pac4jConstants.USERNAME);
			setPasswordParameter(Pac4jConstants.PASSWORD);
		}

	}

	/**
	 * Indirect Basic Auth 客户端配置
	 */
	public final static class IndirectBasicAuth extends BaseBasicAuthConfig {

		/**
		 * 构造函数
		 */
		public IndirectBasicAuth() {
			super("indirect-basic-auth");
		}

	}

	/**
	 * Direct Basic Auth 客户端配置
	 */
	public final static class DirectBasicAuth extends BaseBasicAuthConfig {

		/**
		 * 构造函数
		 */
		public DirectBasicAuth() {
			super("direct-basic-auth");
		}

	}

	/**
	 * Direct Bearer Auth 客户端配置
	 *
	 * @since 3.0.0
	 */
	public final static class DirectBearerAuth extends BaseBasicAuthConfig {

		/**
		 * 构造函数
		 */
		public DirectBearerAuth() {
			super("direct-bearer-auth");
		}

	}

	/**
	 * Cookie 客户端配置
	 *
	 * @since 3.0.0
	 */
	public final static class Cookie extends BaseClientConfig {

		/**
		 * Cookie 名称
		 */
		private String cookieName;

		/**
		 * 构造函数
		 */
		public Cookie() {
			super("cookie");
		}

		/**
		 * 返回 Cookie 名称
		 *
		 * @return Cookie 名称
		 */
		public String getCookieName() {
			return cookieName;
		}

		/**
		 * 设置 Cookie 名称
		 *
		 * @param cookieName
		 * 		Cookie 名称
		 */
		public void setCookieName(String cookieName) {
			this.cookieName = cookieName;
		}

	}

	/**
	 * Direct Digest Auth 客户端配置
	 *
	 * @since 3.0.0
	 */
	public final static class DirectDigestAuth extends BaseClientConfig {

		private String realm;

		/**
		 * 构造函数
		 */
		public DirectDigestAuth() {
			super("direct-digest-auth");
		}

		public String getRealm() {
			return realm;
		}

		public void setRealm(String realm) {
			this.realm = realm;
		}

	}

	/**
	 * Header 客户端配置
	 *
	 * @since 3.0.0
	 */
	public final static class Header extends BaseHeaderConfig {

		/**
		 * 构造函数
		 */
		public Header() {
			super("header");
		}

	}

	/**
	 * Parameter 客户端配置
	 *
	 * @since 3.0.0
	 */
	public final static class Parameter extends BaseParameterConfig {

		/**
		 * 构造函数
		 */
		public Parameter() {
			super("arameter");
		}

	}

	/**
	 * IP 客户端配置
	 *
	 * @since 3.0.0
	 */
	public final static class Ip extends BaseClientConfig {

		/**
		 * 构造函数
		 */
		public Ip() {
			super("ip");
		}

	}

	/**
	 * X509 客户端配置
	 *
	 * @since 3.0.0
	 */
	public final static class X509 extends BaseClientConfig {

		/**
		 * 构造函数
		 */
		public X509() {
			super("x509");
		}

	}

}
