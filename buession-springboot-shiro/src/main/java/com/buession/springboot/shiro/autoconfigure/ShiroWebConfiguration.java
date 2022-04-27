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
package com.buession.springboot.shiro.autoconfigure;

import com.buession.core.utils.SystemPropertyUtils;
import com.buession.core.validator.Validate;
import com.buession.security.shiro.Cookie;
import org.apache.shiro.config.Ini;
import org.apache.shiro.spring.boot.autoconfigure.ShiroAutoConfiguration;
import org.apache.shiro.spring.web.config.AbstractShiroWebConfiguration;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yong.Teng
 */
@Configuration
@AutoConfigureBefore({ShiroAutoConfiguration.class})
@EnableConfigurationProperties(ShiroProperties.class)
@ConditionalOnProperty(name = "shiro.web.enabled", matchIfMissing = true)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ShiroWebConfiguration extends AbstractShiroWebConfiguration {

	protected ShiroProperties properties;

	public ShiroWebConfiguration(ShiroProperties properties){
		this.properties = properties;
		ShiroProperties.Session session = properties.getSession();

		// Session Cookie info
		Cookie cookie = session.getCookie();

		// RememberMe Cookie info
		Cookie rememberMeCookie = properties.getRememberMe().getCookie();

		SystemPropertyUtils.setProperty("shiro.userNativeSessionManager", session.isUseNativeSessionManager());

		SystemPropertyUtils.setProperty("shiro.sessionManager.sessionIdCookieEnabled",
				session.isSessionIdCookieEnabled());
		SystemPropertyUtils.setProperty("shiro.sessionManager.sessionIdUrlRewritingEnabled",
				session.isSessionIdUrlRewritingEnabled());
		SystemPropertyUtils.setProperty("shiro.sessionManager.deleteInvalidSessions",
				session.isSessionManagerDeleteInvalidSessions());
		SystemPropertyUtils.setPropertyIfPresent("shiro.sessionManager.cookie.name", cookie.getName());
		SystemPropertyUtils.setPropertyIfPresent("shiro.sessionManager.cookie.domain", cookie.getDomain());
		SystemPropertyUtils.setPropertyIfPresent("shiro.sessionManager.cookie.path", cookie.getPath());
		SystemPropertyUtils.setProperty("shiro.sessionManager.cookie.maxAge", cookie.getMaxAge());
		SystemPropertyUtils.setProperty("shiro.sessionManager.cookie.secure", cookie.isSecure());
		if(cookie.getSameSite() != null){
			SystemPropertyUtils.setPropertyIfPresent("shiro.sessionManager.cookie.sameSite",
					cookie.getSameSite().name());
		}

		SystemPropertyUtils.setPropertyIfPresent("shiro.rememberMeManager.cookie.name", rememberMeCookie.getName());
		SystemPropertyUtils.setPropertyIfPresent("shiro.rememberMeManager.cookie.domain", rememberMeCookie.getDomain());
		SystemPropertyUtils.setPropertyIfPresent("shiro.rememberMeManager.cookie.path", rememberMeCookie.getPath());
		SystemPropertyUtils.setProperty("shiro.rememberMeManager.cookie.maxAge", rememberMeCookie.getMaxAge());
		SystemPropertyUtils.setProperty("shiro.rememberMeManager.cookie.secure", rememberMeCookie.isSecure());
		if(rememberMeCookie.getSameSite() != null){
			SystemPropertyUtils.setPropertyIfPresent("shiro.rememberMeManager.cookie.sameSite",
					rememberMeCookie.getSameSite().name());
		}
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnResource(resources = "classpath:chainDefinition.ini")
	@Override
	protected ShiroFilterChainDefinition shiroFilterChainDefinition(){
		Ini ini = Ini.fromResourcePath("classpath:chainDefinition.ini");

		if(Validate.isEmpty(ini)){
			return super.shiroFilterChainDefinition();
		}

		Ini.Section section = ini.get("filterChainDefinitions");
		if(Validate.isEmpty(section)){
			return super.shiroFilterChainDefinition();
		}

		DefaultShiroFilterChainDefinition shiroFilterChainDefinition = new DefaultShiroFilterChainDefinition();

		shiroFilterChainDefinition.addPathDefinitions(section);

		return shiroFilterChainDefinition;
	}

}