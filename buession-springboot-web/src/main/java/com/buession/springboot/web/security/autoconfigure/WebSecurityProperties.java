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
 * | Copyright @ 2013-2020 Buession.com Inc.														|
 * +------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.web.security.autoconfigure;

import com.buession.security.spring.web.csrf.CookieCsrfTokenRepositoryGenerator;
import com.buession.security.spring.web.csrf.CsrfTokenRepositoryGenerator;
import com.buession.security.spring.web.csrf.HttpSessionCsrfTokenRepositoryGenerator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Map;

/**
 * Configuration properties for Spring Security.
 *
 * @author Yong.Teng
 * @since 1.2.0
 */
@ConfigurationProperties(prefix = "spring.security")
public class SecurityProperties {

	/**
	 * Http Basic 配置
	 */
	@NestedConfigurationProperty
	private HttpBasic httpBasic = new HttpBasic();

	/**
	 * Csrf 配置
	 */
	@NestedConfigurationProperty
	private Csrf csrf = new Csrf();

	/**
	 * Frame Options 配置
	 */
	@NestedConfigurationProperty
	private FrameOptions frameOptions = new FrameOptions();

	/**
	 * Hsts 配置
	 */
	@NestedConfigurationProperty
	private Hsts hsts = new Hsts();

	/**
	 * Hpkp 配置
	 */
	@NestedConfigurationProperty
	private Hpkp hpkp = new Hpkp();

	/**
	 * Content Security Policy 配置
	 */
	@NestedConfigurationProperty
	private ContentSecurityPolicy contentSecurityPolicy = new ContentSecurityPolicy();

	/**
	 * Referrer Policy 配置
	 */
	@NestedConfigurationProperty
	private ReferrerPolicy referrerPolicy = new ReferrerPolicy();

	/**
	 * XSS 配置
	 */
	@NestedConfigurationProperty
	private Xss xss = new Xss();

	/**
	 * 返回 Http Basic 配置
	 *
	 * @return Http Basic 配置
	 */
	public HttpBasic getHttpBasic(){
		return httpBasic;
	}

	/**
	 * 设置 Http Basic 配置
	 *
	 * @param httpBasic
	 * 		Http Basic 配置
	 */
	public void setHttpBasic(HttpBasic httpBasic){
		this.httpBasic = httpBasic;
	}

	/**
	 * 返回 Csrf 配置
	 *
	 * @return Csrf 配置
	 */
	public Csrf getCsrf(){
		return csrf;
	}

	/**
	 * 设置 Csrf 配置
	 *
	 * @param csrf
	 * 		Csrf 配置
	 */
	public void setCsrf(Csrf csrf){
		this.csrf = csrf;
	}

	/**
	 * 返回 Frame Options 配置
	 *
	 * @return Frame Options 配置
	 */
	public FrameOptions getFrameOptions(){
		return frameOptions;
	}

	/**
	 * 设置 Frame Options 配置
	 *
	 * @param frameOptions
	 * 		Frame Options 配置
	 */
	public void setFrameOptions(FrameOptions frameOptions){
		this.frameOptions = frameOptions;
	}

	/**
	 * 返回 Hsts 配置
	 *
	 * @return Hsts 配置
	 */
	public Hsts getHsts(){
		return hsts;
	}

	/**
	 * 设置 Hsts 配置
	 *
	 * @param hsts
	 * 		Hsts 配置
	 */
	public void setHsts(Hsts hsts){
		this.hsts = hsts;
	}

	/**
	 * 返回 Hpkp 配置
	 *
	 * @return Hpkp 配置
	 */
	public Hpkp getHpkp(){
		return hpkp;
	}

	/**
	 * 设置 Hpkp 配置
	 *
	 * @param hpkp
	 * 		Hpkp 配置
	 */
	public void setHpkp(Hpkp hpkp){
		this.hpkp = hpkp;
	}

	/**
	 * 返回 Content Security Policy 配置
	 *
	 * @return Content Security Policy 配置
	 */
	public ContentSecurityPolicy getContentSecurityPolicy(){
		return contentSecurityPolicy;
	}

