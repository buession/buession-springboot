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
import org.apache.shiro.spring.boot.autoconfigure.ShiroAutoConfiguration;
import org.apache.shiro.spring.web.config.AbstractShiroWebConfiguration;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.servlet.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * @author Yong.Teng
 */
@Configuration
@AutoConfigureBefore(ShiroAutoConfiguration.class)
@EnableConfigurationProperties(ShiroProperties.class)
@ConditionalOnProperty(name = "shiro.web.enabled", matchIfMissing = true)
public class ShiroWebConfiguration extends AbstractShiroWebConfiguration {

	@Autowired
	protected ShiroProperties shiroProperties;

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
	protected Cookie sessionCookieTemplate(){
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
	protected Cookie rememberMeCookieTemplate(){
		return super.rememberMeCookieTemplate();
	}

	@Bean
	@ConditionalOnMissingBean
	@Override
	protected ShiroFilterChainDefinition shiroFilterChainDefinition(){
		if(Validate.isEmpty(shiroProperties.getFilterChainDefinitions()) == false){
			return super.shiroFilterChainDefinition();
		}else{
			DefaultShiroFilterChainDefinition shiroFilterChainDefinition = new DefaultShiroFilterChainDefinition();

			if(Validate.isEmpty(shiroProperties.getFilterChainDefinitions()) == false){
				shiroProperties.getFilterChainDefinitions().forEach((antPath, definition)->{
					shiroFilterChainDefinition.addPathDefinition(antPath, definition);
				});
			}

			/*if(Validate.isEmpty(urls) == false){
				urls.forEach((antPath, definition)->{
					shiroFilterChainDefinition.addPathDefinition(antPath, definition);
				});
			}*/

			return shiroFilterChainDefinition;
		}
	}

}
