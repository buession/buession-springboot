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
 * | Copyright @ 2013-2020 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.shiro.autoconfigure;

import com.buession.core.validator.Validate;
import com.buession.security.core.SameSite;
import com.buession.security.shiro.Cookie;
import org.apache.shiro.config.Ini;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.boot.autoconfigure.ShiroAutoConfiguration;
import org.apache.shiro.spring.web.config.AbstractShiroWebConfiguration;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Yong.Teng
 */
@Configuration
@AutoConfigureBefore({ShiroAutoConfiguration.class})
@EnableConfigurationProperties(ShiroProperties.class)
@ConditionalOnProperty(name = "shiro.web.enabled", matchIfMissing = true)
@ConditionalOnWebApplication
public class ShiroWebConfiguration extends AbstractShiroWebConfiguration {

	@Autowired
	protected ShiroProperties shiroProperties;

	@PostConstruct
	public void initialize(){
		ShiroProperties.Session session = shiroProperties.getSession();

		useNativeSessionManager = session.isUserNativeSessionManager();
		sessionIdCookieEnabled = session.isSessionIdCookieEnabled();
		sessionIdUrlRewritingEnabled = session.isSessionIdUrlRewritingEnabled();
		sessionManagerDeleteInvalidSessions = session.isSessionManagerDeleteInvalidSessions();

		// Session Cookie info
		Cookie cookie = session.getCookie();
		sessionIdCookieName = cookie.getName();
		sessionIdCookieDomain = cookie.getDomain();
		sessionIdCookiePath = cookie.getPath();
		sessionIdCookieMaxAge = cookie.getMaxAge();
		sessionIdCookieSecure = cookie.isSecure();

		// RememberMe Cookie info
		Cookie rememberMeCookie = shiroProperties.getRememberMe().getCookie();
		rememberMeCookieName = rememberMeCookie.getName();
		rememberMeCookieDomain = rememberMeCookie.getDomain();
		rememberMeCookiePath = rememberMeCookie.getPath();
		rememberMeCookieMaxAge = rememberMeCookie.getMaxAge();
		rememberMeCookieSecure = rememberMeCookie.isSecure();
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
	@ConditionalOnMissingBean
	@Override
	protected SessionManager sessionManager(){
		return super.sessionManager();
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
	protected SessionStorageEvaluator sessionStorageEvaluator(){
		return super.sessionStorageEvaluator();
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
		org.apache.shiro.web.servlet.Cookie cookie = super.sessionCookieTemplate();

		buildShiroCookie(shiroProperties.getSession().getCookie(), cookie);

		return cookie;
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
		org.apache.shiro.web.servlet.Cookie cookie = super.rememberMeCookieTemplate();

		buildShiroCookie(shiroProperties.getRememberMe().getCookie(), cookie);

		return cookie;
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

		section.forEach((antPath, definition)->{
			shiroFilterChainDefinition.addPathDefinition(antPath, definition);
		});

		return shiroFilterChainDefinition;
	}

	protected final static void buildShiroCookie(final Cookie cookie, final org.apache.shiro.web.servlet.Cookie
			shiroCookie){
		shiroCookie.setHttpOnly(cookie.isHttpOnly());
		if(cookie.getSameSite() == SameSite.NONE){
			shiroCookie.setSameSite(org.apache.shiro.web.servlet.Cookie.SameSiteOptions.NONE);
		}else if(cookie.getSameSite() == SameSite.LAX){
			shiroCookie.setSameSite(org.apache.shiro.web.servlet.Cookie.SameSiteOptions.LAX);
		}else if(cookie.getSameSite() == SameSite.STRICT){
			shiroCookie.setSameSite(org.apache.shiro.web.servlet.Cookie.SameSiteOptions.STRICT);
		}
	}

}
