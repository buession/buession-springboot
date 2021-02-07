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
 * | Copyright @ 2013-2020 Buession.com Inc.														|
 * +------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.web.security.autoconfigure;

import com.buession.springboot.web.security.WebSecurityConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * Configuration properties for Spring Security.
 *
 * @author Yong.Teng
 * @since 1.2.0
 */
@ConfigurationProperties(prefix = "spring.security")
public class WebSecurityProperties {

	/**
	 * Http Basic 配置
	 */
	@NestedConfigurationProperty
	private WebSecurityConfiguration.HttpBasic httpBasic = new WebSecurityConfiguration.HttpBasic();

	/**
	 * Csrf 配置
	 */
	@NestedConfigurationProperty
	private WebSecurityConfiguration.Csrf csrf = new WebSecurityConfiguration.Csrf();

	/**
	 * Frame Options 配置
	 */
	@NestedConfigurationProperty
	private WebSecurityConfiguration.FrameOptions frameOptions = new WebSecurityConfiguration.FrameOptions();

	/**
	 * Hsts 配置
	 */
	@NestedConfigurationProperty
	private WebSecurityConfiguration.Hsts hsts = new WebSecurityConfiguration.Hsts();

	/**
	 * Hpkp 配置
	 */
	@NestedConfigurationProperty
	private WebSecurityConfiguration.Hpkp hpkp = new WebSecurityConfiguration.Hpkp();

	/**
	 * Content Security Policy 配置
	 */
	@NestedConfigurationProperty
	private WebSecurityConfiguration.ContentSecurityPolicy contentSecurityPolicy =
			new WebSecurityConfiguration.ContentSecurityPolicy();

	/**
	 * Referrer Policy 配置
	 */
	@NestedConfigurationProperty
	private WebSecurityConfiguration.ReferrerPolicy referrerPolicy = new WebSecurityConfiguration.ReferrerPolicy();

	/**
	 * XSS 配置
	 */
	@NestedConfigurationProperty
	private WebSecurityConfiguration.Xss xss = new WebSecurityConfiguration.Xss();

	/**
	 * 返回 Http Basic 配置
	 *
	 * @return Http Basic 配置
	 */
	public WebSecurityConfiguration.HttpBasic getHttpBasic(){
		return httpBasic;
	}

	/**
	 * 设置 Http Basic 配置
	 *
	 * @param httpBasic
	 * 		Http Basic 配置
	 */
	public void setHttpBasic(WebSecurityConfiguration.HttpBasic httpBasic){
		this.httpBasic = httpBasic;
	}

	/**
	 * 返回 Csrf 配置
	 *
	 * @return Csrf 配置
	 */
	public WebSecurityConfiguration.Csrf getCsrf(){
		return csrf;
	}

	/**
	 * 设置 Csrf 配置
	 *
	 * @param csrf
	 * 		Csrf 配置
	 */
	public void setCsrf(WebSecurityConfiguration.Csrf csrf){
		this.csrf = csrf;
	}

	/**
	 * 返回 Frame Options 配置
	 *
	 * @return Frame Options 配置
	 */
	public WebSecurityConfiguration.FrameOptions getFrameOptions(){
		return frameOptions;
	}

	/**
	 * 设置 Frame Options 配置
	 *
	 * @param frameOptions
	 * 		Frame Options 配置
	 */
	public void setFrameOptions(WebSecurityConfiguration.FrameOptions frameOptions){
		this.frameOptions = frameOptions;
	}

	/**
	 * 返回 Hsts 配置
	 *
	 * @return Hsts 配置
	 */
	public WebSecurityConfiguration.Hsts getHsts(){
		return hsts;
	}

	/**
	 * 设置 Hsts 配置
	 *
	 * @param hsts
	 * 		Hsts 配置
	 */
	public void setHsts(WebSecurityConfiguration.Hsts hsts){
		this.hsts = hsts;
	}

	/**
	 * 返回 Hpkp 配置
	 *
	 * @return Hpkp 配置
	 */
	public WebSecurityConfiguration.Hpkp getHpkp(){
		return hpkp;
	}

	/**
	 * 设置 Hpkp 配置
	 *
	 * @param hpkp
	 * 		Hpkp 配置
	 */
	public void setHpkp(WebSecurityConfiguration.Hpkp hpkp){
		this.hpkp = hpkp;
	}

	/**
	 * 返回 Content Security Policy 配置
	 *
	 * @return Content Security Policy 配置
	 */
	public WebSecurityConfiguration.ContentSecurityPolicy getContentSecurityPolicy(){
		return contentSecurityPolicy;
	}

	/**
	 * 设置 Content Security Policy 配置
	 *
	 * @param contentSecurityPolicy
	 * 		Content Security Policy 配置
	 */
	public void setContentSecurityPolicy(WebSecurityConfiguration.ContentSecurityPolicy contentSecurityPolicy){
		this.contentSecurityPolicy = contentSecurityPolicy;
	}

	/**
	 * 返回 Referrer Policy 配置
	 *
	 * @return Referrer Policy 配置
	 */
	public WebSecurityConfiguration.ReferrerPolicy getReferrerPolicy(){
		return referrerPolicy;
	}

	/**
	 * 设置 Referrer Policy 配置
	 *
	 * @param referrerPolicy
	 * 		Referrer Policy 配置
	 */
	public void setReferrerPolicy(WebSecurityConfiguration.ReferrerPolicy referrerPolicy){
		this.referrerPolicy = referrerPolicy;
	}

	/**
	 * 返回 XSS 配置
	 *
	 * @return XSS 配置
	 */
	public WebSecurityConfiguration.Xss getXss(){
		return xss;
	}

	/**
	 * 设置 XSS 配置
	 *
	 * @param xss
	 * 		XSS 配置
	 */
	public void setXss(WebSecurityConfiguration.Xss xss){
		this.xss = xss;
	}

}
