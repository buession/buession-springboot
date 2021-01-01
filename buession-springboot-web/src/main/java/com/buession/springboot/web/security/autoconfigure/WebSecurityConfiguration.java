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

import com.buession.security.spring.web.csrf.CookieCsrfTokenRepositoryGenerator;
import com.buession.security.spring.web.csrf.CsrfTokenRepositoryGenerator;
import com.buession.security.spring.web.csrf.HttpSessionCsrfTokenRepositoryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.csrf.LazyCsrfTokenRepository;

/**
 * Spring Security Configuration
 *
 * @author Yong.Teng
 * @since 1.2.0
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
@ConditionalOnClass({HttpSecurity.class})
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception{
		httpBasicConfigure(httpSecurity, securityProperties.getHttpBasic());
		csrfConfigure(httpSecurity, securityProperties.getCsrf());
		frameOptionsConfigure(httpSecurity, securityProperties.getFrameOptions());
		hstsConfigure(httpSecurity, securityProperties.getHsts());
		hpkpConfigure(httpSecurity, securityProperties.getHpkp());
		contentSecurityPolicyConfigure(httpSecurity, securityProperties.getContentSecurityPolicy());
		referrerPolicyConfigure(httpSecurity, securityProperties.getReferrerPolicy());
		xssConfigure(httpSecurity, securityProperties.getXss());
	}

	protected void httpBasicConfigure(final HttpSecurity httpSecurity, final SecurityProperties.HttpBasic config) throws Exception{
		if(config.isEnable() == false){
			httpSecurity.httpBasic().disable();
		}
	}

	protected void csrfConfigure(final HttpSecurity httpSecurity, final SecurityProperties.Csrf config) throws Exception{
		CsrfConfigurer<HttpSecurity> csrfConfigurer = httpSecurity.csrf();

		if(config.isEnable()){
			CsrfTokenRepositoryGenerator csrfTokenRepositoryGenerator = null;

			if(config.getTokenRepositoryGenerator().isAssignableFrom(CookieCsrfTokenRepositoryGenerator.class)){
				SecurityProperties.Csrf.Cookie cookie = config.getCookie();
				csrfTokenRepositoryGenerator = new CookieCsrfTokenRepositoryGenerator(cookie.getParameterName(),
						cookie.getHeaderName(), cookie.getCookieName(), cookie.getCookieDomain(),
						cookie.getCookiePath(), cookie.getCookieHttpOnly());
			}else if(config.getTokenRepositoryGenerator().isAssignableFrom(HttpSessionCsrfTokenRepositoryGenerator.class)){
				SecurityProperties.Csrf.Session session = config.getSession();
				csrfTokenRepositoryGenerator = new HttpSessionCsrfTokenRepositoryGenerator(session.getParameterName(),
						session.getHeaderName(), session.getSessionAttributeName());
			}

			if(csrfTokenRepositoryGenerator != null){
				csrfConfigurer.csrfTokenRepository(new LazyCsrfTokenRepository(csrfTokenRepositoryGenerator.generate()));
			}
		}else{
			csrfConfigurer.disable();
		}
	}

	protected void frameOptionsConfigure(final HttpSecurity httpSecurity,
			final SecurityProperties.FrameOptions config) throws Exception{
		HeadersConfigurer.FrameOptionsConfig frameOptionsConfig = httpSecurity.headers().frameOptions();

		if(config.isEnable()){
			switch(config.getMode()){
				case ALLOW_FROM:
					break;
				case SAMEORIGIN:
					frameOptionsConfig.sameOrigin();
					break;
				case DENY:
					frameOptionsConfig.deny();
					break;
				default:
					break;
			}
		}else{
			frameOptionsConfig.disable();
		}
	}

	protected void hstsConfigure(final HttpSecurity httpSecurity, final SecurityProperties.Hsts config) throws Exception{
		HeadersConfigurer.HstsConfig hstsConfig = httpSecurity.headers().httpStrictTransportSecurity();

		if(config.isEnable()){
			if(config.getMatcher() == null){
				hstsConfig.maxAgeInSeconds(config.getMaxAge()).includeSubDomains(config.getIncludeSubDomains()).preload(config.isPreload());
			}else{
				hstsConfig.requestMatcher(config.getMatcher().newInstance()).maxAgeInSeconds(config.getMaxAge()).includeSubDomains(config.getIncludeSubDomains()).preload(config.isPreload());
			}
		}else{
			hstsConfig.disable();
		}
	}

	protected void hpkpConfigure(final HttpSecurity httpSecurity, final SecurityProperties.Hpkp config) throws Exception{
		HeadersConfigurer.HpkpConfig hpkpConfig = httpSecurity.headers().httpPublicKeyPinning();

		if(config.isEnable()){
			if(config.getPins() == null){
				hpkpConfig.withPins(config.getPins()).addSha256Pins(config.getSha256Pins()).maxAgeInSeconds(config.getMaxAge()).includeSubDomains(config.getIncludeSubDomains()).reportOnly(config.isReportOnly()).reportUri(config.getReportUri());
			}else{
				hpkpConfig.addSha256Pins(config.getSha256Pins()).maxAgeInSeconds(config.getMaxAge()).includeSubDomains(config.getIncludeSubDomains()).reportOnly(config.isReportOnly()).reportUri(config.getReportUri());
			}
		}else{
			hpkpConfig.disable();
		}
	}

	protected void contentSecurityPolicyConfigure(final HttpSecurity httpSecurity,
			final SecurityProperties.ContentSecurityPolicy config) throws Exception{
		HeadersConfigurer.ContentSecurityPolicyConfig contentSecurityPolicyConfig =
				httpSecurity.headers().contentSecurityPolicy(config.getPolicyDirectives());

		if(config.isEnable()){
			if(config.isReportOnly()){
				contentSecurityPolicyConfig.reportOnly();
			}
		}
	}

	protected void referrerPolicyConfigure(final HttpSecurity httpSecurity,
			final SecurityProperties.ReferrerPolicy config) throws Exception{
		if(config.isEnable()){
			httpSecurity.headers().referrerPolicy(config.getReferrerPolicy());
		}
	}

	protected void xssConfigure(final HttpSecurity httpSecurity, final SecurityProperties.Xss config) throws Exception{
		HeadersConfigurer.XXssConfig xXssConfig = httpSecurity.headers().xssProtection();

		if(config.isEnable()){
			xXssConfig.block(config.getBlock()).xssProtectionEnabled(config.isEnabledProtection());
		}else{
			xXssConfig.disable();
		}
	}

}