	/**
	 * 设置 Content Security Policy 配置
	 *
	 * @param contentSecurityPolicy
	 * 		Content Security Policy 配置
	 */
	public void setContentSecurityPolicy(ContentSecurityPolicy contentSecurityPolicy){
		this.contentSecurityPolicy = contentSecurityPolicy;
	}

	/**
	 * 返回 Referrer Policy 配置
	 *
	 * @return Referrer Policy 配置
	 */
	public ReferrerPolicy getReferrerPolicy(){
		return referrerPolicy;
	}

	/**
	 * 设置 Referrer Policy 配置
	 *
	 * @param referrerPolicy
	 * 		Referrer Policy 配置
	 */
	public void setReferrerPolicy(ReferrerPolicy referrerPolicy){
		this.referrerPolicy = referrerPolicy;
	}

	/**
	 * 返回 XSS 配置
	 *
	 * @return XSS 配置
	 */
	public Xss getXss(){
		return xss;
	}

	/**
	 * 设置 XSS 配置
	 *
	 * @param xss
	 * 		XSS 配置
	 */
	public void setXss(Xss xss){
		this.xss = xss;
	}

	/**
	 * Http Basic 验证
	 * <p><a href="https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Authentication"
	 * target="_blank">https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Authentication</a></p>
	 */
	public final static class HttpBasic {

		/**
		 * 是否启用 Http Basic 验证
		 */
		private boolean enable = false;

		/**
		 * 返回是否启用 Http Basic 验证
		 *
		 * @return 是否启用 Http Basic 验证
		 */
		public boolean isEnable(){
			return getEnable();
		}

		/**
		 * 返回是否启用 Http Basic 验证
		 *
		 * @return 是否启用 Http Basic 验证
		 */
		public boolean getEnable(){
			return enable;
		}

		/**
		 * 设置是否启用 Http Basic 验证
		 *
		 * @param enable
		 * 		是否启用 Http Basic 验证
		 */
		public void setEnable(boolean enable){
			this.enable = enable;
		}

	}

	/**
	 * Csrf 配置
	 * <p>ef="https://baike.baidu.com/item/跨站请求伪造/13777878" target="_blank">https://baike.baidu
	 * .com/item/跨站请求伪造/13777878</a>
	 * </p>
	 */
	public final static class Csrf {

		/**
		 * 是否启用 Csrf
		 */
		private boolean enable = true;

		/**
		 * Csrf Token Repository 生成器类
		 */
		private Class<? extends CsrfTokenRepositoryGenerator> tokenRepositoryGenerator;

		/**
		 * Cookie Csrf Token Repository 配置
		 */
		@NestedConfigurationProperty
		private Cookie cookie = new Cookie();

		/**
		 * Session Csrf Token Repository 配置
		 */
		@NestedConfigurationProperty
		private Session session = new Session();

		/**
		 * 返回是否启用 Csrf
		 *
		 * @return 是否启用 Csrf
		 */
		public boolean isEnable(){
			return getEnable();
		}

		/**
		 * 返回是否启用 Csrf
		 *
		 * @return 是否启用 Csrf
		 */
		public boolean getEnable(){
			return enable;
		}

		/**
		 * 设置是否启用 Csrf
		 *
		 * @param enable
		 * 		是否启用 Csrf
		 */
		public void setEnable(boolean enable){
			this.enable = enable;
		}

		/**
		 * 返回 Csrf Token Repository 生成器类
		 *
		 * @return Csrf Token Repository 生成器类
		 */
		public Class<? extends CsrfTokenRepositoryGenerator> getTokenRepositoryGenerator(){
			return tokenRepositoryGenerator;
		}

		/**
		 * 设置 Csrf Token Repository 生成器类
		 *
		 * @param tokenRepositoryGenerator
		 * 		Csrf Token Repository 生成器类
		 */
		public void setTokenRepositoryGenerator(Class<? extends CsrfTokenRepositoryGenerator> tokenRepositoryGenerator){
			this.tokenRepositoryGenerator = tokenRepositoryGenerator;
		}

