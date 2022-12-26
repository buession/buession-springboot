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
import com.buession.security.shiro.RedisManager;
import com.buession.security.shiro.converter.SameSiteConverter;
import com.buession.security.shiro.session.RedisSessionDAO;
import com.buession.security.shiro.session.mgt.DefaultWebSessionManager;
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
import org.springframework.beans.factory.ObjectProvider;
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

		SystemPropertyUtils.setProperty("shiro.sessionManager.deleteInvalidSessions",
				session.isSessionManagerDeleteInvalidSessions());
		SystemPropertyUtils.setProperty("shiro.sessionManager.sessionIdCookieEnabled",
				session.isSessionIdCookieEnabled());
		SystemPropertyUtils.setProperty("shiro.sessionManager.sessionIdUrlRewritingEnabled",
				session.isSessionIdUrlRewritingEnabled());
		SystemPropertyUtils.setProperty("shiro.userNativeSessionManager", session.isUseNativeSessionManager());
		SystemPropertyUtils.setProperty("shiro.useNativeSessionManager", session.isUseNativeSessionManager());

		// Session Cookie info
		Cookie cookie = session.getCookie();
		if(Validate.hasText(cookie.getName())){
			SystemPropertyUtils.setProperty("shiro.sessionManager.cookie.name", cookie.getName());
		}

		if(cookie.getMaxAge() != null){
			SystemPropertyUtils.setProperty("shiro.sessionManager.cookie.maxAge", cookie.getMaxAge());
		}

		if(Validate.hasText(cookie.getDomain())){
			SystemPropertyUtils.setProperty("shiro.sessionManager.cookie.domain", cookie.getDomain());
		}

		if(Validate.hasText(cookie.getPath())){
			SystemPropertyUtils.setProperty("shiro.sessionManager.cookie.path", cookie.getPath());
		}

		if(cookie.getSecure() != null){
			SystemPropertyUtils.setProperty("shiro.sessionManager.cookie.secure", cookie.getSecure());
		}

		if(cookie.getSameSite() != null){
			org.apache.shiro.web.servlet.Cookie.SameSiteOptions sameSiteOptions =
					sameSiteConverter.convert(cookie.getSameSite());

			if(sameSiteOptions != null){
				SystemPropertyUtils.setProperty("shiro.sessionManager.cookie.sameSite",
						sameSiteOptions.name());
			}
		}

		// RememberMe Cookie info
		Cookie rememberMeCookie = properties.getRememberMe().getCookie();

		if(Validate.hasText(rememberMeCookie.getName())){
			SystemPropertyUtils.setProperty("shiro.rememberMeManager.cookie.name", rememberMeCookie.getName());
		}

		if(rememberMeCookie.getMaxAge() != null){
			SystemPropertyUtils.setProperty("shiro.rememberMeManager.cookie.maxAge", rememberMeCookie.getMaxAge());
		}

		if(Validate.hasText(rememberMeCookie.getDomain())){
			SystemPropertyUtils.setProperty("shiro.rememberMeManager.cookie.domain", rememberMeCookie.getDomain());
		}

		if(Validate.hasText(rememberMeCookie.getPath())){
			SystemPropertyUtils.setProperty("shiro.rememberMeManager.cookie.path", rememberMeCookie.getPath());
		}

		if(rememberMeCookie.getSecure() != null){
			SystemPropertyUtils.setProperty("shiro.rememberMeManager.cookie.secure", rememberMeCookie.getSecure());
		}

		if(rememberMeCookie.getSameSite() != null){
			org.apache.shiro.web.servlet.Cookie.SameSiteOptions sameSiteOptions =
					sameSiteConverter.convert(rememberMeCookie.getSameSite());

			if(sameSiteOptions != null){
				SystemPropertyUtils.setProperty("shiro.rememberMeManager.cookie.sameSite",
						sameSiteOptions.name());
			}
		}
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
	protected SessionDAO redisSessionDAO(RedisManager redisManager){
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
	protected ShiroFilterChainDefinition shiroFilterChainDefinition(ObjectProvider<SessionManager> sessionManager){
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

	@Override
	protected SessionManager nativeSessionManager(){
		DefaultWebSessionManager webSessionManager = new DefaultWebSessionManager();
		webSessionManager.setSessionIdCookieEnabled(sessionIdCookieEnabled);
		webSessionManager.setSessionIdUrlRewritingEnabled(sessionIdUrlRewritingEnabled);
		webSessionManager.setSessionIdCookie(sessionCookieTemplate());

		webSessionManager.setSessionFactory(sessionFactory());
		webSessionManager.setSessionDAO(sessionDAO());
		webSessionManager.setDeleteInvalidSessions(sessionManagerDeleteInvalidSessions);

		return webSessionManager;
	}

}