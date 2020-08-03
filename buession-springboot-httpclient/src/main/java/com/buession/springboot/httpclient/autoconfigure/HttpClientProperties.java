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
 * | Copyright @ 2013-2019 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.httpclient.autoconfigure;

import com.buession.httpclient.core.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;

/**
 * @author Yong.Teng
 */
@ConfigurationProperties(prefix = "spring.httpclient")
public class HttpClientProperties extends Configuration {

	@ConfigurationProperties(prefix = "httpclient")
	@Deprecated
	public final static class DeprecatedHttpClientProperties extends HttpClientProperties {

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.httpclient.max-connections")
		@Override
		public void setMaxConnections(int maxConnections){
			super.setMaxConnections(maxConnections);
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.httpclient.max-per-route")
		@Override
		public void setMaxPerRoute(int maxPerRoute){
			super.setMaxPerRoute(maxPerRoute);
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.httpclient.idle-connection-time")
		@Override
		public void setIdleConnectionTime(int idleConnectionTime){
			super.setIdleConnectionTime(idleConnectionTime);
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.httpclient.connect-timeout")
		@Override
		public void setConnectTimeout(int connectTimeout){
			super.setConnectTimeout(connectTimeout);
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.httpclient.connection-request-timeout")
		@Override
		public void setConnectionRequestTimeout(int connectionRequestTimeout){
			super.setConnectionRequestTimeout(connectionRequestTimeout);
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.httpclient.read-timeout")
		@Override
		public void setReadTimeout(int readTimeout){
			super.setReadTimeout(readTimeout);
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.httpclient.allow-redirects")
		@Override
		public void setAllowRedirects(boolean allowRedirects){
			super.setAllowRedirects(allowRedirects);
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.httpclient.allow-redirects")
		@Override
		public void setRelativeRedirectsAllowed(boolean relativeRedirectsAllowed){
			super.setRelativeRedirectsAllowed(relativeRedirectsAllowed);
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.httpclient.circular-redirects-allowed")
		@Override
		public void setCircularRedirectsAllowed(boolean circularRedirectsAllowed){
			super.setCircularRedirectsAllowed(circularRedirectsAllowed);
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.httpclient.max-redirects")
		@Override
		public void setMaxRedirects(int maxRedirects){
			super.setMaxRedirects(maxRedirects);
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.httpclient.authentication-enabled")
		@Override
		public void setAuthenticationEnabled(boolean authenticationEnabled){
			super.setAuthenticationEnabled(authenticationEnabled);
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.httpclient.contentCompression-enabled")
		@Override
		public void setContentCompressionEnabled(boolean contentCompressionEnabled){
			super.setContentCompressionEnabled(contentCompressionEnabled);
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.httpclient.normalize-uri")
		@Override
		public void setNormalizeUri(boolean normalizeUri){
			super.setNormalizeUri(normalizeUri);
		}

	}

}