		/**
		 * 返回 Cookie Csrf Token Repository 配置
		 *
		 * @return Cookie Csrf Token Repository 配置
		 */
		public Cookie getCookie(){
			return cookie;
		}

		/**
		 * 设置 Cookie Csrf Token Repository 配置
		 *
		 * @param cookie
		 * 		Cookie Csrf Token Repository 配置
		 */
		public void setCookie(Cookie cookie){
			this.cookie = cookie;
		}

		/**
		 * 设置 Session Csrf Token Repository 配置
		 *
		 * @return Session Csrf Token Repository 配置
		 */
		public Session getSession(){
			return session;
		}

		/**
		 * 设置 Session Csrf Token Repository 配置
		 *
		 * @param session
		 * 		Session Csrf Token Repository 配置
		 */
		public void setSession(Session session){
			this.session = session;
		}

		/**
		 * Cookie Csrf Token Repository 配置{@link org.springframework.security.web.csrf.CookieCsrfTokenRepository}
		 */
		public final static class Cookie {

			/**
			 * Csrf 请求参数名
			 */
			private String parameterName = CookieCsrfTokenRepositoryGenerator.DEFAULT_CSRF_PARAMETER_NAME;

			/**
			 * Csrf 请求头名称
			 */
			private String headerName = CookieCsrfTokenRepositoryGenerator.DEFAULT_CSRF_HEADER_NAME;

			/**
			 * Csrf Cookie 名称
			 */
			private String cookieName = CookieCsrfTokenRepositoryGenerator.DEFAULT_CSRF_COOKIE_NAME;

			/**
			 * Csrf Cookie 作用域
			 */
			private String cookieDomain;

			/**
			 * Csrf Cookie 作用路径
			 */
			private String cookiePath;

			/**
			 * Csrf Cookie 是否可通过客户端脚本访问
			 */
			private boolean cookieHttpOnly = true;

			/**
			 * 返回 Csrf 请求参数名
			 *
			 * @return Csrf 请求参数名
			 */
			public String getParameterName(){
				return parameterName;
			}

			/**
			 * 设置 Csrf 请求参数名
			 *
			 * @param parameterName
			 * 		Csrf 请求参数名
			 */
			public void setParameterName(String parameterName){
				this.parameterName = parameterName;
			}

			/**
			 * 返回 Csrf 请求头名称
			 *
			 * @return Csrf 请求头名称
			 */
			public String getHeaderName(){
				return headerName;
			}

			/**
			 * 设置 Csrf 请求头名称
			 *
			 * @param headerName
			 * 		Csrf 请求头名称
			 */
			public void setHeaderName(String headerName){
				this.headerName = headerName;
			}

			/**
			 * 返回 Csrf Cookie 名称
			 *
			 * @return Csrf Cookie 名称
			 */
			public String getCookieName(){
				return cookieName;
			}

			/**
			 * 设置 Csrf Cookie 名称
			 *
			 * @param cookieName
			 * 		Csrf Cookie 名称
			 */
			public void setCookieName(String cookieName){
				this.cookieName = cookieName;
			}

			/**
			 * 返回 Csrf Cookie 作用域
			 *
			 * @return Csrf Cookie 作用域
			 */
			public String getCookieDomain(){
				return cookieDomain;
			}

			/**
			 * 设置 Csrf Cookie 作用域
			 *
			 * @param cookieDomain
			 * 		Csrf Cookie 作用域
			 */
			public void setCookieDomain(String cookieDomain){
				this.cookieDomain = cookieDomain;
			}

			/**
			 * 返回 Csrf Cookie 作用路径
			 *
			 * @return Csrf Cookie 作用路径
			 */
			public String getCookiePath(){
				return cookiePath;
			}

			/**
			 * 设置 Csrf Cookie 作用路径
			 *
			 * @param cookiePath
			 * 		Csrf Cookie 作用路径
			 */
			public void setCookiePath(String cookiePath){
				this.cookiePath = cookiePath;
			}

