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
package com.buession.springboot.pac4j.autoconfigure;

import com.buession.security.pac4j.http.JsonAjaxRequestResolver;
import com.buession.springboot.pac4j.config.BaseConfig;
import com.buession.springboot.pac4j.config.Cas;
import com.buession.springboot.pac4j.config.Http;
import com.buession.springboot.pac4j.config.Jwt;
import com.buession.springboot.pac4j.config.OAuth;
import org.pac4j.core.http.ajax.AjaxRequestResolver;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

/**
 * @author Yong.Teng
 * @since 2.0.0
 */
@ConfigurationProperties(prefix = Pac4jProperties.PREFIX)
public class Pac4jProperties {

	public final static String PREFIX = "spring.pac4j";

	/**
	 * 启用认证的客户端类型名称，需要和各配置 {@link BaseConfig.BaseClientConfig} 类型中的名称保持一致
	 */
	private Set<String> clients;

	/**
	 * 默认客户端类型名称
	 */
	private String defaultClient;

	/**
	 * 客户端配置
	 */
	private Client client;

	/**
	 * Compute if a HTTP request is an AJAX one and the appropriate response.
	 */
	private Class<? extends AjaxRequestResolver> ajaxRequestResolverClass = JsonAjaxRequestResolver.class;

	/**
	 * 是否允许多个 Profile
	 */
	private boolean multiProfile;

	/**
	 * 是否保存到 SESSION 中
	 */
	private boolean saveInSession;

	/**
	 * 过滤器配置
	 */
	private Filter filter = new Filter();

	/**
	 * 返回启用认证的客户端类型名称，需要和各配置 {@link BaseConfig.BaseClientConfig} 类型中的名称保持一致
	 *
	 * @return 启用认证的客户端类型名称
	 */
	public Set<String> getClients(){
		return clients;
	}

	/**
	 * 设置启用认证的客户端类型名称，需要和各配置 {@link BaseConfig.BaseClientConfig} 类型中的名称保持一致
	 *
	 * @param clients
	 * 		启用认证的客户端类型名称
	 */
	public void setClients(Set<String> clients){
		this.clients = clients;
	}

	/**
	 * 返回默认客户端类型名称
	 *
	 * @return 默认客户端类型名称
	 */
	public String getDefaultClient(){
		return defaultClient;
	}

	/**
	 * 设置默认客户端类型名称
	 *
	 * @param defaultClient
	 * 		默认客户端类型名称
	 */
	public void setDefaultClient(String defaultClient){
		this.defaultClient = defaultClient;
	}

	/**
	 * 返回客户端配置
	 *
	 * @return 客户端配置
	 */
	public Client getClient(){
		return client;
	}

	/**
	 * 设置客户端配置
	 *
	 * @param client
	 * 		客户端配置
	 */
	public void setClient(Client client){
		this.client = client;
	}

	/**
	 * Return Compute if a HTTP request is an AJAX one and the appropriate response.
	 *
	 * @return Compute if a HTTP request is an AJAX one and the appropriate response.
	 */
	public Class<? extends AjaxRequestResolver> getAjaxRequestResolverClass(){
		return ajaxRequestResolverClass;
	}

	/**
	 * Set Compute if a HTTP request is an AJAX one and the appropriate response.
	 *
	 * @param ajaxRequestResolverClass
	 * 		Compute if a HTTP request is an AJAX one and the appropriate response
	 */
	public void setAjaxRequestResolverClass(
			Class<? extends AjaxRequestResolver> ajaxRequestResolverClass){
		this.ajaxRequestResolverClass = ajaxRequestResolverClass;
	}

	/**
	 * 返回是否允许多个 Profile
	 *
	 * @return 是否允许多个 Profile
	 */
	public boolean isMultiProfile(){
		return multiProfile;
	}

	/**
	 * 设置是否允许多个 Profile
	 *
	 * @param multiProfile
	 * 		是否允许多个 Profile
	 */
	public void setMultiProfile(boolean multiProfile){
		this.multiProfile = multiProfile;
	}

