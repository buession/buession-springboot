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
 * | Copyright @ 2013-2026 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.web.autoconfigure;

import com.buession.security.web.xss.Options;
import com.buession.springboot.web.security.WebSecurityProperties;

/**
 * @author Yong.Teng
 * @since 2.0.0
 */
public abstract class AbstractWebSecurityConfiguration {

	protected WebSecurityProperties properties;

	public AbstractWebSecurityConfiguration(WebSecurityProperties properties) {
		this.properties = properties;
	}

	protected Options xssOptions() {
		final com.buession.security.web.config.Xss xss = properties.getXss();
		final Options.Builder optionsBuilder = Options.Builder.getInstance();

		if(xss instanceof WebSecurityProperties.Xss xssProperties){
			optionsBuilder.policy(xssProperties.getMode());

			if(xssProperties.getMode() == Options.Policy.ESCAPE){
				optionsBuilder.escape(new Options.Escape());
			}else{
				optionsBuilder.clean(new Options.Clean(xssProperties.getPolicyConfigLocation()));
			}
		}

		return optionsBuilder.build();
	}

}