			/**
			 * 返回 Csrf Cookie 是否可通过客户端脚本访问
			 *
			 * @return Csrf Cookie 是否可通过客户端脚本访问
			 */
			public boolean isCookieHttpOnly(){
				return getCookieHttpOnly();
			}

			/**
			 * 返回 Csrf Cookie 是否可通过客户端脚本访问
			 *
			 * @return Csrf Cookie 是否可通过客户端脚本访问
			 */
			public boolean getCookieHttpOnly(){
				return cookieHttpOnly;
			}

			/**
			 * 设置 Csrf Cookie 是否可通过客户端脚本访问
			 *
			 * @param cookieHttpOnly
			 * 		Csrf Cookie 是否可通过客户端脚本访问
			 */
			public void setCookieHttpOnly(boolean cookieHttpOnly){
				this.cookieHttpOnly = cookieHttpOnly;
			}

		}

		/**
		 * Cookie Csrf Token Repository 配置{@link org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository}
		 */
		public final static class Session {

			/**
			 * Csrf 请求参数名
			 */
			private String parameterName = HttpSessionCsrfTokenRepositoryGenerator.DEFAULT_CSRF_PARAMETER_NAME;

			/**
			 * Csrf 请求头名称
			 */
			private String headerName = HttpSessionCsrfTokenRepositoryGenerator.DEFAULT_CSRF_HEADER_NAME;

			/**
			 * Csrf Session 属性名称
			 */
			private String sessionAttributeName =
					HttpSessionCsrfTokenRepositoryGenerator.DEFAULT_CSRF_TOKEN_SESSION_ATTRIBUTE_NAME;

			/**
			 * 返回 Csrf 请求参数名
			 *
			 * @return Csrf 请求参数名
			 */
			public String getParameterName(){
				return parameterName;
			}

			/**
			 * 设置 Csrf 请求参数名
			 *
			 * @param parameterName
			 * 		Csrf 请求参数名
			 */
			public void setParameterName(String parameterName){
				this.parameterName = parameterName;
			}

			/**
			 * 返回 Csrf 请求头名称
			 *
			 * @return Csrf 请求头名称
			 */
			public String getHeaderName(){
				return headerName;
			}

			/**
			 * 设置 Csrf 请求头名称
			 *
			 * @param headerName
			 * 		Csrf 请求头名称
			 */
			public void setHeaderName(String headerName){
				this.headerName = headerName;
			}

			/**
			 * 返回 Csrf Session 属性名称
			 *
			 * @return Csrf Session 属性名称
			 */
			public String getSessionAttributeName(){
				return sessionAttributeName;
			}

			/**
			 * 设置 Csrf Session 属性名称
			 *
			 * @param sessionAttributeName
			 * 		Csrf Session 属性名称
			 */
			public void setSessionAttributeName(String sessionAttributeName){
				this.sessionAttributeName = sessionAttributeName;
			}

		}

	}

	/**
	 * Frame Options 配置
	 * <p><a href="https://developer.mozilla.org/zh-CN/docs/Web/HTTP/X-Frame-Options"
	 * target="_blank">https://developer.mozilla.org/zh-CN/docs/Web/HTTP/X-Frame-Options</a></p>
	 */
	public final static class FrameOptions {

		/**
		 * 是否启用 Frame Options
		 */
		private boolean enable = true;

		/**
		 * Frame Options 模式
		 */
		private XFrameOptionsHeaderWriter.XFrameOptionsMode mode = XFrameOptionsHeaderWriter.XFrameOptionsMode.DENY;

		/**
		 * 返回是否启用 Frame Options
		 *
		 * @return 是否启用 Frame Options
		 */
		public boolean isEnable(){
			return getEnable();
		}

		/**
		 * 返回是否启用 Frame Options
		 *
		 * @return 是否启用 Frame Options
		 */
		public boolean getEnable(){
			return enable;
		}

		/**
		 * 配置是否启用 Frame Options
		 *
		 * @return 是否启用 Frame Options
		 */
		public void setEnable(boolean enable){
			this.enable = enable;
		}

