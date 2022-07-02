/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * =================================================================================================
 *
 * This software consists of voluntary contributions made by many individuals on behalf of the
 * Apache Software Foundation. For more information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 * +------------------------------------------------------------------------------------------------+
 * | License: http://www.apache.org/licenses/LICENSE-2.0.txt 										|
 * | Author: Yong.Teng <webmaster@buession.com> 													|
 * | Copyright @ 2013-2022 Buession.com Inc.														|
 * +------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.web.security;

import com.buession.security.web.config.ContentSecurityPolicy;
import com.buession.security.web.config.Cors;
import com.buession.security.web.config.Csrf;
import com.buession.security.web.config.FormLogin;
import com.buession.security.web.config.FrameOptions;
import com.buession.security.web.config.Hpkp;
import com.buession.security.web.config.Hsts;
import com.buession.security.web.config.HttpBasic;
import com.buession.security.web.config.ReferrerPolicy;
import com.buession.security.web.config.Xss;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * Configuration properties for Spring Security.
 *
 * @author Yong.Teng
 * @since 1.2.0
 */
@ConfigurationProperties(prefix = WebSecurityProperties.PREFIX)
public class WebSecurityProperties {

	public final static String PREFIX = "spring.security";

	/**
	 * 是否禁用默认配置
	 */
	private boolean disableDefaults;

	/**
	 * Http Basic 配置
	 */
	@NestedConfigurationProperty
	private HttpBasic httpBasic = new HttpBasic();

	/**
	 * Csrf 配置
	 */
	@NestedConfigurationProperty
	private Csrf csrf = new Csrf();

	/**
	 * Frame Options 配置
	 */
	@NestedConfigurationProperty
	private FrameOptions frameOptions = new FrameOptions();

	/**
	 * Hsts 配置
	 */
	@NestedConfigurationProperty
	private Hsts hsts = new Hsts();

	/**
	 * Hpkp 配置
	 */
	@NestedConfigurationProperty
	private Hpkp hpkp = new Hpkp();

	/**
	 * Content Security Policy 配置
	 */
	@NestedConfigurationProperty
	private ContentSecurityPolicy contentSecurityPolicy = new ContentSecurityPolicy();

	/**
	 * Referrer Policy 配置
	 */
	@NestedConfigurationProperty
	private ReferrerPolicy referrerPolicy = new ReferrerPolicy();

	/**
	 * XSS 配置
	 */
	@NestedConfigurationProperty
	private Xss xss = new Xss();

	/**
	 * CORS 配置
	 *
	 * @since 2.0.0
	 */
	@NestedConfigurationProperty
	private Cors cors = new Cors();

	/**
	 * 登录表单配置
	 */
	@NestedConfigurationProperty
	private FormLogin formLogin = new FormLogin();

	/**
	 * 返回是否禁用默认配置
	 *
	 * @return 是否禁用默认配置
	 */
	public boolean isDisableDefaults(){
		return disableDefaults;
	}

	/**
	 * 设置是否禁用默认配置
	 *
	 * @param disableDefaults
	 * 		是否禁用默认配置
	 */
	public void setDisableDefaults(boolean disableDefaults){
		this.disableDefaults = disableDefaults;
	}

	/**
	 * 返回 Http Basic 配置
	 *
	 * @return Http Basic 配置
	 */
	public HttpBasic getHttpBasic(){
		return httpBasic;
	}

	/**
	 * 设置 Http Basic 配置
	 *
	 * @param httpBasic
	 * 		Http Basic 配置
	 */
	public void setHttpBasic(HttpBasic httpBasic){
		this.httpBasic = httpBasic;
	}

	/**
	 * 返回 Csrf 配置
	 *
	 * @return Csrf 配置
	 */
	public Csrf getCsrf(){
		return csrf;
	}

	/**
	 * 设置 Csrf 配置
	 *
	 * @param csrf
	 * 		Csrf 配置
	 */
	public void setCsrf(Csrf csrf){
		this.csrf = csrf;
	}

	/**
	 * 返回 Frame Options 配置
	 *
	 * @return Frame Options 配置
	 */
	public FrameOptions getFrameOptions(){
		return frameOptions;
	}

	/**
	 * 设置 Frame Options 配置
	 *
	 * @param frameOptions
	 * 		Frame Options 配置
	 */
	public void setFrameOptions(FrameOptions frameOptions){
		this.frameOptions = frameOptions;
	}

	/**
	 * 返回 Hsts 配置
	 *
	 * @return Hsts 配置
	 */
	public Hsts getHsts(){
		return hsts;
	}

	/**
	 * 设置 Hsts 配置
	 *
	 * @param hsts
	 * 		Hsts 配置
	 */
	public void setHsts(Hsts hsts){
		this.hsts = hsts;
	}

	/**
	 * 返回 Hpkp 配置
	 *
	 * @return Hpkp 配置
	 */
	public Hpkp getHpkp(){
		return hpkp;
	}

	/**
	 * 设置 Hpkp 配置
	 *
	 * @param hpkp
	 * 		Hpkp 配置
	 */
	public void setHpkp(Hpkp hpkp){
		this.hpkp = hpkp;
	}

	/**
	 * 返回 Content Security Policy 配置
	 *
	 * @return Content Security Policy 配置
	 */
	public ContentSecurityPolicy getContentSecurityPolicy(){
		return contentSecurityPolicy;
	}

	/**
	 * 设置 Content Security Policy 配置
	 *
	 * @param contentSecurityPolicy
	 * 		Content Security Policy 配置
	 */
	public void setContentSecurityPolicy(ContentSecurityPolicy contentSecurityPolicy){
		this.contentSecurityPolicy = contentSecurityPolicy;
	}

	/**
	 * 返回 Referrer Policy 配置
	 *
	 * @return Referrer Policy 配置
	 */
	public ReferrerPolicy getReferrerPolicy(){
		return referrerPolicy;
	}

	/**
	 * 设置 Referrer Policy 配置
	 *
	 * @param referrerPolicy
	 * 		Referrer Policy 配置
	 */
	public void setReferrerPolicy(ReferrerPolicy referrerPolicy){
		this.referrerPolicy = referrerPolicy;
	}

	/**
	 * 返回 XSS 配置
	 *
	 * @return XSS 配置
	 */
	public Xss getXss(){
		return xss;
	}

	/**
	 * 设置 XSS 配置
	 *
	 * @param xss
	 * 		XSS 配置
	 */
	public void setXss(Xss xss){
		this.xss = xss;
	}

	/**
	 * 返回 CORS 配置
	 *
	 * @return CORS 配置
	 */
	public Cors getCors(){
		return cors;
	}

	/**
	 * 设置 CORS 配置
	 *
	 * @param cors
	 * 		CORS 配置
	 */
	public void setCors(Cors cors){
		this.cors = cors;
	}

	/**
	 * 返回登录表单配置
	 *
	 * @return 登录表单配置
	 */
	public FormLogin getFormLogin(){
		return formLogin;
	}

	/**
	 * 设置登录表单配置
	 *
	 * @param formLogin
	 * 		登录表单配置
	 */
	public void setFormLogin(FormLogin formLogin){
		this.formLogin = formLogin;
	}

}
