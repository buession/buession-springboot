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

import com.buession.core.validator.Validate;
import com.buession.security.shiro.Cookie;
import com.buession.security.shiro.RedisManager;
import com.buession.security.shiro.converter.SameSiteConverter;
import com.buession.security.shiro.session.RedisSessionDAO;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.config.Ini;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.mgt.SubjectDAO;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroUrlPathHelper;
import org.apache.shiro.spring.web.config.AbstractShiroWebConfiguration;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Yong.Teng
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ShiroProperties.class)
@ConditionalOnProperty(prefix = ShiroProperties.PREFIX, name = "web.enabled", matchIfMissing = true)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@AutoConfigureBefore({ShiroConfiguration.class})
@AutoConfigureAfter({ShiroWebMvcConfiguration.class})
public class ShiroWebConfiguration extends AbstractShiroWebConfiguration {

	protected ShiroProperties properties;

	public ShiroWebConfiguration(ShiroProperties properties){
		this.properties = properties;

		SameSiteConverter sameSiteConverter = new SameSiteConverter();

		// Session info
		ShiroProperties.Session session = properties.getSession();

		this.sessionManagerDeleteInvalidSessions = session.isSessionManagerDeleteInvalidSessions();
		this.sessionIdCookieEnabled = session.isSessionIdCookieEnabled();
		this.sessionIdUrlRewritingEnabled = session.isSessionIdUrlRewritingEnabled();
		this.useNativeSessionManager = session.isUseNativeSessionManager();

		// Session Cookie info
		Cookie cookie = session.getCookie();

		if(Validate.hasText(cookie.getName())){
			this.sessionIdCookieName = cookie.getName();
		}

		if(cookie.getMaxAge() != null){
			this.sessionIdCookieMaxAge = cookie.getMaxAge();
		}

		if(Validate.hasText(cookie.getDomain())){
			this.sessionIdCookieDomain = cookie.getDomain();
		}

		if(Validate.hasText(cookie.getPath())){
			this.sessionIdCookiePath = cookie.getPath();
		}

		if(cookie.getSecure() != null){
			this.sessionIdCookieSecure = cookie.getSecure();
		}

		if(cookie.getSameSite() != null){
			this.sessionIdCookieSameSite = sameSiteConverter.convert(cookie.getSameSite());
		}

		// RememberMe Cookie info
		Cookie rememberMeCookie = properties.getRememberMe().getCookie();

		if(Validate.hasText(rememberMeCookie.getName())){
			this.rememberMeCookieName = rememberMeCookie.getName();
		}

		if(rememberMeCookie.getMaxAge() != null){
			this.rememberMeCookieMaxAge = rememberMeCookie.getMaxAge();
		}

		if(Validate.hasText(rememberMeCookie.getDomain())){
			this.rememberMeCookieDomain = rememberMeCookie.getDomain();
		}

		if(Validate.hasText(rememberMeCookie.getPath())){
			this.rememberMeCookiePath = rememberMeCookie.getPath();
		}

		if(rememberMeCookie.getSecure() != null){
			this.rememberMeCookieSecure = rememberMeCookie.getSecure();
		}

		if(rememberMeCookie.getSameSite() != null){
			this.rememberMeSameSite = sameSiteConverter.convert(rememberMeCookie.getSameSite());
		}

		/*
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

		 */

		/*
		SystemPropertyUtils.setPropertyIfPresent("shiro.loginUrl", properties.getLoginUrl());
		SystemPropertyUtils.setPropertyIfPresent("shiro.successUrl", properties.getSuccessUrl());
		SystemPropertyUtils.setPropertyIfPresent("shiro.unauthorizedUrl", properties.getUnauthorizedUrl());

		 */
	}

	@Bean
	@ConditionalOnMissingBean
	@Override
	protected AuthenticationStrategy authenticationStrategy(){
		return super.authenticationStrategy();
	}

	@Bean
	@ConditionalOnMissingBean
	@Override
	protected Authenticator authenticator(){
		return super.authenticator();
	}

	@Bean
	@ConditionalOnMissingBean
	@Override
	protected Authorizer authorizer(){
		return super.authorizer();
	}

	@Bean
	@ConditionalOnMissingBean
	@Override
	protected SubjectDAO subjectDAO(){
		return super.subjectDAO();
	}

	@Bean
	@ConditionalOnMissingBean
	@Override
	protected SessionStorageEvaluator sessionStorageEvaluator(){
		return super.sessionStorageEvaluator();
	}

	@Bean
	@ConditionalOnMissingBean
	@Override
	protected SubjectFactory subjectFactory(){
		return super.subjectFactory();
	}

	@Bean
	@ConditionalOnMissingBean
	@Override
	protected SessionFactory sessionFactory(){
		return super.sessionFactory();
	}

	@Bean
	@ConditionalOnBean({RedisManager.class})
	@ConditionalOnMissingBean
	protected SessionDAO sessionDAO(RedisManager redisManager){
		ShiroProperties.Session session = properties.getSession();
		return new RedisSessionDAO(redisManager, session.getPrefix(), session.getExpire(),
				session.isSessionInMemoryEnabled(), session.getSessionInMemoryTimeout());
	}

	@Bean
	@ConditionalOnMissingBean
	@Override
	protected SessionDAO sessionDAO(){
		return super.sessionDAO();
	}

	@Bean
	@ConditionalOnMissingBean
	@Override
	protected SessionManager sessionManager(){
		return super.sessionManager();
	}

	@Bean
	@ConditionalOnMissingBean
	@Override
	protected SessionsSecurityManager securityManager(List<Realm> realms){
		return super.securityManager(realms);
	}

	@Bean
	@ConditionalOnMissingBean(name = "sessionCookieTemplate")
	@Override
	protected org.apache.shiro.web.servlet.Cookie sessionCookieTemplate(){
		return super.sessionCookieTemplate();
	}

	@Bean
	@ConditionalOnMissingBean
	@Override
	protected RememberMeManager rememberMeManager(){
		return super.rememberMeManager();
	}

	@Bean
	@ConditionalOnMissingBean(name = "rememberMeCookieTemplate")
	@Override
	protected org.apache.shiro.web.servlet.Cookie rememberMeCookieTemplate(){
		return super.rememberMeCookieTemplate();
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnResource(resources = "classpath:chainDefinition.ini")
	protected ShiroFilterChainDefinition shiroFilterChainDefinition(SessionManager sessionManager){
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

	@Bean
	@ConditionalOnMissingBean
	@Override
	protected ShiroFilterChainDefinition shiroFilterChainDefinition(){
		return super.shiroFilterChainDefinition();
	}

	@Bean
	@ConditionalOnMissingBean
	@Override
	protected ShiroUrlPathHelper shiroUrlPathHelper(){
		return super.shiroUrlPathHelper();
	}

}