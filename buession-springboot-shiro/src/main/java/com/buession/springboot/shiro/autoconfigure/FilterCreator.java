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
 * | Copyright @ 2013-2021 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.shiro.autoconfigure;

import com.buession.core.utils.ArrayUtils;
import com.buession.core.validator.Validate;
import com.buession.security.pac4j.filter.CallbackFilter;
import com.buession.security.pac4j.filter.LogoutFilter;
import com.buession.security.pac4j.filter.SecurityFilter;
import org.pac4j.core.config.Config;
import org.pac4j.core.util.Pac4jConstants;

/**
 * @author Yong.Teng
 * @since 1.2.2
 */
class FilterCreator {

	final Config config;

	final Pac4j pac4j;

	public FilterCreator(final Config config, final Pac4j pac4j){
		this.config = config;
		this.pac4j = pac4j;
	}

	public SecurityFilter createSecurityFilter(){
		final SecurityFilter filter = new SecurityFilter(config);

		if(pac4j.getClients() != null){
			filter.setClients(ArrayUtils.toString(pac4j.getClients(), Pac4jConstants.ELEMENT_SEPARATOR));
		}

		filter.setMultiProfile(pac4j.isMultiProfile());

		if(Validate.isNotEmpty(pac4j.getAuthorizers())){
			filter.setAuthorizers(ArrayUtils.toString(pac4j.getAuthorizers(), Pac4jConstants.ELEMENT_SEPARATOR));
		}

		if(Validate.isNotEmpty(pac4j.getMatchers())){
			filter.setMatchers(ArrayUtils.toString(pac4j.getMatchers(), Pac4jConstants.ELEMENT_SEPARATOR));
		}

		return filter;
	}

	public CallbackFilter createCallbackFilter(){
		final CallbackFilter callbackFilter = new CallbackFilter(config);

		callbackFilter.setDefaultUrl(pac4j.getDefaultUrl());
		callbackFilter.setMultiProfile(pac4j.isMultiProfile());
		callbackFilter.setDefaultClient(pac4j.getDefaultClient());
		callbackFilter.setSaveInSession(pac4j.isSaveInSession());

		return callbackFilter;
	}

	public LogoutFilter createLogoutFilter(){
		final LogoutFilter logoutFilter = new LogoutFilter(config);

		logoutFilter.setDefaultUrl(pac4j.getLogoutRedirectUrl());
		logoutFilter.setLogoutUrlPattern(pac4j.getLogoutUrlPattern());
		logoutFilter.setLocalLogout(pac4j.isLocalLogout());
		logoutFilter.setCentralLogout(pac4j.isCentralLogout());

		return logoutFilter;
	}

}
