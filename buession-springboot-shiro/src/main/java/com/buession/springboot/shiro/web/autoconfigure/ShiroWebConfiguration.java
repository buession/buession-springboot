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
 * | Copyright @ 2013-2026 Buession.com Inc.														|
 * +------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.shiro.web.autoconfigure;

import com.buession.core.utils.SystemPropertyUtils;
import com.buession.core.validator.Validate;
import com.buession.security.shiro.Cookie;
import com.buession.security.shiro.RedisManager;
import com.buession.security.shiro.converter.SameSiteConverter;
import com.buession.security.shiro.session.RedisSessionDAO;
import com.buession.springboot.shiro.autoconfigure.ShiroConfiguration;
import com.buession.springboot.shiro.autoconfigure.ShiroProperties;
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
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;

/**
 * Shiro Web 自动配置
 *
 * @author Yong.Teng
 * @since 4.0.0
 */
@AutoConfiguration(before = {ShiroConfiguration.class}, after = {ShiroWebMvcConfiguration.class})
@EnableConfigurationProperties(ShiroProperties.class)
@ConditionalOnProperty(prefix = ShiroProperties.PREFIX, name = "web.enabled", matchIfMissing = true)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ShiroWebConfiguration extends AbstractShiroWebConfiguration {

	private final ShiroProperties properties;

	/**
	 * @since 3.0.1
	 */
	private final RedisManager redisManager;

	public ShiroWebConfiguration(ShiroProperties properties, ObjectProvider<RedisManager> redisManager) {
		this.properties = properties;

		SameSiteConverter sameSiteConverter = new SameSiteConverter();

		// Session info
		ShiroProperties.Session session = properties.getSession();

		SystemPropertyUtils.setPropertyIfPresent("shiro.sessionManager.deleteInvalidSessions",
				session.isSessionManagerDeleteInvalidSessions());
		SystemPropertyUtils.setPropertyIfPresent("shiro.sessionManager.sessionIdCookieEnabled",
				session.isSessionIdCookieEnabled());
		SystemPropertyUtils.setPropertyIfPresent("shiro.sessionManager.sessionIdUrlRewritingEnabled",
				session.isSessionIdUrlRewritingEnabled());
		SystemPropertyUtils.setPropertyIfPresent("shiro.userNativeSessionManager", session.isUseNativeSessionManager());
		SystemPropertyUtils.setPropertyIfPresent("shiro.useNativeSessionManager", session.isUseNativeSessionManager());

		// Session Cookie info
		Cookie cookie = session.getCookie();

		SystemPropertyUtils.setPropertyIfPresent("shiro.sessionManager.cookie.name", cookie.getName());
		SystemPropertyUtils.setPropertyIfPresent("shiro.sessionManager.cookie.maxAge", cookie.getMaxAge());
		SystemPropertyUtils.setPropertyIfPresent("shiro.sessionManager.cookie.domain", cookie.getDomain());
		SystemPropertyUtils.setPropertyIfPresent("shiro.sessionManager.cookie.path", cookie.getPath());
		SystemPropertyUtils.setPropertyIfPresent("shiro.sessionManager.cookie.secure", cookie.getSecure());

		if(cookie.getSameSite() != null){
			org.apache.shiro.web.servlet.Cookie.SameSiteOptions sameSiteOptions =
					sameSiteConverter.convert(cookie.getSameSite());

			SystemPropertyUtils.setPropertyIfPresent("shiro.sessionManager.cookie.sameSite", sameSiteOptions.name());
		}

		// RememberMe Cookie info
		Cookie rememberMeCookie = properties.getRememberMe().getCookie();

		SystemPropertyUtils.setPropertyIfPresent("shiro.rememberMeManager.cookie.name", rememberMeCookie.getName());
		SystemPropertyUtils.setPropertyIfPresent("shiro.rememberMeManager.cookie.maxAge", rememberMeCookie.getMaxAge());
		SystemPropertyUtils.setPropertyIfPresent("shiro.rememberMeManager.cookie.domain", rememberMeCookie.getDomain());
		SystemPropertyUtils.setPropertyIfPresent("shiro.rememberMeManager.cookie.path", rememberMeCookie.getPath());
		SystemPropertyUtils.setPropertyIfPresent("shiro.rememberMeManager.cookie.secure", rememberMeCookie.getSecure());

		if(rememberMeCookie.getSameSite() != null){
			org.apache.shiro.web.servlet.Cookie.SameSiteOptions sameSiteOptions =
					sameSiteConverter.convert(rememberMeCookie.getSameSite());

			SystemPropertyUtils.setPropertyIfPresent("shiro.rememberMeManager.cookie.sameSite", sameSiteOptions.name());
		}

		this.redisManager = redisManager.getIfAvailable();
	}

	@Bean
	@ConditionalOnMissingBean
	@Override
	protected AuthenticationStrategy authenticationStrategy() {
		return super.authenticationStrategy();
	}

	@Bean
	@ConditionalOnMissingBean
	@Override
	protected Authenticator authenticator() {
		return super.authenticator();
	}

	@Bean
	@ConditionalOnMissingBean
	@Override
	protected Authorizer authorizer() {
		return super.authorizer();
	}

	@Bean
	@ConditionalOnMissingBean
	@Override
	protected SubjectDAO subjectDAO() {
		return super.subjectDAO();
	}

	@Bean
	@ConditionalOnMissingBean
	@Override
	protected SubjectFactory subjectFactory() {
		return super.subjectFactory();
	}

	@Bean
	@ConditionalOnMissingBean
	@Override
	protected SessionStorageEvaluator sessionStorageEvaluator() {
		return super.sessionStorageEvaluator();
	}

	@Bean
	@ConditionalOnMissingBean
	@Override
	protected SessionFactory sessionFactory() {
		return super.sessionFactory();
	}

	@Bean(name = "sessionDAO")
	@ConditionalOnMissingBean({SessionDAO.class})
	@Override
	protected SessionDAO sessionDAO() {
		if(redisManager == null){
			return super.sessionDAO();
		}else{
			ShiroProperties.Session session = properties.getSession();
			return new RedisSessionDAO(redisManager, session.getPrefix(), session.getExpire(),
					session.isSessionInMemoryEnabled(), session.getSessionInMemoryTimeout());
		}
	}

	@Bean
	@ConditionalOnMissingBean
	@Override
	protected SessionManager sessionManager() {
		return super.sessionManager();
	}

	@Bean
	@ConditionalOnMissingBean
	@Override
	protected SessionsSecurityManager securityManager(List<Realm> realms) {
		return super.securityManager(realms);
	}

	@Bean(name = "sessionCookieTemplate")
	@ConditionalOnMissingBean(name = "sessionCookieTemplate")
	@Override
	protected org.apache.shiro.web.servlet.Cookie sessionCookieTemplate() {
		org.apache.shiro.web.servlet.Cookie cookie = super.sessionCookieTemplate();

		Optional.ofNullable(properties.getSession().getCookie().getHttpOnly()).ifPresent(cookie::setHttpOnly);

		return cookie;
	}

	@Bean
	@ConditionalOnMissingBean
	@Override
	protected RememberMeManager rememberMeManager() {
		return super.rememberMeManager();
	}

	@Bean(name = "rememberMeCookieTemplate")
	@ConditionalOnMissingBean(name = "rememberMeCookieTemplate")
	@Override
	protected org.apache.shiro.web.servlet.Cookie rememberMeCookieTemplate() {
		org.apache.shiro.web.servlet.Cookie cookie = super.rememberMeCookieTemplate();

		Optional.ofNullable(properties.getRememberMe().getCookie().getHttpOnly()).ifPresent(cookie::setHttpOnly);

		return cookie;
	}

	@Bean
	@ConditionalOnMissingBean({ShiroFilterChainDefinition.class})
	@ConditionalOnResource(resources = "classpath:chainDefinition.ini")
	protected ShiroFilterChainDefinition iniShiroFilterChainDefinition() {
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
	@ConditionalOnMissingBean({ShiroFilterChainDefinition.class})
	@Override
	protected ShiroFilterChainDefinition shiroFilterChainDefinition() {
		return super.shiroFilterChainDefinition();
	}

	@Bean
	@ConditionalOnMissingBean
	@Override
	protected ShiroUrlPathHelper shiroUrlPathHelper() {
		return super.shiroUrlPathHelper();
	}

}