	/**
	 * 返回是否保存到 SESSION 中
	 *
	 * @return 是否保存到 SESSION 中
	 */
	public boolean isSaveInSession(){
		return saveInSession;
	}

	/**
	 * 设置是否保存到 SESSION 中
	 *
	 * @param saveInSession
	 * 		是否保存到 SESSION 中
	 */
	public void setSaveInSession(boolean saveInSession){
		this.saveInSession = saveInSession;
	}

	/**
	 * 返回过滤器配置
	 *
	 * @return 过滤器配置
	 */
	public Filter getFilter(){
		return filter;
	}

	/**
	 * 设置过滤器配置
	 *
	 * @param filter
	 * 		过滤器配置
	 */
	public void setFilter(Filter filter){
		this.filter = filter;
	}

	/**
	 * 过滤器配置
	 */
	public final static class Filter {

		/**
		 * {@link io.buji.pac4j.filter.SecurityFilter} 配置
		 */
		private Security security = new Security();

		/**
		 * {@link io.buji.pac4j.filter.CallbackFilter} 配置
		 */
		private Callback callback = new Callback();

		/**
		 * {@link io.buji.pac4j.filter.LogoutFilter} 配置
		 */
		private Logout logout = new Logout();

		/**
		 * 返回 {@link io.buji.pac4j.filter.SecurityFilter} 配置
		 *
		 * @return {@link io.buji.pac4j.filter.SecurityFilter} 配置
		 */
		public Security getSecurity(){
			return security;
		}

		/**
		 * 设置 {@link io.buji.pac4j.filter.SecurityFilter} 配置
		 *
		 * @param security
		 *        {@link io.buji.pac4j.filter.SecurityFilter} 配置
		 */
		public void setSecurity(Security security){
			this.security = security;
		}

		/**
		 * 返回 {@link io.buji.pac4j.filter.CallbackFilter} 配置
		 *
		 * @return {@link io.buji.pac4j.filter.CallbackFilter} 配置
		 */
		public Callback getCallback(){
			return callback;
		}

		/**
		 * 设置 {@link io.buji.pac4j.filter.CallbackFilter} 配置
		 *
		 * @param callback
		 *        {@link io.buji.pac4j.filter.CallbackFilter} 配置
		 */
		public void setCallback(Callback callback){
			this.callback = callback;
		}

		/**
		 * 返回 {@link io.buji.pac4j.filter.LogoutFilter} 配置
		 *
		 * @return {@link io.buji.pac4j.filter.LogoutFilter} 配置
		 */
		public Logout getLogout(){
			return logout;
		}

		/**
		 * 设置 {@link io.buji.pac4j.filter.LogoutFilter} 配置
		 *
		 * @param logout
		 *        {@link io.buji.pac4j.filter.LogoutFilter} 配置
		 */
		public void setLogout(Logout logout){
			this.logout = logout;
		}

		/**
		 * {@link io.buji.pac4j.filter.SecurityFilter} 配置
		 */
		public final static class Security {

			/**
			 * 认证器名称
			 */
			private Set<String> authorizers;

			private Set<String> matchers;

			/**
			 * 返回认证器名称
			 *
			 * @return 认证器名称
			 */
			public Set<String> getAuthorizers(){
				return authorizers;
			}

			/**
			 * 设置认证器名称
			 *
			 * @param authorizers
			 * 		认证器名称
			 */
			public void setAuthorizers(Set<String> authorizers){
				this.authorizers = authorizers;
			}

			public Set<String> getMatchers(){
				return matchers;
			}

			public void setMatchers(Set<String> matchers){
				this.matchers = matchers;
			}

		}

		/**
		 * {@link io.buji.pac4j.filter.CallbackFilter} 配置
		 */
		public final static class Callback {

			/**
			 * 默认跳转地址
			 */
			private String defaultUrl;

			/**
			 * 返回默认跳转地址
			 *
			 * @return 默认跳转地址
			 */
			public String getDefaultUrl(){
				return defaultUrl;
			}