		/**
		 * 返回 Frame Options 模式
		 *
		 * @return Frame Options 模式
		 */
		public XFrameOptionsHeaderWriter.XFrameOptionsMode getMode(){
			return mode;
		}

		/**
		 * 设置 Frame Options 模式
		 *
		 * @param mode
		 * 		Frame Options 模式
		 */
		public void setMode(XFrameOptionsHeaderWriter.XFrameOptionsMode mode){
			this.mode = mode;
		}

	}

	/**
	 * Hsts 配置
	 * <p><a href="https://developer.mozilla.org/zh-CN/docs/Web/HTTP/HTTP_Strict_Transport_Security"
	 * target="_blank">https://developer.mozilla.org/zh-CN/docs/Web/HTTP/HTTP_Strict_Transport_Security</a></p>
	 */
	public final static class Hsts {

		private final static long DEFAULT_MAX_AGE = 31536000;

		/**
		 * 是否启用 Hsts
		 */
		private boolean enable = true;

		/**
		 * Request 匹配器类
		 */
		private Class<? extends RequestMatcher> matcher;

		/**
		 * 缓存时间，在浏览器收到这个请求后的 maxAge 秒的时间内凡是访问这个域名下的请求都使用HTTPS请求，默认缓存一年
		 */
		private long maxAge = DEFAULT_MAX_AGE;

		/**
		 * 配置此规则也适用于该网站的所有子域名
		 */
		private boolean includeSubDomains;

		/**
		 * 配置是否预加载 HSTS
		 */
		private boolean preload;

		/**
		 * 返回是否启用 Hsts
		 *
		 * @return 是否启用 Hsts
		 */
		public boolean isEnable(){
			return getEnable();
		}

		/**
		 * 返回是否启用 Hsts
		 *
		 * @return 是否启用 Hsts
		 */
		public boolean getEnable(){
			return enable;
		}

		/**
		 * 配置是否启用 Hsts
		 *
		 * @return 是否启用 Hsts
		 */
		public void setEnable(boolean enable){
			this.enable = enable;
		}

		/**
		 * 返回 Request 匹配器类
		 *
		 * @return Request 匹配器类
		 */
		public Class<? extends RequestMatcher> getMatcher(){
			return matcher;
		}

		/**
		 * 设置 Request 匹配器类
		 *
		 * @param matcher
		 * 		Request 匹配器类
		 */
		public void setMatcher(Class<? extends RequestMatcher> matcher){
			this.matcher = matcher;
		}

		/**
		 * 返回缓存时间，在浏览器收到这个请求后的 maxAge 秒的时间内凡是访问这个域名下的请求都使用HTTPS请求
		 *
		 * @return 缓存时间
		 */
		public long getMaxAge(){
			return maxAge;
		}

		/**
		 * 设置缓存时间，设置在浏览器收到这个请求后的 maxAge 秒的时间内凡是访问这个域名下的请求都使用HTTPS请求
		 *
		 * @param maxAge
		 * 		缓存时间（单位：秒）
		 */
		public void setMaxAge(long maxAge){
			this.maxAge = maxAge;
		}

		/**
		 * 返回此规则是否适用于该网站的所有子域名
		 *
		 * @return 此规则是否适用于该网站的所有子域名
		 */
		public boolean isIncludeSubDomains(){
			return getIncludeSubDomains();
		}

		/**
		 * 返回此规则是否适也用于该网站的所有子域名
		 *
		 * @return 此规则是否也适用于该网站的所有子域名
		 */
		public boolean getIncludeSubDomains(){
			return includeSubDomains;
		}

		/**
		 * 设置此规则是否也适用于该网站的所有子域名
		 *
		 * @param includeSubDomains
		 * 		此规则是否也适用于该网站的所有子域名
		 */
		public void setIncludeSubDomains(boolean includeSubDomains){
			this.includeSubDomains = includeSubDomains;
		}

