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
 * | Copyright @ 2013-2024 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.shiro.autoconfigure;

import com.buession.core.utils.SystemPropertyUtils;
import com.buession.security.shiro.RedisManager;
import com.buession.security.shiro.exception.NoRealmBeanConfiguredException;
import com.buession.security.shiro.session.RedisSessionDAO;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.mgt.SubjectDAO;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.config.AbstractShiroConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * Shiro Auto Configuration
 *
 * @author Yong.Teng
 * @since 2.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(ShiroProperties.class)
@ConditionalOnProperty(prefix = ShiroProperties.PREFIX, name = "enabled", matchIfMissing = true)
public class ShiroConfiguration extends AbstractShiroConfiguration {

	private final ShiroProperties properties;

	public ShiroConfiguration(ShiroProperties properties) {
		this.properties = properties;

		// Session info
		ShiroProperties.Session session = properties.getSession();

		SystemPropertyUtils.setProperty("shiro.sessionManager.deleteInvalidSessions",
				session.isSessionManagerDeleteInvalidSessions());
	}

	@Bean
	@ConditionalOnMissingBean
	@Override
	protected SessionsSecurityManager securityManager(List<Realm> realms) {
		return super.securityManager(realms);
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
	protected SubjectDAO subjectDAO() {
		return super.subjectDAO();
	}

	@Bean
	@ConditionalOnMissingBean
	@Override
	protected SessionStorageEvaluator sessionStorageEvaluator() {
		return super.sessionStorageEvaluator();
	}

	@Bean
	@ConditionalOnMissingBean({SubjectFactory.class})
	@Override
	protected SubjectFactory subjectFactory() {
		return super.subjectFactory();
	}

	@Bean
	@ConditionalOnMissingBean
	@Override
	protected SessionFactory sessionFactory() {
		return super.sessionFactory();
	}

	@Bean(name = "sessionDAO")
	@ConditionalOnBean({RedisManager.class})
	@ConditionalOnMissingBean({SessionDAO.class})
	protected SessionDAO sessionDAO(RedisManager redisManager) {
		ShiroProperties.Session session = properties.getSession();
		return new RedisSessionDAO(redisManager, session.getPrefix(), session.getExpire(),
				session.isSessionInMemoryEnabled(), session.getSessionInMemoryTimeout());
	}

	@Bean(name = "sessionDAO")
	@ConditionalOnMissingBean({SessionDAO.class})
	@Override
	protected SessionDAO sessionDAO() {
		return super.sessionDAO();
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
	protected RememberMeManager rememberMeManager() {
		return super.rememberMeManager();
	}

	@Bean
	@ConditionalOnResource(resources = "classpath:shiro.ini")
	protected Realm iniClasspathRealm() {
		return iniRealmFromLocation("classpath:shiro.ini");
	}

	@Bean
	@ConditionalOnResource(resources = "classpath:META-INF/shiro.ini")
	protected Realm iniMetaInfClasspathRealm() {
		return iniRealmFromLocation("classpath:META-INF/shiro.ini");
	}

	@Bean
	@ConditionalOnMissingBean(Realm.class)
	protected Realm missingRealm() {
		throw new NoRealmBeanConfiguredException();
	}

}