			/**
			 * 设置默认跳转地址
			 *
			 * @param defaultUrl
			 * 		默认跳转地址
			 */
			public void setDefaultUrl(String defaultUrl){
				this.defaultUrl = defaultUrl;
			}

		}

		/**
		 * {@link io.buji.pac4j.filter.LogoutFilter} 配置
		 */
		public final static class Logout {

			/**
			 * 登出成功默认跳转地址
			 */
			private String defaultUrl;

			private String logoutUrlPattern;

			/**
			 * 本地是否退出登录
			 */
			private boolean localLogout = true;

			/**
			 * 认证中心是否退出登录
			 */
			private boolean centralLogout = true;

			/**
			 * 返回登出成功默认跳转地址
			 *
			 * @return 登出成功默认跳转地址
			 */
			public String getDefaultUrl(){
				return defaultUrl;
			}

			/**
			 * 设置登出成功默认跳转地址
			 *
			 * @param defaultUrl
			 * 		登出成功默认跳转地址
			 */
			public void setDefaultUrl(String defaultUrl){
				this.defaultUrl = defaultUrl;
			}

			public String getLogoutUrlPattern(){
				return logoutUrlPattern;
			}

			public void setLogoutUrlPattern(String logoutUrlPattern){
				this.logoutUrlPattern = logoutUrlPattern;
			}

			/**
			 * 返回本地是否退出登录
			 *
			 * @return 本地是否退出登录
			 */
			public boolean isLocalLogout(){
				return localLogout;
			}

			/**
			 * 设置本地是否退出登录
			 *
			 * @param localLogout
			 * 		本地是否退出登录
			 */
			public void setLocalLogout(boolean localLogout){
				this.localLogout = localLogout;
			}

			/**
			 * 返回认证中心是否退出登录
			 *
			 * @return 认证中心是否退出登录
			 */
			public boolean isCentralLogout(){
				return centralLogout;
			}

			/**
			 * 设置认证中心是否退出登录
			 *
			 * @param centralLogout
			 * 		认证中心是否退出登录
			 */
			public void setCentralLogout(boolean centralLogout){
				this.centralLogout = centralLogout;
			}
			
		}

	}

	/**
	 * 客户端配置
	 */
	public final static class Client {

		/**
		 * CAS 配置
		 */
		private Cas cas;

		/**
		 * HTTP 配置
		 */
		private Http http;

		/**
		 * JWT 配置
		 */
		private Jwt jwt;

		/**
		 * OAuth 配置
		 */
		private OAuth oAuth;

		/**
		 * 返回 CAS 配置
		 *
		 * @return CAS 配置
		 */
		public Cas getCas(){
			return cas;
		}

		/**
		 * 设置 CAS 配置
		 *
		 * @param cas
		 * 		CAS 配置
		 */
		public void setCas(Cas cas){
			this.cas = cas;
		}

		/**
		 * 返回 HTTP 配置
		 *
		 * @return HTTP 配置
		 */
		public Http getHttp(){
			return http;
		}

		/**
		 * 设置 HTTP 配置
		 *
		 * @param http
		 * 		HTTP 配置
		 */
		public void setHttp(Http http){
			this.http = http;
		}

		/**
		 * 返回 JWT 配置
		 *
		 * @return JWT 配置
		 */
		public Jwt getJwt(){
			return jwt;
		}

		/**
		 * 设置 JWT 配置
		 *
		 * @param jwt
		 * 		JWT 配置
		 */
		public void setJwt(Jwt jwt){
			this.jwt = jwt;
		}

		/**
		 * 返回 OAuth 配置
		 *
		 * @return OAuth 配置
		 */
		public OAuth getOAuth(){
			return oAuth;
		}

		/**
		 * 设置 OAuth 配置
		 *
		 * @param oAuth
		 * 		OAuth 配置
		 */
		public void setOAuth(OAuth oAuth){
			this.oAuth = oAuth;
		}

	}

}
