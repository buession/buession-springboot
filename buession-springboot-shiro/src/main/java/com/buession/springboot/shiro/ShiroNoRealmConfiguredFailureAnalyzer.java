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
 * | Copyright @ 2013-2022 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.shiro;

import com.buession.security.shiro.exception.NoRealmBeanConfiguredException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

/**
 * @author Yong.Teng
 * @since 2.0.0
 */
public class ShiroNoRealmConfiguredFailureAnalyzer extends AbstractFailureAnalyzer<NoRealmBeanConfiguredException> {

	@Override
	protected FailureAnalysis analyze(Throwable rootFailure, NoRealmBeanConfiguredException cause){
		return new FailureAnalysis("No bean of type 'org.apache.shiro.realm.Realm' found.",
				"Please create bean of type 'org.apache.shiro.realm.Realm' or add a shiro.ini in the root classpath (src/main/resources/shiro.ini) or in the META-INF folder (src/main/resources/META-INF/shiro.ini).",
				cause);
	}

}