		/**
		 * 返回是否预加载 HSTS
		 *
		 * @return 是否预加载 HSTS
		 */
		public boolean isPreload(){
			return getPreload();
		}

		/**
		 * 返回是否预加载 HSTS
		 *
		 * @return 是否预加载 HSTS
		 */
		public boolean getPreload(){
			return preload;
		}

		/**
		 * 设置是否预加载 HSTS
		 *
		 * @param preload
		 * 		是否预加载 HSTS
		 */
		public void setPreload(boolean preload){
			this.preload = preload;
		}

	}

	/**
	 * Hpkp 配置
	 * <p><a href="https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Public_Key_Pinning"
	 * target="_blank">https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Public_Key_Pinning</a></p>
	 */
	public final static class Hpkp {

		/**
		 * 是否启用 Hpkp
		 */
		private boolean enable = true;

		private Map<String, String> pins;

		private String[] sha256Pins;

		/**
		 * 浏览器应记住仅使用其中一个已定义的密钥访问此站点的时间
		 */
		private long maxAge;

		/**
		 * 配置此规则也适用于该网站的所有子域名
		 */
		private boolean includeSubDomains;

		private boolean reportOnly;

		private String reportUri;

		/**
		 * 返回是否启用 Hpkp
		 *
		 * @return 是否启用 Hpkp
		 */
		public boolean isEnable(){
			return getEnable();
		}

		/**
		 * 返回是否启用 Hpkp
		 *
		 * @return 是否启用 Hpkp
		 */
		public boolean getEnable(){
			return enable;
		}

		/**
		 * 配置是否启用 Hsts
		 *
		 * @return 是否启用 Hsts
		 */
		public void setEnable(boolean enable){
			this.enable = enable;
		}

		public Map<String, String> getPins(){
			return pins;
		}

		public void setPins(Map<String, String> pins){
			this.pins = pins;
		}

		public String[] getSha256Pins(){
			return sha256Pins;
		}

		public void setSha256Pins(String[] sha256Pins){
			this.sha256Pins = sha256Pins;
		}

		/**
		 * 返回浏览器应记住仅使用其中一个已定义的密钥访问此站点的时间
		 *
		 * @return 浏览器应记住仅使用其中一个已定义的密钥访问此站点的时间
		 */
		public long getMaxAge(){
			return maxAge;
		}

		/**
		 * 设置浏览器应记住仅使用其中一个已定义的密钥访问此站点的时间
		 *
		 * @param maxAge
		 * 		浏览器应记住仅使用其中一个已定义的密钥访问此站点的时间
		 */
		public void setMaxAge(long maxAge){
			this.maxAge = maxAge;
		}

		/**
		 * 返回此规则是否也适用于该网站的所有子域名
		 *
		 * @return 配置是否也规则也适用于该网站的所有子域名
		 */
		public boolean isIncludeSubDomains(){
			return getIncludeSubDomains();
		}

		/**
		 * 返回此规则是否也适用于该网站的所有子域名
		 *
		 * @return 配置是否也规则也适用于该网站的所有子域名
		 */
		public boolean getIncludeSubDomains(){
			return includeSubDomains;
		}

		/**
		 * 设置此规则是否也适用于该网站的所有子域名
		 *
		 * @param includeSubDomains
		 * 		此规则是否也也适用于该网站的所有子域名
		 */
		public void setIncludeSubDomains(boolean includeSubDomains){
			this.includeSubDomains = includeSubDomains;
		}

		public boolean isReportOnly(){
			return getReportOnly();
		}

		public boolean getReportOnly(){
			return reportOnly;
		}

		public void setReportOnly(boolean reportOnly){
			this.reportOnly = reportOnly;
		}

		public String getReportUri(){
			return reportUri;
		}

		public void setReportUri(String reportUri){
			this.reportUri = reportUri;
		}

	}

	/**
	 * Content Security Policy 配置
	 * <p><a href="https://developer.mozilla.org/zh-CN/docs/Web/HTTP/CSP" target="_blank">https://developer.mozilla
	 * .org/zh-CN/docs/Web/HTTP/CSP</a></p>
	 */
	public final static class ContentSecurityPolicy {

