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

import com.buession.springboot.pac4j.filter.Pac4jFilter;
import com.buession.springboot.shiro.core.ShiroFilter;
import io.buji.pac4j.realm.Pac4jRealm;
import io.buji.pac4j.subject.Pac4jSubjectFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Pac4j for shiro 自动加载类
 *
 * @author Yong.Teng
 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({Pac4jRealm.class, SubjectFactory.class})
@AutoConfigureAfter({com.buession.springboot.pac4j.autoconfigure.Pac4jConfiguration.class})
public class Pac4jConfiguration {

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnBean({Pac4jFilter.class})
	public ShiroFilter shiroFilter(ObjectProvider<Pac4jFilter> pac4jFilter) {
		return new ShiroFilter(pac4jFilter.getIfAvailable());
	}

	@Bean
	@ConditionalOnMissingBean
	public Realm pac4jRealm() {
		return new Pac4jRealm();
	}

	@Bean
	@ConditionalOnMissingBean({SubjectFactory.class})
	public SubjectFactory subjectFactory(ObjectProvider<SecurityManager> securityManager) {
		SubjectFactory subjectFactory = new Pac4jSubjectFactory();

		securityManager.ifAvailable((securityMgr)->{
			((DefaultSecurityManager) securityMgr).setSubjectFactory(subjectFactory);
		});

		return subjectFactory;
	}

}