		/**
		 * 是否启用 ContentSecurityPolicy
		 */
		private boolean enable = true;

		private boolean reportOnly;

		/**
		 * 策略
		 */
		private String policyDirectives;

		/**
		 * 返回是否启用 ContentSecurityPolicy
		 *
		 * @return 是否启用 ContentSecurityPolicy
		 */
		public boolean isEnable(){
			return getEnable();
		}

		/**
		 * 返回是否启用 ContentSecurityPolicy
		 *
		 * @return 是否启用 ContentSecurityPolicy
		 */
		public boolean getEnable(){
			return enable;
		}

		/**
		 * 配置是否启用 ContentSecurityPolicy
		 *
		 * @return 是否启用 ContentSecurityPolicy
		 */
		public void setEnable(boolean enable){
			this.enable = enable;
		}

		public boolean isReportOnly(){
			return getReportOnly();
		}

		public boolean getReportOnly(){
			return reportOnly;
		}

		public void setReportOnly(boolean reportOnly){
			this.reportOnly = reportOnly;
		}

		/**
		 * 返回策略
		 *
		 * @return 策略
		 */
		public String getPolicyDirectives(){
			return policyDirectives;
		}

		/**
		 * 设置策略
		 *
		 * @param policyDirectives
		 * 		策略
		 */
		public void setPolicyDirectives(String policyDirectives){
			this.policyDirectives = policyDirectives;
		}

	}

	/**
	 * ReferrerPolicy 配置
	 */
	public final static class ReferrerPolicy {

		/**
		 * 是否启用 ReferrerPolicy
		 */
		private boolean enable = true;

		private ReferrerPolicyHeaderWriter.ReferrerPolicy referrerPolicy =
				ReferrerPolicyHeaderWriter.ReferrerPolicy.NO_REFERRER;

		/**
		 * 返回是否启用 ReferrerPolicy
		 *
		 * @return 是否启用 ReferrerPolicy
		 */
		public boolean isEnable(){
			return getEnable();
		}

		/**
		 * 返回是否启用 ReferrerPolicy
		 *
		 * @return 是否启用 ReferrerPolicy
		 */
		public boolean getEnable(){
			return enable;
		}

		/**
		 * 配置是否启用 ReferrerPolicy
		 *
		 * @return 是否启用 ReferrerPolicy
		 */
		public void setEnable(boolean enable){
			this.enable = enable;
		}

		public ReferrerPolicyHeaderWriter.ReferrerPolicy getReferrerPolicy(){
			return referrerPolicy;
		}

		public void setReferrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy referrerPolicy){
			this.referrerPolicy = referrerPolicy;
		}

	}

	/**
	 * XSS 配置
	 * <p><a href="https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Headers/X-XSS-Protection"
	 * target="_blank">https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Headers/X-XSS-Protection</a></p>
	 */
	public final static class Xss {

		/**
		 * 是否启用 Xss 配置
		 */
		private boolean enable = true;

		private boolean block;

		private boolean enabledProtection;

		/**
		 * 返回是否启用 Xss 配置
		 *
		 * @return 是否启用 Xss 配置
		 */
		public boolean isEnable(){
			return getEnable();
		}

		/**
		 * 返回是否启用 Xss 配置
		 *
		 * @return 是否启用 Xss 配置
		 */
		public boolean getEnable(){
			return enable;
		}

		/**
		 * 配置是否启用 Xss 配置
		 *
		 * @return 是否启用 Xss 配置
		 */
		public void setEnable(boolean enable){
			this.enable = enable;
		}

		public boolean isBlock(){
			return getBlock();
		}

		public boolean getBlock(){
			return block;
		}

		public void setBlock(boolean block){
			this.block = block;
		}

		public boolean isEnabledProtection(){
			return getEnabledProtection();
		}

		public boolean getEnabledProtection(){
			return enabledProtection;
		}

		public void setEnabledProtection(boolean enabledProtection){
			this.enabledProtection = enabledProtection;
		}

	}

}
